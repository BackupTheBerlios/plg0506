package procesador;

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
	 * Metodos para la gestion de tipos,
	 * controlan que no se repitan tipos.
	 */
	public void agnadeTipo (String s) {
		if (this.tabla.containsKey(s)){
			System.out.println("No se pudo agnadir el tipo, ya existe.");
		}
		else{
			this.tabla.put(s,"");
		}
			
	}
	
	public void eliminaTipo (String s) {
		if (!this.tabla.containsKey(s)){
			System.out.println("No se pudo agnadir el tipo, no existe.");
		}
		else{
			this.tabla.remove(s);
		}
			
	}
	
	/*
	 * Un identificador id' de tipo 't' existe en la tabla
	 * solo si existe el tipo 't' en las claves, 
	 * existe el identificador 'id' en los valores
	 * y la clave en la tabla hash de 'id' es 't'. 
	 */
	public boolean existeID(String id, String t){
		if (!this.tabla.containsKey(t)){
			System.out.println("No existe el tipo");
			return false;
		}
		if (!this.tabla.contains(id)){
			System.out.println("No existe el identificador");
			return false;
		}
		if (this.tabla.get(t)==id){
			return true;	
		}
		else{
			System.out.println("Id no es de tipo t");
			return false;
		}
		/*
		 * contaisValue(id) no funciona como queremos
		 */
	}
}
