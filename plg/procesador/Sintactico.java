package procesador;

import java.io.RandomAccessFile;

import maquinaP.Codigo;
import tablaSimbolos.TablaSimbolos;

/**
 * La clase <B>Sintactico</B> analiza los tokens que han sido reconocidos por <B>Lexico</B>. 
 * <P>La clase Sintactico cuenta con los siguientes atributos:
 * <UL><LI><CODE>codigo:</CODE> Se encarga de almacenar el cdigo generado por las instrucciones del lenguaje. De tipo Codigo, clase
 * incluida en el paquete <B>maquinaP</B>.</LI>
 * <LI><CODE>lexico:</CODE> Analiza el fichero de entrada para reconocer tokens. De tipo Lexico.</LI>
 * <LI><CODE>TS:</CODE> Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos. De tipo TablaSimbolos.</LI>
 * <LI><CODE>dir:</CODE> Entero que marca la posicin de la pila con la que estamos trabajando. De tipo Entero.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Jonas Andradas, Leticia Garcma y Silvia Martmn
 *
 */

public class Sintactico{
	
	/*
	 * Atributos de la clase:
	 * 
	 * codigo: Se encarga de almacenar el codigo generado por las instrucciones del lenguaje.
	 * lexico: Analiza el fichero de entrada para reconocer tokens.
 	 * TS: Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
 	 * dir: Entero que marca la posicin de la pila con la que estamos trabajando.
	 */
	Codigo codigo;
	Lexico lexico;
	TablaSimbolos TS;
	int dir;
	
	/**
	 * Constructor que inicializa los atributos con los datos que recibe por parametro.
	 * 
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contine que contine el cdigo a analizar.
	 * @param T Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
	 * @param f String donde se guarga la ruta del fichero donde se va a guardar el codigo generado por el compilador.
	 * @throws Exception Propaga una excepcion que haya sucedido en otro lugar.
	 */
	public Sintactico(RandomAccessFile fuente, TablaSimbolos T, String f) throws Exception{
		codigo = new Codigo(f);
		lexico = new Lexico(fuente);		
		TS = T;
		dir = 0;
	}

	/**
	 * Accesor para el atributo de la clase sintactico. 
	 * @return Devuelve el codigo generado hasta ese momento
	 */
	public Codigo getCodigo() {
		return codigo;
	}

	/**
	 * Mutador para el atributo de la clase sintactico. 
	 * @param codigo Recibe el nuevo valor del codigo que se ha generado
	 */
	public void setCodigo(Codigo codigo) {
		this.codigo = codigo;
	}

	/**
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		Prog();
		codigo.muestraCodigo();
	}

	/**
	 * Evalua el programa.  Primero lee las declaraciones de variables (identificadores), que se encuentran
	 * separados del conjunto de instrucciones "Is" mediante un "#".  Acto seguido, procesa cada instruccisn de Is.
	 * 
	 * @return errDeProg Devuelve un booleano que indica si existio un error al analizar el codigo del Programa. 
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */	
	public boolean Prog() throws Exception{
		boolean errDeProg = true;
		Atributos atrDeDecs;
		Atributos atrDeIs;
		atrDeDecs = Decs();
		atrDeIs = Is();
		errDeProg = atrDeDecs.getErr() || atrDeIs.getErr();
		return errDeProg;	
	}
	
