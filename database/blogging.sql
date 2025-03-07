--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-03-06 23:03:21

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
-- TOC entry 217 (class 1259 OID 16818)
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16865)
-- Name: tb_comment; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_comment (
    id integer NOT NULL,
    content text NOT NULL,
    user_id integer NOT NULL,
    post_id integer NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tb_comment OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 16864)
-- Name: tb_comment_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_comment_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tb_comment_id_seq OWNER TO postgres;

--
-- TOC entry 4847 (class 0 OID 0)
-- Dependencies: 224
-- Name: tb_comment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_comment_id_seq OWNED BY public.tb_comment.id;


--
-- TOC entry 223 (class 1259 OID 16853)
-- Name: tb_post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_post (
    id integer NOT NULL,
    title character varying(200) NOT NULL,
    thumbnail character varying(255) DEFAULT 'no-thumbnail.png'::character varying,
    content text NOT NULL,
    category character varying(100) NOT NULL,
    tags text[],
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.tb_post OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16852)
-- Name: tb_post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tb_post_id_seq OWNER TO postgres;

--
-- TOC entry 4848 (class 0 OID 0)
-- Dependencies: 222
-- Name: tb_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_post_id_seq OWNED BY public.tb_post.id;


--
-- TOC entry 219 (class 1259 OID 16828)
-- Name: tb_role; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_role (
    id integer NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.tb_role OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16827)
-- Name: tb_role_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_role_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tb_role_id_seq OWNER TO postgres;

--
-- TOC entry 4849 (class 0 OID 0)
-- Dependencies: 218
-- Name: tb_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_role_id_seq OWNED BY public.tb_role.id;


--
-- TOC entry 221 (class 1259 OID 16835)
-- Name: tb_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tb_user (
    id integer NOT NULL,
    full_name character varying(100) NOT NULL,
    email character varying(200) NOT NULL,
    password_hash character varying(64) NOT NULL,
    profile_url character varying(255) DEFAULT 'user.png'::character varying,
    role_id integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.tb_user OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16834)
-- Name: tb_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tb_user_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tb_user_id_seq OWNER TO postgres;

--
-- TOC entry 4850 (class 0 OID 0)
-- Dependencies: 220
-- Name: tb_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tb_user_id_seq OWNED BY public.tb_user.id;


--
-- TOC entry 4669 (class 2604 OID 16868)
-- Name: tb_comment id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_comment ALTER COLUMN id SET DEFAULT nextval('public.tb_comment_id_seq'::regclass);


--
-- TOC entry 4665 (class 2604 OID 16856)
-- Name: tb_post id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_post ALTER COLUMN id SET DEFAULT nextval('public.tb_post_id_seq'::regclass);


--
-- TOC entry 4661 (class 2604 OID 16831)
-- Name: tb_role id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_role ALTER COLUMN id SET DEFAULT nextval('public.tb_role_id_seq'::regclass);


--
-- TOC entry 4662 (class 2604 OID 16838)
-- Name: tb_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user ALTER COLUMN id SET DEFAULT nextval('public.tb_user_id_seq'::regclass);


--
-- TOC entry 4833 (class 0 OID 16818)
-- Dependencies: 217
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	01	CREATE USER AND ROLE TABLE	SQL	V01__CREATE_USER_AND_ROLE_TABLE.sql	-1062547655	postgres	2025-03-03 16:52:23.679686	52	t
2	02	CREATE POST AND COMMENT TABLE	SQL	V02__CREATE_POST_AND_COMMENT_TABLE.sql	-405406239	postgres	2025-03-03 16:52:23.785211	22	t
\.


--
-- TOC entry 4841 (class 0 OID 16865)
-- Dependencies: 225
-- Data for Name: tb_comment; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_comment (id, content, user_id, post_id, created_at, updated_at) FROM stdin;
4	React has much more to do, so keep on my posts ;).	4	3	2025-03-04 20:29:24.346709	2025-03-04 20:29:24.346709
5	React is very good!.	2	3	2025-03-04 20:29:35.742011	2025-03-04 20:30:01.223103
7	Very good!	4	6	2025-03-06 16:46:47.687332	2025-03-06 20:55:06.665969
8	Muito legal esse post negão!	2	6	2025-03-06 21:11:45.187536	2025-03-06 21:11:45.187536
\.


