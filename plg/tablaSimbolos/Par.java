package tablaSimbolos;

public class Par {
	String id;
	String tipo;
		
	public Par() {
		super();
	}

	public Par(String id, String tipo) {
		super();
		this.id = id;
		this.tipo = tipo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
		
	public String toString(){
		String aux = "(";
		aux = aux.concat(id);
		aux = aux.concat(",");
		aux = aux.concat(tipo);
		aux = aux.concat(")");
		return aux;
}


}
