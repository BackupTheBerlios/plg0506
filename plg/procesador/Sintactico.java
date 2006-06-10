package procesador;

import java.io.RandomAccessFile;

import maquinaP.Codigo;
import tablaSimbolos.TablaSimbolos;
import tablaSimbolos.Par;
import tablaSimbolos.Atributos;

/**
 * La clase <B>Sintactico</B> analiza los tokens que han sido reconocidos por <B>Lexico</B>. 
 * <P>La clase Sintactico cuenta con los siguientes atributos:
 * <UL><LI><CODE>codigo:</CODE> Se encarga de almacenar el cdigo generado por las instrucciones del lenguaje. De tipo Codigo, clase
 * incluida en el paquete <B>maquinaP</B>.</LI>
 * <LI><CODE>lexico:</CODE> Analiza el fichero de entrada para reconocer tokens. De tipo Lexico.</LI>
 * <LI><CODE>TS:</CODE> Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos. De tipo TablaSimbolos.</LI>
 * <LI><CODE>dir:</CODE> Entero que marca la posicin de la pila con la que estamos trabajando. De tipo Entero.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Jonas Andradas, Leticia Garcma y Silvia Martmn
 *
 */

public class Sintactico{
	
	/*
	 * Atributos de la clase:
	 * 
	 * codigo: Se encarga de almacenar el codigo generado por las instrucciones del lenguaje.
	 * lexico: Analiza el fichero de entrada para reconocer tokens.
 	 * TS: Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
 	 * dir: Entero que marca la posicin de la pila con la que estamos trabajando.
	 */
	Codigo codigo;
	Lexico lexico;
	TablaSimbolos TS;
	int dir;
	int etq;
	
	private static int longApilaRet = 5;
	private static int longPrologo = 13;
	private static int longEpilogo = 12;
	private static int longInicioPaso = 3;
	private static int longFinPaso = 1;
	private static int longAccesoVar = 4;
	private static int longInicio = 4;
	/**
	 * Constructor que inicializa los atributos con los datos que recibe por parametro.
	 * 
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contine que contine el cdigo a analizar.
	 * @param T Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
	 * @param f String donde se guarga la ruta del fichero donde se va a guardar el codigo generado por el compilador.
	 * @throws Exception Propaga una excepcion que haya sucedido en otro lugar.
	 */
	public Sintactico(RandomAccessFile fuente, TablaSimbolos T, String f) throws Exception{
		codigo = new Codigo(f);
		lexico = new Lexico(fuente);		
		TS = T;
	}

	/**
	 * Accesor para el atributo de la clase sintactico. 
	 * @return Devuelve el codigo generado hasta ese momento
	 */
	public Codigo getCodigo() {
		return codigo;
	}

	/**
	 * Mutador para el atributo de la clase sintactico. 
	 * @param codigo Recibe el nuevo valor del codigo que se ha generado
	 */
	public void setCodigo(Codigo codigo) {
		this.codigo = codigo;
	}

	/**
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		if (Prog()){
			throw new Exception("El programa contiene errores de tipo");
		}
		TS.muestra();
		codigo.muestraCodigo();
	}

	/**
	 * Evalua el programa.  Primero lee las declaraciones de variables (identificadores), que se encuentran
	 * separados del conjunto de instrucciones "Is" mediante un "#".  Acto seguido, procesa cada instruccisn de Is.
	 * 
	 * @return errDeProg Devuelve un booleano que indica si existio un error al analizar el codigo del Programa. 
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */	
	public boolean Prog() throws Exception{
		/*
		 * 	Progs.err = Progs.err ??? Decs.pend ????????? 
		 */
		etq = 0;
		dir = 0;
		Par atrDeDecs = Decs();
		Par atrDeIs = Is();
		//System.out.println("El error de Decs es "+atrDeDecs.getProps().getTipo().equals("error"));
		//System.out.println("El error de Is es "+atrDeIs.getProps().getTipo().equals("error"));
		boolean errDeProg = atrDeDecs.getProps().getTipo().equals("error") || atrDeIs.getProps().getTipo().equals("error"); 
		return errDeProg;	
	}
	
