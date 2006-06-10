import tablaSimbolos.TablaSimbolos;
import tablaSimbolos.Atributos;
import java.util.Vector;

public class PruebaTS {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		TablaSimbolos t = new TablaSimbolos();
		Atributos aux = new Atributos();
		t.agnadeID("a", aux, "tipo", 0);
		t.agnadeID("b", aux, "proc", 0);
		t.agnadeID("c", aux, "var", 0);
		t.agnadeID("d", aux, "var", 0);
		t.agnadeID("a1", aux, "tipo", 0);
		t.agnadeID("b1", aux, "proc", 0);
		t.agnadeID("c1", aux, "var", 0);
		t.agnadeID("d1", aux, "var", 0);
		System.out.println("La padre: ");
		t.muestra();
		Vector v = new Vector();
		v.add("c");
		v.add("c1");
		TablaSimbolos thija = new TablaSimbolos(t.getTabla(), v);
		System.out.println("La hija: ");
		thija.muestra();
	}
}
