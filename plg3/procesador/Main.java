package procesador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class Main {
	
	/*
	 * Esta clase es para hacer pruebas.
	 * 
	 * -- Alberto
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filein, fileout;
		
		if (args.length < 2) {
			System.out.println("Debe especificar un archivo de entrada y otro de salida");
			System.exit(1);
		}
		
		filein = args[0];
		fileout = args[1];
		
		Procesador procesador = new Procesador();
		procesador.procesa(new File(filein)); //FIXME: no continuar si hay errores
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
