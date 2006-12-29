package tablaSimbolos;

/**
 * 
 * @author 
 *
 */
public class propiedades {

	String tipo;
	
	/**
	 * 
	 *
	 */
	public propiedades() {
		super();
	}

	/**
	 * 
	 * @param tipo
	 */
	public propiedades(String tipo) {
		super();
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Mtodo que convierte en un String la lista de Atributos
	 */
	public String toString(){
		if (this != null){	
			String aux;
			aux = "( ";
			aux = aux.concat(tipo);
					aux = aux.concat(" )");
			return aux;
		}
		else {
			return "null";
		}
	}
	
	/**
	 * Mtodo que devuelve un booleano con la igualdad de los atributos
	 * @param a Atributo con el que queremos comparar la igualdad
	 * @return booleano con la igual de los atributos
	 */
	public boolean equals(propiedades a){
		return (this.getTipo() ==a.getTipo());
	}
	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
*/
}
