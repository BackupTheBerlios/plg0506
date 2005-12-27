/* 
 * Created on May 9, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package procesador; 

/**
 * @author kat
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
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
	
	public String daLexema() {
		return lexema;
	}
	
	public int daCategoriaLexica() {
		return categoriaLexica;
	}
}
