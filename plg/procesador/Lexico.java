package procesador;
import java.io.IOException;
import java.io.RandomAccessFile;


/**
 * La clase <B>Lexico</B> analiza el fichero de entrada para reconocer tokens. Lanza una excepción en caso de que esto no sea posible. Puede suceder
 * que el token sea erroneo o que ocurra algun problema con el fichero de lectura.
 * <P>La clase Lexico cuenta con los siguientes atributos:
 * <UL><LI><CODE>linea:</CODE> Entero que controla la línea del código donde se detecta el error.</LI>
 * <LI><CODE>lookahead:</CODE> Token que almacena los caracteres de preanálisis.</LI>
 * <LI><CODE>fuente:</CODE> RandomAccessFile que se utiliza para leer del fichero que contine que contine el código a analizar.</LI>
 * <LI><CODE>posicion:</CODE> Entero que marca la posición en la que se está leyendo dentro de una línea.</LI>
 * </UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
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
	 * El constructor de la clase Lexico que sólo tiene el buffer de lectura del fichero como parámetro de entrada.
	 * @param f Buffer de entrada del que se leen caracteres. Que es del tipo RandomAccessFile.
	 * 
	 */
	public Lexico(RandomAccessFile f) {
		linea = 0;
		lookahead = new Token();
		fuente = f;
		posicion = 0;
	}
	
	/**
	 * Accesor para el atributo de la clase linea. 
	 * @return Entero que controla la línea del código donde se detecta el error.
	 */
	public int getLinea() {
		return linea;
	}
	/**
	 * Mutador para el atributo de la clase linea. 
	 * @param linea Entero que controla la línea del código donde se detecta el error.
	 */
	public void setLinea(int linea) {
		this.linea = linea;
	}
	/**
	 * Accesor para el atributo de la clase posicion. 
	 * @return Entero que marca la posición en la que se está leyendo dentro de una línea.
	 */
	public int getPosicion() {
		return posicion;
	}
	/**
	 * Mutador para el atributo de la clase posicion. 
	 * @param posicion Entero que marcara la posición en la que se está leyendo dentro de una línea.
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	/**
	 * Accesor para el atributo de la clase lookahead. 
	 * @return Token actual que almacena los caracteres de preanálisis.
	 */
	public Token getLookahead() {
		return lookahead;
	}
	/**
	 * Mutador para el atributo de la clase lookahead. 
	 * @param lookahead Token para modificar el token actual que almacena los caracteres de preanálisis.
	 */
	public void setLookahead(Token lookahead) {
		this.lookahead = lookahead;
	}
	/**
	 * Accesor para el atributo de la clase fuente. 
	 * @return RandomAccessFile que se utiliza para leer del fichero que contine que contine el código a analizar
	 */
	public RandomAccessFile getFuente() {
		return fuente;
	}
	/**
	 * Mutador para el atributo de la clase fuente. 
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contine que contine el código a analizar
	 */
	public void setFuente(RandomAccessFile fuente) {
		this.fuente = fuente;
	}

	/**
	 * El método getToken lee caracteres del flujo hasta que identifica un token y lo devuelve, o detecta un error y genera una excepción.
	 * Utiliza las funciones que ofrece RandomAccessFile para manejar el flujo que lee de fichero.
	 * 
	 * @see java.io.RandomAccessFile#read() 
	 * @see java.io.RandomAccessFile#seek(long) 
	 * @return Token que hemos identificado.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 */
	public Token getToken () throws IOException, Exception{
		
		char a;
		int error;
		/*
		 * La funcion read() saca un caracter del flujo de entrada
		 */
		while ((error = fuente.read())!=-1){
			a = (char) error;
			posicion ++;
			switch (a){
			
			/*
			 * En primer lugar identificamos todos los caracteres especiales.
			 */
			case '\n':	linea ++;
						break;
			case '\t':	break;
			case ' ':	break;
			case '0':	return new Token("0",Tipos.TKNUM);
			case '(':	return new Token("(",Tipos.TKPAP);
			case ')':	return new Token(")",Tipos.TKPCI);
			case '*':	return new Token("*",Tipos.TKMULT);
			case '/':	return new Token("/",Tipos.TKDIV);
			case ';':	return new Token(";",Tipos.TKPYCOMA);
			case '=':	return new Token("=",Tipos.TKIG);
			case '#':	return new Token("#",Tipos.TKCUA);
			
			case '!':	a = (char)fuente.read();
						posicion ++;
						if (a == '='){
							return new Token("!=",Tipos.TKDIF);
						}	
						else{
							throw new Exception("ERROR en linea "+linea+": ':' debe ir seguido de '='");
						}
			
			/*
			 * Si detectamos ':' hay que discernir si es el operador de asignacion o un error.
			 * Si es un error lanzamos una excepcion. 
			 */
			case ':':	a = (char)fuente.read();
						posicion ++;
						if (a == '='){
							return new Token(":=",Tipos.TKASIGN);
						}	
						else{
							throw new Exception("ERROR en linea "+linea+": ':' debe ir seguido de '='");
						}
			
			/*
			 * Si detectamos '<' hay que discernir si es el operador 'menor que' o 'menor o igual que'
			 * 
			 * La funcion seek(int), mueve el puntero de lectura a donde nos marca el entero. Asi podemos
			 * modificar la posicion del flujo.
			 */
			case '<':	a = (char)fuente.read();
						posicion ++;
						if (a == '='){
							return new Token("<=",Tipos.TKMENIG);
						}	
						else{
							posicion --;
							fuente.seek(posicion);
							return new Token ("<",Tipos.TKMEN);
						}
			
			/*
			 * Si detectamos '>' hay que discernir si es el operador 'mayor que' o 'mayor o igual que' 
			 */
			case '>':	a = (char)fuente.read();
						posicion ++;
						if (a == '='){
							return new Token(">=",Tipos.TKMAYIG);
						}	
						else{
							posicion --;
							fuente.seek(posicion);
							return new Token (">",Tipos.TKMAYIG);
						}
						
			/*
			 * Si detectamos '+' hay que discernir si es el operador '+' o es un numero positivo.
			 * Para leer secuencias de digitos, usamos leerNumero.  
			 */
			case '+':	a = (char)fuente.read();
						posicion ++;
						if ((a >= '1') && (a<='9')){
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerNumero("+");
							return new Token (aux,Tipos.TKNUM);
						}	
						else{
							posicion --;
							fuente.seek(posicion);
							return new Token ("+",Tipos.TKSUMA);
						}
						
			/*
			 * Si detectamos '-' hay que discernir si es el operador '-' o es un numero negativo.
			 * Para leer secuencias de digitos, usamos leerNumero.  
			 */
			case '-':	a = (char)fuente.read();
						posicion ++;
						if ((a >= '1') && (a<='9')){
							String aux;
							aux = leerNumero("-");
							return new Token (aux,Tipos.TKNUM);
						}	
						else{
							posicion --;
							fuente.seek(posicion);
							return new Token ("-",Tipos.TKRESTA);
						}
						
			/*
			 * Si detectamos 'a' hay que discernir si es el operador 'and' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'a':	a = (char)fuente.read();
						posicion ++;
						if (a =='n'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='d'){
								a = (char)fuente.read();
								posicion ++;
								if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("and");
									return new Token (aux,Tipos.TKIDEN);
								}
								else{
									posicion --;
									fuente.seek(posicion);
									return new Token ("and",Tipos.TKAND);
								}
							}	
							else{
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("an");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{	
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("a");
							return new Token (aux,Tipos.TKIDEN);
						}
			
			/*
			 * Si detectamos 'n' hay que discernir si es el operador 'not' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'n':	a = (char)fuente.read();
						posicion ++;
						if (a =='o'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='t'){
								a = (char)fuente.read();
								posicion ++;
								if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("not");
									return new Token (aux,Tipos.TKIDEN);
								}
								else{
									posicion --;
									fuente.seek(posicion);
									return new Token ("not",Tipos.TKNOT);
								}
							}	
							else{
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("no");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{							
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("n");
							return new Token (aux,Tipos.TKIDEN);
						}
			
			/*
			 * Si detectamos 'o' hay que discernir si es el operador 'or' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'o':	a = (char)fuente.read();
						posicion ++;
						if (a =='r'){
							a = (char)fuente.read();
							posicion ++;
							if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("or");
								return new Token (aux,Tipos.TKIDEN);
							}
							else{
								posicion --;
								fuente.seek(posicion);
								return new Token ("or",Tipos.TKOR);
							}
						}	
						else{
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("o");
							return new Token (aux,Tipos.TKIDEN);
						}
						
			/*
			 * Si detectamos 't' hay que discernir si es el valor boolenao 'true' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 't':	a = (char)fuente.read();
						posicion ++;
						if (a =='r'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='u'){
								a = (char)fuente.read();
								posicion ++;
								if (a =='e'){
									a = (char)fuente.read();
									posicion ++;
									if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
										posicion --;
										fuente.seek(posicion);
										String aux;
										aux = leerCaracter("true");
										return new Token (aux,Tipos.TKIDEN);
									}
									else{
										posicion --;
										fuente.seek(posicion);
										return new Token ("true",Tipos.TKTRUE);
									}
								}	
								else{
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("tru");
									return new Token (aux,Tipos.TKIDEN);
								}
							}	
							else{
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("tr");
								return new Token (aux,Tipos.TKIDEN);
							}
						}
						else{							
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("t");
							return new Token (aux,Tipos.TKIDEN);
						}
			
			/*
			 * Si detectamos 'f' hay que discernir si es el valor booleano 'false' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'f':	a = (char)fuente.read();
						posicion ++;
						if (a =='a'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='l'){
								a = (char)fuente.read();
								posicion ++;
								if (a =='s'){
									a = (char)fuente.read();
									posicion ++;
									if (a =='e'){
										a = (char)fuente.read();
										posicion ++;
										if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
											posicion --;
											fuente.seek(posicion);
											String aux;
											aux = leerCaracter("false");
											return new Token (aux,Tipos.TKIDEN);
										}
										else{
											posicion --;
											fuente.seek(posicion);
											return new Token ("false",Tipos.TKFALSE);
										}
									}	
									else{
										posicion --;
										fuente.seek(posicion);
										String aux;
										aux = leerCaracter("fals");
										return new Token (aux,Tipos.TKIDEN);
										}
								}	
								else{
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("fal");
									return new Token (aux,Tipos.TKIDEN);
								}
							}	
							else{
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("fa");
								return new Token (aux,Tipos.TKIDEN);
							}
						}
						else{							
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("f");
							return new Token (aux,Tipos.TKIDEN);
						}
						
			/*
			 * Si detectamos 'i' hay que discernir si es el identificador de tipo 'int' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'i':	a = (char)fuente.read();
						posicion ++;
						if (a =='n'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='t'){
								a = (char)fuente.read();
								posicion ++;
								if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("int");
									return new Token (aux,Tipos.TKIDEN);
								}
								else{
									posicion --;
									fuente.seek(posicion);
									return new Token ("int",Tipos.TKINT);
								}
							}	
							else{
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("in");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{							
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("i");
							return new Token (aux,Tipos.TKIDEN);
						}

			/*
			 * Si detectamos 'b' hay que discernir si es el identificador de tipo 'bool' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'b':	a = (char)fuente.read();
						posicion ++;
						if (a =='o'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='o'){
								a = (char)fuente.read();
								posicion ++;
								if (a =='l'){
									a = (char)fuente.read();
									posicion ++;
									if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
										posicion --;
										fuente.seek(posicion);
										String aux;
										aux = leerCaracter("bool");
										return new Token (aux,Tipos.TKIDEN);
									}
									else{
										posicion --;
										fuente.seek(posicion);
										return new Token ("bool",Tipos.TKBOOL);
									}
								}
								else{
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("boo");
									return new Token (aux,Tipos.TKIDEN);
								}
							}
							else{
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("bo");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{							
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("b");
							return new Token (aux,Tipos.TKIDEN);
						}
			/*
			 * En el caso por defecto detectamos las secuencias de digitos y los indentificadores.
			 * Si es un digito, llamamos a leerNumero.
			 * Si es una letra, llamamos a leerCaracter. Tiene que ser una letra porque los identificadores
			 * comienzan por letra y luego pueden llevar digitos o letras o '_'.
			 * Sino, hemos detectado un error y lanzamos una excepcion. 
			 */
			default:		if ((a>='1') && (a<='9')){
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerNumero("");
							return new Token (aux,Tipos.TKNUM);
						}
						else{
							if ((a>='A') && (a<='z')){
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("");
								return new Token (aux,Tipos.TKIDEN);
							}
							else{
								throw new Exception("ERROR en linea "+linea+": No existe ese identificador");
							}
						}			
			}
		}	
		
		/*
		 * Si se sale del while es que read detecto un error y lanzamos una excepcion de entrada/salida. 
		 */
		if (error != -1)
			throw new Exception("ERROR en linea "+linea+": Error de entrada/salida");
		else {
			return new Token ("eof",Tipos.TKFF);
		}
	}
	
	/**
	 * El método leerNumero se encarga de leer un entero completo del fichero fuente. 
	 * Como la definición de nuestro lenguaje no permite la representación del 0 como '+0' ni '-0', si detectamos un 0
	 * lanzamos una excepción. Sino, vamos leyendo mientras sean dígitos hasta terminar de leer el entero.
	 * 
	 * @param aux String que almacena la parte leída del entero.
	 * @return aux String que almacena el entero reconocido.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 */
	public String leerNumero(String aux) throws Exception,IOException{
		char a;
		int i = 0;
		a = (char)fuente.read();
		posicion ++;
		if (a == '0'){
			throw new Exception("ERROR en linea "+linea+": No existe ese numero");
		}
		else{
			aux = aux.concat(new Character(a).toString());
			while (((a = (char)fuente.read()) != -1) && (i <= 8)){
				posicion ++;
				if ((a>='0') && (a<'9')){
					aux = aux.concat(new Character(a).toString());			
				}
				else{
					posicion --;
					fuente.seek(posicion);
					return aux;
				}
				i ++;
			}
			if (i<=8){
				posicion --;
				fuente.seek(posicion);
			}
			return aux;
		}	
	}
	
	/**
	 * El método leerCaracter es llamado cuando al menos hemos leído ya una letra. Así, sólo tenemos que controlar que se lean letras, digitos o '_'.
	 * Es decir, leemos mientras sean caracteres válidos según nuestra especificación y hasta terminar de leer el identificador.
	 * 
	 * @param aux String que almacena la parte ya leída del identificador.
	 * @return String que almacena el identificador ya reconocido.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 */
	
	public String leerCaracter(String aux) throws Exception, IOException{
		char a;
		while ((a = (char)fuente.read()) != -1){
			posicion ++;
			if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
				aux = aux.concat(new Character(a).toString());			
			}
			else{
				posicion --;
				fuente.seek(posicion);
				return aux;
			}
		}	
		return aux;	
	}
	
	/**
	 * El método getNextToken devuelve el siguiente Token para poder realizar el preanálisis. Llama a getToken(). También acutualiza el lookahead de Lexico.
	 * 
	 * @see procesador.Lexico#getToken()
	 * @return Token actual que ha reconocido.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que propagamos de la funcion getToken().
	 */
	public Token getNextToken() throws IOException, Exception{
		lookahead = getToken();
		return lookahead;	
	}
	
	/**
	 * El método lexer actualiza el léxico con el nuevo Token del preanálisis. El nuevo Token se obtiene llamando a getNextToken.
	 * 
	 * @return Token actual que ha reconocido.
	 * @exception IOException que propagamos de las funciones de los RandomAccessFile de JAVA.
	 * @exception Exception que propagamos de la funcion getNextToken().
	 */
	public Token lexer() throws IOException, Exception{
			lookahead = getNextToken();
			return lookahead;
	}
	
	/**
	 * El método reconoce indica si una categoría léixca dada es igual o no a la categoría léxica del Token
	 * @param tk Entero que indica una categoría léxica. 
	 * @return Booleano que nos dirá si son iguales las categorías léxicas.
	 * 
	 */
	public boolean reconoce(int tk){
		boolean aux = true;
		if (tk == lookahead.getCategoriaLexica()){
			aux = true;
		}	
		else{ 
			aux = false;	
		}
		return aux;
	}
}