import java.io.File;
import javax.swing.JOptionPane;
import procesador.*;
public class pruebaSintactico {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//RandomAccessFile fuente;
		String f= "Ejemplo.txt";
		File fuente = new File(f);
		//String cod = new String();
		try{
			//fuente= new RandomAccessFile(f,"r");
			Sintactico parser = new Sintactico(fuente);
			fuente= new File(f);
			parser.startParsing();
			Codigo c = parser.getCodigo();
			c.muestraCodigo();
			}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}

	}

}
