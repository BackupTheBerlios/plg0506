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
 * </UL></P>
 * 
 * @author Jonás Andradas, Paloma de la Fuente, Leticia García y Silvia Martín
 *
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
	 * 
	 * @param prog
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
			System.out.println("ERROR: Archivo no encontrado: " + fcod);	
		}
		Prog = damePrograma(fichero);
		pasos="";
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPasos() {
		return pasos;
	}

	/**
	 * 
	 * @return
	 */
	public int getH() {
		return H;
	}
	
	/**
	 * 
	 * @param h
	 */
	public void setH(int h) {
		H = h;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector getMem() {
		return Mem;
	}
	
	/**
	 * 
	 * @param mem
	 */
	public void setMem(Vector mem) {
		Mem = mem;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPC() {
		return PC;
	}
	
	/**
	 * 
	 * @param pc
	 */
	public void setPC(int pc) {
		PC = pc;
	}
	
	/**
	 * 
	 * @return
	 */
	public Stack getPila() {
		return pila;
	}
	
	/**
	 * 
	 * @param pila
	 */
	public void setPila(Stack pila) {
		this.pila = pila;
	}
	
	/**
	 * 
	 * @return
	 */
	public Vector getProg() {
		return Prog;
	}
	
	/**
	 * 
	 * @param prog
	 */
	public void setProg(Vector prog) {
		Prog = prog;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getST() {
		return ST;
	}
	
	/**
	 * 
	 * @param st
	 */
	public void setST(int st) {
		ST = st;
	}
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	public Vector damePrograma(FileReader f){
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
		      ex.printStackTrace();
		}
		catch (IOException ex){
		      ex.printStackTrace();
		}
		return v;
	}
	
	/**
	 * 
	 * @param tam
	 */
	public void aumentoMem(int tam){
		for(int i = Mem.size();i<tam+1;i++){
			Mem.add(i,null);
		}
	}
	
	/**
	 * 
	 *
	 */
	public void ejecuta(){
		String i;
		String[] linea;
		int j= 0;
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
					j++;
				}
				else if (linea[0].compareTo("desapila-dir")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					desapila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
					j++;
				}
				else if (linea[0].compareTo("apila-dir")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					apila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
					j++;
				}
				else if (linea[0].compareTo("suma")==0){
					System.out.print(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					suma();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("resta")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					resta();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("multiplica")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					multiplica();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("divide")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					divide();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("and")==0){
					System.out.println(linea[0]+ "  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					and();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("or")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					or();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("not")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					not();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("neg")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					neg();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("menor")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					menor();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("menor_o_igual")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					menorIgual();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("mayor")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					mayor();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("mayor_o_igual")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					mayorIgual();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("igual")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					igual();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("distinto")==0){
					System.out.println(linea[0]+"  ");
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  ");
					pasos= pasos.concat(" \n");
					distinto();
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("ir-a")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					ir_a((new Integer(Integer.parseInt(linea[1]))).intValue());
					System.out.println(pila.peek());
					j++;
				}
				else if (linea[0].compareTo("ir-f")==0){
					System.out.println(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat("El numero de instruccion es: ("+PC+") - ");
					pasos= pasos.concat(linea[0]+"  "+Integer.parseInt(linea[1]));
					pasos= pasos.concat(" \n");
					ir_f((new Integer(Integer.parseInt(linea[1]))).intValue());
					System.out.println(pila.peek());
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
	 * 
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
	public void apila_dir (int d){
		ST = ST + 1; 
		System.out.println(Mem.elementAt(d));
		pila.push(Mem.elementAt(d));  
		PC = PC + 1;
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