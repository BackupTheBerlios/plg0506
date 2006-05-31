package procesador;
/**
 * La clase <B>Atributos</B> almacena los atributos err y tipo de las gramticas.
 * 
 * <P>Los atributos que tiene esta clase son los siguientes:
 * <UL><LI><CODE>err:</CODE> booleano que nos indica si hubo un error o no.</LI>
 * <LI><CODE>tipo:</CODE> string donde se almacena el tipo: int o bool.</LI></UL></P>
 * 
 * @author Jons Andradas, Paloma de la Fuente, Leticia Garca y Silvia Martn.
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
	
	String iden;
	
	String tbase;
	
	int i;
	
	/**
	 * Constructor de la clase sin parametros. Inicializa llamando al constructor superior.
	 */
	public Atributos() {
		super();
		tipo = "";
		iden = "";
		tbase = "";
		i = 0;
	}
	
	/**
	 * Constructor que inicializa los atributos de Atributos con los valores que recibe por parmetro.
	 * 
	 * @param err Booleano que nos indica si ha sucedido un error.
	 * @param tipo String con el tipo del identificador, expresion o valor.
	 * 
	 */
	public Atributos(boolean err, String iden,  String tipo, String tbase, int i) {
		super();
		this.err = err;
		this.tipo = tipo;
		this.iden = iden;
		this.tbase = tbase;
		this.i = i;
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

	public String getIden() {
		return iden;
	}

	public void setIden(String iden) {
		this.iden = iden;
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
	
	public void props(Atributos a){
		this.err = a.getErr();
		this.tipo = a.getTipo();
		this.iden = a.getIden();
		this.tbase = a.getTbase();
		this.i = a.getI();
	}
}