package procesador;

import java.io.File;
import java.io.FileOutputStream;
//import java.lang.String;
import java.util.Vector;

import javax.swing.JOptionPane;

import tablaSimbolos.Propiedades;

/** 
 * La clase <B>Codigo</B> se encarga de manejar el cdigo generado por las instrucciones del lenguaje.
 * <P>Tiene dos atributos:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el cdigo del lenguaje objeto, que es el cdigo de la mquina P. Como la mquina P de momento no existe, este el contenido de este atributo se mostrar por pantalla.</LI></UL>
 * <UL><LI><CODE>fichero:</CODE> Es el fichero donde vamos a guardar las instrucciones que podra ejecutar la mquinaP. Se guarda con extensin '.obj'</LI></UL></P>
 * 
 * @author Paloma de la Fuente, Ines Gonzalez, Federico G. Mon Trotti, Nicolas Mon Trotti y Alberto Velazquez
 * 
 */
public class Codigo {
	
	/*
	 * Atributos de la clase:
	 * 
	 *  El String cod guarda el codigo del lenguaje objeto, que es el codigo de la maquina P.
	 *  En el fichero se guarda tambien el codigo generado, por si se quiere ejecutar en otra ocasin. El fichero se guarda con extension '.obj'
	 */
	Vector<String> cod;
	FileOutputStream fichero;
	
	final int longInicio = 4;
	
	
	/**
	 * El constructor de la clase Codigo no tiene parmetros de entrada ni de salida. Este constructor inicializa el atributo cod con la cadena vaca.
	 * 
	 * @param fich String que guarda la ruta del fichero donde se almacenara el cdigo. Este se almacena en el mismo directorio que se encuentra el cdigo fuente.
	 */
	public Codigo(File fich){		
		cod = new Vector<String>();
		String fcod;
		int i= fich.toString().length();
		fcod = new String(fich.toString().substring( 0,i-3));
		fcod = fcod.concat("obj");
		try{
			fichero = new FileOutputStream(fcod, false);
		}
		catch(java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + fcod +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Accesor para el atributo cod de la clase Cdigo. 
	 * @return Devuelve el codigo generado hasta ese momento
	 */
	public Vector<String> getCod() {
		return cod;
	}

	/**
	 * Este mtodo inicializa el atributo cod con la cadena vaca. No cuenta con parmetros de entrada ni de salida. No devuelve nada.
	 */
	public void inicializaCodigo(){
		cod = new Vector<String>();
	}
	
	/**
	 * Mtodo que modifica el contenido del atributo cod agregndole la instruccin y el nmero de lnea que recibe como parmetro. 
	 * Adems de modificar el fichero donde se almacena el codigo generado parra que lo ejecute la mquinaP. No devuelve nada.
	 * 
	 * @param instr String con la instruccin que ha codificado.
	 * @param num Entero indica el nmero de lnea.
	 * 
	 */
	public void inicio(){
		String inicio = "apila";
		cod.add(inicio);
		inicio = "desapila-dir 1" ;
		cod.add(inicio);
		inicio = "apila";
		cod.add(inicio);
		inicio = "desapila-dir 0";
		cod.add(inicio);
	}
	
	
	/**
	 * Mtodo que modifica el contenido del atributo cod agregndole la instruccin y el nmero de lnea que recibe como parmetro. 
	 * Adems de modificar el fichero donde se almacena el codigo generado parra que lo ejecute la mquinaP. No devuelve nada.
	 * 
	 * @param instr String con la instruccin que ha codificado.
	 * @param num Entero indica el nmero de lnea.
	 * 
	 */
	public void emite(String instr, int num){
		String i =instr + " " + num;
		cod.add(i);
	}
	/**
	 * Mtodo que genera el cdigo de una instruccin
	 * @param instr String con la instruccin
	 * @param num Entero que indica el nmero de lnea
	 * @param dir entero que indica la direccin
	 */
	public void emite(String instr, int num, int dir){
		String i =instr + " " + num + " " + dir;
		cod.add(i);
	}
	
	/**
	 * Mtodo que modifica el contenido del atributo cod agregndole la instruccin que recibe como parmetro y un salto de lnea. 
	 * Adems de modificar el fichero donde se almacena el codigo generado para que lo ejecute la mquinaP. No devuelve nada.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void emite(String instr){
		String i= instr;
		cod.add(i);
		i= i.concat("\n");
	}
	
	/**
	 * Método que genera el código necesario para hacer ir-f o ir-a
	 * @param s String contiene el código ir-f o ir-a
	 */
/*//FIXME: Esto no hay que hacerlo en esta iteración
  	public void emite(String s){
		if (s.startsWith("ir-f")){
			genIns(s);
		}
		if (s.startsWith("ir-a")){
			genIns(s);
		}
	}
*/
	/**
	 * Método que parchea la dirección a sumándole la dirección de b
	 * @param a int a parchear
	 * @param b int necesario para hacer el parcheo
	 */
	public void parchea(int a, int b){
		String i = (String)cod.elementAt(a) + " " + b;
		cod.setElementAt(i,a);
	}
	
	/**
	 * 
	 * @param numniveles
	 * @param tamdatos
	 * @param etq
	 */
	public void parcheaInicio (int numniveles, int tamdatos, int etq){
		int b = 1 + numniveles;
		String i = (String)cod.elementAt(etq) + " " + b;
		cod.setElementAt(i,etq);
		b = b + tamdatos;
		i = (String)cod.elementAt(etq+2) + " " + b;
		cod.setElementAt(i,etq);
	}
	
	
	/**
	 * Mtodo que imprime por pantalla el contenido del atributo cod. No tiene parmetros de entrada ni de salida.
	 * 
	 */
	public void muestraCodigo(){		
		for (int i=0;i<cod.size();i++){
			System.out.println(i+"  "+cod.elementAt(i));
		}
		String ins;
		try {
			for (int i=0;i<cod.size();i++){
				ins = (String)cod.elementAt(i);
				ins = ins+("\n");
				fichero.write(ins.getBytes());
			}
			fichero.close();
		} catch(java.io.IOException e) {
			JOptionPane.showMessageDialog(null,"No he podido cerrar el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String getString(){
		String ins = new String();
		for (int i = 0; i < cod.size(); i++) {
			ins = ins.concat((String)cod.elementAt(i) + "\n");
		}
		return ins;
	}
	
	/**
	 * 
	 * @param p
	 */
	public void accesoVar(Propiedades p){
		cod.add("apila-dir " + (p.getNivel()+1));
		cod.add("apila "+p.getDir());
		cod.add("suma");
		if (p.getClase().equals("var")) {
			cod.add("apila-ind");
		}
	}

	/**
	 * 
	 * @param p
	 * @return
	 */
	public int longAccesoVar(Propiedades p){
		if (p.getClase().equals("var")) {
			return 4;
		}
		return 3;
	}
}
