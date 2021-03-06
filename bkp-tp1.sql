PGDMP     #    1                y            tp1-daw    13.1    13.1      �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16508    tp1-daw    DATABASE     m   CREATE DATABASE "tp1-daw" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'English_United States.1252';
    DROP DATABASE "tp1-daw";
                postgres    false            �            1255    16693     available_to_visit(bigint, date)    FUNCTION     �  CREATE FUNCTION public.available_to_visit(museumid bigint, day date) RETURNS TABLE(t time without time zone, vacations integer)
    LANGUAGE plpgsql
    AS $$
	DECLARE 
		currentHour TIME;
		closingHour TIME;
		visitorsAtTime INTEGER;
		minutesBetweenVisits INTEGER;
		
		booked INTEGER:=0;
		strInterval TEXT;
		
	BEGIN
		SELECT 
			is_museum_open_at_day.opensAt,
			is_museum_open_at_day.closesAt,
			is_museum_open_at_day.visitorsAtTime, 
			is_museum_open_at_day.minutesBetweenVisits 
			FROM is_museum_open_at_day(museumId, day)
			INTO currentHour, closingHour, visitorsAtTime, minutesBetweenVisits;
			
		LOOP
			SELECT visitors FROM booked_visitors(museumId, day) INTO booked WHERE times = currentHour;
			
			IF NOT FOUND THEN
				booked:=0;
			END IF;
		
			RETURN QUERY SELECT currentHour, visitorsAtTime - booked ;
			currentHour:= currentHour + (minutesBetweenVisits || ' minutes')::interval;
		
			EXIT WHEN currentHour > closingHour;
		END LOOP;
	END;
$$;
 D   DROP FUNCTION public.available_to_visit(museumid bigint, day date);
       public          postgres    false            �            1255    16657    booked_visitors(bigint, date)    FUNCTION     h  CREATE FUNCTION public.booked_visitors(museumid bigint, day date) RETURNS TABLE(visitors integer, times time without time zone)
    LANGUAGE sql
    AS $$ 
	SELECT 
		visitors,
		schedule_time
		FROM schedule
		WHERE museum_id = museumId
			AND schedule_date = day
			AND EXTRACT(isodow FROM schedule_date::TIMESTAMP) = EXTRACT(isodow FROM day::TIMESTAMP)
$$;
 A   DROP FUNCTION public.booked_visitors(museumid bigint, day date);
       public          postgres    false            �            1255    16656 #   is_museum_open_at_day(bigint, date)    FUNCTION     l  CREATE FUNCTION public.is_museum_open_at_day(museumid bigint, day date) RETURNS TABLE(id bigint, opensat time without time zone, closesat time without time zone, visitorsattime integer, minutesbetweenvisits integer, dayofweek integer)
    LANGUAGE sql
    AS $$
	SELECT DISTINCT museum.id, museum.opens_at, museum.closes_at, museum.visitors_at_time, minutes_between_visits, museum_working_days.day_of_week
		FROM museum 
		INNER JOIN museum_working_days ON museum_working_days.museum_id = museum.id 
		WHERE museum_id = museumId AND museum_working_days.day_of_week = EXTRACT(isodow FROM day::TIMESTAMP)
		LIMIT 1
	;
