package procesador;

import java.io.RandomAccessFile;
import java.io.File;
import tablaSimbolos.*;

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
 * @author Paloma de la Fuente y Leticia Garcia
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
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contiene el cdigo a analizar.
	 * @param T Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
	 * @param f String donde se guarga la ruta del fichero donde se va a guardar el codigo generado por el compilador.
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
		System.out.println("Decs");
		Atributo atrDec = Dec();
		TS = new tablaSimbolos();
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
		System.out.println("RDecs");
		Atributo atrRDecs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RDecs ::= λ
			return heredado;
		}
		lexico.lexer(); //consumo ;
		Atributo atrDec = Dec();
		TS.addID(atrDec.getId(),atrDec.getTipo());
		if (TS.existeID(atrDec.getId()) || heredado.getTipo().equals("error")){
			atrDec.setTipo("error");
		}
		atrRDecs = RDecs(atrDec);
		return atrRDecs;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo Dec() throws Exception{
		System.out.println("Dec");
		Atributo atrTipo = Tipo();
		Atributo atrDec = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			atrDec.setTipo("error");
			return atrDec;
		}
		Token tk = lexico.lexer(); //consumo iden
		atrDec.setTipo(atrTipo.getTipo());
		atrDec.setId(tk.getLexema());
		return atrDec;
	}
	
	/**
	 * 
	 * @return
	 */
	private Atributo Tipo(){
		System.out.println("Tipo");
		Atributo atrTipo = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			atrTipo.setTipo("int");
			return atrTipo;
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			atrTipo.setTipo("bool");
			return atrTipo;
		}
		atrTipo.setTipo("error");
		return atrTipo;
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
		System.out.println("RIs");
		Atributo atrRIs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RIs ::= λ
			return heredado;
		}
		lexico.lexer(); //consumo ;
		Atributo atrI = I();
		atrRIs = RIs(atrI);
		if (heredado.getTipo().equals("error") || atrRIs.getTipo().equals("error")){
			atrRIs.setTipo("error");
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
		Atributo atrI = IAsig();
		return atrI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo IAsig() throws Exception{
		System.out.println("IAsig");
		Atributo atrIAsig = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			atrIAsig.setTipo("error");
			return atrIAsig;
		}
		Token tk = lexico.lexer(); //Consumimos el iden
		atrIAsig.setId(tk.getLexema());
		String tipo = ((propiedades)TS.getTabla().get(atrIAsig.getId())).getTipo();
		atrIAsig.setTipo(tipo);
		if (!lexico.reconoce(CategoriaLexica.TKASIGN)){
	            atrIAsig.setTipo("error");
	            return atrIAsig;
	    }
		lexico.lexer();//Consumimos =
		Atributo atrExpOr= ExpOr();
		if ((!TS.existeID(atrIAsig.getId())) || !atrExpOr.getTipo().equals(atrIAsig.getTipo())){
			atrIAsig.setTipo("error");
			return atrIAsig;
		}
		return atrIAsig;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpOr() throws Exception{
		System.out.println("ExpOr");
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
		System.out.println("RExpOr");
		Atributo atrRExpOr = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= λ
			return heredado;
		}
		genOpOr((lexico.lexer()).getLexema());
		Atributo atrExpAnd = ExpAnd();
		if (!(atrExpAnd.getTipo()).equals("bool") && heredado.getTipo().equals("bool")){
			atrExpAnd.setTipo("error");
		}
		else{
			atrExpAnd.setTipo("bool");
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
		System.out.println("ExpAnd");
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
		System.out.println("RExpAnd");
		Atributo atrRExpAnd = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= λ
			return heredado;
		}
		genOpAnd((lexico.lexer()).getLexema());
		Atributo atrExpRel = ExpRel();
		if (!(atrExpRel.getTipo()).equals("bool") && heredado.getTipo().equals("bool")){
			atrExpRel.setTipo("error");
		}
		else{
			atrExpRel.setTipo("bool");
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
		System.out.println("ExpRel");
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
		System.out.println("RExpRel");
		Atributo atrRExpRel = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpOr ::= λ
			return heredado;
		}
		genOpAnd((lexico.lexer()).getLexema());
		if (heredado.getTipo().equals("error")){
			atrRExpRel.setTipo("error");
		}
		else{
			atrRExpRel.setTipo("bool");
		}
		atrRExpRel = ExpAd();
		return atrRExpRel;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Atributo ExpAd() throws Exception{
		System.out.println("ExpAd");
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
		System.out.println("RExpAd");
		Atributo atrRExpAd = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpAd ::= λ
			return heredado;
		}
		genOpAd((lexico.lexer()).getLexema());
		Atributo atrExpMul = ExpMul();
		if (!(atrExpMul.getTipo()).equals("int") && !heredado.getTipo().equals("int")){
			atrRExpAd.setTipo("error");
			return atrRExpAd;
		}
		else{
			atrRExpAd.setTipo("int");	
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
		System.out.println("ExpMul");
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
		System.out.println("RExpMul");
		Atributo atrRExpMul = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpMul ::= λ
			return heredado;
		}
		genOpMul((lexico.lexer()).getLexema());
		Atributo atrFact = Fact();
		if ((atrFact.getTipo()).equals("int") && heredado.getTipo().equals("int")){
			atrRExpMul.setTipo("int");
		}
		else if ((atrFact.getTipo()).equals("bool") && heredado.getTipo().equals("bool")){
			atrRExpMul.setTipo("bool");
		}
		else{
			atrRExpMul.setTipo("error");
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
		System.out.println("Fact");
		Atributo atrFact = new Atributo();
		
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			atrFact.setTipo("int");
			codigo.genIns("apila", Integer.parseInt(lexico.getLookahead().getLexema()));
			lexico.lexer(); //Cosumimos el entero
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
				atrFact.setTipo("error");
				return atrFact;
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
				codigo.genIns("apila", Integer.parseInt(tk.getLexema()));
			}
			else{
				atrFact.setTipo("error");
			}
			return atrFact;
		}
		if ( (lexico.reconoce(CategoriaLexica.TKNOT)) || (lexico.reconoce(CategoriaLexica.TKSUMA)) || (lexico.reconoce(CategoriaLexica.TKRESTA))){
			Token tk = lexico.lexer(); //Consumimos operador unario
			genOpUn(tk.getLexema()); //Consumimos el operador
			atrFact = Fact();
			return atrFact;
		}
		atrFact.setTipo("error");
		return atrFact;
	}
	
	/**
	 * 
	 * @param opDeOpAnd
	 */
	private void genOpAnd(String opDeOpAnd){
		if (opDeOpAnd == "&&")
			codigo.genIns("and");
	}
	
	/**
	 * 
	 * @param opDeOpOr
	 */
	private void genOpOr(String opDeOpOr){
		if (opDeOpOr == "||")
			codigo.genIns("or");
	}
	
	/**
	 * 
	 * @param opDeOpAd
	 */
	private void genOpAd(String opDeOpAd){
		if (opDeOpAd == "+")
			codigo.genIns("suma");
		else if (opDeOpAd.equals("-"))
			codigo.genIns("resta");
	}
	
	/**
	 * 
	 * @param opDeOpMul
	 */
	private void genOpMul(String opDeOpMul){
		if (opDeOpMul == "*")
			codigo.genIns("multiplica");
		else if (opDeOpMul.equals("/"))
			codigo.genIns("divide");
		else if (opDeOpMul.equals("%"))
			codigo.genIns("modulo");
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 */
	private void genOpUn(String opDeOpUn){
		if (opDeOpUn == "+")
			codigo.genIns("positivo");
		else if (opDeOpUn.equals("-"))
			codigo.genIns("menos");
		else if (opDeOpUn.equals("!"))
			codigo.genIns("not");
	}
	
	/**
	 * 
	 * @param opDeOpComp
	 */
	private void genOpComp(String opDeOpComp){
		
		if (opDeOpComp == "<="){
			codigo.genIns("menorIgual");
		}	
		if (opDeOpComp == "<"){
				codigo.genIns("menor");
		}
		if (opDeOpComp == ">="){
			codigo.genIns("mayorIgual");
		}	
		if (opDeOpComp == ">"){
				codigo.genIns("mayor");
		}
		if (opDeOpComp == "=="){
			codigo.genIns("igual");
		}	
		if (opDeOpComp == "!="){
				codigo.genIns("distinto");
		}
	}

}
