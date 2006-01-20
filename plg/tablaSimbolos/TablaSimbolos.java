package tablaSimbolos;

import java.util.Hashtable;


public class TablaSimbolos {
	Hashtable tabla;

	/*
	 * Cosntructores
	 */
	public TablaSimbolos() {
		super();
		this.tabla = new Hashtable();
	}

	public TablaSimbolos(Hashtable tabla) {
		super();
		this.tabla = new Hashtable(tabla);
	}

	/*
	 * Accesores y mutadores
	 */
	public Hashtable getTabla() {
		return tabla;
	}

	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}
	
	/*
	 * Parametros de entrada: Buffer de entrada del que lee caracteres.
	 * Parametros de salida: Token que ha reconocido.
	 * 
	 * Un identificador 'id' de tipo 't' existe en la tabla
	 * solo si existe el tipo 't' en las claves, 
	 * existe el identificador 'id' en los valores
	 * y la clave en la tabla hash de 'id' es 't'. 
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
	 * Un identificador 'id' de tipo 't' se agnade a la tabla
	 * si el tipo 't' es valido y no estba agnadido antes
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
	 * Un identificador id' de tipo 't' se elimina a la tabla
	 * si existe en la tabla un identificador 'id' de tipo 't'
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
	
	public void muestra(){
		String aux = this.tabla.toString();
		System.out.println(aux);
	}
	
	
}
