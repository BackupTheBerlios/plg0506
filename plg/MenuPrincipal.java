

public class MenuPrincipal{

	

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
		MenuVentana vent= new MenuVentana();
		vent.initialize();
		vent.setVisible(true);
	}
}  
