package procesador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;/*
import java.lang.Character;
import java.util.*;*/



/**
 * La clase <B>Lexico</B> analiza el fichero de entrada para reconocer tokens. Lanza una excepcin en caso de que esto no sea posible. Puede suceder
 * que el token sea erroneo o que ocurra algun problema con el fichero de lectura.
 * <P>La clase Lexico cuenta con los siguientes atributos:
 * <UL><LI><CODE>linea:</CODE> Entero que controla la linea del codigo donde se detecta el error.</LI>
 * <LI><CODE>columna:</CODE> Entero que controla la columna del codigo donde se detecta el error.</LI>
 * <LI><CODE>lookahead:</CODE> Token que almacena los caracteres de preanálisis.</LI>
 * <LI><CODE>fuente:</CODE> RandomAccessFile que se utiliza para leer del fichero que contine que contine el cdigo a analizar.</LI>
 * <LI><CODE>posicion:</CODE> Entero que marca la posicin en la que se est leyendo dentro de una lnea.</LI>
 * <LI><CODE>buff:</CODE> String utilizado para leer de fichero.</LI>
 * <LI><CODE>estado:</CODE> Entero que nos dice en que estado del diagrama nos encontramos.</LI>
 * <LI><CODE>palReserv:</CODE> De tipo PalabrasReservadas nos dice si estamos reconociendo una palabra reservada del lenguaje .</LI>
 * </UL></P>
 * 
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
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
	/*
	 * Entero que controla la linea del codigo donde se detecta el error.
	 */
	int linea;
	int columna;
	Token lookahead;
	String buff;
	RandomAccessFile fuente;
	//Character a;
	String a;
	int posicion;
	int estado;
	PalabrasReservadas palReserv = new PalabrasReservadas();
	
	/**
	 * El constructor de la clase Lexico que slo tiene el buffer de lectura del fichero como parmetro de entrada.
	 * @param f Buffer de entrada del que se leen caracteres. Que es del tipo RandomAccessFile.
	 * 
	 */
	public Lexico(File f) {
		linea = 1;
		columna = 0;
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
	 * Accesor para el atributo de la clase, columna. 
	 * @return Entero que controla la columna del cdigo donde se detecta el error.
	 */
	public int getColumna() {
		return columna;
	}
	
	/**
	 * Mutador para el atributo de la clase columna. 
	 * @param columna Entero que controla la columna del cdigo donde se detecta el error.
	 */
	public void setColumna(int columna) {
		this.columna = columna;
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
		
		
		int error = 0;
		//boolean compara;
		Token tk = new Token("error",CategoriaLexica.TKLAMBDA);
		estado = 0;
		boolean acabado = false;
		if (posicion !=0){
			fuente.seek(posicion-1);
			posicion--;
			columna--;
		}
		
		while (!acabado){
			error = fuente.read();
			posicion ++;
			columna ++;
			if (error!=-1){
				a = "" + ((char)error);
				a = a.toLowerCase();
			}
			switch (estado) {
				case 0:
					buff = "";
					if (a.matches("\\n")) {
						linea++;
						columna = 0;
						transita(0);
					}
					else if	((a.matches("\\t")) ||
							(a.matches("\\s")) ||
							(a.matches("\\r")))
						transita(0);
					else if (a.equals("="))
						transita(1);
					else if (a.equals(":"))
						transita(2);
					else if (a.equals(">"))
						transita(3);
					else if (a.equals("<"))
						transita(4);
					else if ((a.equals(".")) || 
							(a.equals(",")) || 
							(a.equals(";")) ||
							(a.equals("(")) ||
							(a.equals(")")) ||
							(a.equals("{")) ||
							(a.equals("}")) ||
							(a.equals("+")) || 
							(a.equals("-")) ||
							(a.equals("[")) ||
							(a.equals("]")) ||
							(a.equals("*")))
						transita(7);
					else if (a.matches("[1-9]*"))
						transita(9);
					else if (a.matches("0"))
						transita(11);
					else if (a.matches("[a-z]*"))
						transita(12);
					else
						throw new Exception("ERROR en linea "+linea+" y columna "+columna+": error de sintaxis");
					break;
				case 1:
					return new Token (buff.toString(),CategoriaLexica.TKIG);
				case 2:
					if (a.equals("="))
						transita(5);
					else
						return new Token (buff.toString(),CategoriaLexica.TKDOSPUNTOS);
					break;
				case 3:
					if (a.equals("="))
						transita(13);
					else
						return new Token (buff.toString(),CategoriaLexica.TKMAY);
					break;
				case 4:
					if (a.equals("="))
						transita(14);
					else if (a.equals(">"))
						transita(6);
					else
						return new Token (buff.toString(),CategoriaLexica.TKMEN);
					break;
				case 5:
					return new Token (buff.toString(),CategoriaLexica.TKASIGN);
				case 6:
					return new Token (buff.toString(),CategoriaLexica.TKDIF);
				case 7:
					if (buff.equals(".")) {
						if (a.equals(".")){ 
							transita (15);
							break;
						} 
						else return new Token(buff.toString(),CategoriaLexica.TKPUNTO);
					}
					else if (buff.equals(","))
						return new Token(buff.toString(),CategoriaLexica.TKCOMA);
					else if (buff.equals(";"))
						return new Token(buff.toString(),CategoriaLexica.TKPYCOMA);
					else if (buff.equals("("))
						return new Token(buff.toString(),CategoriaLexica.TKPAP);
					else if (buff.equals(")"))
						return new Token(buff.toString(),CategoriaLexica.TKPCI);
					else if (buff.equals("{"))
						return new Token(buff.toString(),CategoriaLexica.TKLLAP);
					else if (buff.equals("}"))
						return new Token(buff.toString(),CategoriaLexica.TKLLCI);
					else if (buff.equals("+"))
						return new Token (buff.toString(),CategoriaLexica.TKSUMA);
					else if (buff.equals("-"))
						return new Token (buff.toString(),CategoriaLexica.TKRESTA); 
					else if (buff.equals("["))
						return new Token (buff.toString(),CategoriaLexica.TKCORCHAP); 
					else if (buff.equals("]"))
						return new Token (buff.toString(),CategoriaLexica.TKCORCHCI); 
					else // *
						return new Token(buff.toString(),CategoriaLexica.TKMULT);
				case 9:
					if (a.matches("[0-9]*"))
						transita(10);
					else{
						String aux = buff;//leeNumero(posicion);
						return new Token (aux,CategoriaLexica.TKNUM);
					}
					break;
				case 10:
					if (a.matches("[0-9]*"))
						transita(10);
					else{
						String aux = buff;//leeNumero(posicion);
						return new Token (aux,CategoriaLexica.TKNUM);
					}
					break;
				case 11:
					String aux = buff;//leeNumero(posicion);
					return new Token (aux,CategoriaLexica.TKNUM);
				case 12:
					if (a.matches("[0-9a-z]*"))
						transita(12);
					else{				
						if (!palReserv.esPalabraReservada(buff))
							return new Token (buff,CategoriaLexica.TKIDEN);
						else{
							tk = palReserv.reconoceCategoria(buff);
							return tk;
						}
					}
					break;
				case 13:
					return new Token(buff.toString(),CategoriaLexica.TKMAYIG);
				case 14:
					return new Token(buff.toString(),CategoriaLexica.TKMENIG);
				case 15:
					return new Token(buff.toString(),CategoriaLexica.TKPUNTOPUNTO);
			}
			if (error == -1) acabado = true;
		}
		/*
		 * Si se sale del while es que read detecto un error y lanzamos una excepcion de entrada/salida. 
		 */
		return new Token(buff.toString(),CategoriaLexica.TKFF);
	
	}
					
	/**
	 * Metodo para cambiar de estado al que nos indica el parametro que recibimos.
	 * 
	 * @param s Entero que indica cual es el estado al que vamos a movernos.
	 */
	private void transita(int s){
		buff = buff + a;
		estado = s;
	}
		

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
		int auxCol = columna;
		int auxLinea = linea;
		Token tk = getToken();
		fuente.seek(aux);
		posicion = aux;
		linea = auxLinea;
		columna = auxCol;
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
	
	/**
	 * Main utilizado para las pruebas de funcionamiento de esta clase.
	 * 
	 * @param args
	 */
	public static void main(String args[]){
		Lexico l = new Lexico(new File("Ejemplo.txt"));
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
