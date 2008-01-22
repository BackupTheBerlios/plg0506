package procesador;

import tablaSimbolos.*;

public class Tupla {

	private ExpresionTipo tipo1;
	private ExpresionTipo tipo2;
	
	public Tupla(ExpresionTipo t1, ExpresionTipo t2) {
		tipo1 = t1;
		tipo2 = t2;
	}
	
	public ExpresionTipo getExpresionTipo1() {
		return tipo1;
	}
	public ExpresionTipo getExpresionTipo2() {
		return tipo2;
	}
	
	public boolean equals(Object o) {
		if(tipo1.equals(((Tupla)o).getExpresionTipo1()) && tipo2.equals(((Tupla)o).getExpresionTipo2())) {
			return true;
		} else
			return false;
	}

}
