/*
 * Created on 24-abr-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package procesador;
import java.util.Hashtable;


/**
 * @author kat
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Procesador {
	
	public static void main(String[] args) {
		
		//Si no hemos recibido por parametro el archivo fuente, avisamos del error.
		if (args.length == 0)
			System.out.println("ERROR: Debe indicar fichero fuente como parametro.");
		else {	
			//Por ahora vamos a usar una tabla hash como Tabla de S???bolos.
			Hashtable ht = new Hashtable();	
			Token s;
			boolean error = false;
			Sintactico sintactico;
			try {		
				sintactico = new Sintactico(args[0], ht);
				System.out.println("Inicio del analisis\n");
				sintactico.startParsing();
				System.out.println("Fin del analisis");

/*				
				//Pedimos tokens al Analizador Lexico hasta que devuelva null, y los vamos mostrando.
				while ((s = lexico.daToken()) != null)
					System.out.print(s.daLexema() + " ");
*/			
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
		}
	}
}

