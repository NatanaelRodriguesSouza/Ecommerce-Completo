--
-- PostgreSQL database dump
--



-- Dumped from database version 18.1
-- Dumped by pg_dump version 18.1

-- Started on 2026-01-06 16:46:32

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5234 (class 1262 OID 16716)
-- Name: loja_virtual_mentoria; Type: DATABASE; Schema: -; Owner: postgres
--

--CREATE DATABASE loja_virtual_mentoria WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE loja_virtual_mentoria OWNER TO postgres;

--\connect loja_virtual_mentoria


SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 259 (class 1255 OID 17073)
-- Name: validachave(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validachave() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    existe INTEGER;
BEGIN

    SELECT COUNT(1)
    INTO existe
    FROM tb_pessoa_fisica
    WHERE id = NEW.pessoa_id;

    IF existe <= 0 THEN

        SELECT COUNT(1)
        INTO existe
        FROM tb_pessoa_juridica
        WHERE id = NEW.pessoa_id;

        IF existe <= 0 THEN
            RAISE EXCEPTION
                'Não foi encontrado o ID da pessoa (PF ou PJ) para realizar a associação';
        END IF;

    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.validachave() OWNER TO postgres;

--
-- TOC entry 260 (class 1255 OID 17082)
-- Name: validachave2(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.validachave2() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    existe INTEGER;
BEGIN

    SELECT COUNT(1)
    INTO existe
    FROM tb_pessoa_fisica
    WHERE id =  NEW.pessoa_forn_id;

    IF existe <= 0 THEN

        SELECT COUNT(1)
        INTO existe
        FROM tb_pessoa_juridica
        WHERE id = NEW.pessoa_forn_id;

        IF existe <= 0 THEN
            RAISE EXCEPTION
                'Não foi encontrado o ID da pessoa (PF ou PJ) para realizar a associação';
        END IF;

    END IF;

    RETURN NEW;
END;
$$;


ALTER FUNCTION public.validachave2() OWNER TO postgres;

--
-- TOC entry 237 (class 1259 OID 16914)
-- Name: seq_acesso; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_acesso
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_acesso OWNER TO postgres;

--
-- TOC entry 258 (class 1259 OID 17055)
-- Name: seq_avaliacao_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_avaliacao_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_avaliacao_produto OWNER TO postgres;

--
-- TOC entry 238 (class 1259 OID 16915)
-- Name: seq_categoria_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_categoria_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_categoria_produto OWNER TO postgres;

--
-- TOC entry 240 (class 1259 OID 16917)
-- Name: seq_conta_pagar; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_conta_pagar
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_conta_pagar OWNER TO postgres;

--
-- TOC entry 239 (class 1259 OID 16916)
-- Name: seq_conta_receber; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_conta_receber
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_conta_receber OWNER TO postgres;

--
-- TOC entry 241 (class 1259 OID 16918)
-- Name: seq_cup_desc; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_cup_desc
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_cup_desc OWNER TO postgres;

--
-- TOC entry 242 (class 1259 OID 16919)
-- Name: seq_endereco; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_endereco
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_endereco OWNER TO postgres;

--
-- TOC entry 243 (class 1259 OID 16920)
-- Name: seq_forma_pagamento; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_forma_pagamento
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_forma_pagamento OWNER TO postgres;

--
-- TOC entry 244 (class 1259 OID 16921)
-- Name: seq_imagem_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_imagem_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_imagem_produto OWNER TO postgres;

--
-- TOC entry 256 (class 1259 OID 17029)
-- Name: seq_item_venda_loja; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_item_venda_loja
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_item_venda_loja OWNER TO postgres;

--
-- TOC entry 245 (class 1259 OID 16922)
-- Name: seq_marca_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_marca_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_marca_produto OWNER TO postgres;

--
-- TOC entry 248 (class 1259 OID 16925)
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_compra
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 246 (class 1259 OID 16923)
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_fiscal_venda
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 247 (class 1259 OID 16924)
-- Name: seq_nota_item_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_nota_item_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_nota_item_produto OWNER TO postgres;

--
-- TOC entry 249 (class 1259 OID 16926)
-- Name: seq_pessoa; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_pessoa
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_pessoa OWNER TO postgres;

--
-- TOC entry 250 (class 1259 OID 16927)
-- Name: seq_produto; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_produto OWNER TO postgres;

--
-- TOC entry 251 (class 1259 OID 16928)
-- Name: seq_status_rastreio; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_status_rastreio
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_status_rastreio OWNER TO postgres;

--
-- TOC entry 252 (class 1259 OID 16929)
-- Name: seq_usuario; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_usuario OWNER TO postgres;

--
-- TOC entry 254 (class 1259 OID 16993)
-- Name: seq_vd_cp_loja_virt; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_vd_cp_loja_virt
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_vd_cp_loja_virt OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 16717)
-- Name: tb_acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_acesso (
    id bigint NOT NULL,
    descricao character varying(255)
);


ALTER TABLE public.tb_acesso OWNER TO postgres;

--
-- TOC entry 257 (class 1259 OID 17045)
-- Name: tb_avaliacao_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_avaliacao_produto (
    id bigint NOT NULL,
    descricao character varying(255) NOT NULL,
    nota integer NOT NULL,
    pessoa_id bigint NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.tb_avaliacao_produto OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16723)
-- Name: tb_categoria_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_categoria_produto (
    id bigint NOT NULL,
    nome_desc character varying(255)
);


ALTER TABLE public.tb_categoria_produto OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16729)
-- Name: tb_conta_pagar; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_conta_pagar (
    id bigint NOT NULL,
    valor_desconto numeric(38,2),
    data_pagamento date,
    data_vencimento date,
    descricao character varying(255),
    status character varying(255),
    valor_total numeric(38,2),
    forma_pagamento_id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    pessoa_fornecedor_id bigint NOT NULL,
    CONSTRAINT tb_conta_pagar_status_check CHECK (((status)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying, 'ALUGUEL'::character varying, 'FUNCIONARIO'::character varying, 'NEGOCIADA'::character varying])::text[])))
);


