import java.util.*;

/**
 * <p>Título: </p>
 * <p>Descripción: </p>
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
   * Crea la interfaz gráfica
   */
  public Main() {
    CompiladorGUIImp ventani= new CompiladorGUIImp(this);
  }

  /**
   * Método principal que crea la tabla de símbolos y el analizador léxico
   * y se los pasa por parámetro al analizador sintáctico cuando lo crea.
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