	/**
	 * Recorre el conjunto de declaraciones (Dec) una por una.  Si tras una declaracisn encontramos
	 * un punto y coma (";"), procesamos otra mas.  Si en cambio lo que encontramos es una almohadilla
	 * ("#"), dejamos de leer Decs.
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par Decs() throws Exception{
	/*
	 * Decs ::= Decs; Dec
	 * Decso.pend = Decs1.pend ??? Dec.pend ???	(si Dec.props.clase = tipo entonces {Dec.id} si no ??? ) 
	 * 
	 * Decs ::= Dec
	 * Decs.pend = Dec.pend ??? (si Dec.props.clase = tipo entonces {Dec.id} 	si no ??????)
	 */
		Par a = new Par();
		Par atrDeDec = Dec();
		TS.agnadeID(atrDeDec.getId(), atrDeDec.getProps(), atrDeDec.getClase(), atrDeDec.getDir());
		if (atrDeDec.getClase().equals("var")){
			dir = dir + atrDeDec.getProps().getTam();
		}
		if (atrDeDec.getProps().getTipo().equals("error")){
			a.getProps().setTipo("error");
		}
		Token tk = lexico.getNextToken();
		if (tk.equals(new Token("#", Tipos.TKCUA))){
			lexico.lexer(); //consumimos #
			return a;
		}
		else{
			if (tk.equals(new Token(";", Tipos.TKPYCOMA))){
				lexico.lexer(); //consumimos ;
				Par atrDeDecs = Decs();
				if (atrDeDecs.getProps().getTipo().equals("error")){
					a.getProps().setTipo("error");
				}
				return a;
			}
			else{
				a.getProps().setTipo("error");
				return a;
			}
		}
    }
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Par Dec() throws Exception{
		/*
		 * Dec ::= DecTipo 
		 * Dec.pend = DecTipo.pend
		 * 
		 * Dec ::= DecVar
		 * Dec.pend = DecVar.pend
		 */
		Par a = new Par();
		Token tk = lexico.getNextToken();
		if (tk.equals(new Token("tipo", Tipos.TKTIPO))){
			Par atrDeDecTipo = DecTipo();
			a.setId(atrDeDecTipo.getId());
			a.setProps(atrDeDecTipo.getProps());
			a.setClase("tipo");
			a.setDir(0);
			return a;
		}
		else {
			Par atrDeDecVar = DecVar();
			a.setId(atrDeDecVar.getId());
			a.setProps(atrDeDecVar.getProps());
			a.setClase("var");
			a.setDir(dir);
			return a;
		}
	}
	
	public Par DecTipo() throws Exception{
		/*	
		 * Dec.pend = DecTipo.pend
		 */
		Par a = new Par();
		lexico.lexer(); // consumimos tipo
		Token tk = lexico.lexer();
		if (!lexico.reconoce(Tipos.TKIDEN)){
			throw new Exception ("ERROR: Necesitas un identificador");
		}
		a.setId(tk.getLexema());
		lexico.lexer(); //consumimos =
		if (!lexico.reconoce(Tipos.TKIG)){
			throw new Exception ("ERROR: Necesitas un =");
		}
		Par atrDeTipo = Tipo();
		a.getProps().setTam(atrDeTipo.getProps().getTam());
		a.getProps().setElems(atrDeTipo.getProps().getElems());
		a.getProps().setTipo("ref");
		a.getProps().setTbase(atrDeTipo.getProps());
		return a;
	}	
	/**
	 * Procesa una declaracisn de variable.  Cada declaracion Dec consta de dos elementos:  El tipo de la variable
	 * y su nombre, de la forma: 
	 * 			tipo identificador;
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par DecVar() throws Exception{
		/*
		 * DecVar.pend = Tipo.pend
		 */
		Par a = new Par();
		Par atrDeTipo = Tipo();
		Token tk = lexico.lexer();
		if (!lexico.reconoce(Tipos.TKIDEN)){
			throw new Exception ("ERROR: Necesitas un identificador");
		}
		a.setId(tk.getLexema());
		a.getProps().setElems(atrDeTipo.getProps().getElems());
		a.getProps().setTam(atrDeTipo.getProps().getTam());
		a.getProps().setTipo(atrDeTipo.getProps().getTipo());
		a.getProps().setTbase(atrDeTipo.getProps().getTbase());
		
		System.out.println("En " + a.getId() + "  Elems: " + a.getProps().getElems() + " y TAM: " + a.getProps().getTam());
		if (TS.existeID(tk.getLexema()) || TS.referenciaErronea(atrDeTipo)){
			a.getProps().setTipo("error");
		}
		return a;
	}	
	
	public Par Tipo() throws Exception{
		/* Tipo ::= pointer Tipo
		Tipoo.pend = Tipo1.pend
		 */
		Par a = new Par();
		Token tk = lexico.lexer(); // cosumimos int, bool, iden, array o pointer
		if (lexico.reconoce(Tipos.TKINT) || lexico.reconoce(Tipos.TKBOOL)){
			a.getProps().setTipo(tk.getLexema());
			a.getProps().setTam(1);
			a.getProps().setElems(1);
			a.getProps().setTbase(null);
		}
		else if(lexico.reconoce(Tipos.TKIDEN)){
			a.setId(tk.getLexema());
			a.getProps().setTipo("ref");
			a.getProps().setTam(TS.getProps(tk.getLexema()).getTam());
			a.getProps().setElems(TS.getProps(tk.getLexema()).getElems());
			a.getProps().setTbase(new Atributos(tk.getLexema(),"",0,1));
		}
		else if(lexico.reconoce(Tipos.TKARRAY)){
			lexico.lexer(); //consumimos [
			if (!lexico.reconoce(Tipos.TKCAP)){
				throw new Exception ("ERROR: Necesitas un [");
			}
			tk = lexico.lexer(); //consumimos num
			if (!lexico.reconoce(Tipos.TKNUM)){
				throw new Exception ("ERROR: Necesitas un numero");
			}
			int n = Integer.parseInt(tk.getLexema());
			lexico.lexer(); //consumimos ]
			if (!lexico.reconoce(Tipos.TKCCI)){
				throw new Exception ("ERROR: Necesitas un ]");
			}
			lexico.lexer(); //consumimos of
			if (!lexico.reconoce(Tipos.TKOF)){
				throw new Exception ("ERROR: Necesitas un of");
			}
			Par atrDeTipo = Tipo();
			a.getProps().setTipo("array");
			a.getProps().setElems(n);
			a.getProps().setTam(atrDeTipo.getProps().getTam() * n);
			a.getProps().setTbase(atrDeTipo.getProps());
		}
		else if(lexico.reconoce(Tipos.TKPUNT)){
			Par atrDeTipo = Tipo();
			if (a.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			}
			a.getProps().setTipo("pointer");
			a.getProps().setTbase(atrDeTipo.getProps());
			a.getProps().setElems(atrDeTipo.getProps().getElems());
			a.getProps().setTam(atrDeTipo.getProps().getTam() * a.getProps().getElems());
		}
		return a;
	}
	/**
	 * Recorre el conjunto de Instrucciones del programa.  Cada instruccion I se separa del conjunto de 
	 * instrucciones restantes (Is) mediante un punto y coma (";").  Si encontramos el token Fin de Fichero,
	 * hemos terminado de leer instrucciones. 
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par Is() throws Exception{
		Par atrDeIs;
		Par atrDeI; 
		Par a = new Par();
		atrDeI = I();
		if (lexico.reconoce(Tipos.TKFF)){
			a.getProps().setTipo(""); 
		}
		else{
			if (!lexico.reconoce(Tipos.TKPYCOMA)){
				throw new Exception("ERROR: Secuencia de Instrucciones Incorrecta. Cada instruccion ha de ir separada de la siguiente por un \";\"");
			}
			atrDeIs = Is();
			if (atrDeI.getProps().getTipo().equals("error") || atrDeIs.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			}
			else {
				a.getProps().setTipo("");
			}
			return a;	
		}
		return a;	
	}

	/**
	 * Procesa cada instruccion el conjunto de instrucciones del Programa.
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par I() throws Exception{
		Par atrDeIns;
		Token tk = lexico.lexer();
		System.out.println("En I leemos" + tk.getLexema());
		if (lexico.reconoce(Tipos.TKBEG)){
			atrDeIns = ICompuesta();
		}
		else{
			if (lexico.reconoce(Tipos.TKIF)){
				atrDeIns = IIf();
			}
			else if(lexico.reconoce(Tipos.TKWHL)){
					atrDeIns = IWhile();
			}
			else if (lexico.reconoce(Tipos.TKNEW)){
				atrDeIns = INew();
			}
			else if (lexico.reconoce(Tipos.TKDEL)){
				atrDeIns = IDel();
			}
			else{
					atrDeIns = IAsig();
			}	
				
		}
		return atrDeIns;
	}


	public Par ICompuesta() throws Exception{
		Par atrDeIns = IsOpc();
		if (!lexico.reconoce(Tipos.TKEND)){
			throw new Exception("ERROR: begin sin end.  El formato correcto es \"begin ... end;\".");
		}
		Token tk = lexico.lexer();
		System.out.println("En ICompuesta leemos" + tk.getLexema());
		if (! (lexico.reconoce(Tipos.TKPYCOMA))){
			throw new Exception("ERROR: end sin ;. El formato correcto es \"begin ... end;\".");
		}
		return atrDeIns;	
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Par IsOpc() throws Exception{
		Par atrDeIsOpc;
		Par atrDeI;
		Par a = new Par();
		boolean errDeIsOpc = false;
		atrDeI = I();
		if (lexico.reconoce(Tipos.TKFF)){
			throw new Exception("ERROR: begin sin end.  El formato correcto es \"begin ... end;\".");
		}
		if (lexico.reconoce(Tipos.TKPYCOMA)){
			Token tk;
			tk = lexico.getNextToken();
			if (tk.equals(new Token("end",Tipos.TKEND))){
				tk = lexico.lexer();
				System.out.println("En IsOpc leemos" + tk.getLexema());
				a = atrDeI;
				return a;
			}
			else{
				atrDeIsOpc = IsOpc();
				errDeIsOpc = (atrDeI.getProps().getTipo().equals("error") || atrDeIsOpc.getProps().getTipo().equals("error"));
			}	
		}
		else{
			throw new Exception("ERROR: Secuencia de Instrucciones Incorrecta. Todo begin debe llevar end.");
		}
		if (errDeIsOpc) {
			a.getProps().setTipo("error");
		}
		else {
			a = atrDeI;
		}
		return a;	
	}
	
	/**	
	 * IIf ::= {var etqs1, etqs2;
	 * 			ExpC();
	 *  		then 
	 * 			emite(ir-f);
	 * 			etqs1 <-- etq;
	 * 			etq <--etq +1;
	 * 			I();
	 * 			emite(ir-a);
	 * 			etqs2 <-- etq;
	 * 			parchea(etqs1,etq);
	 * 			etq <--etq +1;
	 * 			PElse();
	 * 			parchea(etqs2,etq); 
	 * 			}
	 */
	public Par IIf() throws Exception{
		Par a = new Par();
		Par atrDeExpC;
		Par atrDeI;
		Par atrDePElse;
		int etqs1;
		int etqs2;
		atrDeExpC = ExpC();
		System.out.println("EXPC: Tipo es " + atrDeExpC);
		if (!atrDeExpC.getProps().getTipo().equals("bool")){
			throw new Exception("ERROR: La condicion del If ha de ser una expresion booleana.");
		}
		if (lexico.reconoce(Tipos.TKTHN)){
			codigo.emite("ir-f");
			etqs1 = etq; 
			etq ++;
			atrDeI = I();
			codigo.emite("ir-a");
			etqs2 = etq;
			etq ++;
			codigo.parchea(etqs1,etq);
			atrDePElse = PElse();
			codigo.parchea(etqs2,etq);
			if ( atrDeI.getProps().getTipo().equals("error") || atrDePElse.getProps().getTipo().equals("error") || atrDeExpC.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			}
			else {
				a.getProps().setTipo(""); 
			}
		}
		else{
			a.getProps().setTipo("error");
		}	
		return a;	
	}
	
	public Par PElse() throws Exception{
		Par atrDeIns = new Par();
		if (lexico.reconoce(Tipos.TKPYCOMA)){
			Token tk;
			tk = lexico.getNextToken();
			if (!tk.equals(new Token ("else",Tipos.TKELS))){
				atrDeIns.getProps().setTipo("");
				return atrDeIns; //terminamos con exito
			}
			tk = lexico.lexer();
			System.out.println("En PElse leemos" + tk.getLexema());
			atrDeIns = I();
		}
		else{
			atrDeIns.getProps().setTipo("error");
		}	
		return atrDeIns;	
	}

	/**	
	 * IWhile ::= {var etqb, etqs;
	 *  		etqb <-- etq
	 * 			ExpC();
	 *  		do 
	 * 			emite(ir-f);
	 * 			etqs <-- etq;
	 * 			etq <--etq +1;
	 * 			I();
	 * 			emite(ir-a etqb);
	 * 			etq <--etq +1;
	 * 			parchea(etqs,etq); 
	 * 			}
	 */
	public Par IWhile() throws Exception{
		Par a = new Par();
		Par atrDeExpC;
		Par atrDeI;
		int etqb = etq;
		int etqs;
		atrDeExpC = ExpC();
		if (!atrDeExpC.getProps().getTipo().equals("bool")){
			throw new Exception("ERROR: La condicion del while ha de ser una expresion booleana.");
		}
		else {
			if (lexico.reconoce(Tipos.TKDO)){
				codigo.emite("ir-f");
				etqs = etq; 
				etq ++;
				atrDeI = I();
				codigo.emite("ir-a " + etqb);
				etq ++;
				codigo.parchea(etqs,etq);
			}
			else{
				a.getProps().setTipo("error");
				return a;
			}	
			if ( atrDeI.getProps().getTipo().equals("error") || atrDeExpC.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			}
			else {
				a.getProps().setTipo("");
			}
		}
		return a;	
	}
	
	
	public Par INew() throws Exception{
		
		Token tk = lexico.lexer(); //consumimo.s el iden
		System.out.println("En INew leemos" + tk.getLexema());
		
		Par a = new Par();
		a.setId(tk.getLexema());
		a.setProps(TS.getProps(tk.getLexema()));
		a.getProps().setTbase(Mem().getProps());
		if (TS.ref(a.getProps()).getTipo().equals("pointer")){
			if (a.getProps().getTipo().equals("ref")){
				codigo.genIns("new",TS.ref(a.getProps()).getTam(), dir);
			}
			else {
				codigo.genIns("new",a.getProps().getTam(), dir);
			}
			codigo.genIns("desapila-ind");
			etq += 2;
		}
		else {
			a.getProps().setTipo("error");
		}
		tk = lexico.lexer();
		System.out.println("En INew al final leemos" + tk.getLexema());
		
		return a;
	}
	
	public Par IDel() throws Exception{
		Token tk = lexico.lexer(); //consumimo.s el iden
		System.out.println("En IDel leemos" + tk.getLexema());
		
		Par a = new Par();
		a.setId(tk.getLexema());
		a.setProps(TS.getProps(tk.getLexema()));
		a.getProps().setTbase(Mem().getProps());
		codigo.genIns("apila-ind");
		etq ++;
		if (TS.ref(a.getProps()).getTipo().equals("pointer")){
			if (a.getProps().getTipo().equals("ref")){
				codigo.genIns("delete",TS.ref(a.getProps()).getTam());
			}
			else {
				codigo.genIns("delete",a.getProps().getTam());
			}
			etq ++;
		}
		else {
			a.getProps().setTipo("error");
		}
		tk = lexico.lexer();
		System.out.println("En IDel al final leemos" + tk.getLexema());
		
		return a;
	}
	
	/**
	 * Procesa una instruccisn de asignacisn, de la forma:
	 * 
	 * 		identificador := Expresisn.
	 * 
	 * Si hay un error en el formato de la instruccisn de asignacisn, o si 
	 * el tipo del identificador usado no coincide con el de la expresisn, 
	 * se lanza una Excepcisn.
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */

	public Par IAsig() throws Exception{
		
		Par  atrDeExpC = new Par();
		Par a = new Par();
		boolean errDeIAsig = false; 
		String lex = "";
		Token tk;
		tk = lexico.getLookahead();
		if (lexico.reconoce(Tipos.TKIDEN)){
			lex = tk.getLexema();
			a = Mem();
			tk = lexico.lexer(); //consumimos :=
			System.out.println("En IAsig leemos" + tk.getLexema());
			
			if (lexico.reconoce(Tipos.TKASIGN)){
				atrDeExpC = ExpC();
				boolean tiposIguales = atrDeExpC.getProps().getTipo().equals(TS.ref(a.getProps()).getTipo());
				errDeIAsig = (!(tiposIguales) || !(TS.existeID(lex)) || (atrDeExpC.getProps().getTipo().equals("error")));
				if (!(TS.existeID(lex))){
					errDeIAsig = true;
					throw new Exception("ERROR: Identificador no declarado. \nEl identificador ha de estar declarado en la seccion de Declaraciones antes de que se le pueda asignar un valor.");
				}
				else{
					if (TS.compatibles(a.getProps(), new Atributos("int","",0,1)) || TS.compatibles(a.getProps(), new Atributos("bool","",0,1))){
							codigo.genIns("desapila-ind");
							etq ++;		
					}
					else {
						codigo.genIns("mueve",a.getProps().getTam());
						etq ++;
					}
				}	
				a.setId(lex);
				a.getProps().setTipo(TS.getProps(lex).getTipo());
			}
		}
		else{
			if (! (lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKFF))){
				errDeIAsig = true;
				a.getProps().setTipo("error");
				throw new Exception("ERROR: Asignacisn Incorrecta. El formato correcto es \"identificador := Expresion;\".");
			} 
			else {
				errDeIAsig = false;
			}
		}
		if (errDeIAsig){
			a.getProps().setTipo("error");
		}
		return a;
	}

	
	/**
	 * Procesa y desarrolla una Expresisn de Comparacisn, ExpC, llamando a Exp y a RExpC, 
	 * para empezar a desarrollar el arbol sintactico que reconocera la Expresisn. 
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par ExpC() throws Exception{
		Par atrDeExp;
		Par atrDeRExpC;
		atrDeExp = Exp();
		Par a = new Par();
		atrDeRExpC = RExpC();
		
		//System.out.println("El tipo de Exp es: " + atrDeExp.getProps().getTipo());
		//System.out.println("El tipo de RExpC es: " + atrDeRExpC.getProps().getTipo());
		
		if ( atrDeExp.getProps().getTipo().equals(atrDeRExpC.getProps().getTipo())){
			if (atrDeExp.getProps().getTipo().equals("int"))
					a.getProps().setTipo("bool");
			else if (atrDeExp.getProps().getTipo().equals("bool"))
					a.getProps().setTipo("bool");
			else a.getProps().setTipo("error");
		}
		else{
			
			if (atrDeRExpC.getProps().getTipo().equals("")){
				a.getProps().setTipo(atrDeExp.getProps().getTipo());
			}else{
				a.getProps().setTipo("error");
			}
		}
		
		//System.out.println("El tipo en expc es :");
		//System.out.println(a.getProps().getTipo());
		
		return a;
	}
	
	/**
	 * Reconoce la segunda mitad de una Expresisn de Comparacisn de la forma:
	 * 
	 *       OpComp Exp RExpC | landa
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par RExpC() throws Exception{
		Par atrDeExp;
		Par atrDeRExpC;
		Par a = new Par();
		if (lexico.reconoce(Tipos.TKFF)){
			a.getProps().setTipo("error");
			return a;
		}
		if (!lexico.reconoce(Tipos.TKPYCOMA) || !lexico.reconoce(Tipos.TKEND) || 
				! lexico.reconoce(Tipos.TKTHN) || !lexico.reconoce(Tipos.TKDO) ) {
			if (lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKMAYIG) || lexico.reconoce(Tipos.TKMEN) || lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) || lexico.reconoce(Tipos.TKDIF)){
				Token tk = lexico.getLookahead();
				atrDeExp = Exp();
				genOpComp(tk.getLexema());				
				atrDeRExpC = RExpC();
				if (atrDeRExpC.getProps().getTipo().equals("")){
					a.getProps().setTipo(atrDeExp.getProps().getTipo());
				}
				else {
					if ( ( (atrDeExp.getProps().getTipo().equals(atrDeRExpC.getProps().getTipo())) && (atrDeExp.getProps().getTipo().equals("bool")) ) || atrDeRExpC.getProps().getTipo().equals("")){
						a.getProps().setTipo(atrDeExp.getProps().getTipo());
					}
					else{
						a.getProps().setTipo("error");
					}
				}
				return a;
			} 
		} 
		a.getProps().setTipo("");
		return a;
	}
	
	/**
	 * Reconoce una Expresisn, tanto aritmitica como lsgica (booleana).
	 *  
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par Exp() throws Exception{
		Par atrDeTerm;
		Par atrDeRExp;
		Par a = new Par();
		atrDeTerm = Term();
		atrDeRExp = RExp();
		
		//System.out.println("El tipo de Term es: " + atrDeTerm.getProps().getTipo());
		//System.out.println("El tipo de RExp es: " + atrDeRExp.getProps().getTipo());
		
		if ( atrDeTerm.getProps().getTipo().equals(atrDeRExp.getProps().getTipo())){
			if (atrDeTerm.getProps().getTipo().equals("int"))
					a.getProps().setTipo("int");
			else if (atrDeTerm.getProps().getTipo().equals("bool"))
					a.getProps().setTipo("bool");
			else a.getProps().setTipo("error");
		}
		else{
			if (atrDeRExp.getProps().getTipo().equals("")){
				a.getProps().setTipo(atrDeTerm.getProps().getTipo());
			}else{
				a.getProps().setTipo("error");
			}
		}
		
		//System.out.println("El tipo en exp es :");
		//System.out.println(a.getProps().getTipo());
		
		return a;
	}
	
	/**
	 * Reconoce la segunda mitad (con el operador) de la descomposicisn de una Expresisn booleana o aritmitica.
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par RExp() throws Exception{
		Par atrDeTerm = new Par();
		Par atrDeRExp;
		Par a = new Par();
		if (lexico.reconoce(Tipos.TKFF)){
			a.getProps().setTipo("error");
			return a;
		}
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKEND) || 
				lexico.reconoce(Tipos.TKTHN) || lexico.reconoce(Tipos.TKDO) ) ){
			Token tk;
			tk = lexico.getLookahead(); 
		
			boolean numerico = lexico.reconoce(Tipos.TKSUMA) || lexico.reconoce(Tipos.TKRESTA);
			boolean booleano = lexico.reconoce(Tipos.TKOR); 
		
			atrDeTerm = Term();
			if (numerico){
				genOpAd(tk.getLexema());
			} else if (booleano) {
				genOpAd("or");    
			}
			atrDeRExp = RExp();
			
			if ( (atrDeTerm.getProps().getTipo().equals("error")) || (atrDeRExp.getProps().getTipo().equals("error")) ){
				a.getProps().setTipo("error");
			} else {
				if (atrDeRExp.getProps().getTipo().equals("")){
					a.getProps().setTipo(atrDeTerm.getProps().getTipo());
				}
				else {
					if (numerico){
						if ( atrDeRExp.getProps().getTipo().equals("int") && atrDeRExp.getProps().getTipo().equals(atrDeTerm.getProps().getTipo()) ){
							a.getProps().setTipo("int");
						} else {
							a.getProps().setTipo("error");
						}
					} 
					else if (booleano){
						if ( atrDeTerm.getProps().getTipo().equals("bool") && atrDeTerm.getProps().getTipo().equals(atrDeRExp.getProps().getTipo()) ){
							a.getProps().setTipo("bool");
						} else {
							a.getProps().setTipo("error");
						}
					}
				}
			}
		} 
		else {
			a.getProps().setTipo("");
		}
		return a;
	}
	
	/**
	 * Reconoce un Tirmino, compuesto de un Factor y un Tirmino Recursivo:
	 *  
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par Term() throws Exception{
		Par atrDeFact;
		Par atrDeRTerm;
		Par a = new Par();
		atrDeFact = Fact();
		atrDeRTerm = RTerm();
		
		//System.out.println("El tipo de Fact es: " + atrDeFact.getProps().getTipo());
		//System.out.println("El tipo de RTerm es: " + atrDeRTerm.getProps().getTipo());
		
		if ( atrDeFact.getProps().getTipo().compareTo(atrDeRTerm.getProps().getTipo()) == 0){
			if (atrDeFact.getProps().getTipo().compareTo("int") == 0)
					a.getProps().setTipo("int");
			else if (atrDeFact.getProps().getTipo().compareTo("bool") == 0)
					a.getProps().setTipo("bool");
			else a.getProps().setTipo("error");
		}
		else{
			if (atrDeRTerm.getProps().getTipo().equals("")){
				a.getProps().setTipo(atrDeFact.getProps().getTipo());
			}else{
				a.getProps().setTipo("error");
			}
		}
		
		//System.out.println("El tipo en term es :");
		//System.out.println(a.getProps().getTipo());
		
		return a;
	}
	
	/**
	 * Reconoce un Tirmino Aritmitico recursivo.
	 *  
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par RTerm() throws Exception{
		Par atrDeFact;
		Par atrDeRTerm;
		Par a = new Par();
		Token tk = lexico.lexer();
		System.out.println("En RTerm leemos" + tk.getLexema());
		
		if (lexico.reconoce(Tipos.TKFF)){
			a.getProps().setTipo("error");
			return a;
		}
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKSUMA) ||
				lexico.reconoce(Tipos.TKRESTA) || lexico.reconoce(Tipos.TKEND) || 
				lexico.reconoce(Tipos.TKTHN) || lexico.reconoce(Tipos.TKDO) ) ){
			

			boolean numerico = lexico.reconoce(Tipos.TKMULT) || lexico.reconoce(Tipos.TKDIV);
			boolean booleano = lexico.reconoce(Tipos.TKAND); 
		
			atrDeFact = Fact();
			
			if (numerico){
				genOpMul(tk.getLexema());
			} else if (booleano) {
				genOpMul("and");    // O deber?amos cambiarlo por un genOpAd(tk.getLexema()) tambi?n??  CONSULTAR
			}
			atrDeRTerm = RTerm();
			
			if ( atrDeFact.getProps().getTipo().equals("error") || atrDeRTerm.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			} else {
				if (atrDeRTerm.getProps().getTipo().equals("")){
					a.getProps().setTipo(atrDeFact.getProps().getTipo());
				}
				else {
					if (numerico){ 
						if (atrDeFact.getProps().getTipo().equals(atrDeRTerm.getProps().getTipo()) && atrDeFact.getProps().getTipo().equals("int") ){
							a.getProps().setTipo(atrDeFact.getProps().getTipo()); // int
						} else {
							a.getProps().setTipo("error");
						}
					} else if (booleano) {
						if (atrDeFact.getProps().getTipo().equals(atrDeRTerm.getProps().getTipo()) && atrDeFact.getProps().getTipo().equals("bool") ){
							a.getProps().setTipo(atrDeFact.getProps().getTipo()); // bool
						} else {
							a.getProps().setTipo("error");
						}
					}
				}
			}
		}
		else{
			a.getProps().setTipo("");
		}
		return a;
	}

	
	/**
	 * Reconoce un Factor.  Un Factor puede ser un entero, un identificador o una Expresisn aritmitica
	 * entre parintesis.
	 * 
	 * @return Par devuelve los Par obtenidos en el analisis del Programa.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public Par Fact() throws Exception{
		Par a = new Par();
		Par atrDeExpC;
		Par atrDeFact;
		Token tk;
		tk = lexico.lexer();
		System.out.println("En Fact leemos" + tk.getLexema());
		
		if (lexico.reconoce(Tipos.TKNUM)){
			a.getProps().setTipo("int");
			codigo. genIns("apila", Integer.parseInt(tk.getLexema()) );
			etq ++;
		} 
		else if (lexico.reconoce(Tipos.TKTRUE) || lexico.reconoce(Tipos.TKFALSE)){
			a.getProps().setTipo("bool");
			int cod;
			if (tk.getLexema().equals("false"))
				cod = 0;
			else
				cod = 1;
			codigo.genIns("apila", cod);
			etq ++;
		} else if (lexico.reconoce(Tipos.TKNOT) || lexico.reconoce(Tipos.TKRESTA)) {  // es un OpUn
			boolean numerico = lexico.reconoce(Tipos.TKRESTA); // numerico != true ==> booleano = true
			atrDeFact = Fact();
			a = atrDeFact;
			if (numerico){
				genOpNega();
				if (atrDeFact.getProps().getTipo().equals("int")){
					a.getProps().setTipo(atrDeFact.getProps().getTipo());
				}else{
					a.getProps().setTipo("error");
				}
			} else {
				genOpNot();
				if (atrDeFact.getProps().getTipo().equals("bool")){
					a.getProps().setTipo(atrDeFact.getProps().getTipo());
				}else{
					a.getProps().setTipo("error");
				}
			}
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				a = Mem();
				codigo.genIns("apila-ind");
				etq ++;
			}
			/*else if (lexico.reconoce(Tipos.TKMEMDIR)){
				tk = lexico.lexer();
				System.out.println("En Memdir leemos" + tk.getLexema());
				
				codigo.genIns("apila",TS.getDir(tk.getLexema()));
				etq ++;
			}*/
			else {
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExpC = ExpC();
					if (lexico.reconoce(Tipos.TKPCI)){
						a.getProps().setTipo(atrDeExpC.getProps().getTipo());
					}
					else{
						a.getProps().setTipo("error");
					}
				}
				else{
					a.getProps().setTipo("error");
				}
			}
		}
		
		//System.out.println("El tipo en fact es :");
		//System.out.println(a.getProps().getTipo());
		
		return a;
	}
	
	/** 
	 * Resuelve un Fact en el que intervienen identificadores, tanto si es un identificador s?lo, como si es un puntero o un array.
	 * @return Par El objeto Par con los atributos obtenidos de resolver el identificador y sus referencias.
	 * @throws Exception En caso de producirse una excepci?n, ?sta se propaga.
	 */
	public Par Mem() throws Exception{
		//System.out.println("Pasamos por Mem");
		
		Atributos atrDeRMem = null;
		Par a = new Par();
		
		Token tk = lexico.getLookahead();

		if (!lexico.reconoce(Tipos.TKIDEN)){
			a = null;
			throw new Exception ("ERROR: Deberiamos haber leido un iden.");
		}
		a.setProps(new Atributos(TS.getProps(tk.getLexema())));
		//System.out.println("El tipo en mem es :");
		//System.out.println(a.getProps().getTipo());
		a.setId(tk.getLexema());
		a.setClase(TS.getClase(tk.getLexema()));
		a.setDir(TS.getDir(tk.getLexema()));
		codigo.genIns("apila",TS.getDir(tk.getLexema()));
		etq ++;
		
		atrDeRMem = RMem(a.getProps()/*.getTbase()*/);
		if (atrDeRMem != null){
			if (atrDeRMem.getTipo().equals("")){
				a.setProps(TS.getProps(tk.getLexema()));
			}
			else a.setProps(atrDeRMem);
		}
		return a;
	}
	
	public Atributos RMem(Atributos a) throws Exception{
		Atributos atrDeRMem = null;
		
		Token tk = lexico.getNextToken();
		
		if (tk.getCategoriaLexica() == Tipos.TKPUNT){
			tk = lexico.lexer();
			System.out.println("En RMem leemos" + tk.getLexema());
			
			// Resolvemos el puntero:
			codigo.genIns("apila-ind");
			etq ++;
			if (a.getTbase().getTbase() == null)
				return a.getTbase();
			else
				atrDeRMem = RMem(a.getTbase());
		} 
		else if (tk.getCategoriaLexica() == Tipos.TKCAP){
			tk = lexico.lexer();
			System.out.println("En RMem [ leemos" + tk.getLexema());
			int n;
			if (lexico.getNextToken().getCategoriaLexica()==Tipos.TKNUM){
				tk = lexico.lexer(); //consumo la n
				System.out.println("En RMem n leemos" + tk.getLexema());
		
				n = Integer.parseInt(tk.getLexema());
				codigo.genIns("apila",n);
				etq ++;
			}
			else{
				Par atrDeExp = Exp();
				if (atrDeExp.getProps().getTipo().equals("int")){
					n = Integer.parseInt(atrDeExp.getId());
				}
				else{
					throw new Exception("ERROR: El indice del array no es un entero");
				}
			}
			if ( n >= a.getElems()){
				throw new Exception("ERROR: array overflow");
			}
			tk = lexico.lexer(); // consumo ]
			System.out.println("En RMem ] leemos" + tk.getLexema());
			
			if (lexico.reconoce(Tipos.TKCCI)){
				codigo.genIns("apila",a.getTbase().getTam());
				codigo.genIns("multiplica");
				codigo.genIns("suma");
				etq += 3;

				if (a.getTbase().getTbase() == null)
					return a.getTbase();
				else
					atrDeRMem = RMem(a.getTbase());
			}
			else{
				a.getTbase().setTipo("error"); 
			}
		}
		else {
			//System.out.println("Pasamos por RMem - Landa");
			if (a.getTbase() == null){
				System.out.println("ES NULL EL A");
				return a;
			}
			else{
				return a.getTbase();
			}
		}
		
		// Comprobamos que los tipos son iguales.
		if (!(a.getTbase().equals(atrDeRMem)) || a.getTipo().equals("error")){
			a.setTipo("error");
			throw new Exception("ERROR: RMEM Error en los tipos. /// EXCEPTION PARA QUITAR!!!!");
		}
		
		return atrDeRMem.getTbase();
	}
	
	/**
	 * Genera el cdigo de la operacin de suma, resta o el or.
	 * 
	 * @param opDeOpAd
	 */
	public void genOpAd(String opDeOpAd){
		
		if (opDeOpAd == "+")
			codigo.genIns("suma");
		else if (opDeOpAd.equals("-"))
			codigo.genIns("resta");
		else
			codigo.genIns("or");
		etq ++;
	}
	
	/**
	 * Genera el csdigo de la operacisn de multiplicacisn, divisisn o and.
	 * 
	 * @param opDeOpMul
	 */
	public void genOpMul(String opDeOpMul){
		
		if (opDeOpMul == "*")
			codigo.genIns("multiplica");
		else if (opDeOpMul.equals("/"))
			codigo.genIns("divide");
		else 
			codigo.genIns("and");
		etq ++;
	}
	
	/**
	 * Genera el csdigo de la operacisn de comparacisn
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
		etq ++;
	}

	/**
	 * Genera el csdigo de la negacisn booleana "not".
	 *
	 */
	public void genOpNot(){
		codigo.genIns("not");
		etq ++;
	}
	
	public void genOpNega(){
		codigo.genIns("neg");
		etq ++;
	}
	
	private String sacaTipo(Atributos atr){
		if (atr.getTbase() == null){
			return atr.getTipo();
		}
		else {
			return sacaTipo(atr.getTbase());
		}
	}
}