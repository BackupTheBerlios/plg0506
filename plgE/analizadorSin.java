/**
 * <p>Título: </p>
 * <p>Descripción: Analizador Sintáctico</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Empresa: </p>
 * @author 
 * @version 1.0
 */

public class analizadorSin {

	/**
	 * tabla de símbolos, lo recibe por parámetro en el constructor
	 */
	private tablaSimbolos ts;
	/**
	 *token que se analiza en cada momento 
	 */
	private int tokenActual;
	/**lexema del token que se analiza en cada momento
	 *(si está a null es que no lo necesitamos para nada)
	 */
	private String lexemaActual;
	/**objeto que devuelve el metodo principal, contiene los errores y las
	 *instrucciones del lenguaje objeto
	 */
	private resultadoAnalisis resultado;
	/**objeto del analizador léxico que se le pasa por parámetro al constructor
	 */
	private AnalizadorLexico lexico;
	/**
	 *tipo del identificador 
	 */
	private int tipoId;

   /**
    * Constructor
    * @param ts: tabla de simbolos
    * @param l: léxico
    * 
    */
  public analizadorSin(tablaSimbolos ts, AnalizadorLexico l) {
    this.ts = ts;
    lexico = l;
    resultado = new resultadoAnalisis();
    this.actualizaAtributos(lexico.siguienteToken());
    tipoId = 2;
  }
//----------------------------------------------------------------------------//

  /**
   * Método principal
   * @return resultado, objeto de la clase resultadoAnalisis
   */
  resultadoAnalisis analizadorSintactico (){
    prog ();
    return resultado;
  }
//----------------------------------------------------------------------------//

