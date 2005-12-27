package procesador;
import java.util.Hashtable;
import java.io.FileReader;
import java.io.BufferedReader;

public class Lexico {
	
	//Referencia a la Tabla de Simbolos.	
	private Hashtable TablaSimbolos;
	//Buffer de entrada.
	private BufferedReader entrada;
	//Variable que guarda el numero de lea en estudio.
	private int numeroLinea;
	//Variable que controla que no se lea otro caracter del buffer de entrada.
	private boolean noLeerCaracter;
	//Variable que almacena el entero lexico por ltima vez del buffer de entrada.
	private int enteroLeido;
	//Variable que almacena la categoria lexica del ultimo lexema lexico.
	private int categoriaLexica;	
	// Almacena el caracter de preanalisis
	private Token lookahead;
	
	
	public Token daToken() throws Exception {
		//Variable que almacena el estado del AFD.
		int estado = 1;
		char aux;
		//Variable que controla si el nmero que estamos leyendo es entero o real.
		boolean esReal = false;
		
		//Buffer en el que almacenaremos los caracteres lexicos de un identificador.
		StringBuffer buffer = new StringBuffer(1);
			
		//Mientras tengamos que leer un caracter y no se acabe el archivo...
		//Usando noLeerCaracter controlamos que no se tenga que leer un nuevo caracter del archivo
		//de entrada: sirve para aquellos estados en los que hay que distinguir segn el caracter siguiente.
		//Cuando noLeerCaracter es true, no se evala la siguiente expresion, y por tanto, no se lee caracter.
		while ((noLeerCaracter)||((enteroLeido = entrada.read()) != -1)) {
			//Para el caso en el que esta variable estatica true, hay que ponerla a false de nuevo.
			noLeerCaracter = false;
			//Convertimos el entero lexico a caracter.
			aux = (char)enteroLeido;
			
			//...distinguimos segn el estado del AFD.
			switch (estado) {
				case 1:		switch (aux) {
							//Distinguimos segun el caracter a estudiar:
								//Ante un espacio o una tabulacion, no hacemos nada.
								case ' ': 	case '\t': 	case 13: break;

								//Ante salto de linea, avanzamos el contador de lineas.
								case '\n':	numeroLinea++; break;
								
								case ';':	return new Token(";", Tipos.TKPYCOMA); 	
												
								case '/':	return new Token("/", Tipos.TKMUL);	
												
								case '*':	return new Token("*", Tipos.TKMUL);	
									
								case ':': 	estado = 7; 	break;	
								case '|':   estado = 16;	break;	
								case '!':	estado = 22;	break;
								case '=':	estado = 26;	break;
								case '(':	return new Token("(", Tipos.PARAP);	

								case ')':	return new Token(")", Tipos.PARCIE);	

								//En este caso hay que leer un caracter mas de la entrada para saber si estamos
								//ante un separador (&) o el operador lexico and (&&).
								case '&':	if ((char)(enteroLeido = entrada.read()) == '&')
												return new Token("&&", Tipos.AND);
											else {
												//Si no estamos en el caso del operador AND, evitamos que se
												//lea un nuevo caracter de la entrada, y se estudie el ya lexico.
												noLeerCaracter = true;
												return new Token("&", Tipos.TKSEP);
											}
								//Ocurre lo mismo al tener que distinguir entre > y >=.
								case '>':	if ((char)(enteroLeido = entrada.read()) == '=') {
												return new Token(">=", Tipos.MAYIG);
											} else {
												noLeerCaracter = true;
												return new Token(">", Tipos.MAYOR);
											}
								case '<':	if ((char)(enteroLeido = entrada.read()) == '=') {
												return new Token("<=", Tipos.MENIG);
											} else {
												noLeerCaracter = true;
												return new Token("<", Tipos.MENOR);
											}
								case '+':	aux = (char)(enteroLeido = entrada.read());
											//Tras leer el signo '+', tenemos que distinguir segun la siguiente
											//cifra si es el signo positivo de un numero o el operador de suma.
											if ((aux >= '0') && (aux <= '9')) {
												//Si el siguiente caracter es una cifra, creamos el buffer para
												//almacenar el nmero y pasamos al estado 3.
												buffer = new StringBuffer();
												buffer.append("+"+aux);
												estado = 3;
											} else {
												//Si el siguiente caracter no es una cifra, evitamos que se lea
												//un nuevo caracter y devolvemos que es el operador suma.
												noLeerCaracter = true;
												return new Token("+", Tipos.TKSUM);
											} break;
											
								case '-':	aux = (char)(enteroLeido = entrada.read());
											//Tras leer el signo '-', tenemos que distinguir segun la siguiente
											//cifra si es el signo negativo de un numero o el operador de resta.
											if ((aux >= '0') && (aux <= '9')) {
												//Si el siguiente caracter es una cifra, creamos el buffer para
												//almacenar el nmero y pasamos al estado 3.
												buffer = new StringBuffer();
												buffer.append("-"+aux);
												estado = 3;
											} else {
												//Si el siguiente caracter no es una cifra, evitamos que se lea
												//un nuevo caracter y devolvemos que es el operador resta.
												noLeerCaracter = true;
												return new Token("-", Tipos.TKSUM);
											} break;
									case '0':	buffer = new StringBuffer();
											//En caso de leer un 0, creamos un StringBuffer por si viene un nmero
											//real, para ir metiendo en ??? las cifras le???as.
											//Debemos leer el siguiente caracter para saber si es un punto. 
											aux = (char)(enteroLeido = entrada.read());
											if (aux == '.') {
												//Si es un punto, pasamos el AFD al estado 4.
												estado = 4;
												buffer.append("0.");
											} else
												//Comprobamos si es una cifra lo que viene a continuaci???.
												if ((aux >= '0') && (aux <= '9'))
													throw new Exception("ERROR en linea "+numeroLinea+": No se admiten ceros a la izquierda.");
												else {
													//En caso de que no sea una cifra, hay que devolver el cero
													//encontrado y activar que no se lea un nuevo caracter.
													noLeerCaracter = true;
													return new Token("0", Tipos.TKNUM);
												}
											break;
																									
								default:	//Si el caracter a estudiar es una letra...
											if (((aux >= 'a') && (aux <= 'z')) || ((aux >= 'A') && (aux <= 'Z'))) {
												estado = 6;
												//Creamos un nuevo buffer para ir almacenando los caracteres del
												//identificador a leer.
												buffer = new StringBuffer();
												buffer.append(aux);
												break;
											} else
												//Si es una cifra...
												if ((aux >= '1') && (aux <= '9')) {
													//Creamos un nuevo buffer para ir almacenando las cifras del numero
													//que vamos a leer.
													buffer = new StringBuffer();
													buffer.append(aux);
													//Pasamos al estado 3.
													estado = 3;	
												} else
													throw new Exception("ERROR en linea "+numeroLinea+": Caracter "+aux+" no valido.");
							} break;
					case 3:		if ((aux >= '0') && (aux <= '9'))
								//Si es una nueva cifra, la a???dimos al buffer que tenemos.
								buffer.append(aux);
								else 
									if (aux == '.') {
										//Si es un punto, pasamos el aut???ata al estado 4 y lo a???dimos al buffer.
										buffer.append('.');
										estado = 4;	
									} else {
										//Si no es una nueva cifra ni un punto, evitamos que se lea otro caracter...
										noLeerCaracter = true;
										//...y devolvemos que se ha leido un nmero.
										return new Token(buffer.toString(), Tipos.TKNUM);
									} break;
					case 4:		if ((aux >= '0') && (aux <= '9')) {
								//Si es una nueva cifra, la anadimos al buffer que tenemos y pasamos al estado 5.
								buffer.append(aux);
									estado = 5;
								} else {
									//Si no es una nueva cifra, el numero se ha quedado sin parte decimal.
									throw new Exception("ERROR en linea "+numeroLinea+": Se esperaba parte decimal en el numero "+buffer.toString());
								} break;
								
					case 5:		if ((aux >= '0') && (aux <= '9'))
									//Si es una nueva cifra, la a???dimos al buffer que tenemos.
									buffer.append(aux);
								else { 
									//Si no es una nueva cifra, evitamos que se lea otro caracter...
									noLeerCaracter = true;
									//...y devolvemos que se ha leido un numero.
									return new Token(buffer.toString(), Tipos.TKNUM);
								} break;
																							
					case 6:		if (((aux >= 'a') && (aux <= 'z')) || ((aux >= 'A') && (aux <= 'Z'))
											|| ((aux >= '0') && (aux <= '9'))) {
									//Si el caracter es una letra o un digito lo anadimos al buffer.
									buffer.append(aux);	
								} else {
									//Si el caracter leido no es un digito o una letra, tenemos que evitar
									//que se lea un nuevo caracter de la entrada.
									noLeerCaracter = true;
									//Buscamos si la secuencia de caracteres leida hasta
									//entonces esta en la Tabla de Simbolos.
									String secuencia = buffer.toString();
									return new Token(secuencia, Tipos.TKIDEN);
								}
								break;
					case 7:		if (aux == '=') {
									return new Token(":=", Tipos.ASIGN);
								} else 
									throw new Exception("ERROR en linea "+numeroLinea+": Se esperaba := y no :" + aux);
					case 16:	if (aux == '|') {
									return new Token("||", Tipos.OR);
								} else 
									throw new Exception("ERROR en linea "+numeroLinea+": Se esperaba || y no |" + aux);
					case 22:	if (aux == '=') {
									return new Token("!=", Tipos.DISTINTO);
								} else 
									throw new Exception("ERROR en linea "+numeroLinea+": Se esperaba != y no !" + aux);
					case 26:	if (aux == '=') {
									return new Token("==", Tipos.IGUAL);
								}
								else 
									throw new Exception("ERROR en linea "+numeroLinea+": Se esperaba == y no =" + aux);
				}
			}
			//Si llegamos a este punto del codigo, es que no quedan caracteres en el fichero
			//(read() ha devuelto -1), asique devolvemos el token final de fichero: TKFF
			//Antes cerramos el buffer de entrada.
			cierraEntrada();
			return new Token("", Tipos.TKFF);
		}

	
	public Token getNextToken() throws Exception{
		
		return daToken();
		
	}

