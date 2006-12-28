import java.util.*;

/**
 * <p>T�tulo: </p>
 * <p>Descripci�n: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Empresa: </p>
 * @author unascribed
 * @version 1.0
 */

public class Main {

  AnalizadorLexico lexico;
  analizadorSin sintac;
  tablaSimbolos ts;
  resultadoAnalisis rA;

  /**
   * Crea la interfaz gr�fica
   */
  public Main() {
    CompiladorGUIImp ventani= new CompiladorGUIImp(this);
  }

  /**
   * M�todo principal que crea la tabla de s�mbolos y el analizador l�xico
   * y se los pasa por par�metro al analizador sint�ctico cuando lo crea.
   * @param txt: ruta del fichero a compilar
   */
  public void compilar(String txt){
    ts = new tablaSimbolos ();
    lexico = new AnalizadorLexico(txt,ts);
    sintac = new analizadorSin (ts,lexico);
    rA = sintac.analizadorSintactico();
  }

  public static void main(String[] args) {
    Main main1 = new Main();
    Instruccion ins;
  }
}