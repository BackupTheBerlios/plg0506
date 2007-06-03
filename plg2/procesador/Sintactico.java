package procesador;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
//import java.util.Enumeration;
import java.util.Hashtable;

import tablaSimbolos.*;

/**
 * La clase <B>Sintactico</B> analiza los tokens que han sido reconocidos por <B>Lexico</B>. 
 * <P>La clase Sintactico cuenta con los siguientes atributos:
 * <UL><LI><CODE>codigo:</CODE> Se encarga de almacenar el cdigo generado por las instrucciones del lenguaje. De tipo Codigo, clase
 * incluida en el paquete <B>maquinaP</B>.</LI>
 * <LI><CODE>lexico:</CODE> Analiza el fichero de entrada para reconocer tokens. De tipo Lexico.</LI>
 * <LI><CODE>TS:</CODE> Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos. De tipo TablaSimbolos.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
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
	Lexico lexico;
	tablaSimbolos TS;
	Codigo codigo;
	int etq;
	
	/**
	 * Constructor que inicializa los atributos con los datos que recibe por parametro.
	 * 
	 * @param f String donde se guarda la ruta del fichero donde se va a guardar el codigo generado por el compilador.
	 * @throws Exception Propaga una excepcion que haya sucedido en otro lugar.
	 */
	public Sintactico(File f) throws Exception{
		lexico = new Lexico(f);		
		codigo = new Codigo(f);
	}
	
	public Codigo getCodigo() {
		return codigo;
	}

	/**
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		codigo.inicializaCodigo();
		boolean tipoProg = Prog();
		if (tipoProg){
			throw new Exception("El programa contiene errores de tipo");
		}
	}

	/**
	 * Evalua el programa.  Primero lee las declaraciones de variables (identificadores), que se encuentran
	 * separados del conjunto de instrucciones "Is" mediante un "#".  Acto seguido, procesa cada instruccisn de Is.
	 * 
	 * @return errDeProg Devuelve un booleano que indica si existio un error al analizar el codigo del Programa. 
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */	
	private boolean Prog() throws Exception{
		System.out.println("Prog");
		boolean errProg = true;
		boolean errDecs = Decs();
		etq = 0;
		if (lexico.reconoce(CategoriaLexica.TKLLAP)){
			lexico.lexer(); //consumo {
			boolean errIs = Is();
			if (lexico.reconoce(CategoriaLexica.TKLLCI)){
				lexico.lexer(); // consumo }
				errProg = errDecs || errIs;
				codigo.genIns("stop");
			}
		}
		return errProg;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Decs() throws Exception{
		//System.out.println("Decs");
		TS = new tablaSimbolos();
		Atributo atrDec = Dec();
		TS.addID(atrDec.getId(),atrDec.getTipo(),atrDec.getClase());
		Atributo atrRDecs = RDecs(atrDec);
		if (atrRDecs.getTipo().equals("error")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RDecs(Atributo heredado)throws Exception{
		//System.out.println("RDecs");
		Atributo atrRDecs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RDecs ::= λ
			return heredado;
		}
		lexico.lexer(); //consumo ;
		Atributo atrDec = Dec();
		if (TS.existeID(atrDec.getId())){
			throw new Exception("El identificador " + atrDec.getId() + " esta duplicado");
		}
		if (heredado.getTipo().equals("error")){
			atrRDecs.getTipo().setTipo("error");
			return atrRDecs;
		}
		TS.addID(atrDec.getId(),atrDec.getTipo(),atrDec.getClase());
		atrRDecs = RDecs(atrDec);
		return atrRDecs;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo Dec() throws Exception{
		Atributo a = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKTYPE)){
			lexico.lexer(); //consumo TYPE
			Atributo b = DecTipo();
			a.setId(b.getId());
			a.setClase("tipo");
			a.setTipo(b.getTipo());
			return a;
		}
		else if (!lexico.reconoce(CategoriaLexica.TKTYPE)){ //Variables{
			Atributo b = DecVar();
			a.setId(b.getId());
			a.setTipo(b.getTipo());
			a.setClase("var");
			return a;
		}
		throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());		
	}
	
	public Atributo DecTipo() throws Exception{
		Atributo atrTipo = Tipo();
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());
		}
		Token tk = lexico.lexer(); //consumo iden
		boolean a = referenciaErronea(atrTipo.getTipo());
		boolean b = TS.existeID(tk.getLexema());
		if (!a && !b){
			atrTipo.setId(tk.getLexema());
			return atrTipo;
		}
		else
			throw new Exception("Variable ya declarada en linea " + lexico.getLinea());
	}
	
	public Vector Campos() throws Exception{
		Vector campos = new Vector();
		Atributo atrCampo = Campo();
//		int despla = 0;
		campos = RCampos(campos,atrCampo);
		return campos;
	}
	
	public Atributo Campo() throws Exception {
		Atributo a = new Atributo();
		Atributo b = Tipo();
		if (referenciaErronea(b.getTipo()))
				throw new Exception("Referencia erronea en linea " + lexico.getLinea());
		a.setTipo(b.getTipo());
		lexico.lexer(); // consumo el tipo
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());
		}
		Token tk = lexico.lexer(); //consumo iden
		a.setId(tk.getLexema());
		return a; 
	}
	
	public Vector RCampos(Vector v, Atributo campo)throws Exception{
//		campo.setDesplazamiento(despla);
//		System.out.println("Campo : " + campo.getId() + " : " + campo.getDesplazamiento());
		if (!v.contains(campo)){
			v.add(campo);
		}
		else{
			throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());
		}
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			lexico.lexer(); //consumo ;
			campo = Campo();
			//despla++;
			RCampos(v,campo);
			return v;
		}
		else if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			return v;
		}
		throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());
	}
	
	
	public Atributo DecVar() throws Exception{
		Atributo atrTipo = Tipo();
		Atributo atrDec = new Atributo();
		Token tk = lexico.lexer(); //consumo el tipo
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());
		}
		tk = lexico.lexer(); //consumo el iden
		atrDec.setTipo(atrTipo.getTipo());
		atrDec.setId(tk.getLexema());
		return atrDec;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	private Atributo Tipo() throws Exception{
		Atributo atrTipo = new Atributo();
		ExpresionTipo et = new ExpresionTipo();
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			et.setTipo("int");
			atrTipo.setTipo(et);
			et.setTam(1);
			return atrTipo;
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			et.setTipo("bool");
			atrTipo.setTipo(et);
			et.setTam(1);
			return atrTipo;
		}
		else if (lexico.reconoce(CategoriaLexica.TKREG)){
			lexico.lexer();//consumo reg
			if (!lexico.reconoce(CategoriaLexica.TKLLAP)){
				throw new Exception("ERROR: Necesitas una {");
			}
			lexico.lexer();//consumo {
			Vector campos = new Vector();
			campos = Campos();
			et.setParams(campos);
			et.setTam(campos.size());
			et.setTipo("reg");
			atrTipo.setTipo(et);
			if (!lexico.reconoce(CategoriaLexica.TKLLCI)){
				throw new Exception ("ERROR: Necesitas una }");
			}
			System.out.println(lexico.getNextToken().getLexema());
			lexico.lexer(); //consumo }
			System.out.println(lexico.getNextToken().getLexema());
			return atrTipo;
		}
		else if (lexico.reconoce(CategoriaLexica.TKARRAY)){
			lexico.lexer(); //consumo array
			Atributo b = Tipo();
			if (referenciaErronea(b.getTipo())){
					throw new Exception("Referencia erronea en linea " + lexico.getLinea());
			}
			et.setTbase(b.getTipo());
			lexico.lexer(); // consumo el tipo
			if (!lexico.reconoce(CategoriaLexica.TKCORAP)){
				throw new Exception("ERROR: Necesitas un [");
			}
			lexico.lexer(); //consumo '['
			if (!lexico.reconoce(CategoriaLexica.TKINT)){
				throw new Exception("ERROR: Necesitas un entero para definir el valor del array");
			}
			Token tk = lexico.lexer(); //consumo el entero
			et.setElems(new Integer(Integer.parseInt(tk.getLexema())).intValue());
			et.setTipo("array");
			et.setTam(b.getTipo().getTam() * et.getTam());
			if (!lexico.reconoce(CategoriaLexica.TKCORCI)){
				throw new Exception("ERROR: Necesitas un ']'");
			}
			lexico.lexer(); //consumo ']'			
			atrTipo.setTipo(et);
			return atrTipo;			
		}
		else if (lexico.reconoce(CategoriaLexica.TKIDEN)){
			Token tk = lexico.getNextToken();
			if (TS.existeID(tk.getLexema())){
				propiedades p = (propiedades)TS.getTabla().get(tk.getLexema());
				if (p.getClase().equals("tipo")){
					et.setTipo("ref");
					et.setId(tk.getLexema());
					atrTipo.setTipo(et);
					return atrTipo;
				}
				else
					throw new Exception("Tipo incorrecto en linea " + lexico.getLinea());
			}
		}
		throw new Exception("Tipo incorrecto en linea " + lexico.getLinea());
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Is() throws Exception{
		System.out.println("Is");
		Atributo atrI = I(); 
		Atributo atrRIs = RIs(atrI);
		if (atrRIs.getTipo().getTipo().equals("error")){
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RIs(Atributo heredado) throws Exception{
		//System.out.println("RIs");
		Atributo atrRIs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RIs ::= λ
			return heredado;
		}
		lexico.lexer(); //consumo ;
		Atributo atrI = I();
		atrRIs = RIs(atrI);
		if (heredado.getTipo().getTipo().equals("error") || 
				atrRIs.getTipo().getTipo().equals("error")){
			atrRIs.getTipo().setTipo("error");
			return atrRIs;
		}
		return atrRIs;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo I() throws Exception{
		System.out.println("I");
		Atributo atrI = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKLLAP)){
			atrI = ICompuesta();
		}
		else if (lexico.reconoce(CategoriaLexica.TKIF)){
			System.out.println("If");
			atrI = IIf();
		}
		else if (lexico.reconoce(CategoriaLexica.TKWHILE)){
			System.out.println("While");
			atrI = IWhile();
		}
		else{
			atrI = IAsig();
		}
		return atrI;
	}
	/**
	 *ICompuesta (out err)::=  
	 *reconoce ({)
	 *IsOpc (out err1)
	 *reconoce (})
	 *(err  ← err1)
	 */

	private Atributo ICompuesta() throws Exception{
		System.out.println("ICompuesta");
		lexico.lexer(); //consumimos {
		Atributo atrIsOpc = IsOpc();
		if (! lexico.reconoce(CategoriaLexica.TKLLCI)){
			throw new Exception("Error falta '}' en linea: " + lexico.getLinea());
		}
		lexico.lexer(); //consumimos }
		atrIsOpc.getTipo().setTipo("");
		return atrIsOpc;
	}
	/**
	 * IsOpc (out err)::= λ
	 *(err  ← false)	
	 *IsOpc (out err)::= Is (out err1)
	 *(err  ← err1)
	 */
	private Atributo IsOpc() throws Exception{
		System.out.println("IsOpc");
		Atributo atrIs = new Atributo();
		atrIs.setTipo(new ExpresionTipo());
		// IsOpc ::= λ 
		if (! lexico.reconoce(CategoriaLexica.TKLLCI)){
			if (Is()){
				atrIs.getTipo().setTipo("error");
			}
		}
		atrIs.getTipo().setTipo("");
		return atrIs;
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo IIf() throws Exception{
		System.out.println("IIf");
		Atributo atrIf = new Atributo();
		atrIf.setTipo(new ExpresionTipo());
		lexico.lexer();//Consumimos el IF
		if (!lexico.reconoce(CategoriaLexica.TKPAP)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el parentesis '('");
		}
		lexico.lexer(); //Consumimos el '('
		Atributo atrExpOr = ExpOr();
		if (!atrExpOr.getTipo().getTipo().equals("bool")){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " La condicion de la instrucción IF ha de ser de tipo bool");
		}
		if (!lexico.reconoce(CategoriaLexica.TKPCI)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el parentesis ')'");
		}
		lexico.lexer();//Consumimos el ')'
		if (!lexico.reconoce(CategoriaLexica.TKTHEN)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el then de la instrucción IF");
		}
		lexico.lexer();//Consumimos THEN
		codigo.emite("ir-f"); 
		int etqs1=etq;
		etq++;
		Atributo atrI = I();
		codigo.emite("ir-a");
		int etqs2 = etq;
		etq++;
		codigo.parchea(etqs1, etq);
		Atributo atrPElse = PElse();
		codigo.parchea(etqs2,etq);
		if ((atrI.getTipo().getTipo().equals("error")) || 
				(atrPElse.getTipo().getTipo().equals("error"))){
			atrIf.getTipo().setTipo("error");
		}
		else{
			atrIf.getTipo().setTipo("");
		}
		return atrIf;
	}
		
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo PElse() throws Exception{
		System.out.println("PElse");
		Atributo atrElse = new Atributo();
		atrElse.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			return atrElse;
		}
		if (!lexico.reconoce(CategoriaLexica.TKELSE)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el else de la instrucción IF o un ';'");
		}
		lexico.lexer();//Consumimos ELSE
		Atributo atrI = I();
		if (atrI.getTipo().equals("error")){
			atrElse.getTipo().setTipo("error");
		}
		else
			atrElse.getTipo().setTipo("");
		return atrElse;
	}
	
	/**
	 * 
	 *  
	 * @return
	 * @throws Exception
	 */
	private Atributo IWhile() throws Exception{
		Atributo atrWhile = new Atributo();
		atrWhile.setTipo(new ExpresionTipo());
		lexico.lexer(); //consumimos while
		if (!lexico.reconoce(CategoriaLexica.TKPAP)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el parentesis '('");
		}
		lexico.lexer(); //Consumimos el '('
		int etqAux = etq;
		Atributo atrExpOr = ExpOr();
		if (!atrExpOr.getTipo().getTipo().equals("bool")){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " La condicion de la instrucción IF ha de ser de tipo bool");
		}
		if (!lexico.reconoce(CategoriaLexica.TKPCI)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el parentesis ')'");
		}
		lexico.lexer();//Consumimos el ')'
		if (!lexico.reconoce(CategoriaLexica.TKDO)){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " Falta el 'do' en la expresión");
		}
		lexico.lexer(); //Consumimos el do
		codigo.emite("ir-f");
		int etqAux1 = etq;
		etq++;
		Atributo atrI = I();
		codigo.emite("ir-a " + etqAux);
		etq++;
		codigo.parchea(etqAux1, etq);
		if(atrI.getTipo().equals("error"))
			atrWhile.getTipo().setTipo("error");
		else
			atrWhile.getTipo().setTipo("");
		return atrWhile;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo IAsig() throws Exception{
		System.out.println("IAsig");
		ExpresionTipo et = new ExpresionTipo();
		Atributo atrIAsig = new Atributo();
		atrIAsig.setTipo(et);	
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			atrIAsig.getTipo().setTipo("error");
			return atrIAsig;
		}
