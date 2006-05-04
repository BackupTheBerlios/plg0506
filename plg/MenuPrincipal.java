import java.io.File;
import java.io.RandomAccessFile;
import java.util.Vector;

import procesador.Procesador;
import maquinaP.MaquinaP;

public class MenuPrincipal {

	/**
	 * El método main de esta clase implementa el menú principal de la practica que es capaz de procesar del lenguaje 
	 * y captura una excepción IOException procedente de la función de RandomAccessFile de JAVA si no se encuentra el 
	 * fichero de entrada. Además también se ofrece la posiblidad de ejecutar en la maquinaP el código obtenido despues
	 * de procesar el fichero fuente.
	 * 
	 * @param args Que contendrá la ruta del fichero a procesar.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		 * Declaramos el buffer que usaremos para leer de fichero.
		 */
		RandomAccessFile fuente;
		
		/*
		 * Si no hemos recibido por parametro el archivo fuente, avisamos del error.
		 */
		if (args.length == 0){
			System.out.println("ERROR: Debe indicar fichero fuente como parametro.");
		}
		else {	
			try {
				/*
				 * Tratamos de realizar todas las operaciones, si alguna falla y genera excepcion
				 * se recoge mas abajo.
				 */
				fuente = new RandomAccessFile(new File(args[0]),"r");
				Procesador p=new Procesador();
				Vector prog = p.procesa(fuente);
				MaquinaP maquina= new MaquinaP(prog);
				maquina.ejecuta();
				maquina.resultadoMem();
			} 
			catch (java.io.FileNotFoundException e) {
				System.out.println("ERROR: Archivo no encontrado: " + args[0]);	
			}
		}

	}

}
