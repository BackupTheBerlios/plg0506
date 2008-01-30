package tablaSimbolos;

/**
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */
public class propiedades {

	String tipo;
	int dir;
	
	/**
	 * Constructor de la clase sin parametros.
	 */
	public propiedades() {
		super();
	}

	/**
	 * Constructor de la clase y recibe como parametros los valores de los atributos.
	 * @param tipo String con el tipo del token.
	 * @param dir Entero con la direccion de memoria de la variable.
	 */
	public propiedades(String tipo, int dir) {
		super();
		this.tipo = tipo;
		this.dir = dir;
	}
	
	/**
	 * Accesor para el atributo tipo de la clase.
	 * @return Devuelve el tipo que contiene el atributo.
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Mutador el atributo tipo de la clase.
	 * @param tipo Nuevo valor para el atributo tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Accesor para el atributo dir de la clase.
	 * @return Devuelve la direccion que contiene el atributo.
	 */
	public int getDir() {
		return dir;
	}

	/**
	 * Mutador el atributo direccion de la clase.
	 * @param dir Nuevo valor para el atributo dir.
	 */
	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * Metodo que convierte en un String la lista de Atributos
	 * @return Devuelve un String con los valores que contienen sus atributos
	 */
	public String toString(){
		if (this != null){	
			String aux;
			aux = "( ";
			aux = aux.concat(this.tipo);
			aux = aux.concat(", ");
			aux = aux.concat(new Integer (this.dir).toString());
			aux = aux.concat(" )");
			return aux;
		}
		else {
			return "null";
		}
	}
	
	/**
	 * Metodo que devuelve un booleano con la igualdad de los atributos
	 * @param a Atributo con el que queremos comparar la igualdad
	 * @return booleano con la igual de los atributos
	 */
	public boolean equals(propiedades a){
		return (this.getTipo() ==a.getTipo());
	}
}