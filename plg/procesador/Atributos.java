package procesador;
/**
 * La clase <B>Atributos</B> almacena los atributos err y tipo de las gramáticas
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>err:</CODE> booleano que nos indica si hubo un error o no.</LI>
 * <LI><CODE>tipo:</CODE> string donde se almacena el tipo: int o bool.</LI></UL></P>
 * 
 * @author Paloma de la Fuente, Jonás Andradas, Leticia García y Silvia Martín
 *
 */
public class Atributos {

	/*
	 * Atributos de la clase:
	 * 
	 * err boleano que nos indica si exitio un error o no.
	 * tipo, string donde se almacena el tipo: int o bool
	 */
	boolean err;
	String tipo;
	
	/**
	 * Constructor sin parametros.
	 */
	public Atributos() {
		super();
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
	 * Accesor para err.
	 * @return err
	 */
	public boolean getErr() {
		return err;
	}
	/**
	 * Mutador para err. 
	 * @param err
	 */
	public void setErr(boolean err) {
		this.err = err;
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
}