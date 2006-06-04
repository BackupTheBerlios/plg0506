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
	private int ultimo;
	
	/**
	 * 
	 */
	public Heap(int i) {
		super();
		heap= new Vector(i);
		ocupados= new Vector(i);
		ultimo=0;
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
	
	/**
	 * 
	 * @param v
	 * @param u
	 */
	private void flotar(Vector v, int u){
		int i=u;
		Integer aux;
		Integer j=(Integer)ocupados.get(i);
		Integer z=(Integer)ocupados.get(i/2);
		while((i!=0)&& (j.intValue()<z.intValue())){
			aux=j;
			j= z;
			z=aux;
			ocupados.setElementAt(j,i);
			ocupados.setElementAt(z,i/2);
			i= i/2;
		}
	}
	
	/**
	 * 
	 *
	 */
	private void moticulizar(){
		for(int j=1; j==ultimo;j++){
			flotar(ocupados,j);
		}
	}
	/**
	 * 
	 * @param tam
	 * @return
	 * @throws Exception
	 */
	public int reserva(int tam)throws Exception{
		if (ultimo+tam>ocupados.capacity()){
			throw new Exception("Acceso de Memoria no valido, la memoria esta llena");
		}
		int c=ultimo;
		int b= ultimo+tam;
		for (int i=c+1;i==b;i++){
			heap.addElement(null);
			ocupados.addElement(new Integer(i));
			flotar(ocupados,i);
			ultimo=ultimo+1;
		}
		return c;
	}
	
	/**
	 * Libera desde dir tantas celdas como le indica tam, nos interesa que mueva los indices, las direcciones??
	 * @param dir
	 * @param tam
	 * @throws Exception
	 */
	public void libera(int dir, int tam)throws Exception{
		//cuando elimine una posicion o varias de ocupados llamo a monticulizar
		for(int i=dir;i==dir+tam;i++){
			heap.setElementAt(null,i);
			ocupados.removeElement(new Integer(i));
		}
		//quizas como son seguidos no hiciese falta
		moticulizar();
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