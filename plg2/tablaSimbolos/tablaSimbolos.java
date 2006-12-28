package tablaSimbolos;
import java.util.Hashtable;

public class tablaSimbolos {

		
	Hashtable tabla;

	
	public tablaSimbolos() {
		super();
		// TODO Auto-generated constructor stub
	}


	public tablaSimbolos(Hashtable tabla) {
		super();
		// TODO Auto-generated constructor stub
		this.tabla = tabla;
	}

	public boolean existeID(String id){
		return this.tabla.containsKey(id);
	}
	
	public boolean addID(String id, String tipo) throws Exception{
		if (this.tabla.containsKey(id)){
			throw new Exception ("No se puede duplicar el identificador");
		}
		else{
			propiedades prop = new propiedades(tipo);
			Par p = new Par(id, prop);
			this.tabla.put(id,p);
			return true;	
		}	
	}

	public Hashtable getTabla() {
		return tabla;
	}


	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
