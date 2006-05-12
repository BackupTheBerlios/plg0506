package maquinaP;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.String;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * La clase <B>Codigo</B> se encarga de manejar el c�digo generado por las instrucciones del lenguaje.
 * <P>Tiene dos atributos:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el c�digo del lenguaje objeto, que es el c�digo de la m�quina P. Como la m�quina P de momento no existe, este el contenido de este atributo se mostrar� por pantalla.</LI></UL>
 * <UL><LI><CODE>fichero:</CODE> Es el fichero donde vamos a guardar las instrucciones que podra ejecutar la m�quinaP. Se guarda con extensi�n '.obj'</LI></UL></P>
 * 
 * @author Jon�s Andradas, Paloma de la Fuente, Leticia Garc�a y Silvia Mart�n
 *
 */
public class Codigo {
	
	/*
	 * Atributos de la clase:
	 * 
	 *  El String cod guarda el codigo del lenguaje objeto, que es el codigo de la maquina P.
	 *  En el fichero se guarda tambien el codigo generado, por si se quiere ejecutar en otra ocasi�n. El fichero se guarda con extension '.obj'
	 */
	Vector cod;
	FileOutputStream fichero;
	
	/**
	 * El constructor de la clase Codigo no tiene par�metros de entrada ni de salida. Este constructor inicializa el atributo cod con la cadena vac�a.
	 * 
	 * @param f String que guarda la ruta del fichero donde se almacenara el c�digo. Este se almacena en el mismo directorio que se encuentra el c�digo fuente.
	 */
	public Codigo(String f){		
		cod = new Vector();
		String fcod;
		int i= f.length();
		fcod = new String(f.substring( 0,i-3));
		fcod = fcod.concat("obj");
		File fich= new File(fcod);
		try{
			fichero = new FileOutputStream(fich, false);
		}
		catch(java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + fcod +e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	/**
	 * Accesor para el atributo cod de la clase C�digo. 
	 * @return Devuelve el codigo generado hasta ese momento
	 */
	public Vector getCod() {
		return cod;
	}

	/**
	 * Este m�todo inicializa el atributo cod con la cadena vac�a. No cuenta con par�metros de entrada ni de salida. No devuelve nada.
	 */
	public void inicializaCodigo(){
		cod = new Vector();
	}
	
	/**
	 * M�todo que modifica el contenido del atributo cod agreg�ndole la instrucci�n y el n�mero de l�nea que recibe como par�metro. 
	 * Adem�s de modificar el fichero donde se almacena el codigo generado parra que lo ejecute la m�quinaP. No devuelve nada.
	 * 
	 * @param instr String con la instrucci�n que ha codificado.
	 * @param num Entero indica el n�mero de l�nea.
	 * 
	 */
	public void genIns(String instr, int num){
		String i =instr + " " + num;
		cod.add(i);
		i= i.concat("\n");
		try{
			fichero.write(i.getBytes());
		}
		catch(java.io.IOException e){
			JOptionPane.showMessageDialog(null,"No he podido escribir en el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * M�todo que modifica el contenido del atributo cod agreg�ndole la instrucci�n que recibe como par�metro y un salto de l�nea. 
	 * Adem�s de modificar el fichero donde se almacena el codigo generado parra que lo ejecute la m�quinaP. No devuelve nada.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void genIns(String instr){
		String i= instr;
		cod.add(i);
		i= i.concat("\n");
		try{
			fichero.write(i.getBytes());
		}
		catch(java.io.IOException e){	
			JOptionPane.showMessageDialog(null,"No he podido escribir en el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * M�todo que imprime por pantalla el contenido del atributo cod. No tiene par�metros de entrada ni de salida.
	 * 
	 */
	public void muestraCodigo(){		
		for (int i=0;i<cod.size();i++){
			System.out.println(cod.elementAt(i));
		}
		try{
			fichero.close();
		}
		catch(java.io.IOException e){
			JOptionPane.showMessageDialog(null,"No he podido cerrar el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
}