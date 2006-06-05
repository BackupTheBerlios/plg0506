package tablaSimbolos;

public class Atributos {
	
	String tipo;
	Atributos tbase;
	int elems;
	int tam;
	
	
	public Atributos() {
		super();
		this.tipo = "";
		this.tbase = null;
		this.elems = 0;
		this.tam = 0;
	}

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
	}

	public Atributos(String tipo, String tbase, int elems, int tam) {
		super();
		// TODO Auto-generated constructor stub
		this.tipo = tipo;
		System.out.println("voy crear tbase ");
		if (tbase != ""){
			this.tbase = new Atributos(tbase,"",0,0);
		}
		else{
			tbase = null;
		}
		System.out.println("creo tbase ");
		this.elems = elems;
		this.tam = tam;
	}

	public int getElems() {
		return elems;
	}


	public void setElems(int elems) {
		this.elems = elems;
	}


	public Atributos getTbase() {
		return tbase;
	}


	public void setTbase(Atributos tbase) {
		this.tbase = tbase;
	}

	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	} 

	public int getTam() {
		return tam;
	}


	public void setTam(int tam) {
		this.tam = tam;
	}

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
			aux = aux.concat(" )");
			return aux;
		}
		else {
			return "null";
		}
	}
	
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
