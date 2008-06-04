package procesador;

import tablaSimbolos.Propiedades;

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

	Propiedades props;
	String id;
	
	/**
	 * Constructor vacio de la clase.
	 *
	 */
	public Atributo() {
		super();
	}
	
	/**
	 * Constructor de la clase en el que se le pasan por parametro valores para los dos atributos.
	 * @param tipo String con el valor que le le quiere dar a tipo
	 * @param id String con el valor que le le quiere dar a id
	 */
	public Atributo(Propiedades p, String id) {
		super();
		this.props = p;
		this.id = id;
	}
	
	/**
	 * Accesor para el atributo de la clase, id.
	 * @return Valor del atributo id
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Mutador para el atributo de la clase, id.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Accesor para el atributo de la clase, tipo.
	 * @return Valor del atributo tipo
	 */
	public Propiedades getProps() {
		return props;
	}

	/**
	 * Mutador para el atributo de la clase, tipo.
	 * @param tipo
	 */
	public void setProps(Propiedades p) {
		this.props = p;
	}
}