package procesador;

public class Tipos {
	
	/*
	 * Declaracion de categorias lexicas, cada categoria lexica reconocida
	 * sera un valor entero.
	 */
	
	/*
	 * El valor para reconocer numeros enteros.
	 */
	public static final int TKNUM 		= 1;
	
	/*
	 * El valor para reconocer un tipo.
	 */
	public static final int TKTIPO 		= 2; 
	
	/*
	 * El valor para reconocer un identificador.
	 */
	public static final int TKIDEN 		= 3; 
	
	/*
	 * El valor para reconocer una asignacion.
	 */
	public static final int TKASIGN	 	= 4; 
	
	/*
	 * El valor para reconocer un punto y coma ';'.
	 */
	public static final int TKPYCOMA		= 5; 
	
	/*
	 * El valor para reconocer un signo de suma '+'.
	 */
	public static final int TKSUMA		= 6; 
	
	/*
	 * El valor para reconocer un signo de resta '-'.
	 */
	public static final int TKRESTA		= 7; 
	
	/*
	 * El valor para reconocer un signo de multiplicacion '*'.
	 */
	public static final int TKMULT		= 8; 
	
	/*
	 * El valor para reconocer un signo de division '/'.
	 */
	public static final int TKDIV	 	= 9; 
	
	/*
	 * El valor para reconocer un parentesis de apertura '('.
	 */
	public static final int TKPAP		= 10; 
	
	/*
	 * El valor para reconocer un parentesis de cierre ')'.
	 */
	public static final int TKPCI		= 11; 
	
	/*
	 * El valor para reconocer un menor o igual '<='.
	 */
	public static final int TKMENIG		= 12;
	
	/*
	 * El valor para reconocer un menor '<'.
	 */
	public static final int TKMEN		= 13;
	
	/*
	 * El valor para reconocer un mayor o igual '=>'.
	 */
	public static final int TKMAYIG		= 14;
	
	/*
	 * El valor para reconocer un mayor '>'.
	 */
	public static final int TKMAY		= 15;
	
	/*
	 * El valor para reconocer un igual '='.
	 */
	public static final int TKIG			= 16;
	
	/*
	 * El valor para reconocer el operador 'and'.
	 */
	public static final int TKAND		= 17;
	
	/*
	 * El valor para reconocer el operador 'or'.
	 */
	public static final int TKOR			= 18;
	
	/*
	 * El valor para reconocer el operador 'not'.
	 */
	public static final int TKNOT		= 19;
	
	/*
	 * El valor para reconocer el valor boleano 'true'.
	 */
	public static final int TKTRUE		= 20;
	
	/*
	 * El valor para reconocer el valor boleano 'false'.
	 */
	public static final int TKFALSE		= 21;
	
	/*
	 * El valor para reconocer el tipo int.
	 */
	public static final int TKINT		= 22;
	
	/*
	 * El valor para reconocer el tipo bool.
	 */
	public static final int TKBOOL		= 23;
	
	/*
	 * El valor para reconocer caracter que indica el final de las declaraciones '#'.
	 */
	public static final int TKCUA		= 24;
	
	/*
	 * El valor para reconocer el operador de distinto.
	 */
	public static final int TKDIF		= 25;
	
	/*
	 * El valor para reconocer el final del fichero.
	 */
	public static final int TKFF			= 26; 
}