/**
 * <p>T�tulo: </p>
 * <p>Descripci�n: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Empresa: </p>
 * @author sin atribuir
 * @version 1.0
 */
import java.util.*;

public class tablaSimbolos {

//Clase que contiene los atributos asiciados al lexema
  public class Simbolo{
    private int dir;
    private int tipo; // bool=0, int=1. Cuando incluyamos tipo construidos le a�adimos
    //un numero asociado a cada tipo construido. error= -1, y 2 para las constantes
    private int numLinea;

   /**
    * Construcctor de simbolo que es donde se guardan los atributos de un lexema
    * se inicializa con tipo -1, porque cuando se crean no se conoce ni su tipo
    * ni si est�n bien declarados
    * @param d direcci�n de memoria del s�mbolo
    * @param num Numero de linea en el que aparece por primera vez
    */
    public Simbolo( int d,int num){
      dir=d;
      tipo = -1;
      numLinea=num;
    }

   /**
    * Constructora de la clase Atributo que incluye el tipo como parametro
    * @param d direcci�n de memoria
    * @param t tipo del identificador
    * @param numLinea numero de linea en el que aparece por primera vez
    */
    public Simbolo( int d,int t,int numLinea){
     dir=d;
     tipo = t;
     numLinea=numLinea;
    }
   /**
    *Accesor a la direcci�n de memoria
    * @return int direcci�n de memoria
    */
    public int getDir() {return dir;}

   /**
    * Accesor al tipo
    * @return int que indica el tipo
    */
    public int getTipo() {return tipo;}

   /**
    * Accesor al numero de linea
    * @return int que indica el n�mero de linea
    */
    public int getNumLinea() {return numLinea;}

   /**
    * Mutador del tipo
    * @param nuevoTipo  nuevo entero que indica el tipo del identificador
    */
    public void setTipo(int nuevoTipo) {tipo = nuevoTipo;}

    /**
     *Mutador del numero de linea
     * @param num Nueva informaci�n sobre el n�mero de linea
     */
    public void setNumLinea(int num) {numLinea = num;}
  }

  private Hashtable tabla= new Hashtable();
  private int ultimaDir;
  private int tokenId;

/**
 *Constructora de la tabla de s�mbolos, incluye el nombre de todas la constantes del lenguaje,
 * en la tabla.
 */
  public tablaSimbolos() {
    ultimaDir= 0;
    a�adeCte( "INT");
    a�adeCte(" BOOL");
    a�adeCte("OR");
    a�adeCte("AND");
    a�adeCte("MAS");
    a�adeCte("MENOS");
    a�adeCte("NOT");
    a�adeCte("SUMA");
    a�adeCte("RESTA");
    a�adeCte("MULTIPLICA");
    a�adeCte("DIVIDE");
    a�adeCte("MOD");
    a�adeCte("MENOR");
    a�adeCte("MAYOR");
    a�adeCte("MENORIGUAL");
    a�adeCte("MAYORIGUAL");
    a�adeCte("IGUAL");
    a�adeCte("DISTINTO");
    a�adeCte("LLAVEAB");
    a�adeCte("LLAVECERR");
    a�adeCte("PARENTAB");
    a�adeCte("PARENTCERR");
    a�adeCte("PUNTOYCOMA");
    a�adeCte("ERROR");
    a�adeCte("ID");
    a�adeCte("ASIG");
    a�adeCte("ENTERO");
    a�adeCte("TRUE");
    a�adeCte("FALSE");
    a�adeCte("END");
  }
  /**
   * Accesor a la Hashtable
   * @return devuelve la tabla
   */
  public Hashtable dameTabla(){
    return tabla;
  }
  /**
   * Accesor al numero de identificadores introducidos en la tabla
   * @return int que indica la �ltima direcci�n ocupada
   */
 public int dameUltimaDir(){
    return ultimaDir;
  }
// Funciones para obtener los datos de la tabla

/**
 * Devuelve el tipo de un lexema dado
 * @param lex Lexema a buscar (String)
 * @return int  tipo
 */
  public int dameTipo (String lex){
    Simbolo atribut;
    atribut= (Simbolo) tabla.get(lex);
    return atribut.getTipo();
  }
/**
 * Proporciona la direcci�n de memoria de un lexema dado
 * @param lex String que indica el lexema
 * @return int direcci�n del lexema
 */
  public int dameDir (String lex){
    Simbolo atribut;
    atribut= (Simbolo) tabla.get(lex);
    return atribut.getDir();
  }

  /**
   */
  /**
   * Da el numero de linea en el que aparece el identificador por primera vez
   * @param lex lexema a buscar
   * @return int, numero de linea
   */
  public int dameNumLinea(String lex){
    Simbolo atributo;
    if(tabla.containsKey(lex)){
    atributo=(Simbolo)tabla.get(lex);
    return atributo.getNumLinea();
    }
    else return 0;
  }

  /**
   *A�ade un identificador a la tabla, se le asigna una direcci�n de memoria y
   * se le inicializa el tipo a -1, para que indique error hasta que el analizador
   * sint�ctico compruebe que est� bien declarado y con un tipo valido
   * @param lex lexema String
   * @param numLinea numero de linea int
   * @return true si se ha a�adido correctamente, false si el identificador ya existia en la tabla
   */
  public boolean a�adeID (String lex,int numLinea){
    boolean a�adido;
    if (!tabla.containsKey(lex)){
      ultimaDir++;
      Simbolo atributos= new Simbolo ( ultimaDir, numLinea);
      tabla.put(lex, atributos);
      a�adido = true;
    }
    else a�adido=false;
    return a�adido;
  }

  /**
   */
  /**
   * Comprueba si existe el lexema en la tabla
   * @param lex lexema a buscar
   * @return true si ya se encuentra en la tabla
   */
  public boolean existeID (String lex){
    return tabla.containsKey(lex);
  }

  /**
   * A�ade constantes a la tabal con tipo 2, su numero de linea y su direcci�n son siempre
   * 0 porque nunca se vana consultar y adem�s no hacen falta.
   * Este m�todo es privado porque las dem�s clases solo van a a�adira la tabla
   * de s�mbolos identificadores y no constantes.
   * @param lex
   */
  private void a�adeCte(String lex){
    Simbolo atributos=new Simbolo(0,2,0);
    tabla.put(lex,atributos);
  }

   /** M�todo que muestra la tabla, primero el lexema, luego el tipo, la direccion y por ultimo el token
   * @return La concatenacion de todos esos valores
   * */
  public String muestraTabla ()
  {
    String s = "";
    Enumeration x = tabla.keys();
    String lex;
    for (int i=0;i<tabla.size();i++)
    {
      lex = (String) x.nextElement();
     Simbolo a = (Simbolo) tabla.get(lex);
      s = s + " " + lex +"  "+ a.getDir()+" "+a.getTipo()+" "+a.getNumLinea()+"/n";
    }
    return s;
  }

  /**
   * Modifica el tipo de un identificador
   * @param lex Lexema por el que se busca el identificador
   * @param t nuevo tipo del identificador
   */
  public void ponTipo (String lex, int t){
      Simbolo atributo=(Simbolo)tabla.get(lex);
      if(atributo.getTipo()!=2){
        atributo.setTipo(t);
      }

  }

}
