package tablaSimbolos;
import java.util.Enumeration;
import java.util.Hashtable;

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

	/**
	 * Constructor de la clase y no necesita parametros.
	 */
	public tablaSimbolos() {
		super();
		this.tabla = new Hashtable();
		this.dir = 0;
	}

	/**
	 * 
	 * @param tabla
	 */
	public tablaSimbolos(Hashtable tabla) {
		super();
		this.tabla = tabla;
		this.dir = 0;
	}
	
	/**
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean existeID(String id)throws Exception{
		return this.tabla.containsKey(id);
	}
	
	/**
	 * 
	 * @param id
	 * @param tipo
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addID(String id, String tipo) throws Exception{
		if (this.tabla.containsKey(id)){
			throw new Exception ("No se puede duplicar el identificador");
		}
		else{
			propiedades prop = new propiedades(tipo, this.dir);
			this.dir ++;
			this.tabla.put(id,prop);
			return true;	
		}	
	}
	
	/**
	 * 
	 * @return hashtable
	 */
	public Hashtable getTabla() {
		return tabla;
	}

	/**
	 * 
	 * @param tabla
	 */
	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}
	
	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * 
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
	
}