  /**
   * Reconoce la estructura de un programa
   */
  private void prog (){
    decs();
    if (tokenActual == Constantes.LLAVEAB){
      reconoce(Constantes.LLAVEAB);
      is();
      reconoce(Constantes.LLAVECERR);
      //Añade la instrucción stop al vector de instrucciones de resultadoAnalisis
      resultado.emite("stop");
    } else
      resultado.añadeError("Error en la estructura del programa ");
  }

//----------------------------------------------------------------------------//
  /**
   * Producción de las declaraciones
   */
  private void decs(){
    dec();
    rdecs();
  }

//----------------------------------------------------------------------------//
  /**
   * Producción del resto de las declaraciones
   */
  private void rdecs(){
    if (tokenActual == Constantes.PUNTOYCOMA){
      reconoce(Constantes.PUNTOYCOMA);
      if (tokenActual == Constantes.PUNTOYCOMA)
        resultado.añadeError("Error por token duplicado: "+Constantes.traduce(tokenActual));
      dec();
      rdecs();
    }
  }
//----------------------------------------------------------------------------//
  /**
   * Declaración
   */
  private void dec(){
    int tipo = 0;
    if (tokenActual == Constantes.INT) {
      //lee el siguiente token
      reconoce(Constantes.INT);
      //1 es el tipo de los enteros en la ts
      tipo = 1;
      // si es una declaración:
      if (tokenActual == Constantes.ID) {
        // si el identificador ya estaba en la tabla de simbolos
        // es que ha sido declarado dos veces: error!
        if (ts.dameTipo(lexemaActual) == -1)
          ts.ponTipo(lexemaActual, tipo);
        else
        /*hay error!*/{
          resultado.añadeError("error por identificador duplicado, en linea "+ts.dameNumLinea(lexemaActual)+" ");
        }
        id();

      }
    }
    else if (tokenActual == Constantes.BOOL) {
      //lee el siguiente token
      reconoce(Constantes.BOOL);
      //0 es el tipo de los booleanos en la ts
      tipo = 0;
      // si es una declaración:
      if (tokenActual == Constantes.ID) {
        // si el identificador ya estaba en la tabla de simbolos
        // es que ha sido declarado dos veces: error!
        if (ts.dameTipo(lexemaActual) == -1)
          ts.ponTipo(lexemaActual, tipo);
        else{
          /*hay error!*/
          resultado.añadeError("error por identificador duplicado, en línea "+ts.dameNumLinea(lexemaActual)+" ");
        }
      }
      id();
    }
  }
//----------------------------------------------------------------------------//
  /**
   * Método que crea el código para un identificador y busca el siguiente token
   */
  void id(){
    if (ts.dameTipo(lexemaActual) == -1)
      resultado.añadeError("El identificador "+lexemaActual+" debe ser declarado previamente, en línea "+ts.dameNumLinea(lexemaActual)+" ");
    if (tokenActual == Constantes.ID)
      reconoce(Constantes.ID);
  }
//----------------------------------------------------------------------------//
  /**
   * Cadena de instrucciones
   */
  private void is (){
    i();
    rIs();
  }
//----------------------------------------------------------------------------//
  /**
   * Instrucción
   */
  private void i(){
    //por ahora solo tenemos instrucciones de asignacion
    iAsig();
  }
//----------------------------------------------------------------------------//
  /**
   * Resto de instrucciones
   */
  private void rIs(){
    if (tokenActual == Constantes.PUNTOYCOMA){
      reconoce (Constantes.PUNTOYCOMA);
      if (tokenActual == Constantes.PUNTOYCOMA)
        resultado.añadeError("Error por token duplicado: "+Constantes.traduce(tokenActual));
      i();
      rIs();
    }
  }
//----------------------------------------------------------------------------//
  /**
   * Instrucción de asignación
   */
  private void iAsig(){
    int tipo2 = -1;
    if (tokenActual == Constantes.ID){
      int dir = ts.dameDir(lexemaActual);
      tipoId = ts.dameTipo(lexemaActual);
      id ();
      if (tokenActual == Constantes.ASIG) {
        reconoce(Constantes.ASIG);
        tipo2 = expOr();
      }
      if (dir >= 0){
        resultado.emite("desapila-dir", dir);
      } else /*error*/
      resultado.añadeError("El identificador "+lexemaActual+" debe ser declarado previamente, en línea "+ts.dameNumLinea(lexemaActual)+" ");
      if ((tipoId == -1) || (tipo2 == -1) || (tipoId!=tipo2))
          resultado.añadeError("Error de tipado");
    }
  }
//----------------------------------------------------------------------------//
 /**
  * Expresion Or
  * @return el tipo de la expresión
  * -1 si es error, 0 booleano 1 entero 
  */
private int expOr(){
    int tipo = tipoId;
    int tipo1 = expAnd();
    int tipo2 = rExpOr();
    if (tipo1 == tipo2 && tipo1 != -1)
      tipo = tipo1;
    else tipo = -1;
    return tipo;
  }
//----------------------------------------------------------------------------//
  /**
   * Expresión And
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int expAnd(){
    int tipo = tipoId;
    int tipo1 = expRel();
    int tipo2 = rExpAnd();
    if (tipo1 == tipo2 && tipo1 != -1)
      tipo = tipo1;
    else tipo = -1;
    return tipo;
  }
//----------------------------------------------------------------------------//
  /**
   * Resto expresión Or
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int rExpOr(){
    int tipo = tipoId;
    int tipo1 = 0;
    int tipo2 = 0;
    if (tokenActual == Constantes.OR){
      reconoce (Constantes.OR);
      tipo1 = expAnd ();
      resultado.emite ("or");
      tipo2 = rExpOr();
      if (tipo1 == tipo2 && tipo1 == 0)
        tipo = tipo1;
      else tipo = -1;
    }
    return tipo;
  }
//----------------------------------------------------------------------------//
  /**
   * Expresión Relación
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int expRel (){
    int tipo = tipoId;
    int tipo1 = expAd();
    int tipo2 = rExpRel();
    if (tipo1!=-2 && tipo2!=-2){
      if (tipo1 == tipo2 && tipo1 != -1 && tipoId == 0)
        tipo = 0;
      else tipo = -1;
      } else if (tipo1 == -2)
        tipo = tipo2;
      else tipo = tipo1;
    return tipo;
  }
//----------------------------------------------------------------------------//
  /**
   * Resto expresión rel
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int rExpRel(){
    int tipo2 = 0;
    int tipo = -2;
    if (esOpRel(tokenActual)){
      String s = opRel();
      reconoce (tokenActual);
      tipo2 = expAd();
      resultado.emite(s);
      tipo = tipo2;
    }
    return tipo;
   }
//----------------------------------------------------------------------------//
  /**
   * Resto expresión And
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int rExpAnd (){
    int tipo1 = 0;
    int tipo2 = 0;
    int tipo = tipoId;
    if (tokenActual == Constantes.AND){
      reconoce (Constantes.AND);
      tipo1 = expRel ();
      resultado.emite("and");
      tipo2 = rExpAnd();
      if (tipo1 == tipo2 && tipo1 == 0)
        tipo = tipo1;
      else tipo = -1;
    }
    return tipo;
  }
//----------------------------------------------------------------------------//
  /**
   * Expresiónes de suma
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int expAd (){
    int tipo = tipoId;
    int tipo1 = expMul();
    int tipo2 = rExpAd();
    if (tipo1!=-2 && tipo2!=-2){
      if (tipo1 == tipo2 && tipo1 != -1)
        tipo = tipo1;
      else tipo = -1;
    } else if (tipo1 == -2)
      tipo = tipo2;
    else tipo = tipo1;
    return tipo;
  }

//----------------------------------------------------------------------------//
  /**
   * Función auxiliar que indica si un token es o no un operador de relación
   * @param t: token
   * @return true o false
   */
  private boolean esOpRel (int t){
    return (t==Constantes.MAYOR || t == Constantes.MAYORIGUAL || t == Constantes.MENOR
            || t == Constantes.MENORIGUAL || t == Constantes.IGUAL || t == Constantes.DISTINTO);
  }
//----------------------------------------------------------------------------//
  /**
   * Resto de expresiones de Suma
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int rExpAd(){
    int tipo1 = 1;
    int tipo2 = 1;
    int tipo = -2;
    if (esOpAd(tokenActual)){
      String s = opAd();
      reconoce (tokenActual);
      tipo1 =  expMul();
      resultado.emite(s);
      tipo2 = rExpAd();
      if (tipo1 == tipo2 && tipo1 == 1)
        tipo = tipo1;
      else
        if (tipo2 == -2 && tipo1 == 1)
          tipo = tipo1;
      else if (tipo1 == -2 && tipo2 == 1)
        tipo = tipo2;
      else tipo = -1;
    }
    return tipo;
  }

//----------------------------------------------------------------------------//
  /**
   * Función auxiliar que indica si un token es o no un operador de suma.
   * @param t:token
   * @return
   */
  private boolean esOpAd (int t){
    return (t == Constantes.SUMA || t == Constantes.RESTA);
  }
//----------------------------------------------------------------------------//
  /**
   * Expresión de Multiplicación
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int expMul (){
    int tipo = tipoId;
    int tipo1 = fact();
    int tipo2 = rExpMul();
    if (tipo2!=-2){
      if (tipo1 == tipo2 && tipo1 != -1)
        tipo = tipo1;
      else tipo = -1;
    } else tipo = tipo1;
    return tipo;
  }
//----------------------------------------------------------------------------//
  
  /**
   * Resto de la expresion de multiplicación
   * @return
   */
  private int rExpMul(){
    int tipo = -2;
    int tipo1 = 1;
    int tipo2 = 1;
    if (esOpMul(tokenActual)){
      String s = opMul();
      reconoce (tokenActual);
      tipo1 = fact();
      resultado.emite(s);
      tipo2 = rExpMul();
      if (tipo1 == tipo2 && tipo1 == 1)
        tipo = tipo1;
      else
        if (tipo2 == -2 && tipo1 == 1)
          tipo = tipo1;
      else if (tipo1 == -2 && tipo2 == 1)
        tipo = tipo2;
      else tipo = -1;
    }
    return tipo;
  }
//----------------------------------------------------------------------------//
  /**
   * Función auxiliar que indica si una función es o no un operador de multiplicación
   * @param t
   * @return
   */
  private boolean esOpMul (int t){
    return (t==Constantes.MULTIPLICA || t == Constantes.MOD || t == Constantes.DIVIDE);
  }
//----------------------------------------------------------------------------//
  
