package procesador;
import java.io.RandomAccessFile;

import javax.swing.JOptionPane;

/**
 * La clase <B>Procesador</B> es el procesador del compilador del lenguaje. Se encarga de crear la Tabla de Simbolos,
 * de llamar a Sintactico, para empezar el analisis.
 * 
 * <P>No tiene atributos</P>
 * 
 * @author Paloma de la Fuente
 *
 */
public class Procesador {

	/**
	 * El método procesa de esta clase implementa del procesador del lenguaje y captura una excepción
	 * IOException procedente de la función de RandomAccessFile de JAVA si no se encuentra el fichero de entrada.
	 * 
	 * @param fuente Que contendrá la ruta del fichero a procesar.
	 * @param f String con la ruta del fichero.
	 * 
	 */
	public void procesa(RandomAccessFile fuente, String f){
		
			try {
				/*
				 * Tratamos de realizar todas las operaciones, si alguna falla y genera excepcion
				 * se recoge mas abajo.
				 */
				Sintactico sintactico;		
				sintactico = new Sintactico(fuente, f);
				System.out.println("Inicio del analisis\n");
				sintactico.startParsing();
				System.out.println("Fin del analisis");
			} 
			catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
			}
	}
}