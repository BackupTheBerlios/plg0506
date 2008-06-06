package procesador;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

//		codigo.inicio();
//		etq = codigo.longInicio;
//		codigo.emite("ir-a");//TODO: Luego hay que parchearlo
		int etqaux = etq;
//		etq++;
		
		boolean errRProg = RProg();
		
		errProg = errRProg;
		if (lexico.reconoce(CategoriaLexica.TKPUNTO)){
			lexico.lexer();//consumo .
//			codigo.parcheaInicio(0, etq, dir);
//			codigo.parchea(etqaux, etq);
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
		err1= atrDecTipo.getProps().getTipo().getT().equals(Tipo.tipo.error);
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
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			throw new Exception("Se esperaba un ; "+lexico.getLinea()+ " columna" + lexico.getColumna());			
		}
		lexico.lexer(); // consumo ;
		if ((lexico.reconoce(CategoriaLexica.TKVAR)) || 
				(lexico.reconoce(CategoriaLexica.TKPROC)) || 
				(lexico.reconoce(CategoriaLexica.TKBEGIN))){
			return false; //Esto es lambda
		}
		boolean err2;
		boolean errRDecsTipo = false;		
		Atributo atrDecTipo = DecTipo();
		if (atrDecTipo.getProps().getTipo().getT()!= null)
			errRDecsTipo = atrDecTipo.getProps().getTipo().getT().equals(Tipo.tipo.error);
		if (! errRDecsTipo){
			TS.addID(atrDecTipo.getId(),atrDecTipo.getProps());
			err2 = RDecsTipo();
			errRDecsTipo = errRDecsTipo || err2;
		}
		return errRDecsTipo;
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
		tipo = Tipo();
		if (tipo.getId().equals(""))
			tipo.setId(tk.getLexema());
		tipo.getProps().setClase("tipo");
		boolean err1 = false;
		if (tipo.getProps().getTipo().getT() != null)
			err1 = err1 || tipo.getProps().getTipo().getT().equals(Tipo.tipo.error);
		boolean err0 = TS.existeID(tk.getLexema()) || referenciaErronea(tipo.getProps().getTipo());
		if (err1 || err0 ){
			tipo.getProps().getTipo().setT(Tipo.tipo.error);
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
		boolean errNDecsVar, err2;
		Atributo atrDecVar = DecVar ();
		errNDecsVar = atrDecVar.getProps().getTipo().getT() == Tipo.tipo.error;
		if (! errNDecsVar){
			atrDecVar.getProps().setDir(dir);
			TS.addID(atrDecVar.getId(),atrDecVar.getProps());
			dir += atrDecVar.getProps().getTipo().getTam();
			err2 = RDecsVar();
			errNDecsVar = errNDecsVar || err2;
		}
		return errNDecsVar; 
	}
	
	private boolean RDecsVar() throws Exception{
		boolean err2, errRDecsVar;
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			throw new Exception("Se esperaba un ; "+lexico.getLinea()+ " columna" + lexico.getColumna());			
		}	
		lexico.lexer(); // consumo ;
		if ((lexico.reconoce(CategoriaLexica.TKPROC)) ||
				(lexico.reconoce(CategoriaLexica.TKBEGIN))){ 
			return false; //Esto es lambda
		}
		Atributo atrDecVar = DecVar ();
		errRDecsVar = atrDecVar.getProps().getTipo().getT().equals(Tipo.tipo.error);
		if (! errRDecsVar){
			atrDecVar.getProps().setDir(dir);
			TS.addID(atrDecVar.getId(),atrDecVar.getProps());
			dir += atrDecVar.getProps().getTipo().getTam();
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
		var = Tipo();
		if (var.getId().equals(""))
			var.setId(tk.getLexema());
		var.getProps().setClase("var");
		boolean err1 = var.getProps().getTipo().getT() == Tipo.tipo.error;
		boolean err0 = TS.existeID(tk.getLexema()) || referenciaErronea(var.getProps().getTipo());
		if (err1 || err0 ){
			var.getProps().getTipo().setT(Tipo.tipo.error);
		}
		return var;
	}
	
	/**
	 * Reconoce el tipo de la variable declarada.
	 * 
	 * @throws Exception 
	 * @throws IOException 
	 */
	private Atributo Tipo() throws Exception{
		Atributo atrib = new Atributo();
		//boolean errC ;
		if (lexico.reconoce(CategoriaLexica.TKINT)){
			lexico.lexer();
			atrib.getProps().setTipo(new Tipo(Tipo.tipo.integer));
		}
		else if (lexico.reconoce(CategoriaLexica.TKBOOL)){
			lexico.lexer();
			atrib.getProps().setTipo(new Tipo(Tipo.tipo.bool));
		}
		else if(lexico.reconoce(CategoriaLexica.TKRECORD)){
			lexico.lexer(); // Consumo RECORD
			//En Campos modifico el tamaño y el desplazamiento de Tipo con el tamaño de los Campos. 
			//Y meto los Campos en Tipo
			Tipo t = Campos();
			atrib.getProps().setTipo(t);
			atrib.getProps().getTipo().setT(Tipo.tipo.rec);
		}
		else if(lexico.reconoce(CategoriaLexica.TKARRAY)){
			lexico.lexer(); //Consumo array
			if(!lexico.reconoce(CategoriaLexica.TKCORCHAP)){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			lexico.lexer(); //Consumo [
			if(!lexico.reconoce(CategoriaLexica.TKNUM)){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			Token tk = lexico.lexer(); // Consumo el cero
			int inicio =  Integer.parseInt(tk.getLexema());
			if (inicio != 0){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			if(!lexico.reconoce(CategoriaLexica.TKPUNTOPUNTO)){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			lexico.lexer(); // Consumo el ".."
			if(!lexico.reconoce(CategoriaLexica.TKNUM)){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			tk = lexico.lexer(); // Consumo el final del array
			int fin =  Integer.parseInt(tk.getLexema());
			if(!lexico.reconoce(CategoriaLexica.TKCORCHCI)){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			lexico.lexer(); //Consumo ]
			if(!lexico.reconoce(CategoriaLexica.TKOF)){
				throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", revise la declaracion de array");
			}
			lexico.lexer(); //Consumo OF
			// Ahora tengo que reconocer el tipo de los elementos del array
			Atributo tbase = new Atributo();
			tbase = Tipo();
			atrib.getProps().setTipo(new Tipo(Tipo.tipo.array,"",tbase.getProps().getTipo().getTam()*(fin+1)));
			atrib.getProps().getTipo().setTBase(tbase.getProps().getTipo());
		}
		else if(lexico.reconoce(CategoriaLexica.TKIDEN)){
			Token tk = lexico.lexer();
			int tam_ref = TS.getProps(tk.getLexema()).getTipo().getTam();
			atrib.getProps().setTipo(new Tipo(Tipo.tipo.ref,tk.getLexema(),tam_ref));
		}
		else throw new Exception("Error en linea "+lexico.getLinea()+ ", columna "+ lexico.getColumna()+", el tipo es incorrecto");// incorrecto en linea " + lexico.getLinea());
		return atrib;
	}
	
	private Tipo Campos () throws Exception{
		Tipo tipo = new Tipo(Tipo.tipo.rec);
		int desp1 = 0;
		Atributo c = Campo(desp1);
		Hashtable<Object, Object> campos = new Hashtable<Object, Object>();
		boolean err = false;
		int desph = 0;
		if (!c.getId().equals("error")){
			//campos.put(c.getId(),c.getProps());
			desph = c.getProps().getTipo().getTam();
			boolean err1 = RCampos(desph,campos,c); 
			err = err || err1;
		}
		if (!err){
			//int tam1 = c.getProps().getTipo().getTam(); 
			tipo.setCampos(campos);
			int tam = ponTamano(campos);
			tipo.setTam(tam);
		}
		else
			tipo.setT(Tipo.tipo.error);
		return tipo;
	}
	
	
	private boolean RCampos(int desph,Hashtable<Object,Object> campos,Atributo c) throws Exception {
		boolean errCampos = false;
		if ((!c.getProps().getTipo().getT().equals(Tipo.tipo.error))||(!campos.containsKey(c.getId()))){
				campos.put(c.getId(), c.getProps());
		}else
				errCampos = true;
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){
			return errCampos;
		}
		lexico.lexer();
		if (lexico.reconoce(CategoriaLexica.TKEND)){
			lexico.lexer();
			return errCampos;
		}
		c = Campo(desph);
		if (!c.getId().equals("error")){
			//campos.put(c.getId(),c.getProps());
			desph = desph + c.getProps().getTipo().getTam();
			errCampos = errCampos || (RCampos(desph,campos,c));
		}
		return errCampos;
	}

	private Atributo Campo (int desp) throws Exception{
		Atributo campo = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception("Ses esperaba un identificador en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		Token tk = lexico.lexer(); //consumo iden
		if (!lexico.reconoce(CategoriaLexica.TKDOSPUNTOS)){
			throw new Exception("Se esperaba : en la linea "+lexico.getLinea()+ " columna" + lexico.getColumna());
		}
		lexico.lexer(); //consumo :
		campo = Tipo();
		if (campo.getId().equals("")){
			campo.setId(tk.getLexema());
			campo.getProps().getTipo().setDesplazamiento(desp);
		}
		boolean err1 = false;
		if (campo.getProps().getTipo().getT() != null)
			err1 = campo.getProps().getTipo().getT().equals(Tipo.tipo.error);
		boolean err0 = TS.existeID(tk.getLexema()) || referenciaErronea(campo.getProps().getTipo());
		if (err1 || err0 ){
			campo.getProps().getTipo().setT(Tipo.tipo.error);
		}
		return campo;
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
		//Token tk = lexico.lexer();
		Atributo tipoMem = Mem();
		if (!lexico.reconoce(CategoriaLexica.TKASIGN))
			throw new Exception ("Se esperaba \":=\" en "+lexico.getLinea()+","+lexico.getColumna());		
		Token tk = lexico.lexer();
		
		Tipo tipoExpRel = ExpRel();
		
		Propiedades idTSProps = TS.getProps(tipoMem.getId());		
		if (!compatibles(idTSProps.getTipo(), tipoExpRel)){
			return true;
		}else if (compatibles(idTSProps.getTipo(), new Tipo(Tipo.tipo.integer)) || compatibles(idTSProps.getTipo(),new Tipo(Tipo.tipo.bool)))
			codigo.emite("desapila-ind");
		else
			codigo.emite("mueve");

		/*if (!tipoExpRel.equals(idTSProps.getTipo())){
			System.out.println("Error de tipo en linea: " + lexico.getLinea() +", columna " + lexico.getColumna());
			return true;
		}
		codigo.emite("desapila-dir", idTSProps.getDir());*/
		etq++;
		return false;
	
	}
	
	private boolean compatibles(Tipo t1, Tipo t2){
		Vector visitados = new Vector();
		return compatibles2(t1, t2, visitados);
	}
	
	private boolean compatibles2 (Tipo t1, Tipo t2, Vector visitados){
		Tupla pareja = new Tupla (t1,t2);
		if (visitados.contains(pareja))
			return true;
		else{
			visitados.add(pareja);
			if ((t1.getT() == t2.getT()) && 
					(t1.getT() == Tipo.tipo.integer || 
					t1.getT() == Tipo.tipo.bool))
				return true;
			else if (t1.getT() == Tipo.tipo.ref){
					t1 = (Tipo)((Propiedades)TS.getTabla().get(t1.getId())).getTipo();
					return compatibles2(t1,t2,visitados);
			}
			else if (t2.getT() == Tipo.tipo.ref){
					t2 = (Tipo)((Propiedades)TS.getTabla().get(t2.getId())).getTipo();
					return compatibles2(t1,t2,visitados);
			}
			else if ((t1.getT() == t2.getT()) && 
						(t1.getT() == Tipo.tipo.array) &&
						(t1.getNElems() == t2.getNElems())){
					return compatibles2(t1.getTBase(),t2.getTBase(),visitados);				
			}			
			else if ((t1.getT() == Tipo.tipo.array) &&
					(t2.getT() != Tipo.tipo.array)) {
					//System.out.println("\n Estoy en linea de codigo "+ lexico.getLinea());
					//System.out.println("Voy a llamar con t2: "+ t2.getT());
					return compatibles2(t1.getTBase(),t2,visitados);
			}
			else if ((t2.getT() == Tipo.tipo.array) &&
					(t1.getT() != Tipo.tipo.array)) {
						return compatibles2(t1,t2.getTBase(),visitados);
			}
			else if((t1.getT() == t2.getT()) && 
						(t1.getT() == Tipo.tipo.rec) &&
						(t1.getCampos().size() == t2.getCampos().size())) {
					Enumeration e = t1.getCampos().keys();
					while (e.hasMoreElements()) {
						Object key = e.nextElement();
						Tipo tipo1, tipo2;
						tipo1 = (Tipo)t1.getCampos().get(key);
						tipo2 = (Tipo)t2.getCampos().get(key);
						if (!compatibles2(tipo1, tipo2, visitados)) {
							return false;
						}
					}
					return true;
			}
			/*else if ((t1.getTipo().equals(t2.getTipo())) && 
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
			}*/
			else
				return false;
		}
	}


	/*private void compatibles2(ArrayList<Set <Tipo.tipo>> visitadas, Tipo tipoMem, Tipo tipoExpRel) {
		// TODO Auto-generated method stub
		Set <Tipo.tipo> par = Collections.synchronizedSet(EnumSet.noneOf(Tipo.tipo.class));
		par.add(tipoMem.getT());
		par.add(tipoExpRel.getT());
		
		//return tipoMem, tipoRel in visitadas
	}*/

	/**
	 * bu
	 */
	private Atributo Mem() throws Exception{
		if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
			throw new Exception ("Se esperaba un iden en "+lexico.getLinea()+","+lexico.getColumna());
		}
		Tipo t;
		Atributo tRMem = new Atributo();
		Token tk = lexico.lexer();
		if (!TS.existeID(tk.getLexema()) || !TS.getProps(tk.getLexema()).getClase().equals("var")){
			tRMem.getProps().getTipo().setT(Tipo.tipo.error);
			return tRMem;
		}
		t = ref(TS.getProps(tk.getLexema()).getTipo() );
//////////Codigo para procs
		//Propiedades p = new Propiedades();
		//p = TS.getProps(tk.getLexema());
		if (TS.getProps(tk.getLexema())!=null){
			codigo.emite("apila", TS.getProps(tk.getLexema()).getDir());
			etq++;
		}
		
		//codigo.accesoVar(p);
		//etq = etq + codigo.longAccesoVar(p);
		tRMem.setId(tk.getLexema());
		tRMem.getProps().setTipo(RMem(t));
		// TODO Auto-generated method stub
		return tRMem;
	}

	/**
	 * 
	 * @param exp
	 * @return
	 * @throws Exception
	 */
	private Tipo ref(Tipo exp) throws Exception{
		 if (exp.getT() == Tipo.tipo.ref){
             if (TS.existeID(exp.getId()))
                     return ref(TS.getProps(exp.getId()).getTipo());
             else
                     return new Tipo(Tipo.tipo.error);
     }
     else return exp;
	}
	
	/**
	 * 
	 * @param mem
	 * @return
	 * @throws Exception
	 */
	private Tipo RMem (Tipo mem) throws Exception{
		Tipo tRMem = new Tipo();
		Token tk;
		if (lexico.reconoce(CategoriaLexica.TKPUNTO)){ //Ahora reconocere ".iden"
			lexico.lexer(); // Consumo el "."
			if (!lexico.reconoce(CategoriaLexica.TKIDEN)){
				throw new Exception ("Se esperaba un iden en "+lexico.getLinea()+","+lexico.getColumna());
			}
			tk = lexico.lexer();
			if (mem.getT().equals(Tipo.tipo.rec)){
				if(hasCampo(mem.getCampos(), tk.getLexema())){
					tRMem = ref(((Propiedades)mem.getCampos().get(tk.getLexema())).getTipo());
				}
				else{
					tRMem.setT(Tipo.tipo.error);
				}
			}
			else{
				tRMem.setT(Tipo.tipo.error);
			}
			codigo.emite("apila", ((Propiedades)mem.getCampos().get(tk.getLexema())).getTipo().getDesplazamiento());
			codigo.emite("suma");
			etq = etq + 2;
			Tipo t = RMem(tRMem);
			return t;
		}
		else if (lexico.reconoce(CategoriaLexica.TKCORCHAP)){ //Ahora reconocere ".iden"
			lexico.lexer(); //Consumo '['
			Tipo tExpAd = ExpAd();
			if (!lexico.reconoce(CategoriaLexica.TKCORCHCI)){ //Ahora reconocere ".iden"
				throw new Exception ("Se esperaba ']' en "+lexico.getLinea()+","+lexico.getColumna());
			}
			lexico.lexer();
			if (mem.getT().equals(Tipo.tipo.array) && tExpAd.getT().equals(Tipo.tipo.integer)){
				tRMem = ref(mem.getTBase());
			}
			else{
				tRMem.setT(Tipo.tipo.error);
			}
			codigo.emite("apila", mem.getTBase().getTam());
			codigo.emite("multiplica");
			codigo.emite("suma");
			etq=etq+3;
			return RMem(tRMem);
		}
		else{
			return mem;
		}
	}
	
	private boolean hasCampo(Hashtable<Object, Object> campos, String lexema) {
		// TODO Auto-generated method stub
		return campos.containsKey(lexema);
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
		if (idTSProps.getTipo().getT() != Tipo.tipo.integer)
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
		Tipo tipoExpRel = ExpRel();
		
		if (!lexico.reconoce(CategoriaLexica.TKPCI ))
			throw new Exception ("Se esperaba ')'  en "+lexico.getLinea()+","+lexico.getColumna());		
		lexico.lexer(); //Consumo )
		/*if (!tipoExpRel.equals("int"))
			return true;*/
		codigo.emite("write");
		etq++;
		return tipoExpRel.getT() == Tipo.tipo.error;
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
		Tipo tipoExpRel = ExpRel();
		
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
		
		errIIf = (tipoExpRel.getT() != Tipo.tipo.bool) || errI || errPElse; 
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
		Tipo tipoExpRel = ExpRel();
		
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
		errW = (tipoExpRel.getT() != Tipo.tipo.bool) || errI;
		return errW;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Tipo ExpRel() throws Exception{
		return RExpRel(ExpAd());
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Tipo RExpRel(Tipo tipoh) throws Exception{
		//String tipo = "error";
		Tipo tipo = new Tipo();
		tipo.setT(Tipo.tipo.error);
		String cod = OpRel(); //Reconoce menorigual, igual, diferente, etc
		if (cod.length()>0){
			Tipo tipo1 = ExpAd();
			if (comparables(tipo1,tipoh,cod))
				tipo.setT(Tipo.tipo.bool);
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
	private Tipo ExpAd() throws Exception{
		return RExpAd(ExpMul());
	}

	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Tipo RExpAd(Tipo tipoh) throws Exception {
		Tipo tipo1, tipo = new Tipo(Tipo.tipo.error);
		String cod = OpAd();
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.getT() == Tipo.tipo.integer && tipo1.getT() == tipoh.getT())
				tipo.setT(Tipo.tipo.integer);
			codigo.emite(cod);
			etq++;
			return RExpAd(tipo);
		}
		cod = OpOr();
		if (cod.length()>0){
			tipo1 = ExpMul();
			if (tipoh.getT() == Tipo.tipo.bool && tipo1.getT() == tipoh.getT()) {
				tipo.setT(Tipo.tipo.bool);
			}
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
	private Tipo ExpMul() throws Exception{
		return RExpMul(Fact());
	}


	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private Tipo RExpMul(Tipo tipoh) throws Exception{
		Tipo tipo1, tipo = new Tipo(Tipo.tipo.error);
		String cod = OpMul(); //Reconoce * y div, etc
		if (cod.length()>0){
			tipo1 = Fact();
			if (tipoh.getT() == Tipo.tipo.integer && tipo1.getT() == tipoh.getT())
				tipo.setT(Tipo.tipo.integer);
			codigo.emite(cod);
			etq++;
			return RExpMul(tipo);
		}
		cod = OpAnd();
		if (cod.length()>0){
			tipo1 = Fact();
			if (tipoh.getT() == Tipo.tipo.bool && tipo1.getT() == tipoh.getT())
				tipo.setT(Tipo.tipo.bool);
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
	private Tipo Fact() throws Exception{
		Tipo tipo0 = new Tipo(Tipo.tipo.error);
		if (lexico.reconoce(CategoriaLexica.TKNUM)){
			tipo0.setT(Tipo.tipo.integer);
			lexico.lexer(); //Cosumimos el entero
			codigo.emite("apila", Integer.parseInt(lexico.getLookahead().getLexema()));
			etq++;
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKTRUE)) {
			tipo0.setT(Tipo.tipo.bool);
			codigo.emite("apila",1);
			etq++;
			lexico.lexer(); //Cosumimos 'true'
			return tipo0;
		}
		if (lexico.reconoce(CategoriaLexica.TKFALSE)) {
			tipo0.setT(Tipo.tipo.bool);
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
			Atributo atrFact = Mem();
			if (compatibles(atrFact.getProps().getTipo(),new Tipo(Tipo.tipo.integer)) || 
					compatibles(atrFact.getProps().getTipo(),new Tipo(Tipo.tipo.bool))){
				codigo.emite("apila-ind");
				etq++;
			}
			return atrFact.getProps().getTipo();
		}

		if (lexico.reconoce(CategoriaLexica.TKNOT)){
			String op = OpUnarioLog(); //Consumimos operador unario logico
			tipo0 = Fact();
			codigo.emite(op);
			etq++;
			if (tipo0.getT() != Tipo.tipo.bool){
					tipo0.setT(Tipo.tipo.error);
				}
			else{
				tipo0.setT(Tipo.tipo.bool);
			}
			return tipo0;
		}
		
		if ( (lexico.reconoce(CategoriaLexica.TKSUMA)) || (lexico.reconoce(CategoriaLexica.TKRESTA))){
			String op = OpUnarioArit(); //Consumimos operador unario aritmetico
			tipo0 = Fact();
			codigo.emite(op);
			etq++;
			if (tipo0.getT() != Tipo.tipo.integer){
					//atrFact = "error";
			}
			else{
				tipo0.setT(Tipo.tipo.integer);
			}
			return tipo0;
		}
		
		tipo0.setT(Tipo.tipo.error);
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
	
	private boolean comparables (Tipo tipo0, Tipo tipo1, String operador) {
		if (operador.equals("igual")|| operador.equals("distinto"))
			return (tipo0.getT() == tipo1.getT() && tipo0.getT() != Tipo.tipo.error);
		else return(tipo0.getT() == tipo1.getT() && tipo1.getT() == Tipo.tipo.integer);
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
		return ((t.getT() == Tipo.tipo.ref) && !(TS.existeID(t.getId())));
	}
	
	private int ponTamano(Hashtable<Object, Object> campos){
		int t = 0;
		for (Enumeration<Object> e = campos.keys() ; e.hasMoreElements() ;) {
	         String key = (String)e.nextElement();
	         Propiedades value = (Propiedades)(campos.get(key)); 
	         t += value.getTipo().getTam(); 
	     }
		return t;
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