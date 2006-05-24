import maquinaP.Codigo;

public class pruebaEmite {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String f = new String ("Ejemplo.txt");
		Codigo cod = new Codigo(f);
		cod.inicializaCodigo();
		cod.genIns("apila",1);
		cod.emite("ir-f");
		cod.genIns("apila",2);
		cod.emite("ir-a");
		cod.genIns("apila",3);
		cod.parchea(1,4);
		cod.parchea(3,8);
		cod.muestraCodigo();
	}

}
