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
			System.out.println("Error: falta nombre de archivo o parametro no reconocido");
			System.exit(1);
		}
		Vector<String> prog = deserialize(filename);
		
		MaquinaP mp = new MaquinaP(prog, traza);
		String resultado = "";
		try {
			resultado = mp.ejecuta();
		} catch (Exception e) {
			System.out.println("Error: excepcion ejecutando el programa");
			System.out.println(e.toString());
			System.exit(1);
		}
	}

	
	@SuppressWarnings("unchecked")
	private static Vector<String> deserialize(String filename) {
		Vector<String> prog = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(filename);
			ois = new ObjectInputStream(fis);
			prog = (Vector<String>)ois.readObject();
			ois.close();
		} catch (Exception e) {
			System.out.println("Error: excepcion leyendo el archivo");
			System.exit(1);
		}
		return prog;
		
	}

	
}
