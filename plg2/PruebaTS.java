import tablaSimbolos.*;

public class PruebaTS {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		tablaSimbolos TS = new tablaSimbolos();
		System.out.println("Vacia");
		TS.mostrar();
		System.out.println("Con int i");
		TS.addID("i","int");
		TS.mostrar();
		System.out.println("Con bool a");
		TS.addID("a","bool");
		TS.mostrar();
		System.out.println("¿Existe a?");
		boolean b = TS.existeID("a");
		System.out.println(b);
		System.out.println("¿Existe c?");
		b = TS.existeID("c");
		System.out.println(b);
		
	}

}
