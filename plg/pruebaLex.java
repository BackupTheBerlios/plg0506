import java.io.RandomAccessFile;
import java.io.File;
import procesador.*;

public class pruebaLex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
		RandomAccessFile fuente;
		Token tk;
		Lexico lex;
		String s;
		
		try {
			System.out.println("Hola, voy abrir el archivo");
			fuente = new RandomAccessFile(new File("petete"), "r");
			System.out.println("Creo un nuevo Lexico");
			lex= new Lexico(fuente);
			try{
				for (int i=0; i<24; i++){
					System.out.println("Voy a reconocer un Token");
					 tk= lex.getNextToken();
					 System.out.println("Voy a mostrar un Token");
					 s = tk.muestraToken();
					 System.out.println(s);
				}
			}
			catch (java.io.IOException ex) {
				System.out.println(ex.getMessage());
			}
			catch (Exception exc) {
				System.out.println(exc.getMessage());
			}
		} 
		catch (java.io.FileNotFoundException e) {
				System.out.println("El fichero no ha sido encontrado");
		}

	}

}