  /**
   * Método asiciado con la producción fact
   * @return el tipo de la expresión
   * -1 si es error, 0 booleano 1 entero
   */
  private int fact(){
    int tipo = tipoId;
    int tipo1 = -1;
    switch (tokenActual)
    {
      case Constantes.ENTERO:
        Integer i = new Integer (lexemaActual);
        resultado.emite("apila",i.intValue());
        reconoce (Constantes.ENTERO);
        tipo = 1;
        break;
      case Constantes.ID:
        //ver si está declarado
        tipo = ts.dameTipo(lexemaActual);
        if (tipo!=-1){
          resultado.emite("apila-dir",ts.dameDir(lexemaActual));
          reconoce (Constantes.ID);
        } else {
          //añado el error al vector de resultado análisis
          resultado.añadeError("El identificador "+lexemaActual+" debe ser declarado previamente. Linea: "+ts.dameNumLinea(lexemaActual)+ " ");
        }
        break;
      case Constantes.PARENTAB:
        reconoce (Constantes.PARENTAB);
        tipo = expOr();
        if (Constantes.PARENTCERR == tokenActual)
          reconoce (Constantes.PARENTCERR);
        else resultado.añadeError("Falta paréntesis de cierre.");
        break;
      //opUnario
      case Constantes.SUMA:
        reconoce(Constantes.SUMA);
        tipo = this.fact();
        if (tipo1!=1)
          tipo = -1;
        else tipo = 1;
        resultado.emite("mas");
        break;
      //opUnario
      case Constantes.RESTA:
        reconoce(Constantes.RESTA);
        tipo1 = fact();
        if (tipo1!=1)
          tipo = -1;
        else tipo = 1;
        resultado.emite("menos");
        break;
      //opUnario
      case Constantes.NOT:
        reconoce(Constantes.NOT);
        tipo1 = fact();
        if (tipo1!=0)
          tipo = -1;
        else tipo = 0;
        resultado.emite("not");
        break;
      case Constantes.TRUE:
        //según el lenguaje objeto vamos a apilar un 1 si es TRUE
        resultado.emite("apila",1);
        reconoce(Constantes.TRUE);
        tipo = 0;
        break;
      case Constantes.FALSE:
        resultado.emite("apila",0);
        reconoce(Constantes.FALSE);
        tipo = 0;
        break;
      case Constantes.ERROR:
        reconoce(Constantes.ERROR);
        break;
      default :
        resultado.añadeError("Error, se esperaba un identificador, un número o una expresión. ");
    }
    return tipo;
  }

//----------------------------------------------------------------------------//
/**Operadores:
 * 
 *OpUnario ::= + | - | !
 *OpAd ::= + | -
 *OpMul ::= * | / | %
 *OpRel ::= < | > | <= | >= | = | !=
 */	
  private String opAd (){
    switch(tokenActual)
        {
          case Constantes.SUMA:
            return ("suma");
          case Constantes.RESTA:
            return ("resta");
        }
        return "";
  }
//----------------------------------------------------------------------------//
  private String opMul(){
    String aux = "";
    switch (tokenActual){
      case Constantes.MOD:
        aux = "mod";
        break;
      case Constantes.MULTIPLICA:
        aux = "multiplica";
        break;
      case Constantes.DIVIDE:
        aux = "divide";
        break;
    }
    return aux;
  }
//----------------------------------------------------------------------------//
  private String opRel(){
    switch (tokenActual){
      case Constantes.MAYOR:
        return ("mayor");
      case Constantes.MAYORIGUAL:
        return ("mayor-igual");
      case Constantes.MENOR:
        return ("menor");
      case Constantes.MENORIGUAL:
       return ("menor-igual");
      case Constantes.IGUAL:
       return ("igual");
      case Constantes.DISTINTO:
        return ("distinto");
    }
    return "";
  }
//----------------------------------------------------------------------------//
  /**
   * Método que reconoce un token y lee el siguiente
   * @param n: Número del token a reconocer.
   */
private void reconoce (int n){
	//Si es el token error, añado el error al resultado
    if (tokenActual == Constantes.ERROR){
      resultado.añadeError(lexemaActual);
      this.actualizaAtributos(lexico.siguienteToken());
    } else{
    	//si el token es el esperado busco el siguiente token
      if (tokenActual == n){
        this.actualizaAtributos(lexico.siguienteToken());
      }
      //si no se corresponde el token, se añade el error al resultado
      else resultado.añadeError("Error al reconocer el siguiente token: "+Constantes.traduce(tokenActual));
    }
  }

//----------------------------------------------------------------------------//
/**
 * Método que dado un array con dos object actualiza los atributos token y lexema
 * asignando a cada uno las posiciones 0 y 1 del array respectivamente.
 * @param v array de [token,lexema]
 */
  void actualizaAtributos (Object[] v){
    Integer i = (Integer)v[0];
    tokenActual =  i.intValue();
    if (v[1] == null)
      lexemaActual = null;
    else
      lexemaActual = v[1].toString();
  }
  
}