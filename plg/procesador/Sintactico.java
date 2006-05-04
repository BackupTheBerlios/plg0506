package procesador;

import java.io.RandomAccessFile;
import tablaSimbolos.TablaSimbolos;
//import tablaSimbolos.Par;

/**
 * La clase <B>Sintactico</B> analiza los tokens que han sido reconocidos por <B>Lexico</B>. 
 * <P>La clase Sintactico cuenta con los siguientes atributos:
 * <UL><LI><CODE>codigo:</CODE> Se encarga de almacenar el cdigo generado por las instrucciones del lenguaje. De tipo String.</LI>
 * <LI><CODE>lexico:</CODE> Analiza el fichero de entrada para reconocer tokens. De tipo Lexico.</LI>
 * <LI><CODE>TS:</CODE> Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos. De tipo TablaSimbolos.</LI>
 * <LI><CODE>dir:</CODE> Entero que marca la posicin de la pila con la que estamos trabajando. De tipo Entero.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Jonás Andradas, Leticia García y Silvia Martín
 *
 */

public class Sintactico{
	
	/*
	 * Atributos de la clase:
	 * 
	 * codigo: Se encarga de almacenar el cdigo generado por las instrucciones del lenguaje.
	 * lexico: Analiza el fichero de entrada para reconocer tokens.
 	 * TS: Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
 	 * dir: Entero que marca la posicin de la pila con la que estamos trabajando.
	 */
	Codigo codigo;
	Lexico lexico;
	TablaSimbolos TS;
	int dir;
	
	/**
	 * Constructor que inicializa los atributos con los datos que recibe por parametro.
	 * 
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contine que contine el cdigo a analizar.
	 * @param T Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
	 * @throws Exception Propaga una excepcion que haya sucedido en otro lugar.
	 */
	public Sintactico(RandomAccessFile fuente, TablaSimbolos T) throws Exception{
		codigo = new Codigo(); 
		lexico = new Lexico(fuente);		
		TS = T;
		dir = 0;
	}

	/**
	 * 
	 * @return
	 */
	public Codigo getCodigo() {
		return codigo;
	}

	/**
	 * 
	 * @param codigo
	 */
	public void setCodigo(Codigo codigo) {
		this.codigo = codigo;
	}

	/**
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		//System.out.println("Start");
		Prog();
		codigo.muestraCodigo();
	}

	/**
	 * Evalúa el programa.  Primero lee las declaraciones de variables (identificadores), que se encuentran
	 * separados del conjunto de instrucciones "Is" mediante un "#".  Acto seguido, procesa cada instrucción de Is.
	 * 
	 * @return errDeProg Devuelve un booleano que indica si existio un error al analizar el codigo del Programa. 
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */	
	public boolean Prog() throws Exception{
		
		//System.out.println("Prog");
		boolean errDeProg = true;
		Atributos atrDeDecs;
		Atributos atrDeIs;
		atrDeDecs = Decs();
		atrDeIs = Is();
		errDeProg = atrDeDecs.getErr() || atrDeIs.getErr();
		return errDeProg;	
	}
	
