
package procesador; 

public class Token {
	
	/*
	 * Atributos de la clase:
	 * 
	 * categoria lexica nos indica a que categoria Lexica pertenece el Token,
	 * lexema es un string que nos da una representacion de lex del Token.
	 */
	private int categoriaLexica;
	private String lexema;
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 * Constructor sin parametros que inicializamos el lexema a vacio y la 
	 * categoria lexica a un valor que no indica nada.
	 */
	public Token() {
		lexema = "";
		categoriaLexica = 0;
	}
	
	/*
	 * Parametros de entrada: String que almacena el lex del Token, y String con
	 * 						el tipo del Token.
	 * Parametros de salida:
	 * 
	 * Constructor para inicializar con los valores que se reciben por parametro.
	 */
	public Token(String lex, int tipo) {
		lexema = lex;
		categoriaLexica = tipo;
	}

	/*
	 * Accesores y mutadores de los atributos lex y categoriaLexica.
	 */
	public int getCategoriaLexica() {
		return categoriaLexica;
	}

	public void setCategoriaLexica(int categoriaLexica) {
		this.categoriaLexica = categoriaLexica;
	}

	public String getLexema() {
		return lexema;
	}

	public void setLexema(String lexema) {
		this.lexema = lexema;
	}
	
	/*
	 * Parametros de entrada: Token con el que se quiere comparar si son iguales. 
	 * Parametros de salida: Boleano que nos indica la igualdad o no de los dos Tokens.
	 * 
	 * Compara un Token que recibe por parametro con sigo mismo. Para que sean iguales han de tener
	 * la misma categoria lexica y el mismo lexema.
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
	
	/*
	 * Parametros de entrada:
	 * Parametros de salida: String para poder mostrar el contenido de Token.
	 * 
	 * Pasamos Token a un String para poder mostrarlo por pantalla
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