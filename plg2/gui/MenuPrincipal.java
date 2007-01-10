package gui;
/**
 * La clase <B>MenuPrincipal</B> es el main del compilador del lenguaje especificado. Lo único que hace es crear e inicializar un 
 * objeto de la clase MenuVentana.
 * <P>La clase Lexico no tiene atributos.
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
 *
 */

public class MenuPrincipal{

	/**
	 * El método main de esta clase inicializa la parte gráfica del compilador.
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MenuVentana vent= new MenuVentana();
		vent.initialize();
		vent.setVisible(true);
	}
}  
