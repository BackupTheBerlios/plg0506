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
	private boolean Prog() throws Exception{
		boolean errProg = false;
		ProgDec();
		if (lexico.reconoce(CategoriaLexica.TKVAR)){
			lexico.lexer();//consumo VAR
			errProg = Decs();
			errProg = Bloque() || errProg;
			if (lexico.reconoce(CategoriaLexica.TKPUNTO)){
				lexico.lexer();//consumo .
				codigo.emite("stop");	
System.out.println(codigo.getString());
			}
			else throw new Exception ("Se esperaba \".\" en "+lexico.getLinea()+","+lexico.getColumna());			
		}
		else throw new Exception ("Se esperaba \"VAR\" en "+lexico.getLinea()+","+lexico.getColumna());			
		return errProg;		
	}

	
	
	/**
	 * Reconoce los tokens de inicio de programa, leyendo seguidamente el nombre del programa.
	 * @throws Exception Si sucede algún error en la cabecera del programa.
	 */
	private void ProgDec() throws Exception{
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

	
	
	/**
	 * Procesa la seccion de declaraciones de las variables.
	 * 
	 * @return errorDecs: Es cierto si ocurre algún error en las declaraciones de variables.
	 * @throws Exception Si ocurre algún error sintáctico en la declaración de alguna variable.
	 */
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
	
	/**
	 * Método derivado de eliminar la recursión a izquierdas de Decs.
	 * @return error Devuelve cierto si ocurre algún error en la declaración de variables.
	 * @throws Exception  Si ocurre algún error sintáctico en la declaración de alguna variable.
	 */
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
		
	

	
	/**
	 * Reconoce una declaración de variable.
	 * 
	 * @throws Exception si ocurre algún error sintáctico en la declaración de la variable.
	 */
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
	}
	
	/**
	 * Reconoce el tipo de la variable declarada.
	 * 
	 * @throws Exception 
	 * @throws IOException 
	 */
	private void Tipo(Atributo atrib) throws Exception{
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			lexico.lexer();
			atrib.setTipo("int");
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			lexico.lexer();
			atrib.setTipo("bool");
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
				throw new Exception("Bloque de instrucciones tiene que estar entre BEGIN y END, en "+lexico.getLinea()+","+lexico.getColumna());		
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
			lexico.reconoce(CategoriaLexica.TKWRITE ))
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
			return true;
		}
		String tipoExpRel = ExpRel();
		propiedades idTSProps = TS.getProps(tk.getLexema());
		if (!tipoExpRel.equals(idTSProps.getTipo())){
			System.out.println("Error de tipo en linea: " + lexico.getLinea() +", columna " + lexico.getColumna());
			return true;
		}
		codigo.emite("desapila-dir", idTSProps.getDir());
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
		propiedades idTSProps = TS.getProps(tk.getLexema());
		if (!idTSProps.getTipo().equals("int"))
			return true;
		codigo.emite("read");
		codigo.emite("desapila-dir", idTSProps.getDir());
		return false;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private boolean IWrite() throws Exception{
/*		if (!lexico.reconoce(CategoriaLexica.TKWRITE )){
			//throw new Exception ("Se esperaba la palabra reservada write");
			return true;
		}
*/
		lexico.lexer(); //Consumo write
		if (!lexico.reconoce(CategoriaLexica.TKPAP ))
			throw new Exception ("Se esperaba '(' ");
		lexico.lexer(); //Consumo (
		String tipoExpAd = ExpAd();
		
		if (!lexico.reconoce(CategoriaLexica.TKPCI ))
			throw new Exception ("Se esperaba ')'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer(); //Consumo )
		if (!tipoExpAd.equals("int"))
			return true;
		codigo.emite("write");
		return false;
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
			return RExpAd(tipo);
		}
		cod = OpOr();
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.equals("bool") && tipo1.equals(tipoh))
				tipo = "bool";
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
			return RExpMul(tipo);
		}
		cod = OpAnd();
		if (cod.length()>0){
			tipo1 = Fact();
			if (tipoh.equals("bool") && tipo1.equals(tipoh))
				tipo = "bool";
			codigo.emite(cod);
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
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKTRUE)) {
			tipo0 = "bool";
			codigo.emite("apila",1);
			lexico.lexer(); //Cosumimos 'true'
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKFALSE)) {
			tipo0 = "bool";
			codigo.emite("apila",0);
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
			propiedades propsLexema = (propiedades)TS.getTabla().get(tk.getLexema());
			if (TS.existeID(tk.getLexema())){
				tipo0 = propsLexema.getTipo();
				int dir = propsLexema.getDir();
				codigo.emite("apila-dir", dir);
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
			if (!tipo0.equalsIgnoreCase("bool")){
					tipo0="error";
					throw new Exception("Error de tipos en "+lexico.getLinea()+","+lexico.getColumna());		
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
			if (!tipo0.equalsIgnoreCase("int")){
					//atrFact = "error";
					throw new Exception("Error de tipos en "+lexico.getLinea()+","+lexico.getColumna());		
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
		else return(tipo0.equals(tipo1) && tipo1.equals("integer"));
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

}