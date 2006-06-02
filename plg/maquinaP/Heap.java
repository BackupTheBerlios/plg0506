/**
 * 
 */
package maquinaP;

import java.util.Vector;

/**
 * @author paloma
 *
 */
public class Heap {
	
	private Vector heap;
	private Vector ocupados;
	
	/**
	 * 
	 */
	public Heap(int i) {
		super();
		heap= new Vector(i);
		ocupados= new Vector(i);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return Returns the heap.
	 */
	public Vector getHeap() {
		return heap;
	}
	/**
	 * @param heap The heap to set.
	 */
	public void setHeap(Vector heap) {
		this.heap = heap;
	}
	/**
	 * @return Returns the ocupados.
	 */
	public Vector getOcupados() {
		return ocupados;
	}
	/**
	 * @param ocupados The ocupados to set.
	 */
	public void setOcupados(Vector ocupados) {
		this.ocupados = ocupados;
	}
	
	public int reserva(int tam){
		
		
		return 0;
	}
	
	public void libera(int dir, int tam){
		
	}
	
	public int getElementAt(int d) throws Exception{
		if (ocupados.contains(new Integer(d))){
			return ((Integer)heap.get(d)).intValue();
		}
		else{
			throw new Exception("Acceso de Memoria no valido");
		}
	}
	
	public void setElementAt(int d, int value) throws Exception{
		if (!ocupados.contains(new Integer(d))){
			heap.setElementAt(new Integer(value),d);
			
		}
		else{
			throw new Exception("Acceso de Memoria no valido");
		}
	}
}
