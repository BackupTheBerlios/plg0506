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
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 * 
 */

public class Sintactico{
	
	/**
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
	int dir;
	
	/**
	 * Constructor que inicializa los atributos con los datos que recibe por parametro.
	 * 
	 * @param f String donde se guarda la ruta del fichero donde se va a guardar el codigo generado por el compilador.
	 * @throws Exception Propaga una excepcion que haya sucedido en otro lugar.
	 */
	public Sintactico(File f) throws Exception{
		lexico = new Lexico(f);		
		codigo = new Codigo(f);
		etq = 0;
	}
	
	/**
	 * Accesor para el atributo de la clase codigo.
	 * @return codigo: El codigo generado.
	 */
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
			throw new Exception("El programa contiene errores");
		}
	}
	
	/**
	 * Evalua el programa.  Primero lee el nombre del programa, luego las declaraciones de variables (identificadores), 
	 * que se encuentran separados del bloque de instrucciones "Is" mediante un "BEGIN".  Acto seguido, procesa cada 
	 * instruccion de Is, y  una vez terminado el bloque, lee "." que es donde acaba el programa.
	 * 
	 * @return errProg Devuelve un booleano que indica si existio un error al analizar el codigo del Programa. 
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */	
	/*private boolean Prog() throws Exception{
		boolean errProg = false;
		etq=0;
		ProgDec();
		if (lexico.reconoce(CategoriaLexica.TKVAR)){
			lexico.lexer();//consumo VAR
			errProg = Decs();
			errProg = Bloque() || errProg;
			if (lexico.reconoce(CategoriaLexica.TKPUNTO)){
				lexico.lexer();//consumo .
				codigo.emite("stop");
				etq++;
System.out.println(codigo.getString());
			}
			else throw new Exception ("Se esperaba \".\" en "+lexico.getLinea()+","+lexico.getColumna());			
		}
		else throw new Exception ("Se esperaba \"VAR\" en "+lexico.getLinea()+","+lexico.getColumna());			
		return errProg;		
	}*/

	
	 private boolean Prog() throws Exception{
		boolean errProg = false;
		etq=0;
		dir=0;
		if (lexico.reconoce(CategoriaLexica.TKPROGRAM)){
			lexico.lexer();
			if (lexico.reconoce(CategoriaLexica.TKIDEN)){
				Token tk = lexico.lexer(); //consumo iden
				TS = new tablaSimbolos();
				Propiedades p = new Propiedades();
				TS.addID(tk.getLexema(),p);
				if (lexico.reconoce(CategoriaLexica.TKPYCOMA))
					lexico.lexer();
				else throw new Exception ("Se esperaba \";\" en "+lexico.getLinea()+","+lexico.getColumna());		
				
			}else{
				throw new Exception("El Programa tiene que tener un nombre en "+lexico.getLinea()+","+lexico.getColumna());		
			}
	
		}
		else throw new Exception ("Se esperaba \"PROGRAM\" en "+lexico.getLinea()+","+lexico.getColumna());			

		codigo.inicio();
		etq = codigo.longInicio;
		codigo.emite("ir-a");//TODO: Luego hay que parchearlo
		int etqaux = etq;
		etq++;
		
		boolean errRProg = RProg();
		
		errProg = errRProg;
		if (lexico.reconoce(CategoriaLexica.TKPUNTO)){
			lexico.lexer();//consumo .
			codigo.parcheaInicio(0, etq, dir);
			codigo.parchea(etqaux, etq);
			codigo.emite("stop");
			etq++;
System.out.println(codigo.getString());
		}
		else throw new Exception ("Se esperaba \".\" en "+lexico.getLinea()+","+lexico.getColumna());
		return errProg;
	}
	
	private boolean RProg () throws Exception{ 
			boolean err1 = BloqueDecs(); 
			boolean err2 = Bloque();
			return err1 || err2;
	}
		
	

	private boolean BloqueDecs() throws Exception{ 
		boolean err1 = DecsTipo();
		boolean err2 = DecsVar();
		//boolean err3 = DecsProc(); 
		return err1 || err2; //|| err3;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean DecsTipo()throws Exception{ 
		boolean err0 = false;
		if (lexico.reconoce(CategoriaLexica.TKTYPE)){
			lexico.lexer(); //Consumo TYPE
			err0 = NDecsTipo();
		}
		return err0;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean NDecsTipo() throws Exception{ 
		boolean errNDecsTipo, err1, err2;
		Atributo atrDecTipo = DecTipo ();
		if (atrDecTipo != null)
			err1= atrDecTipo.getProps().getTipo().getId().equals("error");
		else throw new Exception("La declaracion de tipo no es correcta (linea: "+lexico.getLinea()+", columna: "+lexico.getColumna()+")");
		errNDecsTipo = err1;
		if (! err1){
			TS.addID(atrDecTipo.getId(),atrDecTipo.getProps());
			err2 = RDecsTipo();
			errNDecsTipo = errNDecsTipo || err2;
		}
		return errNDecsTipo; 
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean RDecsTipo() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKVAR)){
			return false; //Esto es lambda
		}
		boolean err1, err2, errRDecsTipo;
		Atributo atrDecTipo = DecTipo ();
		if (atrDecTipo != null)
			err1= atrDecTipo.getProps().getTipo().getId().equals("error");
		else return false;
		errRDecsTipo = err1;
		if (! err1){
			TS.addID(atrDecTipo.getId(),atrDecTipo.getProps());
			err2 = RDecsTipo();
			errRDecsTipo = errRDecsTipo || err2;
		}
		return errRDecsTipo;
		/*!(lexico.reconoce(CategoriaLexica.TKPROC))
		&&
		!(lexico.reconoce(CategoriaLexica.TKBEGIN)))*/
	}
	
	private Atributo DecTipo () throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Se esperaba un identificador en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		Token tk = lexico.lexer(); //consumo iden
		if (!lexico.reconoce(CategoriaLexica.TKIG)){
			throw new Exception("Se esperaba un '=' en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		lexico.lexer(); //consumo el "="
		Atributo tipo = new Atributo();
		tipo.setId(tk.getLexema());
		tipo.getProps().setClase("tipo");
		Tipo(tipo);
		boolean err1 = tipo.getProps().getTipo().getId().equals("error");
		//iden = Tipo (out err1) ;
		boolean err0 = TS.existeID(tk.getLexema()) || referenciaErronea(tipo.getProps().getTipo());
		if (err1 || err0 ){
			tipo.getProps().getTipo().setId("error");
		}
		return tipo;
	}
	
	private boolean DecsVar() throws Exception{
		boolean err0 = false;
		if (lexico.reconoce(CategoriaLexica.TKVAR)){
			lexico.lexer(); //Consumo VAR
			err0 = NDecsVar();
		}
		return err0;
	}
	
	private boolean NDecsVar() throws Exception{
		boolean errNDecsVar, err1, err2;
		Atributo atrDecVar = DecVar ();
		if (atrDecVar != null)
			err1= atrDecVar.getProps().getTipo().getId().equals("error");
		else throw new Exception("La declaracion de variables no es correcta (linea: "+lexico.getLinea()+", columna: "+lexico.getColumna()+")");
		errNDecsVar = err1;
		if (! err1){
			TS.addID(atrDecVar.getId(),atrDecVar.getProps());
			err2 = RDecsVar();
			errNDecsVar = errNDecsVar || err2;
		}
		return errNDecsVar; 
	}
	
	private boolean RDecsVar() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKBEGIN)){ //TODO: Ojo cambiar a TKPROC cuando se ponga la declaracion de procedimientos....
			return false; //Esto es lambda
		}
		boolean err1, err2, errRDecsVar;
		Atributo atrDecVar = DecVar ();
		if (atrDecVar != null)
			err1= atrDecVar.getProps().getTipo().getId().equals("error");
		else return false;
		errRDecsVar = err1;
		if (! err1){
			TS.addID(atrDecVar.getId(),atrDecVar.getProps());
			err2 = RDecsVar();
			errRDecsVar = errRDecsVar || err2;
		}
		return errRDecsVar;
	}
	
	private Atributo DecVar() throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Se esperaba un identificador en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		Token tk = lexico.lexer(); //consumo iden
		if (!lexico.reconoce(CategoriaLexica.TKDOSPUNTOS)){
			throw new Exception("Se esperaba ':' en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		lexico.lexer(); //consumo el ":"
		Atributo var = new Atributo();
		var.setId(tk.getLexema());
		var.getProps().setClase("var");
		//var.getProps().setTam(); El tamaño habrá que ponerlo en Tipo...
		Tipo(var);
		boolean err1 = var.getProps().getTipo().getId().equals("error");
		//iden = Tipo (out err1) ;
		boolean err0 = TS.existeID(tk.getLexema()) || referenciaErronea(var.getProps().getTipo());
		if (err1 || err0 ){
			var.getProps().getTipo().setId("error");
		}
		return var;
	}
	
	/**
	 * Reconoce el tipo de la variable declarada.
	 * 
	 * @throws Exception 
	 * @throws IOException 
	 */
	private void Tipo(Atributo atrib) throws Exception{
		Propiedades p;
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			lexico.lexer();
			atrib.getProps().setTam(1);
			//Tipo t = atrib.getProps().getTipo();
			if (atrib.getProps().getTipo()==null){
				atrib.getProps().setTipo(new Tipo());
			}
			atrib.getProps().getTipo().setT(Tipo.tipo.integer);
			atrib.getProps().getTipo().setTam(1);
			atrib.getProps().getTipo().setCampos(null);
			atrib.getProps().getTipo().setTBase(null);
			atrib.getProps().getTipo().setNElems(0);
			atrib.getProps().getTipo().setDesplazamiento(0);
			atrib.getProps().getTipo().setId("");
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			lexico.lexer();
			atrib.getProps().setTam(1);
			//Tipo t = atrib.getProps().getTipo();
			if (atrib.getProps().getTipo()==null){
				atrib.getProps().setTipo(new Tipo());
			}
			atrib.getProps().getTipo().setT(Tipo.tipo.bool);
			atrib.getProps().getTipo().setTam(1);
			atrib.getProps().getTipo().setCampos(null);
			atrib.getProps().getTipo().setTBase(null);
			atrib.getProps().getTipo().setNElems(0);
			atrib.getProps().getTipo().setDesplazamiento(0);
			atrib.getProps().getTipo().setId("");
		}
		else throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", los tipos son INTEGER o BOOLEAN");// incorrecto en linea " + lexico.getLinea());
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
				lexico.lexer();
			}
			else
				throw new Exception("Bloque de instrucciones tiene que estar entre BEGIN y END, en ("+lexico.getLinea()+","+lexico.getColumna()+")");		
		}
		else
			return true;
		return errBloque;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean Is() throws Exception{
		boolean errI = I();
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			lexico.lexer();
		} else throw new Exception("Se esperaba \";\" en la linea "+ lexico.getLinea()+ " columna "+ lexico.getColumna());
		errI = RIs() || errI;
		return errI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean RIs() throws Exception{
		boolean errI = false;
		if (lexico.reconoce(CategoriaLexica.TKIDEN)||
			lexico.reconoce(CategoriaLexica.TKBEGIN) || 
			lexico.reconoce(CategoriaLexica.TKREAD ) ||
			lexico.reconoce(CategoriaLexica.TKWRITE )||
			lexico.reconoce(CategoriaLexica.TKIF )||
			lexico.reconoce(CategoriaLexica.TKWHILE ))
		{	
		    errI = I();
			if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
				lexico.lexer();
			} else throw new Exception("Se esperaba \";\" en: linea "+lexico.getLinea()+", columna "+lexico.getColumna());
			errI = RIs() || errI;
		}
		return errI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean I() throws Exception{
		boolean errI = false;
		if (lexico.reconoce(CategoriaLexica.TKIDEN))
			errI = IAsig();
		else if (lexico.reconoce(CategoriaLexica.TKBEGIN)) 
			errI = Bloque();
		else  if (lexico.reconoce(CategoriaLexica.TKREAD ))
			errI = IRead();
		else if (lexico.reconoce(CategoriaLexica.TKWRITE ))
			errI = IWrite();
		else if (lexico.reconoce(CategoriaLexica.TKIF ))
			errI = IIf();
		else if (lexico.reconoce(CategoriaLexica.TKWHILE )){
			errI = IWhile();
		}
		return errI;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean IAsig() throws Exception{
		Token tk = lexico.lexer();
		if (!lexico.reconoce(CategoriaLexica.TKASIGN))
			throw new Exception ("Se esperaba \":=\" en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer();
		if (!TS.existeID(tk.getLexema())){
			System.out.println ("Error en linea: " + lexico.getLinea() +","+lexico.getColumna()+ " El identificador "+tk.getLexema()+  " no ha sido declarado antes");
			lexico.lexer();//FIXME:LImpiar		
			return true;
		}
		String tipoExpRel = ExpRel();
		Propiedades idTSProps = TS.getProps(tk.getLexema());
		if (!tipoExpRel.equals(idTSProps.getTipo())){
			System.out.println("Error de tipo en linea: " + lexico.getLinea() +", columna " + lexico.getColumna());
			return true;
		}
		codigo.emite("desapila-dir", idTSProps.getDir());
		etq++;
		return false;
	
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean IRead() throws Exception{
/*		if (!lexico.reconoce(CategoriaLexica.TKREAD )){
			return true;
		}
	*/
		Token tk = lexico.lexer(); //Consumo read
		if (!lexico.reconoce(CategoriaLexica.TKPAP))
			throw new Exception ("Se esperaba '(' ");
		tk = lexico.lexer();//Consumo (
		tk = lexico.lexer(); // Consumo iden
		if (!TS.existeID(tk.getLexema())){
			System.out.println ("Error en linea: " + lexico.getLinea() +","+lexico.getColumna()+" El identificador "+tk.getLexema() +" no ha sido declarado antes");
			return true;
		}
		if (!lexico.reconoce(CategoriaLexica.TKPCI))
			throw new Exception ("Se esperaba ')'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer();
		Propiedades idTSProps = TS.getProps(tk.getLexema());
		if (!idTSProps.getTipo().equals("int"))
			return true;
		codigo.emite("read");
		codigo.emite("desapila-dir", idTSProps.getDir());
		etq+=2;
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean IWrite() throws Exception{
		lexico.lexer(); //Consumo write
		if (!lexico.reconoce(CategoriaLexica.TKPAP ))
			throw new Exception ("Se esperaba '(' ");
		lexico.lexer(); //Consumo (
		String tipoExpRel = ExpRel();
		
		if (!lexico.reconoce(CategoriaLexica.TKPCI ))
			throw new Exception ("Se esperaba ')'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer(); //Consumo )
		/*if (!tipoExpRel.equals("int"))
			return true;*/
		codigo.emite("write");
		etq++;
		return tipoExpRel.equals("error");
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean IIf() throws Exception{
		boolean errIIf, errI, errPElse;
		int etqaux;
		lexico.lexer(); //Consumo IF
		if (!lexico.reconoce(CategoriaLexica.TKPAP ))
			throw new Exception ("Se esperaba '(' ");
		lexico.lexer(); //Consumo (
		String tipoExpRel = ExpRel();
		
		if (!lexico.reconoce(CategoriaLexica.TKPCI ))
			throw new Exception ("Se esperaba ')'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer(); //Consumo )
		
		if (!lexico.reconoce(CategoriaLexica.TKTHEN ))
			throw new Exception ("Se esperaba 'THEN'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer(); //Consumo THEN
		codigo.emite("ir-f");
		etqaux=etq;
		etq++;
		errI = I(); // compilo las instrucciones del IF
		codigo.parchea(etqaux, etq+1);
		errPElse = PElse(); //Compilo ELSE
		
		errIIf = (!tipoExpRel.equals("bool")) || errI || errPElse; 
		return errIIf;
	}
	
	private boolean PElse() throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKELSE )){
			return false;
		}
		lexico.lexer(); //Consumo ELSE
		codigo.emite("ir-a");
		int etqaux=etq;
		etq++;
		boolean errI = I();
		codigo.parchea(etqaux, etq); //parcheo el ir_a
		return errI;
	}
	
	/**
	 * 
	 */
	private boolean IWhile() throws Exception{
		boolean errW=false;
		int etqaux=etq;
		lexico.lexer(); //Consumo WHILE
		if (!lexico.reconoce(CategoriaLexica.TKPAP ))
			throw new Exception ("Se esperaba '(' ");
		lexico.lexer(); //Consumo (
		String tipoExpRel = ExpRel();
		
		if (!lexico.reconoce(CategoriaLexica.TKPCI ))
			throw new Exception ("Se esperaba ')'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer(); //Consumo )
		
		if (!lexico.reconoce(CategoriaLexica.TKDO ))
			throw new Exception ("Se esperaba 'DO' en el while ");
		lexico.lexer(); //Consumo DO
		
		codigo.emite("ir-f");
		int etqaux2 = etq;
		etq++;
		boolean errI = I();
		codigo.emite("ir-a",etqaux);
		etq++;
		codigo.parchea(etqaux2, etq);
		errW = (!tipoExpRel.equals("bool")) || errI;
		return errW;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String ExpRel() throws Exception{
		String tipo1 = ExpAd();
		return RExpRel (tipo1);
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String RExpRel(String tipoh) throws Exception{
		String tipo = "error";
		String cod = OpRel(); //Reconoce menorigual, igual, diferente, etc
		if (cod.length()>0){
			String tipo1 = ExpAd();
			if (comparables(tipo1,tipoh,cod))
				tipo = "bool";
			codigo.emite(cod);
			etq++;
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
		String tipo = "error";
		String tipo1 = "";
		String cod = OpAd();
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.equals("int") && tipo1.equals(tipoh))
				tipo = "int";
			codigo.emite(cod);
			etq++;
			return RExpAd(tipo);
		}
		cod = OpOr();
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.equals("bool") && tipo1.equals(tipoh))
				tipo = "bool";
			codigo.emite(cod);
			etq++;
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
	private String ExpMul() throws Exception{
		String tipo1 = Fact();
		return RExpMul(tipo1);
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String RExpMul(String tipoh) throws Exception{
		String tipo = "error";
		String tipo1 = "";
		String cod = OpMul(); //Reconoce * y div, etc
		if (cod.length()>0){
			tipo1 = Fact();
			if (tipoh.equals("int") && tipo1.equals(tipoh))
				tipo = "int";
			codigo.emite(cod);
			etq++;
			return RExpMul(tipo);
		}
		cod = OpAnd();
		if (cod.length()>0){
			tipo1 = Fact();
			if (tipoh.equals("bool") && tipo1.equals(tipoh))
				tipo = "bool";
			codigo.emite(cod);
			etq++;
			return RExpMul(tipo);
		}
		else //lambda
			return tipoh;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private String Fact() throws Exception{
		String tipo0 = "error";
		if (lexico.reconoce(CategoriaLexica.TKNUM)){
			tipo0 = "int";
			lexico.lexer(); //Cosumimos el entero
			codigo.emite("apila", Integer.parseInt(lexico.getLookahead().getLexema()));
			etq++;
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKTRUE)) {
			tipo0 = "bool";
			codigo.emite("apila",1);
			etq++;
			lexico.lexer(); //Cosumimos 'true'
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKFALSE)) {
			tipo0 = "bool";
			codigo.emite("apila",0);
			etq++;
			lexico.lexer(); //Cosumimos 'false'
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKPAP)) {
			lexico.lexer(); //Cosumimos '('
			tipo0 = ExpRel();
			if (!lexico.reconoce(CategoriaLexica.TKPCI)){
				throw new Exception(" Error de parentizacion, en linea: " + lexico.getLinea() +", columna "+ lexico.getColumna());
			}
			lexico.lexer(); //Cosumimos ')'
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKIDEN)) {
			Token tk = lexico.lexer(); //Consumimos el iden
			Propiedades propsLexema = (Propiedades)TS.getTabla().get(tk.getLexema());
			if (TS.existeID(tk.getLexema())){
				tipo0 = propsLexema.getTipo();
				int dir = propsLexema.getDir();
				codigo.emite("apila-dir", dir);
				etq++;
			}
			else{
				tipo0="error";
			}
			return tipo0;
		}

		if (lexico.reconoce(CategoriaLexica.TKNOT)){
			String op = OpUnarioLog(); //Consumimos operador unario logico
			tipo0 = Fact();
			codigo.emite(op);
			etq++;
			if (!tipo0.equalsIgnoreCase("bool")){
					tipo0="error";
				}
			else{
				tipo0="bool";
			}
			return tipo0;
		}
		
		if ( (lexico.reconoce(CategoriaLexica.TKSUMA)) || (lexico.reconoce(CategoriaLexica.TKRESTA))){
			String op = OpUnarioArit(); //Consumimos operador unario aritmetico
			tipo0 = Fact();
			codigo.emite(op);
			etq++;
			if (!tipo0.equalsIgnoreCase("int")){
					//atrFact = "error";
			}
			else{
				tipo0 = "int";
			}
			return tipo0;
		}
		
		tipo0 = "error";
		return tipo0;
	}
	

	
	/**
	 * 
	 * @param opDeOpAnd
	 * @throws Exception 
	 */
	private String OpAnd() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKAND)){
			lexico.lexer();
			return("and");
		}
		else return "";
	}	
	
	/**
	 * 
	 * @param opDeOpOr
	 */
	private String OpOr() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKOR)){
			lexico.lexer();
			return("or");
		}
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpAd
	 */
	private String OpAd() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKSUMA)){
			lexico.lexer();
			return("suma");
		}
		if (lexico.reconoce(CategoriaLexica.TKRESTA)){
			lexico.lexer();
			return("resta");
		}
		else return "";
	}
		
	
	/**
	 * 
	 * @param opDeOpMul
	 */
	private String OpMul() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKMULT)){
			lexico.lexer();
			return("multiplica");
		}
		if (lexico.reconoce(CategoriaLexica.TKDIV)){
			lexico.lexer();
			return("divide");
		}
		if (lexico.reconoce(CategoriaLexica.TKMOD)){
			lexico.lexer();
			return("modulo");
		}
		else return "";
	}
	
	private boolean comparables (String tipo0, String tipo1, String operador){
		if (operador.equals("igual")|| operador.equals("distinto"))
			return (tipo0.equals(tipo1) && !tipo0.equals("error"));
		else return(tipo0.equals(tipo1) && tipo1.equals("int"));
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 * @return
	 */
	private String OpUnarioArit() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKSUMA)){
			lexico.lexer();
			return("mas");
		}	
		else if (lexico.reconoce(CategoriaLexica.TKRESTA)){
			lexico.lexer();
			return("menos");
		}
		else return "";
	}
	
	/**
	 * 
	 * @param opDeOpUn
	 * @return
	 */
	private String OpUnarioLog() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKNOT)){
			lexico.lexer();
			return("not");
		}
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
	
	/**
	 * 
	 * @param t
	 * @return
	 * @throws Exception
	 */
	private boolean referenciaErronea (Tipo t) throws Exception{
		return ((t.getT().equals("ref")) && !(TS.existeID(t.getId())));
	}
	
	
