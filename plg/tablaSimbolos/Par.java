package tablaSimbolos;

public class Par {
	
	/*
	 * Atributos de la clase:
	 * 
	 * id almacena el nombre del identificador,
	 * tipo es un string que almacena el tipo del identificador.
	 */
	String id;
	String tipo;
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 * Constructor sin parametros.
	 */
	public Par() {
		super();
	}

	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 *  Constructor para inicializar con los valores que se reciben por parametro.
	 */
	public Par(String id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	/*
	 * Accesores y mutadores de los atributos de Par.
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 * Metodo para pasar a String la clase y poderla mostrar por pantalla.
	 */
	public String toString(){
		String aux = "(";
		aux = aux.concat(id);
		aux = aux.concat(",");
		aux = aux.concat(tipo);
		aux = aux.concat(")");
		return aux;
	}
}
