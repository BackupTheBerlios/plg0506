package procesador;

import java.lang.String;
/**
 * La clase <B>Codigo</B> se encarga de manejar el código generado por las instrucciones del lenguaje
 * <P>Tiene un sólo atributo:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el código del lenguaje objeto, que es el código de la máquina P. Como la máquina P de momento no existe, este el contenido de este atributo se mostrará por pantalla.</LI></UL></P>
 * 
 * @author Paloma de la Fuente, Jonás Andradas, Leticia García y Silvia Martín
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
	 * El constructor de código no tiene parámetros de entrada ni de salida. Este constructor inicializa cod con la cadena vacía.
	 */
	public Codigo(){		
		cod = "";
	}
	
	/**
	 * Esta función inicializa el atributo cod con la cadena vacía. No cuenta con parámetros de entrada ni de salida. Este constructor inicializa cod con la cadena vacía.
	 */
	public void inicializaCodigo(){
		cod = "";
	}
	
	/**
	 * Función que modifica el contenido del atributo cod agregándole la instrucción y el número de línea que recibe como parámetro.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * @param num Entero indica el numero de línea.
	 * @return Esta función no devuelve nada
	 * 
	 */
	public void genIns(String instr, int num){
		cod = cod + instr + " " + num + "\n";
	}
	
	/**
	 * Función que modifica el contenido del atributo cod agregándole la instrucción que recibe como parámetro y un salto de línea.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void genIns(String instr){
		cod = cod + instr + "\n";
	}
	
	/**
	 * Función que imprime por pantalla el contenido del atributo cod. No tiene parámetros de entrada ni de salida.
	 * 
	 */
	public void muestraCodigo(){		
		System.out.println(cod);
	}
}