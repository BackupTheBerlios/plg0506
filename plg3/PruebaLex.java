//import java.io.RandomAccessFile;
import java.io.File;
import javax.swing.JOptionPane;
import procesador.*;

public class PruebaLex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//RandomAccessFile fuente;
		File fuente;
		String f= "Ejemplo.txt";
		try{
			//fuente= new RandomAccessFile(f,"r");
			fuente = new File(f);
			Lexico lexico = new Lexico(fuente);
			System.out.println("Inicio de fichero");
			Token tk=lexico.lexer();
			System.out.println(tk.muestraToken());
			//while(!tk.equals(new Token("eof",CategoriaLexica.TKFF))){
			while(!tk.getLexema().equals("eof")){
				tk=lexico.lexer();
				System.out.println(tk.muestraToken());
			}
			System.out.println("Fin de fichero");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}

	}

}
