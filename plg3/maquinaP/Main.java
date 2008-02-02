package maquinaP;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean stepbystep = false;
		String filename = "";
		
		if (args.length > 1 && args[0] == "-s") {
			stepbystep = true;
			filename = args[1];
		} else if (args.length == 1) {
			filename = args[0];
		} else {
			System.out.println("Error: falta nombre de archivo o parámetro no reconocido");
			System.exit(1);
		}
		
		Vector prog = deserialize(filename);
		
		MaquinaP mp = new MaquinaP(prog, stepbystep);
		String resultado = "";
		try {
			resultado = mp.ejecuta();
		} catch (Exception e) {
			System.out.println("Error: excepcion ejecutando el programa");
			System.exit(1);
		}
		System.out.println("=== RESULTADO FINAL DE LA EJECUCION ===");
		System.out.println(resultado);

	}

	
	private static Vector deserialize(String filename) {
		
		Vector prog = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);
			prog = (Vector)ois.readObject();
		} catch (Exception e) {
			System.out.println("Error: excepcion leyendo el archivo");
			System.exit(1);
		}
		return prog;
		
	}

	
}
