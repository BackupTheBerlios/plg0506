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
			System.out.println("El resultado del parser: ");
			Codigo c = p.getCod();
			c.muestraCodigo();
			MaquinaP MP = new MaquinaP(f);
			MP.setProg(c.getCod());
			MP.ejecuta();
			System.out.print(MP.resultadoMem());
	}
}
