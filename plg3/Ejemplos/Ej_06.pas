program prueba6;
type 
	tint0 =integer;
	tint1 = tint0;
	trecord=record
		bool:boolean;
		miarray: array[0..9] of tint1;
	end;
	tarray = array[0..9] of tint0;
	tarrayrec = array[0..2] of trecord;
var 
	a:tarrayrec;
	b:tarray;
	i: tint1;
begin
i := 0;
while (i<10) do begin
  a[2].miarray[i] := i*i;
  i := i + 1;
end;
b := a[2].miarray;
end .




