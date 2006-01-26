package procesador;
import java.io.IOException;
import java.io.RandomAccessFile;

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
	
	/*
	 * Parametros de entrada: Buffer de entrada del que lee caracteres.
	 * 
	 * Constructor.
	 */
	public Lexico(RandomAccessFile f) {
		linea = 0;
		lookahead = new Token();
		fuente = f;
		posicion = 0;
	}
	
	/*
	 * Accesores y mutadores para los atributos del analizador lexico.
	 */
	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}


	public Token getLookahead() {
		return lookahead;
	}

	public void setLookahead(Token lookahead) {
		this.lookahead = lookahead;
	}
	
	public RandomAccessFile getFuente() {
		return fuente;
	}

	public void setFuente(RandomAccessFile fuente) {
		this.fuente = fuente;
	}

	/*
	 * Parametros de entrada: 
	 * Parametros de salida: Token identificado.
	 * Execpciones: IOException, que propagamos de las funciones de los RandomAccessFile de java y
	 * 				Exception, que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 *  
	 *  Va leyendo caracteres del flujo hasta que identifica un token y lo devuleve,
	 *  o detecta un error y genera una excepcion.
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
						if ((a >= '1') || (a<='9')){
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
						if ((a >= '1') || (a<='9')){
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
								return new Token ("and",Tipos.TKAND);
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
								return new Token ("not",Tipos.TKNOT);
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
							return new Token ("or",Tipos.TKOR);
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
									return new Token ("true",Tipos.TKTRUE);
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
										return new Token ("false",Tipos.TKFALSE);
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
								return new Token ("int",Tipos.TKINT);
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
									return new Token ("bool",Tipos.TKBOOL);
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
	
	/*
	 * Parametros de entrada: String donde lleva almacenado lo que ya ha leido.
	 * Parametros de salida: String con el numero que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los RandomAccessFile de java y
	 * 				Exception, que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 * Si leemos un 0, detectamos error porque no se puede tener '+0' ni '-0'.
	 * Sino, vamos leyendo mientras sean digitos hasta terminar de leer el numero.
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
	
	/*
	 * Parametros de entrada: String donde lleva almacenado lo que ya ha leido.
	 * Parametros de salida: String con todos los caracteres del identificador que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los RandomAccessFile de java y
	 * 				Exception, que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 * Cuando llamamos a este metedo ya hemos leido al menos una letra asi que solo tenemos que controlar que se
	 * lean letras, digitos o '_'.
	 * Vamos leyendo mientras sean caracteres validos hasta terminar de leer el identificador.
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
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida: Token que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los RandomAccessFile de java y
	 * 				Exception, que propagamos de la funcion getToken().
	 * 
	 *  Devuelve el siguiente Token para que podamos realizar el preanalisis, actualizando tambien
	 *  lookahead de Lexico con ese Token.
	 */
	public Token getNextToken() throws IOException, Exception{
		lookahead = getToken();
		return lookahead;	
	}
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida: Token que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los RandomAccessFile de java y
	 * 				Exception, que propagamos de la funcion getNextToken().
	 * 
	 * Actualiza el lexico con el nuevo Token del preanalisis. El nuevo Token se obtiene 
	 * llamando a getNextToken.
	 */
	public Token lexer() throws IOException, Exception{
			lookahead = getNextToken();
			return lookahead;
	}
	
	/*
	 * Parametros de entrada: Entero que indica una categoria lexica 
	 * Parametros de salida: booleano que nos dira si son iguales las categorias lexicas.
	 * 
	 *  Dada una categoria lexica se nos indica si es igual o no a la categoria lexica del Token.
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