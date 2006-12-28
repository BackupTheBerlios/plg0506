import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que implementa el analizador léxico.<p>
 * @author
 */
public class AnalizadorLexico {

	// Archivo a analizar;
	private BufferedReader in;
	// Puntero a la tabla de símbolos.
	private tablaSimbolos ts;
	// Buffer para guardar el lexema que se está leyendo actualmente.
	// La posición cero la utilizamos de centinela.
	private char[] bufferLex;
	// Puntero al último carácter leido.
	private int puntero;
	// Buffer que guarda el carácter actualmente procesado.
	private char bufferCar;
	// Estado en el que se encuentra el analizador léxico.
	private int estado;
	// Linea en la que estamos leyendo.
	private int linea;
	// Constantes que se corresponden con los estados en los que
	//se puede encontrar el analizador léxico. Equivalente a hacer un
	//enum en C.
	private static final int S0 = 0;
	private static final int S1 = 1;
	private static final int S2 = 2;
	private static final int S3 = 3;
	private static final int S4 = 4;
	private static final int S5 = 5;
	private static final int S6 = 6;
	private static final int S7 = 7;
	private static final int S8 = 8;
	private static final int S9 = 9;
	private static final int S10 = 10;
	private static final int S11 = 11;
	private static final int S12 = 12;
	private static final int S13 = 13;
	private static final int S14 = 14;
	private static final int S15 = 15;