	/**
	 * Recorre el conjunto de declaraciones (Dec) una por una.  Si tras una declaración encontramos
	 * un punto y coma (";"), procesamos otra más.  Si en cambio lo que encontramos es una almohadilla
	 * ("#"), dejamos de leer Decs.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Decs() throws Exception{
		
		//System.out.println("Decs");
		Atributos atrDeDecs;
		Atributos atrDeDec;
		Atributos a = new Atributos();
		boolean errDeDecs = false;
		atrDeDec = Dec();
		lexico.lexer();
		if (lexico.reconoce(Tipos.TKPYCOMA)){
			atrDeDecs = Decs();
			errDeDecs = atrDeDec.getErr() || atrDeDecs.getErr();
			a.setErr(errDeDecs);
			a.setTipo("");
			return a;
		}
		else{
			if (lexico.reconoce(Tipos.TKCUA)){
				errDeDecs = atrDeDec.getErr() || errDeDecs;
				a.setErr(errDeDecs);
				a.setTipo("");
				return a;
			}
			else{
				errDeDecs = true;
			}
		}
		a.setErr(errDeDecs);
		a.setTipo("");
		return a;
	}

	/**
	 * Procesa una declaración de variable.  Cada declaracion Dec consta de dos elementos:  El tipo de la variable
	 * y su nombre, de la forma: 
	 * 			tipo identificador;
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Dec() throws Exception{

		//System.out.println("Dec");
		boolean errDeDec;
		Atributos a = new Atributos();
		String t = "";
		Token tk;
		tk = lexico.lexer();
		if (lexico.reconoce(Tipos.TKINT) || lexico.reconoce(Tipos.TKBOOL)){
			t = tk.getLexema();
			tk = lexico.lexer();
			if (lexico.reconoce(Tipos.TKIDEN)){
				String i = tk.getLexema();
				errDeDec = TS.existeID(i);
				if (!errDeDec){
					TS.agnadeID(i,t);
				}
				else {
					throw new Exception("ERROR: Identificador duplicado.  Cada identificador solo se puede declarar una vez");
				}
			}
			else{
				errDeDec = true;
				throw new Exception("ERROR: Declaracion Incorrecta. El formato correcto es \"tipo identificador;\".");
			}
		}
		else{
			errDeDec = true;
			throw new Exception("ERROR: Declaracion Incorrecta. El formato correcto es \"tipo identificador;\".");
		}
		a.setErr(errDeDec);
		a.setTipo(t);
		return a;
	}	
	
	/**
	 * Recorre el conjunto de Instrucciones del programa.  Cada instruccion I se separa del conjunto de 
	 * instrucciones restantes (Is) mediante un punto y coma (";").  Si encontramos el token Fin de Fichero,
	 * hemos terminado de leer instrucciones. 
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Is() throws Exception{
		
		//System.out.println("Is");
		Atributos atrDeIs;
		Atributos atrDeI;
		Atributos a = new Atributos();
		boolean errDeIs = false;
		atrDeI = I();
		if (lexico.reconoce(Tipos.TKFF)){
			errDeIs = false;	
		}
		else{
			if (lexico.reconoce(Tipos.TKPYCOMA)){
				atrDeIs = Is();
				errDeIs = atrDeI.getErr() || atrDeIs.getErr();
				a.setErr(errDeIs);
				a.setTipo(atrDeI.getTipo());
				return a;
			}
			else{
				errDeIs = true;
				throw new Exception("ERROR: Secuencia de Instrucciones Incorrecta. Cada instruccion ha de ir separada de la siguiente por un \";\"");
			}
		}
		a.setErr(errDeIs);
		a.setTipo(atrDeI.getTipo());
		return a;	
	}

	/**
	 * Procesa cada instrucción el conjunto de instrucciones del Programa.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos I() throws Exception{

		//System.out.println("I");
		Atributos a = new Atributos();
		Atributos atrDeIAsig;
		atrDeIAsig = IAsig();
		a.setErr(atrDeIAsig.getErr());
		a.setTipo(atrDeIAsig.getTipo());
		return a;
	}
	
	/**
	 * Procesa una instrucción de asignación, de la forma:
	 * 
	 * 		identificador := Expresión.
	 * 
	 * Si hay un error en el formato de la instrucción de asignación, o si 
	 * el tipo del identificador usado no coincide con el de la expresión, 
	 * se lanza una Excepción.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos IAsig() throws Exception{
		
		//System.out.println("IAsig");
		Atributos  atrDeExpC = new Atributos();
		Atributos a = new Atributos();
		boolean errDeIAsig; 
		Token tk;
		String lex = "";
		tk = lexico.lexer();
		if (lexico.reconoce(Tipos.TKIDEN)){
			lex = tk.getLexema();
			tk = lexico.lexer();
			if (lexico.reconoce(Tipos.TKASIGN)){
				atrDeExpC = ExpC();
				errDeIAsig = atrDeExpC.getErr() || !(TS.existeID(lex));
				if (!(TS.existeID(lex))){
					throw new Exception("ERROR: Identificador no declarado. \nEl identificador ha de estar declarado en la seccion de Declaraciones antes de que se le pueda asignar un valor.");
				}
				else{
					//if (atrDeExpC.getTipo().equals(((Par)TS.getTabla().get(lex)).getTipo())){
						codigo.genIns("desapila-dir",TS.dirID(lex));
					//}
					//else{
					//	throw new Exception("ERROR: El Tipo de la Expresion no coincide con el del Identificador.");
					//}
				}
			}
			else{
				errDeIAsig = true;
				throw new Exception("ERROR: Asignación Incorrecta. El formato correcto es \"identificador := Expresion;\".");
			}
		}
		else{
			if (! (lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKFF))){
				errDeIAsig = true;
				throw new Exception("ERROR: Asignación Incorrecta. El formato correcto es \"identificador := Expresion;\".");
			} 
			else {
				errDeIAsig = false;
			}
		}
		a.setErr(errDeIAsig);
		a.setTipo(atrDeExpC.getTipo());
		return a;
	}
	
	/**
	 * Procesa y desarrolla una Expresión de Comparación, ExpC, llamando a Exp y a RExpC, 
	 * para empezar a desarrollar el árbol sintáctico que reconocerá la Expresión. 
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos ExpC() throws Exception{
		
		//System.out.println("ExpC");
		
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		atrDeExp = Exp();
		Atributos a = new Atributos();
		boolean errDeExpC;
		atrDeRExpC = RExpC();
		errDeExpC = atrDeExp.getErr() || atrDeRExpC.getErr() || (!atrDeExp.getTipo().equals(atrDeRExpC.getTipo()) && !atrDeRExpC.getTipo().equals(""));
		a.setErr(errDeExpC);
		if (atrDeRExpC.equals("")){
			a.setTipo(atrDeExp.getTipo());
		}
		else{
			a.setTipo("bool");
		}
		return a;
	}
	
	/**
	 * Reconoce la segunda mitad de una Expresión de Comparación de la forma:
	 * 
	 *       OpComp Exp RExpC | landa
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RExpC() throws Exception{
		
		//System.out.println("RExpC");
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		Atributos a = new Atributos();
		boolean errDeRExpC = false;
		if (!lexico.reconoce(Tipos.TKPYCOMA)){
			if (lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKMAYIG) || lexico.reconoce(Tipos.TKMEN) || lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) || lexico.reconoce(Tipos.TKDIF)){
				Token tk = lexico.getLookahead();
				atrDeExp = Exp();
				genOpComp(tk.getLexema());				
				atrDeRExpC = RExpC();
				errDeRExpC = atrDeExp.getErr() || atrDeRExpC.getErr() || (!(atrDeRExpC.getTipo() == atrDeExp.getTipo()) && !atrDeRExpC.getTipo().equals(""));
				a.setErr(errDeRExpC);
				a.setTipo(atrDeExp.getTipo());	
				return a;
			}
		}	
		else{
			errDeRExpC = false;
		}
		a.setErr(errDeRExpC);
		a.setTipo("");
		return a;
	}
	
	/**
	 * Reconoce una Expresión, tanto aritmética como lógica (booleana).
	 *  
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Exp() throws Exception{
		
		//System.out.println("Exp");
		Atributos atrDeTerm;
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		boolean errDeExp = false;
		long posFuente = lexico.getFuente().getFilePointer();
		int posLex = lexico.getPosicion();
		atrDeTerm = Term();
		if (!atrDeTerm.getErr()){
			atrDeRExp = RExp();
			if ((atrDeRExp.getTipo() == null)||(atrDeRExp.getTipo().equals(""))){
				atrDeRExp.setTipo("int");
			}	
			errDeExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("int")) && (!atrDeRExp.getTipo().equals("")));
		}
		else{
			lexico.getFuente().seek(posFuente);
			lexico.setPosicion(posLex);
			lexico.lexer();
			atrDeTerm = TermB();
			if (!atrDeTerm.getErr()){
				atrDeRExp = RExp();					
				atrDeRExp.setTipo("bool");	
				errDeExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("bool")) && (!atrDeRExp.getTipo().equals("")));
			}
			else{
				errDeExp = true;
			}
		}	
		a.setErr(errDeExp);
		a.setTipo(atrDeTerm.getTipo());
		return a;
	}
	
	/**
	 * Reconoce la segunda mitad (con el operador) de la descomposición de una Expresión booleana o aritmética.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RExp() throws Exception{
		//System.out.println("RExp");
		Atributos atrDeTerm = new Atributos();
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		boolean errDeRExp = false;
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI))){
			Token tk;
			tk = lexico.getLookahead(); 
			if (lexico.reconoce(Tipos.TKSUMA) || lexico.reconoce(Tipos.TKRESTA)){
				atrDeTerm = Term();
				genOpAd(tk.getLexema());
				atrDeRExp = RExp();
				errDeRExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("int")) && (!atrDeRExp.getTipo().equals(""))); 
			}
			else{
				if (lexico.reconoce(Tipos.TKOR)){
					tk = lexico.lexer();
					atrDeTerm = TermB();
					genOpOr();
					atrDeRExp = RExp();
					errDeRExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("bool")) && (!atrDeRExp.getTipo().equals(""))); 
				}
				else{
					errDeRExp = false;
				}
			}
		} 
		else {
			atrDeTerm.setTipo("");
		}
		a.setErr(errDeRExp);
		a.setTipo(atrDeTerm.getTipo());
		return a;
	}
	
	/**
	 * Reconoce un Término, compuesto de un Factor y un Término Recursivo:
	 *  
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Term() throws Exception{
		
		//System.out.println("Term");
		Atributos atrDeFact;
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		atrDeFact = Fact();
		atrDeRTerm = RTerm();	
		boolean errDeTerm;
		errDeTerm = atrDeFact.getErr() || atrDeRTerm.getErr();
		a.setErr(errDeTerm);
		a.setTipo("int");
		return a;
	}
	
	/**
	 * Reconoce un Término Aritmético recursivo.
	 *  
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RTerm() throws Exception{
		
		//System.out.println("RTerm");
		Atributos atrDeFact = new Atributos();
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		boolean errDeRTerm = false;
		Token tk;
		tk = lexico.lexer();
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKSUMA) ||
				lexico.reconoce(Tipos.TKRESTA))){
			if (lexico.reconoce(Tipos.TKMULT) || lexico.reconoce(Tipos.TKDIV)){
				atrDeFact = Fact();
				genOpMul(tk.getLexema());
				atrDeRTerm = RTerm();
				errDeRTerm = atrDeFact.getErr() || atrDeRTerm.getErr() || ((!atrDeRTerm.getTipo().equals("int")) && (!atrDeRTerm.getTipo().equals("")));
				atrDeFact.setTipo(atrDeFact.getTipo());
			}
			else{
				errDeRTerm = true;
				a.setErr(errDeRTerm);
				a.setTipo("");
				return a;
			}
		}
		else{
			errDeRTerm = false;
			atrDeFact.setTipo("");
		}
		a.setErr(errDeRTerm);
		a.setTipo(atrDeFact.getTipo());
		return a;
	}

	/**
	 * Reconoce un Término Booleano.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos TermB() throws Exception{
		
		//System.out.println("TermB");
		Atributos atrDeNega;
		Atributos atrDeRTermB;
		Atributos a = new Atributos();
		atrDeNega = Nega();
		atrDeRTermB = RTermB();
		boolean errDeTermB;
		errDeTermB = atrDeNega.getErr() || atrDeRTermB.getErr();
		a.setErr(errDeTermB);
		a.setTipo("bool");
		return a;
	}
	
	/**
	 * Reconoce un Término Booleano Recursivo.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RTermB() throws Exception{
		
		//System.out.println("RTermB");
		Atributos atrDeNega = new Atributos();
		Atributos atrDeRTermB;
		Atributos a = new Atributos();
		boolean errDeRTermB = false;
		if (!lexico.reconoce(Tipos.TKPYCOMA)){
			lexico.getLookahead();
			if (lexico.reconoce(Tipos.TKAND)){
				lexico.lexer();
				atrDeNega = Nega();
				genOpAnd();
				atrDeRTermB = RTermB();
				errDeRTermB = atrDeNega.getErr() || atrDeRTermB.getErr() || ((!atrDeRTermB.getTipo().equals("bool")) && (!atrDeRTermB.getTipo().equals(""))); 
			}
			else{
				errDeRTermB = false;
				atrDeNega.setTipo("");
			}
		}
		else {
			
		}
		a.setErr(errDeRTermB);
		a.setTipo(atrDeNega.getTipo());
		return a;
	}
	
	/**
	 * Reconoce un Factor.  Un Factor puede ser un entero, un identificador o una Expresión aritmética
	 * entre paréntesis.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Fact() throws Exception{
		
		//System.out.println("Fact");
		Atributos a = new Atributos();
		boolean errDeFact = true;
		Atributos atrDeExp;
		Token tk;
		tk = lexico.lexer();
		if (lexico.reconoce(Tipos.TKNUM)){
			errDeFact = false;
			codigo. genIns("apila", Integer.decode(tk.getLexema()).intValue());
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				errDeFact = !TS.existeID(tk.getLexema());
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema()));
			}
			else{
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExp = Exp();
					if (lexico.reconoce(Tipos.TKPCI)){
						errDeFact = atrDeExp.getErr() || (!atrDeExp.getTipo().equals("int") && !atrDeExp.getTipo().equals(""));
					}
					else{
						errDeFact = true;
					}
				}
				else{
					errDeFact = true;
				}
			}
		}
		a.setErr(errDeFact);
		a.setTipo("int");
		return a;
	}
	
	/**
	 * Reconoce la negación (o no) de un componente booleano.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Nega() throws Exception{
		
		//System.out.println("Nega");
		Atributos atrDeClausula = new Atributos();
		Atributos a = new Atributos();
		boolean errDeNega;
		if (lexico.reconoce(Tipos.TKNOT)){
			lexico.lexer();
			 atrDeClausula = Clausula();
			 genOpNot();
			 errDeNega = atrDeClausula.getErr(); 
		}
		else{
			atrDeClausula = Clausula();
			errDeNega = atrDeClausula.getErr();
		}
		a.setErr(errDeNega);
		a.setTipo(atrDeClausula.getTipo());
		return a;
	}
	
	/**
	 * Reconoce una Clausula de tipo booleano.  Una Clausula puede ser el valor "true" o 
	 * el valor "false", un identificador (de tipo booleano), o una Expresión booleana entre
	 * paréntesis.
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Clausula() throws Exception{
		
		//System.out.println("Clausula");
		Atributos a = new Atributos();
		boolean errDeClausula = true;
		Atributos atrDeExpC;
		Token tk;
		tk = lexico.getLookahead();
		if (lexico.reconoce(Tipos.TKTRUE) || lexico.reconoce(Tipos.TKFALSE)){
			errDeClausula = false;
			int cod;
			// Revisar el intValue de true y false
			if (tk.getLexema().equals("false"))
				cod = 0;
			else
				cod = 1;
			codigo.genIns("apila", cod);
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				errDeClausula = !TS.existeID(tk.getLexema());
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema()));
			}
			else{
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExpC = ExpC();
					lexico.getLookahead();
					if (lexico.reconoce(Tipos.TKPCI)){
						errDeClausula = atrDeExpC.getErr() || !atrDeExpC.getTipo().equals("bool");
					}
					else{
						errDeClausula = true;
					}
				}
				else{
					errDeClausula = true;
				}
			}
		}
		lexico.lexer();
		a.setErr(errDeClausula);
		a.setTipo("bool");
		return a;
	}
	
	/**
	 * Genera el código de la operación de suma o resta.
	 * 
	 * @param opDeOpAd
	 */
	public void genOpAd(String opDeOpAd){
		
		if (opDeOpAd == "+")
			codigo.genIns("suma");
		else
			codigo.genIns("resta");	
	}
	
