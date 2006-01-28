package procesador;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;
import tablaSimbolos.TablaSimbolos;
/**
 * La clase <B>Procesador</B> implementa la función del menú principal del procesador del lenguaje
 * <P>No tiene atributos</P>
 * 
 * @author Paloma de la Fuente, Jonás Andradas, Leticia García y Silvia Martín
 *
 */
public class Procesador {

	/**
	 * El método main de esta clase implementa el menú principal del procesador del lenguaje y captura una excepción
	 * IOException procedente de la función de RandomAccessFile de JAVA si no se encuentra el fichero de entrada.
	 * 
	 * @param ruta del fichero a procesar.
	 * 
	 */
	public static void main(String[] args){
		
		/*
		 * Declaramos el buffer que usaremos para leer de fichero.
		 */
		RandomAccessFile fuente;
		
		/*
		 * Si no hemos recibido por parametro el archivo fuente, avisamos del error.
		 */
		if (args.length == 0){
			System.out.println("ERROR: Debe indicar fichero fuente como parametro.");
		}
		else {	
			try {
				/*
				 * Tratamos de realizar todas las operaciones, si alguna falla y genera excepcion
				 * se recoge mas abajo.
				 */
				fuente = new RandomAccessFile(new File(args[0]),"r");
				TablaSimbolos TS = new TablaSimbolos();	
				Sintactico sintactico;		
				sintactico = new Sintactico(fuente, TS);
				System.out.println("Inicio del analisis\n");
				sintactico.startParsing();
				System.out.println("Fin del analisis");				 
			} 
			catch (java.io.FileNotFoundException e) {
				System.out.println("ERROR: Archivo no encontrado: " + args[0]);	
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

