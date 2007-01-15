import java.io.File;
import procesador.*;
import maquinaP.MaquinaP;

public class pruebaMaquinaP {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
			String f = new String ("Ejemplo.txt");
			File file = new File(f);
			Procesador p = new Procesador();
			p.procesa(file);
			Codigo c = p.getCod();
			MaquinaP MP = new MaquinaP(f);
			MP.setProg(c.getCod());
			MP.ejecuta();
			MP.muestraPila();
			MP.resultadoMem();
	}
}
