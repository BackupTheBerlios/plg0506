program prog;

var 
	yotro : array  [0..1] of boolean;
	unomas : array  [0..1] of boolean;
	i : integer;
begin
	i := 0;
	unomas[0] := true;
	while (i < 2) do
	begin
		yotro[i] := not unomas[0];

		if (i > 0) then
			unomas[i] := unomas[(i-1)] or yotro[i];
		i := i+1;
	end;


end
.


