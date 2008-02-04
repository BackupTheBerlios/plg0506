package procesador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;
import java.lang.Character;



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

public class LexicoB {
	
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
	String buff;
	RandomAccessFile fuente;
	Character a;
	int posicion;
	int estado;
	PalabrasReservadas palReserv = new PalabrasReservadas();
	
	/**
	 * El constructor de la clase Lexico que slo tiene el buffer de lectura del fichero como parmetro de entrada.
	 * @param f Buffer de entrada del que se leen caracteres. Que es del tipo RandomAccessFile.
	 * 
	 */
	public LexicoB(File f) {
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
	private Token getToken() throws IOException, Exception{
		
		
		int error;
		//boolean compara;
		Token tk = new Token("error",CategoriaLexica.TKLAMBDA);
		
		while ((error = fuente.read())!=-1){
			
			a = new Character((char)error);
			posicion ++;
			a = Character.toLowerCase(a);
			estado = 0;
			switch (estado) {
				case 0:
					buff = "";
					if ((a == '\n') || (a == '\t') || (a == ' '))
						transita(0);
					else if (a=='=')
						transita(1);
					else if (a==':')
						transita(2);
					else if (a=='>')
						transita(3);
					else if (a=='<')
						transita(4);
					else if ((a == '.') || 
							(a == ',') || 
							(a == ';') ||
							(a == '(') ||
							(a == ')') ||
							(a == '{') ||
							(a == '}') ||
							(a == '*'))
						transita(7);
					else if ((a == '+') || 
							(a == '-'))
						transita(8);
					else if (a >= '1' && a <= '9')
						transita(9);
					else if (a == '0')
						transita(11);
					else if (a >= 'a' && a <= 'z')
						transita(12);
					else
						throw new Exception("ERROR en linea "+linea+" y posicion "+posicion+": error de sintaxis");
					break;
				case 1:
					return new Token (a.toString(),CategoriaLexica.TKIG);
				case 2:
					if (a == '=')
						transita(5);
					else
						return new Token (a.toString(),CategoriaLexica.TKDOSPUNTOS);
					break;
				case 3:
					if (a == '=')
						transita(13);
					else
						return new Token (a.toString(),CategoriaLexica.TKMAY);
					break;
				case 4:
					if (a == '=')
						transita(14);
					else if (a == '>')
						transita(6);
					else
						return new Token (a.toString(),CategoriaLexica.TKMEN);
					break;
				case 5:
					return new Token (a.toString(),CategoriaLexica.TKASIGN);
				case 6:
					return new Token (a.toString(),CategoriaLexica.TKDIF);
				case 7:
					if (a == '.') 
							return new Token(a.toString(),CategoriaLexica.TKPUNTO);
					else if (a == ',')
						return new Token(a.toString(),CategoriaLexica.TKCOMA);
					else if (a == ';')
						return new Token(a.toString(),CategoriaLexica.TKPYCOMA);
					else if (a == '(')
						return new Token(a.toString(),CategoriaLexica.TKPAP);
					else if (a == ')')
						return new Token(a.toString(),CategoriaLexica.TKPCI);
					else if (a == '{')
						return new Token(a.toString(),CategoriaLexica.TKLLAP);
					else if (a == '}')
						return new Token(a.toString(),CategoriaLexica.TKLLCI);
					else // *
						return new Token(a.toString(),CategoriaLexica.TKMULT);
				case 8:
					if (a >= '0' && a <= '9')
						transita(10);
					else if (a == '+')
						return new Token (a.toString(),CategoriaLexica.TKSUMA);
					else if (a == '-')
						return new Token (a.toString(),CategoriaLexica.TKRESTA);
					break;
				case 9:
					if (a >= '0' && a <= '9')
						transita(10);
					else{
						String aux = leeNumero(posicion);
						return new Token (aux,CategoriaLexica.TKNUM);
					}
					break;
				case 10:
					if (a >= '0' && a <= '9')
						transita(10);
					else{
						String aux = leeNumero(posicion);
						return new Token (aux,CategoriaLexica.TKNUM);
					}
					break;
				case 11:
					String aux = leeNumero(posicion);
					return new Token (aux,CategoriaLexica.TKNUM);
				case 12:
					if ((a >= '0' && a <= '9') || (a >= 'a' && a <= 'z'))
						transita(12);
					else{
						String aux2 = leeIdentificador(posicion);
						if (!palReserv.esPalabraReservada(aux2))
							return new Token (aux2,CategoriaLexica.TKIDEN);
						else{
							tk = palReserv.reconoceCategoria(aux2);
							return tk;
						}
					}
					break;
				case 13:
					return new Token(a.toString(),CategoriaLexica.TKMAYIG);
				case 14:
					return new Token(a.toString(),CategoriaLexica.TKMENIG);
			}
		}
		/*
		 * Si se sale del while es que read detecto un error y lanzamos una excepcion de entrada/salida. 
		 */
		if (error != -1)
			throw new Exception("ERROR en linea "+linea+": Error de entrada/salida");
		return tk;
	}
					
	
	private void transita(int s){
		System.out.println(a.toString());
		buff.concat(a.toString((char)a));
		estado = s+1;
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
	
	public static void main(String args[]){
		LexicoB l = new LexicoB(new File("Ejemplo.txt"));
		try {
		Token tk = l.lexer();
		while (tk.getCategoriaLexica()!=CategoriaLexica.TKFF){
			System.out.println(tk.getLexema() + " -> " + tk.getCategoriaLexica());
			tk = l.lexer();
		}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
