package procesador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;



/**
 * La clase <B>Lexico</B> analiza el fichero de entrada para reconocer tokens. Lanza una excepcin en caso de que esto no sea posible. Puede suceder
 * que el token sea erroneo o que ocurra algun problema con el fichero de lectura.
 * <P>La clase Lexico cuenta con los siguientes atributos:
 * <UL><LI><CODE>linea:</CODE> Entero que controla la lnea del cdigo donde se detecta el error.</LI>
 * <LI><CODE>lookahead:</CODE> Token que almacena los caracteres de prean?lisis.</LI>
 * <LI><CODE>fuente:</CODE> RandomAccessFile que se utiliza para leer del fichero que contine que contine el cdigo a analizar.</LI>
 * <LI><CODE>posicion:</CODE> Entero que marca la posicin en la que se est leyendo dentro de una lnea.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */

public class Lexico {
	
	/*
	 * Los Atributos:
	 * 
	 * linea sirve para controlar en que linea de codigo detectamos el error.
	 * lookahead sirve para almacenar el caracteres de preanalisis.
	 * fuente se utiliza para leer del fichero que contiene el codigo a analizar.
	 * posicion entero que marca la posicion dentro de una linea por donde se esta
	 * leyendo.
	 */
	int linea;
	Token lookahead;
	RandomAccessFile fuente;
	int posicion;
	
	/**
	 * El constructor de la clase Lexico que slo tiene el buffer de lectura del fichero como parmetro de entrada.
	 * @param f Buffer de entrada del que se leen caracteres. Que es del tipo RandomAccessFile.
	 * 
	 */
	public Lexico(File f) {
		linea = 0;
		lookahead = new Token();
		try {
			fuente = new RandomAccessFile(f, "r");
		} 
		catch (FileNotFoundException e) {	
		}
		posicion = 0;
	}
	
	/**
	 * Accesor para el atributo de la clase, linea. 
	 * @return Entero que controla la lnea del cdigo donde se detecta el error.
	 */
	public int getLinea() {
		return linea;
	}
	
	/**
	 * Mutador para el atributo de la clase linea. 
	 * @param linea Entero que controla la lnea del cdigo donde se detecta el error.
	 */
	public void setLinea(int linea) {
		this.linea = linea;
	}
	
	/**
	 * Accesor para el atributo de la clase posicion. 
	 * @return Entero que marca la posicin en la que se este leyendo dentro de una linea.
	 */
	public int getPosicion() {
		return posicion;
	}
	
	/**
	 * Mutador para el atributo de la clase posicion. 
	 * @param posicion Entero que marcara la posicin en la que se est leyendo dentro de una linea.
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * Accesor para el atributo de la clase lookahead. 
	 * @return Token actual que almacena los caracteres de preanalisis.
	 */
	public Token getLookahead() {
		return lookahead;
	}
	
	/**
	 * Mutador para el atributo de la clase lookahead. 
	 * @param lookahead Token para modificar el token actual que almacena los caracteres de preanalisis.
	 */
	public void setLookahead(Token lookahead) {
		this.lookahead = lookahead;
	}
	
	/**
	 * Accesor para el atributo de la clase fuente. 
	 * @return RandomAccessFile que se utiliza para leer del fichero que contiene el codigo a analizar
	 */
	public RandomAccessFile getFuente() {
		return fuente;
	}
	
	/**
	 * Mutador para el atributo de la clase fuente. 
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contine el codigo a analizar
	 */
	public void setFuente(RandomAccessFile fuente) {
		this.fuente = fuente;
	}