ALTER TABLE public.tb_conta_pagar OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16741)
-- Name: tb_conta_receber; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_conta_receber (
    id bigint NOT NULL,
    valor_desconto numeric(38,2),
    data_pagamento date,
    data_vencimento date,
    descricao character varying(255),
    status character varying(255),
    valor_total numeric(38,2),
    pessoa_id bigint NOT NULL,
    CONSTRAINT tb_conta_receber_status_check CHECK (((status)::text = ANY ((ARRAY['COBRANCA'::character varying, 'VENCIDA'::character varying, 'ABERTA'::character varying, 'QUITADA'::character varying])::text[])))
);


ALTER TABLE public.tb_conta_receber OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16751)
-- Name: tb_cup_desc; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_cup_desc (
    id bigint NOT NULL,
    cod_desc character varying(255) NOT NULL,
    data_validade_cupom date NOT NULL,
    valor_porcent_desc numeric(38,2),
    valor_real_desc numeric(38,2)
);


ALTER TABLE public.tb_cup_desc OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16759)
-- Name: tb_endereco; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_endereco (
    id bigint NOT NULL,
    bairro character varying(255) NOT NULL,
    cep character varying(255) NOT NULL,
    cidade character varying(255) NOT NULL,
    complemento character varying(255),
    numero character varying(255) NOT NULL,
    rua_logra character varying(255) NOT NULL,
    tipo_endereco character varying(255),
    uf character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL,
    CONSTRAINT tb_endereco_tipo_endereco_check CHECK (((tipo_endereco)::text = ANY ((ARRAY['COBRANCA'::character varying, 'ENTREGA'::character varying])::text[])))
);


ALTER TABLE public.tb_endereco OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16775)
-- Name: tb_forma_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_forma_pagamento (
    id bigint NOT NULL,
    discricao character varying(255) NOT NULL
);


