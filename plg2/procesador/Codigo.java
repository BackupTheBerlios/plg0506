package procesador;

import java.io.File;
import java.io.FileOutputStream;
//import java.lang.String;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * La clase <B>Codigo</B> se encarga de manejar el cdigo generado por las instrucciones del lenguaje.
 * <P>Tiene dos atributos:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el cdigo del lenguaje objeto, que es el cdigo de la mquina P. Como la mquina P de momento no existe, este el contenido de este atributo se mostrar por pantalla.</LI></UL>
 * <UL><LI><CODE>fichero:</CODE> Es el fichero donde vamos a guardar las instrucciones que podra ejecutar la mquinaP. Se guarda con extensin '.obj'</LI></UL></P>
 * 
 * @author Leticia Garcia 
 *
 */
public class Codigo {
	
	/*
	 * Atributos de la clase:
	 * 
	 *  El String cod guarda el codigo del lenguaje objeto, que es el codigo de la maquina P.
	 *  En el fichero se guarda tambien el codigo generado, por si se quiere ejecutar en otra ocasin. El fichero se guarda con extension '.obj'
	 */
	Vector cod;
	FileOutputStream fichero;
	
	/**
	 * El constructor de la clase Codigo no tiene parmetros de entrada ni de salida. Este constructor inicializa el atributo cod con la cadena vaca.
	 * 
	 * @param f String que guarda la ruta del fichero donde se almacenara el cdigo. Este se almacena en el mismo directorio que se encuentra el cdigo fuente.
	 */
	public Codigo(File fich){		
		cod = new Vector();
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
	public Vector getCod() {
		return cod;
	}

	/**
	 * Este mtodo inicializa el atributo cod con la cadena vaca. No cuenta con parmetros de entrada ni de salida. No devuelve nada.
	 */
	public void inicializaCodigo(){
		cod = new Vector();
	}
	
	/**
	 * Mtodo que modifica el contenido del atributo cod agregndole la instruccin y el nmero de lnea que recibe como parmetro. 
	 * Adems de modificar el fichero donde se almacena el codigo generado parra que lo ejecute la mquinaP. No devuelve nada.
	 * 
	 * @param instr String con la instruccin que ha codificado.
	 * @param num Entero indica el nmero de lnea.
	 * 
	 */
	public void genIns(String instr, int num){
		String i =instr + " " + num;
		cod.add(i);
	}
	/**
	 * Mtodo que genera el cdigo de una instruccin
	 * @param instr String con la instruccin
	 * @param num Entero que indica el nmero de lnea
	 * @param dir entero que indica la direccin
	 */
	public void genIns(String instr, int num, int dir){
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
	public void genIns(String instr){
		String i= instr;
		cod.add(i);
		i= i.concat("\n");
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
}