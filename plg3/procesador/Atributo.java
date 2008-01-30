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
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
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
}