	/**
	 * Constructora de la clase.
	 * @param fichero Archivo a analizar.
	 */
	public AnalizadorLexico(String fichero, tablaSimbolos ts) {
		try {
			in = new BufferedReader(new FileReader(fichero));
			bufferLex = new char[256];
			puntero = 0;
			linea = 1;
			//Dejamos el primer carácter en bufferCar.
			bufferCar = (char)in.read();
			this.ts = ts;
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Función principal del analizador léxico.
	 * @return Devuelve el siguiente token leido.
	 */
	public Object[] siguienteToken() {
		puntero = 0;
		estado = S0;
		boolean completado = false;
		Object[] token = new Object[2];
		while(!completado) {
			switch(estado) {
			case S0:
				if(bufferCar == (char)-1){
					token[0] = new Integer(Constantes.END);
					completado = true;
				}
				else if(bufferCar == '\t' || bufferCar == '\r' ||
						bufferCar == '\n' || bufferCar == ' ') {
					// Control de nueva linea de lectura.
					if(bufferCar == '\n')
						linea += 1;
					transita(S0);
					puntero = 0;
				}
				else if(bufferCar == '=')
					transita(S1);
				else if(bufferCar == '>')
					transita(S2);
				else if(bufferCar == '<')
					transita(S3);
				else if(bufferCar == '!')
					transita(S4);
				else if(bufferCar == '&')
					transita(S6);
				else if(bufferCar == '|')
					transita(S8);
				else if(bufferCar == '*' || bufferCar == '%' ||
						bufferCar == '0' || bufferCar == '{' ||
						bufferCar == '}' || bufferCar == '(' ||
						bufferCar == ')' || bufferCar == ';')
					transita(S10);
				else if((bufferCar >= 'A' && bufferCar <= 'Z') ||
						(bufferCar >= 'a' && bufferCar <= 'z'))
					transita(S11);
				else if(bufferCar == '+' || bufferCar == '-')
					transita(S12);
				else if(bufferCar >= '1' && bufferCar <= '9')
					transita(S13);
				else if(bufferCar == '/')
					transita(S14);
				else
					transita(S15);
				break;
			case S1:
				if(bufferCar == '=')
					transita(S5);
				else {
					token[0] = new Integer(Constantes.ASIG);
					completado = true;
				}
				break;
			case S2:
				if(bufferCar == '=')
					transita(S5);
				else {
					token[0] = new Integer(Constantes.MAYOR);
					completado = true;
				}
				break;
			case S3:
				if(bufferCar == '=')
					transita(S5);
				else {
					token[0] = new Integer(Constantes.MENOR);
					completado = true;
				}
				break;
			case S4:
				if(bufferCar == '=')
					transita(S5);
				else {
					token[0] = new Integer(Constantes.NOT);
					completado = true;
				}
				break;
			case S5:
				String rel = String.valueOf(bufferLex);
				rel = rel.substring(1,3);
				if(rel.equals((String)"==")) {
					token[0] = new Integer(Constantes.IGUAL);
					completado = true;
				}
				else if(rel.equals((String)">=")) {
					token[0] = new Integer(Constantes.MAYORIGUAL);
					completado = true;
				}
				else if(rel.equals((String)"<=")) {
					token[0] = new Integer(Constantes.MENORIGUAL);
					completado = true;
				}
				else if(rel.equals((String)"!=")) {
					token[0] = new Integer(Constantes.DISTINTO);
					completado = true;
				}
				break;
			case S6:
				if(bufferCar == '&')
					transita(S7);
				else {
					transita(S15);
				}
				break;
			case S7:
				token[0] = new Integer(Constantes.AND);
				completado = true;
				break;
			case S8:
				if(bufferCar == '|')
					transita(S9);
				else {
					transita(S15);
				}
				break;
			case S9:
				token[0] = new Integer(Constantes.OR);
				completado = true;
				break;
			case S10:
				if(bufferLex[1] == '*') {
					token[0] = new Integer(Constantes.MULTIPLICA);
					completado = true;
				}
				else if(bufferLex[1] == '%') {
					token[0] = new Integer(Constantes.MOD);
					completado = true;
				}
				else if(bufferLex[1] == '{') {
					token[0] = new Integer(Constantes.LLAVEAB);
					completado = true;
				}
				else if(bufferLex[1] == '}') {
					token[0] = new Integer(Constantes.LLAVECERR);
					completado = true;
				}
				else if(bufferLex[1] == '(') {
					token[0] = new Integer(Constantes.PARENTAB);
					completado = true;
				}
				else if(bufferLex[1] == ')') {
					token[0] = new Integer(Constantes.PARENTCERR);
					completado = true;
				}
				else if(bufferLex[1] == ';') {
					token[0] = new Integer(Constantes.PUNTOYCOMA);
					completado = true;
				}
				else if(bufferLex[1] == '0') {
					token[0] = new Integer(Constantes.ENTERO);
					token[1] = new Integer(0);
					completado = true;
				}
				break;
			case S11:
				if((bufferCar >= 'A' && bufferCar <= 'Z') ||
						(bufferCar >= 'a' && bufferCar <= 'z') ||
						(bufferCar >= '0' && bufferCar <= '9'))
					transita(S11);
				else {
					// Verifico si es una palabra clave.
					String caracs = String.valueOf(bufferLex);
					caracs = caracs.substring(1, puntero +1);
					if(caracs.equals((String)"true")) {
						token[0] = new Integer(Constantes.TRUE);
					}
					else if(caracs.equals((String)"false"))
							token[0] = new Integer(Constantes.FALSE);
					else if(caracs.equals((String)"int"))
							token[0] = new Integer(Constantes.INT);
					else if(caracs.equals((String)"bool"))
							token[0] = new Integer(Constantes.BOOL);
					else {
						token[0] = new Integer(Constantes.ID);
						if(!ts.existeID(caracs))
							ts.añadeID(caracs,linea);
						token[1] = caracs;
					}
					completado = true;
				}
				break;
			case S12:
				if(bufferCar >= '1' && bufferCar <= '9')
					transita(S13);
				else {
					if(bufferLex[1] == '+')
						token[0] = new Integer(Constantes.SUMA);
					else if(bufferLex[1] == '-')
						token[0] = new Integer(Constantes.RESTA);
					completado = true;
				}
				break;
			case S13:
				if(bufferCar >= '0' && bufferCar <= '9')
					transita(S13);
				else {
					token[0] = new Integer(Constantes.ENTERO);
					token[1] = new Integer(char_to_int());
					completado = true;
				}
				break;
			case S14:
				if(bufferCar == '/') {
					try {
						if(in.readLine() != null)
							linea += 1;
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
					}
					transita(S0);
					puntero = 0;
				}
				else {
					token[0] = new Integer(Constantes.DIVIDE);
					completado = true;
				}
				break;
			case S15:
				token[0] = new Integer(Constantes.ERROR);
				token[1] = new String("Carácter no válido: " + bufferLex[puntero]
					+ ", error en linea " + String.valueOf(linea));
				completado = true;
				break;
			}
		}
		return token;
	}

	/**
	 * Hace la trasición a otro estado.
	 * @param estado Estado al que ir.
	 */
	private void transita(int estado) {
		try {
			puntero += 1;
			bufferLex[puntero] = bufferCar;
			bufferCar = (char)in.read();
			this.estado = estado;
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Tranforma carácteres a un entero.
	 * @return La transformación a entero.
	 */
	private int char_to_int() {
		String caracs = String.valueOf(bufferLex);
		if(bufferLex[1] == '+')
			caracs = caracs.substring(2,puntero+1);
		else
			caracs = caracs.substring(1,puntero+1);
		return Integer.parseInt(caracs);
	}
}
