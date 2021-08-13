/*DROP FUNCTION public.persona2_update(integer, character varying, character varying, integer, character varying);
DROP FUNCTION public.persona2_insert(integer, character varying, character varying, integer, character varying);
DROP FUNCTION public.persona2_list();
drop table public.persona2;

create schema public;*/


CREATE TABLE public.persona2
(
  id serial NOT NULL primary key,
  lastname character varying(50),
  email character varying(30),
  name character varying(50),
  age int
);



CREATE OR REPLACE FUNCTION public.persona2_update(IN pid INT, IN pname VARCHAR(50), IN plastname VARCHAR(50), IN page INT, IN pemail VARCHAR(30))
  RETURNS INT
LANGUAGE plpgsql
AS $BODY$
BEGIN
    UPDATE public.persona2
    SET name = pname, lastname = plastname, age = page, email = pemail
    WHERE id = pid;
  RETURN pid;
END;
$BODY$;


CREATE OR REPLACE FUNCTION public.persona2_insert(OUT pid INT, IN pname VARCHAR(50), IN plastname VARCHAR(50), IN page INT, IN pemail VARCHAR(30))
  RETURNS INT
LANGUAGE plpgsql
AS $BODY$
BEGIN
    INSERT INTO public.persona2 (name, lastname, age, email) VALUES (pname, plastname, page, pemail); --RETURNING id INTO pid;
    pid := currval(pg_get_serial_sequence('public.persona2','id'));
END;
$BODY$;



CREATE OR REPLACE FUNCTION public.persona2_list()
  RETURNS refcursor AS
$BODY$
declare 
pcursor refcursor;
begin
	open pcursor for 
		select 
			id
			,name
			,lastname
			,age
			,email
		from public.persona2;
	return pcursor;
end;
$BODY$
  LANGUAGE plpgsql VOLATILE


select public.persona2_list();
FETCH ALL IN "<unnamed portal 4>";



select * from public.persona2