package tablaSimbolos;
/**
 * La clase <B>Par</B> define los atributos y métodos relacionados con los identificadores reconocidos. Los elementos de esta clase serán almacenados en la tabla de símbolos
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>id:</CODE> String que almacena el nombre del identificador</LI>
 * <LI><CODE>tipo:</CODE> String que almacena el tipo del identificador.</LI></UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
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
	String tipo;
	String tbase;
	int i;
	
	/**
	 * 
	 * Constructor de la clase sin parametros.
	 */
	public Par() {
		super();
	}

	/**
	 * Constructor de la clase Par con valores de inicialización recibidos por parámetro
	 * 
	 * @param id String con el nombre del identificador.
	 * @param tipo String con el tipo del identificador.
	 *  
	 */
	public Par(String id, String tipo, String tbase, int i) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.tbase = tbase;
		this.i = i;
	}

	/**
	 * Accesor el atributo de la clase id.
	 * @return String con el nombre del identificador.
	 */
	public String getId() {
		return id;
	}
	/**
	 * Mutador el atributo de la clase id.
	 * @param id String con el nombre del identificador.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Accesor el atributo de la clase tipo.
	 * @return String con el tipo del identificador.
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Mutador el atributo de la clase tipo.
	 * @param tipo String con el tipo del identificador.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	
	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getTbase() {
		return tbase;
	}

	public void setTbase(String tbase) {
		this.tbase = tbase;
	}

	/**
	 * El método toString pasa a String la clase Par para poder mostrarla por pantalla.
	 * @return String que contiene un par para poderlo mostrar por pantalla.
	 */
	public String toString(){
		String aux = "(";
		aux = aux.concat(id);
		aux = aux.concat(",");
		aux = aux.concat(tipo);
		aux = aux.concat(",");
		aux = aux.concat(tbase);
		aux = aux.concat(",");
		aux = aux.concat((new Integer(i)).toString());
		aux = aux.concat(")");
		return aux;
	}
}
