

public class MenuPrincipal{

	

	/**
	 * El m�todo main de esta clase implementa el men� principal de la practica que es capaz de procesar del lenguaje 
	 * y captura una excepci�n IOException procedente de la funci�n de RandomAccessFile de JAVA si no se encuentra el 
	 * fichero de entrada. Adem�s tambi�n se ofrece la posiblidad de ejecutar en la maquinaP el c�digo obtenido despues
	 * de procesar el fichero fuente.
	 * 
	 * @param args Que contendr� la ruta del fichero a procesar.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MenuVentana vent= new MenuVentana();
		vent.initialize();
		vent.setVisible(true);
	}
}  
