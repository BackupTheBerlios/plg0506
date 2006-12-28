/**
 * <p>Título: </p>
 * <p>Descripción: </p>
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
    private int tipo; // bool=0, int=1. Cuando incluyamos tipo construidos le añadimos
    //un numero asociado a cada tipo construido. error= -1, y 2 para las constantes
    private int numLinea;

   /**
    * Construcctor de simbolo que es donde se guardan los atributos de un lexema
    * se inicializa con tipo -1, porque cuando se crean no se conoce ni su tipo
    * ni si están bien declarados
    * @param d dirección de memoria del símbolo
    * @param num Numero de linea en el que aparece por primera vez
    */
    public Simbolo( int d,int num){
      dir=d;
      tipo = -1;
      numLinea=num;
    }

   /**
    * Constructora de la clase Atributo que incluye el tipo como parametro
    * @param d dirección de memoria
    * @param t tipo del identificador
    * @param numLinea numero de linea en el que aparece por primera vez
    */
    public Simbolo( int d,int t,int numLinea){
     dir=d;
     tipo = t;
     numLinea=numLinea;
    }
   /**
    *Accesor a la dirección de memoria
    * @return int dirección de memoria
    */
    public int getDir() {return dir;}

   /**
    * Accesor al tipo
    * @return int que indica el tipo
    */
    public int getTipo() {return tipo;}

   /**
    * Accesor al numero de linea
    * @return int que indica el número de linea
    */
    public int getNumLinea() {return numLinea;}

   /**
    * Mutador del tipo
    * @param nuevoTipo  nuevo entero que indica el tipo del identificador
    */
    public void setTipo(int nuevoTipo) {tipo = nuevoTipo;}

    /**
     *Mutador del numero de linea
     * @param num Nueva información sobre el número de linea
     */
    public void setNumLinea(int num) {numLinea = num;}
  }

  private Hashtable tabla= new Hashtable();
  private int ultimaDir;
  private int tokenId;

/**
 *Constructora de la tabla de símbolos, incluye el nombre de todas la constantes del lenguaje,
 * en la tabla.
 */
  public tablaSimbolos() {
    ultimaDir= 0;
    añadeCte( "INT");
    añadeCte(" BOOL");
    añadeCte("OR");
    añadeCte("AND");
    añadeCte("MAS");
    añadeCte("MENOS");
    añadeCte("NOT");
    añadeCte("SUMA");
    añadeCte("RESTA");
    añadeCte("MULTIPLICA");
    añadeCte("DIVIDE");
    añadeCte("MOD");
    añadeCte("MENOR");
    añadeCte("MAYOR");
    añadeCte("MENORIGUAL");
    añadeCte("MAYORIGUAL");
    añadeCte("IGUAL");
    añadeCte("DISTINTO");
    añadeCte("LLAVEAB");
    añadeCte("LLAVECERR");
    añadeCte("PARENTAB");
    añadeCte("PARENTCERR");
    añadeCte("PUNTOYCOMA");
    añadeCte("ERROR");
    añadeCte("ID");
    añadeCte("ASIG");
    añadeCte("ENTERO");
    añadeCte("TRUE");
    añadeCte("FALSE");
    añadeCte("END");
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
   * @return int que indica la última dirección ocupada
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
 * Proporciona la dirección de memoria de un lexema dado
 * @param lex String que indica el lexema
 * @return int dirección del lexema
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
   *Añade un identificador a la tabla, se le asigna una dirección de memoria y
   * se le inicializa el tipo a -1, para que indique error hasta que el analizador
   * sintáctico compruebe que está bien declarado y con un tipo valido
   * @param lex lexema String
   * @param numLinea numero de linea int
   * @return true si se ha añadido correctamente, false si el identificador ya existia en la tabla
   */
  public boolean añadeID (String lex,int numLinea){
    boolean añadido;
    if (!tabla.containsKey(lex)){
      ultimaDir++;
      Simbolo atributos= new Simbolo ( ultimaDir, numLinea);
      tabla.put(lex, atributos);
      añadido = true;
    }
    else añadido=false;
    return añadido;
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
   * Añade constantes a la tabal con tipo 2, su numero de linea y su dirección son siempre
   * 0 porque nunca se vana consultar y además no hacen falta.
   * Este método es privado porque las demás clases solo van a añadira la tabla
   * de símbolos identificadores y no constantes.
   * @param lex
   */
  private void añadeCte(String lex){
    Simbolo atributos=new Simbolo(0,2,0);
    tabla.put(lex,atributos);
  }

   /** Método que muestra la tabla, primero el lexema, luego el tipo, la direccion y por ultimo el token
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
