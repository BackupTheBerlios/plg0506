program prueba7;
type
  tdni = array[0..7] of integer;
  persona = record
            dni: tdni;
	    mayor: boolean;
	    edad: integer;
          end;
var i : integer;
    j : integer;
    pepe : persona; 
    clase : array[0..5] of persona;
    encontrado : boolean;
begin
  j := 0; 
  while (j < 6) do begin
    i := 0;
    while (i < 8) do begin
      clase[j].dni[i] := i*2 + j*10;
      i := i + 1;
    end;
    j := j + 1;
  end;
  i := 0;
  while (i<8) do begin
    read(pepe.dni[i]);
    i := i + 1;
  end;
  encontrado := false;
  j := 0; 
  while (not encontrado and (j < 6)) do begin
    i := 0;
    encontrado := true;
    while (i < 8) do begin
      if (not(clase[j].dni[i] = pepe.dni[i])) then
         encontrado := false;
      i := i + 1;
    end;
    j := j + 1;
  end; 
end.
     
