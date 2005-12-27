package procesador;

import java.lang.String;

public class Codigo {
	
	String cod;
	
	public Codigo(){		
		cod = "";
	}

	public void inicializaCodigo(){
		cod = "";
	}

	public void genIns(String instr, int num){
		cod = cod + instr + " " + num + "\n";
	}

	public void genIns(String instr){
		cod = cod + instr + "\n";
	}

	public void muestraCodigo(){		
		System.out.println(cod);
	}
}