	/**
	 * Recorre el conjunto de declaraciones (Dec) una por una.  Si tras una declaracisn encontramos
	 * un punto y coma (";"), procesamos otra mas.  Si en cambio lo que encontramos es una almohadilla
	 * ("#"), dejamos de leer Decs.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Decs() throws Exception{
		Atributos atrDeDecs;
		Atributos atrDeDec;
		Atributos a = new Atributos();
		boolean errDeDecs = false;
		atrDeDec = Dec();
		lexico.lexer();
		if (lexico.reconoce(Tipos.TKPYCOMA)){
			atrDeDecs = Decs();
			errDeDecs = atrDeDec.getErr() || atrDeDecs.getErr();
			a.setErr(errDeDecs);
			a.setTipo("");
			return a;
		}
		else{
			if (lexico.reconoce(Tipos.TKCUA)){
				errDeDecs = atrDeDec.getErr() || errDeDecs;
				a.setErr(errDeDecs);
				a.setTipo("");
				return a;
			}
			else{
				errDeDecs = true;
			}
		}
		a.setErr(errDeDecs);
		a.setTipo("");
		return a;
	}

	/**
	 * Procesa una declaracisn de variable.  Cada declaracion Dec consta de dos elementos:  El tipo de la variable
	 * y su nombre, de la forma: 
	 * 			tipo identificador;
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Dec() throws Exception{
		boolean errDeDec;
		Atributos a = new Atributos();
		String t = "";
		Token tk;
		tk = lexico.lexer();
		if (lexico.reconoce(Tipos.TKINT) || lexico.reconoce(Tipos.TKBOOL)){
			t = tk.getLexema();
			tk = lexico.lexer();
			if (lexico.reconoce(Tipos.TKIDEN)){
				String i = tk.getLexema();
				errDeDec = TS.existeID(i);
				if (!errDeDec){
					TS.agnadeID(i,t);
				}
				else {
					throw new Exception("ERROR: Identificador duplicado.  Cada identificador solo se puede declarar una vez");
				}
			}
			else{
				errDeDec = true;
				throw new Exception("ERROR: Declaracion Incorrecta. El formato correcto es \"tipo identificador;\".");
			}
		}
		else{
			errDeDec = true;
			throw new Exception("ERROR: Declaracion Incorrecta. El formato correcto es \"tipo identificador;\".");
		}
		a.setErr(errDeDec);
		a.setTipo(t);
		return a;
	}	
	
	/**
	 * Recorre el conjunto de Instrucciones del programa.  Cada instruccion I se separa del conjunto de 
	 * instrucciones restantes (Is) mediante un punto y coma (";").  Si encontramos el token Fin de Fichero,
	 * hemos terminado de leer instrucciones. 
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Is() throws Exception{
		Atributos atrDeIs;
		Atributos atrDeI;
		Atributos a = new Atributos();
		boolean errDeIs = false;
		atrDeI = I();
		if (lexico.reconoce(Tipos.TKFF)){
			errDeIs = false;	
		}
		else{
			if (lexico.reconoce(Tipos.TKPYCOMA)){
				atrDeIs = Is();
				errDeIs = atrDeI.getErr() || atrDeIs.getErr();
				a.setErr(errDeIs);
				a.setTipo(atrDeI.getTipo());
				return a;
			}
			else{
				errDeIs = true;
				throw new Exception("ERROR: Secuencia de Instrucciones Incorrecta. Cada instruccion ha de ir separada de la siguiente por un \";\"");
			}
		}
		a.setErr(errDeIs);
		return a;	
	}

	/**
	 * Procesa cada instruccisn el conjunto de instrucciones del Programa.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos I() throws Exception{
		Atributos a = new Atributos();
		Atributos atrDeIAsig;
		atrDeIAsig = IAsig();
		a.setErr(atrDeIAsig.getErr());
		return a;
	}
	
	/**
	 * Procesa una instruccisn de asignacisn, de la forma:
	 * 
	 * 		identificador := Expresisn.
	 * 
	 * Si hay un error en el formato de la instruccisn de asignacisn, o si 
	 * el tipo del identificador usado no coincide con el de la expresisn, 
	 * se lanza una Excepcisn.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos IAsig() throws Exception{
		Atributos  atrDeExpC = new Atributos();
		Atributos a = new Atributos();
		boolean errDeIAsig; 
		Token tk;
		String lex = "";
		tk = lexico.lexer();
		if (lexico.reconoce(Tipos.TKIDEN)){
			lex = tk.getLexema();
			tk = lexico.lexer();
			if (lexico.reconoce(Tipos.TKASIGN)){
				atrDeExpC = ExpC();
				errDeIAsig = (atrDeExpC.getTipo().compareTo(TS.getTipo(lex)) != 0 ) || !(TS.existeID(lex)) || (atrDeExpC.getTipo().compareTo("error")== 0 );
				if (!(TS.existeID(lex))){
					throw new Exception("ERROR: Identificador no declarado. \nEl identificador ha de estar declarado en la seccion de Declaraciones antes de que se le pueda asignar un valor.");
				}
				else{
						codigo.genIns("desapila-dir",TS.dirID(lex));
				}
			}
			else{
				errDeIAsig = true;
				throw new Exception("ERROR: Asignacisn Incorrecta. El formato correcto es \"identificador := Expresion;\".");
			}
		}
		else{
			if (! (lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKFF))){
				errDeIAsig = true;
				throw new Exception("ERROR: Asignacisn Incorrecta. El formato correcto es \"identificador := Expresion;\".");
			} 
			else {
				errDeIAsig = false;
			}
		}
		a.setErr(errDeIAsig);
		return a;
	}
	
	/**
	 * Procesa y desarrolla una Expresisn de Comparacisn, ExpC, llamando a Exp y a RExpC, 
	 * para empezar a desarrollar el arbol sintactico que reconocera la Expresisn. 
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos ExpC() throws Exception{
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		atrDeExp = Exp();
		Atributos a = new Atributos();
		atrDeRExpC = RExpC();
		if ( atrDeExp.getTipo().compareTo(atrDeRExpC.getTipo()) == 0){
			if (atrDeExp.getTipo().compareTo("int") == 0)
					a.setTipo("int");
			else if (atrDeExp.getTipo().compareTo("bool") == 0)
					a.setTipo("bool");
			else a.setTipo("error");
		}
		else{
			if (atrDeRExpC.getTipo().equals("")){
				a.setTipo(atrDeExp.getTipo());
			}else{
				a.setTipo("error");
			}
		}
		return a;
	}
	
	/**
	 * Reconoce la segunda mitad de una Expresisn de Comparacisn de la forma:
	 * 
	 *       OpComp Exp RExpC | landa
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RExpC() throws Exception{
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		Atributos a = new Atributos();
		if (!lexico.reconoce(Tipos.TKPYCOMA)){
			if (lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKMAYIG) || lexico.reconoce(Tipos.TKMEN) || lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) || lexico.reconoce(Tipos.TKDIF)){
				Token tk = lexico.getLookahead();
				atrDeExp = Exp();
				genOpComp(tk.getLexema());				
				atrDeRExpC = RExpC();
				if ( ( (atrDeExp.getTipo().compareTo(atrDeRExpC.getTipo()) == 0) && (atrDeExp.getTipo().compareTo("bool") == 0) ) || atrDeRExpC.getTipo().equals("")){
					a.setTipo(atrDeExp.getTipo());
				}
				else{
					a.setTipo("error");
				}
				return a;
			} 
		} 
		a.setTipo("");
		return a;
	}
	
	/**
	 * Reconoce una Expresisn, tanto aritmitica como lsgica (booleana).
	 *  
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Exp() throws Exception{
		Atributos atrDeTerm;
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		atrDeTerm = Term();
		atrDeRExp = RExp();
		if ( atrDeTerm.getTipo().compareTo(atrDeRExp.getTipo()) == 0){
			if (atrDeTerm.getTipo().compareTo("int") == 0)
					a.setTipo("int");
			else if (atrDeTerm.getTipo().compareTo("bool") == 0)
					a.setTipo("bool");
			else a.setTipo("error");
		}
		else{
			if (atrDeRExp.getTipo().equals("")){
				a.setTipo(atrDeTerm.getTipo());
			}else{
				a.setTipo("error");
			}
		}
		return a;
	}
	
	/**
	 * Reconoce la segunda mitad (con el operador) de la descomposicisn de una Expresisn booleana o aritmitica.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RExp() throws Exception{
		Atributos atrDeTerm = new Atributos();
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI))){
			Token tk;
			tk = lexico.getLookahead(); 
		
			boolean numerico = lexico.reconoce(Tipos.TKSUMA) || lexico.reconoce(Tipos.TKRESTA);
			boolean booleano = lexico.reconoce(Tipos.TKOR); 
		
			atrDeTerm = Term();
			
			if (numerico){
				genOpAd(tk.getLexema());
			} else if (booleano) {
				genOpAd("or");    // O deber?amos cambiarlo por un genOpAd(tk.getLexema()) tambi?n??  CONSULTAR
			}
			atrDeRExp = RExp();
			
			if ( (atrDeTerm.getTipo().compareTo("error") == 0) || (atrDeRExp.getTipo().compareTo("error") == 0) ){
				a.setTipo("error");
			} else {
				if (numerico){
					if ( atrDeRExp.getTipo().equals("int") && atrDeRExp.getTipo().equals(atrDeTerm.getTipo()) ){
						a.setTipo("int");
					} else {
						a.setTipo("error");
					}
				} else if (booleano){
					if ( atrDeTerm.getTipo().equals("bool") && atrDeTerm.getTipo().equals(atrDeRExp.getTipo()) ){
						a.setTipo("bool");
					} else {
						a.setTipo("error");
					}
				}
			}
		} 
		else {
			a.setTipo("");
		}
		return a;
	}
	
	/**
	 * Reconoce un Tirmino, compuesto de un Factor y un Tirmino Recursivo:
	 *  
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Term() throws Exception{
		Atributos atrDeFact;
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		atrDeFact = Fact();
		atrDeRTerm = RTerm();
		if ( atrDeFact.getTipo().compareTo(atrDeRTerm.getTipo()) == 0){
			if (atrDeFact.getTipo().compareTo("int") == 0)
					a.setTipo("int");
			else if (atrDeFact.getTipo().compareTo("bool") == 0)
					a.setTipo("bool");
			else a.setTipo("error");
		}
		else{
			if (atrDeRTerm.getTipo().equals("")){
				a.setTipo(atrDeFact.getTipo());
			}else{
				a.setTipo("error");
			}
		}
		return a;
	}
	
	/**
	 * Reconoce un Tirmino Aritmitico recursivo.
	 *  
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RTerm() throws Exception{
		Atributos atrDeFact;
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		Token tk;
		tk = lexico.lexer();
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKSUMA) ||
				lexico.reconoce(Tipos.TKRESTA))){
			

			boolean numerico = lexico.reconoce(Tipos.TKMULT) || lexico.reconoce(Tipos.TKDIV);
			boolean booleano = lexico.reconoce(Tipos.TKAND); 
		
			atrDeFact = Fact();
			
			if (numerico){
				genOpMul(tk.getLexema());
			} else if (booleano) {
				genOpMul("and");    // O deber?amos cambiarlo por un genOpAd(tk.getLexema()) tambi?n??  CONSULTAR
			}
			atrDeRTerm = RTerm();
			
			if ( atrDeFact.getTipo().equals("error") || atrDeRTerm.getTipo().equals("error")){
				a.setTipo("error");
			} else {
				if (numerico){ 
					if (atrDeFact.getTipo().equals(atrDeRTerm.getTipo()) && atrDeFact.getTipo().equals("int") ){
						a.setTipo(atrDeFact.getTipo()); // int
					} else {
						a.setTipo("error");
					}
				} else if (booleano) {
					if (atrDeFact.getTipo().equals(atrDeRTerm.getTipo()) && atrDeFact.getTipo().equals("bool") ){
						a.setTipo(atrDeFact.getTipo()); // int
					} else {
						a.setTipo("error");
					}
				}
			}
		}
		else{
			a.setTipo("");
		}
		return a;
	}

	
	/**
	 * Reconoce un Factor.  Un Factor puede ser un entero, un identificador o una Expresisn aritmitica
	 * entre parintesis.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Fact() throws Exception{
		Atributos a = new Atributos();
		Atributos atrDeExpC;
		Atributos atrDeFact;
		Token tk;
		tk = lexico.lexer();
		
		if (lexico.reconoce(Tipos.TKNUM)){
			a.setTipo("int");
			codigo. genIns("apila", Integer.parseInt(tk.getLexema()) );
		} 
		else if (lexico.reconoce(Tipos.TKTRUE) || lexico.reconoce(Tipos.TKFALSE)){
			a.setTipo("bool");
			int cod;
			if (tk.getLexema().equals("false"))
				cod = 0;
			else
				cod = 1;
			codigo.genIns("apila", cod);
		} else if (lexico.reconoce(Tipos.TKNOT) || lexico.reconoce(Tipos.TKRESTA)) {  // es un OpUn
			boolean numerico = lexico.reconoce(Tipos.TKRESTA); // numerico != true ==> booleano = true
			atrDeFact = Fact();
			if (numerico){
				genOpNega();
				if (atrDeFact.getTipo().equals("int")){
					a.setTipo(atrDeFact.getTipo());
				}else{
					a.setTipo("error");
				}
			} else {
				genOpNot();
				if (atrDeFact.getTipo().equals("bool")){
					a.setTipo(atrDeFact.getTipo());
				}else{
					a.setTipo("error");
				}
			}
		}
		else if (lexico.reconoce(Tipos.TKIDEN)){
				if (TS.existeID(tk.getLexema()) ){
					a.setTipo(TS.getTipo(tk.getLexema()));
				} else {
					a.setTipo("error");
				}
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema()));
			}
			else {
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExpC = ExpC();
					if (lexico.reconoce(Tipos.TKPCI)){
						a.setTipo(atrDeExpC.getTipo());
					}
					else{
						a.setTipo("error");
					}
				}
				else{
					a.setTipo("error");
				}
			}
		return a;
	}
	
	/**
	 * Genera el código de la operación de suma, resta o el or.
	 * 
	 * @param opDeOpAd
	 */
	public void genOpAd(String opDeOpAd){
		
		if (opDeOpAd == "+")
			codigo.genIns("suma");
		else if (opDeOpAd.equals("-"))
			codigo.genIns("resta");
		else
			codigo.genIns("or");
	}
	
