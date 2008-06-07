program prog;

type
	unrecord = record
			a:boolean;
			b:integer;
		end;
	otrorecord = record
			a:boolean;
			b:integer;
			c:array [0..5] of boolean;
		end;
var 
	unarray : array [0..9] of unrecord;
	otroarray : array [0..9] of otrorecord;
	i : integer;
	j : integer;

begin
	i := 0;

	unarray[0].a := true;
	otroarray[0].a := false;
	unarray[0].b := 0;

	while (i < 10) do
	begin
		j := 0;
		unarray[0].a:= not unarray[0].a;
		otroarray[i].a := not unarray[0].a;
		if (i > 0) then
			unarray[i].a := unarray[i-1].a or otroarray[i].a;
		unarray[i].b := i+1;
		otroarray[i].b := i * unarray[i].b;
		write(unarray[i].a);
		write(otroarray[i].a);

		write(unarray[i].b);
		write(otroarray[i].b);
		while (j < 6) do
		begin
			otroarray[i].c[j] := (j = i);
			write(otroarray[i].c[j]);
			j := j+1;
		end;
		i := i+1;
	end;


end
.


