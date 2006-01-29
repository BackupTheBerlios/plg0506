package procesador;

import java.lang.String;
/**
 * La clase <B>Codigo</B> se encarga de manejar el código generado por las instrucciones del lenguaje.
 * <P>Tiene un sólo atributo:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el código del lenguaje objeto, que es el código de la máquina P. Como la máquina P de momento no existe, este el contenido de este atributo se mostrará por pantalla.</LI></UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
 *
 */
public class Codigo {
	
	/*
	 * Atributos de la clase:
	 * 
	 *  Este String guarda el codigo del lenguaje objeto, que es el codigo de la maquina P que 
	 *  no existe asique de momento se mostrara por pantalla.
	 */
	String cod;
	
	/**
	 * El constructor de la clase Codigo no tiene parámetros de entrada ni de salida. Este constructor inicializa el atributo cod con la cadena vacía.
	 */
	public Codigo(){		
		cod = "";
	}
	
	/**
	 * Este método inicializa el atributo cod con la cadena vacía. No cuenta con parámetros de entrada ni de salida. No devuelve nada.
	 */
	public void inicializaCodigo(){
		cod = "";
	}
	
	/**
	 * Método que modifica el contenido del atributo cod agregándole la instrucción y el número de línea que recibe como parámetro. No devuelve nada.
	 * 
	 * @param instr String con la instrucción que ha codificado.
	 * @param num Entero indica el número de línea.
	 * 
	 */
	public void genIns(String instr, int num){
		cod = cod + instr + " " + num + "\n";
	}
	
	/**
	 * Método que modifica el contenido del atributo cod agregándole la instrucción que recibe como parámetro y un salto de línea. No devuelve nada.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void genIns(String instr){
		cod = cod + instr + "\n";
	}
	
	/**
	 * Método que imprime por pantalla el contenido del atributo cod. No tiene parámetros de entrada ni de salida.
	 * 
	 */
	public void muestraCodigo(){		
		System.out.println(cod);
	}
}