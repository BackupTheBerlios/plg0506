

/**
 * <p>Título: </p>
 * <p>Descripción: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Empresa: </p>
 * @author sin atribuir
 * @version 1.0
 */

public class Constantes {
  static final int INT = 256;
  static final int BOOL =257;
  static final int OR=258;
  static final int AND= 259;
  static final int MAS = 260; // Op Unario
  static final int MENOS = 261; // Op Unario
  static final int NOT = 262;
  static final int SUMA = 263;
  static final int RESTA = 264;
  static final int MULTIPLICA = 265;
  static final int DIVIDE = 266;
  static final int MOD = 267;
  static final int MENOR = 268;
  static final int MAYOR = 269;
  static final int MENORIGUAL = 270;
  static final int MAYORIGUAL = 271;
  static final int IGUAL = 272;
  static final int DISTINTO = 273;
  static final int LLAVEAB = 274;
  static final int LLAVECERR = 275;
  static final int PARENTAB = 276;
  static final int PARENTCERR = 277;
  static final int PUNTOYCOMA = 278;
  static final int ERROR=279;
  static final int ID=280;
  static final int ASIG=281;
  static final int ENTERO=282;
  static final int TRUE=283;
  static final int FALSE=284;
  static final int END=285;

  public Constantes() {
  }
  /** Me traduce un codigo a un string para poder sacar mensajes de error legibles
   * @param cod : Codigo numerico
   * @return Cadena de texto entendible :) */
  public static String traduce(int cod)
  {
    String s;
    switch(cod)
    {
      case(Constantes.INT):s="INT"; break;
      case(Constantes.BOOL):s="BOOL"; break;
      case(Constantes.OR):s="OR"; break;
      case(Constantes.AND):s="AND"; break;
      case(Constantes.MAS):s="+ (Unario)"; break;
      case(Constantes.MENOS):s="- (Unario)"; break;
      case(Constantes.NOT):s="!"; break;  //cOMPROBAR SI ERA !
      case(Constantes.SUMA):s="+"; break;
      case(Constantes.RESTA):s="-"; break;
      case(Constantes.MULTIPLICA):s="*"; break;
      case(Constantes.DIVIDE):s="/"; break;
      case(Constantes.MOD):s="MOD"; break;
      case(Constantes.MENOR):s="<"; break;
      case(Constantes.MAYOR):s=">"; break;
      case(Constantes.MENORIGUAL):s="<="; break;
      case(Constantes.MAYORIGUAL):s=">="; break;
      case(Constantes.IGUAL):s="=="; break;
      case(Constantes.DISTINTO):s="!="; break;
      case (Constantes.LLAVEAB):s="{";break;
      case (Constantes.LLAVECERR):s="}";break;
      case(Constantes.PARENTAB):s="("; break;
      case(Constantes.PARENTCERR):s=")"; break;
      case(Constantes.PUNTOYCOMA):s=";"; break;
      case(Constantes.ID):s="ID"; break;
      case(Constantes.ERROR):s="ERROR"; break;
      case (Constantes.ENTERO):s="int"; break;
      case (Constantes.TRUE):s="true";break;
      case (Constantes.FALSE):s="false"; break;
      case (Constantes.END):s="end";break;
      default:s="El token introducido no se corresponde con ninguna constante.";break;

    }
    return s;
  }
}