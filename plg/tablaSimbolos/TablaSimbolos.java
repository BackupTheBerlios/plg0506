package tablaSimbolos;

import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * La clase <B>TablaSimbolos</B> define los atributos y mtodos relacionados con la tabla de smbolos.
 * <P>Esta clase cuenta con el siguiente atributo:
 * <UL><LI><CODE>tabla:</CODE> Usaremos del API de JAVA una tabla Hash como tabla de smbolos. El valor que almacenamos ser un par con el nombre del identificador y el tipo del mismo. 
 * No se podrn almacenar datos repetidos.</LI></UL></P>
 * 
 * @author Jons Andradas, Paloma de la Fuente, Leticia Garca y Silvia Martn
 *
 */
public class TablaSimbolos {
	
	/*
	 * Atributos de la clase:
	 * 
	 * Usaremos del API de java una tabla Hash como tabla de simbolos. El valor 
	 * que almacenamos sera un par con el nombre del identificador y el tipo del 
	 * mismo. No se podran almacenar datos repetidos.
	 */
	Hashtable tabla;

	/**
	 * Constructor de la clase sin parmetros que inicializa la tabla de smbolos con una tabla vaca.
	 */
	public TablaSimbolos() {
		super();
		this.tabla = new Hashtable();
	}

	/**
	 * Constructor de la clase que recibe una tabla de smbolos por parametro y la usa para inicializar la tabla de smbolos creada.
	 * 
	 * @param tabla Hashtable recibida por parmetro y a la que se inicializa la nueva tabla creada.
	 */
	public TablaSimbolos(Hashtable tabla) {
		super();
		this.tabla = new Hashtable(tabla);
	}

	public TablaSimbolos(Hashtable t, Vector params) throws Exception{
		System.out.println("En el constr. chulo: ");
		System.out.println(params.toString());
		this.tabla = new Hashtable();
		Enumeration e = t.keys();
		while (e.hasMoreElements()){ //copiamos
			Object aux = e.nextElement();
			Par elem = new Par ((Par)t.get(aux));
			this.tabla.put(elem.getId(),elem);
		}
		/*System.out.println("copiamos en el constructor chachi");
		this.muestra();
		e = t.keys();
		System.out.println("Las claves: ");
		System.out.println(e.toString());
		while (e.hasMoreElements()){ //cambiamos a solo lectura los q no son parámetros
			Object aux = e.nextElement();
			System.out.println("La clave actual:");
			System.out.println(aux.toString());
			System.out.println("En el constr. chulo antes de cambiar: ");
			System.out.println(params.toString());
			if ( ! params.contains(((Par)aux).getId()) ){
				System.out.println("Cambiamos:" + aux.toString());
				((Par)tabla.get(aux)).setClase("valor");
			}
		}*/
		e = t.keys();
		while (e.hasMoreElements()){ //copiamos
			Object aux = e.nextElement();
			if (((Par)this.tabla.get(aux)).getClase().equals("var")){
				((Par)this.tabla.get(aux)).setClase("valor");
			}
		}
		System.out.println("en el constructor chachi puestos todos a valor");
		this.muestra();
		for (int i = 0; i<params.size(); i++){
			String aux = ((Par)params.elementAt(i)).getId();
			System.out.println("Elemento es;" + aux);
			if (this.existeID(aux)){
				System.out.println("y está");
				((Par)this.tabla.get(aux)).setClase("var");
			}	
		}
		System.out.println("en el constructor chachi");
		this.muestra();
	}
	
	/**
	 * Accesor para el atributo de la clase tabla.
	 * @return Hashtable recibida por parmetro y a la que se inicializa la nueva tabla creada.
	 */
	public Hashtable getTabla() {
		return tabla;
	}
	/**
	 * Mutador para el atributo de la clase tabla.
	 * @param tabla Hashtable recibida por parmetro y a la que se inicializa la nueva tabla creada.
	 */
	public void setTabla(Hashtable tabla) {
		this.tabla = tabla;
	}

	/**
	 * El mtodo existeID discrimina si un identificador 'id' de tipo 't' existe en la tabla de smbolos, 
	 * solo si existe su clave. La clave de cada identificador de un tipo es nica, no habr repeticiones.
	 *
	 * @param id String que almacena el nombre del identificador 
	 * @return Booleano que indica la existencia o no de este valor en la tabla.
	 */
	public boolean existeID(String id){
		
		return this.tabla.containsKey(id);
	}
	
