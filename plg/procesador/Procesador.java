package procesador;
import java.io.BufferedReader;
import java.io.FileReader;
import tablaSimbolos.TablaSimbolos;

public class Procesador {

	/*
	 * Parametros de entrada: 
	 * Parametros de salida: 
	 * Execpciones: Exception, que lanzamos si no hemos podido abrir el fichero.
	 * 
	 * Menu principal del procesador de lenguaje. Lanza una excepcion si el archivo no 
	 * se encuentra.
	 */
	public static void main(String[] args) throws Exception{
		
		BufferedReader fuente;
		//Si no hemos recibido por parametro el archivo fuente, avisamos del error.
		if (args.length == 0){
			System.out.println("ERROR: Debe indicar fichero fuente como parametro.");
		}
		else {	
			try {
				fuente = new BufferedReader(new FileReader(args[0]));
			} 
			catch (java.io.FileNotFoundException e) {
				throw new Exception("ERROR: Archivo no encontrado: " + args[0]);	
			}
			TablaSimbolos TS = new TablaSimbolos();	
			Sintactico sintactico;
			try {		
				sintactico = new Sintactico(fuente, TS);
				System.out.println("Inicio del analisis\n");
				sintactico.startParsing();
				System.out.println("Fin del analisis");				
			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}

