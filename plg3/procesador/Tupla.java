package procesador;

import tablaSimbolos.*;

public class Tupla {

	private Tipo tipo1;
	private Tipo tipo2;
	
	public Tupla(Tipo t1, Tipo t2) {
		tipo1 = t1;
		tipo2 = t2;
	}
	
	public Tipo getTipo1() {
		return tipo1;
	}
	public Tipo getTipo2() {
		return tipo2;
	}
	
	public boolean equals(Object o) {
		return (tipo1.equals(((Tupla)o).getTipo1()) && tipo2.equals(((Tupla)o).getTipo2()));
	}
	
}

