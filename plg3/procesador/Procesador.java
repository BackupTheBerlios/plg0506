package procesador;

import java.io.File;
import javax.swing.JOptionPane;

/**
 * La clase <B>Procesador</B> es el procesador del compilador del lenguaje. Se encarga de crear la Tabla de Simbolos,
 * de llamar a Sintactico, para empezar el analisis.
 * 
 * <P>No tiene atributos</P>
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 * 
 */
public class Procesador {

	protected Sintactico sintactico;
	
	public Procesador() {
		super();
	}
	/**
	 * El mtodo procesa de esta clase implementa del procesador del lenguaje y captura una excepcin
	 * IOException procedente de la funcin de RandomAccessFile de JAVA si no se encuentra el fichero de entrada.
	 * 
	 * @param f String con la ruta del fichero.
	 * @throws Exception 
	 * 
	 */
	public void procesa(File f) throws Exception{
		try{
			sintactico = new Sintactico(f);
			System.out.println("Inicio del analisis\n");
			sintactico.startParsing();
			System.out.println("Fin del analisis");
		}catch (Exception e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Accesor del codigo generado al procesar el codigo fuente.
	 * 
	 * @return devuelve el codigo objeto que se ha generado al procesar el fichero de codigo fuente.
	 */
	public Codigo getCod() {
		return sintactico.getCodigo();
	}
}