package tablaSimbolos;
/**
 * La clase <B>Par</B> define los atributos y m�todos relacionados con los identificadores reconocidos. Los elementos de esta clase ser�n almacenados en la tabla de s�mbolos
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>id:</CODE> String que almacena el nombre del identificador</LI>
 * <LI><CODE>tipo:</CODE> String que almacena el tipo del identificador.</LI></UL></P>
 * 
 * @author Paloma de la Fuente, Jon�s Andradas, Leticia Garc�a y Silvia Mart�n
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
	
	/**
	 * 
	 * Constructor sin parametros.
	 */
	public Par() {
		super();
	}

	/**
	 * Constructor de la clase Par con valores de inicializaci�n recibidos por par�metro
	 * 
	 * @param id String con el nombre del identificador
	 * @param tipo String con el tipo del identificador
	 *  
	 */
	public Par(String id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	/**
	 * Accesor para id.
	 * @return id
	 */
	public String getId() {
		return id;
	}
	/**
	 * Mutador para id.
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * Accesor para tipo.
	 * @return tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Mutador para tipo.
	 * @param tipo
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * El m�todo toString pasa a String la clase Par para poder mostrarla por pantalla.
	 * 
	 */
	public String toString(){
		String aux = "(";
		aux = aux.concat(id);
		aux = aux.concat(",");
		aux = aux.concat(tipo);
		aux = aux.concat(")");
		return aux;
	}
}
