import nGui.GUI;

/**
 * Clase que contiene el menu principal del compilador desde donde se llama a la Gui para que el usuario pueda manejar el compilador.
 * @author Paloma de la Fuente, Leticia Garcia, Ines Gonzalez, Emilia Rodriguez y Alberto Velazquez 
 *
 */
public class Main {
	
	/**
	 * Main del compilador.
	 * @param args Parametros de entrada. En este caso no se utilizan.
	 */
	public static void main(String[] args) {
		System.out.println("Main main");	
		GUI mainGui = new GUI();
		mainGui.main();
		
	}
}
