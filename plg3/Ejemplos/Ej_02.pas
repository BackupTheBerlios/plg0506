program prog;

type
array0 = array [0..5] of boolean;
reg0 = record 
	campo0: boolean;
	campo1: integer;
	end;

var 
arrBool: array0;
regComp: reg0;

begin
	arrBool[0] := false;
	arrBool[1] := false;
	arrBool[2] := false;
	arrBool[3] := false;
	arrBool[4] := false;
	arrBool[5] := false;

	if (not arrBool[0])
	then 
		regComp.campo0 := true;

	arrBool[0] := true;

	if (not arrBool[1])
		 regComp.campo1 := 5;
end
.
