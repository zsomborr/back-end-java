--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5 (Ubuntu 11.5-0ubuntu0.19.04.1)
-- Dumped by pg_dump version 11.5 (Ubuntu 11.5-0ubuntu0.19.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER TABLE IF EXISTS ONLY public.user_entity_project_tags DROP CONSTRAINT IF EXISTS fksb926pfsk89w0ppsmdufvv5jd;
ALTER TABLE IF EXISTS ONLY public.question_entity DROP CONSTRAINT IF EXISTS fkrbfnww9bek2buo1dq79gbdgg0;
ALTER TABLE IF EXISTS ONLY public.answer_entity DROP CONSTRAINT IF EXISTS fkn4hq04x8rcw8oyvc5ku6qowd0;
ALTER TABLE IF EXISTS ONLY public.user_entity_roles DROP CONSTRAINT IF EXISTS fkjvvinok3stf32dvgie3vr73s0;
ALTER TABLE IF EXISTS ONLY public.user_entity_project_tags DROP CONSTRAINT IF EXISTS fkd38vdutva5xxy3j0vogwxbecp;
ALTER TABLE IF EXISTS ONLY public.user_entity_technology_tags DROP CONSTRAINT IF EXISTS fkamr5mkjnice5v708glkuve2q6;
ALTER TABLE IF EXISTS ONLY public.user_entity_technology_tags DROP CONSTRAINT IF EXISTS fk5dpu8qjrpq5mxmby6cd4ynq4m;
ALTER TABLE IF EXISTS ONLY public.answer_entity DROP CONSTRAINT IF EXISTS fk30nyarffudkxokj2gqqm0e349;
ALTER TABLE IF EXISTS ONLY public.user_entity_technology_tags DROP CONSTRAINT IF EXISTS user_entity_technology_tags_pkey;
ALTER TABLE IF EXISTS ONLY public.user_entity_project_tags DROP CONSTRAINT IF EXISTS user_entity_project_tags_pkey;
ALTER TABLE IF EXISTS ONLY public.user_entity DROP CONSTRAINT IF EXISTS user_entity_pkey;
ALTER TABLE IF EXISTS ONLY public.user_entity DROP CONSTRAINT IF EXISTS uk_4xad1enskw4j1t2866f7sodrx;
ALTER TABLE IF EXISTS ONLY public.user_entity DROP CONSTRAINT IF EXISTS uk_2jsk4eakd0rmvybo409wgwxuw;
ALTER TABLE IF EXISTS ONLY public.technology_entity DROP CONSTRAINT IF EXISTS technology_entity_pkey;
ALTER TABLE IF EXISTS ONLY public.question_entity DROP CONSTRAINT IF EXISTS question_entity_pkey;
ALTER TABLE IF EXISTS ONLY public.project_entity DROP CONSTRAINT IF EXISTS project_entity_pkey;
ALTER TABLE IF EXISTS ONLY public.answer_entity DROP CONSTRAINT IF EXISTS answer_entity_pkey;
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '31361';
SELECT pg_catalog.lo_unlink(oid) FROM pg_catalog.pg_largeobject_metadata WHERE oid = '31360';
DROP TABLE IF EXISTS public.user_entity_technology_tags;
DROP TABLE IF EXISTS public.user_entity_roles;
DROP TABLE IF EXISTS public.user_entity_project_tags;
DROP TABLE IF EXISTS public.user_entity;
DROP TABLE IF EXISTS public.technology_entity;
DROP TABLE IF EXISTS public.question_entity;
DROP TABLE IF EXISTS public.project_entity;
DROP SEQUENCE IF EXISTS public.hibernate_sequence;
DROP TABLE IF EXISTS public.answer_entity;
SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: answer_entity; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.answer_entity (
    id bigint NOT NULL,
    content text,
    submission_time timestamp without time zone,
    question_id bigint,
    user_id bigint
);


ALTER TABLE public.answer_entity OWNER TO peermentoring;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: peermentoring
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO peermentoring;

--
-- Name: project_entity; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.project_entity (
    id bigint NOT NULL,
    project_tag character varying(255)
);


ALTER TABLE public.project_entity OWNER TO peermentoring;

--
-- Name: question_entity; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.question_entity (
    id bigint NOT NULL,
    description text,
    submission_time timestamp without time zone,
    title text,
    user_id bigint
);


ALTER TABLE public.question_entity OWNER TO peermentoring;

--
-- Name: technology_entity; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.technology_entity (
    id bigint NOT NULL,
    technology_tag character varying(255)
);


ALTER TABLE public.technology_entity OWNER TO peermentoring;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.user_entity (
    id bigint NOT NULL,
    city character varying(255),
    country character varying(255),
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    module integer,
    password character varying(255),
    registration_date timestamp without time zone,
    username character varying(255)
);


ALTER TABLE public.user_entity OWNER TO peermentoring;

--
-- Name: user_entity_project_tags; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.user_entity_project_tags (
    user_entities_id bigint NOT NULL,
    project_tags_id bigint NOT NULL
);


ALTER TABLE public.user_entity_project_tags OWNER TO peermentoring;

--
-- Name: user_entity_roles; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.user_entity_roles (
    user_entity_id bigint NOT NULL,
    roles character varying(255)
);


ALTER TABLE public.user_entity_roles OWNER TO peermentoring;

--
-- Name: user_entity_technology_tags; Type: TABLE; Schema: public; Owner: peermentoring
--

CREATE TABLE public.user_entity_technology_tags (
    user_entities_id bigint NOT NULL,
    technology_tags_id bigint NOT NULL
);


ALTER TABLE public.user_entity_technology_tags OWNER TO peermentoring;

--
-- Name: 31360; Type: BLOB; Schema: -; Owner: peermentoring
--

SELECT pg_catalog.lo_create('31360');


ALTER LARGE OBJECT 31360 OWNER TO peermentoring;

--
-- Name: 31361; Type: BLOB; Schema: -; Owner: peermentoring
--

SELECT pg_catalog.lo_create('31361');


ALTER LARGE OBJECT 31361 OWNER TO peermentoring;

--
-- Data for Name: answer_entity; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.answer_entity VALUES (13, 'Is this what you''re looking for?

d = {}
with open("abbreviations.txt") as abb:
    for line in abb:
        (key, val) = line.split(":")
        d[key] = val

print(d)

im = input("Enter message here: ")
message = '' ''.join([d[s] if s in d else s for s in im.split()])', '2020-10-06 10:33:28.09146', 3, 4);
INSERT INTO public.answer_entity VALUES (20, '
+50
Update April 2019

jQuery isn''t needed for cookie reading/manipulation, so don''t use the original answer below.

Go to https://github.com/js-cookie/js-cookie instead, and use the library there that doesn''t depend on jQuery.

Basic examples:

// Set a cookie
Cookies.set(''name'', ''value'');

// Read the cookie
Cookies.get(''name'') => // => ''value''
See the docs on github for details.

See the plugin:

https://github.com/carhartl/jquery-cookie

You can then do:

$.cookie("test", 1);
To delete:

$.removeCookie("test");
Additionally, to set a timeout of a certain number of days (10 here) on the cookie:

$.cookie("test", 1, { expires : 10 });
If the expires option is omitted, then the cookie becomes a session cookie and is deleted when the browser exits.

To cover all the options:

$.cookie("test", 1, {
   expires : 10,           // Expires in 10 days

   path    : ''/'',          // The value of the path attribute of the cookie
                           // (Default: path of page that created the cookie).

   domain  : ''jquery.com'', // The value of the domain attribute of the cookie
                           // (Default: domain of page that created the cookie).

   secure  : true          // If set to true the secure attribute of the cookie
                           // will be set and the cookie transmission will
                           // require a secure protocol (defaults to false).
});
To read back the value of the cookie:

var cookieValue = $.cookie("test");
You may wish to specify the path parameter if the cookie was created on a different path to the current one:

var cookieValue = $.cookie("test", { path: ''/foo'' });
UPDATE (April 2015):

As stated in the comments below, the team that worked on the original plugin has removed the jQuery dependency in a new project (https://github.com/js-cookie/js-cookie) which has the same functionality and general syntax as the jQuery version. Apparently the original plugin isn''t going anywhere though.', '2020-10-06 10:40:03.149782', 11, 14);
INSERT INTO public.answer_entity VALUES (27, 'No, that is fine, as container is not attached to the DOM. This is the preferred way to add a lot of elements to the DOM.
But by using html() you will loose all event handlers or data you add to the elements. If you want to keep them, consider using this:

$(''#actualElement'').empty().append(container.children());
You could also use a DocumentFragement, but as you are using jQuery, I would stick with jQuery.

Update: To answer the actual question: Yes, it is DOM manipulation, but you are adding to the DOM only once which is the best you can get (besides not inserting at all ;)).', '2020-10-06 10:45:16.49464', 12, 21);
INSERT INTO public.answer_entity VALUES (28, 'If it is an auto generated key, then you can use Statement#getGeneratedKeys() for this. You need to call it on the same Statement as the one being used for the INSERT. You first need to create the statement using Statement.RETURN_GENERATED_KEYS to notify the JDBC driver to return the keys.

Here''s a basic example:

public void create(User user) throws SQLException {
    try (
        Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_INSERT,
                                      Statement.RETURN_GENERATED_KEYS);
    ) {
        statement.setString(1, user.getName());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getEmail());
        // ...

        int affectedRows = statement.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong(1));
            }
            else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }
        }
    }
}
Note that you''re dependent on the JDBC driver as to whether it works. Currently, most of the last versions will work, but if I am correct, Oracle JDBC driver is still somewhat troublesome with this. MySQL and DB2 already supported it for ages. PostgreSQL started to support it not long ago. I can''t comment about MSSQL as I''ve never used it.