	/**
	 * Este mtodo devuelve el tipo de un identificador que le llega por prametro. Y el resultado lo devuelve como un String.
	 * @param id Identificador sobre el cual se va a buscar su tipo.
	 * @return String que devuelve el tipo asociado al id que entra como parmetro.
	 */
	public Atributos getProps(String id){
		if (this.tabla.containsKey(id))
			return ((Par)this.tabla.get(id)).getProps();
		else
			return null;
	}
	/**
	 * Accesor de la direccin de un identificador en la tabla de smbolos
	 * @param id String con el identificador
	 * @return el entero con la direccin
	 */
	public int getDir(String id){
		if (this.tabla.containsKey(id))
			return ((Par)this.tabla.get(id)).getDir();// Devolver la dir...
		else
			return -1;
	}
	/**
	 * Accesor de la clase de un identificador en la tabla de smbolos
	 * @param id String con el identificador
	 * @return String con el nombre de la clase del identificador
	 */
	public String getClase(String id){
		if (this.tabla.containsKey(id))
			return ((Par)this.tabla.get(id)).getClase();// Devolver la clase...
		else
			return "";
	}

	/**
	 * El mtodo agnadeID aade un identificador 'id' de tipo 't' a la tabla si no existe ningun identificador con ese nombre 
	 * El valor de la clave en la tabla hash es znico, ya que concatenamos el nombre del identificador con el tipo usando 
	 * entre medias un carcter de separacion: '#'. Como valor para la tabla introducimos un par, que son el nombre del id 
	 * y el tipo. Si ya existe el par entonces no es posible aadir y lanzamos una excepcin
	 * 
	 * @param id String que tienen almacenado el nombre del identificador
	 * @param t String que tienen almacenado el tipo del identificador
	 * @return Booleano que indica si la operacin se realizo bien.
	 * @exception Exception Porque no se puede duplicar un identificador
	 * 
	 */
	public boolean agnadeID(String id, Atributos t, String clase, int dir, int nivel) throws Exception{
		if (this.tabla.containsKey(id)){
			throw new Exception ("No se puede duplicar el identificador");
		}
		else{
			Par p = new Par(id, t, clase, dir, nivel);
			this.tabla.put(id,p);
			return true;	
		}	
	}

	/**
	 * El mtodo eliminaID elimina un identificador 'id' de la tabla si existe. 
	 * Si no, lanzara una excepcion.
	 * 
	 * @param id String que tienen almacenado el nombre del identificador
	 * @return Booleano que indica si la operacin se realizo bien.
	 * @exception  Exception Porque no se encuentra el par a eliminar
	 * 
	 */
	public boolean eliminaID(String id) throws Exception{
		if (!this.tabla.containsKey(id)){
			throw new Exception("No se puede eliminar el identificador: no existe");
		}
		else{
			this.tabla.remove(id);
			return true;	
		}	
	}
	
	/**
	 * El mtodo muestra se usa para mostrar por pantalla la tabla de smbolos actual.
	 */
	public void muestra(){
		String aux = this.tabla.toString();
		System.out.println(aux);
	}

	/**
	 * El mC)todo dirI obtiene la posicin de memoria del identificador "id" proporcionado como parametro.
	 * Es necesario para saber, cuando generemos el cdigo, de dnde tomar los valores necesarios.
	 *  
     * @param id String que tienen almacenado el nombre del identificador
	 * @return i int que nos indica la posicin en memoria del identificador que indica si la operacin se realizo bien.
	 * 
	 *  
	 */
	public int dirID(String id){
		int i = 0;
		for (Enumeration e = this.tabla.keys(); e.hasMoreElements();){
			String s = (String)e.nextElement();
			if(s.equals(id)){
				return i;
			}
			i ++;
	     }
		return i;
	}	
	
	/*
	 * fun ref!(exp,ts)
	 * 	si exp.t = ref entonces
	 * 		si existeID(ts,exp.id)
	 * 			devuelve ref!(ts[exp.id].tipo,ts)
	 * 		si no <t:err>
	 * 	si no devuelve exp
	 * ffun
	 */
	/**
	 * Mtodo que resuelve la referencia de tipos de identificadores.
	 * Recibe como parmetro exp que es del tipo Atributos
	 */
	public Atributos ref(Atributos exp){
		//System.out.println("Dentro de REF:  " + exp);
		
		/*String iden = exp.getTipo();
		if (this.existeID(iden)){
			return (this.getProps(iden));
		}
		else{
			return exp;
		}*/
		
		if (exp.getTipo().equals("ref")){
			//System.out.println("Es una referencia.");
			String iden = exp.getTbase().getTipo();
			//System.out.println("Su iden del tbase es: " + iden);
			if (iden.equals("bool") || iden.equals("int") || iden.equals("pointer") || iden.equals("array")){
				return exp.getTbase();
			} 
			else if(this.existeID( iden )){
				//System.out.println("Ref recursivo.");
				return ref( this.getProps(iden) );
			}
			else{
				Atributos a = new Atributos("err","",0,0, new Vector());
				return a;
			}
		}
		else if (existeID(exp.getTipo())){
			//System.out.println("Tengo el tipo en la TS");
			return ref(getProps(exp.getTipo()));
			
		}
		else{
			//System.out.println("No sabemos deshacer la referencia y es:" + exp);
			return exp;
		}
		
	}
	
