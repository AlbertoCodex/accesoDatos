PGDMP       2                {            BDVehiculos    16.1    16.1     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16397    BDVehiculos    DATABASE     �   CREATE DATABASE "BDVehiculos" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'English_United States.1252';
    DROP DATABASE "BDVehiculos";
                postgres    false            �           0    0    DATABASE "BDVehiculos"    COMMENT     9   COMMENT ON DATABASE "BDVehiculos" IS 'Práctica tema 4';
                   postgres    false    4802                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4            N           1247    16406    estado    TYPE     ]   CREATE TYPE public.estado AS ENUM (
    'fabricación',
    'en reparto',
    'preparado'
);
    DROP TYPE public.estado;
       public          postgres    false    4            �            1259    16398    vehiculo    TABLE     �   CREATE TABLE public.vehiculo (
    matricula character varying(8) NOT NULL,
    color character varying,
    modelo character varying
);
    DROP TABLE public.vehiculo;
       public         heap    postgres    false    4            �            1259    16423    camion    TABLE     q   CREATE TABLE public.camion (
    tornillos text[],
    estado_actual public.estado
)
INHERITS (public.vehiculo);
    DROP TABLE public.camion;
       public         heap    postgres    false    4    846    215            �            1259    16413    coche    TABLE     p   CREATE TABLE public.coche (
    tornillos text[],
    estado_actual public.estado
)
INHERITS (public.vehiculo);
    DROP TABLE public.coche;
       public         heap    postgres    false    215    4    846            �            1259    16418    moto    TABLE     o   CREATE TABLE public.moto (
    tornillos text[],
    estado_actual public.estado
)
INHERITS (public.vehiculo);
    DROP TABLE public.moto;
       public         heap    postgres    false    4    215    846            �          0    16423    camion 
   TABLE DATA           T   COPY public.camion (matricula, color, modelo, tornillos, estado_actual) FROM stdin;
    public          postgres    false    218   �       �          0    16413    coche 
   TABLE DATA           S   COPY public.coche (matricula, color, modelo, tornillos, estado_actual) FROM stdin;
    public          postgres    false    216   �       �          0    16418    moto 
   TABLE DATA           R   COPY public.moto (matricula, color, modelo, tornillos, estado_actual) FROM stdin;
    public          postgres    false    217   	       �          0    16398    vehiculo 
   TABLE DATA           <   COPY public.vehiculo (matricula, color, modelo) FROM stdin;
    public          postgres    false    215   &       )           2606    16404    vehiculo vehiculo_pkey 
   CONSTRAINT     [   ALTER TABLE ONLY public.vehiculo
    ADD CONSTRAINT vehiculo_pkey PRIMARY KEY (matricula);
 @   ALTER TABLE ONLY public.vehiculo DROP CONSTRAINT vehiculo_pkey;
       public            postgres    false    215            �      x������ � �      �   .   x�3162Tprv�����0�vtr��,(J-H,JL������ �c
(      �      x������ � �      �   "   x�3426Qptr�L�M,�����2����� ZOS     