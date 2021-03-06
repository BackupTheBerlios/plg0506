package tablaSimbolos;

/**
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */
public class propiedades {

	/**
	 * ExpresionTipo tipo;
	 * String clase;
	 * int dir;
	 * int tam;
	 */
	
	ExpresionTipo tipo;
	int dir;
	String clase;
	int nivel;
	
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
	public propiedades(ExpresionTipo tipo, int dir) {
		super();
		this.tipo = tipo;
		this.dir = dir;
	}
	
	/**
	 * Accesor para el atributo tipo de la clase.
	 * @return Devuelve el tipo que contiene el atributo.
	 */
	public ExpresionTipo getTipo() {
		return tipo;
	}

	/**
	 * Mutador el atributo tipo de la clase.
	 * @param tipo Nuevo valor para el atributo tipo
	 */
	public void setTipo(ExpresionTipo tipo) {
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
	 * Metodo que devuelve un booleano con la igualdad de los atributos
	 * @param a Atributo con el que queremos comparar la igualdad
	 * @return booleano con la igual de los atributos
	 */
	public boolean equals(propiedades a){
		return (this.getTipo() ==a.getTipo());
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
}
