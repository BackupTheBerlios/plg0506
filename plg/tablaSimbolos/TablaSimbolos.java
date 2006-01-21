package tablaSimbolos;

import java.util.Hashtable;
import java.util.Enumeration;


public class TablaSimbolos {
	
	/*
	 * Usaremos del API de java una tabla Hash como tabla de simbolos. El valor 
	 * que almacenamos sera un par con el nombre del identificador y el tipo del 
	 * mismo. No se podran almacenar datos repetidos.
	 */
	Hashtable tabla;

	/*
	 * Parametros de entrada: 
	 * Parametros de salida: 
	 * 
	 * Cosntructor sin parametros que inicializa la tabla de simbolos con una tabla
	 * vacia.
	 */
	public TablaSimbolos() {
		super();
		this.tabla = new Hashtable();
	}

	/*
	 * Parametros de entrada: recibe por parametro la tabla con la que inicializamos.
	 * Parametros de salida: 
	 * 
	 * Constructor que recibe una tabla de simbolos por parametro y la inicializamos 
	 * con esa.
	 */
	public TablaSimbolos(Hashtable tabla) {
		super();
		this.tabla = new Hashtable(tabla);
	}

	/*
	 * Accesores y mutadores de la tabla de simbolos
	 */
	public Hashtable getTabla() {
		return tabla;
	}

	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}
	
	/*
	 * Parametros de entrada: Strings que tienen almacenado el nombre del identificador y
	 * 					y el tipo del identificador.
	 * Parametros de salida: Boleano que inidica la existencia o no.
	 * 
	 * Un identificador 'id' de tipo 't' existe en la tabla solo si existe su clave, 
	 * la clave de cada identificador de un tipo es unica, no habra repeticiones. 
	 */
	public boolean existeID(String id, String t){
		String aux = id.concat("#");
		aux = aux.concat(t);
		if (!this.tabla.containsKey(aux)){
			System.out.println("No existe el identificador");
			return false;
		}
		else{
			return true;	
		}
	}
	
	/*
	 * Parametros de entrada: Strings que tienen almacenado el nombre del identificador y
	 * 					y el tipo del identificador.
	 * Parametros de salida: Boleano que inidica si la operacion se realizo bien.
	 * 
	 * Un identificador 'id' de tipo 't' se agnade a la tabla si no existe ningun identificador
	 * de ese tipo. El valor de la clave en la tabla hash es unico, ya que concatenamos el nombre
	 * del identificador con el tipo usando entre medias un caracter de separacion '#'. Como valor
	 * para la tabla introducimos un par, que son el nombre del id y el tipo.
	 */
	public boolean agnadeID(String id, String t){
		String aux = id.concat("#");
		aux = aux.concat(t);
		if (this.tabla.containsKey(aux)){
			System.out.println("No se puede duplicar el identificador");
			return false;
		}
		else{
			Par p = new Par(id,t);
			this.tabla.put(aux,p);
			return true;	
		}	
	}

	/*
	 * Parametros de entrada: Strings que tienen almacenado el nombre del identificador y
	 * 					y el tipo del identificador.
	 * Parametros de salida: Boleano que inidica si la operacion se realizo bien.
	 * 
	 * Un identificador id' de tipo 't' se elimina a la tabla si existe en la tabla un 
	 * identificador 'id' de tipo 't'. Sino se mostrara un error por pantalla.
	 */
	public boolean eliminaID(String id, String t){
		String aux = id.concat("#");
		aux = aux.concat(t);
		if (!this.tabla.containsKey(aux)){
			System.out.println("No se puede eliminar el identificador, no existe");
			return false;
		}
		else{
			this.tabla.remove(aux);
			return true;	
		}	
	}
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida: 
	 * 
	 * Metodo para mostar por pantalla la tabla de simbolos actual.
	 */
	public void muestra(){
		String aux = this.tabla.toString();
		System.out.println(aux);
	}

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
