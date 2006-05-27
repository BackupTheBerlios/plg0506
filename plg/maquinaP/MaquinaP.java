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
 * es necesario que sea ejecutado en una máquina y que ésta traduzca los elementos del lenguaje fuente al lenguaje 
 * objeto.
 * <P>La clase MaquinaP cuenta con los siguientes atributos:
 * <UL><LI><CODE>pila:</CODE> La pila de los operandos de la máquina.</LI>
 * <LI><CODE>PC:</CODE> Contador de programa. Al final de la ejecución nos dice cuantas lienas tiene dicho programa.</LI>
 * <LI><CODE>H:</CODE> Indica si la máquina esta en ejecución, parada por error, o acabo su ejecución.</LI>
 * <LI><CODE>ST:</CODE> Puntero a la cima de la pila.</LI>
 * <LI><CODE>Prog:</CODE>Memoria de programas. Aqui había puesto el nombre del fichero pero quizas deberia ser el
 * código del programa.</LI>
 * <LI><CODE>Mem:</CODE> Memoria de datos.</LI>
 * <LI><CODE>fichero:</CODE> Fichero donde se encuetra el codigo que va a ejecutar la MaquinaP. Sera un fichero con extension '.obj'</LI>
 * <LI><CODE>pasos:</CODE> String con todos los pasos que ejecuta la MaquinaP.</LI>
 * </UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
 * @see java.io.FileReader#FileReader(java.lang.String)
 */
public class MaquinaP {

	/*
	 * Atributos de la clase:
	 * 
	 * pila: La pila de los operandos de la máquina.
	 * PC: Contador de programa. Al final de la ejecución nos dice cuantas lienas tiene dicho programa.
	 * H: Indica si la máquina esta en ejecución, parada por error, o acabo su ejecución.
	 * ST: Puntero a la cima de la pila.
	 * Prog:Memoria de programas. Aqui había puesto el nombre del fichero pero quizas deberia ser el
	 * código del programa.
	 * Mem: Memoria de datos.
	 * fichero: Fichero donde se encuetra el codigo que va a ejecutar la MaquinaP.
	 * pasos: String con todos los pasos que ejecuta la MaquinaP.
	 */
	private Stack pila;
	private int PC;
	private int H;
	private int ST;
	private Vector Prog;
	private Vector Mem;
	private FileReader fichero;
	private String pasos;
	
	/**
	 * El constructor de la clase MaquinaP que sólo tiene el buffer de lectura del fichero como parmetro de entrada.
	 * @param f Recibe como parámetro la ruta del fichero a ejecutar para poder inicializar todo.
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
	 * @param h Entero que controla el estado actual de la pila, además es donde se refleja el error.
	 */
	public void setH(int h) {
		H = h;
	}
	
	/**
	 * Accesor para el atributo de la clase, Mem. Que indica el estado de la memoria del Programa. 
	 * @return Vector donde cada celda es una posición de Memoria.
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
	 * @return Entero que indica el numero de instrucción.
	 */
	public int getPC() {
		return PC;
	}
	
	/**
	 * Mutador para el atributo de la clase, PC. Que indica el contador de instrucciones del Programa. 
	 * @param pc Entero para actualizar la nueva posción del programa.
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
	 * @param st Recibe por parámetro el entero con el que cambiar la cima de la pila.
	 */
	public void setST(int st) {
		ST = st;
	}
	
