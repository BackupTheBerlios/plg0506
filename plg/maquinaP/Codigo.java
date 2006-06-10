package maquinaP;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.String;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * La clase <B>Codigo</B> se encarga de manejar el cdigo generado por las instrucciones del lenguaje.
 * <P>Tiene dos atributos:
 * <UL><LI><CODE>cod:</CODE> string donde se almacena el cdigo del lenguaje objeto, que es el cdigo de la mquina P. Como la mquina P de momento no existe, este el contenido de este atributo se mostrar por pantalla.</LI></UL>
 * <UL><LI><CODE>fichero:</CODE> Es el fichero donde vamos a guardar las instrucciones que podra ejecutar la mquinaP. Se guarda con extensin '.obj'</LI></UL></P>
 * 
 * @author Jons Andradas, Paloma de la Fuente, Leticia Garca y Silvia Martn
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
		/*i= i.concat("\n");
		try{
			fichero.write(i.getBytes());
		}
		catch(java.io.IOException e){
			JOptionPane.showMessageDialog(null,"No he podido escribir en el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}*/
	}
	
	public void genIns(String instr, int num, int dir){
		String i =instr + " " + num + " " + dir;
		cod.add(i);
		/*i= i.concat("\n");
		try{
			fichero.write(i.getBytes());
		}
		catch(java.io.IOException e){
			JOptionPane.showMessageDialog(null,"No he podido escribir en el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}*/
	}
	
	/**
	 * Mtodo que modifica el contenido del atributo cod agregndole la instruccin que recibe como parmetro y un salto de lnea. 
	 * Adems de modificar el fichero donde se almacena el codigo generado parra que lo ejecute la mquinaP. No devuelve nada.
	 * 
	 * @param instr String con la instruccion que ha codificado.
	 * 
	 */
	public void genIns(String instr){
		String i= instr;
		cod.add(i);
		i= i.concat("\n");
		/*try{
			fichero.write(i.getBytes());
		}
		catch(java.io.IOException e){	
			JOptionPane.showMessageDialog(null,"No he podido escribir en el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}*/
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
		try{
			for (int i=0;i<cod.size();i++){
				ins = (String)cod.elementAt(i);
				ins = ins+("\n");
				fichero.write(ins.getBytes());
			}
			fichero.close();
		}
		catch(java.io.IOException e){
			JOptionPane.showMessageDialog(null,"No he podido cerrar el fichero. "+e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void emite(String s){
		System.out.println("Estoy pasando por emite");
		if (s.startsWith("ir-f")){
			// Metemos s, para permitir los "ir-f NumInst"
			//genIns("ir-f");
			genIns(s);
		}
		if (s.startsWith("ir-a")){
//			 Metemos s, para permitir los "ir-f NumInst"
			//genIns("ir-a");
			genIns(s);
		}
	}
	
	public void parchea(int a, int b){
		String i = (String)cod.elementAt(a) + " " + b;
		System.out.println("Estoy pasando por parche: cambio " + (String)cod.elementAt(a) + " por " + i);
		cod.setElementAt(i,a);
	}
	
	public void apila_ret (int ret) {
		this.genIns("apila-dir",0);
		this.genIns("apila",1);
		this.genIns("apila", ret);
		this.genIns("desapila-ind");
	}
	
	public void prologo (int nivel, int tamlocales){
		this.genIns("apila-dir",0);
		this.genIns("apila", 2);
		this.genIns("suma");
		this.genIns("apila-dir",1 + nivel);
		this.genIns("desapila-ind");
		this.genIns("apila-dir", 0);
		this.genIns("apila", 3);
		this.genIns("suma");
		this.genIns("desapila-dir", 1 + nivel);
		this.genIns("apila-dir", 0);
		this.genIns("apila", tamlocales + 2);
		this.genIns("suma");
		this.genIns("desapila_dir", 0);
	}
	public void paso_parametro (int pformal) {
		// a pforma hay que pasarle la dirección del parámetro formal
		/*apila(pformal.dir) ||
		suma || // dirección comienzo parámetro formal
		desapila-ind*/
		this.genIns("apila", pformal);
		this.genIns("suma");
		this.genIns("desapila_ind");

	}
	
	public void acceso_var (int infoID_nivel, int infoID_dir){
		this.genIns("apila-dir", 1 + infoID_nivel);
		this.genIns("apila", infoID_dir);
		this.genIns("suma");
		this.genIns("apila-ind");
	}
	
	public void inicio (int num_niveles, int tam_datos) {
		this.genIns("apila", 1 + num_niveles);
		this.genIns("desapila-dir", 1);
		this.genIns("apila", 1 + num_niveles + tam_datos);
		this.genIns("desapila-dir", 0);
	}
	public void epilogo (int nivel) {
		this.genIns("apila-dir", 1 + nivel);
		this.genIns("apila", 2);
		this.genIns("resta");
		this.genIns("apila-ind");
		this.genIns("apila-dir", 1 + nivel);
		this.genIns("apila", 3);
		this.genIns("resta");
		this.genIns("copia");
		this.genIns("desapila-dir",0);
		this.genIns("apila",2);
		this.genIns("suma");
		this.genIns("desapila-dir", 1+nivel);
	}	
	public void fin_paso (){
		this.genIns("desapila");
	}
	
	public void inicio_paso (){
		this.genIns("apila-dir", 0);
		this.genIns("apila", 3);
		this.genIns("suma");
	}
}