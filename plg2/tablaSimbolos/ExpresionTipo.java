package tablaSimbolos;

import java.util.Vector;

public class ExpresionTipo {
	/**
	 * <t:num>, <t:bool>
	 * <t:ref,id:...>
	 * <t:array,nelems:...,tbase:...>
	 * <t:reg,campos:...> 
	 */
	String t; 
	ExpresionTipo tbase; 
	int nelems;
	int tam;
	Vector params;
	String id;
	
	/**
	 * Contructor sin parmetros para la clase ExpresionTipo 
	 *
	 */
	public ExpresionTipo() {
		super();
		this.id = "";
		this.t = "";
		this.tbase = null;
		this.nelems = 0;
		this.tam = 0;
		this.params = new Vector();
	}
	
	public ExpresionTipo(String tipo) {
		super();
		this.id = "";
		this.t = tipo;
		this.tbase = null;
		this.nelems = 0;
		this.tam = 0;
		this.params = new Vector();
	}
	
	/**
	 * Contructor por copia para la clase ExpresionTipo
	 *
	 */
	public ExpresionTipo(ExpresionTipo a){
		this.id = a.getId();
		this.t = a.getTipo();
		if (a.getTbase()==null){
			this.tbase=null;
		}
		else {
			this.tbase = new ExpresionTipo(a.getTbase());
		}
		this.nelems = a.getElems();
		this.tam = a.getTam();
		this.params = a.getParams();
	}
	
	/**
	 * Contructor con parmetros para la clase ExpresionTipo
	 *
	 */
	public ExpresionTipo(String tipo, String tbase, int elems, int tam, Vector params) {
		super();
		this.t = tipo;
		if (tbase != ""){
			this.tbase = new ExpresionTipo(tbase,"",0,0, new Vector());
		}
		else{
			tbase = null;
		}
		this.nelems = elems;
		this.tam = tam;
		this.params = params;
	}
	
	/**
	 * Accesor para elems de ExpresionTipo
	 * @return el nmero de elementos
	 */
	public int getElems() {
		return nelems;
	}

	/**
	 * Mutador para los elems
	 * @param elems entero con el nmero de elementos
	 */
	public void setElems(int elems) {
		this.nelems = elems;
	}
	
	/**
	 * Accesor para los parmetros
	 * @return params
	 */
	
	public Vector getParams() {
		return params;
	}
	
	/**
	 * Mutador para los parmetros
	 * @param params Vector que contiene los parmetros
	 */
	public void setParams(Vector params) {
		this.params = params;
	}
	
	/**
	 * 	Accesor para el tipo base 
	 * 	@return el tipo base que es del tipo ExpresionTipo
	 */
	public ExpresionTipo getTbase() {
		return tbase;
	}

	/**
	 * Mutador para el tipo base
	 * @param ExpresionTipo recibe el tipo base de tipo ExpresionTipo como parmetros
	 */
	public void setTbase(ExpresionTipo tbase) {
		this.tbase = tbase;
	}
	
	/**
	 * Accesor para el tipo  
	 * @return el tipo que es del tipo String
	 */
	public String getTipo() {
		return t;
	}

	/**
	 * Mutador para el tipo 
	 * @param tipo recibe el tipo de tipo String
	 */
	public void setTipo(String tipo) {
		this.t = tipo;
	}
	
	/**
	 * Accesor para el tamao
	 * @return el tamao que es de tipo entero
	 */
	public int getTam() {
		return tam;
	}

	/**
	 * Mutador para el tamao
	 * @param tam recibe el tamao de tipo entero parmetros
	 */
	public void setTam(int tam) {
		this.tam = tam;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Mtodo que convierte en un String la lista de ExpresionTipo
	 */
	public String toString(){
		if (this != null){	
			String aux;
			aux = "( ";
			aux = aux.concat(t);
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
			n= new Integer (nelems);
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
	 * Mtodo que devuelve un booleano con la igualdad de los ExpresionTipo
	 * @param a Atributo con el que queremos comparar la igualdad
	 * @return booleano
	 */
	public boolean equals(ExpresionTipo a){
		boolean iguales = false;
		// TODO REVISAR ESTE EQUALS, hacerlo recursivo, y que pare cuando el tbase de alguno de los dos sea "", y si no son iguales, devuelva falso.
		iguales = (this.nelems == a.getElems() && this.tam == a.getTam() && this.t.equals(a.getTipo()));
		if (a.getTbase() == null)
			return (iguales && tbase == null);
		else 
			if (tbase == null)
				return (iguales && a.getTbase() == null);
			return (tbase.getTipo().equals(a.getTbase().getTipo()));	
	}
}
