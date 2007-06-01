package procesador;
/**
 * La clase <B>CategoriaLexica</B> declara las categorias lexicas que nuestro lenguaje puede reconocer. Cada una de ellas es un valor entero.
 * <P>No tiene atributos, solo las constantes de los tipos.</P>
 * 
* @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */
public class CategoriaLexica {
	
	/*
	 * Declaracion de categorias lexicas, cada categoria lexica reconocida
	 * sera un valor entero.
	 */
	
	/**
	 * Valor para reconocer numeros enteros.
	 */
	public static final int TKNUM 		= 1; 
	
	/**
	 * Valor para reconocer un identificador.
	 */
	public static final int TKIDEN 		= 2; 
	
	/**
	 * Valor para reconocer una asignacion.
	 */
	public static final int TKASIGN	 	= 3; 
	
	/**
	 * Valor para reconocer un punto y coma ';'.
	 */
	public static final int TKPYCOMA		= 4; 
	
	/**
	 * Valor para reconocer un signo de suma '+'.
	 */
	public static final int TKSUMA		= 5; 
	
	/**
	 * Valor para reconocer un signo de resta '-'.
	 */
	public static final int TKRESTA		= 6; 
	
	/**
	 * Valor para reconocer un signo de multiplicacion '*'.
	 */
	public static final int TKMULT		= 7; 
	
	/**
	 * Valor para reconocer un signo de division '/'.
	 */
	public static final int TKDIV	 	= 8;
	
	/**
	 * Valor para reconocer un signo de division '%'.
	 */
	public static final int TKMOD	 	= 9;
	
	/**
	 * valor para reconocer un parentesis de apertura '('.
	 */
	public static final int TKPAP		= 10; 
	
	/**
	 * Valor para reconocer un parentesis de cierre ')'.
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
	 * Valor para reconocer un igual '=='.
	 */
	public static final int TKIG			= 16;
	
	/**
	 * Valor para reconocer el operador de distinto.
	 */
	public static final int TKDIF		= 17;
	
	/**
	 * Valor para reconocer el operador '&&'.
	 */
	public static final int TKAND		= 18;
	
	/**
	 * Valor para reconocer el operador '||'.
	 */
	public static final int TKOR			= 19;
	
	/**
	 * Valor para reconocer el operador '!'.
	 */
	public static final int TKNOT		= 20;
	
	/**
	 * Valor para reconocer el valor booleano 'true'.
	 */
	public static final int TKTRUE		= 21;
	
	/**
	 * Valor para reconocer el valor booleano 'false'.
	 */
	public static final int TKFALSE		= 22;
	
	/**
	 * Valor para reconocer el tipo int.
	 */
	public static final int TKINT		= 23;
	
	/**
	 * Valor para reconocer el tipo bool.
	 */
	public static final int TKBOOL		= 24;
	
	/**
	 * Valor para reconocer el tipo error.
	 */
	public static final int TKERROR		= 25;
	
	/**
	 * Valor para reconocer el final del fichero.
	 */
	public static final int TKFF		= 26;	
	
	/**
	 * Valor para reconocer el caracter '{'
	 */
	public static final int TKLLAP		= 27;
	
	/**
	 * Valor para reconocer el caracter '}'
	 */
	public static final int TKLLCI		= 28;
	
	/**
	 * Valor para reconocer la palabra reservada 'if'
	 */
	public static final int TKIF		= 29;
	
	/**
	 * Valor para reconocer la palabra reservada 'else'
	 */
	public static final int TKELSE		= 30;
	
	/**
	 * Valor para reconocer la palabra reservada 'then'
	 */
	public static final int TKTHEN		= 31;
	
	/**
	 * Valor para reconocer la palabra reservada 'while'
	 */
	public static final int TKWHILE		= 32;
	
	/**
	 * Valor para reconocer la palabra reservada 'do'
	 */
	public static final int TKDO		= 33;
	/**
	 * Valor para reconocer la palabra reservada 'type'
	 */
	public static final int TKTYPE		= 34;
	/**
	 * Valor para reconocer la palabra reservada 'reg'
	 */
	public static final int TKREG		= 35;
	/**
	 * Valor para reconocer el caracter '.'
	 */
	public static final int TKPUNTO		= 36;
	/**
	 * Valor para reconocer la palabra reservada 'array'
	 */
	public static final int TKARRAY		= 37;
}