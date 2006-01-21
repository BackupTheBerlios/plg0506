package procesador;

import java.io.BufferedReader;
import tablaSimbolos.TablaSimbolos;

//import procesador.procesador.info.token.token;

public class Sintactico{
	
	Codigo codigo;
	Lexico lexico;
	TablaSimbolos TS;
	int dir;
	
	public Sintactico(BufferedReader fuente, TablaSimbolos T) throws Exception{
		
		codigo = new Codigo(); 
		lexico = new Lexico(fuente);		
		TS = T;
		dir = 0;
	}

	public void startParsing() throws Exception{
		Prog();
		codigo.muestraCodigo();
	}

		
	public boolean Prog() throws Exception{
		
		boolean errDeProg;
		boolean errDeDecs;
		boolean errDeIs;	
		errDeDecs = Decs();
		codigo.inicializaCodigo();
		lexico.reconoce(Tipos.TKPYCOMA);
		errDeIs = Is();
		errDeProg = errDeDecs || errDeIs;
		return errDeProg;
	}
	
	public boolean Decs() throws Exception{
		
		boolean errDeDecs;
		boolean errDeDecs1;
		boolean errDeDec;
		errDeDec = Dec();
		Token tk;
		Token aux = new Token();
		tk = lexico.lexer(Tipos.TKPYCOMA);
		/*
		 * Si no he reconocido ';' es que hay que aplicar Decs::=Dec.
		 * Sino, aplico Decs = Decs;Dec.
		 */
		if (tk.equals(aux)){
			errDeDecs = errDeDec;
		}
		else{
			errDeDecs1 = Decs();
			errDeDecs = errDeDecs1 || errDeDec;
		}
		return errDeDecs;
	}
	
	public boolean Dec() throws Exception{
		
		boolean errDeDec = false;
		String lexDeIden;
		String tipoDeIden;
		Token tk;
		Token aux = new Token();
		tk = lexico.lexer(Tipos.TKBOOL);
		if (!tk.equals(aux)){
			tk = lexico.lexer(Tipos.TKIDEN);
			lexDeIden = tk.getLexema();
			tipoDeIden = "bool";
			errDeDec = TS.existeID(lexDeIden,tipoDeIden);
			TS.agnadeID(lexDeIden,tipoDeIden);	
		}
		else{
			tk = lexico.lexer(Tipos.TKINT);
			if (!tk.equals(aux)){
				tk = lexico.lexer(Tipos.TKIDEN);
				lexDeIden = tk.getLexema();
				tipoDeIden = "int";
				errDeDec = TS.existeID(lexDeIden,tipoDeIden);
				TS.agnadeID(lexDeIden,tipoDeIden);
			}
		}
		dir ++;
		return errDeDec;
	}
	
	public boolean Is() throws Exception{
		boolean errDeIs;
		boolean errDeIs1;
		boolean errDeI;
		errDeI = I();
		Token tk;
		Token aux = new Token();
		tk = lexico.lexer(Tipos.TKPYCOMA);
		/*
		 * Si no he reconocido ';' es que hay que aplicar Decs::=Dec.
		 * Sino, aplico Decs = Decs;Dec.
		 */
		if (tk.equals(aux)){
			errDeIs = errDeI;
		}
		else{
			errDeIs1 = Is();
			errDeIs = errDeIs1 || errDeI;
		}
		return errDeIs;
	}

	public boolean I() throws Exception{

		boolean errDeI;
		errDeI = IAsig();
		return errDeI;
	}
	
