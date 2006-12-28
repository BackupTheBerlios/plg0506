
import java.util.*;
import java.math.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class MaquinaP {
  //Contador del programa
  private int PC;
  //Contador de pila
  private int ST;
  //Para
  private boolean H;
  //Contiene las instrucciones y almacena errores
  private resultadoAnalisis rA;
  //memoria de datos
  private Vector memoria;
  //instrucciones a ejecutar
  private Vector instrucciones;
  //Booleano que indica si ha habido un fallo o no
  private boolean sinFallo;
  //Pila
  private Stack pila;
  //Constructor
  public MaquinaP() {
    pila = new Stack();
    memoria= new Vector();


  }

/**
 * Método que devuelve true si la ejecución ha sido correcta
 * @return booleano
 */
public boolean sinFallo(){
  return sinFallo;
}

/**
 * Devuelve la pila de ejecución para mostrarla
 * @return la pila
 */
public Stack damePila(){
  return pila;
}

/**
 *Devuelve la memoria de datos para mostrarlos
 * @return un vector con la memoria
 */
public Vector dameMemoria(){
  return memoria;
}
/**
 * método que inicializa la máquina
 */
  public void reset(int numId){
    pila.clear();
    PC=0;
    ST=-1;
    H=false;
    memoria.clear();
    sinFallo= true;
    for (int i=0; i<=numId;i++){
      memoria.add(new Integer(Integer.MAX_VALUE));
    }

  }
/**
 * Método que es llamado desde el main para comenzar la ejecución del programa compilado
 * @param rAnali objeto que contiene la instrucciones y en el que se acumulan los errores.
 */
  public void ejecuta(resultadoAnalisis rAnali,int numId){
    rA= rAnali;
    instrucciones=rA.dameInstrucciones();
    reset(numId);
    sinFallo= true;
    while(eject()&&!H) {}
  }

/**
 * Método que toma los datos trata los fallos y ejecuta instrucciones
 * @return True si la ejecución de la última instrucción ha sido correcta
 */
  private boolean eject(){

    int Op1,Op2, valor;
    String instruc;
    Instruccion i= (Instruccion)instrucciones.get(PC);
    instruc=i.dameOrden();
    valor=i.dameValor();
    PC++;

    //Controlar errores de instrucción vacía y esas cosas
    if(PC>instrucciones.size())
    {
      rA.añadeError("Contador de programa mayor que el número de instrucciones");
      sinFallo=false;
    }
    else if(instruc==null){
      rA.añadeError("instrucción nula");
      sinFallo= false;
    }

    //ejecución de las instrucciones
    else  if (instruc.equals("suma")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la suma");
        sinFallo=false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        Op1= Op2+Op1;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("resta")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la resta");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        Op1= Op2-Op1;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("multiplica")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la multiplicación");
        sinFallo=false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        Op1= Op2*Op1;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("divide")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la división");
        sinFallo=false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op1!=0){
        Op1= Op2 /  Op1;
        pila.push(new Integer(Op1));
        ST--;
        }
        else{ rA.añadeError("Imposible dividir por cero");
        sinFallo=false;
        }
      }
    }
    else if (instruc.equals("mod")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar el módulo");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        Op1= Op2%Op1;
        pila.push(new Integer(Op1));
        ST--;
      }
    }

    else if (instruc.equals("mas")){
      if (ST<1){
        rA.añadeError("El más no indica el signo de ningún operando");
        sinFallo=false;
      }
      else {
      }
    }
    else if (instruc.equals("menos")){
      if (ST<0){
        rA.añadeError("El menos no modifica el signo de ningún operando");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op1= (-1)*Op1;
        pila.push(new Integer(Op1));
      }
    }
    else if (instruc.equals("mayor")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la comparación >");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op2>Op1)  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("menor")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la comparación <");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op2<Op1)  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("igual")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la comparación ==");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op2==Op1)  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("mayor-igual")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la comparación >=");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op2>=Op1)  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("menor-igual")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la comparación <=");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op2<=Op1)  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("distinto")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la comparación !=");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if (Op2!=Op1)  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("and")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la operación lógica and");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if ((Op2==1)&&(Op1==1))  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("or")){
      if (ST<1){
        rA.añadeError("No hay suficientes operandos para realizar la operación lógica or");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        Op2= ((Integer)pila.pop()).intValue();
        if ((Op2==1)||(Op1==1))  Op1=1;
        else Op1=0;
        pila.push(new Integer(Op1));
        ST--;
      }
    }
    else if (instruc.equals("not")){
      if (ST<0){
        rA.añadeError("No hay operando para realizar la negación");
        sinFallo= false;
      }
      else {
        Op1= ((Integer)pila.pop()).intValue();
        if (Op1==1)  Op1=0;
        else Op1=1;
        pila.push(new Integer(Op1));
      }
    }
    else if (instruc.equals("stop")){
      H=true;
    }
    else if (instruc.equals("apila")){

        pila.push(new Integer(valor));
        ST++;

    }
    else if(instruc.equals("apila-dir")){

        int dir= ((Integer)memoria.get(valor-1)).intValue();
        if (dir==Integer.MAX_VALUE){
          rA.añadeError("Variable no inicializada");
          sinFallo= false;
        }
        else{
        pila.push(new Integer(dir));
        ST++;
        }
    }
    else if (instruc.equals("desapila-dir")){
      if (ST==-1) {
        rA.añadeError("No se puede desapilar el elemento de la pila porque está vacía");
        sinFallo=false;
      }
     else{
      int dir;
      dir =((Integer)pila.pop()).intValue();
      memoria.set(valor-1, new Integer(dir));
      ST--;
     }
    }

    return sinFallo;
  }

}