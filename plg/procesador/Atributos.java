package procesador;

public class Atributos {

	/*
	 * Atributos de la clase:
	 * 
	 * err boleano que nos indica si exitio un error o no.
	 * tipo, string donde se almacena el tipo: int o bool
	 */
	boolean err;
	String tipo;
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 * Constructor sin parametros.
	 */
	public Atributos() {
		super();
	}
	
	/*
	 * Parametros de entrada: Boleano que nos indica si ha sucedido un error y,
	 * 						String con el tipo del identificador, expresion o valor.
	 * Parametros de salida:
	 * 
	 * Constructor que inicializa los atributos de Atributos con los valores que recibe
	 * por parametro.
	 */
	public Atributos(boolean err, String tipo) {
		super();
		this.err = err;
		this.tipo = tipo;
	}
	
	/*
	 * Accesores y mutadores de los atributos de la clase.
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