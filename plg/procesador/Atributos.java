package procesador;

public class Atributos {

	/*
	 * 
	 */
	boolean err;
	String tipo;
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 * 
	 */
	public Atributos() {
		super();
	}
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida:
	 * 
	 * 
	 */
	public Atributos(boolean err, String tipo) {
		super();
		this.err = err;
		this.tipo = tipo;
	}
	
	/*
	 * 
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
