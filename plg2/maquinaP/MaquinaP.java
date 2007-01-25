package maquinaP;
/**
 * La clase <B>maquinaP</B> 
 * @author  Alberto Velazquez
 *
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import javax.swing.JOptionPane;

public class MaquinaP {

	/*
	 * Atributos de la clase:
	 * 
	 * pila: La pila de los operandos de la m?quina.
	 * PC: Contador de programa. Al final de la ejecuci?n nos dice cuantas lienas tiene dicho programa.
	 * H: Indica si la m?quina esta en ejecuci?n, parada por error, o acabo su ejecuci?n.
	 * ST: Puntero a la cima de la pila.
	 * Prog:Memoria de programas. Aqui hab?a puesto el nombre del fichero pero quizas deberia ser el
	 * codigo del programa.
	 * Mem: Memoria de datos estatica.
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
	private int tamMem;

	/**
	 * El constructor de la clase MaquinaP que solo tiene el buffer de lectura del fichero como parametro de entrada.
	 * @param f Recibe como parametro la ruta del fichero a ejecutar para poder inicializar todo.
	 *
	 */	
	public MaquinaP(File file) {
		super();
		String f = file.toString();
		pila = new Stack();
		PC = 0;
		H = 0;
		tamMem= Integer.MAX_VALUE;
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
	 * @param h Entero que controla el estado actual de la pila, ademas es donde se refleja el error.
	 */
	public void setH(int h) {
		H = h;
	}
	
	/**
	 * Accesor para el atributo de la clase, Mem. Que indica el estado de la memoria del Programa. 
	 * @return Vector donde cada celda es una posicion de Memoria.
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
	 * @return Entero que indica el numero de instruccion.
	 */
	public int getPC() {
		return PC;
	}
	
	/**
	 * Mutador para el atributo de la clase, PC. Que indica el contador de instrucciones del Programa. 
	 * @param pc Entero para actualizar la nueva poscion del programa.
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
	 * @param st Recibe por parametro el entero con el que cambiar la cima de la pila.
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
	 * Obtiene el programa del fichero que recibe por parametro. Guarda cada instruccion del programa en una posicion del 
	 * vector Prog. Para obtener el programa crea un BufferReader y se lanzan y capturan excepciones al respecto. 
	 * 
	 * @param f Recibe por parametro el fichero del cual obtiene el programa. Ha de ser un FileReader para luego trabajar con el.
	 * @return Devuelve el Vector con el programa. Cada posicion es una instruccion de la maquina P.
	 * @exception java.io.FileNotFoundException Se lanza y se captura en este mismo metodo.
	 * @exception java.io.IOException Se lanza y se captura en este mismo metodo.
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
	 * Aumenta el tama?o del vector memoria segun las necesidades del programa que va a ejecutar.
	 * 
	 * @param tam Recibe un entero con el tamao que ha de aumentar.
	 */
	private void aumentoMem(int tam){
		for(int i = Mem.size();i<tam+1;i++){
			Mem.add(i,null);
		}
	}
	
	/**
	 * Metodo que ejecuta la Maquina P. Va leyendo las intrucciones que ha generado el 
	 * compilador y las ejecuta.
	 *
	 */
	
	private boolean hasInt(String l) {
		return (l.compareTo("apila") == 0) || (l.compareTo("desapila") == 0) || (l.compareTo("apila-dir") == 0) || (l.compareTo("desapila-dir") == 0);
	}
	
	public String ejecuta() throws Exception{
		String i;
		String[] linea;
		pasos=pasos.concat("Comenzamos con la ejecucion de la pila. \n\n");
		//System.out.println("\n\n\nComenzamos con la ejecucion de la pila. \n\n\n");
		//System.out.println(Prog.size());
		for (int j = 0; H == 0; j++){
			if(PC<Prog.size()){
				i= (String)Prog.get(PC);
				linea = i.split(" ");
				if (hasInt(linea[0])){
					System.out.println(linea[0] + "  " + linea[1]);
				}
				else{
					System.out.println(linea[0] + "  ");
				}
				pasos = pasos.concat("Num. de instr.: (" + PC + ") - " + linea[0] + "  ");
				
				if (hasInt(linea[0]))
					pasos = pasos.concat(linea[1]);
				pasos = pasos.concat("\n");
				if (linea[0].compareTo("apila")==0)
					apila((new Integer(Integer.parseInt(linea[1]))).intValue());
				else if (linea[0].compareTo("desapila-dir")==0)
					desapila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
				else if (linea[0].compareTo("apila-dir")==0)
					apila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
				else if (linea[0].compareTo("suma")==0){
					suma();
				}
				else if (linea[0].compareTo("resta")==0)
					resta();
				else if (linea[0].compareTo("multiplica")==0)
					multiplica();
				else if (linea[0].compareTo("divide")==0)
					divide();
				else if (linea[0].compareTo("modulo")==0)
					modulo();
				else if (linea[0].compareTo("and")==0)
					and();
				else if (linea[0].compareTo("or")==0)
					or();
				else if (linea[0].compareTo("not")==0)
					not();
				else if (linea[0].compareTo("positivo")==0)
					mas();
				else if (linea[0].compareTo("menos")==0)
					menos();
				else if (linea[0].compareTo("menor")==0)
					menor();
				else if (linea[0].compareTo("menorIgual")==0)
					menorIgual();
				else if (linea[0].compareTo("mayor")==0)
					mayor();
				else if (linea[0].compareTo("mayorIgual")==0)
					mayorIgual();
				else if (linea[0].compareTo("igual")==0)
					igual();
				else if (linea[0].compareTo("distinto")==0)
					distinto();
				else if (linea[0].compareTo("stop")==0)
					H=1;
				else{
					error();
				}
				if (pila.empty())
					pasos= pasos.concat("La pila ahora esta vacia\n");
				else
					pasos= pasos.concat("La cima de la pila ha cambiado a: "+ pila.peek() + "\n");
			}
			else
				eof();
		}
		return pasos;
	}
	
	/**
	 * Metodo que devuelve un String con el contenido de la Memoria. 
	 * Se usa para ver el contenido final de la memoria despues de ejecutar la maquina P.
	 * 
	 * @return String con el contenido del vector Memoria.
	 */
	public String resultadoMem(){
		String s="\n"+"Memoria :"+"\n";
		for (int i=0;i<Mem.size();i++){
			if(Mem.elementAt(i)!=null){
				s= s.concat("posicion "+i+":  "+((Integer)Mem.elementAt(i)).toString());
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
	 * Metodo que realiza una operacion de suma. Se desapilan los dos primeros elementos de la pila y se suman.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
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
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * Metodo que realiza una operacion de resta. Se desapilan los dos primeros elementos de la pila y se restan.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
	 * el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de multiplicacion. Se desapilan los dos primeros elementos de la pila y se multiplican.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
	 * el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de division. Se desapilan los dos primeros elementos de la pila y se dividen.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
	 * el contador del programa.
	 * 
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
		if (s1.intValue()==0) throw new Exception("ERROR: Estas tratando de dividir por 0.");
		Integer s = new Integer(s2.intValue()/s1.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}

	public void modulo()throws Exception{
		if (ST<1){
			throw new Exception("ERROR: Modulo. La pila no contiene los datos necesarios.");
		}
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		if (s1.intValue()==0) throw new Exception("ERROR: Estas tratando de dividir por 0.");
		Integer s = new Integer(s2.intValue()%s1.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}

	/**
	 * Metodo que apila un entero en la pila. Se aumenta en uno el tamao de la pila y se apila el entero que recibe como
	 * parametro. Tambien se aumenta en uno el contador del programa.
	 * 
	 * ST <-- ST + 1 
	 * Pila[ST] <-- n
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
	 * Metodo que apila en la cima de la pila el valor que contiene la direccion de memoria que recibe como parametro. Se comprueba antes 
	 * si la direccion de memoria pertenece a memoria estatica o memoria dinamica. Tambien se aumenta en uno el contador del programa.
	 * 
	 * (R6) apila-dir(d):
	 *	ST <-- ST + 1 
	 *	Pila[ST] <-- Mem[d]  
	 *	PC <-- PC + 1
	 *
	 * @param d
	 */
	public void apila_dir (int d) throws Exception{
		//System.out.println("Estoy en apila-dir")
		ST = ST + 1; 
		//System.out.println("Con valor de dir " + d);
		if(d<tamMem){
			if (d >= 0){
				if ((Mem.size()>=d)&&(!Mem.isEmpty())){ 
					if (Mem.elementAt(d)!=null) pila.push(Mem.elementAt(d));
					else throw new Exception("ERROR: Variable sin inicializar.");
					PC = PC + 1;
				}
				else{				
					throw new Exception("ERROR: Variable sin declarar.");
				}
			}
			else{
				throw new Exception("ERROR: Puntero sin inicializar.");
			}
		}
	}
	
	/**
	 * Metodo que desapila la cima de la pila y lo guarda en la direccion de memoria que recibe como parametro. Se disminuye en uno el tamao 
	 * de la pila y se comprueba si es memoria dinamica o estatica. Tambien se aumenta en uno el contador del programa.
	 * 
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
		if (d<tamMem){
			if (d >= 0){
				if (d>=Mem.size()){
					aumentoMem(d);
					Mem.set(d,pila.pop());
				}
				else{
					Mem.set(d,pila.pop());
				}
			}
			else{
				throw new Exception("ERROR: Puntero sin inicializar.");
			}
			
		}
		ST = ST -1;
		PC = PC + 1;
	}

	/**
	 * Metodo que para la ejecucion de la mquina P cuando se recibe un final de fichero.
	 * 
	 * (R8) EOF:
	 *	H <-- 1
	 */
	public void eof(){
		H=1;
	}
	
	/**
	 * Metodo que indica a la pila que pare la ejecucion con un error.
	 * 
	 * (R9) En cualquier otro caso, la m?quina entra en estado de error y se detiene la ejecuci?n.
	 * H <-- -1
	 */
	public void error(){
		H = -1;
	}
	
	/**
	 * Metodo que realiza una operacion and. Se desapilan los dos primeros elementos de la pila y se realiza una and.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
	 * el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion or. Se desapilan los dos primeros elementos de la pila y se realiza la operacion.  Despues se apila 
	 * en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
	 * el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de negacion. Se desapila la cima de la pila y se realiza una negacion.  Despues se apila 
	 * en la cima el resultado. Tambien se aumenta en uno el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion negacion de un entero. Se desapilan los dos primeros elementos de la pila y se realiza una and.  
	 * Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta 
	 * en uno el contador del programa.
	 * 
	 * (R16) Neg:
	 *	Pila[ST] <--  - Pila[ST]
	 *	PC <-- PC + 1 
	 */
	public void menos()throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Neg. La pila no contiene los datos necesarios.");
		}
		Integer n= (Integer)pila.pop();
		if (n.intValue()!=0) pila.push(new Integer(-(n.intValue())));
		else pila.push(n);
		PC = PC + 1;
	}
	
	public void mas()throws Exception{
		if (ST<0){
			throw new Exception("ERROR: Neg. La pila no contiene los datos necesarios.");
		}
		PC = PC + 1;
	}
	/**
	 * Metodo que realiza una operacion de menor con dos operandos. Se desapilan los dos primeros elementos de la pila y se realiza la operacion.
	 * Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se aumenta en uno 
	 * el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de menor o igual con dos operandos. Se desapilan los dos primeros elementos de la pila y se realiza la 
	 * operacion.  Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se 
	 * aumenta en uno el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de myor con dos operandos. Se desapilan los dos primeros elementos de la pila y se realiza la 
	 * operacion.  Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se 
	 * aumenta en uno el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de mayor o igual con dos operandos. Se desapilan los dos primeros elementos de la pila y se realiza la 
	 * operacion.  Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se 
	 * aumenta en uno el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de igual con dos operandos. Se desapilan los dos primeros elementos de la pila y se realiza la 
	 * operacion.  Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se 
	 * aumenta en uno el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de distinto con dos operandos. Se desapilan los dos primeros elementos de la pila y se realiza la 
	 * operacion.  Despues se apila en la cima el resultado, disminuye en 1 el puntero a la cima ya que habra un elemento menos. Tambien se 
	 * aumenta en uno el contador del programa.
	 * 
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
	 * Metodo que realiza una operacion de salto del programa. Se comprueba que el salto no supere el tamao del programa y se aumenta el contador
	 * del programa segun el valor que recibimos por parametro.
	 * 
	 * (R20) ir-a(s):
	 * PC <-- s
	 * 
	 * @param s Numero de instrucciones que ha de saltar y no ejecutar la maquinaP
	 */
	
	/**
	 * Metodo que obtiene el contenido de la pila en un String para ver su contenido.
	 * 
	 * @return String con el contenido de la Pila
	 */
	public String muestraPila(){
		Stack aux = new Stack();
		String pilas="El contenido de la pila es: \n";
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
