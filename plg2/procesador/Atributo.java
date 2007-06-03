package procesador;

import tablaSimbolos.ExpresionTipo;

/**
 * La clase <B>Atributos</B> guarda el tipo y el nombre o identificador de un token. Contiene accesores y mutadores
 * de ambos parametros.
 * 
 * <P>La clase MaquinaP cuenta con los siguientes atributos:
 * <UL><LI><CODE>tipo:</CODE> String con el tipo de token.</LI>
 * <LI><CODE>id:</CODE> Nombre o identificador de la variable.</LI>
 * </UL></P>
 * 
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez
 *
 */public class Atributo {

	ExpresionTipo tipo;
	String id;
	String clase;
//	int desplazamiento = 0;
	
	public Atributo() {
		super();
	}
	
	public Atributo(ExpresionTipo tipo, String id) {
		super();
		this.tipo = tipo;
		this.id = id;
	}
	
	public Atributo(ExpresionTipo tipo) {
		super();
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public ExpresionTipo getTipo() {
		return tipo;
	}

	public void setTipo(ExpresionTipo tipo) {
		this.tipo = tipo;
	}

/*	public int getDesplazamiento() {
		return desplazamiento;
	}

	public void setDesplazamiento(int desplazamiento) {
		this.desplazamiento = desplazamiento;
	}*/
}