For Oracle, you can invoke a CallableStatement with a RETURNING clause or a SELECT CURRVAL(sequencename) (or whatever DB-specific syntax to do so) directly after the INSERT in the same transaction to obtain the last generated key. See also this answer.', '2020-10-06 10:45:55.46713', 18, 21);
INSERT INTO public.answer_entity VALUES (29, 'There is no need to use jQuery particularly to manipulate cookies.

From QuirksMode (including escaping characters)

function createCookie(name, value, days) {
    var expires;

    if (days) {
        var date = new Date();
        date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        expires = "; expires=" + date.toGMTString();
    } else {
        expires = "";
    }
    document.cookie = encodeURIComponent(name) + "=" + encodeURIComponent(value) + expires + "; path=/";
}

function readCookie(name) {
    var nameEQ = encodeURIComponent(name) + "=";
    var ca = document.cookie.split('';'');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === '' '')
            c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) === 0)
            return decodeURIComponent(c.substring(nameEQ.length, c.length));
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name, "", -1);
}', '2020-10-06 10:47:57.103561', 11, 21);


--
-- Data for Name: project_entity; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.project_entity VALUES (9, 'AskMate');
INSERT INTO public.project_entity VALUES (10, 'lightweight ERP');
INSERT INTO public.project_entity VALUES (16, 'nearby elements');
INSERT INTO public.project_entity VALUES (17, 'five-in-a-row');
INSERT INTO public.project_entity VALUES (25, 'breadth-first search');
INSERT INTO public.project_entity VALUES (26, 'TODO app');


