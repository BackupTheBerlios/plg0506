package procesador;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
//import java.util.Vector;

import tablaSimbolos.TablaSimbolos;

/**
 * La clase <B>Procesador</B> implementa la funci�n del men� principal del procesador del lenguaje.
 * <P>No tiene atributos</P>
 * 
 * @author Jon�s Andradas, Paloma de la Fuente, Leticia Garc�a y Silvia Mart�n
 *
 */
public class Procesador {

	/**
	 * El m�todo main de esta clase implementa el men� principal del procesador del lenguaje y captura una excepci�n
	 * IOException procedente de la funci�n de RandomAccessFile de JAVA si no se encuentra el fichero de entrada.
	 * 
	 * @param fuente Que contendr� la ruta del fichero a procesar.
	 * 
	 */
	public void procesa(RandomAccessFile fuente, String f){
		
			try {
				/*
				 * Tratamos de realizar todas las operaciones, si alguna falla y genera excepcion
				 * se recoge mas abajo.
				 */
				TablaSimbolos TS = new TablaSimbolos();	
				Sintactico sintactico;		
				sintactico = new Sintactico(fuente, TS, f);
				System.out.println("Inicio del analisis\n");
				sintactico.startParsing();
				System.out.println("Fin del analisis");
				//return sintactico.getCodigo().getCod();
			} 
			catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
		
	}
}