	public Atributos IAsig() throws Exception{
		
		Atributos a = new Atributos();
		boolean errDeIAsig = false; 
		Atributos atrExp = new Atributos();
		String lex;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKIDEN);
		lex = tk.getLexema();
		if (!tk.equals(aux)){
			tk = lexico.getNextToken();
			tk = lexico.lexer(Tipos.TKASIGN);
			atrExp = Exp();
			errDeIAsig = atrExp.getErr() || !TS.existeID(lex,atrExp.getTipo()); 
			codigo.genIns("desapila-dir", TS.dirID(lex,atrExp.getTipo()));
		}
		a.setErr(errDeIAsig);
		a.setTipo(atrExp.getTipo());
		return a;
	}
	
	public Atributos Exp() throws Exception{
		
		Atributos a = new Atributos();
		boolean errDeExp = false;
		String tipoExp = "";
		Atributos atrTerm;
		Atributos atrRExp;
		atrTerm = Term();
		if (atrTerm.getErr()==true){
			atrTerm = TermB();
		}
		atrRExp = RExp();
		errDeExp = atrTerm.getErr() || atrRExp.getErr() || !(atrRExp.getTipo().equals(atrTerm.getTipo()) || (atrRExp.getTipo().equals("")));
		tipoExp = atrTerm.getTipo();
		a.setErr(errDeExp);
		a.setTipo(tipoExp);
		return a;
	}
		
	public Atributos RExp() throws Exception{
		Atributos a = new Atributos();
		boolean errDeRExp = false;
		String tipoRExp = "";
		Atributos atrTerm;
		Atributos atrRExp;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKSUMA);
		if (!tk.equals(aux)){
			atrTerm = Term();
			genOpAd(tk.getLexema());
			atrRExp = RExp();
			errDeRExp = atrTerm.getErr() || atrRExp.getErr() || !(atrRExp.getTipo().equals("int") || atrRExp.getTipo().equals(""));
			tipoRExp = atrTerm.getTipo();		
		}
		else{
			tk = lexico.lexer(Tipos.TKRESTA);
			if (!tk.equals(aux)){
				atrTerm = Term();
				genOpAd(tk.getLexema());
				atrRExp = RExp();
				errDeRExp = atrTerm.getErr() || atrRExp.getErr() || !(atrRExp.getTipo().equals("int") || atrRExp.getTipo().equals(""));
				tipoRExp = atrTerm.getTipo();		
			}
			else{
				tk = lexico.lexer(Tipos.TKOR);
				if (!tk.equals(aux)){
					atrTerm = Term();
					genOpAd(tk.getLexema());
					atrRExp = RExp();
					errDeRExp = atrTerm.getErr() || atrRExp.getErr() || !(atrRExp.getTipo().equals("bool") || atrRExp.getTipo().equals(""));
					tipoRExp = atrTerm.getTipo();		
				}
				else{
					errDeRExp = false;
					tipoRExp = "";
				}
			}	
		}
		a.setErr(errDeRExp);
		a.setTipo(tipoRExp);
		return a;
	}
	
	public Atributos Term() throws Exception{
		Atributos a = new Atributos();
		boolean errDeTerm = false;
		String tipoTerm = "";
		Atributos atrFact;
		Atributos atrRTerm;
		atrFact = Fact();
		atrRTerm = RTerm();
		errDeTerm = atrFact.getErr() || atrRTerm.getErr();
		tipoTerm = atrFact.getTipo();
		a.setErr(errDeTerm);
		a.setTipo(tipoTerm);
		return a;
	}
	
	public Atributos RTerm() throws Exception{
		Atributos a = new Atributos();
		boolean errDeRTerm = false;
		String tipoRTerm = "";
		Atributos atrFact;
		Atributos atrRTerm;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKMULT);
		if (!tk.equals(aux)){
			atrFact = Fact();
			tk = lexico.getNextToken();
			genOpMul(tk.getLexema());
			atrRTerm = RTerm();
			errDeRTerm = atrFact.getErr() || atrRTerm.getErr() || !(atrRTerm.getTipo().equals("int") || atrRTerm.getTipo().equals(""));
			tipoRTerm = atrRTerm.getTipo();		
		}
		else{
			tk = lexico.lexer(Tipos.TKDIV);
			if (!tk.equals(aux)){
				atrFact = Fact();
				tk = lexico.getNextToken();
				genOpMul(tk.getLexema());
				atrRTerm = RTerm();
				errDeRTerm = atrFact.getErr() || atrRTerm.getErr() || !(atrRTerm.getTipo().equals("int") || atrRTerm.getTipo().equals(""));
				tipoRTerm = atrRTerm.getTipo();		
			}
			else{
				errDeRTerm = false;
				tipoRTerm = "";
			}
		}
		a.setErr(errDeRTerm);
		a.setTipo(tipoRTerm);
		return a;
	}

	
	public Atributos TermB() throws Exception{
		Atributos a = new Atributos();
		boolean errDeTermB = false;
		String tipoTermB = "";
		Atributos atrNega;
		Atributos atrRTermB;
		atrNega = Nega();
		atrRTermB = RTermB();
		errDeTermB = atrNega.getErr() || atrRTermB.getErr();
		tipoTermB = atrNega.getTipo();
		a.setErr(errDeTermB);
		a.setTipo(tipoTermB);
		return a;
	}
	
	public Atributos RTermB() throws Exception{
		Atributos a = new Atributos();
		boolean errDeRTermB = false;
		String tipoRTermB = "";
		Atributos atrNega;
		Atributos atrRTermB;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKAND);
		if (!tk.equals(aux)){
			atrNega = Nega();
			genOpAnd();
			atrRTermB = RTermB();
			errDeRTermB = atrNega.getErr() || atrRTermB.getErr() || !(atrRTermB.getTipo().equals("bool") || atrRTermB.getTipo().equals(""));
			tipoRTermB = atrRTermB.getTipo();		
		}
		else{
			errDeRTermB = false;
			tipoRTermB = "";
		}
		a.setErr(errDeRTermB);
		a.setTipo(tipoRTermB);
		return a;
	}
	
	public Atributos Fact() throws Exception{
		Atributos a = new Atributos();
		boolean errDeFact = false;
		String tipoFact = "";
		Atributos atrExp;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKNUM);
		if (!tk.equals(aux)){
			errDeFact = false;
			tipoFact = "int";
			codigo.genIns("apila",Integer.decode(tk.getLexema()).intValue());
		}
		else{
			tk = lexico.lexer(Tipos.TKIDEN);
			if (!tk.equals(aux)){
				errDeFact = !TS.existeID(tk.getLexema(),"int");
				tipoFact = "int";
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema(),"int"));
			}
			else{
				tk = lexico.lexer(Tipos.TKPAP);
				if (!tk.equals(aux)){
					atrExp = Exp();
					tipoFact = atrExp.getTipo();
					errDeFact = atrExp.getErr() || (!atrExp.getTipo().equals("int"));
					tk = lexico.getNextToken(); 
					tk = lexico.lexer(Tipos.TKPCI);
				}
			}
		}
		a.setErr(errDeFact);
		a.setTipo(tipoFact);
		return a;
	}
	
	public Atributos Nega() throws Exception{
		Atributos a = new Atributos();
		boolean errDeNega = false;
		String tipoNega = "";
		Atributos atrClausula;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKNOT);
		if (!tk.equals(aux)){
			atrClausula = Clausula();
			errDeNega = atrClausula.getErr();
			tipoNega = atrClausula.getTipo();
			genOpNot();
		}
		else{
			atrClausula = Clausula();
			errDeNega = atrClausula.getErr();
			tipoNega = atrClausula.getTipo();
		}
		a.setErr(errDeNega);
		a.setTipo(tipoNega);
		return a;
	}
	
	public Atributos Clausula() throws Exception{
		Atributos a = new Atributos();
		boolean errDeClausula = false;
		String tipoClausula = "";
		Atributos atrExp;
		Token tk;
		Token aux = new Token();
		tk = lexico.getNextToken();
		tk = lexico.lexer(Tipos.TKFALSE);
		if (!tk.equals(aux)){
			errDeClausula = false;
			tipoClausula = "bool";
			codigo.genIns("apila",0);
		}
		else{
			tk = lexico.lexer(Tipos.TKTRUE);
			if (!tk.equals(aux)){
				errDeClausula = true;
				tipoClausula = "bool";
				codigo.genIns("apila",1);
			}
			else{
				tk = lexico.lexer(Tipos.TKIDEN);
				if (!tk.equals(aux)){
					errDeClausula = !TS.existeID(tk.getLexema(),"bool");
					tipoClausula = "bool";
					codigo.genIns("apila-dir",TS.dirID(tk.getLexema(),"bool"));
				}
				else{
					tk = lexico.lexer(Tipos.TKPAP);
					if (!tk.equals(aux)){
						atrExp = Exp();
						tipoClausula = atrExp.getTipo();
						errDeClausula = atrExp.getErr() || (!atrExp.getTipo().equals("bool"));
						tk = lexico.getNextToken(); 
						tk = lexico.lexer(Tipos.TKPCI);
						
					}
				}
			}
		}
		a.setErr(errDeClausula);
		a.setTipo(tipoClausula);
		return a;
	}
	
	public void genOpAd(String opDeOpAd){
		
		if (opDeOpAd == "+")
			codigo.genIns("suma");
		else
			codigo.genIns("resta");	
	}
	
	public void genOpMul(String opDeOpMul){
		
		if (opDeOpMul == "*")
			codigo.genIns("multiplica");
		else
			codigo.genIns("divide");
		
	}

	public void genOpAnd(){

		codigo.genIns("and");
		
	}

	public void genOpOr(){

		codigo.genIns("or");
		
	}

	public void genOpNot(){

		codigo.genIns("not");
		
	}

}
