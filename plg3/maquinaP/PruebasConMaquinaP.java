package maquinaP;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

public class PruebasConMaquinaP {

	public PruebasConMaquinaP(String filename) {
		Vector<Object> prog = new Vector<Object>();
		// Codigo del programa para MaquinaP
		prog.add("read");
		prog.add("write");
		// Fin del codigo
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(filename);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(prog);
			oos.close();
		} catch (Exception e) {
			System.out.println("Error: excepcion creando el archivo");
			System.exit(1);
		}
	}

}
