//import maquinaP.Codigo;
import java.io.File;

import procesador.Codigo;
import maquinaP.MaquinaP;

public class pruebaMaquinaP {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
			String f = new String ("Ejemplo.txt");
			File file = new File(f);
			Codigo cod = new Codigo(file);
			cod.inicializaCodigo();
			cod.genIns("apila",10);
			cod.genIns("desapila-dir",0);
			cod.genIns("apila",5);
			cod.genIns("desapila-dir",1);
			cod.genIns("apila-dir", 0);
			cod.genIns("apila-dir", 1);
			cod.genIns("divide");
			cod.genIns("desapila-dir",2);
			cod.muestraCodigo();
			MaquinaP MP = new MaquinaP(f);
			MP.setProg(cod.getCod());
			MP.ejecuta();
			MP.muestraPila();
			MP.resultadoMem();
			//la memoria quedaría [10, 5, 2]
	}
}
