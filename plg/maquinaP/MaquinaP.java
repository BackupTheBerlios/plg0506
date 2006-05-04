/**
 * 
 */
package maquinaP;

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
	
	/**
	 * 
	 * @param prog
	 */	
	public MaquinaP(Vector prog) {
		super();
		// TODO Auto-generated constructor stub
		Prog = prog;
		pila = new Stack();
		PC = 0;
		H = 0;
		ST = -1;
		Mem= new Vector();
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
					System.out.println(linea[0]);
					apila((new Integer(Integer.parseInt(linea[1]))).intValue());
					j++;
				}
				else if (linea[0].compareTo("desapila-dir")==0){
					System.out.println(linea[0]);
					desapila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
					j++;
				}
				else if (linea[0].compareTo("apila-dir")==0){
					System.out.println(linea[0]);
					apila_dir((new Integer(Integer.parseInt(linea[1]))).intValue());
					j++;
				}
				else if (linea[0].compareTo("suma")==0){
					System.out.println(linea[0]);
					suma();
					j++;
				}
				else if (linea[0].compareTo("resta")==0){
					System.out.println(linea[0]);
					resta();
					j++;
				}
				else if (linea[0].compareTo("multiplica")==0){
					System.out.println(linea[0]);
					multiplica();
					j++;
				}
				else if (linea[0].compareTo("divide")==0){
					System.out.println(linea[0]);
					divide();
					j++;
				}
				else if (linea[0].compareTo("and")==0){
					System.out.println(linea[0]);
					and();
					j++;
				}
				else if (linea[0].compareTo("or")==0){
					System.out.println(linea[0]);
					or();
					j++;
				}
				else if (linea[0].compareTo("not")==0){
					System.out.println(linea[0]);
					not();
					j++;
				}
				else if (linea[0].compareTo("neg")==0){
					System.out.println(linea[0]);
					neg();
					j++;
				}
				else if (linea[0].compareTo("menor")==0){
					System.out.println(linea[0]);
					menor();
					j++;
				}
				else if (linea[0].compareTo("menor_o_igual")==0){
					System.out.println(linea[0]);
					menorIgual();
					j++;
				}
				else if (linea[0].compareTo("mayor")==0){
					System.out.println(linea[0]);
					mayor();
					j++;
				}
				else if (linea[0].compareTo("mayor_o_igual")==0){
					System.out.println(linea[0]);
					mayorIgual();
					j++;
				}
				else if (linea[0].compareTo("igual")==0){
					System.out.println(linea[0]);
					igual();
					j++;
				}
				else if (linea[0].compareTo("distinto")==0){
					System.out.println(linea[0]);
					distinto();
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
	public void resultadoMem(){
		for (int i=0;i<Mem.size();i++){
			System.out.println(Mem.elementAt(i));
		}
	}
	
	/**
	 * (R1) suma:
	 * Pila[ST-1] <-- Pila[ST] + Pila[ST-1]
	 * ST <-- ST -1 
	 * PC <-- PC + 1
	 */

	public void suma(){
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s1.intValue()+s2.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R2) resta:
	 *	Pila[ST-1] <-- Pila[ST] - Pila[ST-1]
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void resta(){
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s1.intValue()-s2.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R3) multiplica:
	 *	Pila[ST-1] <-- Pila[ST] * Pila[ST-1]  
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void multiplica(){
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s1.intValue()*s2.intValue());
		pila.push(s);
		ST = ST-1;
		PC = PC + 1;
	}
	
	/**
	 * (R4) divide:
	 *	Pila[ST-1] <-- Pila[ST] / Pila[ST-1]  
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void divide(){
		Integer s1 = (Integer)pila.pop();
		Integer s2 = (Integer)pila.pop();
		Integer s = new Integer(s1.intValue()/s2.intValue());
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
			Mem.set(d,pila.elementAt(ST));
			System.out.println(Mem.elementAt(d)+" "+d);
		}
		else{
			Mem.set(d,pila.elementAt(ST));
			System.out.println(Mem.elementAt(d)+" "+d);
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
			pila.add(ST,new Integer(1));
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
	 *	Pila[ST ? 1] <-- "true" si Pila[ST ? 1] < Pila[ST]
	 *					"false" en c.o.c
	 *	ST <-- ST ? 1
	 *	PC <-- PC + 1
	 */
	public void menor(){
		int c1= ((Integer)pila.pop()).intValue();
		if (c1<((Integer)pila.pop()).intValue()){
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
		if (c1<=((Integer)pila.pop()).intValue()){
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
		if (c1>((Integer)pila.pop()).intValue()){
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
		if (c1>=((Integer)pila.pop()).intValue()){
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
}