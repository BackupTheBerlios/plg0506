package tablaSimbolos;

public class Atributos {
	
	String tipo;
	String tbase;
	int elems;
	int tam;
	
	
	public Atributos() {
		super();
		this.tipo = "";
		this.tbase = "";
		this.elems = 0;
		this.tam = 0;
	}


	public Atributos(String tipo, String tbase, int elems, int tam) {
		super();
		// TODO Auto-generated constructor stub
		this.tipo = tipo;
		this.tbase = tbase;
		this.elems = elems;
		this.tam = tam;
	}

	public int getElems() {
		return elems;
	}


	public void setElems(int elems) {
		this.elems = elems;
	}


	public String getTbase() {
		return tbase;
	}


	public void setTbase(String tbase) {
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
		String aux;
		aux = "( ";
		aux = aux.concat(tipo);
		aux = aux.concat(", ");
		aux.concat((new Integer(tam)).toString());
		aux = aux.concat(", ");
		aux = aux.concat(tbase);
		aux = aux.concat(", ");
		aux = aux.concat((new Integer(elems)).toString());
		aux = aux.concat(" )");
		return aux;
	}

}
