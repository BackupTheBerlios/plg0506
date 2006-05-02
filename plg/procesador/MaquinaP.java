/**
 * 
 */
package procesador;

import java.util.Stack;
import java.util.Vector;

/**
 * @author paloma
 *
 */
public class MaquinaP {

	private Stack pila;
	private int PC;
	private int H;
	private int ST;
	private String Prog;
	private Vector Mem;
	
		
	public MaquinaP(String prog) {
		super();
		// TODO Auto-generated constructor stub
		Prog = prog;
		pila = new Stack();
		PC = 0;
		H = 0;
		ST = -1;
		Mem= new Vector();
	}
	public int getH() {
		return H;
	}
	public void setH(int h) {
		H = h;
	}
	public Vector getMem() {
		return Mem;
	}
	public void setMem(Vector mem) {
		Mem = mem;
	}
	public int getPC() {
		return PC;
	}
	public void setPC(int pc) {
		PC = pc;
	}
	public Stack getPila() {
		return pila;
	}
	public void setPila(Stack pila) {
		this.pila = pila;
	}
	public String getProg() {
		return Prog;
	}
	public void setProg(String prog) {
		Prog = prog;
	}
	public int getST() {
		return ST;
	}
	public void setST(int st) {
		ST = st;
	}
	
	/*
	 * (R1) suma:
	 * Pila[ST-1] <-- Pila[ST] + Pila[ST-1]
	 * ST <-- ST -1 
	 * PC <-- PC + 1
	 */
	public void suma(){
		
	}
	
	/*
	 * (R2) resta:
	 *	Pila[ST-1] <-- Pila[ST] - Pila[ST-1]
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void resta(){
		
	}
	
	/*
	 * (R3) multiplica:
	 *	Pila[ST-1] <-- Pila[ST] * Pila[ST-1]  
	 *	ST <-- ST -1 
	 *	PC <-- PC + 1
	 */
	public void multiplica(){
		
	}
	
	/*
	 * 
	 */
	public void divide(){
		
	}
	
	/*
	 * ST <-- ST + 1 
	 * Pila[ST] <-- n  Si utilizo una pila no puedo hacer eso exactamente...
	 * PC <-- PC + 1
	 */
	public void apila (int n){
		ST=ST+1;
		Integer n1= new Integer (n);
		pila.push(n1);
		PC=PC+1;
	}
	/*
	 * 
	 */
	
}
