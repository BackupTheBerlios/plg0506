program prog

type
array0 = array [0..9] of integer;

var 
arrInt: array0;
i: integer:


begin
	i := 2;
	arrInt[0] := 1;
	arrInt[1] := 1;
	while (i < 10)
	begin
		arrInt[i] := arrInt[i-1] + arrInt[i-2];
		i := i+1;
	end;

	i := 0;
	while (i < 10)
	begin
		write(arrInt[i]);
		i := i+1;
	end;
	
end
.
