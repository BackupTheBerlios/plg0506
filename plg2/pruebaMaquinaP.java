import maquinaP.Codigo;
import maquinaP.MaquinaP;

public class pruebaMaquinaP {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
			String f = new String ("Ejemplo.txt");
			Codigo cod = new Codigo(f);
			cod.inicializaCodigo();
			cod.genIns("apila",10);
			cod.genIns("desapila_dir",0);
			cod.genIns("apila",5);
			cod.genIns("desapila_dir",1);
			cod.genIns("apila_dir", 0);
			cod.genIns("apila_dir", 1);
			cod.genIns("divide");
			cod.genIns("desapila_dir",2);
			MaquinaP MP = new MaquinaP(f);
			MP.setProg(cod.getCod());
			MP.muestraPila();
			MP.ejecuta();
			MP.muestraPila();
			MP.resultadoMem();
			//la memoria quedaría [10, 5, 2]
	}
}
