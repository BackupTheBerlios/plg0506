package tablaSimbolos;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import procesador.*;

/**
 * La clase <B>tablaSimbolos</B> define los atributos y mtodos relacionados con la tabla de smbolos.
 * <P>Esta clase cuenta con el siguiente atributo:
 * <UL><LI><CODE>tabla:</CODE> Usaremos del API de JAVA una tabla Hash como tabla de smbolos. El valor que almacenamos ser un par con el nombre del identificador y el tipo del mismo. 
 * No se podrn almacenar datos repetidos.</LI>
 * <LI><CODE>dir:</CODE> Ultima direccion disponible de la tabla.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */
public class tablaSimbolos {

		
	Hashtable tabla;
	int dir;
	tablaSimbolos tsPadre;
	Stack piladir;
	Stack pilatam;
	int tamlocales;
	int nivel;

	/**
	 * Constructor de la clase y no necesita parametros.
	 */
	public tablaSimbolos() {
		super();
		this.tabla = new Hashtable();
		this.dir = 0;
		tamlocales = 0;
		nivel = 0;
		piladir = new Stack();
		pilatam = new Stack();
	}

	/**
	 * Constructor de la tabla de Simbolos y recibe como parametro un nuevo valor de la tabla.
	 * 
	 * @param tabla Nuevo valor de la tabla de simbolos.
	 */
	public tablaSimbolos(Hashtable tabla) {
		super();
		this.tabla = tabla;
		this.dir = 0;
	}
	
	/**
	 * Constructor de la tabla de simbolos: recibe una tabla padre.
	 * 
	 * @param tsPadre Tabla de simbolos padre
	 */
	public tablaSimbolos(tablaSimbolos tsPadre) {
		this();
		this.tsPadre = tsPadre;
	}
	
	/**
	 * Metodo para comprobar si existe ya un identificador declarado en la tabla de simbolos. Si es asi
	 * devolvera verdadero para que se genere un error.
	 * 
	 * @param id Identificador de variable que se va a comprobar si exite o no en la tabla.
	 * @return boolean Sera verdadero si el identificador si existe y falso en otro caso.
	 */
	public boolean existeID(String id)throws Exception{
		return this.tabla.containsKey(id);
	}
	
	/**
	 * Metodo para añadir un nuevo identificador a la tabla de simbolos. 
	 * 
	 * @param id Nombre del identificador que se va añadir.
	 * @param tipo Tipo del nuevo identificador a añadir.
	 * @return boolean Se devuelve verdadero si todo ha sido correcto. Falso en caso contrario.
	 * @throws Exception Excepcion generada si el identificador ya existe, se capturara en otro lugar.
	 */
	public boolean addID(String id, ExpresionTipo tipo, String clase) throws Exception{
		if (this.tabla.containsKey(id)){
			throw new Exception ("No se puede duplicar el identificador");
		}
		else{
			propiedades prop = new propiedades(tipo, this.dir);
			prop.setClase(clase);
			if (tipo.getTipo().equals("reg"))
				this.dir = this.dir + tipo.getParams().size();
			else
				this.dir ++;
			this.tabla.put(id,prop);
			return true;	
		}	
	}
	
	/**
	 * Accesor del atributo que contiene la tabla de simbolos
	 * @return hashtable Tabla de simbolos con la que se esta trabajando en ese momento.
	 */
	public Hashtable getTabla() {
		return tabla;
	}

	/**
	 * Mutador del atributo tabla.
	 * @param tabla Nuevo valor de la tabla.
	 */
	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}
	
	/**
	 * Accesor del atributo que contiene la ultima direccion de la tabla de simbolos
	 * @return entero con la direccion.
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * Mutador del atributo direccion.
	 * @param dir
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}
	
	public tablaSimbolos getTSPadre() {
		return tsPadre;
	}

	public void setTSPadre(tablaSimbolos tsPadre) {
		this.tsPadre = tsPadre;
	}
	
	/**
	 * Metodo para mostrar el contenido de la tabla de simbolos. Se pasa el contenido de la tabla a un String y luego se imprime por pantalla.
	 *
	 */
	public void mostrar(){
		Enumeration e = this.tabla.keys();
		String aux = new String();
		String id = new String();
		propiedades p = new propiedades();
		while (e.hasMoreElements()){
			id = (String) e.nextElement();
			aux = aux.concat("( ");
			aux = aux.concat(id);
			p = (propiedades)this.tabla.get(id);
			aux = aux.concat(", ");
			aux = aux.concat(p.toString());
			aux = aux.concat(" )");
			System.out.println(aux);
		}
	}
	
	public boolean hayCampo(Vector campos,String id){
		boolean enc = false;
		int i = 0;
		while (i<campos.size()){
			Atributo a = (Atributo)campos.elementAt(i);
			if (a.getId().equals(id))
				return true;
			i++;
		}
		return enc;
	}
	
	public void setNivel(int n) {
		if (n > 0) {
			piladir.push(new Integer(dir));
			pilatam.push(new Integer(tamlocales));
			dir = 0;
			tamlocales = 0;
			nivel = n;
		} else {
			dir = ((Integer)piladir.pop()).intValue();
			tamlocales = ((Integer)pilatam.pop()).intValue();
			nivel = 0; //FIXME
		}
	}
	
	public void restoreNivel() {
		dir = ((Integer)piladir.pop()).intValue();
		tamlocales = ((Integer)pilatam.pop()).intValue();
	}

	public int getTamlocales() {
		return tamlocales;
	}

	public void setTamlocales(int tamlocales) {
		this.tamlocales = tamlocales;
	}

}
