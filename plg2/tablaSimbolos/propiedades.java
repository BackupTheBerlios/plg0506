package tablaSimbolos;

/**
 * 
 * @author 
 *
 */
public class propiedades {

	String tipo;
	int dir;
	
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
	public propiedades(String tipo, int dir) {
		super();
		this.tipo = tipo;
		this.dir = dir;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	/**
	 * Mtodo que convierte en un String la lista de Atributos
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
