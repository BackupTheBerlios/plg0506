import java.util.*;

/**
 *Clase Instruccion, contiene las partes de una instrucción del lenguaje objeto
 * @author owner
 */

class Instruccion
{
	
	/**
	 *Atributo que guarda la orden de la instrucción. 
	 */
	private String orden;
	/**
	 *Atributo que guarda el valor de la instrucción. 
	 */
	private int valor = 0;
//	private String etiqueta; esto lo necesitaremos para una futura ampliación.
	
	
	
	/**
	 * Constructor con dos parámetros.
	 * @param ord Orden del código P
	 * @param val Valor de la orden
	 */
	public Instruccion(String ord, int val)
	{
		orden = ord;
		valor = val;
	}
	
	/**
	 * Contructor de la instrucción correspondiente a una instrucción con una
	 * orden sin parametros.
	 * @param ord Orden del código P
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
 * Clase resutadoAnálisis: guarda el resultado de los análisis sintáctico y léxico
 * @author owner
 *
 */
public class resultadoAnalisis {
	
	//vector que almacena las instrucciones del lenguaje objeto
	private Vector instrucciones;
	//vector que almacena strings con errores + el número de línea.
	private Vector errores;
	
	
	/**
	 * Constructor que crea dos vectores vacíos
	 */
	public resultadoAnalisis() {
		instrucciones = new Vector();
		errores = new Vector();
	}
	
	/**
	 * Método para añadir una instrucción con valor al 
	 * vector de instrucciones
	 * @param o Orden de la instrucción
	 * @param v Valor de la instrucción
	 */
	public void emite (String o, int v){
		Instruccion i = new Instruccion(o,v);
		instrucciones.add(i);
	}
	
	/**
	 * Método para añadir una instrucción sin valor al 
	 * vector de instrucciones
	 * @param o Orden de la instrucción
	 */
	public void emite (String o){
		Instruccion i = new Instruccion(o);
		instrucciones.add(i);
	}
	
	/**
	 * Método que añade un error al vector de errores
	 * @param s String con el nuevo error
	 */
	public void añadeError (String s){
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