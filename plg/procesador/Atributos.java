package procesador;
/**
 * La clase <B>Atributos</B> almacena los atributos "err" y "tipo" de las gramáticas
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>err:</CODE> boleano que nos indica si hubo un error o no.</LI>
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
	 * Constructor que inicializa los atributos de Atributos con los valores que recibe
	 * por parametro.
	 * 
	 * @param err Booleano que nos indica si ha sucedido un error y,
	 * @param tipo String con el tipo del identificador, expresion o valor.
	 * Parametros de salida:
	 * 
	 */
	public Atributos(boolean err, String tipo) {
		super();
		this.err = err;
		this.tipo = tipo;
	}
	
	/**
	 * Accesores y mutadores de los atributos de la clase.
	 * @return
	 */
	public boolean getErr() {
		return err;
	}

	public void setErr(boolean err) {
		this.err = err;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}	
}