	/**
	 * Obtiene el programa del fichero que recibe por parámetro. Guarda cada instruccion del programa en una posición del 
	 * vector Prog. Para obtener el programa crea un BufferReader y se lanzan y capturan excepciones al respecto. 
	 * 
	 * @param f Recibe por parámetro el fichero del cual obtiene el programa. Ha de ser un FileReader para luego trabajar con el.
	 * @return Devuelve el Vector con el programa. Cada posición es una instruccion de la maquina P.
	 * @exception java.io.FileNotFoundException Se lanza y se captura en este mismo método.
	 * @exception java.io.IOException Se lanza y se captura en este mismo método.
	 */
	private Vector damePrograma(FileReader f){
		Vector v=new Vector();
		BufferedReader entrada = null;
	    try {
	      entrada = new BufferedReader(f);
	      String linea = null;
	      //int i=0;
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
	 * Aumenta el tamaño del vector memoria según las necesidades del programa que va a ejecutar.
	 * 
	 * @param tam Recibe un entero con el tamaño que ha de aumentar.
	 */
	private void aumentoMem(int tam){
		for(int i = Mem.size();i<tam+1;i++){
			Mem.add(i,null);
		}
	}
	
	/**
	 * Método que ejecuta la Máquina P. Va leyendo las intrucciones que ha generado el compilador y las ejecuta.
	 *
	 */
	public void ejecuta() throws Exception{
		String i;
		String[] linea;
		int j= 0;
		pasos=pasos.concat("Comenzamos con la ejecucion de la pila. \n\n");
		System.out.println("\n\n\nComenzamos con la ejecucion de la pila. \n\n\n");
		while (H==0){
			if(j<Prog.size()){
				i= (String)Prog.get(j);
				linea = i.split(" ");
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
				else{
					error();
					j++;
				}
			}
			else{
				eof();
			}
		}
	}
	
	/**
	 * Método que devuelve un String con el contenido de la Memoria. 
	 * Se usa para ver el contenido final de la memoria para despues de ejecutar la máquina P.
	 * 
	 * @return String con el contenido del vector Mamoria.
	 */
	public String resultadoMem(){
		String s="";
		for (int i=0;i<Mem.size();i++){
			System.out.println(Mem.elementAt(i));
			if(Mem.elementAt(i)!=null){
				s= s.concat(((Integer)Mem.elementAt(i)).toString());
				s=s.concat(" \n");
			}
		}
		return s;
	}
	
	/**
	 * Método que realiza una operación suma. Se desapilan los dos primeros elementos de la pila y se suman.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habrá un elemento menos. También se aumenta en uno 
	 * el contador del programa.
	 * 
	 * (R1) suma:
	 * Pila[ST-1] <-- Pila[ST-1] + Pila[ST]
	 * ST <-- ST -1 
	 * PC <-- PC + 1
	 */

	public void suma(){
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s2.intValue()+s1.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R2) resta:
	 *	Pila[ST-1] <-- Pila[ST-1] - Pila[ST]
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void resta(){
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
	public void multiplica(){
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
	public void divide(){
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
		System.out.println(pila.peek());
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
		//System.out.println();
		if ((Mem.size()-1<d)&&(Mem.elementAt(d)!=null)){
			pila.push(Mem.elementAt(d));  
			PC = PC + 1;
		}
		else{
			throw new Exception("ERROR: Variable sin inicializar.");
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
	public void desapila_dir(int d){
		//Primero comprobamos que la memoria sea suficiente.
		//Sino lo es aumentamos el tamaño del vector.
		if (d>=Mem.size()){
			aumentoMem(d);
			Mem.set(d,pila.pop());
			System.out.println(Mem.elementAt(d)+" "+d);
		}
		else{
			System.out.println("La cima de la Pila es:  "+pila.peek());
			Mem.set(d,pila.pop());
			System.out.println("Lo que meto en memoria es: "+Mem.elementAt(d)+" "+d);
			//Mem.insertElementAt(pila.elementAt(ST),d);
		}
		ST = ST -1;
		PC = PC + 1;
	}
	
	/**
	 * (R8) EOF:
	 *	H <-- 1
	 */
	public void eof(){
		H=1;
	}
	
	/**
	 * (R9) En cualquier otro caso, la máquina entra en estado de error y se detiene la ejecución.
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
	public void and(){
		int a1= ((Integer)pila.pop()).intValue();
		if ((a1!=0)&&(((Integer)pila.pop()).intValue()!=0)){
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
	public void or(){
		int o1= ((Integer)pila.pop()).intValue();
		if ((o1==0)&&(((Integer)pila.pop()).intValue()==0)){
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
	public void not(){
		if (((Integer)pila.pop()).intValue()==0){
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
	public void neg(){
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
	public void menor(){
		int c1= ((Integer)pila.pop()).intValue();
		if (((Integer)pila.pop()).intValue()<c1){
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
	public void menorIgual(){
		int c1= ((Integer)pila.pop()).intValue();
		if (((Integer)pila.pop()).intValue()<=c1){
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
	public void mayor(){
		int c1= ((Integer)pila.pop()).intValue();
		if (((Integer)pila.pop()).intValue()>c1){
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
	public void mayorIgual(){
		int c1= ((Integer)pila.pop()).intValue();
		if (((Integer)pila.pop()).intValue()>=c1){
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
	public void igual(){
		int c1= ((Integer)pila.pop()).intValue();
		if (c1==((Integer)pila.pop()).intValue()){
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
	public void distinto(){
		int c1= ((Integer)pila.pop()).intValue();
		if (c1!=((Integer)pila.pop()).intValue()){
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
	}
	
	/**
	 * (R21) ir-f(s):
	 *	si Pila[ST] = 0 PC <-- s
	 *	sino PC <--PC+1 fsi
	 *	ST <-- ST ? 1	
	 */
	public void ir_f(int s){
		if (((Integer)pila.pop()).intValue()==0){
			PC = s;
		}
		else{
			PC = PC + 1;
		}
		ST = ST -1;
	}
}