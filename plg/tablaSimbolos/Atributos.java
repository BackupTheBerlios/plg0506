package tablaSimbolos;

import java.util.Vector;

public class Atributos {
	
	String tipo;
	Atributos tbase;
	int elems;
	int tam;
	Vector params;
	/**
	 * Contructor sin parámetros para la clase Atributos 
	 *
	 */
	
	public Atributos() {
		super();
		this.tipo = "";
		this.tbase = null;
		this.elems = 0;
		this.tam = 0;
		this.params = new Vector();
	}
	/**
	 * Contructor por copia para la clase Atributos
	 *
	 */
	public Atributos(Atributos a){
		this.tipo = a.getTipo();
		if (a.getTbase()==null){
			this.tbase=null;
		}
		else {
			this.tbase = new Atributos(a.getTbase());
		}
		this.elems = a.getElems();
		this.tam = a.getTam();
		this.params = a.getParams();
	}
	/**
	 * Contructor con parámetros para la clase Atributos
	 *
	 */
	public Atributos(String tipo, String tbase, int elems, int tam, Vector params) {
		super();
		// TODO Auto-generated constructor stub
		this.tipo = tipo;
		if (tbase != ""){
			this.tbase = new Atributos(tbase,"",0,0, new Vector());
		}
		else{
			tbase = null;
		}
		this.elems = elems;
		this.tam = tam;
		this.params = params;
	}
/**
 * Accesor para elems de Atributos
 * @return el número de elementos
 */
	public int getElems() {
		return elems;
	}

/**
 * Mutador para los elems
 * @param elems entero con el número de elementos
 */
	public void setElems(int elems) {
		this.elems = elems;
	}
	/**
	 * Accesor para los parámetros
	 * @return params
	 */
	
	public Vector getParams() {
		return params;
	}
	/**
	 * Mutador para los parámetros
	 * @param params Vector que contiene los parámetros
	 */

	public void setParams(Vector params) {
		this.params = params;
	}
/**
 * Accesor para el tipo base 
 * @return el tipo base que es del tipo Atributos
 */
	public Atributos getTbase() {
		return tbase;
	}

	/**
	 * Mutador para el tipo base
	 * @param params recibe el tipo base de tipo Atributos como parámetros
	 */
	public void setTbase(Atributos tbase) {
		this.tbase = tbase;
	}
	/**
	 * Accesor para el tipo  
	 * @return el tipo que es del tipo String
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * Mutador para el tipo 
	 * @param params recibe el tipo de tipo String
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	} 
	/**
	 * Accesor para el tamaño
	 * @return el tamaño que es de tipo entero
	 */
	public int getTam() {
		return tam;
	}

	/**
	 * Mutador para el tamaño
	 * @param params recibe el tamaño de tipo entero parámetros
	 */
	public void setTam(int tam) {
		this.tam = tam;
	}
/**
 * Método que convierte en un String la lista de Atributos
 */
	public String toString(){
		if (this != null){	
			String aux;
			aux = "( ";
			aux = aux.concat(tipo);
			aux = aux.concat(", ");
			Integer n= new Integer (tam);
			aux= aux.concat(n.toString());
			aux = aux.concat(", ");
			aux = aux.concat("(");
			if (tbase!=null){
				aux = aux.concat(tbase.getTipo());
			}
			aux = aux.concat(") ");
			aux = aux.concat(", ");
			n= new Integer (elems);
			aux = aux.concat(n.toString());
			aux = aux.concat(", ");
			aux = aux.concat(params.toString());
			aux = aux.concat(" )");
			return aux;
		}
		else {
			return "null";
		}
	}
	/**
	 * Método que devuelve un booleano con la igualdad de los atributos
	 * @param a Atributo con el que queremos comparar la igualdad
	 * @return booleano con la igual de los atributos
	 */
	public boolean equals(Atributos a){
		boolean iguales = false;
		// TODO REVISAR ESTE EQUALS, hacerlo recursivo, y que pare cuando el tbase de alguno de los dos sea "", y si no son iguales, devuelva falso.
		iguales = (this.elems == a.getElems() && this.tam == a.getTam() && this.tipo.equals(a.getTipo()));
		if (a.getTbase() == null)
			return (iguales && tbase == null);
		else 
			if (tbase == null)
				return (iguales && a.getTbase() == null);
			return (tbase.getTipo().equals(a.getTbase().getTipo()));	
	}

}
