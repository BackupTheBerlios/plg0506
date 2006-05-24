import java.io.RandomAccessFile;

import javax.swing.JOptionPane;
import procesador.Lexico;
import procesador.Tipos;
import procesador.Token;

public class PruebaLex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RandomAccessFile fuente;
		String f= "Ejemplo.txt";
		try{
			fuente= new RandomAccessFile(f,"r");
			Lexico lexico = new Lexico(fuente);
			Token tk=lexico.lexer();
			while(!tk.equals(new Token("eof",Tipos.TKFF))){
				tk=lexico.lexer();
				System.out.println(tk.muestraToken());
			}
			System.out.println("por aqui 4");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}

	}

}
