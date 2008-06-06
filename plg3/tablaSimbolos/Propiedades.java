package tablaSimbolos;

/**
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 * 
 */
public class Propiedades {

	Tipo tipo;
	int dir;
	String clase;
	//int tam;
	int nivel;
	
	/**
	 * Constructor de la clase sin parametros.
	 */
	public Propiedades() {
		super();
		this.nivel = 0;
	}

	/**
	 * Constructor de la clase y recibe como parametros los valores de los atributos.
	 * @param tipo Tipo del token.
	 * @param dir Entero con la direccion de memoria de la variable.
	 */
	public Propiedades(Tipo tipo, int dir, String clas, int t, int n) {
		super();
		this.tipo = tipo;
		this.dir = dir;
		this.clase=clas;
		//this.tam = t;
		this.nivel=0;
	}
	
	/**
	 * Accesor para el atributo tipo de la clase.
	 * @return Devuelve el tipo que contiene el atributo.
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * Mutador el atributo tipo de la clase.
	 * @param tipo Nuevo valor para el atributo tipo
	 */
	public void setTipo(Tipo tipo) {
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
			aux = aux.concat(this.tipo.toString());
			aux = aux.concat(", ");
			aux = aux.concat(new Integer (this.dir).toString());
			aux = aux.concat(", ");
			aux = aux.concat(this.clase);
			//aux = aux.concat(", ");
			//aux = aux.concat(new Integer (this.tam).toString());
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
	public boolean equals(Propiedades a){
		return (this.getTipo() ==a.getTipo());
	}

	/**
	 * @return the clase
	 */
	public String getClase() {
		return clase;
	}

	/**
	 * @param clase the clase to set
	 */
	public void setClase(String clase) {
		this.clase = clase;
	}

	/**
	 * @return the tam
	 */
//	public int getTam() {
//		return tam;
//	}

	/**
	 * @param tam the tam to set
	 */
//	public void setTam(int tam) {
//		this.tam = tam;
//	}
	
	/**
	 * @return the nivel
	 */
	public int getNivel() {
		return nivel;
	}

	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
}