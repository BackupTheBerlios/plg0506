package tablaSimbolos;

import java.util.Hashtable;

/**
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 * 
 */
public class Tipo {

	public enum tipo {bool, integer, ref, rec, array}
	tipo t; //BOOLEAN, INTEGER, ref, rec, array, 
	String id;
	int tam;
	int nElems;
	Tipo tBase;
	Hashtable<Object,Object> campos;
	int desplazamiento;
	
	public Tipo(tipo nt){
		this.t = nt;
		this.tam = 1;
	}
	
	public Tipo(){
		this.t = null;
		this.id = "";
		this.tam = 1;
		this.nElems = 0;
		this.tBase = null;
		this.campos = null;
		this.desplazamiento = 0;
	}
	
	public Tipo(tipo nt, String nid){
		this.t = nt;
		this.id = nid;
		this.tam = 1;	
	}
	
	
	public Tipo(tipo nt, String nid, int ntam){
		this.t = nt;
		this.id = nid;
		this.tam = ntam;	
	}
	
	public Tipo(tipo nt, String nid, Tipo ntBase, int nnElems){
		this.t = nt;
		this.id = nid;
		this.tBase = ntBase;
		this.nElems = nnElems;
	}
	
	public Tipo(tipo nt, String nid, int ntam, int nnElems, Tipo ntBase, Hashtable<Object,Object> ncampos, int ndesplazamiento){
		this.t = nt;
		this.id = nid;
		this.tam = 1;
		this.nElems = 0;
		this.tBase = null;
		this.campos = null;
		this.desplazamiento = 0;
		
		if (ntam != 1)
			this.tam = ntam;
		
		switch (nt){ 
		case ref:
			this.tBase = ntBase;
			break;
		case array:
			this.nElems =nnElems;
			this.tBase =ntBase;
			break;
		case rec:
			this.nElems = nnElems;
			this.campos = ncampos;
			this.desplazamiento = ndesplazamiento;
			break;
		}
	}
	
	/**
	 * Metodo para comprobar si existe ya un identificador declarado en los campos del record. Si es asi
	 * devolvera verdadero para que se genere un error.
	 * 
	 * @param id Identificador de variable que se va a comprobar si exite o no en el record.
	 * @return boolean Sera verdadero si el identificador si existe y falso en otro caso.
	 */
	public boolean existeCampo(String id)throws Exception{
		return this.campos.containsKey(id);
	}
	
	/**
	 * Metodo para añadir un nuevo identificador a el record. 
	 * 
	 * @param id Nombre del identificador que se va añadir.
	 * @param tipo Tipo del nuevo identificador a añadir.
	 * @return boolean Se devuelve verdadero si todo ha sido correcto. Falso en caso contrario.
	 * @throws Exception Excepcion generada si el identificador ya existe, se capturara en otro lugar.
	 */
	public boolean addCampo(String id, Tipo tipo) throws Exception{
		if (this.campos.containsKey(id)){
			throw new Exception ("No se puede duplicar el identificador");
		}
		else{
			this.campos.put(id,tipo);
			return true;	
		}	
	}
	
	
	public String toString(){
		String toString = t.toString();
		toString += " "+ id;
		toString += " "+ Integer.toString(tam).toString();		
		switch (t){ 
			case ref:
				toString += " " + tBase.toString();
				break;
			case array:
				toString += " " + Integer.toString(nElems);
				toString += " " + tBase.toString();
				break;
			case rec:
				toString += " " + Integer.toString(nElems);
				toString += " " + campos.toString();
				toString += " " + Integer.toString(desplazamiento);
				break;
		}
		return toString;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the t
	 */
	public tipo getT() {
		return t;
	}
	
	public tipo getTipo() {
		return t;
	}

	/**
	 * @param t the t to set
	 */
	public void setT(tipo t) {
		this.t = t;
	}

	/**
	 * @return the campos
	 */
	public Hashtable<Object, Object> getCampos() {
		return campos;
	}

	/**
	 * @param campos the campos to set
	 */
	public void setCampos(Hashtable<Object, Object> campos) {
		this.campos = campos;
	}

	/**
	 * @return the desplazamiento
	 */
	public int getDesplazamiento() {
		return desplazamiento;
	}

	/**
	 * @param desplazamiento the desplazamiento to set
	 */
	public void setDesplazamiento(int desplazamiento) {
		this.desplazamiento = desplazamiento;
	}

	/**
	 * @return the nElems
	 */
	public int getNElems() {
		return nElems;
	}

	/**
	 * @param elems the nElems to set
	 */
	public void setNElems(int elems) {
		nElems = elems;
	}

	/**
	 * @return the tam
	 */
	public int getTam() {
		return tam;
	}

	/**
	 * @param tam the tam to set
	 */
	public void setTam(int tam) {
		this.tam = tam;
	}

	/**
	 * @return the tBase
	 */
	public Tipo getTBase() {
		return tBase;
	}

	/**
	 * @param base the tBase to set
	 */
	public void setTBase(Tipo base) {
		tBase = base;
	}
}
