package maquinaP;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.String;
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
	}
	/**
	 * M?todo que genera el c?digo de una instrucci?n
	 * @param instr String con la instrucci?n
	 * @param num Entero que indica el n?mero de l?nea
	 * @param dir entero que indica la direcci?n
	 */
	public void genIns(String instr, int num, int dir){
		String i =instr + " " + num + " " + dir;
		cod.add(i);
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
	/**
	 * M?todo que genera el c?digo necesario para hacer ir-f o ir-a
	 * @param s String contiene el c?digo ir-f o ir-a
	 */
	public void emite(String s){
		if (s.startsWith("ir-f")){
			genIns(s);
		}
		if (s.startsWith("ir-a")){
			genIns(s);
		}
	}
	/**
	 * M?todo que parchea la direcci?n a sum?ndole la direcci?n de b
	 * @param a int a parchear
	 * @param b int necesario para hacer el parcheo
	 */
	public void parchea(int a, int b){
		String i = (String)cod.elementAt(a) + " " + b;
		cod.setElementAt(i,a);
	}
	
	/**
	 * m?todo que genera el c?digo que salva la direcci?n de retorno de la prellamada asociada a la invocaci?n
	 * @param ret entero con la direcci?n 
	 */
	public void apila_ret (int ret) {
		this.genIns("apila-dir",0);
		this.genIns("apila",1);
		this.genIns("apila", ret);
		this.genIns("desapila-ind");
	}
	/**
	 * m?todo que que genera el c?digo de lo siguiente:
	 * - Salva el valor anterior del display asociado con el procedimiento.
	 * ? Fija el valor del display para la activaci?n actual.
	 * ? Reserva de espacio para los datos locales
	 * @param nivel entero con el nivel de anidamiento del pr?logo
	 * @param tamlocales entero que contiene el tama?o de las variables locales al procedimiento
	 */
	public void prologo (int tamlocales){
		/*devuelve apila( t a m l o c a l e s ) ||
          desapila-dir(0)                 // establecido nuevo cp*/
		this.genIns("apila", tamlocales);
		this.genIns("desapila_dir", 0);
		/*this.genIns("apila-dir",0);
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
		this.genIns("desapila_dir", 0);*/
	}
	
	/**
	 * m?todo que genera el c?digo para poder realizar el paso del par?metro formal
	 *
	 *	 * @param pformal entero con el par?metro formal
	 */
	public void paso_parametro (int pformal) {
		this.genIns("apila", pformal);
		//this.genIns("suma");
		this.genIns("desapila-ind");

	}
	/**
	 * m?todo que genera el c?digo para poder acceder a la informaci?n de la variable
	 * @param infoID_nivel entero con el nivel de la variable
	 * @param infoID_dir entero con la direcci?n de la variable
	 */
	public void acceso_var (int infoID_nivel, int infoID_dir){
		this.genIns("apila-dir", 1 + infoID_nivel);
		this.genIns("apila", infoID_dir);
		this.genIns("suma");
		this.genIns("apila-ind");
	}
	/**
	 * m?todo que genera el c?digo para hacer el inicio relativo a los procedimientos
	 * @param num_niveles entero con el n?mero de niveles de anidamiento del procedimiento
	 * @param tam_datos entero con el tama?o de los par?metros que recibe el procedimiento
	 */
	public void inicio () {
		this.genIns("apila");
		this.genIns("desapila-dir", 0);
	}
	/**
	 * m?todo que genera el c?digo relativo al ep?logo
	 * @param nivel entero con el nivel de anidamiento del par?metro
	 */
	public void epilogo (int tamlocales) {
		/*fun epilogo(tamlocales)
 devuelve apila-dir(0)                     ||
          apila( t a m l o c a l e s + 2 ) ||
          resta                            ||
          copia                            ||
          desapila-dir(0)                  || // reestablecido cp
          apila(1)                         ||
          suma                             ||
          apila-ind                           // apilada direccin retorno
ffun
cons longEpilogo = 8*/
		this.genIns("apila-dir", 0);
		this.genIns("apila", tamlocales + 2);
		this.genIns("resta");
		this.genIns("copia");
		this.genIns("desapila-dir",0);
		this.genIns("apila",1);
		this.genIns("suma");
		this.genIns("desapila-ind");
		/*this.genIns("apila-dir", 1 + nivel);
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
		this.genIns("desapila-dir", 1+nivel);*/
	}	
	/**
	 * m?todo que implementa las posiciones de destino en el registro de activaci?n
	 *
	 */
	public void fin_paso (){
		this.genIns("desapila");
	}
	/**
	 * m?todo que implementa las posiciones de destino en el registro de activaci?n
	 *
	 */
	public void inicio_paso (){
		this.genIns("apila-dir", 0);
		this.genIns("apila", 3);
		this.genIns("suma");
	}
}