//		Token tk = lexico.getNextToken();
//		atrIAsig = Mem(new Atributo());
		atrIAsig = Mem();
		if (!lexico.reconoce(CategoriaLexica.TKASIGN)){
			throw new Exception("Error en la asignacion en linea: " + lexico.getLinea());
	    }
		lexico.lexer();//consumo =
//		int dir = ((propiedades)TS.getTabla().get(tk.getLexema())).getDir();
		Atributo atrExpOr= ExpOr();
		if ((atrIAsig.getTipo().getTipo().equals("error")) ||
				!compatibles(atrIAsig.getTipo(),atrExpOr.getTipo()) ||
				(atrExpOr.getTipo().getTipo().equals("error")))
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		et = new ExpresionTipo("int");
		ExpresionTipo et2 = new ExpresionTipo("bool");		
		if (compatibles(atrIAsig.getTipo(),et) ||
				compatibles(atrIAsig.getTipo(),et2)){
			codigo.genIns("desapila_ind");
		}else{
			codigo.genIns("mueve", atrIAsig.getTipo().getTam());
		}
		etq++;
		return atrIAsig;
	}

	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpOr() throws Exception{
		//System.out.println("ExpOr");
		Atributo atrExpAnd = ExpAnd();
		Atributo atrRExpOr = RExpOr(atrExpAnd);
		return atrRExpOr;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RExpOr(Atributo heredado) throws Exception{
		//System.out.println("RExpOr");
		Atributo atrRExpOr = new Atributo();
		atrRExpOr.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= λ
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= λ
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKOR)){
			return heredado;
		}
		String op = genOpOr((lexico.lexer()).getLexema());
		Atributo atrExpAnd = ExpAnd();
		if (!atrExpAnd.getTipo().getTipo().equals("bool") || 
				!heredado.getTipo().getTipo().equals("bool")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrExpAnd.getTipo().setTipo("bool");
			if (op != ""){
				codigo.genIns(op);
				etq++;
			}
			else{
				throw new Exception("Error en linea " + lexico.getLinea() + ": operador no válido");
			}
		}
		atrRExpOr = RExpOr(atrExpAnd);
		return atrRExpOr;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpAnd() throws Exception{
		//System.out.println("ExpAnd");
		Atributo atrExpRel = ExpRel();
		Atributo atrRExpAnd = RExpAnd(atrExpRel);
		return atrRExpAnd;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RExpAnd(Atributo heredado) throws Exception{
		//System.out.println("RExpAnd");
		Atributo atrRExpAnd = new Atributo();
		atrRExpAnd.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= λ
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= λ
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKAND)){
			return heredado;
		}
		String op = genOpAnd((lexico.lexer()).getLexema());
		Atributo atrExpRel = ExpRel();
		if (!atrExpRel.getTipo().getTipo().equals("bool") || !heredado.getTipo().getTipo().equals("bool")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrExpRel.getTipo().setTipo("bool");
			if (op != ""){
				codigo.genIns(op);
				etq++;
			}
			else{
				throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
			}
		}
		atrRExpAnd = RExpOr(atrExpRel);
		return atrRExpAnd;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpRel() throws Exception{
		//System.out.println("ExpRel");
		Atributo atrExpAd = ExpAd();
		Atributo atrRExpRel = RExpRel(atrExpAd);
		return atrRExpRel;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RExpRel(Atributo heredado) throws Exception{
		//System.out.println("RExpRel");
		Atributo atrRExpRel = new Atributo();
		atrRExpRel.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= λ
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= λ
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKIG) && !lexico.reconoce(CategoriaLexica.TKDIF)
				&& !lexico.reconoce(CategoriaLexica.TKMAY)&& !lexico.reconoce(CategoriaLexica.TKMAYIG)
				&& !lexico.reconoce(CategoriaLexica.TKMEN)&& !lexico.reconoce(CategoriaLexica.TKMENIG)){
			return heredado;
		}
		String op = genOpRel((lexico.lexer()).getLexema());
		
		atrRExpRel = ExpAd();
		if (heredado.getTipo().getTipo().equals("error") || 
				atrRExpRel.getTipo().getTipo().equals("error")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else if (!heredado.getTipo().getTipo().equals(atrRExpRel.getTipo().getTipo())) {
			atrRExpRel.getTipo().setTipo("error");
		}
		else{
			atrRExpRel.getTipo().setTipo("bool");
			if (op != ""){
				codigo.genIns(op);
				etq++;
			}
			else{
				throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
			}
		}
		return atrRExpRel;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpAd() throws Exception{
		//System.out.println("ExpAd");
		Atributo atrExpMul = ExpMul();
		Atributo atrExpAd = RExpAd(atrExpMul);
		return atrExpAd;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RExpAd(Atributo heredado) throws Exception{
		//System.out.println("RExpAd");
		Atributo atrRExpAd = new Atributo();
		atrRExpAd.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpAd ::= λ
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= λ
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKSUMA) && !lexico.reconoce(CategoriaLexica.TKRESTA)){
			return heredado;
		}
		String op = genOpAd((lexico.lexer()).getLexema());
		Atributo atrExpMul = ExpMul();
		if (!atrExpMul.getTipo().getTipo().equals("int") || 
				!heredado.getTipo().getTipo().equals("int")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrRExpAd.getTipo().setTipo("int");
			if (op != ""){
				codigo.genIns(op);
				etq++;
			}
			else{
				throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
			}
		}
		atrRExpAd = RExpAd(atrRExpAd);
		return atrRExpAd;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpMul() throws Exception{
		//System.out.println("ExpMul");
		Atributo atrFact = Fact();
		Atributo atrRExpMul = RExpMul(atrFact);
		return atrRExpMul;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo RExpMul(Atributo heredado) throws Exception{
		//System.out.println("RExpMul");
		Atributo atrRExpMul = new Atributo();
		atrRExpMul.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpMul ::= λ
			return heredado;
		}
		if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= λ
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKDIV) && !lexico.reconoce(CategoriaLexica.TKMOD)&& !lexico.reconoce(CategoriaLexica.TKMULT)){
			return heredado;
		}
		String op = genOpMul((lexico.lexer()).getLexema());
		Atributo atrFact = Fact();
		if ((atrFact.getTipo().getTipo()).equals("int") && 
				heredado.getTipo().getTipo().equals("int")){
			atrRExpMul.getTipo().setTipo("int");
			if (op != ""){
				codigo.genIns(op);
				etq++;
			}
			else{
				throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
			}
		}
		else if ((atrFact.getTipo().getTipo()).equals("bool") && 
				heredado.getTipo().getTipo().equals("bool")){
			atrRExpMul.getTipo().setTipo("bool");
		}
		else{
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		atrRExpMul = RExpMul(atrRExpMul);
		return atrRExpMul;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo Fact() throws Exception{
		//System.out.println("Fact");
		Atributo atrFact = new Atributo();
		atrFact.setTipo(new ExpresionTipo());
		if (lexico.reconoce(CategoriaLexica.TKNUM)){
			atrFact.getTipo().setTipo("int");
			lexico.lexer(); //Cosumimos el entero
			codigo.genIns("apila", Integer.parseInt(lexico.getLookahead().getLexema()));
			etq++;
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKTRUE)) {
			atrFact.getTipo().setTipo("bool");
			codigo.genIns("apila",1);
			etq++;			
			lexico.lexer(); //Cosumimos 'true'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKFALSE)) {
			atrFact.getTipo().setTipo("bool");
			codigo.genIns("apila",0);
			etq++;
			lexico.lexer(); //Cosumimos 'false'
			return atrFact;
		}
		// Fact ::= (ExpOr)
		if (lexico.reconoce(CategoriaLexica.TKPAP)) {
			lexico.lexer(); //Cosumimos '('
			atrFact = ExpOr();
			if (!lexico.reconoce(CategoriaLexica.TKPCI)){
				throw new Exception(" Error de parentizacion, en linea: " + lexico.getLinea());
			}
			lexico.lexer(); //Cosumimos ')'
			return atrFact;
		}
		// Fact ::= Mem
		if (lexico.reconoce(CategoriaLexica.TKIDEN)) {
			atrFact = Mem();
			//atrFact = Mem(new Atributo());
			if (!atrFact.getTipo().getTipo().equals("error")){
				Atributo a = new Atributo(new ExpresionTipo("int"));
				Atributo b = new Atributo(new ExpresionTipo("bool"));
				if (compatibles(atrFact.getTipo(),a.getTipo()) || 
						compatibles(atrFact.getTipo(),b.getTipo())){
					int dir = ((propiedades)TS.getTabla().get(atrFact.getId())).getDir();
					codigo.genIns("apila_ind");
					etq++;
				}
			}
			else{
				throw new Exception("Error de tipos en linea: " + lexico.getLinea());				
			}
			return atrFact;
		}
		//Fact ::= opUnarioLog Fact 
		if (lexico.reconoce(CategoriaLexica.TKNOT)){
			String op = genOpUnarioLog(lexico.lexer().getLexema()); //Consumimos operador unario logico
			atrFact = Fact();
			codigo.genIns(op);
			etq++;
			if (!atrFact.getTipo().getTipo().equals("bool")){
					atrFact.getTipo().setTipo("error");
					throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}	
			else{
				atrFact.getTipo().setTipo("bool");
			}
			return atrFact;
		}
		//Fact ::= opUnarioArit Fact 
		if ( (lexico.reconoce(CategoriaLexica.TKSUMA)) || (lexico.reconoce(CategoriaLexica.TKRESTA))){
			String op = genOpUnarioArit(lexico.lexer().getLexema()); //Consumimos operador unario aritmetico
			atrFact = Fact();
			codigo.genIns(op);
			etq++;
			if (!atrFact.getTipo().getTipo().equals("int")){
					atrFact.getTipo().setTipo("error");
					throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
			else{
				atrFact.getTipo().setTipo("int");
			}
			return atrFact;
		}
		
		atrFact.getTipo().setTipo("error");
		return atrFact;
	}

	private Atributo Mem()throws Exception{
		Atributo atrMem = new Atributo();
		Token tk = lexico.getNextToken();
		atrMem.setId(tk.getLexema());
//		int desp = 0;
		int dir = ((propiedades)TS.getTabla().get(tk.getLexema())).getDir();
		ExpresionTipo tipo = ((ExpresionTipo)(((propiedades)TS.getTabla().get(tk.getLexema())).getTipo()));
		tipo = ref(tipo);
		atrMem.setTipo(tipo);
		Atributo atrRMem2 = null;
		if (dir >= 0){
			codigo.genIns("apila",dir);
			etq = etq + 1;
			lexico.lexer(); //consumo el iden
			atrRMem2 = RMem(atrMem);
			if (atrRMem2.getTipo() != null){
				tipo = atrRMem2.getTipo();
				//desp = atrRMem2.getDesplazamiento();
			}
		}
		else{
			throw new Exception(" Variable no declarada, en linea: " + lexico.getLinea());
		}
		atrMem.setTipo(tipo);
		//atrMem.setDesplazamiento(desp);
		return atrMem;
	}

	private Atributo RMem (Atributo atr) throws Exception{
		Atributo atrRMem = null;
		ExpresionTipo etRMem = new ExpresionTipo(); 
		if (lexico.reconoce(CategoriaLexica.TKPUNTO)){
			lexico.lexer(); //consumo el .
			//if (atr.getTipo().getTipo().equals("reg")){
				if (lexico.reconoce(CategoriaLexica.TKIDEN)){
					Token tk = lexico.lexer();
					int desp = indiceCampo(atr.getId(),tk.getLexema());
					if (desp != -1){
						etRMem = ((Parametros)atr.getTipo().getParams().elementAt(desp)).getTipo();
						if (etRMem.getTipo().equals("ref")){
							atr.setTipo(((propiedades)TS.getTabla().get(etRMem.getId())).getTipo());
							//atr.setDesplazamiento(atr.getDesplazamiento() + desp);
							atr.setId(etRMem.getId());
						}
						else{
							atr.setTipo(etRMem);
							//atr.setDesplazamiento(desp);
						}
						codigo.genIns("apila",desp);
						codigo.genIns("suma");
						etq += 2;
						atrRMem = RMem(atr);
						if (atrRMem.getTipo() != null){
							atr.setTipo(atrRMem.getTipo());
							//atr.setDesplazamiento(atrRMem.getDesplazamiento());
						}
					}
					else{
						throw new Exception(" Campo no existente en el registro, en linea: " + lexico.getLinea());	
					}
				}
		}
		return atr;
	}
	
	/**
	 * 
	 * @param opDeOpAnd
	 * @throws Exception 
	 */
	private String genOpAnd(String opDeOpAnd){
		if (opDeOpAnd == "&&")
			return "and";
		else
			return "";
	}
	
	/**
	 * 
	 * @param opDeOpOr
	 */
	private String genOpOr(String opDeOpOr){
		if (opDeOpOr == "||")
			return ("or");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpAd
	 */
	private String genOpAd(String opDeOpAd){
		if (opDeOpAd == "+")
			return ("suma");
		else if (opDeOpAd.equals("-"))
			return("resta");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpMul
	 */
	private String genOpMul(String opDeOpMul){
		if (opDeOpMul == "*")
			return("multiplica");
		else if (opDeOpMul.equals("/")){
			return("divide");}
		else if (opDeOpMul.equals("%"))
			return("modulo");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 * @return
	 */
	private String genOpUnarioArit(String opDeOpUn){
		if (opDeOpUn == "+")
			return("positivo");
		else if (opDeOpUn.equals("-"))
			return("menos");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 * @return
	 */
	private String genOpUnarioLog(String opDeOpUn){
		if (opDeOpUn.equals("!"))
			return("not");
		else return "";
	}
		
	/**
	 * 
	 * @param opDeOpComp
	 * @return
	 */
	private String genOpRel(String opDeOpComp){
		
		if (opDeOpComp == "<="){
			return("menorIgual");
		}	
		else if (opDeOpComp == "<"){
				return("menor");
		}
		else if (opDeOpComp == ">="){
			return("mayorIgual");
		}	
		else if (opDeOpComp == ">"){
				return("mayor");
		}
		else if (opDeOpComp == "=="){
			return("igual");
		}	
		else if (opDeOpComp == "!="){
				return("distinto");
		}
		else return "";
	}
	
	public int indiceCampo(String id,String campo){
		Hashtable tabla = TS.getTabla();
		propiedades p = (propiedades) tabla.get(id);
		ExpresionTipo et = p.getTipo();
		Vector v = et.getParams();
		int despla = -1;
		if (TS.hayCampo(v, campo)){
			int i = 0;
			while (i < v.size()){
				Atributo a = ((Atributo)v.elementAt(i));
				if (a.getId().equals(campo)){
					return i;
				}
				i++;
			}
			despla = i;
		}
		return despla;
	}
	
	private ExpresionTipo ref(ExpresionTipo t) throws Exception{
		if (t.getTipo().equals("ref")) {
			if (TS.existeID(t.getId())) {
				t = (ExpresionTipo)((propiedades)((TS.getTabla()).get(t.getId()))).getTipo();
				return ref(t);
			} else{
				throw new Exception (" Campo de registro mal referenciado, en linea: " + lexico.getLinea());
			}
		}
		else
			return t;
	}
	
	private boolean compatibles(ExpresionTipo t1, ExpresionTipo t2){
		Vector visitados = new Vector();
		return compatibles2(t1, t2, visitados);
	}
	
	private boolean compatibles2 (ExpresionTipo t1, ExpresionTipo t2, Vector visitados){
		Tupla pareja = new Tupla (t1,t2);
		if (visitados.contains(pareja))
			return true;
		else{
			visitados.add(pareja);
			if ((t1.getTipo().equals(t2.getTipo())) && 
					(t1.getTipo().equals("int") || 
					t1.getTipo().equals("bool")))
				return true;
			else if (t1.getTipo().equals("ref")){
					t1 = (ExpresionTipo)((propiedades)TS.getTabla().get(t1.getId())).getTipo();
					return compatibles2(t1,t2,visitados);
			}
			else if (t2.getTipo().equals("ref")){
					t2 = (ExpresionTipo)((propiedades)TS.getTabla().get(t2.getId())).getTipo();
					return compatibles2(t1,t2,visitados);
			}
			else if ((t1.getTipo().equals(t2.getTipo())) && 
						(t1.getTipo().equals("array")) &&
						(t1.getElems() == t2.getElems())){
					return compatibles2(t1.getTbase(),t2.getTbase(),visitados);				
			}
			else if((t1.getTipo().equals(t2.getTipo())) && 
						(t1.getTipo().equals("reg")) &&
						(t1.getParams().size() == t2.getParams().size())){
					Parametros atr1,atr2;
					for (int i=0;i<t1.getParams().size();i++){
						atr1 = (Parametros)t1.getParams().elementAt(i);
						atr2 = (Parametros)t2.getParams().elementAt(i);
						if (!compatibles2(atr1.getTipo(),atr2.getTipo(),visitados))
							return false;
					}
					return true;
			}
			else if ((t1.getTipo().equals(t2.getTipo())) && 
					(t1.getTipo().equals("proc")) &&
					(t1.getParams().size() == t2.getParams().size())) {
				for (int i = 1; i < t1.getParams().size(); i++) {
					Parametros a1 = (Parametros)t1.getParams().elementAt(i);
					Parametros a2 = (Parametros)t2.getParams().elementAt(i);
					if (!compatibles2(a1.getTipo(), a2.getTipo(), visitados)
							|| (a2.getModo().equals("var") && !a1.getModo().equals("var"))) {
						return false;
					}
				}
				return true;
			}
			else
				return false;
		}
	}
	
	private boolean referenciaErronea(ExpresionTipo et) throws Exception{
		return ((et.getTipo().equals("ref")) && (!TS.existeID(et.getId())));
	}
}