ALTER TABLE public.tb_forma_pagamento OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16782)
-- Name: tb_imagem_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_imagem_produto (
    id bigint NOT NULL,
    imagem_miniatura text NOT NULL,
    imagem_original text NOT NULL,
    empresa_id bigint NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.tb_imagem_produto OWNER TO postgres;

--
-- TOC entry 255 (class 1259 OID 17019)
-- Name: tb_item_venda_loja; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_item_venda_loja (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    produto_id bigint NOT NULL,
    venda_compra_loja_virtu_id bigint NOT NULL
);


ALTER TABLE public.tb_item_venda_loja OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16794)
-- Name: tb_marca_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_marca_produto (
    id bigint NOT NULL,
    nome_desc character varying(255)
);


ALTER TABLE public.tb_marca_produto OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 16824)
-- Name: tb_nota_fiscal_compra; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_nota_fiscal_compra (
    id bigint NOT NULL,
    data_compra date NOT NULL,
    descricao_obs character varying(255),
    numero_nota character varying(255) NOT NULL,
    serie_nota character varying(255) NOT NULL,
    valor_desconto numeric(38,2),
    valor_icms numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    conta_pagar_id bigint NOT NULL,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.tb_nota_fiscal_compra OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 16800)
-- Name: tb_nota_fiscal_venda; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_nota_fiscal_venda (
    id bigint NOT NULL,
    chave character varying(255) NOT NULL,
    numero character varying(255) NOT NULL,
    pdf text NOT NULL,
    serie character varying(255) NOT NULL,
    tipo character varying(255) NOT NULL,
    xml text NOT NULL
);


ALTER TABLE public.tb_nota_fiscal_venda OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 16814)
-- Name: tb_nota_item_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_nota_item_produto (
    id bigint NOT NULL,
    quantidade double precision NOT NULL,
    empresa_id bigint NOT NULL,
    nota_fiscal_compra_id bigint NOT NULL,
    produto_id bigint NOT NULL
);


ALTER TABLE public.tb_nota_item_produto OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 16839)
-- Name: tb_pessoa_fisica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_pessoa_fisica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    cpf character varying(255) NOT NULL,
    data_nascimento date
);


ALTER TABLE public.tb_pessoa_fisica OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 16851)
-- Name: tb_pessoa_juridica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_pessoa_juridica (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL,
    categoria character varying(255),
    cnpj character varying(255) NOT NULL,
    insc_estadual character varying(255) NOT NULL,
    insc_municipal character varying(255),
    nome_fantasia character varying(255) NOT NULL,
    razao_social character varying(255) NOT NULL
);


ALTER TABLE public.tb_pessoa_juridica OWNER TO postgres;

--
-- TOC entry 233 (class 1259 OID 16866)
-- Name: tb_produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_produto (
    id bigint NOT NULL,
    alerta_qtde_estoque boolean,
    altura double precision NOT NULL,
    ativo boolean NOT NULL,
    descricao text NOT NULL,
    largura double precision NOT NULL,
    link_youtube character varying(255),
    nome character varying(255) NOT NULL,
    peso double precision NOT NULL,
    profundidade double precision NOT NULL,
    qtd_estoque integer NOT NULL,
    qtde_alerta_estoque integer,
    qtde_clique integer,
    tipo_unidade character varying(255) NOT NULL,
    valor_venda numeric(38,2) NOT NULL
);


ALTER TABLE public.tb_produto OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 16884)
-- Name: tb_status_rastreio; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_status_rastreio (
    id bigint NOT NULL,
    centro_de_destibuicao character varying(255),
    cidade character varying(255),
    estado character varying(255),
    status character varying(255),
    venda_compra_loja_virtual_id bigint NOT NULL
);


ALTER TABLE public.tb_status_rastreio OWNER TO postgres;

