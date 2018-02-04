PGDMP         3                v            delivery    10.1    10.1 �    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           1262    16394    delivery    DATABASE     �   CREATE DATABASE delivery WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Spain.1252' LC_CTYPE = 'Spanish_Spain.1252';
    DROP DATABASE delivery;
             delivery    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
             postgres    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                  postgres    false    3                        3079    12924    plpgsql 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;
    DROP EXTENSION plpgsql;
                  false            �           0    0    EXTENSION plpgsql    COMMENT     @   COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';
                       false    1            �            1259    16478    CARGO    TABLE     Y   CREATE TABLE "CARGO" (
    "ID" integer NOT NULL,
    "DESCRIPCION" character varying
);
    DROP TABLE public."CARGO";
       public         postgres    false    3            �            1259    16476    CARGO_ID_seq    SEQUENCE        CREATE SEQUENCE "CARGO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."CARGO_ID_seq";
       public       postgres    false    3    209            �           0    0    CARGO_ID_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE "CARGO_ID_seq" OWNED BY "CARGO"."ID";
            public       postgres    false    208            �            1259    16722    CLIENTE    TABLE     �   CREATE TABLE "CLIENTE" (
    "ID" integer NOT NULL,
    "CI_NIT" character varying(20),
    "TELEFONO" character varying(100),
    "MAIL" character varying(100),
    "TIPO_CLIENTE" integer
);
    DROP TABLE public."CLIENTE";
       public         postgres    false    3            �            1259    16754    CLIENTE_DIRECCION    TABLE     �   CREATE TABLE "CLIENTE_DIRECCION" (
    "ID" integer NOT NULL,
    "DIRECCION" character varying(1000),
    "DIRECCION_MAP" character varying(100),
    "ID_CLIENTE" integer
);
 '   DROP TABLE public."CLIENTE_DIRECCION";
       public         postgres    false    3            �            1259    16752    CLIENTE_DIRECCION_ID_seq    SEQUENCE     �   CREATE SEQUENCE "CLIENTE_DIRECCION_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 1   DROP SEQUENCE public."CLIENTE_DIRECCION_ID_seq";
       public       postgres    false    3    224            �           0    0    CLIENTE_DIRECCION_ID_seq    SEQUENCE OWNED BY     M   ALTER SEQUENCE "CLIENTE_DIRECCION_ID_seq" OWNED BY "CLIENTE_DIRECCION"."ID";
            public       postgres    false    223            �            1259    16720    CLIENTE_ID_seq    SEQUENCE     �   CREATE SEQUENCE "CLIENTE_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."CLIENTE_ID_seq";
       public       postgres    false    222    3            �           0    0    CLIENTE_ID_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE "CLIENTE_ID_seq" OWNED BY "CLIENTE"."ID";
            public       postgres    false    221            �            1259    16788    COMBO    TABLE     �   CREATE TABLE "COMBO" (
    "ID" integer NOT NULL,
    "NOMBRE" character varying(1000),
    "PRECIO" numeric,
    "IMAGEN" character varying(500)
);
    DROP TABLE public."COMBO";
       public         postgres    false    3            �            1259    16870    COMBO_DETALLE    TABLE        CREATE TABLE "COMBO_DETALLE" (
    "ID" integer NOT NULL,
    "DESCRIPCION" character varying(1000),
    "ID_COMBO" integer
);
 #   DROP TABLE public."COMBO_DETALLE";
       public         postgres    false    3            �            1259    16868    COMBO_DETALLE_ID_seq    SEQUENCE     �   CREATE SEQUENCE "COMBO_DETALLE_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public."COMBO_DETALLE_ID_seq";
       public       postgres    false    3    232            �           0    0    COMBO_DETALLE_ID_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE "COMBO_DETALLE_ID_seq" OWNED BY "COMBO_DETALLE"."ID";
            public       postgres    false    231            �            1259    16786    COMBO_ID_seq    SEQUENCE        CREATE SEQUENCE "COMBO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public."COMBO_ID_seq";
       public       postgres    false    228    3            �           0    0    COMBO_ID_seq    SEQUENCE OWNED BY     5   ALTER SEQUENCE "COMBO_ID_seq" OWNED BY "COMBO"."ID";
            public       postgres    false    227            �            1259    16846    COMBO_PRODUCTO    TABLE     �   CREATE TABLE "COMBO_PRODUCTO" (
    "ID" integer NOT NULL,
    "ID_PRODUCTO" integer,
    "ID_COMBO" integer,
    "CANTIDAD" numeric
);
 $   DROP TABLE public."COMBO_PRODUCTO";
       public         postgres    false    3            �            1259    16844    COMBO_PRODUCTO_ID_seq    SEQUENCE     �   CREATE SEQUENCE "COMBO_PRODUCTO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public."COMBO_PRODUCTO_ID_seq";
       public       postgres    false    230    3            �           0    0    COMBO_PRODUCTO_ID_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE "COMBO_PRODUCTO_ID_seq" OWNED BY "COMBO_PRODUCTO"."ID";
            public       postgres    false    229            �            1259    16441    DETALLE_NOTA_RECEPCION    TABLE     �   CREATE TABLE "DETALLE_NOTA_RECEPCION" (
    "ID" integer NOT NULL,
    "ID_NOTA_RECEPCION" integer,
    "ID_PRODUCTO" integer,
    "CANTIDAD" numeric,
    "PRECIO" numeric
);
 ,   DROP TABLE public."DETALLE_NOTA_RECEPCION";
       public         postgres    false    3            �            1259    16439    DETALLE_NOTA_RECEPCION_ID_seq    SEQUENCE     �   CREATE SEQUENCE "DETALLE_NOTA_RECEPCION_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 6   DROP SEQUENCE public."DETALLE_NOTA_RECEPCION_ID_seq";
       public       postgres    false    3    205            �           0    0    DETALLE_NOTA_RECEPCION_ID_seq    SEQUENCE OWNED BY     W   ALTER SEQUENCE "DETALLE_NOTA_RECEPCION_ID_seq" OWNED BY "DETALLE_NOTA_RECEPCION"."ID";
            public       postgres    false    204            �            1259    16504    MENU    TABLE     X   CREATE TABLE "MENU" (
    "ID" integer NOT NULL,
    "DESCRIPCION" character varying
);
    DROP TABLE public."MENU";
       public         postgres    false    3            �            1259    16502    MENU_ID_seq    SEQUENCE     ~   CREATE SEQUENCE "MENU_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public."MENU_ID_seq";
       public       postgres    false    211    3            �           0    0    MENU_ID_seq    SEQUENCE OWNED BY     3   ALTER SEQUENCE "MENU_ID_seq" OWNED BY "MENU"."ID";
            public       postgres    false    210            �            1259    16433    NOTA_RECEPCION    TABLE     �   CREATE TABLE "NOTA_RECEPCION" (
    "ID" integer NOT NULL,
    "FECHA" date,
    "ID_SUCURSAL" integer,
    "ID_USUARIO_ENTREGA" integer,
    "ID_USUARIO_RECIBE" integer,
    "NUMERO" integer NOT NULL
);
 $   DROP TABLE public."NOTA_RECEPCION";
       public         postgres    false    3            �            1259    16431    NOTA_RECEPCION_ID_seq    SEQUENCE     �   CREATE SEQUENCE "NOTA_RECEPCION_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 .   DROP SEQUENCE public."NOTA_RECEPCION_ID_seq";
       public       postgres    false    3    203            �           0    0    NOTA_RECEPCION_ID_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE "NOTA_RECEPCION_ID_seq" OWNED BY "NOTA_RECEPCION"."ID";
            public       postgres    false    202            �            1259    16684    NOTA_RECEPCION_NUMERO_seq    SEQUENCE     �   CREATE SEQUENCE "NOTA_RECEPCION_NUMERO_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE public."NOTA_RECEPCION_NUMERO_seq";
       public       postgres    false    3    203            �           0    0    NOTA_RECEPCION_NUMERO_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE "NOTA_RECEPCION_NUMERO_seq" OWNED BY "NOTA_RECEPCION"."NUMERO";
            public       postgres    false    216            �            1259    16529    PERMISO    TABLE     �   CREATE TABLE "PERMISO" (
    "ID" integer NOT NULL,
    "ID_CARGO" integer,
    "ID_SUB_MENU" integer,
    "ALTA" boolean,
    "BAJA" boolean,
    "MODIFICACION" boolean
);
    DROP TABLE public."PERMISO";
       public         postgres    false    3            �            1259    16527    PERMISO_ID_seq    SEQUENCE     �   CREATE SEQUENCE "PERMISO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."PERMISO_ID_seq";
       public       postgres    false    215    3            �           0    0    PERMISO_ID_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE "PERMISO_ID_seq" OWNED BY "PERMISO"."ID";
            public       postgres    false    214            �            1259    16711    PERSONA_JURIDICA    TABLE     �   CREATE TABLE "PERSONA_JURIDICA" (
    "ID" integer NOT NULL,
    "RAZON_SOCIAL" character varying(500),
    "ID_CLIENTE" integer
);
 &   DROP TABLE public."PERSONA_JURIDICA";
       public         postgres    false    3            �            1259    16709    PERSONA_JURIDICA_ID_seq    SEQUENCE     �   CREATE SEQUENCE "PERSONA_JURIDICA_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 0   DROP SEQUENCE public."PERSONA_JURIDICA_ID_seq";
       public       postgres    false    3    220            �           0    0    PERSONA_JURIDICA_ID_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE "PERSONA_JURIDICA_ID_seq" OWNED BY "PERSONA_JURIDICA"."ID";
            public       postgres    false    219            �            1259    16700    PERSONA_NATURAL    TABLE     �   CREATE TABLE "PERSONA_NATURAL" (
    "ID" integer NOT NULL,
    "NOMBRES" character varying(500),
    "APELLIDO_PATERNO" character varying(500),
    "APELLIDO_MATERNO" character varying(500),
    "FECHA_NACIMIENTO" date,
    "ID_CLIENTE" integer
);
 %   DROP TABLE public."PERSONA_NATURAL";
       public         postgres    false    3            �            1259    16698    PERSONA_NATURAL_ID_seq    SEQUENCE     �   CREATE SEQUENCE "PERSONA_NATURAL_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public."PERSONA_NATURAL_ID_seq";
       public       postgres    false    218    3            �           0    0    PERSONA_NATURAL_ID_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE "PERSONA_NATURAL_ID_seq" OWNED BY "PERSONA_NATURAL"."ID";
            public       postgres    false    217            �            1259    16408    PRECIO_PRODUCTO    TABLE     �   CREATE TABLE "PRECIO_PRODUCTO" (
    "ID" integer NOT NULL,
    "ID_PRODUCTO" integer,
    "PRECIO_COMPRA" numeric(10,2),
    "PRECIO_VENTA" numeric(10,2),
    "FECHA_DESDE" date,
    "FECHA_HASTA" date
);
 %   DROP TABLE public."PRECIO_PRODUCTO";
       public         postgres    false    3            �            1259    16406    PRECIO_PRODUCTO_ID_seq    SEQUENCE     �   CREATE SEQUENCE "PRECIO_PRODUCTO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public."PRECIO_PRODUCTO_ID_seq";
       public       postgres    false    199    3            �           0    0    PRECIO_PRODUCTO_ID_seq    SEQUENCE OWNED BY     I   ALTER SEQUENCE "PRECIO_PRODUCTO_ID_seq" OWNED BY "PRECIO_PRODUCTO"."ID";
            public       postgres    false    198            �            1259    16397    PRODUCTO    TABLE     �   CREATE TABLE "PRODUCTO" (
    "ID" integer NOT NULL,
    "NOMBRE" character varying(1000),
    "IMAGEN" character varying(500),
    "CODIGO" character varying(50),
    "ID_UNIDAD_MEDIDA" integer
);
    DROP TABLE public."PRODUCTO";
       public         postgres    false    3            �            1259    16395    PRODUCTO_ID_seq    SEQUENCE     �   CREATE SEQUENCE "PRODUCTO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."PRODUCTO_ID_seq";
       public       postgres    false    197    3            �           0    0    PRODUCTO_ID_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE "PRODUCTO_ID_seq" OWNED BY "PRODUCTO"."ID";
            public       postgres    false    196            �            1259    16512    SUB_MENU    TABLE     �   CREATE TABLE "SUB_MENU" (
    "ID" integer NOT NULL,
    "DESCRIPCION" character varying,
    "IMAGEN" character varying,
    "URL" character varying,
    "ID_MENU" integer
);
    DROP TABLE public."SUB_MENU";
       public         postgres    false    3            �            1259    16510    SUB_MENU_ID_seq    SEQUENCE     �   CREATE SEQUENCE "SUB_MENU_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."SUB_MENU_ID_seq";
       public       postgres    false    3    213            �           0    0    SUB_MENU_ID_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE "SUB_MENU_ID_seq" OWNED BY "SUB_MENU"."ID";
            public       postgres    false    212            �            1259    16422    SUCURSAL    TABLE     �   CREATE TABLE "SUCURSAL" (
    "ID" integer NOT NULL,
    "DESCRIPCION" character varying,
    "DIRECCION" character varying,
    "UBICACION_MAP" character varying,
    "IMAGEN" character varying
);
    DROP TABLE public."SUCURSAL";
       public         postgres    false    3            �            1259    16420    SUCURSAL_ID_seq    SEQUENCE     �   CREATE SEQUENCE "SUCURSAL_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE public."SUCURSAL_ID_seq";
       public       postgres    false    3    201            �           0    0    SUCURSAL_ID_seq    SEQUENCE OWNED BY     ;   ALTER SEQUENCE "SUCURSAL_ID_seq" OWNED BY "SUCURSAL"."ID";
            public       postgres    false    200            �            1259    16772    UNIDAD_MEDIDA    TABLE     �   CREATE TABLE "UNIDAD_MEDIDA" (
    "ID" integer NOT NULL,
    "DESCRIPCION" character varying(500),
    "ABREVIACION" character varying(20)
);
 #   DROP TABLE public."UNIDAD_MEDIDA";
       public         postgres    false    3            �            1259    16770    UNIDAD_MEDIDA_ID_seq    SEQUENCE     �   CREATE SEQUENCE "UNIDAD_MEDIDA_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 -   DROP SEQUENCE public."UNIDAD_MEDIDA_ID_seq";
       public       postgres    false    3    226            �           0    0    UNIDAD_MEDIDA_ID_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE "UNIDAD_MEDIDA_ID_seq" OWNED BY "UNIDAD_MEDIDA"."ID";
            public       postgres    false    225            �            1259    16467    USUARIO    TABLE     z  CREATE TABLE "USUARIO" (
    "ID" integer NOT NULL,
    "PASSWORD" character varying,
    "NOMBRES" character varying,
    "APELLIDOS" character varying,
    "FECHA_NACIMIENTO" date,
    "CI" character varying,
    "SEXO" character(1),
    "ID_CARGO" integer,
    "USUARIO" character varying,
    "FECHA_CREACION" date,
    "ESTADO" boolean,
    "ID_USUARIO_CREADOR" integer
);
    DROP TABLE public."USUARIO";
       public         postgres    false    3            �            1259    16465    USUARIO_ID_seq    SEQUENCE     �   CREATE SEQUENCE "USUARIO_ID_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public."USUARIO_ID_seq";
       public       postgres    false    207    3            �           0    0    USUARIO_ID_seq    SEQUENCE OWNED BY     9   ALTER SEQUENCE "USUARIO_ID_seq" OWNED BY "USUARIO"."ID";
            public       postgres    false    206            �
           2604    16481    CARGO ID    DEFAULT     \   ALTER TABLE ONLY "CARGO" ALTER COLUMN "ID" SET DEFAULT nextval('"CARGO_ID_seq"'::regclass);
 ;   ALTER TABLE public."CARGO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    208    209    209            �
           2604    16725 
   CLIENTE ID    DEFAULT     `   ALTER TABLE ONLY "CLIENTE" ALTER COLUMN "ID" SET DEFAULT nextval('"CLIENTE_ID_seq"'::regclass);
 =   ALTER TABLE public."CLIENTE" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    222    221    222            �
           2604    16757    CLIENTE_DIRECCION ID    DEFAULT     t   ALTER TABLE ONLY "CLIENTE_DIRECCION" ALTER COLUMN "ID" SET DEFAULT nextval('"CLIENTE_DIRECCION_ID_seq"'::regclass);
 G   ALTER TABLE public."CLIENTE_DIRECCION" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    224    223    224            �
           2604    16860    COMBO ID    DEFAULT     \   ALTER TABLE ONLY "COMBO" ALTER COLUMN "ID" SET DEFAULT nextval('"COMBO_ID_seq"'::regclass);
 ;   ALTER TABLE public."COMBO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    228    227    228            �
           2604    16873    COMBO_DETALLE ID    DEFAULT     l   ALTER TABLE ONLY "COMBO_DETALLE" ALTER COLUMN "ID" SET DEFAULT nextval('"COMBO_DETALLE_ID_seq"'::regclass);
 C   ALTER TABLE public."COMBO_DETALLE" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    231    232    232            �
           2604    16849    COMBO_PRODUCTO ID    DEFAULT     n   ALTER TABLE ONLY "COMBO_PRODUCTO" ALTER COLUMN "ID" SET DEFAULT nextval('"COMBO_PRODUCTO_ID_seq"'::regclass);
 D   ALTER TABLE public."COMBO_PRODUCTO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    229    230    230            �
           2604    16444    DETALLE_NOTA_RECEPCION ID    DEFAULT     ~   ALTER TABLE ONLY "DETALLE_NOTA_RECEPCION" ALTER COLUMN "ID" SET DEFAULT nextval('"DETALLE_NOTA_RECEPCION_ID_seq"'::regclass);
 L   ALTER TABLE public."DETALLE_NOTA_RECEPCION" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    204    205    205            �
           2604    16507    MENU ID    DEFAULT     Z   ALTER TABLE ONLY "MENU" ALTER COLUMN "ID" SET DEFAULT nextval('"MENU_ID_seq"'::regclass);
 :   ALTER TABLE public."MENU" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    210    211    211            �
           2604    16436    NOTA_RECEPCION ID    DEFAULT     n   ALTER TABLE ONLY "NOTA_RECEPCION" ALTER COLUMN "ID" SET DEFAULT nextval('"NOTA_RECEPCION_ID_seq"'::regclass);
 D   ALTER TABLE public."NOTA_RECEPCION" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    202    203    203            �
           2604    16686    NOTA_RECEPCION NUMERO    DEFAULT     v   ALTER TABLE ONLY "NOTA_RECEPCION" ALTER COLUMN "NUMERO" SET DEFAULT nextval('"NOTA_RECEPCION_NUMERO_seq"'::regclass);
 H   ALTER TABLE public."NOTA_RECEPCION" ALTER COLUMN "NUMERO" DROP DEFAULT;
       public       postgres    false    216    203            �
           2604    16532 
   PERMISO ID    DEFAULT     `   ALTER TABLE ONLY "PERMISO" ALTER COLUMN "ID" SET DEFAULT nextval('"PERMISO_ID_seq"'::regclass);
 =   ALTER TABLE public."PERMISO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    214    215    215            �
           2604    16714    PERSONA_JURIDICA ID    DEFAULT     r   ALTER TABLE ONLY "PERSONA_JURIDICA" ALTER COLUMN "ID" SET DEFAULT nextval('"PERSONA_JURIDICA_ID_seq"'::regclass);
 F   ALTER TABLE public."PERSONA_JURIDICA" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    219    220    220            �
           2604    16703    PERSONA_NATURAL ID    DEFAULT     p   ALTER TABLE ONLY "PERSONA_NATURAL" ALTER COLUMN "ID" SET DEFAULT nextval('"PERSONA_NATURAL_ID_seq"'::regclass);
 E   ALTER TABLE public."PERSONA_NATURAL" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    218    217    218            �
           2604    16411    PRECIO_PRODUCTO ID    DEFAULT     p   ALTER TABLE ONLY "PRECIO_PRODUCTO" ALTER COLUMN "ID" SET DEFAULT nextval('"PRECIO_PRODUCTO_ID_seq"'::regclass);
 E   ALTER TABLE public."PRECIO_PRODUCTO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    199    198    199            �
           2604    16400    PRODUCTO ID    DEFAULT     b   ALTER TABLE ONLY "PRODUCTO" ALTER COLUMN "ID" SET DEFAULT nextval('"PRODUCTO_ID_seq"'::regclass);
 >   ALTER TABLE public."PRODUCTO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    197    196    197            �
           2604    16515    SUB_MENU ID    DEFAULT     b   ALTER TABLE ONLY "SUB_MENU" ALTER COLUMN "ID" SET DEFAULT nextval('"SUB_MENU_ID_seq"'::regclass);
 >   ALTER TABLE public."SUB_MENU" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    212    213    213            �
           2604    16425    SUCURSAL ID    DEFAULT     b   ALTER TABLE ONLY "SUCURSAL" ALTER COLUMN "ID" SET DEFAULT nextval('"SUCURSAL_ID_seq"'::regclass);
 >   ALTER TABLE public."SUCURSAL" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    200    201    201            �
           2604    16775    UNIDAD_MEDIDA ID    DEFAULT     l   ALTER TABLE ONLY "UNIDAD_MEDIDA" ALTER COLUMN "ID" SET DEFAULT nextval('"UNIDAD_MEDIDA_ID_seq"'::regclass);
 C   ALTER TABLE public."UNIDAD_MEDIDA" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    225    226    226            �
           2604    16470 
   USUARIO ID    DEFAULT     `   ALTER TABLE ONLY "USUARIO" ALTER COLUMN "ID" SET DEFAULT nextval('"USUARIO_ID_seq"'::regclass);
 =   ALTER TABLE public."USUARIO" ALTER COLUMN "ID" DROP DEFAULT;
       public       postgres    false    207    206    207            �          0    16478    CARGO 
   TABLE DATA               /   COPY "CARGO" ("ID", "DESCRIPCION") FROM stdin;
    public       postgres    false    209   ̵       �          0    16722    CLIENTE 
   TABLE DATA               P   COPY "CLIENTE" ("ID", "CI_NIT", "TELEFONO", "MAIL", "TIPO_CLIENTE") FROM stdin;
    public       postgres    false    222   �       �          0    16754    CLIENTE_DIRECCION 
   TABLE DATA               X   COPY "CLIENTE_DIRECCION" ("ID", "DIRECCION", "DIRECCION_MAP", "ID_CLIENTE") FROM stdin;
    public       postgres    false    224   �       �          0    16788    COMBO 
   TABLE DATA               >   COPY "COMBO" ("ID", "NOMBRE", "PRECIO", "IMAGEN") FROM stdin;
    public       postgres    false    228   <�       �          0    16870    COMBO_DETALLE 
   TABLE DATA               C   COPY "COMBO_DETALLE" ("ID", "DESCRIPCION", "ID_COMBO") FROM stdin;
    public       postgres    false    232   Y�       �          0    16846    COMBO_PRODUCTO 
   TABLE DATA               P   COPY "COMBO_PRODUCTO" ("ID", "ID_PRODUCTO", "ID_COMBO", "CANTIDAD") FROM stdin;
    public       postgres    false    230   v�       �          0    16441    DETALLE_NOTA_RECEPCION 
   TABLE DATA               k   COPY "DETALLE_NOTA_RECEPCION" ("ID", "ID_NOTA_RECEPCION", "ID_PRODUCTO", "CANTIDAD", "PRECIO") FROM stdin;
    public       postgres    false    205   ��       �          0    16504    MENU 
   TABLE DATA               .   COPY "MENU" ("ID", "DESCRIPCION") FROM stdin;
    public       postgres    false    211   �       �          0    16433    NOTA_RECEPCION 
   TABLE DATA               v   COPY "NOTA_RECEPCION" ("ID", "FECHA", "ID_SUCURSAL", "ID_USUARIO_ENTREGA", "ID_USUARIO_RECIBE", "NUMERO") FROM stdin;
    public       postgres    false    203   j�       �          0    16529    PERMISO 
   TABLE DATA               ]   COPY "PERMISO" ("ID", "ID_CARGO", "ID_SUB_MENU", "ALTA", "BAJA", "MODIFICACION") FROM stdin;
    public       postgres    false    215   ȷ       �          0    16711    PERSONA_JURIDICA 
   TABLE DATA               I   COPY "PERSONA_JURIDICA" ("ID", "RAZON_SOCIAL", "ID_CLIENTE") FROM stdin;
    public       postgres    false    220   �       �          0    16700    PERSONA_NATURAL 
   TABLE DATA                  COPY "PERSONA_NATURAL" ("ID", "NOMBRES", "APELLIDO_PATERNO", "APELLIDO_MATERNO", "FECHA_NACIMIENTO", "ID_CLIENTE") FROM stdin;
    public       postgres    false    218    �       �          0    16408    PRECIO_PRODUCTO 
   TABLE DATA               x   COPY "PRECIO_PRODUCTO" ("ID", "ID_PRODUCTO", "PRECIO_COMPRA", "PRECIO_VENTA", "FECHA_DESDE", "FECHA_HASTA") FROM stdin;
    public       postgres    false    199   =�       �          0    16397    PRODUCTO 
   TABLE DATA               U   COPY "PRODUCTO" ("ID", "NOMBRE", "IMAGEN", "CODIGO", "ID_UNIDAD_MEDIDA") FROM stdin;
    public       postgres    false    197   Z�       �          0    16512    SUB_MENU 
   TABLE DATA               N   COPY "SUB_MENU" ("ID", "DESCRIPCION", "IMAGEN", "URL", "ID_MENU") FROM stdin;
    public       postgres    false    213   P�       �          0    16422    SUCURSAL 
   TABLE DATA               Z   COPY "SUCURSAL" ("ID", "DESCRIPCION", "DIRECCION", "UBICACION_MAP", "IMAGEN") FROM stdin;
    public       postgres    false    201   ��       �          0    16772    UNIDAD_MEDIDA 
   TABLE DATA               F   COPY "UNIDAD_MEDIDA" ("ID", "DESCRIPCION", "ABREVIACION") FROM stdin;
    public       postgres    false    226   C�       �          0    16467    USUARIO 
   TABLE DATA               �   COPY "USUARIO" ("ID", "PASSWORD", "NOMBRES", "APELLIDOS", "FECHA_NACIMIENTO", "CI", "SEXO", "ID_CARGO", "USUARIO", "FECHA_CREACION", "ESTADO", "ID_USUARIO_CREADOR") FROM stdin;
    public       postgres    false    207   `�       �           0    0    CARGO_ID_seq    SEQUENCE SET     5   SELECT pg_catalog.setval('"CARGO_ID_seq"', 1, true);
            public       postgres    false    208            �           0    0    CLIENTE_DIRECCION_ID_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('"CLIENTE_DIRECCION_ID_seq"', 1, false);
            public       postgres    false    223            �           0    0    CLIENTE_ID_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('"CLIENTE_ID_seq"', 1, false);
            public       postgres    false    221            �           0    0    COMBO_DETALLE_ID_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('"COMBO_DETALLE_ID_seq"', 1, false);
            public       postgres    false    231            �           0    0    COMBO_ID_seq    SEQUENCE SET     6   SELECT pg_catalog.setval('"COMBO_ID_seq"', 1, false);
            public       postgres    false    227            �           0    0    COMBO_PRODUCTO_ID_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('"COMBO_PRODUCTO_ID_seq"', 1, false);
            public       postgres    false    229            �           0    0    DETALLE_NOTA_RECEPCION_ID_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('"DETALLE_NOTA_RECEPCION_ID_seq"', 21, true);
            public       postgres    false    204            �           0    0    MENU_ID_seq    SEQUENCE SET     4   SELECT pg_catalog.setval('"MENU_ID_seq"', 6, true);
            public       postgres    false    210            �           0    0    NOTA_RECEPCION_ID_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('"NOTA_RECEPCION_ID_seq"', 15, true);
            public       postgres    false    202            �           0    0    NOTA_RECEPCION_NUMERO_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('"NOTA_RECEPCION_NUMERO_seq"', 12, true);
            public       postgres    false    216            �           0    0    PERMISO_ID_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('"PERMISO_ID_seq"', 5, true);
            public       postgres    false    214            �           0    0    PERSONA_JURIDICA_ID_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('"PERSONA_JURIDICA_ID_seq"', 1, false);
            public       postgres    false    219            �           0    0    PERSONA_NATURAL_ID_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('"PERSONA_NATURAL_ID_seq"', 1, false);
            public       postgres    false    217            �           0    0    PRECIO_PRODUCTO_ID_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('"PRECIO_PRODUCTO_ID_seq"', 1, false);
            public       postgres    false    198            �           0    0    PRODUCTO_ID_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('"PRODUCTO_ID_seq"', 31, true);
            public       postgres    false    196            �           0    0    SUB_MENU_ID_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('"SUB_MENU_ID_seq"', 5, true);
            public       postgres    false    212            �           0    0    SUCURSAL_ID_seq    SEQUENCE SET     8   SELECT pg_catalog.setval('"SUCURSAL_ID_seq"', 1, true);
            public       postgres    false    200            �           0    0    UNIDAD_MEDIDA_ID_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('"UNIDAD_MEDIDA_ID_seq"', 1, false);
            public       postgres    false    225                        0    0    USUARIO_ID_seq    SEQUENCE SET     7   SELECT pg_catalog.setval('"USUARIO_ID_seq"', 2, true);
            public       postgres    false    206                       2606    16483    CARGO CARGO_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY "CARGO"
    ADD CONSTRAINT "CARGO_pkey" PRIMARY KEY ("ID");
 >   ALTER TABLE ONLY public."CARGO" DROP CONSTRAINT "CARGO_pkey";
       public         postgres    false    209                       2606    16762 (   CLIENTE_DIRECCION CLIENTE_DIRECCION_pkey 
   CONSTRAINT     e   ALTER TABLE ONLY "CLIENTE_DIRECCION"
    ADD CONSTRAINT "CLIENTE_DIRECCION_pkey" PRIMARY KEY ("ID");
 V   ALTER TABLE ONLY public."CLIENTE_DIRECCION" DROP CONSTRAINT "CLIENTE_DIRECCION_pkey";
       public         postgres    false    224                       2606    16727    CLIENTE CLIENTE_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY "CLIENTE"
    ADD CONSTRAINT "CLIENTE_pkey" PRIMARY KEY ("ID");
 B   ALTER TABLE ONLY public."CLIENTE" DROP CONSTRAINT "CLIENTE_pkey";
       public         postgres    false    222            $           2606    16878     COMBO_DETALLE COMBO_DETALLE_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY "COMBO_DETALLE"
    ADD CONSTRAINT "COMBO_DETALLE_pkey" PRIMARY KEY ("ID");
 N   ALTER TABLE ONLY public."COMBO_DETALLE" DROP CONSTRAINT "COMBO_DETALLE_pkey";
       public         postgres    false    232            "           2606    16854 "   COMBO_PRODUCTO COMBO_PRODUCTO_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY "COMBO_PRODUCTO"
    ADD CONSTRAINT "COMBO_PRODUCTO_pkey" PRIMARY KEY ("ID");
 P   ALTER TABLE ONLY public."COMBO_PRODUCTO" DROP CONSTRAINT "COMBO_PRODUCTO_pkey";
       public         postgres    false    230                        2606    16862    COMBO COMBO_pkey 
   CONSTRAINT     M   ALTER TABLE ONLY "COMBO"
    ADD CONSTRAINT "COMBO_pkey" PRIMARY KEY ("ID");
 >   ALTER TABLE ONLY public."COMBO" DROP CONSTRAINT "COMBO_pkey";
       public         postgres    false    228                       2606    16446 2   DETALLE_NOTA_RECEPCION DETALLE_NOTA_RECEPCION_pkey 
   CONSTRAINT     o   ALTER TABLE ONLY "DETALLE_NOTA_RECEPCION"
    ADD CONSTRAINT "DETALLE_NOTA_RECEPCION_pkey" PRIMARY KEY ("ID");
 `   ALTER TABLE ONLY public."DETALLE_NOTA_RECEPCION" DROP CONSTRAINT "DETALLE_NOTA_RECEPCION_pkey";
       public         postgres    false    205                       2606    16509    MENU MENU_pkey 
   CONSTRAINT     K   ALTER TABLE ONLY "MENU"
    ADD CONSTRAINT "MENU_pkey" PRIMARY KEY ("ID");
 <   ALTER TABLE ONLY public."MENU" DROP CONSTRAINT "MENU_pkey";
       public         postgres    false    211                       2606    16438 "   NOTA_RECEPCION NOTA_RECEPCION_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY "NOTA_RECEPCION"
    ADD CONSTRAINT "NOTA_RECEPCION_pkey" PRIMARY KEY ("ID");
 P   ALTER TABLE ONLY public."NOTA_RECEPCION" DROP CONSTRAINT "NOTA_RECEPCION_pkey";
       public         postgres    false    203                       2606    16534    PERMISO PERMISO_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY "PERMISO"
    ADD CONSTRAINT "PERMISO_pkey" PRIMARY KEY ("ID");
 B   ALTER TABLE ONLY public."PERMISO" DROP CONSTRAINT "PERMISO_pkey";
       public         postgres    false    215                       2606    16719 &   PERSONA_JURIDICA PERSONA_JURIDICA_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY "PERSONA_JURIDICA"
    ADD CONSTRAINT "PERSONA_JURIDICA_pkey" PRIMARY KEY ("ID");
 T   ALTER TABLE ONLY public."PERSONA_JURIDICA" DROP CONSTRAINT "PERSONA_JURIDICA_pkey";
       public         postgres    false    220                       2606    16708 $   PERSONA_NATURAL PERSONA_NATURAL_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY "PERSONA_NATURAL"
    ADD CONSTRAINT "PERSONA_NATURAL_pkey" PRIMARY KEY ("ID");
 R   ALTER TABLE ONLY public."PERSONA_NATURAL" DROP CONSTRAINT "PERSONA_NATURAL_pkey";
       public         postgres    false    218            �
           2606    16413 $   PRECIO_PRODUCTO PRECIO_PRODUCTO_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY "PRECIO_PRODUCTO"
    ADD CONSTRAINT "PRECIO_PRODUCTO_pkey" PRIMARY KEY ("ID");
 R   ALTER TABLE ONLY public."PRECIO_PRODUCTO" DROP CONSTRAINT "PRECIO_PRODUCTO_pkey";
       public         postgres    false    199            �
           2606    16405    PRODUCTO PRODUCTO_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY "PRODUCTO"
    ADD CONSTRAINT "PRODUCTO_pkey" PRIMARY KEY ("ID");
 D   ALTER TABLE ONLY public."PRODUCTO" DROP CONSTRAINT "PRODUCTO_pkey";
       public         postgres    false    197                       2606    16520    SUB_MENU SUB_MENU_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY "SUB_MENU"
    ADD CONSTRAINT "SUB_MENU_pkey" PRIMARY KEY ("ID");
 D   ALTER TABLE ONLY public."SUB_MENU" DROP CONSTRAINT "SUB_MENU_pkey";
       public         postgres    false    213            �
           2606    16430    SUCURSAL SUCURSAL_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY "SUCURSAL"
    ADD CONSTRAINT "SUCURSAL_pkey" PRIMARY KEY ("ID");
 D   ALTER TABLE ONLY public."SUCURSAL" DROP CONSTRAINT "SUCURSAL_pkey";
       public         postgres    false    201                       2606    16780     UNIDAD_MEDIDA UNIDAD_MEDIDA_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY "UNIDAD_MEDIDA"
    ADD CONSTRAINT "UNIDAD_MEDIDA_pkey" PRIMARY KEY ("ID");
 N   ALTER TABLE ONLY public."UNIDAD_MEDIDA" DROP CONSTRAINT "UNIDAD_MEDIDA_pkey";
       public         postgres    false    226            
           2606    16475    USUARIO USUARIO_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY "USUARIO"
    ADD CONSTRAINT "USUARIO_pkey" PRIMARY KEY ("ID");
 B   ALTER TABLE ONLY public."USUARIO" DROP CONSTRAINT "USUARIO_pkey";
       public         postgres    false    207            �
           2606    16697    PRODUCTO uq_producto_codigo 
   CONSTRAINT     U   ALTER TABLE ONLY "PRODUCTO"
    ADD CONSTRAINT uq_producto_codigo UNIQUE ("CODIGO");
 G   ALTER TABLE ONLY public."PRODUCTO" DROP CONSTRAINT uq_producto_codigo;
       public         postgres    false    197            �
           1259    16419    fki_P    INDEX     G   CREATE INDEX "fki_P" ON "PRECIO_PRODUCTO" USING btree ("ID_PRODUCTO");
    DROP INDEX public."fki_P";
       public         postgres    false    199                       1259    16489    fki_fk_CARGO    INDEX     C   CREATE INDEX "fki_fk_CARGO" ON "USUARIO" USING btree ("ID_CARGO");
 "   DROP INDEX public."fki_fk_CARGO";
       public         postgres    false    207                       1259    16526    fki_fk_MENU    INDEX     B   CREATE INDEX "fki_fk_MENU" ON "SUB_MENU" USING btree ("ID_MENU");
 !   DROP INDEX public."fki_fk_MENU";
       public         postgres    false    213                       1259    16458    fki_fk_NOTA_RECEPCION    INDEX     d   CREATE INDEX "fki_fk_NOTA_RECEPCION" ON "DETALLE_NOTA_RECEPCION" USING btree ("ID_NOTA_RECEPCION");
 +   DROP INDEX public."fki_fk_NOTA_RECEPCION";
       public         postgres    false    205                       1259    16464    fki_fk_PRODUCTO    INDEX     X   CREATE INDEX "fki_fk_PRODUCTO" ON "DETALLE_NOTA_RECEPCION" USING btree ("ID_PRODUCTO");
 %   DROP INDEX public."fki_fk_PRODUCTO";
       public         postgres    false    205                       1259    16452    fki_fk_SUCURSAL    INDEX     P   CREATE INDEX "fki_fk_SUCURSAL" ON "NOTA_RECEPCION" USING btree ("ID_SUCURSAL");
 %   DROP INDEX public."fki_fk_SUCURSAL";
       public         postgres    false    203                       1259    16495    fki_fk_USUARIO_ENTREGA    INDEX     ^   CREATE INDEX "fki_fk_USUARIO_ENTREGA" ON "NOTA_RECEPCION" USING btree ("ID_USUARIO_ENTREGA");
 ,   DROP INDEX public."fki_fk_USUARIO_ENTREGA";
       public         postgres    false    203                       1259    16501    fki_fk_USUARIO_RECIBE    INDEX     \   CREATE INDEX "fki_fk_USUARIO_RECIBE" ON "NOTA_RECEPCION" USING btree ("ID_USUARIO_RECIBE");
 +   DROP INDEX public."fki_fk_USUARIO_RECIBE";
       public         postgres    false    203            ,           2606    16484    USUARIO fk_CARGO    FK CONSTRAINT     l   ALTER TABLE ONLY "USUARIO"
    ADD CONSTRAINT "fk_CARGO" FOREIGN KEY ("ID_CARGO") REFERENCES "CARGO"("ID");
 >   ALTER TABLE ONLY public."USUARIO" DROP CONSTRAINT "fk_CARGO";
       public       postgres    false    207    209    2829            /           2606    16540    PERMISO fk_CARGO    FK CONSTRAINT     l   ALTER TABLE ONLY "PERMISO"
    ADD CONSTRAINT "fk_CARGO" FOREIGN KEY ("ID_CARGO") REFERENCES "CARGO"("ID");
 >   ALTER TABLE ONLY public."PERMISO" DROP CONSTRAINT "fk_CARGO";
       public       postgres    false    215    209    2829            1           2606    16735    PERSONA_JURIDICA fk_CLIENTE    FK CONSTRAINT     {   ALTER TABLE ONLY "PERSONA_JURIDICA"
    ADD CONSTRAINT "fk_CLIENTE" FOREIGN KEY ("ID_CLIENTE") REFERENCES "CLIENTE"("ID");
 I   ALTER TABLE ONLY public."PERSONA_JURIDICA" DROP CONSTRAINT "fk_CLIENTE";
       public       postgres    false    222    2842    220            0           2606    16747    PERSONA_NATURAL fk_CLIENTE    FK CONSTRAINT     z   ALTER TABLE ONLY "PERSONA_NATURAL"
    ADD CONSTRAINT "fk_CLIENTE" FOREIGN KEY ("ID_CLIENTE") REFERENCES "CLIENTE"("ID");
 H   ALTER TABLE ONLY public."PERSONA_NATURAL" DROP CONSTRAINT "fk_CLIENTE";
       public       postgres    false    218    2842    222            2           2606    16763    CLIENTE_DIRECCION fk_CLIENTE    FK CONSTRAINT     |   ALTER TABLE ONLY "CLIENTE_DIRECCION"
    ADD CONSTRAINT "fk_CLIENTE" FOREIGN KEY ("ID_CLIENTE") REFERENCES "CLIENTE"("ID");
 J   ALTER TABLE ONLY public."CLIENTE_DIRECCION" DROP CONSTRAINT "fk_CLIENTE";
       public       postgres    false    2842    222    224            4           2606    16863    COMBO_PRODUCTO fk_COMBO    FK CONSTRAINT     s   ALTER TABLE ONLY "COMBO_PRODUCTO"
    ADD CONSTRAINT "fk_COMBO" FOREIGN KEY ("ID_COMBO") REFERENCES "COMBO"("ID");
 E   ALTER TABLE ONLY public."COMBO_PRODUCTO" DROP CONSTRAINT "fk_COMBO";
       public       postgres    false    230    228    2848            5           2606    16879    COMBO_DETALLE fk_COMBO    FK CONSTRAINT     r   ALTER TABLE ONLY "COMBO_DETALLE"
    ADD CONSTRAINT "fk_COMBO" FOREIGN KEY ("ID_COMBO") REFERENCES "COMBO"("ID");
 D   ALTER TABLE ONLY public."COMBO_DETALLE" DROP CONSTRAINT "fk_COMBO";
       public       postgres    false    2848    228    232            -           2606    16521    SUB_MENU fk_MENU    FK CONSTRAINT     j   ALTER TABLE ONLY "SUB_MENU"
    ADD CONSTRAINT "fk_MENU" FOREIGN KEY ("ID_MENU") REFERENCES "MENU"("ID");
 >   ALTER TABLE ONLY public."SUB_MENU" DROP CONSTRAINT "fk_MENU";
       public       postgres    false    211    213    2831            *           2606    16453 (   DETALLE_NOTA_RECEPCION fk_NOTA_RECEPCION    FK CONSTRAINT     �   ALTER TABLE ONLY "DETALLE_NOTA_RECEPCION"
    ADD CONSTRAINT "fk_NOTA_RECEPCION" FOREIGN KEY ("ID_NOTA_RECEPCION") REFERENCES "NOTA_RECEPCION"("ID");
 V   ALTER TABLE ONLY public."DETALLE_NOTA_RECEPCION" DROP CONSTRAINT "fk_NOTA_RECEPCION";
       public       postgres    false    205    2817    203            &           2606    16414    PRECIO_PRODUCTO fk_PRODDUCTO    FK CONSTRAINT     ~   ALTER TABLE ONLY "PRECIO_PRODUCTO"
    ADD CONSTRAINT "fk_PRODDUCTO" FOREIGN KEY ("ID_PRODUCTO") REFERENCES "PRODUCTO"("ID");
 J   ALTER TABLE ONLY public."PRECIO_PRODUCTO" DROP CONSTRAINT "fk_PRODDUCTO";
       public       postgres    false    197    2808    199            +           2606    16459 "   DETALLE_NOTA_RECEPCION fk_PRODUCTO    FK CONSTRAINT     �   ALTER TABLE ONLY "DETALLE_NOTA_RECEPCION"
    ADD CONSTRAINT "fk_PRODUCTO" FOREIGN KEY ("ID_PRODUCTO") REFERENCES "PRODUCTO"("ID");
 P   ALTER TABLE ONLY public."DETALLE_NOTA_RECEPCION" DROP CONSTRAINT "fk_PRODUCTO";
       public       postgres    false    2808    197    205            3           2606    16855    COMBO_PRODUCTO fk_PRODUCTO    FK CONSTRAINT     |   ALTER TABLE ONLY "COMBO_PRODUCTO"
    ADD CONSTRAINT "fk_PRODUCTO" FOREIGN KEY ("ID_PRODUCTO") REFERENCES "PRODUCTO"("ID");
 H   ALTER TABLE ONLY public."COMBO_PRODUCTO" DROP CONSTRAINT "fk_PRODUCTO";
       public       postgres    false    230    2808    197            .           2606    16535    PERMISO fk_SUB_MENU    FK CONSTRAINT     u   ALTER TABLE ONLY "PERMISO"
    ADD CONSTRAINT "fk_SUB_MENU" FOREIGN KEY ("ID_SUB_MENU") REFERENCES "SUB_MENU"("ID");
 A   ALTER TABLE ONLY public."PERMISO" DROP CONSTRAINT "fk_SUB_MENU";
       public       postgres    false    2833    215    213            '           2606    16447    NOTA_RECEPCION fk_SUCURSAL    FK CONSTRAINT     |   ALTER TABLE ONLY "NOTA_RECEPCION"
    ADD CONSTRAINT "fk_SUCURSAL" FOREIGN KEY ("ID_SUCURSAL") REFERENCES "SUCURSAL"("ID");
 H   ALTER TABLE ONLY public."NOTA_RECEPCION" DROP CONSTRAINT "fk_SUCURSAL";
       public       postgres    false    201    2815    203            %           2606    16781    PRODUCTO fk_UNIDAD_MEDIDA    FK CONSTRAINT     �   ALTER TABLE ONLY "PRODUCTO"
    ADD CONSTRAINT "fk_UNIDAD_MEDIDA" FOREIGN KEY ("ID_UNIDAD_MEDIDA") REFERENCES "UNIDAD_MEDIDA"("ID");
 G   ALTER TABLE ONLY public."PRODUCTO" DROP CONSTRAINT "fk_UNIDAD_MEDIDA";
       public       postgres    false    2846    197    226            (           2606    16490 !   NOTA_RECEPCION fk_USUARIO_ENTREGA    FK CONSTRAINT     �   ALTER TABLE ONLY "NOTA_RECEPCION"
    ADD CONSTRAINT "fk_USUARIO_ENTREGA" FOREIGN KEY ("ID_USUARIO_ENTREGA") REFERENCES "USUARIO"("ID");
 O   ALTER TABLE ONLY public."NOTA_RECEPCION" DROP CONSTRAINT "fk_USUARIO_ENTREGA";
       public       postgres    false    207    203    2826            )           2606    16496     NOTA_RECEPCION fk_USUARIO_RECIBE    FK CONSTRAINT     �   ALTER TABLE ONLY "NOTA_RECEPCION"
    ADD CONSTRAINT "fk_USUARIO_RECIBE" FOREIGN KEY ("ID_USUARIO_RECIBE") REFERENCES "USUARIO"("ID");
 N   ALTER TABLE ONLY public."NOTA_RECEPCION" DROP CONSTRAINT "fk_USUARIO_RECIBE";
       public       postgres    false    2826    203    207            �   &   x�3�tt����	rt�RpqUr\}�b���� ���      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �   s   x�M��� C��0=AԺD'��s���<?�# Qa����I�/�fjc=C¨a�0�놆���遛Z��aJ��K�R�����v��q����O�6#��I�댧B�Y���-�^)�g�)A      �   D   x��K
�0��{���>�*��Hڸ���pf�~�猞�ѥq��[�7�ņp������n�&�?��       �   N   x�mȹ�0E�د3������y6ۚ�4�)�HJ�-���\;��b;+fGw ŝ��.T��P=�P;�s_��c$�      �   +   x�3�4��2���lc ��6�M�lS �ʎ���� z�{      �      x������ � �      �      x������ � �      �      x������ � �      �   �   x�����0���)|�n[h�pc�F�A	Ѩ�I|{�F:7!����2]m�e��w劣j�oa���T�3������u�Iη&����:>���v C����W|���2d����d:��`L֠g���C,c,�Z���ph@mJ��� 44��	G!�F�.�8�5Ye��qw�[���FK��a�e!id���oy����At�荖�*1H�$�z�G0"ƭU,��hJ�x      �   �   x�e�1�0E��9Ama7�Ue�9)S��� �_�Z���}{�;냀����p:�Ws�p;4����zQ�.?8�]c��<���v0��8@�ټS�J����)�����׺V=Ұ ���҃��Tft�7
���8��z�_Zn����Bk�@#K�      �   5   x�3�tN�+)J��t,�S�M,VH��ITH��
�E
���1~@����� m��      �      x������ � �      �   �   x�M�M
�1��]�$������i~
�+��۝¬�,�P!�9�8\<��i��C�S󑴌�%�����<�C7!Pw�!s�0��Z�'Q��$]0����yߗ^"�w>?e���6�\
�̟x�۵�ε�/�f0�     