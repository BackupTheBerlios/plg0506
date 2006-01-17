package procesador;

public class prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TablaSimbolos ts = new TablaSimbolos();
		ts.agnadeTipo("int");
		ts.agnadeID("int","a");
		ts.agnadeID("int","b");
		boolean b = ts.getTabla().containsKey("int");
		System.out.println(b);
		b = ts.getTabla().containsKey("bool");
		System.out.println(b);
		b = ts.existeID("a","bool");
		System.out.println(b);
		b = ts.existeID("c","int");
		System.out.println(b);
		b = ts.existeID("b","int");
		System.out.println(b);
	}

}
