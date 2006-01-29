
package procesador; 
/**
 * La clase <B>Token</B> define los atributos y métodos relacionados con los token que usan nuestro lenguaje
 * <P>Cuenta con los siguientes atributos:
 * <UL><LI><CODE>categoriaLexica:</CODE> entero que indica a que categoría léxica pertenece el token.</LI>
 * <LI><CODE>lexema:</CODE> string que representa la parte lex del Token.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Jonás Andradas, Leticia García y Silvia Martín
 *
 */
public class Token {
	
	/*
	 * Atributos de la clase:
	 * 
	 * categoria lexica nos indica a que categoria Lexica pertenece el Token,
	 * lexema es un string que nos da una representacion de lex del Token.
	 */
	private int categoriaLexica;
	private String lexema;
	
	/**
	 * Constructor sin parametros. Inicializa lexema a vacío y categoría léxica a un valor que no indica nada.
	 */
	public Token() {
		lexema = "";
		categoriaLexica = 0;
	}
	
	/**
	 * Constructor que inicializa Token con los valores que se reciben por parametro.
	 * 
	 * @param lex String que almacena el lex del Token.
	 * @param tipo entero con el tipo del Token.
	 *  
	 */
	public Token(String lex, int tipo) {
		lexema = lex;
		categoriaLexica = tipo;
	}

	/**
	 * Accesor para categoriaLexica.
	 * @return categoriaLexica
	 */
	public int getCategoriaLexica() {
		return categoriaLexica;
	}
	/**
	 * Mutador para categoriaLexica.
	 * @param categoriaLexica
	 */
	public void setCategoriaLexica(int categoriaLexica) {
		this.categoriaLexica = categoriaLexica;
	}
	/**
	 * Accesor para lexema.
	 * @return lexema
	 */
	public String getLexema() {
		return lexema;
	}
	/**
	 * Mutador para lexema.
	 * @param lexema
	 */
	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	/**
	 * 
	 * El método equals compara el Token que recibe como parámetro consigo mismo. Son iguales si tienen la misma categoría léxica y el mismo lexema.
	 * @param tk Token con el que se quiere comparar si son iguales. 
	 * @return b  Booleano que nos indica la igualdad o no de los dos Tokens.
	 * 
	 */
	public boolean equals(Token tk){
		boolean b = true;
		if (this.categoriaLexica == tk.getCategoriaLexica()){
			b = b && true;
		}
		else{
			b = b && false;
		}
		if (this.getLexema() == tk.getLexema()){
			b = b && true;
		}
		else{
			b = b && false;
		}
		return b;
	}
	
	/**
	 * El método muestraToken muestra por pantalla un Token
	 * 
	 * @return aux String para poder mostrar el contenido de Token.
	 */
	public String muestraToken(){
		String aux= "(";
		aux= aux.concat(lexema);
		String aux2= (new Integer(categoriaLexica)).toString();
		aux= aux.concat(" , ");
		aux= aux.concat(aux2);
		aux= aux.concat(")");
		return aux;
	}
}