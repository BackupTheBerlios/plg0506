import java.io.File;
//import java.io.RandomAccessFile;
import javax.swing.JOptionPane;
import procesador.*;
//import maquinaP.*;
public class pruebaSintactico {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File fuente;
		String f= "Ejemplo.txt";
		//String cod = new String();
		try{
			fuente= new File(f);
			Sintactico parser = new Sintactico(fuente);
			parser.startParsing();
			Codigo c = parser.getCodigo();
			c.muestraCodigo();
			}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}

	}

}
