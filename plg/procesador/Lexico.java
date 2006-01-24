package procesador;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Lexico {
	
	/*
	 * linea sirve para controlar en que linea de codigo detectamos el error.
	 * lookahead sirve para almacenar el caracteres de preanalisis.
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
	 * Execpciones: IOException, que propagamos de las funciones de los BufferedReader de java y
	 * 				Exception, que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 *  
	 *  Va leyendo caracteres del flujo hasta que identifica un token y lo devuleve,
	 *  o detecta un error y genera una excepcion.
	 */
	public Token getToken () throws IOException, Exception{
		char a;
		
		/*
		 * La funcion read() saca un caracter del flujo de entrada
		 */
		while ((a =(char)fuente.read())!=-1){
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
			
			/*
			 * Si detectamos ':' hay que discernir si es el operador de asignacion o un error.
			 * Si es un error lanzamos una excepcion. 
			 */
			case ':':	a = (char)fuente.read();
						posicion ++;
						System.out.println("He reconocido los ':' ");
						if (a == '='){
							System.out.println("Devuelvo token de asignacion");
							return new Token(":=",Tipos.TKASIGN);
						}	
						else{
							System.out.println("No reconozco el '=' ");
							throw new Exception("ERROR en linea "+linea+": ':' debe ir seguido de '='");
						}
			
			/*
			 * Si detectamos '<' hay que discernir si es el operador 'menor que' o 'menor o igual que'
			 * 
			 * La funcion mark(int), marca el flujo de entrada y conserva la marca hasta que se leen int
			 * caracteres, nos permite identificar una posicion del flujo.
			 * 
			 * La funcion skip(int), devuelve int caracteres al flujo de entrada.
			 */
			case '<':	//fuente.mark(1);
						a = (char)fuente.read();
						posicion ++;
						if (a == '='){
							return new Token("<=",Tipos.TKMENIG);
						}	
						else{
							//fuente.skip(1);
							posicion --;
							fuente.seek(posicion);
							return new Token ("<",Tipos.TKMEN);
						}
			
			/*
			 * Si detectamos '>' hay que discernir si es el operador 'mayor que' o 'mayor o igual que' 
			 */
			case '>':	//fuente.mark(1);
						a = (char)fuente.read();
						posicion ++;
						if (a == '='){
							return new Token(">=",Tipos.TKMAYIG);
						}	
						else{
							//fuente.skip(1);
							posicion --;
							fuente.seek(posicion);
							return new Token (">",Tipos.TKMAYIG);
						}
						
			/*
			 * Si detectamos '+' hay que discernir si es el operador '+' o es un numero positivo.
			 * Para leer secuencias de digitos, usamos leerNumero.  
			 */
			case '+':	//fuente.mark(1);
						a = (char)fuente.read();
						posicion ++;
						if ((a >= '1') || (a<='9')){
							//fuente.skip(1);
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerNumero("+");
							return new Token (aux,Tipos.TKNUM);
						}	
						else{
							//fuente.skip(1);
							posicion --;
							fuente.seek(posicion);
							return new Token ("+",Tipos.TKSUMA);
						}
						
			/*
			 * Si detectamos '-' hay que discernir si es el operador '-' o es un numero negativo.
			 * Para leer secuencias de digitos, usamos leerNumero.  
			 */
			case '-':	//fuente.mark(1);
						a = (char)fuente.read();
						posicion ++;
						if ((a >= '1') || (a<='9')){
							String aux;
							aux = leerNumero("-");
							return new Token (aux,Tipos.TKNUM);
						}	
						else{
							//fuente.skip(1);
							posicion --;
							fuente.seek(posicion);
							return new Token ("-",Tipos.TKRESTA);
						}
						
			/*
			 * Si detectamos 'a' hay que discernir si es el operador 'and' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'a':	//fuente.mark(3);
						a = (char)fuente.read();
						posicion ++;
						if (a =='n'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='d'){
								return new Token ("and",Tipos.TKAND);
							}	
							else{
								//fuente.skip(1);	
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("an");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{	
							//fuente.skip(1);	
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
			case 'n':	//fuente.mark(3);
						a = (char)fuente.read();
						posicion ++;
						if (a =='o'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='t'){
								return new Token ("not",Tipos.TKNOT);
							}	
							else{
								//fuente.skip(1);	
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("no");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{							
							//fuente.skip(1);
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
			case 'o':	//fuente.mark(2);
						a = (char)fuente.read();
						posicion ++;
						if (a =='r'){
							return new Token ("or",Tipos.TKOR);
						}	
						else{
							//fuente.skip(1);	
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
			case 't':	//fuente.mark(4);
						a = (char)fuente.read();
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
									//fuente.skip(1);	
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("tru");
									return new Token (aux,Tipos.TKIDEN);
								}
							}	
							else{
								//fuente.skip(1);	
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("tr");
								return new Token (aux,Tipos.TKIDEN);
							}
						}
						else{							
							//fuente.skip(1);	
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
			case 'f':	//fuente.mark(5);
						a = (char)fuente.read();
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
										//fuente.skip(1);	
										posicion --;
										fuente.seek(posicion);
										String aux;
										aux = leerCaracter("fals");
										return new Token (aux,Tipos.TKIDEN);
										}
								}	
								else{
									//fuente.skip(1);	
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("fal");
									return new Token (aux,Tipos.TKIDEN);
								}
							}	
							else{
								//fuente.skip(1);	
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("fa");
								return new Token (aux,Tipos.TKIDEN);
							}
						}
						else{							
							//fuente.skip(1);	
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
			case 'i':	//fuente.mark(3);
						a = (char)fuente.read();
						posicion ++;
						System.out.println("He reconocido i");
						if (a =='n'){
							a = (char)fuente.read();
							posicion ++;
							if (a =='t'){
								return new Token ("int",Tipos.TKINT);
							}	
							else{
								//fuente.skip(1);	
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("in");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{							
							System.out.println("He reconocido i y es un iden");
							//fuente.skip(1);	
							posicion --;
							fuente.seek(posicion);
							String aux;
							aux = leerCaracter("i");
							System.out.println("Salgo de leerCaracter");
							return new Token (aux,Tipos.TKIDEN);
						}

			/*
			 * Si detectamos 'b' hay que discernir si es el identificador de tipo 'bool' o es un identificador.
			 * Para leer identificadores, usamos leerCaracter().  
			 */
			case 'b':	//fuente.mark(3);
						a = (char)fuente.read();
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
									//fuente.skip(1);	
									posicion --;
									fuente.seek(posicion);
									String aux;
									aux = leerCaracter("boo");
									return new Token (aux,Tipos.TKIDEN);
								}
							}
							else{
								//fuente.skip(1);	
								posicion --;
								fuente.seek(posicion);
								String aux;
								aux = leerCaracter("bo");
								return new Token (aux,Tipos.TKIDEN);
							}	
						}	
						else{							
							//fuente.skip(1);	
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
			default:		//fuente.mark(1);
						//posicion ++;
						if ((a>='1') && (a<='9')){
							//fuente.skip(1);
							//posicion --;
							posicion --;
							fuente.seek(posicion);
							System.out.print("Antes de ir a Leer numero.He reconocido un numero:  ");
							System.out.println(a);
							String aux;
							aux = leerNumero("");
							return new Token (aux,Tipos.TKNUM);
						}
						else{
							if ((a>='A') && (a<='z')){
								//fuente.skip(1);
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
		throw new Exception("ERROR en linea "+linea+": Error de entrada/salida");
	}
	
	/*
	 * Parametros de entrada: String donde lleva almacenado lo que ya ha leido.
	 * Parametros de salida: String con el numero que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los BufferedReader de java y
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
		System.out.print("El numero que tengo leido es: ");
		System.out.println(a);
		if (a == '0'){
			throw new Exception("ERROR en linea "+linea+": No existe ese numero");
		}
		else{
			aux = aux.concat(new Character(a).toString());
			System.out.print("Lo que tengo leido es: ");
			System.out.println(aux);
			while (((a = (char)fuente.read()) != -1) && (i <= 8)){
				posicion ++;
				if ((a>='0') && (a<'9')){
					aux = aux.concat(new Character(a).toString());			
				}
				else{
					System.out.println(aux);
					//fuente.skip(1);
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
	 * Execpciones: IOException, que propagamos de las funciones de los BufferedReader de java y
	 * 				Exception, que generamos cuando detectamos una secuencia de caracteres incorrecta.
	 * 
	 * Cuando llamamos a este metedo ya hemos leido al menos una letra asi que solo tenemos que controlar que se
	 * lean letras, digitos o '_'.
	 * Vamos leyendo mientras sean caracteres validos hasta terminar de leer el identificador.
	 */
	
	public String leerCaracter(String aux) throws Exception, IOException{
		char a;
		System.out.println("Principio de leerCaracter");
		while ((a = (char)fuente.read()) != -1){
			posicion ++;
			if (((a>='A') && (a<'z')) || (a=='_') || ((a>='0') && (a<'9'))){
				System.out.println("Estoy reconociendo el resto de un iden");
				aux = aux.concat(new Character(a).toString());			
			}
			else{
				System.out.println("El iden acabo");
				//fuente.skip(1);
				posicion --;
				fuente.seek(posicion);
				return aux;
			}
			//System.out.println(i);
		}	
		return aux;	
	}
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida: Token que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los BufferedReader de java y
	 * 				Exception, que propagamos de la funcion getToken().
	 * 
	 *  Devuelve el siguiente token para que podamos realizar el preanálisis
	 */
	public Token getNextToken() throws IOException, Exception{
		System.out.println("Estoy en getNextToken, voy al Token");
		lookahead = getToken();
		System.out.println("He salido voy a devolver el Token");
		/*char a;
		a = (char)fuente.read();
		System.out.println(a);
		fuente.seek(posicion);*/
		return lookahead;	
	}
	
	/*
	 * Parametros de entrada: Buffer de entrada del que lee caracteres.
	 * Parametros de salida: Token que ha reconocido.
	 * Execpciones: IOException, que propagamos de las funciones de los BufferedReader de java y
	 * 				Exception, que propagamos de la funcion getNextToken().
	 * 
	 * Devuelve el proximo token si la categoría lexica es la del token que recibe.
	 * Si no, devuelve un token vacío.
	 */
	public Token lexer(int TK) throws IOException, Exception{
		if (TK == lookahead.getCategoriaLexica()){
			return lookahead;
		}
		else{
			return new Token(); 
		}
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
