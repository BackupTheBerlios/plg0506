package procesador;
/**
 * La clase <B>Atributos</B> almacena los atributos err y tipo de las gramáticas.
 * 
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>err:</CODE> booleano que nos indica si hubo un error o no.</LI>
 * <LI><CODE>tipo:</CODE> string donde se almacena el tipo: int o bool.</LI></UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín.
 * 
 */
public class Atributos {

	/**
	 * err Boleano que nos indica si exitio un error o no.
	 */
	boolean err;
	
	/**
	 * tipo String donde se almacena el tipo: int o bool
	 */
	String tipo;
	
	/**
	 * Constructor de la clase sin parametros. Inicializa llamando al constructor superior.
	 */
	public Atributos() {
		super();
		tipo = "";
	}
	
	/**
	 * Constructor que inicializa los atributos de Atributos con los valores que recibe por parámetro.
	 * 
	 * @param err Booleano que nos indica si ha sucedido un error.
	 * @param tipo String con el tipo del identificador, expresion o valor.
	 * 
	 */
	public Atributos(boolean err, String tipo) {
		super();
		this.err = err;
		this.tipo = tipo;
	}
	
	/**
	 * Accesor para el atributo de la clase err.
	 * @return err Booleano que nos indica si ha sucedido un error.
	 */
	public boolean getErr() {
		return err;
	}
	/**
	 * Mutador para el atributo de la clase err. 
	 * @param err Booleano que nos indica si ha sucedido un error.
	 */
	public void setErr(boolean err) {
		this.err = err;
	}
	/**
	 * Accesor para el atributo de la clase tipo. 
	 * @return tipo String con el tipo del identificador, expresion o valor.
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * Mutador para  el atributo de la clase tipo. 
	 * @param tipo String con el tipo del identificador, expresion o valor
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
}