	/**
	 * El metodo getToken lee caracteres del flujo hasta que identifica un token y lo devuelve, o detecta un error y genera una excepcion.
	 * Utiliza las funciones que ofrece RandomAccessFile para manejar el flujo que lee de fichero.
	 * 
	 * @see java.io.RandomAccessFile#read() 
	 * @see java.io.RandomAccessFile#seek(long) 
	 * @return Token que hemos identificado.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 */
	private Token getToken () throws IOException, Exception{
		
		char a;
		int error;
		boolean compara;
		/*
		 * La funcion read() saca un caracter del flujo de entrada
		 */
		
		while ((error = fuente.read())!=-1){
			
			a = (char) error;
			posicion ++;
			a = Character.toLowerCase(a);
			switch (a){
			//s0
			case '\n':	linea ++;
						break;			
			case '\t':	break;
			case '\f':	break;
			case '\r':	break;
			case ' ':	break;
			//s1
			case '=':	return new Token("=",CategoriaLexica.TKIG); 
			//s2 
			case ':':	compara = cmp(posicion, ":=");
						if (compara){
							return new Token(":=",CategoriaLexica.TKASIGN);
						}	
						else{ // s5
							return new Token(":",CategoriaLexica.TKDOSPUNTOS);
						}
			case '>':	compara = cmp(posicion, ">=");
						if (compara){//s3
							return new Token(">=",CategoriaLexica.TKMAYIG);
						}
						else{//s5
							return new Token(">",CategoriaLexica.TKMAY);
						}
			case '<':	compara = cmp(posicion, "<=");
						if (compara){//s4
							return new Token("<=",CategoriaLexica.TKMENIG);
							}
						else{
							compara = cmp(posicion,"<>");
							if (compara){//s6
								return new Token ("<>",CategoriaLexica.TKDIF);
							}
							else{//s5
								return new Token("<",CategoriaLexica.TKMEN);
							}
						}
			//s7
			case ';':	return new Token(";",CategoriaLexica.TKPYCOMA);
			case '.':	return new Token(".",CategoriaLexica.TKPUNTO);
			case ',':	return new Token(",",CategoriaLexica.TKCOMA);
			case '(':	return new Token("(",CategoriaLexica.TKPAP);
			case ')':	return new Token(")",CategoriaLexica.TKPCI);
			case '{':	return new Token("{",CategoriaLexica.TKLLAP);
			case '}':	return new Token("}",CategoriaLexica.TKLLCI);
			case '*':	return new Token("*",CategoriaLexica.TKMULT);
			//s8
			case '+':	return new Token("+",CategoriaLexica.TKSUMA);
			case '-':	return new Token("-",CategoriaLexica.TKRESTA);
			//s11
			case '0':	return new Token("0",CategoriaLexica.TKNUM);						
			/*
			 * Si detectamos 't' hay que discernir si es el valor boolenao 'true' o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 't':	compara = cmp(posicion, "true");
						if (compara){
							return new Token("true",CategoriaLexica.TKTRUE);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'f' hay que discernir si es el valor booleano 'false' o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'f':	compara = cmp(posicion, "false");
						if (compara){
							return new Token("false",CategoriaLexica.TKFALSE);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
						
			/*
			 * Si detectamos 'i' hay que discernir si es el identificador de tipo 'int' o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'i':	compara = cmp(posicion, "integer");
						if (compara){
							return new Token("integer",CategoriaLexica.TKINT);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'b' hay que discernir si es el identificador de tipo 'bool', 'begin' o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'b':	compara = cmp(posicion, "boolean");
						if (compara){
							return new Token("boolean",CategoriaLexica.TKBOOL);
						}
						else {
							compara = cmp(posicion,"begin");
							if (compara){
								return new Token("begin",CategoriaLexica.TKBEGIN);
							}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
							}
						}
			/*
			 * Si detectamos 'e' hay que discernir si es el identificador de tipo 'end' o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'e':	compara = cmp(posicion, "end");
						if (compara){
							return new Token("end",CategoriaLexica.TKEND);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'p' hay que discernir si es la palabra reservada program o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'p':	compara = cmp(posicion, "program");
						if (compara){
							return new Token("program",CategoriaLexica.TKPROGRAM);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'v' hay que discernir si es la palabra reservada var o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'v':	compara = cmp(posicion, "var");
						if (compara){
							return new Token("var",CategoriaLexica.TKVAR);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'a' hay que discernir si es la palabra reservada and o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'a':	compara = cmp(posicion, "and");
						if (compara){
							return new Token("and",CategoriaLexica.TKAND);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'o' hay que discernir si es la palabra reservada or o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'o':	compara = cmp(posicion, "or");
						if (compara){
							return new Token("or",CategoriaLexica.TKOR);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'n' hay que discernir si es la palabra reservada and o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'n':	compara = cmp(posicion, "not");
						if (compara){
							return new Token("not",CategoriaLexica.TKNOT);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}			
			/*
			 * Si detectamos 'a' hay que discernir si es la palabra reservada and o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'r':	compara = cmp(posicion, "read");
						if (compara){
							return new Token("read",CategoriaLexica.TKREAD);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
						
			case 'w':	compara = cmp(posicion, "write");
						if (compara){
							return new Token("write",CategoriaLexica.TKWRITE);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'a' hay que discernir si es la palabra reservada and o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'd':	compara = cmp(posicion, "div");
						if (compara){
							return new Token("div",CategoriaLexica.TKDIV);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * Si detectamos 'a' hay que discernir si es la palabra reservada and o es un identificador.
			 * Para leer identificadores, usamos leeIdentificador().  
			 */
			case 'm':	compara = cmp(posicion, "mod");
						if (compara){
							return new Token("mod",CategoriaLexica.TKMOD);
						}
						else{
							String aux = leeIdentificador(posicion);
							return new Token (aux,CategoriaLexica.TKIDEN);
						}
			/*
			 * En el caso por defecto detectamos las secuencias de digitos y los indentificadores.
			 * Si es un digito, llamamos a leerNumero.
			 * Si es una letra, llamamos a leeIdentificador. Tiene que ser una letra porque los identificadores
			 * comienzan por letra y luego pueden llevar digitos o letras o '_'.
			 * Sino, hemos detectado un error y lanzamos una excepcion. 
			 */
			default:		if ((a>='1') && (a<='9')){
								String aux = leeNumero(posicion);
								return new Token (aux,CategoriaLexica.TKNUM);
						}
						else{
							if ((a>='A') && (a<='z')){
								String aux = leeIdentificador(posicion);
								return new Token (aux,CategoriaLexica.TKIDEN);
							}
							else{
								throw new Exception("ERROR en linea "+linea+" y posicion "+posicion+": error de sintaxis");
							}
						}			
			}
		}	
		
		/*
		 * Si se sale del while es que read detecto un error y lanzamos una excepcion de entrada/salida. 
		 */
		if (error != -1){
			throw new Exception("ERROR en linea "+linea+": Error de entrada/salida");
		}	
		else {
			return new Token ("eof",CategoriaLexica.TKFF);
		}
	}
	
	
	private boolean cmp(int pos, String string) throws IOException, Exception {
		int a;
		String s = new String();
		boolean match;
		fuente.seek(pos - 1);
		a = fuente.read();
		for (int i = 0; (i < string.length() && (a != -1)); i++) {
			s = s.concat(Character.toString((char)a));
			a = fuente.read();
		}
		match = string.equalsIgnoreCase(s);
		if (match) {
			pos += string.length() - 1;
		}
		fuente.seek(pos);
		setPosicion(pos);
		return match;
	}
	
