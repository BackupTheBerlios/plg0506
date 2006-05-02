/**
 * 
 */
package procesador;

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
	private Vector pila;
	private int PC;
	private int H;
	private int ST;
	private String Prog;
	private Vector Mem;
	
	/**
	 * 
	 * @param prog
	 */	
	public MaquinaP(String prog) {
		super();
		// TODO Auto-generated constructor stub
		Prog = prog;
		pila = new Vector();
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
	public Vector getPila() {
		return pila;
	}
	
	/**
	 * 
	 * @param pila
	 */
	public void setPila(Vector pila) {
		this.pila = pila;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getProg() {
		return Prog;
	}
	
	/**
	 * 
	 * @param prog
	 */
	public void setProg(String prog) {
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
	 * (R1) suma:
	 * Pila[ST-1] <-- Pila[ST] + Pila[ST-1]
	 * ST <-- ST -1 
	 * PC <-- PC + 1
	 */

	public void suma(){
		Integer s1 = (Integer)pila.remove(ST);
		Integer s2 = (Integer)pila.remove(ST-1);
		Integer s = new Integer(s1.intValue()+s2.intValue());
		pila.add(ST-1,s);
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
		Integer s1 = (Integer)pila.remove(ST);
		Integer s2 = (Integer)pila.remove(ST-1);
		Integer s = new Integer(s1.intValue()-s2.intValue());
		pila.add(ST-1,s);
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
		Integer s1 = (Integer)pila.remove(ST);
		Integer s2 = (Integer)pila.remove(ST-1);
		Integer s = new Integer(s1.intValue()*s2.intValue());
		pila.add(ST-1,s);
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
		Integer s1 = (Integer)pila.remove(ST);
		Integer s2 = (Integer)pila.remove(ST-1);
		Integer s = new Integer(s1.intValue()/s2.intValue());
		pila.add(ST-1,s);
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
		Integer n1= new Integer (n);
		pila.add(ST,n1);
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
		pila.add(ST,Mem.elementAt(d));  
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
		Mem.add(d,pila.elementAt(ST));
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
		if ((((Integer)pila.remove(ST)).intValue()!=0)&&(((Integer)pila.remove(ST-1)).intValue()!=0)){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
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
		if ((((Integer)pila.remove(ST)).intValue()!=0)&&(((Integer)pila.remove(ST-1)).intValue()!=0)){
			pila.add(ST,new Integer(0));
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
		if (((Integer)pila.remove(ST)).intValue()==0){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
		}
		PC = PC + 1;
	}
	
	/**
	 * (R16) Neg:
	 *	Pila[ST] <--  - Pila[ST]
	 *	PC <-- PC + 1 
	 */
	public void neg(){
		Integer n= (Integer)pila.remove(ST);
		pila.add(ST,new Integer(-(n.intValue())));
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
		if (((Integer)pila.remove(ST-1)).intValue()<((Integer)pila.remove(ST)).intValue()){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
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
		if (((Integer)pila.remove(ST-1)).intValue()<=((Integer)pila.remove(ST)).intValue()){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
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
		if (((Integer)pila.remove(ST-1)).intValue()>((Integer)pila.remove(ST)).intValue()){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
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
		if (((Integer)pila.remove(ST-1)).intValue()>=((Integer)pila.remove(ST)).intValue()){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
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
		if (((Integer)pila.remove(ST-1)).intValue()==((Integer)pila.remove(ST)).intValue()){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
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
		if (((Integer)pila.remove(ST-1)).intValue()!=((Integer)pila.remove(ST)).intValue()){
			pila.add(ST,new Integer(1));
		}
		else{
			pila.add(ST,new Integer(0));
		}
		ST = ST -1;
		PC = PC + 1;
	}
}