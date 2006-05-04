package procesador;

import java.lang.String;
import java.util.Vector;

/**
 * La clase <B>Codigo</B> se encarga de manejar el c�digo generado por las instrucciones del lenguaje.
 * <P>Tiene un s�lo atributo:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el c�digo del lenguaje objeto, que es el c�digo de la m�quina P. Como la m�quina P de momento no existe, este el contenido de este atributo se mostrar� por pantalla.</LI></UL></P>
 * 
 * @author Jon�s Andradas, Paloma de la Fuente, Leticia Garc�a y Silvia Mart�n
 *
 */
public class Codigo {
	
	/*
	 * Atributos de la clase:
	 * 
	 *  Este String guarda el codigo del lenguaje objeto, que es el codigo de la maquina P que 
	 *  no existe asique de momento se mostrara por pantalla.
	 */
	Vector cod;
	
	/**
	 * El constructor de la clase Codigo no tiene par�metros de entrada ni de salida. Este constructor inicializa el atributo cod con la cadena vac�a.
	 */
	public Codigo(){		
		cod = new Vector();
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector getCod() {
		return cod;
	}

	/**
	 * Este m�todo inicializa el atributo cod con la cadena vac�a. No cuenta con par�metros de entrada ni de salida. No devuelve nada.
	 */
	public void inicializaCodigo(){
		cod = new Vector();
	}
	
	/**
	 * M�todo que modifica el contenido del atributo cod agreg�ndole la instrucci�n y el n�mero de l�nea que recibe como par�metro. No devuelve nada.
	 * 
	 * @param instr String con la instrucci�n que ha codificado.
	 * @param num Entero indica el n�mero de l�nea.
	 * 
	 */
	public void genIns(String instr, int num){
		String i =instr + " " + num;
		cod.add(i);
	}
	
	/**
	 * M�todo que modifica el contenido del atributo cod agreg�ndole la instrucci�n que recibe como par�metro y un salto de l�nea. No devuelve nada.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void genIns(String instr){
		String i= instr;
		cod.add(i);
	}
	
	/**
	 * M�todo que imprime por pantalla el contenido del atributo cod. No tiene par�metros de entrada ni de salida.
	 * 
	 */
	public void muestraCodigo(){		
		for (int i=0;i<cod.size();i++){
			System.out.println(cod.elementAt(i));
		}
	}
}