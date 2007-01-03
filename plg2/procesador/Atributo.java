package procesador;

/**
 * La clase <B>Atributos</B> 
 * 
 * @author  Leticia Garcia 
 *
 */public class Atributo {

	String tipo;
	String id;
	
	public Atributo() {
		super();
	}
	
	public Atributo(String tipo, String id) {
		super();
		this.tipo = tipo;
		this.id = id;
	}

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

	/**
	 * @param args
	 */
	/* */

}