	public Lexico(String fuente, Hashtable ht) throws Exception {

		//Guardamos referencia a la Tabla de Simbolos.
		TablaSimbolos = ht;
		//Iniciamos la Tabla de Simbolos con las palabras reservadas "true", "false", "real" y "bool".
		TablaSimbolos.put("true", new Integer(Tipos.BOOLEANO));
		TablaSimbolos.put("false", new Integer(Tipos.BOOLEANO));
		TablaSimbolos.put("real", new Integer(Tipos.TIPO));
		TablaSimbolos.put("bool", new Integer(Tipos.TIPO));
		//Creamos el buffer de lectura para el archivo fuente.
		try {
			entrada = new BufferedReader(new FileReader(fuente));
		} catch (java.io.FileNotFoundException e) {
			throw new Exception("ERROR: Archivo no encontrado: " + fuente);	
		}
		
		//Inicializamos los atributos del analizador.
		numeroLinea = 1;
		noLeerCaracter = false;
		enteroLeido = 0;
		categoriaLexica = -1;
		
		// Leemos el primer simbolo
		lookahead = new Token();
		lookahead = getNextToken();
	}
	
	
	public static Hashtable creaTS(){
		//Por ahora vamos a usar una tabla hash como Tabla de Simbolos.
		Hashtable ht = new Hashtable();	
		return ht;
	}
	
	private void cierraEntrada() throws Exception {
		try {
			entrada.close();
		} catch (java.io.IOException e) {
			throw new Exception("ERROR con entrada y salida al cerrar BufferedReader del Analizador Lexico.");	
		}
	}

	public int getTokenPreanalisis(){
		
		return lookahead.daCategoriaLexica();

	}
	
	public String getLexemaPreanalisis(){
		
		return lookahead.daLexema();

	}

	public String reconoce(int tk) throws Exception{
		
		String lexema;

		if (tk == lookahead.daCategoriaLexica()){
			
		}
		else{
			/*
			String mensajeDeError = "Error Sintactico: Se esperaba un ";
			mensajeDeError = mensajeDeError + "Identificador";
			throw new Exception("hola");	
			*/
		}	
		
		lexema = lookahead.daLexema();
		lookahead = daToken();
		return lexema;
		
	}
/*		
	public void debuguear(){
		System.out.println("debuguear");
	}
*/
	
}
