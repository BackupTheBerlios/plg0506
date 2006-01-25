package procesador;

import java.io.RandomAccessFile;
import tablaSimbolos.TablaSimbolos;



public class Sintactico{
	
	Codigo codigo;
	Lexico lexico;
	TablaSimbolos TS;
	int dir;
	
	public Sintactico(RandomAccessFile fuente, TablaSimbolos T) throws Exception{
		codigo = new Codigo(); 
		lexico = new Lexico(fuente);		
		TS = T;
		dir = 0;
	}

	public void startParsing() throws Exception{
		System.out.println("Start");
		Prog();
		codigo.muestraCodigo();
	}

		
	public boolean Prog() throws Exception{
		
		System.out.println("Prog");
		boolean errDeProg = true;
		Atributos atrDeDecs;
		Atributos atrDeIs;
		atrDeDecs = Decs();
		atrDeIs = Is();
		errDeProg = atrDeDecs.getErr() || atrDeIs.getErr();
		return errDeProg;
	
	}
	
	public Atributos Decs() throws Exception{
		
		System.out.println("Decs");
		Atributos atrDeDecs;
		Atributos atrDeDec;
		Atributos a = new Atributos();
		boolean errDeDecs = false;
		atrDeDec = Dec();
		lexico.lexer();
		if (lexico.reconoce(Tipos.TKCUA)){
			errDeDecs = atrDeDec.getErr() || errDeDecs;
			a.setErr(errDeDecs);
			a.setTipo("");
			return a;
		}
		else{
			if (lexico.reconoce(Tipos.TKPYCOMA)){
				atrDeDecs = Decs();
				errDeDecs = atrDeDec.getErr() || atrDeDecs.getErr();
				a.setErr(errDeDecs);
				a.setTipo("");
				return a;
			}
			else{
				errDeDecs = true;
			}
		}
		a.setErr(errDeDecs);
		a.setTipo("");
		return a;
	}

