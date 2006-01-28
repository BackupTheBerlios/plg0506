package procesador;

import java.lang.String;
/**
 * La clase <B>Codigo</B> se encarga de manejar el c�digo generado por las instrucciones del lenguaje
 * <P>Tiene un s�lo atributo:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el c�digo del lenguaje objeto, que es el c�digo de la m�quina P. Como la m�quina P de momento no existe, este el contenido de este atributo se mostrar� por pantalla.</LI></UL></P>
 * 
 * @author Paloma de la Fuente, Jon�s Andradas, Leticia Garc�a y Silvia Mart�n
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
	 * El constructor de c�digo no tiene par�metros de entrada ni de salida. Este constructor inicializa cod con la cadena vac�a.
	 */
	public Codigo(){		
		cod = "";
	}
	
	/**
	 * Esta funci�n inicializa el atributo cod con la cadena vac�a. No cuenta con par�metros de entrada ni de salida. Este constructor inicializa cod con la cadena vac�a.
	 */
	public void inicializaCodigo(){
		cod = "";
	}
	
	/**
	 * Funci�n que modifica el contenido del atributo cod agreg�ndole la instrucci�n y el n�mero de l�nea que recibe como par�metro.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * @param num Entero indica el numero de l�nea.
	 * @return Esta funci�n no devuelve nada
	 * 
	 */
	public void genIns(String instr, int num){
		cod = cod + instr + " " + num + "\n";
	}
	
	/**
	 * Funci�n que modifica el contenido del atributo cod agreg�ndole la instrucci�n que recibe como par�metro y un salto de l�nea.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void genIns(String instr){
		cod = cod + instr + "\n";
	}
	
	/**
	 * Funci�n que imprime por pantalla el contenido del atributo cod. No tiene par�metros de entrada ni de salida.
	 * 
	 */
	public void muestraCodigo(){		
		System.out.println(cod);
	}
}