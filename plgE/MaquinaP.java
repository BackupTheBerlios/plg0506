
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
 * M�todo que devuelve true si la ejecuci�n ha sido correcta
 * @return booleano
 */
public boolean sinFallo(){
  return sinFallo;
}

/**
 * Devuelve la pila de ejecuci�n para mostrarla
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
 * m�todo que inicializa la m�quina
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
 * M�todo que es llamado desde el main para comenzar la ejecuci�n del programa compilado
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
 * M�todo que toma los datos trata los fallos y ejecuta instrucciones
 * @return True si la ejecuci�n de la �ltima instrucci�n ha sido correcta
 */
  private boolean eject(){

    int Op1,Op2, valor;
    String instruc;
    Instruccion i= (Instruccion)instrucciones.get(PC);
    instruc=i.dameOrden();
    valor=i.dameValor();
    PC++;

    //Controlar errores de instrucci�n vac�a y esas cosas
    if(PC>instrucciones.size())
    {
      rA.a�adeError("Contador de programa mayor que el n�mero de instrucciones");
      sinFallo=false;
    }
    else if(instruc==null){
      rA.a�adeError("instrucci�n nula");
      sinFallo= false;
    }

    //ejecuci�n de las instrucciones
    else  if (instruc.equals("suma")){
      if (ST<1){
        rA.a�adeError("No hay suficientes operandos para realizar la suma");
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
        rA.a�adeError("No hay suficientes operandos para realizar la resta");
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
        rA.a�adeError("No hay suficientes operandos para realizar la multiplicaci�n");
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
        rA.a�adeError("No hay suficientes operandos para realizar la divisi�n");
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
        else{ rA.a�adeError("Imposible dividir por cero");
        sinFallo=false;
        }
      }
    }
    else if (instruc.equals("mod")){
      if (ST<1){
        rA.a�adeError("No hay suficientes operandos para realizar el m�dulo");
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
        rA.a�adeError("El m�s no indica el signo de ning�n operando");
        sinFallo=false;
      }
      else {
      }
    }
    else if (instruc.equals("menos")){
      if (ST<0){
        rA.a�adeError("El menos no modifica el signo de ning�n operando");
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
        rA.a�adeError("No hay suficientes operandos para realizar la comparaci�n >");
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
        rA.a�adeError("No hay suficientes operandos para realizar la comparaci�n <");
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
        rA.a�adeError("No hay suficientes operandos para realizar la comparaci�n ==");
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
        rA.a�adeError("No hay suficientes operandos para realizar la comparaci�n >=");
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
        rA.a�adeError("No hay suficientes operandos para realizar la comparaci�n <=");
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
        rA.a�adeError("No hay suficientes operandos para realizar la comparaci�n !=");
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
        rA.a�adeError("No hay suficientes operandos para realizar la operaci�n l�gica and");
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
        rA.a�adeError("No hay suficientes operandos para realizar la operaci�n l�gica or");
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
        rA.a�adeError("No hay operando para realizar la negaci�n");
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
          rA.a�adeError("Variable no inicializada");
          sinFallo= false;
        }
        else{
        pila.push(new Integer(dir));
        ST++;
        }
    }
    else if (instruc.equals("desapila-dir")){
      if (ST==-1) {
        rA.a�adeError("No se puede desapilar el elemento de la pila porque est� vac�a");
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