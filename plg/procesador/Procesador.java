/
package procesador;
import java.util.Hashtable;

public class Procesador {
	
	public static void main(String[] args) {
		
		//Si no hemos recibido por parametro el archivo fuente, avisamos del error.
		if (args.length == 0){
			System.out.println("ERROR: Debe indicar fichero fuente como parametro.");
		}
		else {	
			//Por ahora vamos a usar una tabla hash 
			//como Tabla de Simbolos.
			Hashtable ht = new Hashtable();	
			Token s;
			boolean error = false;
			Sintactico sintactico;
			try {		
				sintactico = new Sintactico(args[0], ht);
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

