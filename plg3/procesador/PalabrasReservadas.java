package procesador;

import java.util.TreeMap;

public class PalabrasReservadas {
	private TreeMap conjunto;
	
	void PalabrasReservadas(){
		conjunto = new TreeMap();
		String s = new String();
		
		//Se añaden al conjunto las Palabras Reservadas
		s = "INTEGER";
		conjunto.put(s,s);
		s = "BOOLEAN";
		conjunto.put(s,s);
		s = "BEGIN";
		conjunto.put(s,s);
		s = "END";
		conjunto.put(s,s);
		s = "PROGRAM";
		conjunto.put(s,s);
		s = "VAR";
		conjunto.put(s,s);
		s = "DIV";
		conjunto.put(s,s);
		s = "MOD";
		conjunto.put(s,s);		
		s = "AND";
		conjunto.put(s,s);
		s = "OR";
		conjunto.put(s,s);
		s = "NOT";
		conjunto.put(s,s);
		s = "TRUE";
		conjunto.put(s,s);
		s = "FALSE";
		conjunto.put(s,s);
	}
	
	public boolean esPalabraReservada(String s){
		if (conjunto.containsKey(s))
			return true;
		else
			return false;
	}
	
	public Token reconoceCategoria(String s){
		Token tk = new Token();
		tk.setLexema(s);
		if (s.equalsIgnoreCase("INTEGER"))
			tk.setCategoriaLexica(CategoriaLexica.TKINT);
		else if (s.equalsIgnoreCase("BOOLEAN"))
			tk.setCategoriaLexica(CategoriaLexica.TKBOOL);
		else if (s.equalsIgnoreCase("PROGRAM"))
			tk.setCategoriaLexica(CategoriaLexica.TKPROGRAM);
		else if (s.equalsIgnoreCase("BEGIN"))
			tk.setCategoriaLexica(CategoriaLexica.TKBEGIN);
		else if (s.equalsIgnoreCase("END"))
			tk.setCategoriaLexica(CategoriaLexica.TKEND);
		else if (s.equalsIgnoreCase("VAR"))
			tk.setCategoriaLexica(CategoriaLexica.TKVAR);
		else if (s.equalsIgnoreCase("TRUE"))
			tk.setCategoriaLexica(CategoriaLexica.TKTRUE);
		else if (s.equalsIgnoreCase("FALSE"))
			tk.setCategoriaLexica(CategoriaLexica.TKFALSE);
		else if (s.equalsIgnoreCase("AND"))
			tk.setCategoriaLexica(CategoriaLexica.TKAND);
		else if (s.equalsIgnoreCase("OR"))
			tk.setCategoriaLexica(CategoriaLexica.TKOR);
		else if (s.equalsIgnoreCase("NOT"))
			tk.setCategoriaLexica(CategoriaLexica.TKNOT);
		else if (s.equalsIgnoreCase("DIV"))
			tk.setCategoriaLexica(CategoriaLexica.TKDIV);
		else if (s.equalsIgnoreCase("MOD"))
			tk.setCategoriaLexica(CategoriaLexica.TKMOD);
		return tk;
	}

}
