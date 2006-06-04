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
		System.out.println("llamo a prog");
		if (Prog()){
			System.out.println("vuelvo de prog");
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
		System.out.println("llamo a decs");
		Par atrDeDecs = Decs();
		System.out.println("vuelvo de decs");
		System.out.println("llamo a is");
		Par atrDeIs = Is();
		System.out.println("vuelvo de is");
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
		System.out.println("llamo a dec");
		Par atrDeDec = Dec();
		System.out.println("vuevlo de dec");
		TS.agnadeID(atrDeDec.getId(), atrDeDec.getProps(), atrDeDec.getClase(), atrDeDec.getDir());
		TS.muestra();
		//System.out.println(atrDeDec.getId());
		//System.out.println(atrDeDec.getProps().getTipo());
		dir = dir + atrDeDec.getProps().getTam();
		System.out.println(dir);
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
				System.out.println("");
				System.out.println("llamo a decs");
				Par atrDeDecs = Decs();
				System.out.println("vuelvo de decs");
				//TS.agnadeID(atrDeDecs.getId(), atrDeDecs.getProps(), atrDeDecs.getClase(), atrDeDecs.getDir());
				//TS.muestra();
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
			System.out.println("llamo a dectipo");
			Par atrDeDecTipo = DecTipo();
			System.out.println("vuelvo de dectipo");
			a.setId(atrDeDecTipo.getId());
			a.setProps(atrDeDecTipo.getProps());
			a.setClase("tipo");
			System.out.println("A la salida de dec props");
			return a;
		}
		else {
			System.out.println("llamo a decvar");
			Par atrDeDecVar = DecVar();
			System.out.println("vuelvo de decvar");
			a.setId(atrDeDecVar.getId());
			System.out.println("en dec");
			//System.out.println(a.getProps().toString());
			a.setProps(atrDeDecVar.getProps());
			//System.out.println(a.getId());
			//System.out.println(a.getProps().getTipo());
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
		System.out.println("llamo a tipo");
		Par atrDeTipo = Tipo();
		System.out.println("vuelvo de tipo");
		a.setId(tk.getLexema());
		a.setClase("tipo");
		a.setDir(0);
		a.getProps().setTipo("ref");
		a.getProps().setTbase(new Atributos());
		System.out.println(atrDeTipo.toString());
		a.getProps().setTbase(atrDeTipo.getProps());
		System.out.println(a.getProps().getTbase().toString());
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
		System.out.println("llamo a tipo");
		Par atrDeTipo = Tipo();
		System.out.println("vuelvo de tipo");
		Token tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (!lexico.reconoce(Tipos.TKIDEN)){
			throw new Exception ("ERROR: Necesitas un identificador");
		}
		a.setId(tk.getLexema());
		a.getProps().setElems(atrDeTipo.getProps().getElems());
		a.getProps().setTam(atrDeTipo.getProps().getTam());
		a.getProps().setTipo(atrDeTipo.getId());
		System.out.println("en decvar");
		//System.out.println(a.getId());
		//System.out.println(a.getProps().getTipo());
		//System.out.println(a.getProps().toString());
		System.out.println("El iden esta duplicado");
		System.out.println(a.getId());
		System.out.println(TS.existeID(a.getId()));
		System.out.println("la referencia est?? mal");
		if (TS.existeID(tk.getLexema()) || TS.referenciaErronea(atrDeTipo)){
			a.getProps().setTipo("error");
			System.out.println(a.getProps().toString());
		}
		return a;
	}	
	
	public Par Tipo() throws Exception{
		/* Tipo ::= pointer Tipo
		Tipoo.pend = Tipo1.pend
		 */
		Par a = new Par();
		Token tk = lexico.lexer(); // cosumimos int, bool, iden, array o pointer
		System.out.print("El tipo es ");
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKINT) || lexico.reconoce(Tipos.TKBOOL)){
			a.getProps().setTipo(tk.getLexema());
			a.getProps().setTam(1);
		}
		else if(lexico.reconoce(Tipos.TKIDEN)){
			a.getProps().setTipo("ref");
			a.setId(tk.getLexema());
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
			System.out.println("llamo a tipo");
			Par atrDeTipo = Tipo();
			System.out.println("vuelvo de tipo");
			String t = atrDeTipo.getProps().getTipo();
			System.out.println("t");
			System.out.println(t);
			int tam = atrDeTipo.getProps().getTam() * n;
			System.out.println("tam");
			a.getProps().setTipo("array");
			System.out.println("n");
			a.getProps().setElems(n);
			System.out.println("tbase");
			a.getProps().setTbase(atrDeTipo.getProps());
			System.out.println("tam");
			a.getProps().setTam(tam);
			System.out.println("el array es un tipo");
		}
		else if(lexico.reconoce(Tipos.TKPUNT)){
			Par atrDeTipo = Tipo();
			if (a.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			}
			a.getProps().setTipo("pointer");
			a.getProps().getTbase().setTipo(atrDeTipo.getProps().getTipo());
			a.getProps().setTam(1);
			System.out.println("el puntero es un tipo");
		}
		System.out.println("A la salida de tipo props");
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
		boolean errDeIs = false;
		atrDeI = I();
		if (lexico.reconoce(Tipos.TKFF)){
			// Simplemente ponemos el tipo a vac?o (no se me ocurr?a otra forma)
			//errDeIs = false;
			a.getProps().setTipo("");
		}
		else{
			if (!lexico.reconoce(Tipos.TKPYCOMA)){
				a.getProps().setTipo("error");
				throw new Exception("ERROR: Secuencia de Instrucciones Incorrecta. Cada instruccion ha de ir separada de la siguiente por un \";\"");
			}
			atrDeIs = Is();
			if (atrDeI.getProps().getTipo().equals("error") || atrDeIs.getProps().getTipo().equals("error")){
				a.getProps().setTipo("error");
			}
			else {
				// Qu? props habr?a que meter... Las de I???
				// O igualamos todo el par?  No s? c?mo juntar ambos pares.
				a.setProps(atrDeIs.getProps());
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
		/*ahora puede ser ICompuesta, IIf
		 * las ecs son:
		 * - {I.err= ICompuesta.err; }
		 * - {I.err= Iif.err;}*/
		Par a = new Par();
		Par atrDeIns = null;
		lexico.lexer();
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
		a = atrDeIns;
		return a;
	}

	/*
	 * Aqu?? hay q a??adir ICompuesta:
	 * ICompuesta ::= begin IsOpc end 
	 * {ICompuesta.err=IsOpc.err; }
	 */

	public Par ICompuesta() throws Exception{
		Par a = new Par();
		Par atrDeIns = IsOpc();
		if (!lexico.reconoce(Tipos.TKEND)){
			a.getProps().setTipo("error");
			throw new Exception("ERROR: begin sin end.  El formato correcto es \"begin ... end;\".");
		}
		lexico.lexer();
		if (! (lexico.reconoce(Tipos.TKPYCOMA))){
			a.getProps().setTipo("error");
			throw new Exception("ERROR: end sin ;. El formato correcto es \"begin ... end;\".");
		}
		else {
			a = atrDeIns;;
		}	
		return a;	
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
			errDeIsOpc = true;	
		}
		else{
			if (lexico.reconoce(Tipos.TKPYCOMA)){
				Token tk;
				tk = lexico.getNextToken();
				if (tk.equals(new Token("end",Tipos.TKEND))){
					tk = lexico.lexer();
					a = atrDeI;
					return a;
				}
				else{
					atrDeIsOpc = IsOpc();
					errDeIsOpc = (atrDeI.getProps().getTipo().equals("error") || atrDeIsOpc.getProps().getTipo().equals("error"));
				}	
			}
			else{
				errDeIsOpc=true;
				a.getProps().setTipo("error"); // Como lanzamos excepcion, creo que si no lo pongo aqu?, no se rellena.
				throw new Exception("ERROR: Secuencia de Instrucciones Incorrecta. Todo begin debe llevar end.");
			}
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
		if (!atrDeExpC.getProps().getTipo().equals("bool")){
			throw new Exception("ERROR: La condicion del If ha de ser una expresion booleana.");
		}
		else {
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
					a = atrDeI; // por que de atrDeI???  o atrDePElse??  O como uno ambos?... :(
				}
			}
			else{
				a.getProps().setTipo("error");
				return a;
			}	
			
		}
		return a;	
	}
	
	/*
	 * Pelse ::= else I() 
	 * PElse ::= ?? {}
	 */
	
	public Par PElse() throws Exception{
		Par a = new Par();
		Par atrDeIns;
		if (lexico.reconoce(Tipos.TKPYCOMA)){
			Token tk;
			tk = lexico.getNextToken();
			if (!tk.equals(new Token ("else",Tipos.TKELS))){
				a.getProps().setTipo("");
				return a;
			}
			lexico.lexer(); //consumimos else
			atrDeIns = I();
			a = atrDeIns;	
		}
		else{
			a.getProps().setTipo("error");
		}	
		return a;	
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
				a = atrDeI; // Es lo "importante" y donde se van a modificar cosas...
			}
		}
		return a;	
	}
	
	
	public Par INew() throws Exception{
		
		Par a = Mem();
		if (a.getProps().getTipo().equals("ref")){
			codigo.genIns("new",TS.getProps(a.getProps().getTbase().getTipo()).getTam());
		}
		else {
			codigo.genIns("new",a.getProps().getTam());
		}
		codigo.genIns("desapila-ind");
		etq += 2;
		
		return a;
	}
	
	public Par IDel() throws Exception{
		Par a = Mem();
		if (a.getProps().getTipo().equals("ref")){
			codigo.genIns("delete",TS.getProps(a.getProps().getTbase().getTipo()).getTam());
		}
		else {
			codigo.genIns("delete",a.getProps().getTam());
		}
		etq ++;
		
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
		
		/*IAsig ::= Mem ':=' Exp
		IAsig.cod = si compatible(Mem.tipo,<t:num>,Exp.tsh) ?
				compatible(Mem.tipo,<t:bool>,Exp.tsh)
					Mem.cod || Exp.cod || desapila-ind
				si no 
					Mem.cod || Exp.cod || mueve(Mem.tipo.tam)
		Mem.etqh = IAsig.etqh
		Exp.etqh = Mem.etq
		IAsig.etq = Exp.etq + 2*/
		
		Par  atrDeExpC = new Par();
		Par  atrDeExp = new Par();
		Par a = new Par();
		boolean errDeIAsig; 
		String lex = "";
		Token tk;
		tk = lexico.getLookahead();
		if (lexico.reconoce(Tipos.TKIDEN)){
			lex = tk.getLexema();
			a = Mem();
			//System.out.println(lexico.getLookahead().muestraToken());
			//System.out.println((TS.getTipo(lexico.getLookahead().getLexema())));
			tk = lexico.lexer();
			if (lexico.reconoce(Tipos.TKASIGN)){
				atrDeExpC = ExpC();
				errDeIAsig = (!(atrDeExpC.getProps().getTipo().equals(a.getProps().getTipo())) || !(TS.existeID(lex)) || (atrDeExpC.getProps().getTipo().equals("error")));
				//System.out.println("Estoy en IAsig - 1 -"+errDeIAsig);
				//System.out.println(atrDeExpC.getTipo());
				if (!(TS.existeID(lex))){
					errDeIAsig = true;
					a.getProps().setTipo("error"); // Como antes, creo que la Excepcion me saca del m?todo, y quisiera dejar el error marcado.
					throw new Exception("ERROR: Identificador no declarado. \nEl identificador ha de estar declarado en la seccion de Declaraciones antes de que se le pueda asignar un valor.");
				}
				else{
					
					if (TS.compatibles(a.getProps(), new Atributos("int","",0,1))){
						if ( (a.getProps().getTipo().equals("int") || a.getProps().getTipo().equals("bool"))){
							codigo.genIns("desapila-ind",TS.getDir(lex));
							etq ++;		
						}
						else {
							codigo.genIns("mueve",a.getProps().getTam());
							etq ++;
						}
					}
					
					
					a.setId(lex);
					a.getProps().setTipo(TS.getProps(lex).getTipo());
					// O metemos en 'a' directamente a.setprops( TS.getProps(lex) )  tal cual?  O que otros atributos rellenamos si no?
				}
			}
			else{
				if (lexico.reconoce(Tipos.TKCAP)){
					tk = lexico.lexer();
					int n;
					if (lexico.reconoce(Tipos.TKNUM)){
						n= Integer.parseInt(tk.getLexema());
					}
					else{
						atrDeExp = Exp();
						if (atrDeExp.getProps().getTipo().equals("int")){
							n = Integer.parseInt(atrDeExp.getId());
						}	
						else{
							errDeIAsig = true;
							a.getProps().setTipo("error");
							throw new Exception("ERROR: Asignacisn Incorrecta. El formato correcto es \"identificador := Expresion;\".");
						}
					}
					if (n > TS.getProps(lex).getTam()){
						errDeIAsig = true;
						a.getProps().setTipo("error");
						throw new Exception("ERROR: array overflow");
					}
					tk = lexico.lexer();
					if (lexico.reconoce(Tipos.TKCCI)){
						tk = lexico.lexer();
						if (lexico.reconoce(Tipos.TKASIGN)){
							atrDeExpC = ExpC();
							//System.out.println(lexico.getLookahead().muestraToken());
							errDeIAsig = (!(atrDeExpC.getProps().getTipo().equals(TS.getProps(lex).getTbase())) || !(TS.existeID(lex)) || (atrDeExpC.getProps().getTipo().equals("error")) );
							//System.out.println(TS.getTBase(TS.getTipo(lex)));
							//System.out.println("Estoy en IAsig - 2 -"+errDeIAsig);
							//System.out.println(atrDeExpC.getTipo());
							if (!(TS.existeID(lex))){
								errDeIAsig = true;
								a.getProps().setTipo("error");
								throw new Exception("ERROR: Identificador no declarado. \nEl identificador ha de estar declarado en la seccion de Declaraciones antes de que se le pueda asignar un valor.");
							}
							else{
								codigo.genIns("desapila-dir",TS.getDir(lex)+(n)); // +(n-1)
								etq ++;
								
								a.setId(lex);
								a.getProps().setTipo(atrDeExpC.getProps().getTipo());
							}
						}
						else{
							errDeIAsig = true;
							a.getProps().setTipo("error");
							throw new Exception("ERROR: Asignacin incorrecta a una posicion del array. El formato correcto es \"identificador[num] := Expresion;\".");
						}
					}
					else{
						errDeIAsig = true;
						a.getProps().setTipo("error");
						throw new Exception("ERROR: Asignacin incorrecta a una posicion del array. El formato correcto es \"identificador[num] := Expresion;\".");
					}
				}
				else{
					errDeIAsig = true;
					a.getProps().setTipo("error");
					throw new Exception("ERROR: Asignacin incorrecta a una posicion del array. El formato correcto es \"identificador[num] := Expresion;\".");
				}
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
		if ( atrDeExp.getProps().getTipo().equals(atrDeRExpC.getProps().getTipo())){
			if (atrDeExp.getProps().getTipo().equals("int"))
					a.getProps().setTipo("int");
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
		if (lexico.reconoce(Tipos.TKFF) || lexico.reconoce(Tipos.TKTHN) || lexico.reconoce(Tipos.TKDO)){
			a.getProps().setTipo("error");
			return a;
		}
		if (!lexico.reconoce(Tipos.TKPYCOMA) || !lexico.reconoce(Tipos.TKEND)){
			if (lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKMAYIG) || lexico.reconoce(Tipos.TKMEN) || lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) || lexico.reconoce(Tipos.TKDIF)){
				Token tk = lexico.getLookahead();
				atrDeExp = Exp();
				genOpComp(tk.getLexema());				
				atrDeRExpC = RExpC();
				if ( ( (atrDeExp.getProps().getTipo().equals(atrDeRExpC.getProps().getTipo())) && (atrDeExp.getProps().getTipo().equals("bool")) ) || atrDeRExpC.getProps().getTipo().equals("")){
					a.getProps().setTipo(atrDeExp.getProps().getTipo());
				}
				else{
					a.getProps().setTipo("error");
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
		if (lexico.reconoce(Tipos.TKFF) || lexico.reconoce(Tipos.TKTHN) || lexico.reconoce(Tipos.TKDO)){
			a.getProps().setTipo("error");
			return a;
		}
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKEND))){
			Token tk;
			tk = lexico.getLookahead(); 
		
			boolean numerico = lexico.reconoce(Tipos.TKSUMA) || lexico.reconoce(Tipos.TKRESTA);
			boolean booleano = lexico.reconoce(Tipos.TKOR); 
		
			atrDeTerm = Term();
			
			if (numerico){
				genOpAd(tk.getLexema());
			} else if (booleano) {
				genOpAd("or");    // O deber?amos cambiarlo por un genOpAd(tk.getLexema()) tambi?n??  CONSULTAR
			}
			atrDeRExp = RExp();
			
			if ( (atrDeTerm.getProps().getTipo().compareTo("error") == 0) || (atrDeRExp.getProps().getTipo().compareTo("error") == 0) ){
				a.getProps().setTipo("error");
			} else {
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
		Token tk;
		tk = lexico.lexer();
		if (lexico.reconoce(Tipos.TKFF) || lexico.reconoce(Tipos.TKTHN) || lexico.reconoce(Tipos.TKDO)){
			a.getProps().setTipo("error");
			return a;
		}
		if (!(lexico.reconoce(Tipos.TKPYCOMA) || lexico.reconoce(Tipos.TKMEN) ||
				lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) ||
				lexico.reconoce(Tipos.TKDIF) || lexico.reconoce(Tipos.TKMAYIG) ||
				lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKPAP) ||
				lexico.reconoce(Tipos.TKPCI) || lexico.reconoce(Tipos.TKSUMA) ||
				lexico.reconoce(Tipos.TKRESTA) || lexico.reconoce(Tipos.TKEND))){
			

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
		Par atrDeExp;
		Par atrDeFact;
		Token tk;
		tk = lexico.lexer();
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
			}
			else if (lexico.reconoce(Tipos.TKMEMDIR)){
				tk = lexico.lexer();
				codigo.genIns("apila",TS.getDir(tk.getLexema()));
				etq ++;
			}
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
		return a;
	}
	
	public Par Mem() throws Exception{
		
		Par a = new Par();
		
		Token tk = lexico.getLookahead();    //lexico.lexer();
		Token tkAux = lexico.getNextToken();
		System.out.println("EN MEM!!! Deberia leer un Iden, p. ej., y leo: " + tk.getLexema());
		if (lexico.reconoce(Tipos.TKIDEN)){ // Esta comprobacion es superflua, pero la dejamos "por si acaso" :)
			String iden = tk.getLexema();
			
			codigo.genIns("apila-dir", TS.getDir(tk.getLexema()));
			etq++;
			
			a.getProps().setTam(TS.getProps(iden).getTbase().getTam());
			a.getProps().setTipo(TS.getProps(iden).getTbase().getTipo());
			
			while (tkAux.getCategoriaLexica() == Tipos.TKIDEN || tkAux.equals(new Token("[",Tipos.TKCAP)) || 
					tkAux.equals(new Token("pointer",Tipos.TKPUNT))){
				
				tk = lexico.lexer();
				
				
				if (lexico.reconoce(Tipos.TKCAP)){
					System.out.println("En Mem, reconozco que es un array y lo trato.");
					int n;
					if (lexico.getNextToken().getCategoriaLexica()==Tipos.TKNUM){
						tk = lexico.lexer();
						n = Integer.parseInt(tk.getLexema());
					}
					else{
						Par atrDeExp = Exp();
						if (atrDeExp.getProps().getTipo().equals("int")){
							n = Integer.parseInt(atrDeExp.getId());
						}
						else{
							a.getProps().setTipo("error");
							throw new Exception("ERROR: El indice del array no es un entero");
						}
					}	
					if ( n > TS.getProps(iden).getTam()){
						a.getProps().setTipo("error");
						throw new Exception("ERROR: array overflow");
					}
					tk = lexico.lexer();
					if (lexico.reconoce(Tipos.TKCCI)){
						if (TS.existeID(iden) && ((TS.getProps(iden).getTipo()).equals("array"))){
							a.getProps().setTipo(a.getProps().getTbase().getTipo());
							a.getProps().setTam(a.getProps().getTbase().getTam());
							codigo.genIns("apila",a.getProps().getTam());
							codigo.genIns("multiplica");
							codigo.genIns("suma");
							etq += 3;
						}
						a.getProps().getTbase().setTipo("error");
					}
					else{
						a.getProps().setTipo("error"); 
					}
				}
				else if (lexico.reconoce(Tipos.TKPUNT)){
					tk = lexico.lexer();
					
					a.getProps().setTipo(a.getProps().getTbase().getTipo());
					a.getProps().setTam(a.getProps().getTbase().getTam());
					
					codigo.genIns("apila-ind");
					etq ++;
				}
				else { 
					System.out.println("EXPLOTO");
					throw new Exception("BUM");
				}
				
				a.getProps().setTipo(TS.getProps(tk.getLexema()).getTipo());
				System.out.println("Solo por curiosidad:  dirID = " + TS.dirID(tk.getLexema()) + "  y getDir = " + TS.getDir(tk.getLexema()));
				
				tkAux = lexico.getNextToken();
			}
			
		}
		else {  
			System.out.println("No es un MEM. Error.");
			a.getProps().setTipo("error");
			throw new Exception("ERROR: Expresion derecha mal construida");
		}
		return a;
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
}