	private String leeIdentificador(int pos) throws IOException, Exception {
		int a;
		String s = new String();
		fuente.seek(--pos);
		a = fuente.read();
		pos++;
		while((a != -1) && (Character.isLetterOrDigit((char)a))) {
			s = s.concat(Character.toString((char)a));
			a = fuente.read();
			pos++;
		}
		fuente.seek(--pos);
		setPosicion(pos);
		return s;
	}

	private String leeNumero(int posicion) throws Exception, IOException {
		int a;
		String s = new String();
		fuente.seek(--posicion);
		a = fuente.read();
		posicion++;
		while((a != -1) && (Character.isDigit((char)a))) {
			s = s.concat(Character.toString((char)a));
			a = fuente.read();
			posicion++;
		}
		fuente.seek(--posicion);
		setPosicion(posicion);
		if ((s.charAt(0) == '0') && (s.length() > 1))
			throw new Exception("ERROR en linea "+linea+": No existe ese numero");
		return s;
	}
	
/*private String leeComentario (int posicion)throws Exception, IOException {
	int a;
	String s = new String();
	fuente.seek(--posicion);
	a = fuente.read();
	posicion++;
	while(a != '\n') {
		s = s.concat(Character.toString((char)a));
		a = fuente.read();
		posicion++;
	}
	fuente.seek(--posicion);
	setPosicion(posicion);
	return s;
}*/

	/**
	 * El metodo getNextToken devuelve el siguiente Token para poder realizar el preanalisis. 
	 * No avanza el cursor, sino que s?lo "mira" cual es el siguiente Token que leera "lexer()"
	 * @see procesador.Lexico#getToken()
	 * @return Token actual que ha reconocido.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que propagamos de la funcion getToken().
	 */
	public Token getNextToken() throws IOException, Exception{
		int aux = posicion;
		int auxLinea = linea;
		Token tk = getToken();
		fuente.seek(aux);
		posicion = aux;
		linea = auxLinea;
		return tk;	
		
	}
	
	/**
	 * El metodo lexer actualiza el lxico con el nuevo Token del preanalisis. El nuevo Token se obtiene llamando a getNextToken.
	 * 
	 * @return Token actual que ha reconocido.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que propagamos de la funcion getNextToken().
	 */
	public Token lexer() throws IOException, Exception{
			lookahead = getToken();
			return lookahead;
	}
	
	/**
	 * El metodo reconoce indica si una categora lexica dada es igual o no a la categoria lexica del Token
	 * @param tk Entero que indica una categora lexica. 
	 * @return Booleano que nos dir si son iguales las categoras lexicas.
	 * @throws Exception 
	 * @throws IOException 
	 * 
	 */
	public boolean reconoce(CategoriaLexica tk) throws IOException, Exception{
		boolean aux = true;
		if (tk == getNextToken().getCategoriaLexica()){
			aux = true;
		}	
		else{ 
			aux = false;	
		}
		return aux;
	}
}
