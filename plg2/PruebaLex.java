import java.io.File;
//import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
import procesador.Lexico;
import procesador.CategoriaLexica;
import procesador.Token;

public class PruebaLex {

	public static void main(String[] args) {
		File fuente;
		String f= "Ejemplo.txt";
		try{
			fuente= new File(f);
			Lexico lexico = new Lexico(fuente);
			Token tk=lexico.lexer();
			System.out.println("Inicio de fichero");
			System.out.println(tk.muestraToken());
			while(!tk.equals(new Token("eof",CategoriaLexica.TKFF))){
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
