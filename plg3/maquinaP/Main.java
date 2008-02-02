package maquinaP;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Vector;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		boolean traza = false;
		String filename = "";
		
		if (args.length > 1 && args[0].equals("-s")) {
			traza = true;
			filename = args[1];
		} else if (args.length == 1) {
			filename = args[0];
		} else {
			System.out.println("Error: falta nombre de archivo o parámetro no reconocido");
			System.exit(1);
		}
		
		//Para hacer pruebas:
		new PruebasConMaquinaP(filename);
		
		Vector<Object> prog = deserialize(filename);
		
		MaquinaP mp = new MaquinaP(prog, traza);
		String resultado = "";
		try {
			resultado = mp.ejecuta();
		} catch (Exception e) {
			System.out.println("Error: excepcion ejecutando el programa");
			System.out.println(e.toString());
			System.exit(1);
		}
		System.out.println("=== RESULTADO FINAL DE LA EJECUCION ===");
		System.out.println(resultado);

	}

	
	private static Vector<Object> deserialize(String filename) {
		
		Vector<Object> prog = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);
			prog = (Vector<Object>)ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println("Error: excepcion leyendo el archivo");
			System.exit(1);
		}
		return prog;
		
	}

	
}
