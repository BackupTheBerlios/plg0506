package procesador;

import java.lang.String;

public class Codigo {
	
	/*
	 *  
	 */
	String cod;
	
	/*
	 * 
	 */
	public Codigo(){		
		cod = "";
	}
	
	/*
	 * 
	 */
	public void inicializaCodigo(){
		cod = "";
	}
	
	/*
	 * Parametros de entrada: Buffer de entrada del que lee caracteres.
	 * Parametros de salida: Token identificado.
	 * 
	 */
	public void genIns(String instr, int num){
		cod = cod + instr + " " + num + "\n";
	}
	
	/*
	 * Parametros de entrada: Buffer de entrada del que lee caracteres.
	 * Parametros de salida: Token identificado.
	 * 
	 */
	public void genIns(String instr){
		cod = cod + instr + "\n";
	}
	
	/*
	 * 
	 */
	public void muestraCodigo(){		
		System.out.println(cod);
	}
}
