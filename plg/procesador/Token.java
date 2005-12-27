
package procesador; 

public class Token {
	
	private int categoriaLexica;
	private String lexema;
	
	public Token() {
		lexema = "";
		categoriaLexica = 0;
	}

	public Token(String lex, int tipo) {
		lexema = lex;
		categoriaLexica = tipo;
	}

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
	
}
