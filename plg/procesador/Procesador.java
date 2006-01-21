package procesador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import tablaSimbolos.TablaSimbolos;

public class Procesador {
	
	public static void main(String[] args) {
		
		//Si no hemos recibido por parametro el archivo fuente, avisamos del error.
		if (args.length == 0){
			System.out.println("ERROR: Debe indicar fichero fuente como parametro.");
		}
		else {	
			try {
				BufferedReader fuente = new BufferedReader(new FileReader(args[0]));
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

