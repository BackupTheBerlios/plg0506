package procesador;

import java.io.File;
import java.io.IOException;

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

	private boolean Prog() throws Exception{
		boolean errProg = true;
		ProgDec();
		if (lexico.reconoce(CategoriaLexica.TKVAR)){
			lexico.lexer();//consumo VAR
			boolean errDecs = Decs();
			boolean errBloque = Bloque();
			errProg = errDecs || errBloque;
			codigo.genIns("stop");
		}
		return errProg;
	}

	private void ProgDec() throws Exception{
		TS = new tablaSimbolos();
		if (lexico.reconoce(CategoriaLexica.TKIDEN)){
			Token tk = lexico.lexer(); //consumo iden
			TS.addID(tk.getLexema(),"");
			while (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
				lexico.lexer();
			}
		}else{
			throw new Exception("El Programa tiene que tener un nombre");
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Decs() throws Exception{
		//System.out.println("Decs");
		Atributo atrDec = Dec();
//		TS = new tablaSimbolos();
		TS.addID(atrDec.getId(),atrDec.getTipo());
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
			//RDecs ::= Î»
			return heredado;
		}
		lexico.lexer(); //consumo ;
		Atributo atrDec = Dec();
		if (TS.existeID(atrDec.getId())){
			throw new Exception("El identificador " + atrDec.getId() + " esta duplicado");
		}
		if (heredado.getTipo().equals("error")){
			atrRDecs.setTipo("error");
			return atrRDecs;
		}
		TS.addID(atrDec.getId(),atrDec.getTipo());
		atrRDecs = RDecs(atrDec);
		return atrRDecs;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo Dec() throws Exception{
		//System.out.println("Dec");
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
		//System.out.println("Tipo");
		Atributo atrTipo = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			atrTipo.setTipo("int");
			return atrTipo;
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			atrTipo.setTipo("bool");
			return atrTipo;
		}
		throw new Exception("Tipo incorrecto en linea " + lexico.getLinea());
	}
	
	private boolean Bloque() throws Exception{
		boolean errBloque = true;
		if (lexico.reconoce(CategoriaLexica.TKBEGIN)){
			lexico.lexer();
			boolean errIs = Is();
			if (lexico.reconoce(CategoriaLexica.TKEND)){
				errBloque = errIs;
			}
			else
				throw new Exception("Bloque de instrucciones tiene que estar entre BEGIN y END" + lexico.getLinea());
		}
		else
			throw new Exception("Bloque de instrucciones tiene que estar entre BEGIN y END" + lexico.getLinea());
		return errBloque;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Is() throws Exception{
		//System.out.println("Is");
		Atributo atrI = I(); 
		Atributo atrRIs = RIs(atrI);
		if (atrRIs.getTipo().equals("error")){
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
			//RIs ::= Î»
			return heredado;
		}
		lexico.lexer(); //consumo ;
		Atributo atrI = I();
		atrRIs = RIs(atrI);
		if (heredado.getTipo().equals("error") || atrRIs.getTipo().equals("error")){
			atrRIs.setTipo("error");
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
		//System.out.println("I");
		Atributo atrI = IAsig();
		return atrI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo IAsig() throws Exception{
		//System.out.println("IAsig");
		Atributo atrIAsig = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			atrIAsig.setTipo("error");
			return atrIAsig;
		}
		Token tk = lexico.lexer(); //Consumimos el iden
		atrIAsig.setId(tk.getLexema());
		if (!TS.existeID(atrIAsig.getId())){
			throw new Exception ("Error en linea: " + lexico.getLinea() + " El identificador no ha sido declarado antes");
		}
		String tipo = ((propiedades)TS.getTabla().get(atrIAsig.getId())).getTipo();
		atrIAsig.setTipo(tipo);
		if (!lexico.reconoce(CategoriaLexica.TKASIGN)){
			throw new Exception("Error en la asignacion en linea: " + lexico.getLinea());
	    }
		lexico.lexer();//Consumimos =
		Atributo atrExpOr= ExpOr();
		if (!atrExpOr.getTipo().equals(atrIAsig.getTipo())){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		int dir = ((propiedades)TS.getTabla().get(atrIAsig.getId())).getDir();
		codigo.genIns("desapila-dir", dir);
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
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= Î»
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= Î»
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKOR)){
			return heredado;
		}
		String op = genOpOr((lexico.lexer()).getLexema());
		Atributo atrExpAnd = ExpAnd();
		if (!atrExpAnd.getTipo().equals("bool") || !heredado.getTipo().equals("bool")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrExpAnd.setTipo("bool");
			if (op != ""){
				codigo.genIns(op);
			}
			else{
				throw new Exception("Error en linea " + lexico.getLinea() + ": operador no vÃ¡lido");
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
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= Î»
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= Î»
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKAND)){
			return heredado;
		}
		String op = genOpAnd((lexico.lexer()).getLexema());
		Atributo atrExpRel = ExpRel();
		if (!atrExpRel.getTipo().equals("bool") || !heredado.getTipo().equals("bool")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrExpRel.setTipo("bool");
			if (op != ""){
				codigo.genIns(op);
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
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= Î»
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= Î»
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKIG) && !lexico.reconoce(CategoriaLexica.TKDIF)
				&& !lexico.reconoce(CategoriaLexica.TKMAY)&& !lexico.reconoce(CategoriaLexica.TKMAYIG)
				&& !lexico.reconoce(CategoriaLexica.TKMEN)&& !lexico.reconoce(CategoriaLexica.TKMENIG)){
			return heredado;
		}
		String op = genOpRel((lexico.lexer()).getLexema());
		
		atrRExpRel = ExpAd();
		if (heredado.getTipo().equals("error") || atrRExpRel.getTipo().equals("error")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else if (!heredado.getTipo().equals(atrRExpRel.getTipo())) {
			atrRExpRel.setTipo("error");
		}
		else{
			atrRExpRel.setTipo("bool");
			if (op != ""){
				codigo.genIns(op);
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
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpAd ::= Î»
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= Î»
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKSUMA) && !lexico.reconoce(CategoriaLexica.TKRESTA)){
			return heredado;
		}
		String op = genOpAd((lexico.lexer()).getLexema());
		Atributo atrExpMul = ExpMul();
		if (!atrExpMul.getTipo().equals("int") || !heredado.getTipo().equals("int")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrRExpAd.setTipo("int");
			if (op != ""){
				codigo.genIns(op);
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
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpMul ::= Î»
			return heredado;
		}
		if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= Î»
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKDIV) && !lexico.reconoce(CategoriaLexica.TKMOD)&& !lexico.reconoce(CategoriaLexica.TKMULT)){
			return heredado;
		}
		String op = genOpMul((lexico.lexer()).getLexema());
		Atributo atrFact = Fact();
		if ((atrFact.getTipo()).equals("int") && heredado.getTipo().equals("int")){
			atrRExpMul.setTipo("int");
			if (op != ""){
				codigo.genIns(op);
			}
			else{
				throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
			}
		}
		else if ((atrFact.getTipo()).equals("bool") && heredado.getTipo().equals("bool")){
			atrRExpMul.setTipo("bool");
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
		if (lexico.reconoce(CategoriaLexica.TKNUM)){
			atrFact.setTipo("int");
			lexico.lexer(); //Cosumimos el entero
			codigo.genIns("apila", Integer.parseInt(lexico.getLookahead().getLexema()));
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKTRUE)) {
			atrFact.setTipo("bool");
			codigo.genIns("apila",1);
			lexico.lexer(); //Cosumimos 'true'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKFALSE)) {
			atrFact.setTipo("bool");
			codigo.genIns("apila",0);
			lexico.lexer(); //Cosumimos 'false'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKPAP)) {
			lexico.lexer(); //Cosumimos '('
			atrFact = ExpOr();
			if (!lexico.reconoce(CategoriaLexica.TKPCI)){
				throw new Exception(" Error de parentizacion, en linea: " + lexico.getLinea());
			}
			lexico.lexer(); //Cosumimos ')'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKIDEN)) {
			Token tk = lexico.lexer(); //Consumimos el iden
			if (TS.existeID(tk.getLexema())){
				atrFact.setId(tk.getLexema());
				String tipo = ((propiedades)TS.getTabla().get(atrFact.getId())).getTipo();
				atrFact.setTipo(tipo);
				int dir =((propiedades)TS.getTabla().get(atrFact.getId())).getDir();
				codigo.genIns("apila-dir", dir);
			}
			else{
				atrFact.setTipo("error");
			}
			return atrFact;
		}

		if (lexico.reconoce(CategoriaLexica.TKNOT)){
			String op = genOpUnarioLog(lexico.lexer().getLexema()); //Consumimos operador unario logico
			atrFact = Fact();
			codigo.genIns(op);
			if (!atrFact.getTipo().equals("bool")){
					atrFact.setTipo("error");
					throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
			else{
				atrFact.setTipo("bool");
			}
			return atrFact;
		}
		
		if ( (lexico.reconoce(CategoriaLexica.TKSUMA)) || (lexico.reconoce(CategoriaLexica.TKRESTA))){
			String op = genOpUnarioArit(lexico.lexer().getLexema()); //Consumimos operador unario aritmetico
			atrFact = Fact();
			codigo.genIns(op);
			if (!atrFact.getTipo().equals("int")){
					atrFact.setTipo("error");
					throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
			else{
				atrFact.setTipo("int");
			}
			return atrFact;
		}
		
		atrFact.setTipo("error");
		return atrFact;
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

}