--
-- TOC entry 4839 (class 0 OID 16853)
-- Dependencies: 223
-- Data for Name: tb_post; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_post (id, title, thumbnail, content, category, tags, created_at, updated_at) FROM stdin;
6	Getting Started with Python for Data Science	no-thumbnail.png	<h1>Python for Data Science</h1><p>Python has become one of the most popular programming languages for data science due to its simplicity and the powerful libraries it offers, such as NumPy, Pandas, and Matplotlib.</p><h2>Setting Up Python for Data Science</h2><p>To get started with Python for data science, you'll need to install some essential libraries:</p><ul><li><strong>NumPy</strong>: A library for numerical computations.</li><li><strong>Pandas</strong>: A library for data manipulation and analysis.</li><li><strong>Matplotlib</strong>: A library for data visualization.</li></ul><h3>Example: Loading Data with Pandas</h3><pre><code>import pandas as pd\n\ndf = pd.read_csv('data.csv')\nprint(df.head())</code></pre><p>With Python, you can easily clean, analyze, and visualize data to gain meaningful insights and make data-driven decisions.</p>	Python	{Python,"Data Science",Programming}	2025-03-05 14:54:15.26586	2025-03-05 14:54:15.26586
5	Mastering CSS Grid Layout	post-thumbnail-5_css-grid.jpeg	<h1>Introduction to CSS Grid Layout</h1><p>CSS Grid Layout is a powerful layout system for building complex web designs with ease. It allows developers to create flexible and responsive grid-based layouts.</p><h2>Basic Structure of CSS Grid</h2><p>CSS Grid consists of two main parts:</p><ul><li><strong>Grid Container</strong>: The element that holds the grid items and defines the grid structure.</li><li><strong>Grid Items</strong>: The elements inside the grid container that are arranged based on the grid structure.</li></ul><h3>Example of a Basic Grid Layout</h3><pre><code>.container { display: grid; grid-template-columns: repeat(3, 1fr); grid-gap: 10px; } .item { background: lightgray; padding: 20px; }</code></pre><p>CSS Grid is perfect for creating both simple and complex grid layouts, giving you full control over how elements are placed on the page.</p>	CSS	{CSS,Grid,"Web Design"}	2025-03-05 14:52:23.489573	2025-03-05 14:55:48.888805
3	Introduction to React Components	post-thumbnail-3_react-thumb.jpg	<h1>Understanding React Components</h1><p>React components are the core building blocks of React applications. They manage the rendering of the user interface and handle the logic of the application.</p><h2>How Components Work</h2><p>Each React component can be either:</p><ul><li><strong>Class Component</strong>: A component defined by extending the <code>React.Component</code> class and includes lifecycle methods.</li><li><strong>Function Component</strong>: A simpler component defined as a function, often using React hooks for state and lifecycle management.</li></ul><h3>Example of a Basic Function Component</h3><pre><code>import React from 'react'; function App() { return <h1>Welcome to React Components</h1>; }</code></pre><h3>Example of a Basic Class Component</h3><pre><code>import React, { Component } from 'react'; class App extends Component { render() { return <h1>Welcome to React Components</h1>; } }</code></pre><p>React promotes reusable and declarative UI, enabling developers to efficiently manage complex UIs with minimal effort.</p>	React	{React,Components,"Web Development"}	2025-03-04 17:29:14.138997	2025-03-05 13:17:11.152837
\.


--
-- TOC entry 4835 (class 0 OID 16828)
-- Dependencies: 219
-- Data for Name: tb_role; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_role (id, name) FROM stdin;
1	ROLE_MEMBER
2	ROLE_ADMIN
\.


--
-- TOC entry 4837 (class 0 OID 16835)
-- Dependencies: 221
-- Data for Name: tb_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tb_user (id, full_name, email, password_hash, profile_url, role_id) FROM stdin;
5	Clécio José	kekelinformatica33@gmail.com	$2a$10$C6F/dx5SXcIoP7OAmstfB.Oz0f.67dbzkMMMsWseZ3U8LAnirUpUS	user.png	1
6	Deyvid Santos	d3yv1dpvp@gmail.com	$2a$10$TrjAUCfL032oIai8esLGzujY5nJlxhvWDZIekUbZfnh72AZWm70sa	user.png	1
4	Deyvid Silva	deyvidsantos.salvatore@gmail.com	$2a$10$fay7Dk6D76/In6CSpkR6PevdC.2IdF050QLXQSYgTBp5UPpZQWTQS	4_1697589634492.jpg	2
2	Jonatha Almeida	lvlupnona@gmail.com	$2a$10$OJJuffCIVpHOBxuHYm6KTOmeGO1Tj7dpSpGoroz5O6hFRYW3YZfBS	2_100172272.jfif	1
\.


--
-- TOC entry 4851 (class 0 OID 0)
-- Dependencies: 224
-- Name: tb_comment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_comment_id_seq', 8, true);


--
-- TOC entry 4852 (class 0 OID 0)
-- Dependencies: 222
-- Name: tb_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_post_id_seq', 6, true);


--
-- TOC entry 4853 (class 0 OID 0)
-- Dependencies: 218
-- Name: tb_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_role_id_seq', 1, false);


--
-- TOC entry 4854 (class 0 OID 0)
-- Dependencies: 220
-- Name: tb_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tb_user_id_seq', 6, true);


--
-- TOC entry 4673 (class 2606 OID 16825)
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- TOC entry 4684 (class 2606 OID 16874)
-- Name: tb_comment tb_comment_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_comment
    ADD CONSTRAINT tb_comment_pkey PRIMARY KEY (id);


--
-- TOC entry 4682 (class 2606 OID 16863)
-- Name: tb_post tb_post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_post
    ADD CONSTRAINT tb_post_pkey PRIMARY KEY (id);


--
-- TOC entry 4676 (class 2606 OID 16833)
-- Name: tb_role tb_role_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_role
    ADD CONSTRAINT tb_role_pkey PRIMARY KEY (id);


--
-- TOC entry 4678 (class 2606 OID 16846)
-- Name: tb_user tb_user_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT tb_user_email_key UNIQUE (email);


--
-- TOC entry 4680 (class 2606 OID 16844)
-- Name: tb_user tb_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT tb_user_pkey PRIMARY KEY (id);


--
-- TOC entry 4674 (class 1259 OID 16826)
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- TOC entry 4686 (class 2606 OID 16880)
-- Name: tb_comment tb_comment_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_comment
    ADD CONSTRAINT tb_comment_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.tb_post(id) ON DELETE CASCADE;


--
-- TOC entry 4687 (class 2606 OID 16875)
-- Name: tb_comment tb_comment_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_comment
    ADD CONSTRAINT tb_comment_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.tb_user(id) ON DELETE CASCADE;


--
-- TOC entry 4685 (class 2606 OID 16847)
-- Name: tb_user tb_user_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tb_user
    ADD CONSTRAINT tb_user_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.tb_role(id);


-- Completed on 2025-03-06 23:03:21

--
-- PostgreSQL database dump complete
--