$$;
 G   DROP FUNCTION public.is_museum_open_at_day(museumid bigint, day date);
       public          postgres    false            �            1259    16509    email_sender_credentials    TABLE     �   CREATE TABLE public.email_sender_credentials (
    email character varying(255) NOT NULL,
    pass character varying(255) NOT NULL
);
 ,   DROP TABLE public.email_sender_credentials;
       public         heap    postgres    false            �            1259    16544    museum    TABLE     
  CREATE TABLE public.museum (
    id integer NOT NULL,
    name character varying(255),
    closes_at time without time zone NOT NULL,
    opens_at time without time zone NOT NULL,
    visitors_at_time integer NOT NULL,
    minutes_between_visits integer NOT NULL
);
    DROP TABLE public.museum;
       public         heap    postgres    false            �            1259    16560    museum_employee    TABLE     �   CREATE TABLE public.museum_employee (
    cpf character varying(11) NOT NULL,
    name character varying(100) NOT NULL,
    museum_id bigint
);
 #   DROP TABLE public.museum_employee;
       public         heap    postgres    false            �            1259    16542    museum_id_seq    SEQUENCE     �   CREATE SEQUENCE public.museum_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.museum_id_seq;
       public          postgres    false    202            �           0    0    museum_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.museum_id_seq OWNED BY public.museum.id;
          public          postgres    false    201            �            1259    16550    museum_working_days    TABLE     m   CREATE TABLE public.museum_working_days (
    museum_id bigint NOT NULL,
    day_of_week integer NOT NULL
);
 '   DROP TABLE public.museum_working_days;
       public         heap    postgres    false            �            1259    16604    schedule    TABLE     �   CREATE TABLE public.schedule (
    scheduler_email character varying(50) NOT NULL,
    schedule_date date NOT NULL,
    schedule_time time without time zone NOT NULL,
    visitors integer NOT NULL,
    museum_id bigint NOT NULL,
    code text NOT NULL
);
    DROP TABLE public.schedule;
       public         heap    postgres    false            �            1259    16612    visitor    TABLE     �   CREATE TABLE public.visitor (
    schedule_code text NOT NULL,
    cpf character varying(11) NOT NULL,
    name character varying(100) NOT NULL,
    ticket_type integer NOT NULL
);
    DROP TABLE public.visitor;
       public         heap    postgres    false            <           2604    16547 	   museum id    DEFAULT     f   ALTER TABLE ONLY public.museum ALTER COLUMN id SET DEFAULT nextval('public.museum_id_seq'::regclass);
 8   ALTER TABLE public.museum ALTER COLUMN id DROP DEFAULT;
       public          postgres    false    201    202    202            �          0    16509    email_sender_credentials 
   TABLE DATA           ?   COPY public.email_sender_credentials (email, pass) FROM stdin;
    public          postgres    false    200   .,       �          0    16544    museum 
   TABLE DATA           i   COPY public.museum (id, name, closes_at, opens_at, visitors_at_time, minutes_between_visits) FROM stdin;
    public          postgres    false    202   s,       �          0    16560    museum_employee 
   TABLE DATA           ?   COPY public.museum_employee (cpf, name, museum_id) FROM stdin;
    public          postgres    false    204   �,       �          0    16550    museum_working_days 
   TABLE DATA           E   COPY public.museum_working_days (museum_id, day_of_week) FROM stdin;
    public          postgres    false    203   j-       �          0    16604    schedule 
   TABLE DATA           l   COPY public.schedule (scheduler_email, schedule_date, schedule_time, visitors, museum_id, code) FROM stdin;
    public          postgres    false    205   �-       �          0    16612    visitor 
   TABLE DATA           H   COPY public.visitor (schedule_code, cpf, name, ticket_type) FROM stdin;
    public          postgres    false    206   ,.       �           0    0    museum_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.museum_id_seq', 2, true);
          public          postgres    false    201            >           2606    16516 *   email_sender_credentials email_sender_pkey 
   CONSTRAINT     k   ALTER TABLE ONLY public.email_sender_credentials
    ADD CONSTRAINT email_sender_pkey PRIMARY KEY (email);
 T   ALTER TABLE ONLY public.email_sender_credentials DROP CONSTRAINT email_sender_pkey;
       public            postgres    false    200            D           2606    16564 $   museum_employee museum_employee_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.museum_employee
    ADD CONSTRAINT museum_employee_pkey PRIMARY KEY (cpf);
 N   ALTER TABLE ONLY public.museum_employee DROP CONSTRAINT museum_employee_pkey;
       public            postgres    false    204            @           2606    16549    museum museum_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.museum
    ADD CONSTRAINT museum_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.museum DROP CONSTRAINT museum_pkey;
       public            postgres    false    202            B           2606    16554 ,   museum_working_days museum_working_days_pkey 
   CONSTRAINT     ~   ALTER TABLE ONLY public.museum_working_days
    ADD CONSTRAINT museum_working_days_pkey PRIMARY KEY (museum_id, day_of_week);
 V   ALTER TABLE ONLY public.museum_working_days DROP CONSTRAINT museum_working_days_pkey;
       public            postgres    false    203    203            F           2606    16611    schedule schedule_pkey 
   CONSTRAINT     V   ALTER TABLE ONLY public.schedule
    ADD CONSTRAINT schedule_pkey PRIMARY KEY (code);
 @   ALTER TABLE ONLY public.schedule DROP CONSTRAINT schedule_pkey;
       public            postgres    false    205            H           2606    16619    visitor visitor_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.visitor
    ADD CONSTRAINT visitor_pkey PRIMARY KEY (schedule_code, cpf);
 >   ALTER TABLE ONLY public.visitor DROP CONSTRAINT visitor_pkey;
       public            postgres    false    206    206            J           2606    16565 .   museum_employee museum_employee_museum_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.museum_employee
    ADD CONSTRAINT museum_employee_museum_id_fkey FOREIGN KEY (museum_id) REFERENCES public.museum(id);
 X   ALTER TABLE ONLY public.museum_employee DROP CONSTRAINT museum_employee_museum_id_fkey;
       public          postgres    false    202    204    2880            I           2606    16555 6   museum_working_days museum_working_days_museum_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.museum_working_days
    ADD CONSTRAINT museum_working_days_museum_id_fkey FOREIGN KEY (museum_id) REFERENCES public.museum(id);
 `   ALTER TABLE ONLY public.museum_working_days DROP CONSTRAINT museum_working_days_museum_id_fkey;
       public          postgres    false    2880    203    202            K           2606    16620 "   visitor visitor_schedule_code_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.visitor
    ADD CONSTRAINT visitor_schedule_code_fkey FOREIGN KEY (schedule_code) REFERENCES public.schedule(code) ON DELETE CASCADE;
 L   ALTER TABLE ONLY public.visitor DROP CONSTRAINT visitor_schedule_code_fkey;
       public          postgres    false    205    206    2886            �   5   x���/J-ȩ��--N-uH�M���K���4����L�N�
NJO����� �	R      �   P   x�3��--N-U�-��L�,H�QHIUpJ,JJLN�K�4��20 "NÔ�̀��+%Q�'�4��Y�%�ah R���� ű�      �   �   x�e�;B!��V�
���61&6�6�`Br���������[B$�$.3���-�Q��)@�uȆ������,1�Z�BK�f�����J˯��:��S�3�w�<���?O �K� n��������R�AA2+      �   -   x�ȹ 0İ�7L +�2���`îN�����>>>>>�I>��S      �   u   x���A
�0F�u�.�&�d�� ݄`�%:馧��KAxۏ׾�luzٺ���̭��8���x��� �Kp�8��{�����d�x�p�~W���US�v;=.'�c	@�"=;"���-�      �   D   x��54747���47�44�053113�4��)MN,�4��E�756042�033��L,���K������ �      