	/**
	 * Genera el csdigo de la operacisn de multiplicacisn, divisisn o and.
	 * 
	 * @param opDeOpMul
	 */
	public void genOpMul(String opDeOpMul){
		
		if (opDeOpMul == "*")
			codigo.genIns("multiplica");
		else if (opDeOpMul.equals("/"))
			codigo.genIns("divide");
		else 
			codigo.genIns("and");
	}
	
	/**
	 * Genera el csdigo de la operacisn de comparacisn
	 * @param opDeOpComp
	 */
	public void genOpComp(String opDeOpComp){
		
		if (opDeOpComp == "<="){
			codigo.genIns("menor_o_igual");
		}	
		if (opDeOpComp == "<"){
				codigo.genIns("menor");
		}
		if (opDeOpComp == ">="){
			codigo.genIns("mayor_o_igual");
		}	
		if (opDeOpComp == ">"){
				codigo.genIns("mayor");
		}
		if (opDeOpComp == "="){
			codigo.genIns("igual");
		}	
		if (opDeOpComp == "!="){
				codigo.genIns("distinto");
		}
	}

	/**
	 * Genera el csdigo de la negacisn booleana "not".
	 *
	 */
	public void genOpNot(){

		codigo.genIns("not");
	}
	
	public void genOpNega(){
		codigo.genIns("neg");
	}
}