--
-- TOC entry 235 (class 1259 OID 16892)
-- Name: tb_usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_usuario (
    id bigint NOT NULL,
    data_atual_senha date NOT NULL,
    login character varying(255) NOT NULL,
    senha character varying(255) NOT NULL,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.tb_usuario OWNER TO postgres;

--
-- TOC entry 236 (class 1259 OID 16903)
-- Name: tb_usuarios_acesso; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_usuarios_acesso (
    usuario_id bigint NOT NULL,
    acesso_id bigint NOT NULL
);


ALTER TABLE public.tb_usuarios_acesso OWNER TO postgres;

--
-- TOC entry 253 (class 1259 OID 16976)
-- Name: tb_vd_cp_loja_virt; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_vd_cp_loja_virt (
    id bigint NOT NULL,
    data_entrega date NOT NULL,
    data_venda date NOT NULL,
    dia_entrega integer NOT NULL,
    valor_desconto numeric(38,2),
    valor_fret numeric(38,2) NOT NULL,
    valor_total numeric(38,2) NOT NULL,
    cupom_desc_id bigint,
    endereco_cobranca_id bigint NOT NULL,
    endereco_entrega_id bigint NOT NULL,
    forma_pagamento_id bigint NOT NULL,
    nota_fiscal_venda_id bigint,
    pessoa_id bigint NOT NULL
);


ALTER TABLE public.tb_vd_cp_loja_virt OWNER TO postgres;

--
-- TOC entry 5189 (class 0 OID 16717)
-- Dependencies: 219
-- Data for Name: tb_acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5227 (class 0 OID 17045)
-- Dependencies: 257
-- Data for Name: tb_avaliacao_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tb_avaliacao_produto (id, descricao, nota, pessoa_id, produto_id) VALUES (4, 'Produto excelente, porém teste de trigger', 5, 1, 1);


--
-- TOC entry 5190 (class 0 OID 16723)
-- Dependencies: 220
-- Data for Name: tb_categoria_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5191 (class 0 OID 16729)
-- Dependencies: 221
-- Data for Name: tb_conta_pagar; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5192 (class 0 OID 16741)
-- Dependencies: 222
-- Data for Name: tb_conta_receber; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5193 (class 0 OID 16751)
-- Dependencies: 223
-- Data for Name: tb_cup_desc; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5194 (class 0 OID 16759)
-- Dependencies: 224
-- Data for Name: tb_endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5195 (class 0 OID 16775)
-- Dependencies: 225
-- Data for Name: tb_forma_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5196 (class 0 OID 16782)
-- Dependencies: 226
-- Data for Name: tb_imagem_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5225 (class 0 OID 17019)
-- Dependencies: 255
-- Data for Name: tb_item_venda_loja; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5197 (class 0 OID 16794)
-- Dependencies: 227
-- Data for Name: tb_marca_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5200 (class 0 OID 16824)
-- Dependencies: 230
-- Data for Name: tb_nota_fiscal_compra; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5198 (class 0 OID 16800)
-- Dependencies: 228
-- Data for Name: tb_nota_fiscal_venda; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5199 (class 0 OID 16814)
-- Dependencies: 229
-- Data for Name: tb_nota_item_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5201 (class 0 OID 16839)
-- Dependencies: 231
-- Data for Name: tb_pessoa_fisica; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tb_pessoa_fisica (id, email, nome, telefone, cpf, data_nascimento) VALUES (1, 'natanael@gmail.com', 'natanael', '79900999887766', '87878787', '2005-12-13');


--
-- TOC entry 5202 (class 0 OID 16851)
-- Dependencies: 232
-- Data for Name: tb_pessoa_juridica; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5203 (class 0 OID 16866)
-- Dependencies: 233
-- Data for Name: tb_produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tb_produto (id, alerta_qtde_estoque, altura, ativo, descricao, largura, link_youtube, nome, peso, profundidade, qtd_estoque, qtde_alerta_estoque, qtde_clique, tipo_unidade, valor_venda) VALUES (1, false, 15.5, true, 'Produto exemplo para teste de cadastro', 10.2, 'https://youtube.com/exemplo', 'Produto Exemplo', 1.25, 20, 100, 10, 0, 'UNIDADE', 199.90);
INSERT INTO public.tb_produto (id, alerta_qtde_estoque, altura, ativo, descricao, largura, link_youtube, nome, peso, profundidade, qtd_estoque, qtde_alerta_estoque, qtde_clique, tipo_unidade, valor_venda) VALUES (2, false, 15.5, true, 'Produto exemplo para teste de cadastro', 10.2, 'https://youtube.com/exemplo', 'Produto Exemplo', 1.25, 20, 100, 10, 0, 'UNIDADE', 199.90);


--
-- TOC entry 5204 (class 0 OID 16884)
-- Dependencies: 234
-- Data for Name: tb_status_rastreio; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5205 (class 0 OID 16892)
-- Dependencies: 235
-- Data for Name: tb_usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5206 (class 0 OID 16903)
-- Dependencies: 236
-- Data for Name: tb_usuarios_acesso; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5223 (class 0 OID 16976)
-- Dependencies: 253
-- Data for Name: tb_vd_cp_loja_virt; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 5235 (class 0 OID 0)
-- Dependencies: 237
-- Name: seq_acesso; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_acesso', 1, false);


--
-- TOC entry 5236 (class 0 OID 0)
-- Dependencies: 258
-- Name: seq_avaliacao_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_avaliacao_produto', 4, true);


--
-- TOC entry 5237 (class 0 OID 0)
-- Dependencies: 238
-- Name: seq_categoria_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_categoria_produto', 1, false);


--
-- TOC entry 5238 (class 0 OID 0)
-- Dependencies: 240
-- Name: seq_conta_pagar; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_conta_pagar', 1, false);


--
-- TOC entry 5239 (class 0 OID 0)
-- Dependencies: 239
-- Name: seq_conta_receber; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_conta_receber', 1, false);


--
-- TOC entry 5240 (class 0 OID 0)
-- Dependencies: 241
-- Name: seq_cup_desc; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_cup_desc', 1, false);


--
-- TOC entry 5241 (class 0 OID 0)
-- Dependencies: 242
-- Name: seq_endereco; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_endereco', 1, false);


--
-- TOC entry 5242 (class 0 OID 0)
-- Dependencies: 243
-- Name: seq_forma_pagamento; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_forma_pagamento', 1, false);


--
-- TOC entry 5243 (class 0 OID 0)
-- Dependencies: 244
-- Name: seq_imagem_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_imagem_produto', 1, false);


--
-- TOC entry 5244 (class 0 OID 0)
-- Dependencies: 256
-- Name: seq_item_venda_loja; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_item_venda_loja', 1, false);


--
-- TOC entry 5245 (class 0 OID 0)
-- Dependencies: 245
-- Name: seq_marca_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_marca_produto', 1, false);


--
-- TOC entry 5246 (class 0 OID 0)
-- Dependencies: 248
-- Name: seq_nota_fiscal_compra; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_compra', 1, false);


--
-- TOC entry 5247 (class 0 OID 0)
-- Dependencies: 246
-- Name: seq_nota_fiscal_venda; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_fiscal_venda', 1, false);


--
-- TOC entry 5248 (class 0 OID 0)
-- Dependencies: 247
-- Name: seq_nota_item_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_nota_item_produto', 1, false);


--
-- TOC entry 5249 (class 0 OID 0)
-- Dependencies: 249
-- Name: seq_pessoa; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_pessoa', 1, false);


--
-- TOC entry 5250 (class 0 OID 0)
-- Dependencies: 250
-- Name: seq_produto; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_produto', 2, true);


--
-- TOC entry 5251 (class 0 OID 0)
-- Dependencies: 251
-- Name: seq_status_rastreio; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_status_rastreio', 1, false);


--
-- TOC entry 5252 (class 0 OID 0)
-- Dependencies: 252
-- Name: seq_usuario; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_usuario', 1, false);


--
-- TOC entry 5253 (class 0 OID 0)
-- Dependencies: 254
-- Name: seq_vd_cp_loja_virt; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_vd_cp_loja_virt', 1, false);


--
-- TOC entry 4960 (class 2606 OID 16722)
-- Name: tb_acesso tb_acesso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_acesso
    ADD CONSTRAINT tb_acesso_pkey PRIMARY KEY (id);


--
-- TOC entry 5006 (class 2606 OID 17054)
-- Name: tb_avaliacao_produto tb_avaliacao_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_avaliacao_produto
    ADD CONSTRAINT tb_avaliacao_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4962 (class 2606 OID 16728)
-- Name: tb_categoria_produto tb_categoria_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_categoria_produto
    ADD CONSTRAINT tb_categoria_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4964 (class 2606 OID 16740)
-- Name: tb_conta_pagar tb_conta_pagar_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_conta_pagar
    ADD CONSTRAINT tb_conta_pagar_pkey PRIMARY KEY (id);


--
-- TOC entry 4966 (class 2606 OID 16750)
-- Name: tb_conta_receber tb_conta_receber_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_conta_receber
    ADD CONSTRAINT tb_conta_receber_pkey PRIMARY KEY (id);


--
-- TOC entry 4968 (class 2606 OID 16758)
-- Name: tb_cup_desc tb_cup_desc_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_cup_desc
    ADD CONSTRAINT tb_cup_desc_pkey PRIMARY KEY (id);


--
-- TOC entry 4970 (class 2606 OID 16774)
-- Name: tb_endereco tb_endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_endereco
    ADD CONSTRAINT tb_endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 4972 (class 2606 OID 16781)
-- Name: tb_forma_pagamento tb_forma_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_forma_pagamento
    ADD CONSTRAINT tb_forma_pagamento_pkey PRIMARY KEY (id);


--
-- TOC entry 4974 (class 2606 OID 16793)
-- Name: tb_imagem_produto tb_imagem_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_imagem_produto
    ADD CONSTRAINT tb_imagem_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 5004 (class 2606 OID 17027)
-- Name: tb_item_venda_loja tb_item_venda_loja_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_item_venda_loja
    ADD CONSTRAINT tb_item_venda_loja_pkey PRIMARY KEY (id);


--
-- TOC entry 4976 (class 2606 OID 16799)
-- Name: tb_marca_produto tb_marca_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_marca_produto
    ADD CONSTRAINT tb_marca_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4982 (class 2606 OID 16838)
-- Name: tb_nota_fiscal_compra tb_nota_fiscal_compra_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_fiscal_compra
    ADD CONSTRAINT tb_nota_fiscal_compra_pkey PRIMARY KEY (id);


--
-- TOC entry 4978 (class 2606 OID 16813)
-- Name: tb_nota_fiscal_venda tb_nota_fiscal_venda_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_fiscal_venda
    ADD CONSTRAINT tb_nota_fiscal_venda_pkey PRIMARY KEY (id);


--
-- TOC entry 4980 (class 2606 OID 16823)
-- Name: tb_nota_item_produto tb_nota_item_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_item_produto
    ADD CONSTRAINT tb_nota_item_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4984 (class 2606 OID 16850)
-- Name: tb_pessoa_fisica tb_pessoa_fisica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pessoa_fisica
    ADD CONSTRAINT tb_pessoa_fisica_pkey PRIMARY KEY (id);


--
-- TOC entry 4986 (class 2606 OID 16865)
-- Name: tb_pessoa_juridica tb_pessoa_juridica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_pessoa_juridica
    ADD CONSTRAINT tb_pessoa_juridica_pkey PRIMARY KEY (id);


--
-- TOC entry 4988 (class 2606 OID 16883)
-- Name: tb_produto tb_produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_produto
    ADD CONSTRAINT tb_produto_pkey PRIMARY KEY (id);


--
-- TOC entry 4990 (class 2606 OID 16891)
-- Name: tb_status_rastreio tb_status_rastreio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_status_rastreio
    ADD CONSTRAINT tb_status_rastreio_pkey PRIMARY KEY (id);


--
-- TOC entry 4992 (class 2606 OID 16902)
-- Name: tb_usuario tb_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_usuario
    ADD CONSTRAINT tb_usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 5000 (class 2606 OID 16990)
-- Name: tb_vd_cp_loja_virt tb_vd_cp_loja_virt_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT tb_vd_cp_loja_virt_pkey PRIMARY KEY (id);


--
-- TOC entry 4994 (class 2606 OID 16909)
-- Name: tb_usuario uk1vofibjsgo77e7km8wowva1qe; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_usuario
    ADD CONSTRAINT uk1vofibjsgo77e7km8wowva1qe UNIQUE (login);


--
-- TOC entry 4996 (class 2606 OID 16913)
-- Name: tb_usuarios_acesso uk2uatp8ygefqeuhejeed3baphi; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_usuarios_acesso
    ADD CONSTRAINT uk2uatp8ygefqeuhejeed3baphi UNIQUE (acesso_id);


--
-- TOC entry 5002 (class 2606 OID 16992)
-- Name: tb_vd_cp_loja_virt uk44dmdjcdoj4few4sapi741mpe; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT uk44dmdjcdoj4few4sapi741mpe UNIQUE (nota_fiscal_venda_id);


--
-- TOC entry 4998 (class 2606 OID 16911)
-- Name: tb_usuarios_acesso unique_acesso_user; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_usuarios_acesso
    ADD CONSTRAINT unique_acesso_user UNIQUE (usuario_id, acesso_id);


--
-- TOC entry 5040 (class 2620 OID 17078)
-- Name: tb_avaliacao_produto validachavepessoaavaliacaoprodutoinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaavaliacaoprodutoinsert BEFORE INSERT ON public.tb_avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5041 (class 2620 OID 17077)
-- Name: tb_avaliacao_produto validachavepessoaavaliacaoprodutoupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoaavaliacaoprodutoupdate BEFORE UPDATE ON public.tb_avaliacao_produto FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5026 (class 2620 OID 17081)
-- Name: tb_conta_pagar validachavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.tb_conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5030 (class 2620 OID 17086)
-- Name: tb_conta_receber validachavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.tb_conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5032 (class 2620 OID 17088)
-- Name: tb_endereco validachavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.tb_endereco FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5034 (class 2620 OID 17090)
-- Name: tb_nota_fiscal_compra validachavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.tb_nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5036 (class 2620 OID 17092)
-- Name: tb_usuario validachavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.tb_usuario FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5038 (class 2620 OID 17094)
-- Name: tb_vd_cp_loja_virt validachavepessoacontapagarinsert; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert BEFORE INSERT ON public.tb_vd_cp_loja_virt FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5027 (class 2620 OID 17084)
-- Name: tb_conta_pagar validachavepessoacontapagarinsert2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarinsert2 BEFORE INSERT ON public.tb_conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachave2();


--
-- TOC entry 5028 (class 2620 OID 17080)
-- Name: tb_conta_pagar validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.tb_conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5031 (class 2620 OID 17085)
-- Name: tb_conta_receber validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.tb_conta_receber FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5033 (class 2620 OID 17087)
-- Name: tb_endereco validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.tb_endereco FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5035 (class 2620 OID 17089)
-- Name: tb_nota_fiscal_compra validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.tb_nota_fiscal_compra FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5037 (class 2620 OID 17091)
-- Name: tb_usuario validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.tb_usuario FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5039 (class 2620 OID 17093)
-- Name: tb_vd_cp_loja_virt validachavepessoacontapagarupdate; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate BEFORE UPDATE ON public.tb_vd_cp_loja_virt FOR EACH ROW EXECUTE FUNCTION public.validachave();


--
-- TOC entry 5029 (class 2620 OID 17083)
-- Name: tb_conta_pagar validachavepessoacontapagarupdate2; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER validachavepessoacontapagarupdate2 BEFORE UPDATE ON public.tb_conta_pagar FOR EACH ROW EXECUTE FUNCTION public.validachave2();


--
-- TOC entry 5015 (class 2606 OID 16965)
-- Name: tb_usuarios_acesso acesso_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_usuarios_acesso
    ADD CONSTRAINT acesso_fk FOREIGN KEY (acesso_id) REFERENCES public.tb_acesso(id);


--
-- TOC entry 5013 (class 2606 OID 16960)
-- Name: tb_nota_fiscal_compra contapagar_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_fiscal_compra
    ADD CONSTRAINT contapagar_fk FOREIGN KEY (conta_pagar_id) REFERENCES public.tb_conta_pagar(id);


--
-- TOC entry 5017 (class 2606 OID 16994)
-- Name: tb_vd_cp_loja_virt cupom_desc_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT cupom_desc_fk FOREIGN KEY (cupom_desc_id) REFERENCES public.tb_cup_desc(id);


--
-- TOC entry 5008 (class 2606 OID 16935)
-- Name: tb_imagem_produto empresa_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_imagem_produto
    ADD CONSTRAINT empresa_id_fk FOREIGN KEY (empresa_id) REFERENCES public.tb_pessoa_juridica(id);


--
-- TOC entry 5010 (class 2606 OID 16945)
-- Name: tb_nota_item_produto empresa_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_item_produto
    ADD CONSTRAINT empresa_id_fk FOREIGN KEY (empresa_id) REFERENCES public.tb_pessoa_juridica(id);


--
-- TOC entry 5018 (class 2606 OID 16999)
-- Name: tb_vd_cp_loja_virt endereco_cobranca_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT endereco_cobranca_fk FOREIGN KEY (endereco_cobranca_id) REFERENCES public.tb_endereco(id);


--
-- TOC entry 5019 (class 2606 OID 17004)
-- Name: tb_vd_cp_loja_virt endereco_entrega_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT endereco_entrega_fk FOREIGN KEY (endereco_entrega_id) REFERENCES public.tb_endereco(id);


--
-- TOC entry 5020 (class 2606 OID 17009)
-- Name: tb_vd_cp_loja_virt forma_pagamento_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT forma_pagamento_fk FOREIGN KEY (forma_pagamento_id) REFERENCES public.tb_forma_pagamento(id);


--
-- TOC entry 5011 (class 2606 OID 16950)
-- Name: tb_nota_item_produto nota_fiscal_compra_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_item_produto
    ADD CONSTRAINT nota_fiscal_compra_fk FOREIGN KEY (nota_fiscal_compra_id) REFERENCES public.tb_nota_fiscal_compra(id);


--
-- TOC entry 5021 (class 2606 OID 17014)
-- Name: tb_vd_cp_loja_virt nota_fiscal_venda_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_vd_cp_loja_virt
    ADD CONSTRAINT nota_fiscal_venda_fk FOREIGN KEY (nota_fiscal_venda_id) REFERENCES public.tb_nota_fiscal_venda(id);


--
-- TOC entry 5007 (class 2606 OID 16930)
-- Name: tb_conta_pagar pagamento_i; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_conta_pagar
    ADD CONSTRAINT pagamento_i FOREIGN KEY (forma_pagamento_id) REFERENCES public.tb_forma_pagamento(id);


--
-- TOC entry 5024 (class 2606 OID 17056)
-- Name: tb_avaliacao_produto pessoa_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_avaliacao_produto
    ADD CONSTRAINT pessoa_fk FOREIGN KEY (pessoa_id) REFERENCES public.tb_pessoa_fisica(id);


--
-- TOC entry 5025 (class 2606 OID 17061)
-- Name: tb_avaliacao_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_avaliacao_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.tb_produto(id);


--
-- TOC entry 5009 (class 2606 OID 16940)
-- Name: tb_imagem_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_imagem_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.tb_produto(id);


--
-- TOC entry 5022 (class 2606 OID 17030)
-- Name: tb_item_venda_loja produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_item_venda_loja
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.tb_produto(id);


--
-- TOC entry 5012 (class 2606 OID 16955)
-- Name: tb_nota_item_produto produto_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_nota_item_produto
    ADD CONSTRAINT produto_fk FOREIGN KEY (produto_id) REFERENCES public.tb_produto(id);


--
-- TOC entry 5016 (class 2606 OID 16970)
-- Name: tb_usuarios_acesso usuario_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_usuarios_acesso
    ADD CONSTRAINT usuario_fk FOREIGN KEY (usuario_id) REFERENCES public.tb_usuario(id);


--
-- TOC entry 5023 (class 2606 OID 17035)
-- Name: tb_item_venda_loja venda_compraloja_virtu_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_item_venda_loja
    ADD CONSTRAINT venda_compraloja_virtu_fk FOREIGN KEY (venda_compra_loja_virtu_id) REFERENCES public.tb_vd_cp_loja_virt(id);


--
-- TOC entry 5014 (class 2606 OID 17040)
-- Name: tb_status_rastreio vendacompralojavirtual_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_status_rastreio
    ADD CONSTRAINT vendacompralojavirtual_fk FOREIGN KEY (venda_compra_loja_virtual_id) REFERENCES public.tb_vd_cp_loja_virt(id);


-- Completed on 2026-01-06 16:46:33

--
-- PostgreSQL database dump complete
--


