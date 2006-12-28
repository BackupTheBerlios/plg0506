import java.util.*;

/**
 *Clase Instruccion, contiene las partes de una instrucci�n del lenguaje objeto
 * @author owner
 */

class Instruccion
{
	
	/**
	 *Atributo que guarda la orden de la instrucci�n. 
	 */
	private String orden;
	/**
	 *Atributo que guarda el valor de la instrucci�n. 
	 */
	private int valor = 0;
//	private String etiqueta; esto lo necesitaremos para una futura ampliaci�n.
	
	
	
	/**
	 * Constructor con dos par�metros.
	 * @param ord Orden del c�digo P
	 * @param val Valor de la orden
	 */
	public Instruccion(String ord, int val)
	{
		orden = ord;
		valor = val;
	}
	
	/**
	 * Contructor de la instrucci�n correspondiente a una instrucci�n con una
	 * orden sin parametros.
	 * @param ord Orden del c�digo P
	 */
	public Instruccion (String ord)
	{
		orden = ord;
	}
	
	/**
	 * devuelve la orden de una instruccion
	 * @return atributo orden.
	 */
	String dameOrden()
	{
		return orden;
	}
	
	/**
	 * devuelve el valor de una determinada instruccion
	 * @return atributo valor
	 */
	int dameValor()
	{
		return valor;
	}
	
}
/**
 * Clase resutadoAn�lisis: guarda el resultado de los an�lisis sint�ctico y l�xico
 * @author owner
 *
 */
public class resultadoAnalisis {
	
	//vector que almacena las instrucciones del lenguaje objeto
	private Vector instrucciones;
	//vector que almacena strings con errores + el n�mero de l�nea.
	private Vector errores;
	
	
	/**
	 * Constructor que crea dos vectores vac�os
	 */
	public resultadoAnalisis() {
		instrucciones = new Vector();
		errores = new Vector();
	}
	
	/**
	 * M�todo para a�adir una instrucci�n con valor al 
	 * vector de instrucciones
	 * @param o Orden de la instrucci�n
	 * @param v Valor de la instrucci�n
	 */
	public void emite (String o, int v){
		Instruccion i = new Instruccion(o,v);
		instrucciones.add(i);
	}
	
	/**
	 * M�todo para a�adir una instrucci�n sin valor al 
	 * vector de instrucciones
	 * @param o Orden de la instrucci�n
	 */
	public void emite (String o){
		Instruccion i = new Instruccion(o);
		instrucciones.add(i);
	}
	
	/**
	 * M�todo que a�ade un error al vector de errores
	 * @param s String con el nuevo error
	 */
	public void a�adeError (String s){
		errores.add(s);
	}
	
	/**
	 * @return devuelve el atributo errores
	 */
	public Vector dameErrores(){
		return this.errores;
	}
	
	/**
	 * @return devuelve el atributo instrucciones
	 */
	public Vector dameInstrucciones(){
		return this.instrucciones;
	}
	
}