	public Atributos Dec() throws Exception{

		System.out.println("Dec");
		boolean errDeDec;
		Atributos a = new Atributos();
		String t = "";
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKINT) || lexico.reconoce(Tipos.TKBOOL)){
			t = tk.getLexema();
			tk = lexico.lexer();
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKIDEN)){
				String i = tk.getLexema();
				errDeDec = TS.existeID(i,t);
				if (!errDeDec){
					TS.agnadeID(i,t);
				}
			}
			else{
				errDeDec = true;
			}
		}
		else{
			errDeDec = true;
		}
		a.setErr(errDeDec);
		a.setTipo(t);
		return a;
	}	
	
	public Atributos Is() throws Exception{
		
		System.out.println("Is");
		Atributos atrDeIs;
		Atributos atrDeI;
		Atributos a = new Atributos();
		boolean errDeIs = false;
		atrDeI = I();
		//Token tk = lexico.lexer();
		//System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKPYCOMA)){
			atrDeIs = Is();
			errDeIs = atrDeI.getErr() || atrDeIs.getErr();
			a.setErr(errDeIs);
			a.setTipo(atrDeI.getTipo());
			return a;
		}
		else{
			if (lexico.reconoce(Tipos.TKFF)){
				errDeIs = false;	
			}
			else{
				errDeIs = true;
			}
		}
		a.setErr(errDeIs);
		a.setTipo(atrDeI.getTipo());
		return a;	
	}

	public Atributos I() throws Exception{

		System.out.println("I");
		Atributos a = new Atributos();
		Atributos atrDeIAsig;
		atrDeIAsig = IAsig();
		a.setErr(atrDeIAsig.getErr());
		a.setTipo(atrDeIAsig.getTipo());
		return a;
	}
	
	public Atributos IAsig() throws Exception{
		
		System.out.println("IAsig");
		Atributos  atrDeExpC = new Atributos();
		Atributos a = new Atributos();
		boolean errDeIAsig; 
		Token tk;
		String lex = "";
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKIDEN)){
			lex = tk.getLexema();
			tk = lexico.lexer();
			System.out.println(tk.muestraToken());
			if (lexico.reconoce(Tipos.TKASIGN)){
				atrDeExpC = ExpC();
				errDeIAsig = atrDeExpC.getErr() || !(TS.existeID(lex,atrDeExpC.getTipo()));
				codigo.genIns("desapila-dir",TS.dirID(lex,atrDeExpC.getTipo()));
			}
			else{
				errDeIAsig = true;
			}
		}
		else{
			errDeIAsig = true;
		}
		a.setErr(errDeIAsig);
		a.setTipo(atrDeExpC.getTipo());
		return a;
	}
	
	public Atributos ExpC() throws Exception{
		
		System.out.println("ExpC");
		
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		atrDeExp = Exp();
		atrDeRExpC = RExpC();
		Atributos a = new Atributos();
		boolean errDeExpC;
		errDeExpC = atrDeExp.getErr() || atrDeRExpC.getErr() || !(atrDeExp.getTipo() == atrDeRExpC.getTipo());
		a.setErr(errDeExpC);
		a.setTipo(atrDeExp.getTipo());
		return a;
	}
	
	public Atributos RExpC() throws Exception{
		
		System.out.println("RExpC");
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		Atributos atrDeExp;
		Atributos atrDeRExpC;
		Atributos a = new Atributos();
		boolean errDeRExpC;
		if (lexico.reconoce(Tipos.TKMAY) || lexico.reconoce(Tipos.TKMAYIG) || lexico.reconoce(Tipos.TKMEN) || lexico.reconoce(Tipos.TKMENIG) || lexico.reconoce(Tipos.TKIG) || lexico.reconoce(Tipos.TKDIF)){
			atrDeExp = Exp();
			genOpComp(tk.getLexema());
			atrDeRExpC = RExpC();
			errDeRExpC = atrDeExp.getErr() || atrDeRExpC.getErr() || !(atrDeRExpC.getTipo() == atrDeExp.getTipo());
			a.setErr(errDeRExpC);
			a.setTipo(atrDeExp.getTipo());
			return a;
		}
		else{
			errDeRExpC = false;
		}
		a.setErr(errDeRExpC);
		a.setTipo("");
		return a;
	}
	
	public Atributos Exp() throws Exception{
		
		System.out.println("Exp");
		Atributos atrDeTerm;
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		boolean errDeExp;
		atrDeTerm = Term();
		if (!atrDeTerm.getErr()){
			atrDeRExp = RExp();
			errDeExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("int")) && (!atrDeRExp.getTipo().equals("")));
		}
		else{
			atrDeTerm = TermB();
			if (!atrDeTerm.getErr()){
				atrDeRExp = RExp();
				errDeExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("bool")) && (!atrDeRExp.getTipo().equals("")));
			}
			else{
				errDeExp = true;
			}
		}	
		a.setErr(errDeExp);
		a.setTipo(atrDeTerm.getTipo());
		return a;
	}
		
	public Atributos RExp() throws Exception{
		System.out.println("RExp");
		Atributos atrDeTerm = new Atributos();
		Atributos atrDeRExp;
		Atributos a = new Atributos();
		boolean errDeRExp = false;
		if (!lexico.reconoce(Tipos.TKPYCOMA)){
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKSUMA) || lexico.reconoce(Tipos.TKRESTA)){
			 atrDeTerm = Term();
			 genOpAd(tk.getLexema());
			 atrDeRExp = RExp();
			 errDeRExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("int")) && (!atrDeRExp.getTipo().equals(""))); 
		}
		else{
			if (lexico.reconoce(Tipos.TKOR)){
				 atrDeTerm = TermB();
				 genOpOr();
				 atrDeRExp = RExp();
				 errDeRExp = atrDeTerm.getErr() || atrDeRExp.getErr() || ((!atrDeRExp.getTipo().equals("bool")) && (!atrDeRExp.getTipo().equals(""))); 
			}
			else{
				errDeRExp = false;
			}
		}
		}
		a.setErr(errDeRExp);
		a.setTipo(atrDeTerm.getTipo());
		return a;
	}
	
	public Atributos Term() throws Exception{
		
		System.out.println("Term");
		Atributos atrDeFact;
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		atrDeFact = Fact();
		atrDeRTerm = RTerm();
		boolean errDeTerm;
		errDeTerm = atrDeFact.getErr() || atrDeRTerm.getErr();
		a.setErr(errDeTerm);
		a.setTipo("int");
		return a;
	}
	
	public Atributos RTerm() throws Exception{
		
		System.out.println("RTerm");
		Atributos atrDeFact = new Atributos();
		Atributos atrDeRTerm;
		Atributos a = new Atributos();
		boolean errDeRTerm = false;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (!lexico.reconoce(Tipos.TKPYCOMA)){
			if (lexico.reconoce(Tipos.TKMULT) || lexico.reconoce(Tipos.TKDIV)){
				atrDeFact = Fact();
				genOpMul(tk.getLexema());
				atrDeRTerm = RTerm();
				errDeRTerm = atrDeFact.getErr() || atrDeRTerm.getErr() || ((!atrDeRTerm.getTipo().equals("int")) && (!atrDeRTerm.getTipo().equals(""))); 
			}
			else{
				errDeRTerm = false;
			}
		}
		a.setErr(errDeRTerm);
		a.setTipo(atrDeFact.getTipo());
		return a;
	}

	
	public Atributos TermB() throws Exception{
		
		System.out.println("TermB");
		Atributos atrDeNega;
		Atributos atrDeRTermB;
		Atributos a = new Atributos();
		atrDeNega = Nega();
		atrDeRTermB = RTermB();
		boolean errDeTermB;
		errDeTermB = atrDeNega.getErr() || atrDeRTermB.getErr();
		a.setErr(errDeTermB);
		a.setTipo("int");
		return a;
	}
	
	public Atributos RTermB() throws Exception{
		
		System.out.println("RTermB");
		Atributos atrDeNega = new Atributos();
		Atributos atrDeRTermB;
		Atributos a = new Atributos();
		boolean errDeRTermB;
		Token tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKAND)){
			 atrDeNega = Nega();
			 genOpAnd();
			 atrDeRTermB = RTermB();
			 errDeRTermB = atrDeNega.getErr() || atrDeRTermB.getErr() || ((!atrDeRTermB.getTipo().equals("bool")) && (!atrDeRTermB.getTipo().equals(""))); 
		}
		else{
			errDeRTermB = false;
		}
		a.setErr(errDeRTermB);
		a.setTipo(atrDeNega.getTipo());
		return a;
	}
	
	public Atributos Fact() throws Exception{
		
		System.out.println("Fact");
		Atributos a = new Atributos();
		boolean errDeFact = true;
		Atributos atrDeExp;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKNUM)){
			errDeFact = false;
			codigo. genIns("apila", Integer.decode(tk.getLexema()).intValue());
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				errDeFact = !TS.existeID(tk.getLexema(), "int");
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema(),"int"));
			}
			else{
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExp = Exp();
					Token tk2 = lexico.lexer();
					System.out.println(tk2.muestraToken());
					if (lexico.reconoce(Tipos.TKPCI)){
						errDeFact = atrDeExp.getErr() || !atrDeExp.getTipo().equals("int");
					}
					else{
						errDeFact = true;
					}
				}
				else{
					errDeFact = true;
				}
			}
		}
		a.setErr(errDeFact);
		a.setTipo("int");
		return a;
	}
	
	public Atributos Nega() throws Exception{
		
		System.out.println("Nega");
		Atributos atrDeClausula = new Atributos();
		Atributos a = new Atributos();
		boolean errDeNega;
		Token tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKNOT)){
			 atrDeClausula = Clausula();
			 genOpNot();
			 errDeNega = atrDeClausula.getErr(); 
		}
		else{
			atrDeClausula = Clausula();
			errDeNega = atrDeClausula.getErr();
		}
		a.setErr(errDeNega);
		a.setTipo(atrDeClausula.getTipo());
		return a;
	}
	
	public Atributos Clausula() throws Exception{
		
		System.out.println("Clausula");
		Atributos a = new Atributos();
		boolean errDeClausula = true;
		Atributos atrDeExp;
		Token tk;
		tk = lexico.lexer();
		System.out.println(tk.muestraToken());
		if (lexico.reconoce(Tipos.TKTRUE) || lexico.reconoce(Tipos.TKFALSE)){
			errDeClausula = false;
			// Revisar el intValue de true y false
			codigo. genIns("apila", Integer.decode(tk.getLexema()).intValue());
		}
		else {
			if (lexico.reconoce(Tipos.TKIDEN)){
				errDeClausula = !TS.existeID(tk.getLexema(), "bool");
				codigo.genIns("apila-dir",TS.dirID(tk.getLexema(),"bool"));
			}
			else{
				if (lexico.reconoce(Tipos.TKPAP)){
					atrDeExp = Exp();
					Token tk2 = lexico.lexer();
					System.out.println(tk2.muestraToken());
					if (lexico.reconoce(Tipos.TKPCI)){
						errDeClausula = atrDeExp.getErr() || !atrDeExp.getTipo().equals("bool");
					}
					else{
						errDeClausula = true;
					}
				}
				else{
					errDeClausula = true;
				}
			}
		}
		a.setErr(errDeClausula);
		a.setTipo("bool");
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
	
	public void genOpComp(String opDeOpComp){
		
		if (opDeOpComp == "<="){
			codigo.genIns("menor o igual");
		}	
		if (opDeOpComp == "<"){
				codigo.genIns("menor");
		}
		if (opDeOpComp == ">="){
			codigo.genIns("mayor o igual");
		}	
		if (opDeOpComp == ">"){
				codigo.genIns("mayor");
		}
		if (opDeOpComp == "="){
			codigo.genIns("igual");
		}	
		if (opDeOpComp == "!="){
				codigo.genIns("distinto");
		}
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
