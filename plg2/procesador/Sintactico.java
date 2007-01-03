package procesador;

import java.io.RandomAccessFile;
import tablaSimbolos.tablaSimbolos;
import maquinaP.Codigo;

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
 * @author Leticia Garcia
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
	public Sintactico(RandomAccessFile fuente, String f) throws Exception{
		lexico = new Lexico(fuente);		
		codigo = new Codigo(f);
	}

	/**
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		String tipoProg = Prog();
		codigo.inicializaCodigo();
		if (tipoProg.equals("error")){
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
	public String Prog() throws Exception{
		TS.setDir(0);
		String tipoDecs = Decs();
		if (!lexico.reconoce(CategoriaLexica.TKLLAP)){
			return "error";
		}
		lexico.lexer();
		String tipoIs = Is();
		if (!lexico.reconoce(CategoriaLexica.TKLLCI)){
			return "error";
		} 
		lexico.lexer();
		if (tipoDecs.equals("error") || tipoIs.equals("error")){
			return "error";	
		}
		else{
			return tipoIs;
		}
	}
	
	public String Decs() throws Exception{
		Atributo atrDec = Dec();
		TS = new tablaSimbolos();
		TS.addID(atrDec.getId(),atrDec.getTipo());
		Atributo atrRDecs = RDecs();
		if (atrDec.getTipo().equals("error") || atrRDecs.getTipo().equals("error")){
			return "error";
		}
		else {
			return atrRDecs.getTipo();
		}
	}
	
	public Atributo RDecs()throws Exception{
		Atributo atrRDecs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RDecs ::= λ
			return atrRDecs;
		}
		lexico.lexer(); //consumo ;
		Atributo atrDec = Dec();
		TS.addID(atrDec.getId(),atrDec.getTipo());
		Atributo atrRDecs1 = RDecs();
		if (TS.existeID(atrDec.getId())){
			atrRDecs.setTipo("error");
			return atrRDecs;
		}
		if (atrDec.getTipo().equals("error") || atrRDecs1.getTipo().equals("error")){
			atrRDecs.setTipo("error");
		}
		return atrRDecs;
	}
	 
	public Atributo Dec() throws Exception{
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
	
	public Atributo Tipo(){
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

	public String Is() throws Exception{
		Atributo atrI = I(); 
		Atributo atrRIs = RIs();
		if (atrI.getTipo().equals("error") || atrRIs.getTipo().equals("error")){
			return "error";
		}
		else {
			return atrRIs.getTipo();
		}
	}

	public Atributo RIs() throws Exception{
		Atributo atrRIs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RIs ::= λ
			return atrRIs;
		}
		lexico.lexer(); //consumo ;
		Atributo atrI = I();
		Atributo atrRI1 = RIs();
		if (atrI.getTipo().equals("error") || atrRI1.getTipo().equals("error")){
			atrRIs.setTipo("error");
		}
		return atrRIs;
	}
	public Atributo I(){
		Atributo atrI = IAsig();
		return atrI;
	}
	
	public Atributo IAsig(){
		return new Atributo();
	}

	public Atributo ExpOr(){
		return new Atributo();
	}

	public Atributo RExpOr(){
		return new Atributo();
	}

	public Atributo ExpAnd() throws Exception{
		ExpRel();
		Atributo atrRExpAnd = RExpAnd();
		return atrRExpAnd;
	}
	
	public Atributo RExpAnd() throws Exception{
		Atributo atrRExpAnd = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpAnd ::= λ
			return atrRExpAnd;
		}
		genOpAnd((lexico.lexer()).getLexema());
		Atributo atrExpRel = ExpRel();
		if (!(atrExpRel.getTipo()).equals("bool")){
			atrRExpAnd.setTipo("error");
			return atrRExpAnd;
		}
		atrRExpAnd = RExpAnd();
		return atrRExpAnd;
	}
	
	public Atributo ExpRel() throws Exception{
		ExpAd();
		Atributo atrRExpRel = RExpRel();
		return atrRExpRel;
	
	}
	public Atributo RExpRel() throws Exception{
		Atributo atrRExpRel = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpRel ::= λ
			return atrRExpRel;
		}
		genOpComp((lexico.lexer()).getLexema());
		Atributo atrExpRel1 = ExpRel();
		if ((atrExpRel1.getTipo()).equals("error")){
			atrRExpRel.setTipo("error");
			return atrRExpRel;
		}
		else{
			atrRExpRel.setTipo("bool");	
		}
		Atributo atrExpAd = ExpAd();
		return atrExpAd;
	}
	
	public Atributo ExpAd(){
		return new Atributo();
	}

	public Atributo RExpAd(){
		return new Atributo();
	}
	
	public Atributo ExpMul(){
		return new Atributo();
	}

	public Atributo RExpMul(){
		return new Atributo();
	}
	
	public Atributo Fact(){
		return new Atributo();
	}
	
	public void genOpAnd(String opDeOpAnd){
		if (opDeOpAnd == "&&")
			codigo.genIns("and");
	}
	
	public void genOpOr(String opDeOpOr){
		if (opDeOpOr == "||")
			codigo.genIns("or");
	}

	public void genOpAd(String opDeOpAd){
		if (opDeOpAd == "+")
			codigo.genIns("suma");
		else if (opDeOpAd.equals("-"))
			codigo.genIns("resta");
	}
	
	public void genOpMul(String opDeOpMul){
		if (opDeOpMul == "*")
			codigo.genIns("multiplica");
		else if (opDeOpMul.equals("/"))
			codigo.genIns("divide");
		else if (opDeOpMul.equals("%"))
			codigo.genIns("modulo");
	}
	
	public void genOpUn(String opDeOpUn){
		if (opDeOpUn == "+")
			codigo.genIns("positivo");
		else if (opDeOpUn.equals("-"))
			codigo.genIns("menos");
		else if (opDeOpUn.equals("!"))
			codigo.genIns("not");
	}
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
		if (opDeOpComp == "=="){
			codigo.genIns("igual");
		}	
		if (opDeOpComp == "!="){
				codigo.genIns("distinto");
		}
	}

}
