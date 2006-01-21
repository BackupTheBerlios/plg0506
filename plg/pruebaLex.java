import java.io.BufferedReader;
import java.io.FileReader;
import procesador.*;

public class pruebaLex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		BufferedReader fuente;
		Token tk;
		Lexico lex;
		
		try {
			fuente = new BufferedReader(new FileReader(args[0]));
			lex= new Lexico(fuente);
			try{
				tk= lex.getNextToken();
				tk.muestraToken();
			}
			catch (java.io.IOException ex) {
				System.out.println("El fichero no ha sido encontrado");
			}
			catch (Exception exc) {
				System.out.println("El fichero no ha sido encontrado");
			}
		} 
		catch (java.io.FileNotFoundException e) {
				System.out.println("El fichero no ha sido encontrado");
		}

	}

}
