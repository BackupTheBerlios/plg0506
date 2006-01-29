package procesador;

import java.io.RandomAccessFile;
import tablaSimbolos.TablaSimbolos;

/**
 * La clase <B>Sintactico</B> analiza los tokens que han sido reconocidos por <B>Lexico</B>. 
 * <P>La clase Sintactico cuenta con los siguientes atributos:
 * <UL><LI><CODE>codigo:</CODE> Se encarga de almacenar el cdigo generado por las instrucciones del lenguaje. De tipo String.</LI>
 * <LI><CODE>lexico:</CODE> Analiza el fichero de entrada para reconocer tokens. De tipo Lexico.</LI>
 * <LI><CODE>TS:</CODE> Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos. De tipo TablaSimbolos.</LI>
 * <LI><CODE>dir:</CODE> Entero que marca la posicin de la pila con la que estamos trabajando. De tipo Entero.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Jons Andradas, Leticia Garca y Silvia Martn
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
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		System.out.println("Start");
		Prog();
		codigo.muestraCodigo();
	}

	/**
	 * 
	 * 
	 * @return errDeProg Devuelve un booleano que indica si existio un error al analizar el codigo del Programa. 
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */	
	public boolean Prog() throws Exception{
		
		System.out.println("Prog");
		boolean errDeProg = true;
		Atributos atrDeDecs;
		Atributos atrDeIs;
		atrDeDecs = Decs();
		atrDeIs = Is();
		errDeProg = atrDeDecs.getErr() || atrDeIs.getErr();
		return errDeProg;	
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Decs() throws Exception{
		
		System.out.println("Decs");
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
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Dec() throws Exception{

		System.out.println("Dec");
		boolean errDeDec;
		Atributos a = new Atributos();
		String t = "";
		Token tk;
		tk = lexico.lexer();
		//System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKINT) || lexico.reconoce(Tipos.TKBOOL)){
			t = tk.getLexema();
			tk = lexico.lexer();
			//System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKIDEN)){
				String i = tk.getLexema();
				errDeDec = TS.existeID(i,t);
				if (!errDeDec){
					TS.agnadeID(i,t);
				}
			}
			else{
				errDeDec = true;
			}
		}
		else{
			errDeDec = true;
		}
		a.setErr(errDeDec);
		a.setTipo(t);
		return a;
	}	
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Is() throws Exception{
		
		System.out.println("Is");
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
			}
		}
		a.setErr(errDeIs);
		a.setTipo(atrDeI.getTipo());
		return a;	
	}

	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos I() throws Exception{

		System.out.println("I");
		Atributos a = new Atributos();
		Atributos atrDeIAsig;
		atrDeIAsig = IAsig();
		a.setErr(atrDeIAsig.getErr());
		a.setTipo(atrDeIAsig.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos IAsig() throws Exception{
		
		System.out.println("IAsig");
		Atributos  atrDeExpC = new Atributos();
		Atributos a = new Atributos();
		boolean errDeIAsig; 
		Token tk;
		String lex = "";
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKIDEN)){
			lex = tk.getLexema();
			tk = lexico.lexer();
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKASIGN)){
				atrDeExpC = ExpC();
				errDeIAsig = atrDeExpC.getErr() || !(TS.existeID(lex,atrDeExpC.getTipo()));
				codigo.genIns("desapila-dir",TS.dirID(lex,atrDeExpC.getTipo()));
			}
			else{
				errDeIAsig = true;
			}
		}
		else{
			errDeIAsig = true;
		}
		a.setErr(errDeIAsig);
		a.setTipo(atrDeExpC.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos ExpC() throws Exception{
		
		System.out.println("ExpC");
		
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		atrDeExp = Exp();
		Atributos a = new Atributos();
		boolean errDeExpC;
		atrDeRExpC = RExpC();
		errDeExpC = atrDeExp.getErr() || atrDeRExpC.getErr() || !atrDeExp.getTipo().equals(atrDeRExpC.getTipo()) || !atrDeRExpC.getTipo().equals("");
		a.setErr(errDeExpC);
		a.setTipo(atrDeExp.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RExpC() throws Exception{
		
		System.out.println("RExpC");
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		Atributos a = new Atributos();
		boolean errDeRExpC = false;
		if (!lexico.reconoce(Tipos.TKPYCOMA)){
			//System.out.println("Y el token es: " + tk.muestraToken());
			if (lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKMAYIG) || lexico.reconoce(Tipos.TKMEN) || lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) || lexico.reconoce(Tipos.TKDIF)){
				//System.out.println("Entramos a procesarlo");
				Token tk = lexico.getLookahead();
				genOpComp(tk.getLexema());
				atrDeExp = Exp();
				//Token tk;
				//tk = lexico.getLookahead();//lexico.lexer();
				//System.out.println(tk.muestraToken());
				System.out.println("Entro a RExpC");
				atrDeRExpC = RExpC();
				errDeRExpC = atrDeExp.getErr() || atrDeRExpC.getErr() || !(atrDeRExpC.getTipo() == atrDeExp.getTipo());
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
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Exp() throws Exception{
		
		System.out.println("Exp");
		Atributos atrDeTerm;
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		boolean errDeExp = false;
		atrDeTerm = Term(); 
		if (!atrDeTerm.getErr()){
			//System.out.println("Es un Term, de integer");
			atrDeRExp = RExp();
			if (atrDeRExp.getTipo() == null){
				atrDeRExp.setTipo("int");
			}	
			System.out.println("Era un term int");
			System.out.println(errDeExp);
			errDeExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("int")) && (!atrDeRExp.getTipo().equals("")));
		}
		else{
			//System.out.println("Es un TermB, de bool");
			atrDeTerm = TermB();
			if (!atrDeTerm.getErr()){
				System.out.println("Era un termB bool");
				atrDeRExp = RExp();
				if (atrDeRExp.getTipo() == null){
					atrDeRExp.setTipo("bool");
				}	
				errDeExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("bool")) && (!atrDeRExp.getTipo().equals("")));
			}
			else{
				errDeExp = true;
			}
		}	
		System.out.println("Salgo de Exp");
		a.setErr(errDeExp);
		a.setTipo(atrDeTerm.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RExp() throws Exception{
		System.out.println("RExp");
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
			tk = lexico.getLookahead();  //lexico.lexer();
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKSUMA) || lexico.reconoce(Tipos.TKRESTA)){
				atrDeTerm = Term();
				genOpAd(tk.getLexema());
				System.out.println("Estoy donde debo");
				atrDeRExp = RExp();
				errDeRExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("int")) && (!atrDeRExp.getTipo().equals(""))); 
			}
			else{
				if (lexico.reconoce(Tipos.TKOR)){
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
			System.out.println("He leido un ; o algo de eso");
			atrDeTerm.setTipo("");
		}
		a.setErr(errDeRExp);
		a.setTipo(atrDeTerm.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Term() throws Exception{
		
		System.out.println("Term");
		Atributos atrDeFact;
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		atrDeFact = Fact();
		//if (!atrDeFact.getErr()){
			//System.out.println("Term es Fact RTerm");
			atrDeRTerm = RTerm();
		//}	
		//else{
			//atrDeRTerm = new Atributos(true, "");
		//}	
		boolean errDeTerm;
		errDeTerm = atrDeFact.getErr() || atrDeRTerm.getErr();
		a.setErr(errDeTerm);
		//System.out.println("El error de TERM: " + errDeTerm);
		a.setTipo("int");
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RTerm() throws Exception{
		
		System.out.println("RTerm");
		Atributos atrDeFact = new Atributos();
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		boolean errDeRTerm = false;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKSUMA) ||
				lexico.reconoce(Tipos.TKRESTA))){
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKMULT) || lexico.reconoce(Tipos.TKDIV)){
				atrDeFact = Fact();
				genOpMul(tk.getLexema());
				atrDeRTerm = RTerm();
				errDeRTerm = atrDeFact.getErr() || atrDeRTerm.getErr() || ((!atrDeRTerm.getTipo().equals("int")) && (!atrDeRTerm.getTipo().equals("")));
			}
			else{
				errDeRTerm = true;
				a.setErr(errDeRTerm);
				a.setTipo("");
				System.out.println("Error " + errDeRTerm);
				System.out.println("Tipo " + a.getTipo());
				return a;
			}
		}
		else{
			errDeRTerm = false;
			atrDeFact.setTipo("");
		}
		System.out.println("Rama else");
		a.setErr(errDeRTerm);
		a.setTipo(atrDeFact.getTipo());
		System.out.println("Error " + errDeRTerm);
		System.out.println("Tipo " + a.getTipo());
		return a;
	}

	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos TermB() throws Exception{
		
		System.out.println("TermB");
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
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos RTermB() throws Exception{
		
		System.out.println("RTermB");
		Atributos atrDeNega = new Atributos();
		Atributos atrDeRTermB;
		Atributos a = new Atributos();
		boolean errDeRTermB = false;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKOR))){
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKAND)){
				atrDeNega = Nega();
				genOpMul(tk.getLexema());
				atrDeRTermB = RTermB();
				errDeRTermB = atrDeNega.getErr() || atrDeRTermB.getErr() || ((!atrDeRTermB.getTipo().equals("bool")) && (!atrDeRTermB.getTipo().equals("")));
			}
			else{
				errDeRTermB = true;
				a.setErr(errDeRTermB);
				a.setTipo("");
				return a;
			}
		}
		else{
			errDeRTermB = false;
			atrDeNega.setTipo("");
		}
		System.out.println("Rama else");
		a.setErr(errDeRTermB);
		a.setTipo(atrDeNega.getTipo());
		System.out.println("Error " + errDeRTermB);
		System.out.println("Tipo " + a.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Fact() throws Exception{
		
		System.out.println("Fact");
		Atributos a = new Atributos();
		boolean errDeFact = true;
		Atributos atrDeExp;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKNUM)){
			errDeFact = false;
			codigo. genIns("apila", Integer.decode(tk.getLexema()).intValue());
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				errDeFact = !TS.existeID(tk.getLexema(), "int");
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema(),"int"));
			}
			else{
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExp = Exp();
					if (lexico.reconoce(Tipos.TKPCI)){
						System.out.println("Reconozco pci");
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
		System.out.println("Error fact " + errDeFact);
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Nega() throws Exception{
		
		System.out.println("Nega");
		Atributos atrDeClausula = new Atributos();
		Atributos a = new Atributos();
		boolean errDeNega;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKAND))){
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKNOT)){
				atrDeClausula = Clausula();
				genOpMul(tk.getLexema());
				errDeNega = atrDeClausula.getErr() || ((!atrDeClausula.getTipo().equals("bool")) && (!atrDeClausula.getTipo().equals("")));
			}
			else{
				atrDeClausula = Clausula();
				errDeNega = atrDeClausula.getErr() || ((!atrDeClausula.getTipo().equals("bool")) && (!atrDeClausula.getTipo().equals("")));
			}
		}
		else{
			errDeNega = false;
			atrDeClausula.setTipo("");
		}
		System.out.println("Rama else");
		a.setErr(errDeNega);
		a.setTipo(atrDeClausula.getTipo());
		return a;
	}
	
	/**
	 * 
	 * @return Atributos devuelve los atributos obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Atributos Clausula() throws Exception{
		
		System.out.println("Clausula");
		Atributos a = new Atributos();
		boolean errDeClausula = true;
		Atributos atrDeExpC;
		Token tk;
		tk = lexico.getLookahead();
		//tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKNOT)){
			tk = lexico.lexer();
		}
		if (lexico.reconoce(Tipos.TKTRUE) || lexico.reconoce(Tipos.TKFALSE)){
			System.out.println("Es un true/false");
			errDeClausula = false;
			int cod;
			// Revisar el intValue de true y false
			if (tk.getLexema().equals("false")){
				cod = 0;
			}	
			else{
				cod = 1;
			}	
			codigo.genIns("apila", cod);
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				errDeClausula = !TS.existeID(tk.getLexema(), "bool");
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema(),"bool"));
			}
			else{
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExpC = ExpC();
					Token tk2 = lexico.getLookahead();
					System.out.println(tk2.muestraToken());
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
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		a.setErr(errDeClausula);
		a.setTipo("bool");
		return a;
	}
	
	/**
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
	 * 
	 * @param opDeOpComp
	 */
	public void genOpComp(String opDeOpComp){
		
		if (opDeOpComp == "<="){
			codigo.genIns("menor o igual");
		}	
		if (opDeOpComp == "<"){
				codigo.genIns("menor");
		}
		if (opDeOpComp == ">="){
			codigo.genIns("mayor o igual");
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
	 * 
	 *
	 */
	public void genOpAnd(){

		codigo.genIns("and");
		
	}

	/**
	 * 
	 *
	 */
	public void genOpOr(){

		codigo.genIns("or");
		
	}

	/**
	 * 
	 *
	 */
	public void genOpNot(){

		codigo.genIns("not");
		
	}
}