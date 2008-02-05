package procesador;

/**
 * La clase <B>Atributos</B> guarda el tipo y el nombre o identificador de un token. Contiene accesores y mutadores
 * de ambos parametros.
 * 
 * <P>La clase MaquinaP cuenta con los siguientes atributos:
 * <UL><LI><CODE>tipo:</CODE> String con el tipo de token.</LI>
 * <LI><CODE>id:</CODE> Nombre o identificador de la variable.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 *
 */public class Atributo {

	String tipo;
	String id;
	
	/**
	 * Constructor vacio de la clase.
	 *
	 */
	public Atributo() {
		super();
	}
	
	/**
	 * 
	 * @param tipo
	 * @param id
	 */
	public Atributo(String tipo, String id) {
		super();
		this.tipo = tipo;
		this.id = id;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * 
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}