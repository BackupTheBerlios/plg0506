package procesador;

import java.io.RandomAccessFile;
import java.util.Vector;
import tablaSimbolos.tablaSimbolos;
import tablaSimbolos.Par;


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
	int dir;
	/**
	 * Constructor que inicializa los atributos con los datos que recibe por parametro.
	 * 
	 * @param fuente RandomAccessFile que se utiliza para leer del fichero que contiene el cdigo a analizar.
	 * @param T Tabla de Simbolos que vamos a utilizar en el analisis del fichero, para almacenar los simbolos.
	 * @param f String donde se guarga la ruta del fichero donde se va a guardar el codigo generado por el compilador.
	 * @throws Exception Propaga una excepcion que haya sucedido en otro lugar.
	 */
	public Sintactico(RandomAccessFile fuente, tablaSimbolos T, String f) throws Exception{
		lexico = new Lexico(fuente);		
		TS = T;
	}

	/**
	 * Comienza el analisis sintactico del fichero que queremos analizar. Cuando acaba muestra el codigo que ha reconocido.
	 * @throws Exception Si sucede algun error en otras funciones se propaga la Excepcion.
	 */
	public void startParsing() throws Exception{
		String tipoProg = Prog();
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
		dir = 0;
		String tipoDecs = Decs();
		String tipoIs = Is();
		if (tipoDecs.equals("error") || tipoIs.equals("error")){
			return "error";	
		}
		else{
			return tipoIs;
		}
	}
	
	public String Decs() throws Exception{
		Atributo atrDec = Dec(); 
		TS.addID(atrDec.getId(),atrDec.getTipo());
		Atributo atrRDecs = RDecs();
		if (atrDec.getTipo().equals("error") || atrRDecs.getTipo().equals("error")){
			return "error";
		}
		else {
			return atrRDecs.getTipo();
		}
	}
	
	public Atributo RDecs(){
		Atributo atrRDecs = new Atributo();
		if (!lexico.reconoce(CategoriaLexica.TKPYCOMA)){ 
			atrRDecs.setTipo("error");
			return atrRDecs;
		}
		Atributo Dec = Dec();
		//revisar esto solo he puesto para que no se queje
		return Dec;
	}
	 
	/*{RDecs1.tsh = añadeID (RDecs0.ts, Dec.id, <dir:RDecs0.dir+1, Dec.tipo>)
	RDecs1.errh = RDecs0.errh ∨ existeID (RDecs0.ts, Dec.id)} 
	      RDecs
	{ RDecs0.ts = RDecs1.ts
	RDecs0.err = RDecs1.err}
	RDecs ::= λ
	{RDecs.ts = RDecs.tsh
	RDecs.err = RDecs.errh}
	*/
	
	public Atributo Dec(){
		
		return new Atributo();
	}
	
	public String Is(){
		
		return "";
	}
}