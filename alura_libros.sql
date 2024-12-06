--
-- PostgreSQL database dump
--

-- Dumped from database version 17.1
-- Dumped by pg_dump version 17.0

-- Started on 2024-12-06 09:25:53

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 218 (class 1259 OID 24656)
-- Name: libro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.libro (
    id bigint NOT NULL,
    autor character varying(255) NOT NULL,
    fecha_fallecimiento_autor integer,
    fecha_nacimiento_autor integer,
    idioma character varying(255),
    numero_de_descargas double precision,
    titulo character varying(255)
);


ALTER TABLE public.libro OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24655)
-- Name: libro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.libro ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.libro_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 4791 (class 0 OID 24656)
-- Dependencies: 218
-- Data for Name: libro; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.libro (id, autor, fecha_fallecimiento_autor, fecha_nacimiento_autor, idioma, numero_de_descargas, titulo) FROM stdin;
1	Austen, Jane	1817	1775	en	56305	Pride and Prejudice
2	Cervantes Saavedra, Miguel de	1616	1547	es	16017	Don Quijote
3	Austen, Jane	1817	1775	en	9314	Emma
4	Shakespeare, William	1616	1564	en	68412	Romeo and Juliet
\.


--
-- TOC entry 4797 (class 0 OID 0)
-- Dependencies: 217
-- Name: libro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.libro_id_seq', 4, true);


--
-- TOC entry 4642 (class 2606 OID 24662)
-- Name: libro libro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro
    ADD CONSTRAINT libro_pkey PRIMARY KEY (id);


--
-- TOC entry 4644 (class 2606 OID 24664)
-- Name: libro ukqn9pauuvgqh324jmfru6wp6kj; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.libro
    ADD CONSTRAINT ukqn9pauuvgqh324jmfru6wp6kj UNIQUE (titulo);


-- Completed on 2024-12-06 09:25:53

--
-- PostgreSQL database dump complete
--

