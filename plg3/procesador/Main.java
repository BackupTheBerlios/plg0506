package procesador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;


/**
 * Esta es la clase donde se encuentra el Main del procesador.
 * 
 * 
 * @author usuario_local
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filein, fileout;
		
		if (args.length < 2) {
			System.out.println("Error: debe especificar un archivo de entrada y otro de salida");
			System.exit(1);
		}
		
		filein = args[0];
		fileout = args[1];
		
		Procesador procesador = new Procesador();
		try {
			procesador.procesa(new File(filein));
		} catch (Exception e) {
			// Si hay errores no prosigue
			System.out.println(e.toString());
			System.exit(1);
		}
		Codigo codigo = procesador.getCod();
		Vector<String> prog = codigo.getCod();
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(fileout);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(prog);
			oos.close();
		} catch (Exception e) {
			System.out.println("Error: excepcion creando el archivo");
			System.exit(1);
		}
		
	}

}
