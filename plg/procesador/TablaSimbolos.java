package procesador;

import java.util.Hashtable;
//import java.util.Vector;

public class TablaSimbolos {

  private Hashtable tabla;

  
  public TablaSimbolos(){

  	tabla = new Hashtable();

  }

  public void aadeID(String nombre, int dir){

      tabla.put(nombre, new Integer(dir));

  }

  /*
  public simbolo dameID(String nombre){
    // Devuelve el simbolo correspondiente a la variable nombre
    // tal como est hecho, si no existe tal variable
    // devuelve null
    return (simbolo)tabla.get(nombre);
  }
*/
  
  public int dirID(String id){

  	Integer n = (Integer)tabla.get(id);
  	
  	return n.intValue();
  	
  }

  public boolean existeID(String id){

	return tabla.contains(id);

  }
  
  public void creaTS(){

  	// Al crear la tabla de smbolos aadimos directamente las palabras
    // reservadas: true, false, real y bool
/*
  	tabla.put("true", new simbolo(new Integer(BOOLEANO),0,new Integer(1)));
    tabla.put("false", new simbolo(new Integer(BOOLEANO),0,new Integer(0)));
    tabla.put("real", new simbolo(new Integer(TIPO),0,null));
    tabla.put("bool", new simbolo(new Integer(TIPO),0,null));
*/
  }
  
}



