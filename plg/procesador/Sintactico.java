package procesador;

import java.io.BufferedReader;
import tablaSimbolos.TablaSimbolos;

//import procesador.procesador.info.token.token;

public class Sintactico{
	
	Codigo codigo;
	Lexico lexico;
	TablaSimbolos TS;
	
	public Sintactico(BufferedReader fuente, TablaSimbolos T) throws Exception{
		
		codigo = new Codigo(); 
		lexico = new Lexico(fuente);		
		TS = T;
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
	
	public boolean IAsig() throws Exception{
		
		boolean errDeIAsig = false; 
		boolean errDeExp = false;
		String lexDeIden;
		lexDeIden = lexico.reconoce(Tipos.TKIDEN);
		lexico.reconoce(Tipos.ASIGN);
		errDeExp = Exp();
		errDeIAsig = errDeExp || (!ts.existeID(lexDeIden));
		codigo.genIns("desapila_dir", ts.dirID(lexDeIden));
		return errDeIAsig;
	}
	
	public boolean Exp() throws Exception{
		
		boolean  errDeExp = false;
		boolean errDeTerm = false;
		boolean errDeRExp = false;
		errDeTerm = Term();
		errDeRExp = RExp();
		errDeExp = errDeTerm || errDeRExp;
		return errDeExp;

	}
	
	public String OpAd() throws Exception{

		String opDeOpAd = "";
		opDeOpAd = lexico.reconoce(Tipos.TKSUM);
		return opDeOpAd;

	}
	
	public String OpMul() throws Exception{
		
		String opDeOpMul = "";
		opDeOpMul = lexico.reconoce(Tipos.TKMUL);
		return opDeOpMul;

	}
	
	public boolean RExp() throws Exception{
		
		boolean errDeRExp = false;
		boolean errDeTerm = false;
		boolean errDeRExp1 = false;
		String opDeOpAd = "";
		
		if (lexico.getTokenPreanalisis() == Tipos.TKSUM){
			opDeOpAd = OpAd();
			errDeTerm = Term();
			genOpAd(opDeOpAd);
			errDeRExp1 = RExp();
			errDeRExp = errDeTerm || errDeRExp1;
		}
		else{
			errDeRExp = false;
		}
		return errDeRExp;

	}
	
	public boolean Term() throws Exception{
		
		boolean errDeTerm = false, errDeFact = false, errDeRTerm = false;
		
		errDeFact = Fact();
		errDeRTerm = RTerm();
		errDeTerm = errDeFact || errDeRTerm;
		
		return errDeTerm;

	}
	
	public boolean RTerm() throws Exception{
		
		boolean errDeRTerm = false, errDeRTerm1 = false, errDeFact = false;
		String opDeOpMul = "";
		
		if (lexico.getTokenPreanalisis() == Tipos.TKMUL){
			opDeOpMul = OpMul();
			errDeFact = Fact();
			genOpMul(opDeOpMul);
			errDeRTerm1 = RTerm();
			errDeRTerm = errDeFact || errDeRTerm1;
		}
		else
			errDeRTerm = false;
		
		return errDeRTerm;

	}
	
	public boolean Fact() throws Exception{
		
		boolean errDeFact;
		String lexAux;
		
		if (lexico.getTokenPreanalisis() == Tipos.TKNUM){
			lexAux = lexico.reconoce(Tipos.TKNUM);
			errDeFact = false;
			codigo.genIns("apila", valorDe(lexAux));
		}
		else
			if (lexico.getTokenPreanalisis() == Tipos.TKIDEN){
				lexAux = lexico.reconoce(Tipos.TKIDEN);
				errDeFact = !ts.existeID(lexAux);
				codigo.genIns("apila-dir", ts.dirID(lexAux));
			}
			else{
				lexico.reconoce(Tipos.PARAP);
				errDeFact = Exp();
				lexico.reconoce(Tipos.PARCIE);
			}
		
		return errDeFact;

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
	
	public int valorDe(String num){
		
		return (new Integer(num)).intValue();
		
	}
	
}