/************************************VERSION VIEJA******************************************************/
	/**
	 * Reconoce los tokens de inicio de programa, leyendo seguidamente el nombre del programa.
	 * @throws Exception Si sucede algún error en la cabecera del programa.
	 */
	/*private void ProgDec() throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKPROGRAM)){
			lexico.lexer();
			if (lexico.reconoce(CategoriaLexica.TKIDEN)){
				Token tk = lexico.lexer(); //consumo iden
				TS = new tablaSimbolos();
				TS.addID(tk.getLexema(),"", 0);
				if (lexico.reconoce(CategoriaLexica.TKPYCOMA))
					lexico.lexer();
				else throw new Exception ("Se esperaba \";\" en "+lexico.getLinea()+","+lexico.getColumna());		
				
			}else{
				throw new Exception("El Programa tiene que tener un nombre en "+lexico.getLinea()+","+lexico.getColumna());		
			}
	
		}
		else throw new Exception ("Se esperaba \"PROGRAM\" en "+lexico.getLinea()+","+lexico.getColumna());			
	}

	
	
	*//**
	 * Procesa la seccion de declaraciones de las variables.
	 * 
	 * @return errorDecs: Es cierto si ocurre algún error en las declaraciones de variables.
	 * @throws Exception Si ocurre algún error sintáctico en la declaración de alguna variable.
	 *//*
	private boolean Decs() throws Exception{
		Atributo atrDec = new Atributo();
        Dec(atrDec);		
		boolean err0 = TS.existeID(atrDec.getId());
		if (err0)
			System.out.println("El identificador " +
					atrDec.getId() + " esta repetido en "+lexico.getLinea()+","+lexico.getColumna());		
		else TS.addID(atrDec.getId(),atrDec.getTipo(), 0);
		return RDecs(err0,0);		
	}
	
	*//**
	 * Método derivado de eliminar la recursión a izquierdas de Decs.
	 * @return error Devuelve cierto si ocurre algún error en la declaración de variables.
	 * @throws Exception  Si ocurre algún error sintáctico en la declaración de alguna variable.
	 *//*
	private boolean RDecs (boolean errh0, int dirh0) throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKIDEN)){
			Atributo atrDec = new Atributo();
			Dec(atrDec);
			boolean errh1aux = TS.existeID(atrDec.getId());
			if (errh1aux)
				System.out.println("El identificador " +
						atrDec.getId() + " esta repetido en "+lexico.getLinea()+","+lexico.getColumna());		
			else TS.addID(atrDec.getId(),atrDec.getTipo(), dirh0+1);
			errh0 = errh0 || errh1aux;
			errh0 = RDecs (errh0, dirh0+1) || errh0;
		}
		return errh0;
	}
		
	

	
	*//**
	 * Reconoce una declaración de variable.
	 * 
	 * @throws Exception si ocurre algún error sintáctico en la declaración de la variable.
	 *//*
	private void Dec(Atributo atrib) throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Declaracion incorrecta en linea " + lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		Token tk = lexico.lexer(); //consumo el iden
		atrib.setId(tk.getLexema());

		if (!lexico.reconoce(CategoriaLexica.TKDOSPUNTOS )){
			throw new Exception("Se esperaba \":\" en linea " + lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		tk = lexico.lexer(); //consumo :
		
		Tipo(atrib);
		if (lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			lexico.lexer();
		} else throw new Exception("Se esperaba \";\" en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
	}*/

}