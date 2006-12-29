package tablaSimbolos;
/**
 * La clase <B>Par</B> define los atributos y mtodos relacionados con los identificadores reconocidos. Los elementos de esta clase sern almacenados en la tabla de smbolos
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>id:</CODE> String que almacena el nombre del identificador</LI>
 * <LI><CODE>tipo:</CODE> String que almacena el tipo del identificador.</LI></UL></P>
 * 
 * @author Leticia Garcia y Alberto Velazquez
 *
 */
public class Par {
	
	/*
	 * Atributos de la clase:
	 * 
	 * id almacena el nombre del identificador,
	 * tipo es un string que almacena el tipo del identificador.
	 */
	
	String id;
	propiedades props;
	int dir;
	
	public Par() {
		super();
	}
	
	/**
	 * 
	 * @param id
	 * @param props
	 */
	public Par(String id, propiedades props) {
		super();
		this.id = id;
		this.props = props;
	}

	/**
	 * 
	 * @param id
	 * @param props
	 * @param dir
	 */
	public Par(String id, propiedades props, int dir) {
		super();
		this.id = id;
		this.props = props;
		this.dir = dir;
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public propiedades getProps() {
		return props;
	}

	public void setProps(propiedades props) {
		this.props = props;
	}

	/**
	 * El mtodo toString pasa a String la clase Par para poder mostrarla por pantalla.
	 * @return String que contiene un par para poderlo mostrar por pantalla.
	 */
	public String toString(){
		String aux = "(";
		aux = aux.concat(id);
		aux = aux.concat(",");
		aux = aux.concat(props.toString());
		aux = aux.concat(",");
		aux = aux.concat((new Integer(dir)).toString());
		aux = aux.concat(")");
		aux = aux.concat("\n");
		return aux;
	}
}
