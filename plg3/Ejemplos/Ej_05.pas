program prog;

type
	unrecord = record
			a:boolean;
			b:integer;
		end;
var 
	unarray : array [0..9] of unrecord;
	otroarray : array [0..9] of unrecord;
	i : integer;
begin
	i := 0;

	unarray[0].a := true;

	while (i < 10) do
	begin
		otroarray[i].a := not unarray[0].a;
		if (i > 0) then
			unarray[i].a := unarray[i-1].a or otroarray[i].a;
		i := i+1;
	end;


end
.


