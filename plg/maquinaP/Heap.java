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
		ultimo=-1;
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
		System.out.println("floating");
		for (int m=0; m<ocupados.size();m++){
			if (ocupados.elementAt(m) == null)
				System.out.println("El elemento " + m +" es null");
			else
				System.out.println("El elemento "+ m + " es " + ocupados.elementAt(m));
		}
		System.out.println("Y U vale: " + u);
		if (!ocupados.isEmpty()){
			System.out.println("no soy vacio");
			Integer j=(Integer)ocupados.get(i);
			System.out.println("accedi a i");
			Integer z=(Integer)ocupados.get(i/2);
			System.out.println("enteritos nuevos");
			while((i!=0)&& (j.intValue()<z.intValue())){
				System.out.println("bucle de la muerte");
				aux=j;
				j= z;
				z=aux;
				System.out.println("ocupading");
				ocupados.setElementAt(j,i);
				ocupados.setElementAt(z,i/2);
				i= i/2;
				System.out.println("termino el bucle de la muerte");
			}
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
		System.out.println("reservando");
		for (int i=c+1;i<=b;i++){
			heap.addElement(null);
			ocupados.addElement(new Integer(i));
			ultimo=ultimo+1;
			flotar(ocupados,i);
		}
		return ultimo;
	}
	
	/**
	 * Libera desde dir tantas celdas como le indica tam, nos interesa que mueva los indices, las direcciones??
	 * @param dir
	 * @param tam
	 * @throws Exception
	 */
	public void libera(int dir, int tam)throws Exception{
		//cuando elimine una posicion o varias de ocupados llamo a monticulizar
		System.out.println("Voy a liberar");
		System.out.println(dir);
		for(int i=dir;i<dir+tam;i++){
			System.out.println("voy a poner el puntero a null");
			heap.setElementAt(null,i);
			System.out.println("Ahora voy a borrar la posicion de ocupados");
			ocupados.removeElement(new Integer(i));
			System.out.println("saldre de libera");
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