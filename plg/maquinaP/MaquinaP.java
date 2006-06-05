/**
 * 
 */
package maquinaP;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JOptionPane;

/**
 * La clase <B>MaquinaP</B> implementa la maquina virtual. Para que el lenguaje objeto que hemos creado tenga valor 
 * es necesario que sea ejecutado en una m?quina y que ?sta traduzca los elementos del lenguaje fuente al lenguaje 
 * objeto.
 * <P>La clase MaquinaP cuenta con los siguientes atributos:
 * <UL><LI><CODE>pila:</CODE> La pila de los operandos de la m?quina.</LI>
 * <LI><CODE>PC:</CODE> Contador de programa. Al final de la ejecuci?n nos dice cuantas lienas tiene dicho programa.</LI>
 * <LI><CODE>H:</CODE> Indica si la m?quina esta en ejecuci?n, parada por error, o acabo su ejecuci?n.</LI>
 * <LI><CODE>ST:</CODE> Puntero a la cima de la pila.</LI>
 * <LI><CODE>Prog:</CODE>Memoria de programas. Aqui hab?a puesto el nombre del fichero pero quizas deberia ser el
 * c?digo del programa.</LI>
 * <LI><CODE>Mem:</CODE> Memoria de datos est?tica.</LI>
 * <LI><CODE>heap:</CODE> Memoria de datos din?mica.</LI>
 * <LI><CODE>fichero:</CODE> Fichero donde se encuetra el codigo que va a ejecutar la MaquinaP. Sera un fichero con extension '.obj'</LI>
 * <LI><CODE>pasos:</CODE> String con todos los pasos que ejecuta la MaquinaP.</LI>
 * </UL></P>
 * 
 * @author Jon?s Andradas, Paloma de la Fuente, Leticia Garc?a y Silvia Mart?n
 * @see java.io.FileReader#FileReader(java.lang.String)
 */

public class MaquinaP {

	/*
	 * Atributos de la clase:
	 * 
	 * pila: La pila de los operandos de la m?quina.
	 * PC: Contador de programa. Al final de la ejecuci?n nos dice cuantas lienas tiene dicho programa.
	 * H: Indica si la m?quina esta en ejecuci?n, parada por error, o acabo su ejecuci?n.
	 * ST: Puntero a la cima de la pila.
	 * Prog:Memoria de programas. Aqui hab?a puesto el nombre del fichero pero quizas deberia ser el
	 * c?digo del programa.
	 * Mem: Memoria de datos estatica.
	 * heap:Memoria de datos din?mica.
	 * fichero: Fichero donde se encuetra el codigo que va a ejecutar la MaquinaP.
	 * pasos: String con todos los pasos que ejecuta la MaquinaP.
	 */
	private Stack pila;
	private int PC;
	private int H;
	private int ST;
	private Vector Prog;
	private Vector Mem;
	private Heap heap;
	private FileReader fichero;
	private String pasos;
	private static int longApilaRet = 5;
	private static int longPrologo = 13;
	private static int longEpilogo = 12;
	private static int longInicioPaso = 3;
	private static int longFinPaso = 1;
	private static int longInicio = 4;
	//private static int inicio_paso = funcionAux1();  
	//private static int fin_paso = funcionAux2();
	
