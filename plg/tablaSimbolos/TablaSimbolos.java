package tablaSimbolos;

import java.util.Hashtable;
import java.util.Enumeration;

/**
 * La clase <B>TablaSimbolos</B> define los atributos y métodos relacionados con la tabla de símbolos.
 * <P>Esta clase cuenta con el siguiente atributo:
 * <UL><LI><CODE>tabla:</CODE> Usaremos del API de JAVA una tabla Hash como tabla de símbolos. El valor que almacenamos será un par con el nombre del identificador y el tipo del mismo. 
 * No se podrán almacenar datos repetidos.</LI></UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
 *
 */
public class TablaSimbolos {
	
	/*
	 * Atributos de la clase:
	 * 
	 * Usaremos del API de java una tabla Hash como tabla de simbolos. El valor 
	 * que almacenamos sera un par con el nombre del identificador y el tipo del 
	 * mismo. No se podran almacenar datos repetidos.
	 */
	Hashtable tabla;

	/**
	 * Constructor de la clase sin parámetros que inicializa la tabla de símbolos con una tabla vacía.
	 */
	public TablaSimbolos() {
		super();
		this.tabla = new Hashtable();
	}

	/**
	 * Constructor de la clase que recibe una tabla de símbolos por parametro y la usa para inicializar la tabla de símbolos creada.
	 * 
	 * @param tabla Hashtable recibida por parámetro y a la que se inicializa la nueva tabla creada.
	 */
	public TablaSimbolos(Hashtable tabla) {
		super();
		this.tabla = new Hashtable(tabla);
	}

	/**
	 * Accesor para el atributo de la clase tabla.
	 * @return Hashtable recibida por parámetro y a la que se inicializa la nueva tabla creada.
	 */
	public Hashtable getTabla() {
		return tabla;
	}
	/**
	 * Mutador para el atributo de la clase tabla.
	 * @param tabla Hashtable recibida por parámetro y a la que se inicializa la nueva tabla creada.
	 */
	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}
	
	/**
	 * El método existeID discrimina si un identificador 'id' de tipo 't' existe en la tabla de símbolos, 
	 * solo si existe su clave. La clave de cada identificador de un tipo es única, no habrá repeticiones.
	 *
	 * @param id String que almacena el nombre del identificador 
	 * @param t String que almacena el tipo del identificador
	 * @return Booleano que indica la existencia o no de estos valores en la tabla.
	 */
	public boolean existeID(String id, String t){
		String aux = id.concat("#");
		aux = aux.concat(t);
		if (!this.tabla.containsKey(aux)){
			return false;
		}
		else{
			return true;	
		}
	}
	
	/**
	 * El método agnadeID añade un identificador 'id' de tipo 't' a la tabla si no existe ningun identificador de ese tipo. 
	 * El valor de la clave en la tabla hash es único, ya que concatenamos el nombre del identificador con el tipo usando 
	 * entre medias un carácter de separacion: '#'. Como valor para la tabla introducimos un par, que son el nombre del id 
	 * y el tipo. Si ya existe el par entonces no es posible añadir y lanzamos una excepción
	 * 
	 * @param id String que tienen almacenado el nombre del identificador
	 * @param t String que tienen almacenado el tipo del identificador
	 * @return Booleano que indica si la operación se realizo bien.
	 * @exception Exception Porque no se puede duplicar un identificador
	 * 
	 */
	public boolean agnadeID(String id, String t) throws Exception{
		String aux = id.concat("#");
		aux = aux.concat(t);
		if (this.tabla.containsKey(aux)){
			throw new Exception ("No se puede duplicar el identificador");
		}
		else{
			System.out.println("AÃ±adido");
			Par p = new Par(id,t);
			this.tabla.put(aux,p);
			return true;	
		}	
	}

	/**
	 * El método eliminaID elimina un identificador 'id' de tipo 't' de la tabla si existe en ella existe un 
	 * identificador 'id' de tipo 't'. Sino lanzara una excepcion.
	 * 
	 * @param id String que tienen almacenado el nombre del identificador
	 * @param t String que tienen almacenado el tipo del identificador
	 * @return Booleano que indica si la operación se realizo bien.
	 * @exception  Exception Porque no se encuentra el par a eliminar
	 * 
	 */
	public boolean eliminaID(String id, String t) throws Exception{
		String aux = id.concat("#");
		aux = aux.concat(t);
		if (!this.tabla.containsKey(aux)){
			throw new Exception("No se puede eliminar el identificador, no existe");
		}
		else{
			this.tabla.remove(aux);
			return true;	
		}	
	}
	
	/**
	 * El método muestra se usa para mostrar por pantalla la tabla de símbolos actual.
	 */
	public void muestra(){
		String aux = this.tabla.toString();
		System.out.println(aux);
	}

	/**
	 * El método dirID obtiene la posición de memoria del par (ide y tipo) dado por parámetro al método.
	 * Es necesario para saber cuando generemos el código de dónde tomar los valores necesarios.
	 *  
     * @param id String que tienen almacenado el nombre del identificador
	 * @param tipo String que tienen almacenado el tipo del identificador
	 * @return Entero que nos indica la posición en memoria del identificador que indica si la operación se realizo bien. 
	 */
	public int dirID(String id, String tipo){
		String aux = id.concat("#");
		aux = aux.concat(tipo);
		int i = 0;
		for (Enumeration e = this.tabla.keys(); e.hasMoreElements();){
			String s = (String)e.nextElement();
			if(s.equals(aux)){
				return i;
			}
			i ++;
	     }
		return i;
	}	
}