	/*
	 * fun referenciaErronea(e,ts)
	 * 	devuelve e.t=ref ??? ??existeID(ts,e.id)
	 */
	/**
	 * Mtodo que devuelve el booleano correspondiente a evaluar la condicin de si el Par que se le pasa como parmetro es un referencia errnea
	 */
	public boolean referenciaErronea(Par e){
		//System.out.println("En ref erroenes ref es");
		//System.out.println(e.getProps().getTipo());
		//System.out.println(e.getProps().getTipo().equals("ref"));
		return e.getProps().getTipo().equals("ref") && !this.existeID(e.getId());
	}
	/**
	 * Funcin que devuelve el booleano correspondiente a evaluar si dos Atributos son compatibles
	 * @param e1 Atributo
	 * @param e2 Atributo
	 * @return Booleano
	 */
	public boolean compatibles (Atributos e1, Atributos e2)
	{
		Vector visitadas = new Vector();
		return compatibles2 (visitadas, e1, e2);
	}
	/*
	 * fun compatibles(e1,e2,ts)
	visitadas ? ?
	compatibles2(e1,e2)
ffun*/

/*fun compatibles2(e1,e2)
	si <e1,e2> ? visitadas 
		devuelve true
	si no visitadas?visitadas?{<e1,e2>}
	si (e1.t = e2.t = num) ? (e1.t = e2.t = bool)
		devuelve true
	si no si e1.t = ref 
		devuelve compatibles2(ts[e1.id].tipo,e2)
	si no si e2.t = ref 
		devuelve compatibles2(e1,ts[e2.id].tipo)
	si no si e1.t=e2.t=array ? e1.nelems=e2.nelems
		devuelve compatibles2(e1.tbase,e2.tbase)
	si no si e1.t = e2.t = puntero
		devuelve compatible2(e1.tbase,e2.tbase)
	si no 
		devuelve false
ffun */
	/**
	 * Funcin auxliar para implementar la funcin compatibles
	 * @param v Vector
	 * @param e1 Atributos
	 * @param e2 Atributos
	 * @return el entero que dice si son compatibles
	 */

public boolean compatibles2 (Vector v, Atributos e1, Atributos e2)
{
	if (v.contains(e1) && v.contains(e2)){
		return true;
	}
	else{
		v.addElement(e1);
		v.addElement(e2);
	}
	if ((e1.getTipo().equals(e2.getTipo()) && e2.getTipo().equals("int"))
			|| (e1.getTipo().equals(e2.getTipo()) && e2.getTipo().equals("bool"))){
		return true;
	}
	else if (e1.getTipo().equals("ref"))
			return compatibles2 (v, e1.getTbase(), e2);
		else if (e2.getTipo().equals("ref")) 
			return compatibles2 (v, e1, e2.getTbase());	
		else if (((e1.getTipo()==e2.getTipo() && e2.getTipo().equals("array")) && (e1.getElems() == e2.getElems())))
					return compatibles2 (v, e1.getTbase(), e2.getTbase());
			else if (e1.getTipo().equals("puntero") && e2.getTipo().equals("puntero"))
					return compatibles2(v, e1.getTbase(), e2.getTbase());
				else
					return false;
	/*if (ref(e1).getTipo().equals(ref(e2).getTipo())) {
		if (ref(e1).getTipo().equals("int") || ref(e1).getTipo().equals("bool")){
			return true;
		}
		else if (ref(e1).getTipo().equals("array")){
			return (ref(e1).getElems() == ref(e2).getElems() && ref(e1).getTam() == ref(e2).getTam() &&
					compatibles(ref(e1).getTbase(),ref(e2).getTbase()));
		}
		else if (ref(e1).getTipo().equals("pointer")){
			return (ref(e1).getTam() == ref(e2).getTam() &&	compatibles(ref(e1).getTbase(),ref(e2).getTbase()));
		}
	}
	else {
		return false;
	}*/		
	}
}