--
-- Data for Name: question_entity; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.question_entity VALUES (2, 'I have a list in one of the columns separated by comma and I want to display it vertically inside that cell to improve readability.

As an example -
ID VALUE

               2  [''(39,"nmo","omg",889)'',''(40,"jjj","kkk",880)'']
               
How I want to display it is: ID VALUE

                             2     39 nmo omg  889
                                   40 jjj kkk  880
Thank you.', '2020-10-06 10:20:43.231986', 'Is there any way to display comma separated list vertically in a single cell using python?', 1);
INSERT INTO public.question_entity VALUES (3, 'I am able to print the key:value combinations when I do print(d). Now my objective is that when I input a message in im eg. y r u late. It should print back why are you late. The aim is to split the sentence into words and replace it by the associated text value if available otherwise keep the original word

This is for a school assignment and I am just looking for some help on this.', '2020-10-06 10:21:37.361992', 'How to input a string or a sentence and output the corresponding value(s) from the dictionary', 1);
INSERT INTO public.question_entity VALUES (11, 'How do I set and unset a cookie using jQuery, for example create a cookie named test and set the value to 1?', '2020-10-06 10:30:43.86656', 'How do I set/unset a cookie with jQuery?', 4);
INSERT INTO public.question_entity VALUES (12, 'I am aware that a large amount of DOM manipulation is bad due to it causing repaints ect.

but what about doing something like

var container = $(''<div />'');
{repeat:20}
container.append(<div>content</div>);
{endrepeat}
$(''#actualElement'').html(container.html());

even if it is not DOM manipulation, is it bad?', '2020-10-06 10:32:10.955352', 'is this dom manipulation?', 4);
INSERT INTO public.question_entity VALUES (18, 'I want to INSERT a record in a database (which is Microsoft SQL Server in my case) using JDBC in Java. At the same time, I want to obtain the insert ID. How can I achieve this using JDBC API?', '2020-10-06 10:38:25.297468', 'How to get the insert ID in JDBC?', 14);
INSERT INTO public.question_entity VALUES (19, 'Can anyone please explain the difference between binary tree and binary search tree with an example?', '2020-10-06 10:39:22.57914', 'Difference between binary tree and binary search tree', 14);


--
-- Data for Name: technology_entity; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.technology_entity VALUES (5, 'py');
INSERT INTO public.technology_entity VALUES (6, 'python');
INSERT INTO public.technology_entity VALUES (7, 'flask');
INSERT INTO public.technology_entity VALUES (8, 'postgreSQL');
INSERT INTO public.technology_entity VALUES (15, 'java');
INSERT INTO public.technology_entity VALUES (22, 'JDBC');
INSERT INTO public.technology_entity VALUES (23, 'Spring');
INSERT INTO public.technology_entity VALUES (24, 'lombok');


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.user_entity VALUES (1, 'Budapest', 'Hungary', 'student@codecool.com', 'Codecool', 'Student', 0, '{bcrypt}$2a$10$BHm2XGYeq.AmQYOVazcvnOokiFoaPgtmszEljUJ2PtNGtZwKOkqg2', '2020-10-06 10:18:50.96003', 'student1');
INSERT INTO public.user_entity VALUES (4, 'Budapest', 'Hungary', 'student2@codecool.com', 'Codecool', 'Studenttwo', 1, '{bcrypt}$2a$10$sOQOr1aSZYVvo9N2iemuSutVsbcPs0FVbiXqXmm2tFB6XkvQVRaVG', '2020-10-06 10:25:44.544134', 'student2');
INSERT INTO public.user_entity VALUES (14, 'Budapest', 'Hungary', 'student3@codecool.com', 'Codecool', 'Studentthree', 2, '{bcrypt}$2a$10$vlupBgfJho.eUgZihsBmBu/FEZ4XRLGtMJ9iEb58Wcqa/xneqdSqy', '2020-10-06 10:35:16.105538', 'student3');
INSERT INTO public.user_entity VALUES (21, 'Budapest', 'Hungary', 'student4@codecool.com', 'Codecool', 'Studentfour', 3, '{bcrypt}$2a$10$/Dz/y3jGyNQKRei0duNUaubL9.GxchO8r07ozan.ORLR7BI3LP5/G', '2020-10-06 10:41:19.551351', 'student4');


--
-- Data for Name: user_entity_project_tags; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.user_entity_project_tags VALUES (4, 9);
INSERT INTO public.user_entity_project_tags VALUES (4, 10);
INSERT INTO public.user_entity_project_tags VALUES (14, 16);
INSERT INTO public.user_entity_project_tags VALUES (14, 17);
INSERT INTO public.user_entity_project_tags VALUES (14, 9);
INSERT INTO public.user_entity_project_tags VALUES (21, 25);
INSERT INTO public.user_entity_project_tags VALUES (21, 26);


--
-- Data for Name: user_entity_roles; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.user_entity_roles VALUES (1, 'ROLE_USER');
INSERT INTO public.user_entity_roles VALUES (4, 'ROLE_USER');
INSERT INTO public.user_entity_roles VALUES (14, 'ROLE_USER');
INSERT INTO public.user_entity_roles VALUES (21, 'ROLE_USER');


--
-- Data for Name: user_entity_technology_tags; Type: TABLE DATA; Schema: public; Owner: peermentoring
--

INSERT INTO public.user_entity_technology_tags VALUES (4, 6);
INSERT INTO public.user_entity_technology_tags VALUES (4, 7);
INSERT INTO public.user_entity_technology_tags VALUES (4, 8);
INSERT INTO public.user_entity_technology_tags VALUES (14, 15);
INSERT INTO public.user_entity_technology_tags VALUES (14, 6);
INSERT INTO public.user_entity_technology_tags VALUES (21, 15);
INSERT INTO public.user_entity_technology_tags VALUES (21, 6);
INSERT INTO public.user_entity_technology_tags VALUES (21, 22);
INSERT INTO public.user_entity_technology_tags VALUES (21, 23);
INSERT INTO public.user_entity_technology_tags VALUES (21, 24);


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: peermentoring
--

SELECT pg_catalog.setval('public.hibernate_sequence', 29, true);


--
-- Data for Name: BLOBS; Type: BLOBS; Schema: -; Owner: 
--

BEGIN;

SELECT pg_catalog.lo_open('31360', 131072);
SELECT pg_catalog.lowrite(0, '\x7969656c647320746865206d6f737420636f727265637420726573756c742e2057697468207468652076657273696f6e206f662068626d3264646c2049276d207573696e672c20746869732077696c6c206265207472616e73666f726d656420746f207468652074797065207465787420776974682053716c5365727665724469616c6563742e2053696e63652076617263686172286d61782920697320746865207265706c6163656d656e7420666f72207465787420696e206e657765722076657273696f6e73206f662053514c205365727665722c20686f706566756c6c792c206e657765722076657273696f6e73206f662068626d3264646c2077696c6c207969656c642076617263686172286d61782920696e7374656164206f66207465787420666f7220746869732074797065206f66206d617070696e67202849276d20737475636b20617420612071756974652064617465642076657273696f6e206f662048696265726e61746520617420746865206d6f6d656e742e2e290a7969656c647320746865206d6f737420636f727265637420726573756c742e2057697468207468652076657273696f6e206f662068626d3264646c2049276d207573696e672c20746869732077696c6c206265207472616e73666f726d656420746f207468652074797065207465787420776974682053716c5365727665724469616c6563742e2053696e63652076617263686172286d61782920697320746865207265706c6163656d656e7420666f72207465787420696e206e657765722076657273696f6e73206f662053514c205365727665722c20686f706566756c6c792c206e657765722076657273696f6e73206f662068626d3264646c2077696c6c207969656c642076617263686172286d61782920696e7374656164206f66207465787420666f7220746869732074797065206f66206d617070696e67202849276d20737475636b20617420612071756974652064617465642076657273696f6e206f662048696265726e61746520617420746865206d6f6d656e742e2e29');
SELECT pg_catalog.lo_close(0);

SELECT pg_catalog.lo_open('31361', 131072);
SELECT pg_catalog.lowrite(0, '\x6c65656b6a6661c3a96c69');
SELECT pg_catalog.lo_close(0);

COMMIT;

--
-- Name: answer_entity answer_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.answer_entity
    ADD CONSTRAINT answer_entity_pkey PRIMARY KEY (id);


--
-- Name: project_entity project_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.project_entity
    ADD CONSTRAINT project_entity_pkey PRIMARY KEY (id);


--
-- Name: question_entity question_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.question_entity
    ADD CONSTRAINT question_entity_pkey PRIMARY KEY (id);


--
-- Name: technology_entity technology_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.technology_entity
    ADD CONSTRAINT technology_entity_pkey PRIMARY KEY (id);


--
-- Name: user_entity uk_2jsk4eakd0rmvybo409wgwxuw; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_2jsk4eakd0rmvybo409wgwxuw UNIQUE (username);


--
-- Name: user_entity uk_4xad1enskw4j1t2866f7sodrx; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_4xad1enskw4j1t2866f7sodrx UNIQUE (email);


--
-- Name: user_entity user_entity_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT user_entity_pkey PRIMARY KEY (id);


--
-- Name: user_entity_project_tags user_entity_project_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_project_tags
    ADD CONSTRAINT user_entity_project_tags_pkey PRIMARY KEY (user_entities_id, project_tags_id);


--
-- Name: user_entity_technology_tags user_entity_technology_tags_pkey; Type: CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_technology_tags
    ADD CONSTRAINT user_entity_technology_tags_pkey PRIMARY KEY (user_entities_id, technology_tags_id);


--
-- Name: answer_entity fk30nyarffudkxokj2gqqm0e349; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.answer_entity
    ADD CONSTRAINT fk30nyarffudkxokj2gqqm0e349 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_entity_technology_tags fk5dpu8qjrpq5mxmby6cd4ynq4m; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_technology_tags
    ADD CONSTRAINT fk5dpu8qjrpq5mxmby6cd4ynq4m FOREIGN KEY (technology_tags_id) REFERENCES public.technology_entity(id);


--
-- Name: user_entity_technology_tags fkamr5mkjnice5v708glkuve2q6; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_technology_tags
    ADD CONSTRAINT fkamr5mkjnice5v708glkuve2q6 FOREIGN KEY (user_entities_id) REFERENCES public.user_entity(id);


--
-- Name: user_entity_project_tags fkd38vdutva5xxy3j0vogwxbecp; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_project_tags
    ADD CONSTRAINT fkd38vdutva5xxy3j0vogwxbecp FOREIGN KEY (project_tags_id) REFERENCES public.project_entity(id);


--
-- Name: user_entity_roles fkjvvinok3stf32dvgie3vr73s0; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_roles
    ADD CONSTRAINT fkjvvinok3stf32dvgie3vr73s0 FOREIGN KEY (user_entity_id) REFERENCES public.user_entity(id);


--
-- Name: answer_entity fkn4hq04x8rcw8oyvc5ku6qowd0; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.answer_entity
    ADD CONSTRAINT fkn4hq04x8rcw8oyvc5ku6qowd0 FOREIGN KEY (question_id) REFERENCES public.question_entity(id);


--
-- Name: question_entity fkrbfnww9bek2buo1dq79gbdgg0; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.question_entity
    ADD CONSTRAINT fkrbfnww9bek2buo1dq79gbdgg0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_entity_project_tags fksb926pfsk89w0ppsmdufvv5jd; Type: FK CONSTRAINT; Schema: public; Owner: peermentoring
--

ALTER TABLE ONLY public.user_entity_project_tags
    ADD CONSTRAINT fksb926pfsk89w0ppsmdufvv5jd FOREIGN KEY (user_entities_id) REFERENCES public.user_entity(id);


--
-- PostgreSQL database dump complete
--