	/**
	 * El constructor de la clase MaquinaP que s?lo tiene el buffer de lectura del fichero como parmetro de entrada.
	 * @param f Recibe como par?metro la ruta del fichero a ejecutar para poder inicializar todo.
	 *
	 */	
	public MaquinaP(String f) {
		super();
		// TODO Auto-generated constructor stub
		pila = new Stack();
		PC = 0;
		H = 0;
		ST = -1;
		Mem= new Vector();
		// el 1? entero indica el tam?o inicial, y el otro la capacidad de aumento
		heap= new Heap(50);
		int i= f.length();
		String fcod = new String(f.substring( 0,i-3));
		fcod = fcod.concat("obj");
		File fich= new File(fcod);
		try{
			fichero = new FileReader(fich);
		}
		catch(java.io.FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + fcod,"Error",JOptionPane.ERROR_MESSAGE);
		}
		Prog = damePrograma(fichero);
		pasos="";
	}
	/*public void funcionAux1 (){
		this.apila_dir(0);
		this.apila(3);
		this.suma();
	}
	public void funcionAux2 (){
		pila.pop();
	}*/
	
	/**
	 * Accesor para el atributo de la clase, pasos. 
	 * @return String con los pasos ejecutados y estados de la pila.
	 */
	public String getPasos() {
		return pasos;
	}

	/**
	 * Accesor para el atributo de la clase, H. Que indica el estado de la pila. 
	 * @return String con los pasos ejecutados y estados de la pila.
	 */
	public int getH() {
		return H;
	}
	
	/**
	 * Mutador para el atributo de la clase, H. 
	 * @param h Entero que controla el estado actual de la pila, adem?s es donde se refleja el error.
	 */
	public void setH(int h) {
		H = h;
	}
	
	/**
	 * Accesor para el atributo de la clase, Mem. Que indica el estado de la memoria del Programa. 
	 * @return Vector donde cada celda es una posici?n de Memoria.
	 */
	public Vector getMem() {
		return Mem;
	}
	
	/**
	 * Mutador para el atributo de la clase, Mem. Que indica el estado de la memoria del Programa. 
	 * @param mem Se recibe un vector a modo de memoria de Progrma.
	 */
	public void setMem(Vector mem) {
		Mem = mem;
	}
	
	/**
	 * Accesor para el atributo de la clase, PC. Que indica el contador de instrucciones del Programa. 
	 * @return Entero que indica el numero de instrucci?n.
	 */
	public int getPC() {
		return PC;
	}
	
	/**
	 * Mutador para el atributo de la clase, PC. Que indica el contador de instrucciones del Programa. 
	 * @param pc Entero para actualizar la nueva posci?n del programa.
	 */
	public void setPC(int pc) {
		PC = pc;
	}
	
	/**
	 * Accesor para el atributo de la clase, pila. Pila del programa. 
	 * @return Devuelve la pila del programa.
	 */
	public Stack getPila() {
		return pila;
	}
	
	/**
	 * Mutador para el atributo de la clase, pila. Se actualizara la pila del programa.
	 * @param pila una nueva pila con valores y operaciones.
	 */
	public void setPila(Stack pila) {
		this.pila = pila;
	}
	
	/**
	 * Accesor para el atributo de la clase, Prog. Vector con las intrucciones del programa que se ha de ejecutar.
	 * @return Vector con el contenido del programa.
	 */
	public Vector getProg() {
		return Prog;
	}
	
	/**
	 * Mutador para el atributo de la clase, Prog. Se actualizaran el vector con las instrucciones del Programa.
	 * @param prog Se recibe el vector con el nuevo programa.
	 */
	public void setProg(Vector prog) {
		Prog = prog;
	}
	
	/**
	 * Accesor para el atributo la clase, ST. Se debuelve el valor que apunta a la cima de la pila.
	 * @return Entero que apunta a la cima de la pila de la maquina.
	 */
	public int getST() {
		return ST;
	}
	
	/**
	 * Mutador para el atributo de la clase ST. Se cambia el puntero a la cima de la pila, con lo que cambiara la cima de la pila.
	 * @param st Recibe por par?metro el entero con el que cambiar la cima de la pila.
	 */
	public void setST(int st) {
		ST = st;
	}
	
	/**
	 * @return Returns the fichero.
	 */
	public FileReader getFichero() {
		return fichero;
	}

	/**
	 * @param fichero The fichero to set.
	 */
	public void setFichero(FileReader fichero) {
		this.fichero = fichero;
	}

	/**
	 * @return Returns the heap.
	 */
	public Heap getHeap() {
		return heap;
	}

	/**
	 * @param heap The heap to set.
	 */
	public void setHeap(Heap heap) {
		this.heap = heap;
	}

	/**
	 * Obtiene el programa del fichero que recibe por par?metro. Guarda cada instruccion del programa en una posici?n del 
	 * vector Prog. Para obtener el programa crea un BufferReader y se lanzan y capturan excepciones al respecto. 
	 * 
	 * @param f Recibe por par?metro el fichero del cual obtiene el programa. Ha de ser un FileReader para luego trabajar con el.
	 * @return Devuelve el Vector con el programa. Cada posici?n es una instruccion de la maquina P.
	 * @exception java.io.FileNotFoundException Se lanza y se captura en este mismo m?todo.
	 * @exception java.io.IOException Se lanza y se captura en este mismo m?todo.
	 */
	private Vector damePrograma(FileReader f){
		Vector v=new Vector();
		BufferedReader entrada = null;
	    try {
	      entrada = new BufferedReader(f);
	      String linea = null;
	      while ((linea = entrada.readLine()) != null){
	    	  v.add(linea.trim());
	      }
	    }
	    catch (FileNotFoundException ex) {
		      JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		catch (IOException ex){
			JOptionPane.showMessageDialog(null,"Archivo no encontrado: " + ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
		return v;
	}
	
	/**
	 * Aumenta el tama?o del vector memoria seg?n las necesidades del programa que va a ejecutar.
	 * 
	 * @param tam Recibe un entero con el tama?o que ha de aumentar.
	 */
	private void aumentoMem(int tam){
		for(int i = Mem.size();i<tam+1;i++){
			Mem.add(i,null);
		}
	}
	
	/**
	 * M?todo que ejecuta la M?quina P. Va leyendo las intrucciones que ha generado el compilador y las ejecuta.
	 *
	 */
	public void ejecuta() throws Exception{
		String i;
		String[] linea;
		int j= 0;
		pasos=pasos.concat("Comenzamos con la ejecucion de la pila. \n\n");
		System.out.println("\n\n\nComenzamos con la ejecucion de la pila. \n\n\n");
		System.out.println(Prog.size());
		while (H==0){
			//System.out.println("Holas");
			//System.out.println("pc" + PC);
			//System.out.println("size" + Prog.size());
			if(PC<Prog.size()){
				System.out.println(muestraPila());
				//System.out.println("Hola 1");
				i= (String)Prog.get(PC);
				//System.out.println("Hola 2");
				linea = i.split(" ");
				//System.out.println("Hola 3");
				if (linea[0].compareTo("apila")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					apila((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("desapila-dir")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					desapila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("apila-dir")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					apila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("suma")==0){
					System.out.print(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					suma();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("resta")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					resta();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("multiplica")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					multiplica();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("divide")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					divide();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("and")==0){
					System.out.println(linea[0]+ "  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					and();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("or")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					or();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("not")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					not();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("neg")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					neg();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("menor")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					menor();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("menor_o_igual")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					menorIgual();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("mayor")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					mayor();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("mayor_o_igual")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					mayorIgual();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("igual")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					igual();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("distinto")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					distinto();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("ir-a")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					ir_a((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("ir-f")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					ir_f((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("delete")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					delete((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("new")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					new_o((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("apila-ind")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]);
					pasos= pasos.concat(" \n");
					apila_ind();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("desapila-ind")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]);
					pasos= pasos.concat(" \n");
					desapila_ind();
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("mueve")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					mueve((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}////////////////////////////////////////////
				else if (linea[0].compareTo("prologo")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1])+" "+Integer.parseInt(linea[2]));
					pasos= pasos.concat(" \n");
					prologo((new Integer(Integer.parseInt(linea[1]))).intValue(), (new Integer(Integer.parseInt(linea[2]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("inicio")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1])+" "+Integer.parseInt(linea[2]));
					pasos= pasos.concat(" \n");
					inicio((new Integer(Integer.parseInt(linea[1]))).intValue(), (new Integer(Integer.parseInt(linea[2]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("paso_parametro")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1])+" "+Integer.parseInt(linea[2]));
					pasos= pasos.concat(" \n");
					paso_parametro((new Integer(Integer.parseInt(linea[1]))).intValue(), (new Integer(Integer.parseInt(linea[2]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("acceso_var")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1])+" "+Integer.parseInt(linea[2]));
					pasos= pasos.concat(" \n");
					acceso_var((new Integer(Integer.parseInt(linea[1]))).intValue(), (new Integer(Integer.parseInt(linea[2]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else if (linea[0].compareTo("apila_ret")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					apila_ret((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}else if (linea[0].compareTo("epilogo")==0){
					System.out.println(linea[0]);
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					epilogo((new Integer(Integer.parseInt(linea[1]))).intValue());
					if (!pila.empty()){
						pasos= pasos.concat("La cima de la pila cambio, ahora es: "+ pila.peek());
						pasos= pasos.concat(" \n");
					}
					else{
						pasos= pasos.concat("La pila ahora esta vacia");
						pasos= pasos.concat(" \n");
					}
					j++;
				}
				else{
					error();
					j++;
				}
			}
			else{
				//System.out.println("Adioses");
				eof();
			}
		}
	}
	
	/**
	 * M?todo que devuelve un String con el contenido de la Memoria. 
	 * Se usa para ver el contenido final de la memoria para despues de ejecutar la m?quina P.
	 * 
	 * @return String con el contenido del vector Mamoria.
	 */
	public String resultadoMem(){
		String s="Memoria estatica:"+"\n";
		for (int i=0;i<Mem.size();i++){
			System.out.println(Mem.elementAt(i));
			if(Mem.elementAt(i)!=null){
				s= s.concat("posicion "+i+":  "+((Integer)Mem.elementAt(i)).toString());
				s=s.concat(" \n");
			}
			else{
				s=s.concat("posicion "+i+": "+" null");
				s=s.concat(" \n");
			}
		}
		s = s.concat("Memoria dinamica:"+"\n");
		for (int i=0;i<heap.getHeap().size();i++){
			System.out.println(heap.getHeap().elementAt(i));
			if(heap.getHeap().elementAt(i)!=null){
				s= s.concat("posicion "+i+":  "+((Integer)heap.getHeap().elementAt(i)).toString());
				s=s.concat(" \n");
			}
			else{
				s=s.concat("posicion "+i+": "+" null");
				s=s.concat(" \n");
			}
		}
		return s;
	}
	
	/**
	 * M?todo que realiza una operaci?n suma. Se desapilan los dos primeros elementos de la pila y se suman.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habr? un elemento menos. Tambi?n se aumenta en uno 
	 * el contador del programa.
	 * 
	 * (R1) suma:
	 * Pila[ST-1] <-- Pila[ST-1] + Pila[ST]
	 * ST <-- ST -1 
	 * PC <-- PC + 1
	 */

	public void suma() throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Suma. La pila no contiene los datos necesarios.");
		}
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s2.intValue()+s1.intValue());
		pila.push(s);
		System.out.println("La cima de la pila es: "+ pila.peek());
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R2) resta:
	 *	Pila[ST-1] <-- Pila[ST-1] - Pila[ST]
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void resta()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Resta. La pila no contiene los datos necesarios.");
		}
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s2.intValue()-s1.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R3) multiplica:
	 *	Pila[ST-1] <-- Pila[ST-1] * Pila[ST]  
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void multiplica()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Multiplica. La pila no contiene los datos necesarios.");
		}
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s2.intValue()*s1.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R4) divide:
	 *	Pila[ST-1] <-- Pila[ST-1] / Pila[ST]  
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void divide()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Divide. La pila no contiene los datos necesarios.");
		}
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s2.intValue()/s1.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * ST <-- ST + 1 
	 * Pila[ST] <-- n  Si utilizo una pila no puedo hacer eso exactamente...
	 * PC <-- PC + 1
	 * 
	 * @param n
	 */
	public void apila (int n){
		ST=ST+1;
		pila.push(new Integer (n));
		PC=PC+1;
	}
	
	/**
	 * (R6) apila-dir(d):
	 *	ST <-- ST + 1 
	 *	Pila[ST] <-- Mem[d]  
	 *	PC <-- PC + 1
	 *
	 * @param d
	 */
	public void apila_dir (int d) throws Exception{
		ST = ST + 1; 
		if (d >= 0){
			if ((Mem.size()>=d)&&(Mem.elementAt(d)!=null)){ // Donde pone >=, pon?a <=
				pila.push(Mem.elementAt(d));  
				PC = PC + 1;
			}
			else{				
				throw new Exception("ERROR: Variable sin inicializar.");
			}
		}
		else{
			throw new Exception("ERROR: Puntero sin inicializar.");
		}
	}
	
	/**
	 * (R7) desapila-dir(d):
	 *	Mem[d] <-- Pila[ST]
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 *
	 * @param d
	 */
	public void desapila_dir(int d) throws Exception{
		//Primero comprobamos que la memoria sea suficiente.
		//Sino lo es aumentamos el tama?o del vector.
		if (ST<0){
			throw new Exception("ERROR: Desapila_dir. La pila no contiene los datos necesarios.");
		}
		if (d >= 0){
			if (d>=Mem.size()){
				aumentoMem(d);
				Mem.set(d,pila.pop());
			}
			else{
				Mem.set(d,pila.pop());
			}
			ST = ST -1;
			PC = PC + 1;
		}
		else{
			throw new Exception("ERROR: Puntero sin inicializar.");
		}
	}
	
	/**
	 * (R8) EOF:
	 *	H <-- 1
	 */
	public void eof(){
		H=1;
	}
	
	/**
	 * (R9) En cualquier otro caso, la m?quina entra en estado de error y se detiene la ejecuci?n.
	 * H <-- -1
	 */
	public void error(){
		H = -1;
	}
	
	/**
	 * (R13) And:
	 *	Pila[ST - 1] <-- "true" si Pila[ST ? 1] ? "false" & Pila[ST] ? "false"
	 *		"false" en cualquier otro caso
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1 
	 */
	public void and() throws Exception{
		if (ST<1){
			throw new Exception("ERROR: And. La pila no contiene los datos necesarios.");
		}
		int a1 = ((Integer)pila.pop()).intValue();
		int a2 =((Integer)pila.pop()).intValue();
		if ((a1!=0)&&(a2!=0)){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R14) Or:
	 *	Pila[ST - 1] <-- "false" si Pila[ST ? 1] ? "false" & Pila[ST] ? "false"
	 *		"true" en c.o.c.
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1 
	 */
	public void or()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Or. La pila no contiene los datos necesarios.");
		}
		int o1= ((Integer)pila.pop()).intValue();
		int o2=((Integer)pila.pop()).intValue();
		if ((o1==0)&&(o2==0)){
			pila.push(new Integer(0));
		}
		else{
			pila.push(new Integer(1));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R15) Not:
	 *	Pila[ST] <-- "true" si Pila[ST] = "false"
	 *				"false" en c.o.c
	 *	PC <-- PC + 1 
	 */
	public void not()throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Not. La pila no contiene los datos necesarios.");
		}
		int n=((Integer)pila.pop()).intValue();
		if (n==0){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		PC = PC + 1;
	}
	
	/**
	 * (R16) Neg:
	 *	Pila[ST] <--  - Pila[ST]
	 *	PC <-- PC + 1 
	 */
	public void neg()throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Neg. La pila no contiene los datos necesarios.");
		}
		Integer n= (Integer)pila.pop();
		pila.push(new Integer(-(n.intValue())));
		PC = PC + 1;
	}
	
	/**
	 * (R17) Menor:
	 *	Pila[ST ? 1] <-- "true" si Pila[ST - 1] < Pila[ST]
	 *					"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void menor()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Menor. La pila no contiene los datos necesarios.");
		}
		int c1= ((Integer)pila.pop()).intValue();
		int c2 = ((Integer)pila.pop()).intValue();
		if (c2<c1){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R18) MenorIgual:
	 *	Pila[ST ? 1] <-- "true" si Pila[ST ? 1] ? Pila[ST]
	 *			"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void menorIgual()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Menor o igual. Memoria sin inicializar.");
		}
		int c1= ((Integer)pila.pop()).intValue();
		int c2=((Integer)pila.pop()).intValue(); 
		if (c2<=c1){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R19) Mayor:
	 *	Pila[ST ? 1] <-- "true" si Pila[ST ? 1] > Pila[ST]
	 *			"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void mayor()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Mayor. La pila no contiene los datos necesarios.");
		}
		int c1= ((Integer)pila.pop()).intValue();
		int c2 = ((Integer)pila.pop()).intValue(); 
		if (c2>c1){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R20) MayorIgual:
	 *	Pila[ST ? 1] <-- "true" si Pila[ST ? 1] ? Pila[ST]
	 *			"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void mayorIgual()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Mayor o igual. La pila no contiene los datos necesarios.");
		}
		int c1= ((Integer)pila.pop()).intValue();
		int c2=((Integer)pila.pop()).intValue(); 
		if (c2>=c1){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R21) Igual:
	 *	Pila[ST ? 1] <-- "true" si Pila[ST ? 1] = Pila[ST]
	 *			"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void igual()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Igual. La pila no contiene los datos necesarios.");
		}
		int c1= ((Integer)pila.pop()).intValue();
		int c2= ((Integer)pila.pop()).intValue();
		if (c1==c2){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R22) Distinto:
	 *	Pila[ST ? 1] <-- "true" si Pila[ST ? 1] ? Pila[ST]
	 *			"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void distinto()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Distinto. La pila no contiene los datos necesarios.");
		}
		int c1= ((Integer)pila.pop()).intValue();
		int c2 =((Integer)pila.pop()).intValue();
		if (c1!=c2){
			pila.push(new Integer(1));
		}
		else{
			pila.push(new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R20) ir-a(s):
	 * PC <-- s
	 */
	public void ir_a(int s){
		if (s<Prog.size()){
			PC = s;
		}
		else{
			PC =s;
			eof(); 
		}
	}
	
	/**
	 * (R21) ir-f(s):
	 *	si Pila[ST] = 0 PC <-- s
	 *	sino PC <--PC+1 fsi
	 *	ST <-- ST - 1	
	 */
	public void ir_f(int s)throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Salto sin definir.");
		}
		if (((Integer)pila.pop()).intValue()==0){
			System.out.println("Paso bien");
			PC = s;
		}
		else{
			PC = PC + 1;
		}
		ST = ST -1;
	}
	
	/**
	 * (R22) libera(d)
	 *  Mem[d] <-- null
	 *  PC <-- PC +1
	 * del(t): Desapila una direcci?n de comienzo d de la cima de la pila, 
	 * y libera en el heap t celdas consecutivas a partir de d.
	 */
	public void delete (int t) throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Memoria sin inicializar.");
		}
		int d= ((Integer)pila.pop()).intValue();
		ST= ST-1;
		heap.libera(d,t);
		PC++;
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	public void new_o(int i) throws Exception{
		int j=heap.reserva(i);
		ST=ST+1;
		pila.push(new Integer (j));
		System.out.println(pila.peek());
		PC++;
	}
	
	/**
	 * Para acceder a las partes de las estructuras es necesario dotar a la m?quina de capacidad de direccionamiento indirecto. Esto implica las siguientes operaciones:

		? apila-ind: Interpreta el valor d en la cima de la pila como un n?mero de celda en la memoria, y substituye dicho valor por el almacenado en dicha celda.
			Pila[ST] ? Mem[Pila[ST]]	
			PC ? PC+1
		 @throws Exception
	 */
	public void apila_ind() throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Memoria sin inicializar.");
		}
		int i = ((Integer)pila.pop()).intValue();
		if ((i>=Mem.size())|| (i<0)){
			throw new Exception("Posicion de memoria no inicializada");
		}
		pila.push(Mem.elementAt(i));
		PC = PC +1;
	}
	
	/**
	 * ? despila-ind: Desapila el valor de la cima v y la subcima d, interpreta d como un n?mero de celda en la memoria, y almacena v en dicha celda.
			Mem[Pila[ST]] ? Pila[ST-1]
			ST?ST-2
			PC ? PC+1
	 *  @throws Exception
	 */
	public void desapila_ind() throws Exception{
		//	Primero comprobamos que la memoria sea suficiente.
		//Sino lo es aumentamos el tama?o del vector.
		if (ST<1){
			throw new Exception("ERROR: Desapila_ind. La pila no contiene los datos necesarios.");
		}
		Integer valor=(Integer)pila.pop();
		int d = ((Integer)pila.pop()).intValue();
		if (d >= 0){
			if (d>=Mem.size()){
				aumentoMem(d);
				Mem.set(d,valor);
			}
			else{
				Mem.set(d,valor);
			}
			ST = ST-2;
			PC = PC + 1;
		}
		else{
			throw new Exception("ERROR: Memoria sin inicializar.");
		}
	}
	
	/**
	 * 
	 * mueve(s). Dicha instrucci?n encuentra en la cima la direcci?n origen o y en la subcima la direcci?n destino d, y realiza el movimiento de s celdas desde o a s.
		para i?0 hasta s-1 hacer
		Mem[Pila[ST-1]+i] ? Mem[Pila[ST]+i]
		ST?ST-2
		PC ? PC+1
	 * 
	 * @param s
	 * @throws Exception
	 */
	public void mueve (int s) throws Exception{
		if (pila.size()>2){ 
			int o = ((Integer)pila.pop()).intValue(); //pop desapilar
			int d = ((Integer)pila.pop()).intValue(); //push apilar
			for (int i=0;i<s;i++){
				if(d+i<Mem.size()-1){ //Mem set cambia el elemento de la posición d+i por el elemento que le pasas. en este caso el elemento que devuelve mem.get(o+i)
					Mem.set(d+i,Mem.get(o+i));
				}
				else{
					aumentoMem(d+i);
					Mem.set(d+i,Mem.get(o+i));
				}
			}
			else{
				aumentoMem(d+i);
				Mem.set(d+i,Mem.get(o+i));
			}
		}
		ST = ST-2;
		PC = PC +1;
	}
	
	public void apila_ret (int ret) throws Exception{
		//devuelve apila-dir(0) || apila(1) || suma || apila(ret) || desapila-ind	
		if (ret >= 0){
			this.apila_dir(0);
			this.apila(1);
			this.apila(ret);
			this.desapila_ind();
		}
		else{
			throw new Exception("ERROR: apila ret.");
		}

	}
	public void prologo (int nivel, int tamlocales) throws Exception{
		if (nivel >= 0){
			this.apila_dir(0);
			this.apila(2);
			this.suma();
			this.apila_dir(1 + nivel);
			this.desapila_ind();
			this.apila_dir(0);
			this.apila(3);
			this.suma();
			this.desapila_dir(1+nivel);
			this.apila_dir(0);
			this.apila(tamlocales + 2);
			this.suma();
			this.desapila_dir(0);
		}
		else{
			throw new Exception("ERROR: en prólogo.");
		}

	}
	public void paso_parametro (int modoReal, int pformal) throws Exception{
		//cuando useis esta función le pasáis directamente la dirección del parámetro con el que la llaméis
		if (pformal >= 0){
			this.apila(pformal);
			this.suma();
			this.desapila_ind();
		}
		else{
			throw new Exception("ERROR: en paso parámetro.");
		}

	}
	public void acceso_var (int infoID_nivel, int infoID_dir) throws Exception{
		//infoID recibe el nivel del identificador y su dirección
		if (infoID_nivel >= 0){
			this.apila_dir(1 + infoID_nivel);
			this.apila(infoID_dir);
			this.suma();
		}
		else{
			throw new Exception("ERROR: en acceso var.");
		}
	}
	
	public void inicio (int num_niveles, int tam_datos) throws Exception{
		
		if (num_niveles >= 0){
			this.apila(1 + num_niveles);
			this.desapila_dir(1);
			this.apila(1 + num_niveles + tam_datos);
			this.desapila_dir(0);
		}
		else{
			throw new Exception("ERROR: en acceso var.");
		}

	}
	public void epilogo (int nivel) throws Exception{
		if (nivel >= 0){
			this.apila_dir(1 + nivel);
			this.apila(2);
			this.resta();
			this.apila_ind();
			this.apila_dir(1 + nivel);
			this.apila(3);
			this.resta();
			pila.push(pila.peek());
			this.desapila_dir(0);
			this.apila(2);
			this.suma();
			this.desapila_dir(1+nivel);
		}
		else{
			throw new Exception("ERROR: en epílogo.");
		}

	}
	public String muestraPila(){
		Stack aux = new Stack();
		String pilas="La puta pila es: \n";
		while(!pila.isEmpty()){
			Integer n = (Integer)pila.pop();
			pilas= pilas.concat(n.toString());
			pilas = pilas.concat("\n");
			aux.push(n);
		}
		while(!aux.isEmpty()){
			Integer n = (Integer)aux.pop();
			pila.push(n);
		}
		return pilas;
	}
}