	/**
	 * Genera el código de la operación de multiplicación o división.
	 * 
	 * @param opDeOpMul
	 */
	public void genOpMul(String opDeOpMul){
		
		if (opDeOpMul == "*")
			codigo.genIns("multiplica");
		else
			codigo.genIns("divide");
		
	}
	
	/**
	 * Genera el código de la operación de comparación
	 * @param opDeOpComp
	 */
	public void genOpComp(String opDeOpComp){
		
		if (opDeOpComp == "<="){
			codigo.genIns("menor_o_igual");
		}	
		if (opDeOpComp == "<"){
				codigo.genIns("menor");
		}
		if (opDeOpComp == ">="){
			codigo.genIns("mayor_o_igual");
		}	
		if (opDeOpComp == ">"){
				codigo.genIns("mayor");
		}
		if (opDeOpComp == "="){
			codigo.genIns("igual");
		}	
		if (opDeOpComp == "!="){
				codigo.genIns("distinto");
		}
	}

	/**
	 * Genera el código de la operación booleana "and".
	 *
	 */
	public void genOpAnd(){

		codigo.genIns("and");
		
	}

	/**
	 * Genera el código de la operación booleana "or".
	 *
	 */
	public void genOpOr(){

		codigo.genIns("or");
		
	}

	/**
	 * Genera el código de la negación booleana "not".
	 *
	 */
	public void genOpNot(){

		codigo.genIns("not");
		
	}
}