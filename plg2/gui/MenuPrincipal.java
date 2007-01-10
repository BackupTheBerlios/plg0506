package gui;
/**
 * La clase <B>MenuPrincipal</B> es el main del compilador del lenguaje especificado. Lo �nico que hace es crear e inicializar un 
 * objeto de la clase MenuVentana.
 * <P>La clase Lexico no tiene atributos.
 * 
 * @author Jon�s Andradas, Paloma de la Fuente, Leticia Garc�a y Silvia Mart�n
 *
 */

public class MenuPrincipal{

	/**
	 * El m�todo main de esta clase inicializa la parte gr�fica del compilador.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MenuVentana vent= new MenuVentana();
		vent.initialize();
		vent.setVisible(true);
	}
}  
