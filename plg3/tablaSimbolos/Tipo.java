package tablaSimbolos;

import java.util.Hashtable;

/**
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 * 
 */
public class Tipo {

	public enum tipo {BOOLEAN, INTEGER, ref, rec, array}
	tipo t; //BOOLEAN, INTEGER, ref, rec, array, 
	String id;
	int tam;
	int nElems;
	Tipo tBase;
	Hashtable<Object,Object> campos;
	int desplazamiento;
	
	
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
}
