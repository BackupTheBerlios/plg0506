package procesador;

import java.lang.String;

public class Codigo {
	
	/*
	 *  Este String guarda el codigo del lenguaje objeto, que es el codigo de la maquina P que 
	 *  no existe asique de momento se mostrara por pantalla.
	 */
	String cod;
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida: 
	 * 
	 * Constructor que inicializa la cadena a vacio.
	 */
	public Codigo(){		
		cod = "";
	}
	
	/*
	 * Parametros de entrada: 
	 * Parametros de salida: 
	 * 
	 * Inicializamos la cadena a vacio.
	 */
	public void inicializaCodigo(){
		cod = "";
	}
	
	/*
	 * Parametros de entrada: String con la instruccion que ha codificado, y entero 
	 * 						que indica el numero de linea.
	 * Parametros de salida: 
	 * 
	 * Inserta la nueva instruccion que recibe por parametro en el String del codigo
	 * ademas de indicar tambien el numero de fila.
	 */
	public void genIns(String instr, int num){
		cod = cod + instr + " " + num + "\n";
	}
	
	/*
	 * Parametros de entrada: String con la instruccion que ha codificado.
	 * Parametros de salida: 
	 * 
	 * Inserta la nueva instruccion que recibe por parametro en el String del codigo
	 * ademas de un salto de linea.
	 */
	public void genIns(String instr){
		cod = cod + instr + "\n";
	}
	
	/*
	 * Parametros de entrada:
	 * Parametros de salida: 
	 * 
	 * Imprime por pantalla el contenido del codigo.
	 */
	public void muestraCodigo(){		
		System.out.println(cod);
	}
}