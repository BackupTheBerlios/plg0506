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
		boolean errProg = false;
		errProg = ProgDec();
		if (lexico.reconoce(CategoriaLexica.TKVAR)){
			lexico.lexer();//consumo VAR
			errProg = errProg || Decs();
			errProg = errProg || Bloque();
			if (lexico.reconoce(CategoriaLexica.TKPUNTO)){
				lexico.lexer();//consumo .
				codigo.emite("stop");				
			}
			else throw new Exception ("Se esperaba \".\"");			
		}
		else throw new Exception ("Se esperaba \"VAR\"");
		return errProg;		
	}

	private boolean ProgDec() throws Exception{
		boolean err0 = false;
		if (lexico.reconoce(CategoriaLexica.TKPROGRAM)){
			lexico.lexer();
			if (lexico.reconoce(CategoriaLexica.TKIDEN)){
				Token tk = lexico.lexer(); //consumo iden
				TS = new tablaSimbolos();
				TS.addID(tk.getLexema(),"", 0);
				err0 = RProgdec();
			}else{
				throw new Exception("El Programa tiene que tener un nombre");
			}
	
		}
		else throw new Exception ("Se esperaba \"PROGRAM\"");
		return err0;
	}

	
	
	private boolean RProgdec() throws Exception{
		// TODO Auto-generated method stub
		boolean err0 = false;
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			lexico.lexer();
		}
		else if (lexico.reconoce(CategoriaLexica.TKPAP)){
			lexico.lexer();
			err0 = Idens();
			if (lexico.reconoce(CategoriaLexica.TKPCI))
				lexico.lexer();
			else throw new Exception ("Se esperaba \")\"");
		}
		else throw new Exception ("Se esperaba \"(\" o \";\"");
		return err0;
	}

	
	
	private boolean Idens() throws Exception {
		// TODO Auto-generated method stub
		boolean err0 = false;
		if (lexico.reconoce(CategoriaLexica.TKIDEN)){
			Token tk = lexico.lexer();
			boolean errh1 = TS.existeID(tk.getLexema());
			if (errh1)
				System.out.println("El identificador " +
						tk.getLexema() + " esta repetido.");
			else TS.addID(tk.getLexema(),"",0);	
			err0 = RIdens(errh1);
		}
		else throw new Exception("Se esperaba \"iden\"");
		return err0;
	}
	
	

	private boolean RIdens(boolean errh0) throws Exception{
		boolean err0 = false;
		if (lexico.reconoce(CategoriaLexica.TKCOMA)){
			lexico.lexer();
			if (lexico.reconoce(CategoriaLexica.TKIDEN)){
				Token tk = lexico.lexer();
				boolean errh1aux = TS.existeID(tk.getLexema());
				if (errh1aux)
					System.out.println("El identificador " +
							tk.getLexema() + " esta repetido.");
				else TS.addID(tk.getLexema(), "", 0);
				boolean errh1 = errh0 || errh1aux;
				err0 = RIdens(errh1);			
			} else throw new Exception("Se esperaba \"iden\"");
		} else {
			err0 = errh0;
		}			
		return err0;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Decs() throws Exception{
		Atributo atrDec = new Atributo();
                boolean err0 = Dec(atrDec);		
		boolean errh1aux = TS.existeID(atrDec.getId());
		if (errh1aux)
			System.out.println("El identificador " +
					atrDec.getId() + " esta repetido.");
		else TS.addID(atrDec.getId(),atrDec.getTipo(), 0);
		err0 = err0 || errh1aux;
		return RDecs(err0,0);		
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean RDecs (boolean errh0, int dirh0) throws Exception{//(Atributo heredado)throws Exception{
		boolean err0 = errh0;
		//System.out.println("RDecs");
		if (lexico.reconoce(CategoriaLexica.TKIDEN)){
			Atributo atrDec = new Atributo();
			err0 = Dec(atrDec);
			boolean errh1aux = TS.existeID(atrDec.getId());
			if (errh1aux)
				System.out.println("El identificador " +
						atrDec.getId() + " esta repetido.");	
			else TS.addID(atrDec.getId(),atrDec.getTipo(), dirh0+1);
			err0 = err0 || errh1aux;
			err0 = err0 || RDecs (err0, dirh0+1);
		}
		return err0;
	}
		
	
	
/*		
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RDecs ::= \lambda
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
*/
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Dec(Atributo atrib) throws Exception{
		//System.out.println("Dec");
		//Atributo atrTipo = Tipo();
		//Atributo atrDec = new Atributo();
		//Token tk = lexico.lexer(); //consumo el tipo
		boolean err0 = false;
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea());
		}
		Token tk = lexico.lexer(); //consumo el iden
		atrib.setId(tk.getLexema());
		err0 = Tipo(atrib);
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			lexico.lexer();
		} else throw new Exception("Se esperaba \";\"");
		return err0;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 */
	private boolean Tipo(Atributo atrib) throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			lexico.lexer();
			atrib.setTipo("int");
			return false;
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			lexico.lexer();
			atrib.setTipo("bool");
			return false;
		}
		else if (lexico.reconoce(CategoriaLexica.TKIDEN)){
			System.out.println("Error Contextual: se esperaba INTEGER o BOOLEAN");
			return true;
		} else throw new Exception("Se esperaba un Tipo");// incorrecto en linea " + lexico.getLinea());
	}
	
	
	
	/**
	 * 
	 * @return
	 * @throws Exception 
	 */	
	private boolean Bloque() throws Exception{
		boolean errBloque = false;
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
		boolean errI = I();
		errI = errI || RIs();
		return errI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean RIs() throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			return false;
		}
		boolean errI = I();
		errI = errI || RIs();
		return errI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean I() throws Exception{
		boolean errI = false;//System.out.println("I");
		errI = IAsig();
		return errI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean IAsig() throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception ("Se esperaba un identificador");
		}
		Token tk = lexico.lexer();
		if (!lexico.reconoce(CategoriaLexica.TKASIGN))
			throw new Exception ("Se esperaba \":?\"");
		if (!TS.existeID(tk.getLexema())){
			System.out.println ("Error en linea: " + lexico.getLinea() + " El identificador no ha sido declarado antes");
			return true;
		}
		String tipoExpRel = ExpRel();
		propiedades idTSProps = TS.getProps(tk.getLexema());
		if (!tipoExpRel.equals(idTSProps.getTipo()))
			return true;
		codigo.emite("desapila-dir", idTSProps.getDir());
		return false;
	
	}
	 
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String ExpRel() throws Exception{
		//System.out.println("ExpRel");
		String tipo1 = ExpAd();
		return RExpRel (tipo1);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String RExpRel(String tipoh) throws Exception{
		//System.out.println("ExpRel");
		String tipo = "error";
		String cod = OpRel(); //Reconoce menorigual, igual, diferente, etc
		if (cod.length()>0){
			String tipo1 = ExpAd();
			if (tipoh.equals("boolean") && tipo1.equals(tipoh))
				tipo = "boolean";
			codigo.emite(cod);
			return RExpRel(tipo);
		}
		else //lambda
			return tipoh;
	}
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String ExpAd() throws Exception{
		String tipo1 = ExpMul();
		return RExpAd(tipo1);
	}

	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String RExpAd(String tipoh) throws Exception{
		//System.out.println("ExpRel");
		String tipo = "error";
		String tipo1 = "";
		String cod = OpAd(); //Reconoce menorigual, igual, diferente, etc
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.equals("integer") && tipo1.equals(tipoh))
				tipo = "integer";
			codigo.emite(cod);
			return RExpAd(tipo);
		}
		cod = OpOr();
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.equals("boolean") && tipo1.equals(tipoh))
				tipo = "boolean";
			codigo.emite(cod);
			return RExpAd(tipo);
		}
		else //lambda
			return tipoh;
	}
	

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
/*	private String RExpAd(String heredado) throws Exception{
		//System.out.println("RExpAd");
		Atributo atrRExpAd = new Atributo();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpAd ::= lambda
			return heredado;
		}if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= lambda
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKSUMA) && !lexico.reconoce(CategoriaLexica.TKRESTA)){
			return heredado;
		}
		String op = OpAd((lexico.lexer()).getLexema());
		Atributo atrExpMul = ExpMul();
		if (!atrExpMul.getTipo().equalsIgnoreCase("int") || !heredado.getTipo().equalsIgnoreCase("int")){
			throw new Exception("Error de tipos en linea: " + lexico.getLinea());
		}
		else{
			atrRExpAd.setTipo("int");
			if (op != ""){
				codigo.emite(op);
			}
			else{
				throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
			}
		}
		atrRExpAd = RExpAd(atrRExpAd);
		return atrRExpAd;
	}
*/	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String ExpMul() throws Exception{
		//System.out.println("ExpMul");
		String atrFact = Fact();
		String atrRExpMul = RExpMul(atrFact);
		return atrRExpMul;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String RExpMul(String heredado) throws Exception{
		//System.out.println("RExpMul");
		String atrRExpMul = "error";
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			//RExpMul ::= \lambda
			return heredado;
		}
		if (lexico.reconoce(CategoriaLexica.TKLLCI )){
			//RExpMul ::= \lambda
			return heredado;
		}
		if (!lexico.reconoce(CategoriaLexica.TKDIV) && !lexico.reconoce(CategoriaLexica.TKMOD)&& !lexico.reconoce(CategoriaLexica.TKMULT) && !lexico.reconoce(CategoriaLexica.TKAND)){
			return heredado;
		}
		if(!lexico.reconoce(CategoriaLexica.TKDIV) && !lexico.reconoce(CategoriaLexica.TKMOD)&& !lexico.reconoce(CategoriaLexica.TKMULT)){
			String op = OpMul((lexico.lexer()).getLexema());
			String atrFact = Fact();
			if ((atrFact).equalsIgnoreCase("int") && heredado.equalsIgnoreCase("int")){
				atrRExpMul = "int";
				if (op != ""){
					codigo.emite(op);
				}
				else{
					throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
				}
			}
			else if (atrFact.equalsIgnoreCase("bool") && heredado.equalsIgnoreCase("bool")){
				atrRExpMul = "bool";
			}
			else{
				throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
		}
		else{
			String op = OpAnd((lexico.lexer()).getLexema());
			String atrFact = Fact();
			if (!atrFact.equalsIgnoreCase("bool") || !heredado.equalsIgnoreCase("bool")){
				throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
			else{
				atrFact = "bool";
				if (op != ""){
					codigo.emite(op);
				}
				else{
					throw new Exception("Error en linea: " + lexico.getLinea() + " Operador no valido");
				}
			}
		}
		atrRExpMul = RExpMul(atrRExpMul);
		return atrRExpMul;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String Fact() throws Exception{
		//System.out.println("Fact");
		String atrFact = "error";
		if (lexico.reconoce(CategoriaLexica.TKNUM)){
			atrFact = "int";
			lexico.lexer(); //Cosumimos el entero
			codigo.emite("apila", Integer.parseInt(lexico.getLookahead().getLexema()));
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKTRUE)) {
			atrFact = "bool";
			codigo.emite("apila",1);
			lexico.lexer(); //Cosumimos 'true'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKFALSE)) {
			atrFact = "bool";
			codigo.emite("apila",0);
			lexico.lexer(); //Cosumimos 'false'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKPAP)) {
			lexico.lexer(); //Cosumimos '('
			atrFact = ExpRel();
			if (!lexico.reconoce(CategoriaLexica.TKPCI)){
				throw new Exception(" Error de parentizacion, en linea: " + lexico.getLinea());
			}
			lexico.lexer(); //Cosumimos ')'
			return atrFact;
		}
		if (lexico.reconoce(CategoriaLexica.TKIDEN)) {
			Token tk = lexico.lexer(); //Consumimos el iden
			if (TS.existeID(tk.getLexema())){
				//atrFact.setId(tk.getLexema());
				//String tipo = ((propiedades)TS.getTabla().get(atrFact.getId())).getTipo();
				atrFact = ((propiedades)TS.getTabla().get(tk.getLexema())).getTipo();
				//int dir =((propiedades)TS.getTabla().get(atrFact.getId())).getDir();
				int dir =((propiedades)TS.getTabla().get(tk.getLexema())).getDir();
				codigo.emite("apila-dir", dir);
			}
			else{
				atrFact="error";
			}
			return atrFact;
		}

		if (lexico.reconoce(CategoriaLexica.TKNOT)){
			String op = OpUnarioLog(lexico.lexer().getLexema()); //Consumimos operador unario logico
			atrFact = Fact();
			codigo.emite(op);
			if (!atrFact.equalsIgnoreCase("bool")){
					atrFact="error";
					throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
			else{
				atrFact="bool";
			}
			return atrFact;
		}
		
		if ( (lexico.reconoce(CategoriaLexica.TKSUMA)) || (lexico.reconoce(CategoriaLexica.TKRESTA))){
			String op = OpUnarioArit(lexico.lexer().getLexema()); //Consumimos operador unario aritmetico
			atrFact = Fact();
			codigo.emite(op);
			if (!atrFact.equalsIgnoreCase("int")){
					//atrFact = "error";
					throw new Exception("Error de tipos en linea: " + lexico.getLinea());
			}
			else{
				atrFact = "int";
			}
			return atrFact;
		}
		
		atrFact = "error";
		return atrFact;
	}
	
	/*
	 * OpUnario ::= + | - | NOT 
OpAd ::= + | - 
OpOr::= OR
OpMul ::= * | DIV | MOD
OpAnd ::= AND
OpRel ::= < | > | <= | >= | = | <>
*/
	
	
	/**
	 * 
	 * @param opDeOpAnd
	 * @throws Exception 
	 */
	private String OpAnd(String opDeOpAnd){
		if (opDeOpAnd == "and")
			return "and";
		else
			return "";
	}
	
	/**
	 * 
	 * @param opDeOpOr
	 */
	private String OpOr(String opDeOpOr){
		if (opDeOpOr == "or")
			return ("or");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpAd
	 */
	private String OpAd(String opDeOpAd){
		if (opDeOpAd == "+")
			return ("suma");
		else if (opDeOpAd.equalsIgnoreCase("-"))
			return("resta");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpMul
	 */
	private String OpMul(String opDeOpMul){
		if (opDeOpMul == "*")
			return("multiplica");
		else if (opDeOpMul.equalsIgnoreCase("div")){
			return("divide");}
		else if (opDeOpMul.equalsIgnoreCase("mod"))
			return("modulo");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 * @return
	 */
	private String OpUnarioArit(String opDeOpUn){
		if (opDeOpUn == "+")
			return("positivo");
		else if (opDeOpUn.equalsIgnoreCase("-"))
			return("menos");
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 * @return
	 */
	private String OpUnarioLog(String opDeOpUn){
		if (opDeOpUn.equalsIgnoreCase("not"))
			return("not");
		else return "";
	}
		
	/**
	 * 
	 * @param opDeOpComp
	 * @return
	 */
	private String OpRel() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKMENIG)){
			lexico.lexer();
			return("menorIgual");
		}
		else if (lexico.reconoce(CategoriaLexica.TKMEN)){
			lexico.lexer();
			return("menor");
		}
		else if (lexico.reconoce(CategoriaLexica.TKMAYIG)){
			lexico.lexer();
			return("mayorIgual");
		}	
		else if (lexico.reconoce(CategoriaLexica.TKMAY)){
			lexico.lexer();
			return("mayor");
		}
		else if (lexico.reconoce(CategoriaLexica.TKIG)){
			lexico.lexer();
			return("igual");
		}	
		else if (lexico.reconoce(CategoriaLexica.TKDIF)){
			lexico.lexer();
			return("distinto");
		}
		else return "";
	}

}