package procesador;
/**
 * La clase <B>Tipos</B> declara las categorías léxicas que nuestro lenguaje puede reconocer. Cada una de ellas es un valor entero
 * <P>No tiene atributos, sólo las constantes de los tipos</P>
 * 
 * @author Paloma de la Fuente, Jonás Andradas, Leticia García y Silvia Martín
 *
 */
public class Tipos {
	
	/*
	 * Declaracion de categorias lexicas, cada categoria lexica reconocida
	 * sera un valor entero.
	 */
	
	/**
	 * Valor para reconocer números enteros.
	 */
	public static final int TKNUM 		= 1;
	
	/**
	 * Valor para reconocer un tipo.
	 */
	public static final int TKTIPO 		= 2; 
	
	/**
	 * Valor para reconocer un identificador.
	 */
	public static final int TKIDEN 		= 3; 
	
	/**
	 * Valor para reconocer una asignación.
	 */
	public static final int TKASIGN	 	= 4; 
	
	/**
	 * Valor para reconocer un punto y coma ';'.
	 */
	public static final int TKPYCOMA		= 5; 
	
	/**
	 * Valor para reconocer un signo de suma '+'.
	 */
	public static final int TKSUMA		= 6; 
	
	/**
	 * Valor para reconocer un signo de resta '-'.
	 */
	public static final int TKRESTA		= 7; 
	
	/**
	 * Valor para reconocer un signo de multiplicación '*'.
	 */
	public static final int TKMULT		= 8; 
	
	/**
	 * Valor para reconocer un signo de división '/'.
	 */
	public static final int TKDIV	 	= 9; 
	
	/**
	 * valor para reconocer un paréntesis de apertura '('.
	 */
	public static final int TKPAP		= 10; 
	
	/**
	 * Valor para reconocer un paréntesis de cierre ')'.
	 */
	public static final int TKPCI		= 11; 
	
	/**
	 * Valor para reconocer un menor o igual '<='.
	 */
	public static final int TKMENIG		= 12;
	
	/**
	 * Valor para reconocer un menor '<'.
	 */
	public static final int TKMEN		= 13;
	
	/**
	 * Valor para reconocer un mayor o igual '=>'.
	 */
	public static final int TKMAYIG		= 14;
	
	/**
	 * Valor para reconocer un mayor '>'.
	 */
	public static final int TKMAY		= 15;
	
	/**
	 * Valor para reconocer un igual '='.
	 */
	public static final int TKIG			= 16;
	
	/**
	 * Valor para reconocer el operador 'and'.
	 */
	public static final int TKAND		= 17;
	
	/**
	 * Valor para reconocer el operador 'or'.
	 */
	public static final int TKOR			= 18;
	
	/**
	 * Valor para reconocer el operador 'not'.
	 */
	public static final int TKNOT		= 19;
	
	/**
	 * Valor para reconocer el valor booleano 'true'.
	 */
	public static final int TKTRUE		= 20;
	
	/**
	 * Valor para reconocer el valor booleano 'false'.
	 */
	public static final int TKFALSE		= 21;
	
	/**
	 * Valor para reconocer el tipo int.
	 */
	public static final int TKINT		= 22;
	
	/**
	 * Valor para reconocer el tipo bool.
	 */
	public static final int TKBOOL		= 23;
	
	/**
	 * Valor para reconocer carácter que indica el final de las declaraciones '#'.
	 */
	public static final int TKCUA		= 24;
	
	/**
	 * Valor para reconocer el operador de distinto.
	 */
	public static final int TKDIF		= 25;
	
	/**
	 * Valor para reconocer el final del fichero.
	 */
	public static final int TKFF			= 26; 
}