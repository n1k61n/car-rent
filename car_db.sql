--
-- PostgreSQL database dump
--

\restrict DOrQIBhnKMr4eaWVkaOmKqQYRsXuXSo5xxFCdNmj2MTL4HemZg1FWiFHhKxaqmh

-- Dumped from database version 17.6
-- Dumped by pg_dump version 17.6

-- Started on 2026-01-17 22:54:21

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
-- TOC entry 5039 (class 0 OID 29106)
-- Dependencies: 222
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id, name) FROM stdin;
1	Sedan
2	SUV
3	Luxury
4	Sport
5	Hatchback
6	Coupe
\.


--
-- TOC entry 5044 (class 0 OID 29131)
-- Dependencies: 227
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, email, first_name, last_name, password, phone, role, account_non_expired, account_non_locked, credentials_non_expired, enabled, phone_number, reset_password_token, verification_code, created_at) FROM stdin;
23	yikaka5880@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$2agdavdncRk0UGCnIEppIurUawBxsCu8DIN.SQBh0gBYXL8Y.QXkW	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
4	senan@gmail.com	Tunar	Mammadov	$2a$10$K.N3AqT593ohPZBGaLnwxezElvwVPJpENAJgX24ECW13dKQ43THg2	\N	OWNER	t	t	t	t	01234567	\N	\N	\N
5	tunar1@gmail.com	EMİN	MƏMMƏDOV	$2a$10$yNz16Lk33k/oG7dnlK.KOeUnlO0nc.A8JkcR8NtFqsfJx7d5VRT1C	\N	USER	t	t	t	t	0505458811	\N	\N	\N
6	tunar1234@gmail.com	EMİN	MƏMMƏDOV	$2a$10$tC0oNbgiIeT2OXOb31cSN.5NBkurIknP2UA6neP4LohwSDkDNN0Wm	\N	USER	t	t	t	t	0505451188	\N	\N	\N
8	kobiga2905@jparksky.com	EMİN	MƏMMƏDOV	$2a$10$wE28xbI3VCK11BAk0Ij1L.mCvZwQB1urLdoyVUw.iJ.79mIqOFFxq	\N	USER	t	t	t	t	+994502222222	c7f2f703-fb30-47ab-8d40-b418681c9665	\N	\N
32	bowora7651@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$m0xlH.zHVerIODXZvbli/.jtPNiiyG.32H5TIUIQ4xmryE8Ym/BIG	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
3	musik@gmail.com	Mustafa 	Imanov	$2a$10$ney0TQOMXVhksiG6YKll0uPczljW65qzvxmcthuvIOq6uAEG6.bxm	\N	USER	t	t	t	t	0502221111	\N	\N	\N
7	denag31802@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$97162VsWbBN4l2vP/h3IMeYclFZ6tRZCaUxgQglh1jAeMStC6gaC2	\N	USER	t	t	t	t	0505458811	b0457df0-4ee3-48f6-9fbe-ff701207b846	\N	\N
40	daxiy87814@oremal.com	Emin	Mammadov	$2a$10$jt1XYlgERWUWLOthlvzcyeFObIjslkIvoVz4eH4cmZm0MToZq3jXG	\N	USER	f	f	f	f	0505458811	\N	\N	2026-01-17 16:19:45.190126
36	vasoyaf496@feanzier.com	Emin	Mammadov	$2a$10$TSUzCUFHRVmv8KUgP2E1euweaeEHG9ryZaf9S99gVZLNZgG8vL1du	\N	USER	t	t	t	t	0505458811	\N	\N	\N
26	wevelof975@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$cPOMTpPTXAE2qKkqlRHyXeqqKQnSdE43peMChhJ9vLspoYPDTsXR.	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
27	dojec53549@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$bjaTf29PAR7nd15GFjV8yesGYDmj5MRwnSL.KO3WjI3RESdn1QHga	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
28	melege1160@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$0xsaqUiv3BY0U/gcNd8/DuJmVR1Wl7425NB/eGKmnT4Vj9ZHVlgyO	\N	USER	f	f	f	f	+994502222222	\N	\N	\N
29	hecenoy277@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$xzil8morI6hOFMo1Dvg/JeGVbiJI8NzjeMTGhBjTU4i8R/TH72tee	\N	USER	f	f	f	f	+994502222222	\N	\N	\N
30	nemeho6904@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$T9xD1KbFjd7pKfbiTxWgb.HKQr/3QGBpCE2jSVlVRNGGmFbJ2p0QK	\N	USER	f	f	f	f	+994502222222	\N	\N	\N
31	eminelxanoglu@gmail.com	EMİN	MƏMMƏDOV	$2a$10$gVUFS32nKjM8v2K5SS1CTuOLjcvyeTohCfr0wg7j6DH1vwHiD5mU6	\N	USER	f	f	f	f	+994502222222	\N	\N	\N
14	nopom34927@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$nKLQ3RxxQJ4fjNy1Ia1OmerGH1Kz5cyKIlVODObc5LDa6pwvtkq2i	\N	USER	f	f	f	f	+994502222222	\N	\N	\N
15	vogone7020@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$59iaFuZmQpBPQ0LKW7t96uC2kloYGWNXihvdEqMUWuorbYfI0e3VW	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
16	xehepic915@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$G6YIY9NoxfNOCyNxEnEruulJ6jaqL9J9hpxD81ZzZDXqBAAiOdjO6	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
33	wivid17034@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$q8/HfNBrwJ90TbEZM6UDp.N3H0RNbmwg/Lw.yaqmHV5sSOs7mg1R.	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
2	tunar@gmail.com	Tunar	Mammadov	$2a$10$Xa9WybxVicR6bWZfERFvTOolW3PbdJjRotoUamTIELrlcqtHvLXUa	\N	USER	t	t	t	f	01234567	\N	\N	\N
10	difeg63384@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$hz3X8du7lDGNABNWAvKM8u3W1aCIn16iO2bhYd5BODhKgtmSDqz4.	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
11	gafayil353@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$6ADpYmcI39kvfaD.BMzPL.r7Zep9454IWyVPNp0.R1cgtkEUAXJ9u	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
12	remile5719@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$LDeCEmnXbrG7rXFqEufhnux2hyHSB.7VaVEETTHZ4un1Xe5Jua88K	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
13	tomomoj652@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$8ndG5FxQlBT0Gd5hYX25.uRXeOdlw93ub9qM7xt8RUvCZMAUU4oCe	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
17	lositi1653@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$kIfwMhHhzum8wbuU9C3lJeuZSthpvMGOWxh0KSpOO8KCHWVe5AIj.	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
18	dexivam491@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$fxvordBdXE9nLclDnyGhxeskfhFqReQAdFPMsFncOKx.yOuv5aleK	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
19	bakiraj763@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$ydWwNCz1uCEdGC0rX37iPuKFMelgzzfiqUA0jA5pUXhx6N8XlgOwC	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
20	veges90113@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$NTxaoCElSnc2OjJKtJqYSOPG.B2kqpIUXPRlYhsBh5xxv0hnW3l96	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
21	hasabo7723@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$9MPved3AO5UQSrbFrlm7eukrQxjiykCbU0S3g.SKeDZnciwNVPTe2	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
22	dabelig375@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$wngI5Xp89UVbKfRqYO6XiO34TLFEao/xVicJaJaOTS8cAxFR1C30S	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
25	kolohe2597@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$U5Xos0NTV2VkPSVEY93jEeGAWf6mffPsNraP11C5mToao6EmCZ236	\N	USER	t	t	t	f	+994502222222	\N	\N	\N
24	notefo3236@feanzier.com	EMİN	MƏMMƏDOV	$2a$10$RI2roQsEw.eNznB6TdkcBu8Pl1zPcVRpAcPD6z.7cdNVIssoXF/Hm	\N	USER	f	f	f	t	+994502222222	\N	\N	\N
9	mesobe6992@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$WOVcR5C0ZAFYuy9vv0I5teodmD1GGmHZn4tQiWWRxBLmmp1IgWoj2	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
34	yobah65496@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$Ls/fQ1Ov4FHFJFpfGQ.iFeUnKu5vqC8kkv8ugDHl2RacyuDcAIPnS	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
35	tifigo5802@eubonus.com	EMİN	MƏMMƏDOV	$2a$10$D4Vigv7vMV6jzz3fsdmig.z8ErU7vnU.T5rb2UM4Dv6nAP.48Y.CS	\N	USER	t	t	t	t	+994502222222	\N	\N	\N
38	poreyis909@ixospace.com	Hacibala	Abudalibov	$2a$10$3qTTSzvLDYKnqeTi5PSy.OieExMJTj2lts5dkKl2gfRCx2QltcUXq	\N	USER	t	t	t	t	0502222222	\N	\N	2026-01-16 09:46:06.212193
1	admin@gmail.com	Emin 	Memmedov	$2a$10$pmDegDjREhrTWGMA/dubQe9y.6Gn.ydoNX5Eizf8p3qLGkoNwPta.	\N	ADMIN	t	t	t	t	0505458811	\N	\N	\N
37	fesoga9500@eubonus.com	Emin	Mammadov	$2a$10$15.Og7wDUlaUz6zj2TXqg.IH5ptKOq6XIGzIur/b5ga5B625kmaJ2	\N	USER	t	t	t	t	0505458811	\N	\N	2026-01-13 12:41:44.294394
39	jedileh964@elafans.com	Emin	Mammadov	$2a$10$psxURDWy6fjbIre9Qfm4lufBHmke/aqAtqea8hspBjGnQCWjpWh02	\N	USER	f	f	f	f	0505458811	\N	\N	2026-01-17 16:13:11.143365
41	vokixom976@oremal.com	Emin	Mammadov	$2a$10$cXCs3KmiPi11WGW/C2eizuhUjaTMV14FldVnJNgH/1VaitZKMqy3C	\N	USER	t	t	t	t	0505458811	\N	\N	2026-01-17 16:23:39.695739
42	xerot18125@noihse.com	Hacibala	Abudalibov	$2a$10$D7/XDnRdZng6HjjCLjFW8Oj1sCGmoSXUShzdjwGPP1EAVRW/EiZ0q	\N	USER	t	t	t	t	+994502222222	\N	438478	2026-01-17 17:09:40.333116
43	navidax225@noihse.com	Teymur	Recebov	$2a$10$/UGJdlcM4Zty7fkNRw9WuuqXAYpwNE3f0DP2AYcNPuCSbkmseC3h2	\N	USER	t	t	t	t	+994502222222	\N	729834	2026-01-17 18:43:31.579516
\.


--
-- TOC entry 5037 (class 0 OID 29098)
-- Dependencies: 220
-- Data for Name: cars; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cars (id, available, brand, daily_price, door_count, fuel_type, image_url, model, passenger_count, transmission, category_id, luggage_capacity, description, features, year, free_cancellation, fuel_policy, insurance_included, mileage, road_assistance, suitcases, user_id, owner_id, is_approved) FROM stdin;
13	f	Skoda	90	4	Diesel	/front/images/car_2.jpg	Superb	7	Automatic	1	5	Sinfinin ən geniş baqaj həcmi.	\N	2024	t	Full to Full	t	Unlimited	f	1 Large, 2 Small	\N	\N	f
14	f	Ford	60	4	Hybrid	/front/images/car_3.jpg	Fusion	5	Automatic	1	3	Amerikan komfortu.	\N	2024	f	Full to Full	t	Unlimited	t	1 Large, 2 Small	\N	\N	f
11	t	Nissan	65	4	Petrol	/front/images/car_6.jpg	Altima	7	Automatic	1	3	Yumşaq sürüş və texnoloji interyer.	\N	2023	t	Full to Full	t	Unlimited	t	2 Large, 2 Small	\N	\N	f
12	t	Mazda	75	4	Petrol	/front/images/car_1.jpg	6	5	Automatic	1	2	İdman üslubunda dizayn.	\N	2022	f	Same to Same	t	Unlimited	t	1 Large, 2 Small	\N	\N	f
19	t	BMW	180	5	Diesel	/front/images/car_2.jpg	X5	5	Manual	2	6	Güclü mühərrik və lüks SUV.	\N	2022	t	Full to Full	t	250 km/day	t	1 Large, 1 Small	\N	\N	f
20	t	Mercedes-Benz	200	5	Petrol	/front/images/car_3.jpg	GLE	5	Manual	2	6	Maksimum komfort və prestij.	\N	2024	t	Same to Same	t	Unlimited	t	1 Large, 2 Small	\N	\N	f
21	t	Range Rover	250	5	Diesel	/front/images/car_4.jpg	Sport	7	Manual	2	6	Off-road kralı.	\N	2022	f	Full to Full	t	250 km/day	t	1 Large, 2 Small	\N	\N	f
23	t	Mitsubishi	120	5	Diesel	/front/images/car_6.jpg	Pajero	5	Automatic	2	7	Əsl dözümlülük simvolu.	\N	2022	t	Full to Full	t	Unlimited	t	2 Large, 2 Small	\N	\N	f
24	t	Lexus	190	5	Hybrid	/front/images/car_1.jpg	RX450h	7	Automatic	2	5	Lüks və qənaət bir yerdə.	\N	2024	t	Same to Same	t	Unlimited	t	2 Large, 2 Small	\N	\N	f
48	t	Changan 	75	4	Benzin	https://turbo.azstatic.com/uploads/full/2025%2F07%2F25%2F18%2F51%2F28%2F6f80e193-cae3-46d1-a49c-91a1f94f9e03%2F4513_SdFmiiX51JZHxZnCDO0xMg.jpg	Uni-Z	4	Automatic	1	450	\N	\N	2025	\N	Full to Full	t	0	t	2 Boyuk 1 Kicik 	\N	\N	f
26	t	Porsche	450	2	Petrol	/front/images/car_3.jpg	911 Carrera	5	Automatic	3	1	Əfsanəvi idman avtomobili.	\N	2021	t	Full to Full	t	300 km/day	t	1 Large, 2 Small	\N	\N	f
27	t	Mercedes-Benz	350	4	Petrol	/front/images/car_4.jpg	S-Class	5	Automatic	3	4	Prezident komfortu.	\N	2024	t	Full to Full	t	Unlimited	t	2 Large, 2 Small	\N	\N	f
28	t	BMW	320	4	Petrol	/front/images/car_5.jpg	7 Series	5	Automatic	3	4	Sürücü üçün ən yaxşı lüks sedan.	\N	2021	t	Full to Full	t	250 km/day	t	2 Large, 2 Small	\N	\N	f
37	t	Hyundai	55	4	Petrol	/front/images/car_3.jpg	Elantra	5	Automatic	1	2	\N	\N	2021	\N	Full to Full	\N	Unlimited	t	1 Large, 1 Small	\N	\N	f
30	t	Maserati	400	4	Petrol	/front/images/car_1.jpg	Quattroporte	5	Automatic	3	3	İtalyan zərifliyi və sürəti.	\N	2024	t	Full to Full	t	Unlimited	f	2 Large, 2 Small	\N	\N	f
32	t	Rolls-Royce	1200	4	Petrol	/front/images/car_3.jpg	Ghost	4	Manual	3	4	Yoldakı ən səssiz lüks.	\N	2021	t	Same to Same	t	Unlimited	t	2 Large, 3 Small	\N	\N	f
33	t	Lamborghini	700	5	Petrol	/front/images/car_4.jpg	Urus	7	Manual	3	5	Dünyanın ən sürətli SUV-u.	\N	2023	f	Full to Full	t	250 km/day	t	2 Large, 2 Small	\N	\N	f
34	t	Tesla	220	4	Electric	/front/images/car_5.jpg	Model S	5	Automatic	3	5	Sürətli və ekoloji təmiz.	\N	2021	t	Full to Full	t	300 km/day	t	2 Large, 3 Small	\N	\N	f
40	t	BMW	100	4	Petrol	https://tse3.mm.bing.net/th/id/OIP.sCIR3Mt5pi9wCIP8zcVBqwHaEn?w=1600&h=999&rs=1&pid=ImgDetMain&o=7&rm=3	M5	4	Automatic	1	450	\N	\N	2022	t	Full to Full	t	10000	t	1 Large, 2 Small	\N	4	f
36	t	Mercedes	80	4	Benzin	https://kolesa-uploads.ru/-/c41d5571-98de-4530-95c8-14883d05663b/mercedes-benz-e-front1-mini.jpg	E-Class	4	Automatic	1	450	\N	\N	2025	t	Full to Full	t	10000	t	2 Boyuk 1 Kicik 	\N	\N	f
47	t	BYD	75	4	Benzin	https://turbo.azstatic.com/uploads/full/2025%2F11%2F07%2F15%2F31%2F07%2Fdc1fe4d7-d167-4440-b780-6e0ab0b9dad9%2F63894_ThP4TYO136vlivaKIqjPWA.jpg	Seal 05	4	Automatic	1	450	\N	\N	2025	\N	Full to Full	t	10000	t	2 Boyuk 1 Kicik 	\N	\N	f
22	f	Nissan	85	5	Petrol	/front/images/car_5.jpg	X-Trail	5	Automatic	2	4	7 yerlik ailə SUV-u.	\N	2023	t	Full to Full	t	Unlimited	t	2 Large, 3 Small	\N	\N	f
31	f	Bentley	800	4	Petrol	/front/images/car_2.jpg	Flying Spur	5	Automatic	3	4	Əl işi interyer və güc.	\N	2023	t	Same to Same	t	Unlimited	t	2 Large, 2 Small	\N	\N	f
\.


--
-- TOC entry 5050 (class 0 OID 29215)
-- Dependencies: 233
-- Data for Name: blogs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.blogs (id, author, content, created_at, image_url, title, car_id, category_name, author_bio, author_description, author_image_url, post) FROM stdin;
1	Admin	Bakı küçələrində rahat gəzinti üçün ən yaxşı seçim kompakt avtomobillərdir. Yanacağa qənaət edin.	\N	/front/images/car_1.jpg	Şəhər daxili səyahət üçün 5 məsləhət	\N	Avtomobil / Texnologiya	\N	\N	/front/images/person_1.jpg	          1. Marşrutu Əvvəlcədən Planlayın, Amma Spontanlığa Yer Qoyun\r\n\r\nŞəhər daxilində vaxt itkisinin qarşısını almaq üçün görmək istədiyiniz yerləri xəritədə qruplaşdırın. Məsələn, bir-birinə yaxın olan muzeyləri, parkları və kafeləri eyni günə salın. Lakin hər dəqiqənizi planlamayın; bəzən xəritədə olmayan dar bir küçə və ya kiçik bir sənət qalereyası səyahətinizin ən yadda qalan hissəsi ola bilər.\r\n\r\n          2. İctimai Nəqliyyatdan və Piyada Gəzintidən İstifadə Edin\r\n\r\nŞəhərin ruhunu hiss etməyin ən yaxşı yolu onun küçələrinə qarışmaqdır. Taksi əvəzinə metro və ya avtobuslardan istifadə etmək həm yerli əhalinin gündəlik həyatını müşahidə etməyə imkan verir, həm də tıxaclardan qaçmağa kömək edir. Əgər məsafə uyğundursa, mütləq piyada gəzin. Rahat ayaqqabı geyinmək bu mərhələdə ən vacib şərtdir!\r\n\r\n      3. "Turist Tələləri"ndən Qaçın və Yerli Dadları Kəşf Edin\r\n\r\nƏsas turistik meydanlardakı restoranlar adətən baha və standart olur. Bir neçə küçə kənara çıxaraq yerli əhalinin getdiyi kiçik kafeləri tapın. Həm daha dadlı, həm də daha uyğun qiymətə orijinal yeməklər dadacaqsınız. Həmçinin, şəhərin məşhur abidələrinə səhər tezdən – hələ izdiham başlamadan getmək sizə daha sakit bir təcrübə bəxş edər.\r\n\r\n      4. Şəhərin Mobil Tətbiqlərini Yükləyin\r\n\r\nTexnologiya şəhər daxili səyahətdə ən yaxın dostunuzdur. Getdiyiniz şəhərin:\r\n\r\n* **Nəqliyyat tətbiqini** (avtobus və metro vaxtları üçün),\r\n* **Onlayn/Offlayn xəritələrini** (Google Maps və ya 2GIS),\r\n* **Tədbir bələdçilərini** yükləyin.\r\nBu tətbiqlər sizə həm vaxt qazandıracaq, həm də həmin gün baş tutan pulsuz konsert və ya sərgilərdən xəbərdar edəcək.\r\n\r\n    5. Parklar və Damüstü (Rooftop) Məkanlara Vaxt Ayırın\r\n\r\nŞəhərin səs-küyündən yorulanda yerli parklar "nəfəs dərən" rolunu oynayır. Bir neçə saatlıq parkda dincəlmək enerjinizi bərpa edəcək. Axşam saatlarında isə şəhərə yuxarıdan baxmaq üçün bir binanın damındakı kafeyə və ya müşahidə meydançasına çıxın. Şəhərin işıqlarını yüksəklikdən izləmək səyahətin mükəmməl finalı ola bilər.\r\n\r\n
10	Admin	Həftəsonu qaçışları üçün ən uyğun qiymətə icarə paketlərimizlə tanış olun.	2026-01-02	/front/images/img_2.jpg	Həftəsonu üçün xüsusi endirimli kampaniyalar	19	Səyahət məsləhətləri	Professional bloqqer və səyahətçi.	Bakı və ətraf rayonlar üzrə avtomobil səyahətləri sahəsində 5 illik təcrübəyə malikdir.	/front/images/person_1.jpg	Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean.\n\nA small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.\n\nEven the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.\n\nThe Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way.\n\nWhen she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.\n\nA small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth.\n\nEven the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar.\n\nThe Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way.\n\nWhen she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane. Pityful a rethoric question ran over her cheek, then she continued her way.
11	Ibrahim Huseynov	Elektrikli avtomobillərin üstünlükləri, mühərrik texnologiyası və ətraf mühitə təsiri barədə qısa xülasə.	\N	/front/images/car_5.jpg	Elektrikli Maşınların Gələcəyi: Niyə İndi Keçid Etməliyik?	\N	Avtomobil / Texnologiya	\N	\N	/front/images/person_1.jpg	Avtomobil Dünyasında Yeni Era: Elektrikli Maşınlar Son illərdə avtomobil sənayesi böyük bir transformasiya dövrünü yaşayır. Artıq daxili yanma mühərrikli maşınlar yerini səssiz, sürətli və ekoloji təmiz elektrikli nəqliyyat vasitələrinə verir. Bəs bu maşınları bu qədər cəlbedici edən nədir? 1. Ekonomik qənaət: Yanacaq xərclərinin olmaması və daha az texniki qulluq tələb etməsi. 2. Ekoloji təmizlik: Karbon emissiyasının sıfıra endirilməsi. 3 .Yüksək performans: Elektrik mühərriklərinin anlıq fırlanma anı (torque) sayəsində daha sürətli sürətlənmə. Əgər siz də yeni bir maşın almağı düşünürsünüzsə, texnologiyanın bu istiqamətini mütləq nəzərə almalısınız. Gələcək yollarda deyil, batareyalardadır!
5	Emin	Yol kənarında qalmağınızın qarşısını almaq üçün təkər təzyiqi və yağ səviyyəsini necə yoxlamalı?	\N	https://tse4.mm.bing.net/th/id/OIP.CIac5BAbYhrvBddLTcoNxAHaEW?rs=1&pid=ImgDetMain&o=7&rm=3	Uzun yol öncəsi avtomobilin yoxlanılması	\N	Avtomobil / Texnologiya	\N	\N	/front/images/person_1.jpg	Tətilə və ya uzaq məsafəli səfərə çıxmaq həyəcanverici olsa da, yolun ortasında texniki nasazlıqla qarşılaşmaq bütün planlarınızı alt-üst edə bilər. Avtomobilinizin uzun yola hazır olduğundan əmin olmaq həm sizin, həm də sərnişinlərin təhlükəsizliyi üçün mütləqdir. Səfərə çıxmazdan əvvəl aşağıdakı vacib məqamları yoxlamağı unutmayın.\r\n\r\n1. Maye Səviyyələrini Yoxlayın Mühərrik yağı, soyutma mayesi (antifriz), əyləc mayesi və şüşəyuyan suyu mütləq yoxlanılmalıdır. Uzun müddət yüksək sürətlə sürmək mühərrikin daha çox qızmasına səbəb olur, buna görə də soyutma sisteminin tam qaydasında olması həyati əhəmiyyət kəsb edir.\r\n\r\n2. Təkərlərin Vəziyyəti və Təzyiqi Təkərlərin havası avtomobilin istehsalçı tərəfindən müəyyən edilmiş normalarına uyğun olmalıdır. Həmçinin təkərlərin protektor dərinliyini və üzərində hər hansı kəsik və ya şişkinlik olub-olmadığını yoxlayın. Ehtiyat təkərin (zapaska) də saz vəziyyətdə olduğundan əmin olun.\r\n\r\n3. Əyləc Sistemi Əgər əyləcə basdıqda səs gəlirsə və ya pedalda titrəmə hiss edirsinizsə, səfərə çıxmazdan əvvəl əyləc bəndlərini (kolodkaları) mütləq dəyişdirin. Yüksək sürətdə təhlükəsiz dayanma məsafəsi sizin həyatınızı xilas edə bilər.\r\n\r\n4. Akkumulyator və Elektrik Sistemi Akkumulyatorun terminallarında oksidləşmə olub-olmadığını yoxlayın. Faraların, dönmə işıqlarının və stop-siqnalların düzgün işlədiyindən əmin olun. Gecə sürüşü zamanı faraların parlaqlığı çox önəmlidir.\r\n\r\n5. Kondisioner və Filtrlər Yay aylarında uzun yola çıxırsınızsa, kondisioner sisteminin soyutma gücünü yoxlayın. Salon filtrinin təmiz olması həm havanın keyfiyyətini artırır, həm də kondisionerin yükünü azaldır.\r\n\r\n6. Təhlükəsizlik Avadanlıqları Avtomobildə mütləq olması vacib olan ləvazimatları yoxlayın:\r\n\r\nİlk yardım çantası\r\n\r\nYanğınsöndürən balon\r\n\r\nQəza dayanma nişanı (üçbucaq)\r\n\r\nDomkrat və təkər açarı\r\n\r\nNəticə: Cəmi 15-20 dəqiqə vaxt ayıraraq edəcəyiniz bu yoxlamalar sizi yolda qalmaqdan və gözlənilməz xərclərdən qoruyacaq. Unutmayın, təhlükəsiz sürüş hər zaman ən yaxşı səfərdir!
\.


--
-- TOC entry 5060 (class 0 OID 29347)
-- Dependencies: 243
-- Data for Name: bookings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bookings (id, end_date, license_file_path, notes, pickup_location, start_date, status, total_price, car_id, user_id, created_at) FROM stdin;
31	2026-01-17	392d16f2-2594-4eb4-aa00-e8a74d21a4b3.png	\N	Heydar Aliyev Airport	2026-01-16	APPROVED	85	22	8	2026-01-16 08:59:39.122683
32	2026-01-17	6cd9c4ef-ed0d-4d5a-a80e-c662b811856c.png	\N	Baku City Center	2026-01-16	APPROVED	800	31	38	2026-01-16 09:48:01.575235
23	2026-01-13	4eaebd13-6c2d-4461-b05f-ed2028f71d8d.png	\N	Heydar Aliyev Airport	2026-01-12	APPROVED	65	11	6	\N
24	2026-01-13	48a37942-da3a-43d2-a2f1-3c367958c9e9.png	\N	Heydar Aliyev Airport	2026-01-12	APPROVED	90	13	9	\N
28	2026-01-16	fdda14e0-ae9e-414e-aaeb-16085de223c6.png	\N	Baku City Center	2026-01-13	APPROVED	180	14	37	2026-01-13 12:42:42.045466
\.


--
-- TOC entry 5046 (class 0 OID 29189)
-- Dependencies: 229
-- Data for Name: car_features; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.car_features (car_id, feature_name) FROM stdin;
11	GPS
36	[GPS]
47	[GPS
47	WIFI]
48	[GPS
48	WIFI]
37	[[GPS
37	WIFI]]
40	[[GPS
40	WIFI]]
\.


--
-- TOC entry 5040 (class 0 OID 29111)
-- Dependencies: 223
-- Data for Name: categories_cars; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories_cars (category_id, cars_id) FROM stdin;
\.


--
-- TOC entry 5068 (class 0 OID 29410)
-- Dependencies: 251
-- Data for Name: chats; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.chats (id, content, sender_name, session_id, receiver_name, email, created_at) FROM stdin;
1	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	\N
2	sdfsdf	emin	user_396j1boaj	ADMIN	\N	\N
3	salam	emin	user_396j1boaj	ADMIN	\N	\N
4	slam	ADMIN	user_396j1boaj	user_396j1boaj	\N	\N
5	hey ne oldu	emin	user_396j1boaj	ADMIN	\N	\N
6	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	\N
7	fsdfsd	emin	user_396j1boaj	ADMIN	\N	\N
8	fsdfsd	ADMIN	user_396j1boaj	user_396j1boaj	\N	\N
9	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	\N
10	salam	emin	user_396j1boaj	ADMIN	\N	\N
11	slam	emin	user_396j1boaj	ADMIN	\N	\N
12	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	\N
13	fsdfs	emin	user_396j1boaj	ADMIN	\N	\N
14	Mən qoşuldum	oruc	user_396j1boaj	ADMIN	\N	\N
15	salam	oruc	user_396j1boaj	ADMIN	\N	\N
16	Mən qoşuldum	Nizami	user_ydlqidp9i	ADMIN	\N	\N
17	salam	Nizami	user_ydlqidp9i	ADMIN	\N	\N
18	sdfsdf	Nizami	user_ydlqidp9i	ADMIN	\N	\N
19	Qoşulubuser_396j1boaj	emin	user_396j1boaj	ADMIN	\N	\N
20	salam	emin	user_396j1boaj	ADMIN	\N	\N
21	Qoşulub user_396j1boaj	emin	user_396j1boaj	ADMIN	\N	\N
22	Sistem: emin (fesoga9500@eubonus.com) qoşuldu.	emin	user_396j1boaj	ADMIN	\N	\N
23	salam	emin	user_396j1boaj	ADMIN	\N	\N
24	Sistem: emin (sdfsdfsdf@gmail.com) qoşuldu.	emin	user_396j1boaj	ADMIN	\N	\N
25	Sistem: kobiga2905@jparksky.com (kobiga2905@jparksky.com) qoşuldu.	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
26	salam	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
27	hardasan	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
28	evde harda olacamki	ADMIN	user_396j1boaj	user_396j1boaj	\N	\N
29	Sistem: kobiga2905@jparksky.com (kobiga2905@jparksky.com) qoşuldu.	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
30	ne oldu	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
31	bilmirem	ADMIN	user_396j1boaj	user_396j1boaj	\N	\N
32	Sistem: emin (kobiga2905@jparksky.com) qoşuldu.	emin	user_396j1boaj	ADMIN	\N	\N
33	salam	emin	user_396j1boaj	ADMIN	\N	\N
34	sen kimsen	ADMIN	user_396j1boaj	user_396j1boaj	\N	\N
35	tanimadim	ADMIN	user_396j1boaj	user_396j1boaj	\N	\N
36	Sistem: İstifadəçi yenidən qoşuldu.	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
37	salam	kobiga2905@jparksky.com	user_396j1boaj	ADMIN	\N	\N
38	Sistem: İstifadəçi yenidən qoşuldu.	emin	user_396j1boaj	ADMIN	\N	\N
39	fsdfsd	emin	user_396j1boaj	ADMIN	\N	\N
40	Mən qoşuldum	fsdf	user_396j1boaj	ADMIN	\N	\N
41	dasdsa	fsdf	user_396j1boaj	ADMIN	\N	\N
42	salam	fsdf	user_396j1boaj	ADMIN	\N	\N
43	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	\N
44	adasda	emin	user_396j1boaj	ADMIN	\N	\N
45	dasda	ADMIN	user_ydlqidp9i	user_ydlqidp9i	\N	\N
46	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	\N
47	salam	emin	user_396j1boaj	ADMIN	\N	\N
48	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:06:26.375066
49	salam	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:06:28.59203
50	salam	ADMIN	user_396j1boaj	user_396j1boaj	\N	2026-01-15 09:06:35.649987
51	hi	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:06:41.087954
52	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:08:54.924397
53	salam	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:08:57.793187
54	slam	ADMIN	user_396j1boaj	user_396j1boaj	\N	2026-01-15 09:09:03.602039
55	mesaj	ADMIN	user_396j1boaj	user_396j1boaj	\N	2026-01-15 09:09:20.62513
56	asfsdf	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:09:25.184129
57	fsdsdf	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:09:31.951009
58	fsdfsd	ADMIN	user_396j1boaj	user_396j1boaj	\N	2026-01-15 09:09:35.196653
59	fsdfsd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:09:45.54296
60	Mən qoşuldum	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:12:38.490894
61	asdd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:12:40.472299
62	dasd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:12:47.831946
63	adasd	ADMIN	\N	user_396j1boaj	\N	2026-01-15 09:12:53.609042
64	fsdf	ADMIN	\N	user_396j1boaj	\N	2026-01-15 09:12:55.801668
65	dasd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:12:58.888095
66	Online	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:16:38.228996
67	asa	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:16:39.560123
68	dasds	ADMIN	\N	user_396j1boaj	\N	2026-01-15 09:16:45.248623
69	asdasd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:16:48.199979
70	Online	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:21:15.872791
71	dasd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:21:17.728629
72	ddas	ADMIN	\N	user_396j1boaj	\N	2026-01-15 09:21:22.399728
73	dasdsa	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:22:18.127794
74	dada	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:22:24.352023
75	dasdsa	ADMIN	\N	user_396j1boaj	\N	2026-01-15 09:22:28.637433
76	dada	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:22:31.207484
77	Online	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:25:27.554478
78	asd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:25:30.056031
79	dasda	ADMIN	user_396j1boaj	user_396j1boaj	\N	2026-01-15 09:25:52.824693
80	asdasd	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:25:57.359929
81	Online	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:27:55.138224
82	salam	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:27:57.216482
83	dsds	ADMIN	user_396j1boaj	user_396j1boaj	\N	2026-01-15 09:28:03.576879
84	dasdas	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:28:06.159451
85	Online	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:36:26.58027
86	asdsad	emin	user_396j1boaj	ADMIN	\N	2026-01-15 09:36:28.343971
87	Online	emin	kobiga2905@jparksky.com	ADMIN	\N	2026-01-15 09:41:41.124232
88	asdsd	emin	kobiga2905@jparksky.com	ADMIN	\N	2026-01-15 09:41:42.679437
89	asdad	emin	kobiga2905@jparksky.com	ADMIN	\N	2026-01-15 09:42:41.032836
90	asdsad	emin	kobiga2905@jparksky.com	ADMIN	\N	2026-01-15 09:47:49.926147
91	Mən qoşuldum	emin	emin	ADMIN	emin	2026-01-15 09:55:01.817558
92	salam	emin	emin	ADMIN	emin	2026-01-15 09:55:04.840161
93	sad	emin	emin	ADMIN	emin	2026-01-15 09:55:22.247494
94	dadasd	emin	emin	ADMIN	emin	2026-01-15 09:55:40.584066
95	Mən qoşuldum	emin	emin	ADMIN	emin	2026-01-15 09:57:51.017245
96	dasdsa	emin	emin	ADMIN	emin	2026-01-15 09:57:53.48112
97	dasdsa	emin	emin	ADMIN	emin	2026-01-15 09:58:04.936123
98	Mən qoşuldum	emin	emin	ADMIN	emin	2026-01-15 09:58:14.13756
99	dadsa	emin	emin	ADMIN	emin	2026-01-15 09:58:18.927415
100	dsdsad	ADMIN	emin	emin	ADMIN	2026-01-15 09:58:27.736386
101	Mən qoşuldum	emin	emin	ADMIN	emin	2026-01-15 10:08:04.994147
102	dasd	emin	emin	ADMIN	emin	2026-01-15 10:08:06.943288
103	dsd	ADMIN	emin	emin	ADMIN	2026-01-15 10:08:26.852475
104	adsdas	emin	emin	ADMIN	emin	2026-01-15 10:08:30.701634
105	Mən qoşuldum	tunar	tunar	ADMIN	tunar	2026-01-15 10:23:06.423239
106	salam	tunar	tunar	ADMIN	tunar	2026-01-15 10:23:15.64413
107	salam	ADMIN	tunar	tunar	ADMIN	2026-01-15 10:23:29.238639
108	kimsen	tunar	tunar	ADMIN	tunar	2026-01-15 10:23:38.612792
109	Mən qoşuldum	elmir	elmir	ADMIN	elmir	2026-01-15 10:28:34.881266
110	salam	elmir	elmir	ADMIN	elmir	2026-01-15 10:28:36.799132
111	salam	ADMIN	elmir	elmir	ADMIN	2026-01-15 10:29:59.322745
112	Mən qoşuldum	tugan	tugan	ADMIN	tugan	2026-01-15 10:34:40.96787
113	salam	tugan	tugan	ADMIN	tugan	2026-01-15 10:34:43.548957
114	salam	ADMIN	tugan	tugan	ADMIN	2026-01-15 10:35:16.952766
115	Mən qoşuldum	test	test	ADMIN	test	2026-01-15 10:38:27.865795
116	salam	test	test	ADMIN	test	2026-01-15 10:38:29.901722
117	kimdir	test	test	ADMIN	test	2026-01-15 10:38:44.501531
118	senkimsen	ADMIN	test	test	ADMIN	2026-01-15 10:38:59.703939
119	Mən qoşuldum	samir	samir	ADMIN	samir	2026-01-15 10:46:18.87126
120	salam	samir	samir	ADMIN	samir	2026-01-15 10:46:21.005931
121	Mən qoşuldum	ceyhun	ceyhun	ADMIN	ceyhun	2026-01-15 10:51:33.383395
122	salam	ceyhun	ceyhun	ADMIN	ceyhun	2026-01-15 10:51:35.157567
123	salam	ADMIN	ceyhun	ceyhun	ADMIN	2026-01-15 10:51:51.250247
124	Mən qoşuldum	elos	elos	ADMIN	elos	2026-01-15 10:56:25.207651
125	salalm	elos	elos	ADMIN	elos	2026-01-15 10:56:27.0936
126	salam	ADMIN	elos	elos	ADMIN	2026-01-15 10:56:41.638544
127	Mən qoşuldum	teymur	teymur	ADMIN	teymur	2026-01-15 10:59:34.949933
128	sasa	teymur	teymur	ADMIN	teymur	2026-01-15 10:59:36.800489
129	adasd	ADMIN	teymur	teymur	ADMIN	2026-01-15 10:59:49.886427
130	das	teymur	teymur	ADMIN	teymur	2026-01-15 10:59:56.61442
131	dasda	ADMIN	teymur	teymur	ADMIN	2026-01-15 11:00:06.299596
132	dadsa	teymur	teymur	ADMIN	teymur	2026-01-15 11:01:31.830433
133	asdasd	teymur	teymur	ADMIN	teymur	2026-01-15 11:01:39.701954
134	dasdasd	teymur	teymur	ADMIN	teymur	2026-01-15 11:01:47.68544
135	Mən qoşuldum	tumba	tumba	ADMIN	tumba	2026-01-15 12:13:19.161581
136	tumba	tumba	tumba	ADMIN	tumba	2026-01-15 12:13:22.587703
137	Mən qoşuldum	emin	emin	ADMIN	emin	2026-01-15 12:24:31.612578
138	salam	emin	emin	ADMIN	emin	2026-01-15 12:24:34.373077
139	salam	ADMIN	emin	emin	ADMIN	2026-01-15 12:24:54.439012
140	Mən qoşuldum	gulnar	gulnar	ADMIN	gulnar	2026-01-15 12:27:53.429569
141	salam	gulnar	gulnar	ADMIN	gulnar	2026-01-15 12:27:55.813873
142	salam	ADMIN	gulnar	gulnar	ADMIN	2026-01-15 12:28:07.8221
143	Mən qoşuldum	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-15 12:32:25.451327
144	salam	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-15 12:32:34.268784
145	hi	ADMIN	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	2026-01-15 12:32:55.638442
146	necesen	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-15 12:33:01.444549
147	yaxsiyam sagol	ADMIN	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	2026-01-15 12:33:07.957571
148	Mən qoşuldum	sevda	sevda	ADMIN	sevda	2026-01-15 12:35:14.161514
149	salam	sevda	sevda	ADMIN	sevda	2026-01-15 12:35:16.229274
150	salam	ADMIN	sevda	sevda	ADMIN	2026-01-15 12:35:28.550629
151	Mən qoşuldum	sevda	sevda	ADMIN	sevda	2026-01-15 19:36:29.351044
152	salam	sevda	sevda	ADMIN	sevda	2026-01-15 19:36:32.180149
153	salam	ADMIN	sevda	sevda	ADMIN	2026-01-15 19:36:53.040201
154	Mən qoşuldum	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-15 19:37:27.172787
155	salam	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-15 19:37:31.066935
156	salam	ADMIN	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	2026-01-15 19:37:42.468747
157	salam	August	August	ADMIN	August	2026-01-15 20:46:31.122872
158	kimdir	ADMIN	August	August	ADMIN	2026-01-15 20:46:39.658737
159	men cosqun	August	August	ADMIN	August	2026-01-15 20:46:48.825755
160	salam	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-16 09:06:47.466243
161	salam	ADMIN	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	2026-01-16 09:07:15.53935
162	hardasana	kobiga2905@jparksky.com	kobiga2905@jparksky.com	ADMIN	kobiga2905@jparksky.com	2026-01-16 09:07:25.250134
163	salam	Hacibala	Hacibala	ADMIN	Hacibala	2026-01-16 09:38:24.692781
164	menim bir fikirrim var	Hacibala	Hacibala	ADMIN	Hacibala	2026-01-16 09:38:32.102719
165	salam	ADMIN	Hacibala	Hacibala	ADMIN	2026-01-16 09:38:42.591015
166	sizi dinleyirem	ADMIN	Hacibala	Hacibala	ADMIN	2026-01-16 09:38:50.012724
167	salam	poreyis909@ixospace.com	poreyis909@ixospace.com	ADMIN	poreyis909@ixospace.com	2026-01-16 09:57:09.165963
168	hi	xerot18125@noihse.com	xerot18125@noihse.com	ADMIN	xerot18125@noihse.com	2026-01-17 17:19:13.547134
\.


--
-- TOC entry 5058 (class 0 OID 29313)
-- Dependencies: 241
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.comments (id, created_at, message, name, blog_id, email) FROM stdin;
1	2026-01-01 10:00:00	Çox maraqlı məqalədir, təşəkkürlər!	Ali Memmedov	10	\N
21	2026-01-05 02:23:08.343966	Maşın kirayəsi şərtləri çox səmərəlidir.	Vusal	10	\N
24	2026-01-12 08:34:52.664952	Salam	Emin 	11	eminelxanoglu@gmail.com
25	2026-01-13 09:35:07.295103	salam	Emin 	1	eminelxanoglu@gmail.com
26	2026-01-16 09:32:38.251474	Salam\r\n	Hacibala	1	fesoga9500@eubonus.com
\.


--
-- TOC entry 5042 (class 0 OID 29115)
-- Dependencies: 225
-- Data for Name: contact_messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contact_messages (id, email, message, name, subject) FROM stdin;
\.


--
-- TOC entry 5054 (class 0 OID 29238)
-- Dependencies: 237
-- Data for Name: messages; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.messages (id, created_at, email, first_name, last_name, message, user_id) FROM stdin;
1	\N	eminelxanoglu@gmail.com	EMİN	MƏMMƏDOV	salam	\N
2	2026-01-02 18:47:40.334908	admin@gmail.com	Admin	Adminov	admin	\N
3	2026-01-08 08:14:54.024491	eminelxanoglu@gmail.com	EMİN	MƏMMƏDOV	salam	\N
4	2026-01-11 19:32:31.146206	admin@gmail.com	EMİN	MƏMMƏDOV	salam\r\n	1
5	2026-01-12 08:35:41.227964	eminelxanoglu@gmail.com	Emin	Emin	hi	\N
\.


--
-- TOC entry 5066 (class 0 OID 29402)
-- Dependencies: 249
-- Data for Name: notifications; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notifications (id, created_at, is_read, link, message, type) FROM stdin;
1	2026-01-13 12:41:44.2974	t	/dashboard/user/index	New user registered: Emin	USER
2	2026-01-13 12:42:42.04846	t	/dashboard/bookings/index	New booking for Ford	BOOKING
14	2026-01-15 11:01:47.694985	t	/dashboard/chat?user=teymur	New message from teymur	CHAT
13	2026-01-15 11:01:39.713035	t	/dashboard/chat?user=teymur	New message from teymur	CHAT
12	2026-01-15 11:01:31.839955	t	/dashboard/chat?user=teymur	New message from teymur	CHAT
11	2026-01-15 10:59:56.623467	t	/dashboard/chat?user=teymur	New message from teymur	CHAT
10	2026-01-15 10:59:36.808617	t	/dashboard/chat?user=teymur	New message from teymur	CHAT
9	2026-01-15 10:59:35.063082	t	/dashboard/chat?user=teymur	New message from teymur	CHAT
8	2026-01-15 10:56:27.512329	t	/dashboard/chat?user=elos	New message from elos	CHAT
7	2026-01-15 10:56:25.318578	t	/dashboard/chat?user=elos	New message from elos	CHAT
6	2026-01-15 10:51:35.162601	t	/dashboard/chat?user=ceyhun	New message from ceyhun	CHAT
5	2026-01-15 10:51:33.465534	t	/dashboard/chat?user=ceyhun	New message from ceyhun	CHAT
4	2026-01-15 10:46:21.011927	t	/dashboard/chat?user=samir	New message from samir	CHAT
3	2026-01-15 10:46:18.961265	t	/dashboard/chat?user=samir	New message from samir	CHAT
16	2026-01-15 12:13:22.593229	t	/dashboard/chat?user=tumba	New message from tumba	CHAT
15	2026-01-15 12:13:19.209237	t	/dashboard/chat?user=tumba	New message from tumba	CHAT
18	2026-01-15 12:24:34.379075	t	/dashboard/chat?user=emin	New message from emin	CHAT
17	2026-01-15 12:24:31.617589	t	/dashboard/chat?user=emin	New message from emin	CHAT
20	2026-01-15 12:27:55.820383	t	/dashboard/chat?user=gulnar	New message from gulnar	CHAT
19	2026-01-15 12:27:53.519212	t	/dashboard/chat?user=gulnar	New message from gulnar	CHAT
22	2026-01-15 12:32:34.277784	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
21	2026-01-15 12:32:25.527327	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
23	2026-01-15 12:33:01.45255	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
25	2026-01-15 12:35:16.234284	t	/dashboard/chat?user=sevda	New message from sevda	CHAT
24	2026-01-15 12:35:14.219511	t	/dashboard/chat?user=sevda	New message from sevda	CHAT
27	2026-01-15 19:36:32.185145	t	/dashboard/chat?user=sevda	New message from sevda	CHAT
29	2026-01-15 19:37:31.072933	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
28	2026-01-15 19:37:27.182778	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
26	2026-01-15 19:36:29.405046	t	/dashboard/chat?user=sevda	New message from sevda	CHAT
32	2026-01-15 21:19:20.343569	t	/dashboard/bookings/index	New booking for BYD	BOOKING
33	2026-01-15 21:27:20.514364	t	/dashboard/bookings/index	New booking for Nissan	BOOKING
35	2026-01-16 09:06:47.483337	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
34	2026-01-16 08:59:39.179459	t	/dashboard/bookings/index	New booking for Nissan	BOOKING
31	2026-01-15 20:46:48.832275	t	/dashboard/chat?user=August	New message from August	CHAT
36	2026-01-16 09:07:25.258489	t	/dashboard/chat?user=kobiga2905@jparksky.com	New message from kobiga2905@jparksky.com	CHAT
30	2026-01-15 20:46:31.157972	t	/dashboard/chat?user=August	New message from August	CHAT
38	2026-01-16 09:38:32.11272	t	/dashboard/chat?user=Hacibala	New message from Hacibala	CHAT
37	2026-01-16 09:38:24.728785	t	/dashboard/chat?user=Hacibala	New message from Hacibala	CHAT
40	2026-01-16 09:48:01.578236	t	/dashboard/bookings/index	New booking for Bentley	BOOKING
39	2026-01-16 09:46:06.215167	t	/dashboard/user/index	New user registered: Hacibala	USER
41	2026-01-16 09:57:09.219228	t	/dashboard/chat?user=poreyis909@ixospace.com	New message from poreyis909@ixospace.com	CHAT
42	2026-01-17 16:13:11.145364	f	/dashboard/user/index	New user registered: Emin	USER
43	2026-01-17 16:19:45.193117	f	/dashboard/user/index	New user registered: Emin	USER
44	2026-01-17 16:23:39.697744	f	/dashboard/user/index	New user registered: Emin	USER
45	2026-01-17 17:09:40.33614	f	/dashboard/user/index	New user registered: Hacibala	USER
46	2026-01-17 17:19:13.609711	f	/dashboard/chat?user=xerot18125@noihse.com	New message from xerot18125@noihse.com	CHAT
47	2026-01-17 18:43:31.584513	f	/dashboard/user/index	New user registered: Teymur	USER
\.


--
-- TOC entry 5064 (class 0 OID 29393)
-- Dependencies: 247
-- Data for Name: otp; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.otp (id, email, expires_at, otp_code) FROM stdin;
1	gafayil353@eubonus.com	2026-01-12 19:52:35.778814	255209
2	remile5719@feanzier.com	2026-01-12 19:55:57.938042	533503
3	tomomoj652@feanzier.com	2026-01-12 20:03:50.96542	302155
4	nopom34927@feanzier.com	2026-01-12 20:10:09.861416	590507
5	vogone7020@feanzier.com	2026-01-12 20:17:08.976201	460473
6	xehepic915@eubonus.com	2026-01-12 20:18:59.974738	443948
7	lositi1653@eubonus.com	2026-01-12 20:20:35.838769	454915
8	dexivam491@feanzier.com	2026-01-12 20:23:38.305102	657747
9	bakiraj763@eubonus.com	2026-01-12 20:25:46.711174	583939
10	veges90113@feanzier.com	2026-01-12 20:30:55.601313	320838
11	hasabo7723@feanzier.com	2026-01-12 20:33:44.516346	739670
12	dabelig375@feanzier.com	2026-01-12 20:36:20.778508	712276
13	yikaka5880@feanzier.com	2026-01-12 20:38:46.346057	496829
14	notefo3236@feanzier.com	2026-01-12 20:45:57.816975	299756
18	melege1160@feanzier.com	2026-01-13 08:31:11.148966	126007
19	hecenoy277@eubonus.com	2026-01-13 08:41:12.183379	270258
20	nemeho6904@eubonus.com	2026-01-13 08:47:47.729124	832986
21	eminelxanoglu@gmail.com	2026-01-13 08:49:50.514008	937800
29	jedileh964@elafans.com	2026-01-17 16:18:10.61839	289039
30	daxiy87814@oremal.com	2026-01-17 16:24:45.105126	906737
\.


--
-- TOC entry 5048 (class 0 OID 29204)
-- Dependencies: 231
-- Data for Name: rentals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rentals (id, dropoff_date, pickup_date, car_id, status) FROM stdin;
\.


--
-- TOC entry 5034 (class 0 OID 29070)
-- Dependencies: 217
-- Data for Name: spring_session; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.spring_session (primary_id, session_id, creation_time, last_access_time, max_inactive_interval, expiry_time, principal_name) FROM stdin;
2872c1e7-5c56-439f-902c-78304e5d1d1c	15de07b2-909d-40c0-9400-34023a1adbd5	1767932347878	1767932983308	1800	1767934783308	\N
\.


--
-- TOC entry 5035 (class 0 OID 29078)
-- Dependencies: 218
-- Data for Name: spring_session_attributes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.spring_session_attributes (session_primary_id, attribute_name, attribute_bytes) FROM stdin;
2872c1e7-5c56-439f-902c-78304e5d1d1c	org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE	\\xaced0005737200106a6176612e7574696c2e4c6f63616c657ef811609c30f9ec03000649000868617368636f64654c0007636f756e7472797400124c6a6176612f6c616e672f537472696e673b4c000a657874656e73696f6e7371007e00014c00086c616e677561676571007e00014c000673637269707471007e00014c000776617269616e7471007e00017870ffffffff74000071007e0003740002617a71007e000371007e000378
\.


--
-- TOC entry 5052 (class 0 OID 29223)
-- Dependencies: 235
-- Data for Name: team_members; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.team_members (id, bio, image_url, name, role) FROM stdin;
1	Avtomobil sektorunda 15 illik təcrübəyə malikdir.	/front/images/person_1.jpg	James Doe	Founder & CEO
2	Logistika və müştəri xidmətləri üzrə mütəxəssis.	/front/images/person_2.jpg	Jane Smith	Operations Manager
3	Avtomobil parkının texniki vəziyyətinə cavabdehdir.	/front/images/person_3.jpg	Robert Wilson	Fleet Supervisor
4	Müştərilərin suallarını 24/7 cavablandırır.	/front/images/person_4.jpg	Sarah Connor	Customer Support
5	Ən yaxşı icarə paketlərini sizə təklif edir.	/front/images/person_5.jpg	Michael Scott	Sales Manager
6	Şirkətin rəqəmsal inkişafı üzərində işləyir.	/front/images/person_1.jpg	Alice Brown	Marketing Specialist
\.


--
-- TOC entry 5056 (class 0 OID 29287)
-- Dependencies: 239
-- Data for Name: testimonials; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.testimonials (id, author_name, author_role, content, created_at, image_url, rating, is_approved) FROM stdin;
1	Elnur Məmmədov	Proqramçı	Maşınların texniki vəziyyəti əla idi. Şəhər daxili sürət üçün çox rahatdır.	2026-01-03	/front/images/person_1.jpg	5	f
2	Aysel Kərimova	Daimi Müştəri	Hər dəfə bu servisdən istifadə edirəm. Qiymətlər bazara görə çox münasibdir.	2026-01-03	/front/images/person_2.jpg	5	f
3	Rəşad Əliyev	Biznesmen	Vito kirayələmişdik, təmizliyi və komfortu bizi heyran etdi. Təşəkkürlər!	2026-01-03	/front/images/person_3.jpg	4	f
4	Günel Həsənova	Səyahətçi	Həftəsonu Qəbələ səfərimiz üçün SUV götürdük. Çox razı qaldıq.	2026-01-03	/front/images/person_4.jpg	5	f
5	Kamran Vəliyev	Mühəndis	Sənədləşmə cəmi 5 dəqiqə çəkdi. Sürətli xidmət üçün minnətdaram.	2026-01-03	/front/images/person_5.jpg	5	f
6	Zaur Qasımov	Logistika Meneceri	Şirkətimiz üçün bir neçə maşın icarəyə götürdük. Korporativ xidmət yüksək səviyyədədir.	2026-01-03	/front/images/person_1.jpg	4	f
7	Leyla Baxşəliyeva	Dizayner	Maşın çox səliqəli idi, salonun qoxusu və təmizliyi mükəmməl idi.	2026-01-03	/front/images/person_2.jpg	5	f
\.


--
-- TOC entry 5062 (class 0 OID 29371)
-- Dependencies: 245
-- Data for Name: travel_routes; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.travel_routes (id, description, map_url, name, recommended_car_type, stops) FROM stdin;
1	Bakıdan başlayan bu səfər sizi Beşbarmaq dağının yanından keçirərək Qubanın mənzərəli Qəçrəş meşələrinə aparacaq.	https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d191638.2913349718!2d48.35338666567523!3d41.36538965902137!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x403730761e05d259%3A0x64366055f2849e7!2sGuba!5e0!3m2!1sen!2saz!4v1700000000000	Şimalın Mirvarisi: Quba	SUV	Bakı -> Beşbarmaq -> Qəçrəş -> Çenlibel gölü
2	Sitrus meyvələri və çay plantasiyaları ilə zəngin olan cənub bölgəsinə səyahət edin.	https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d197607.7287957488!2d48.78494870000001!3d38.7523953!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3f8733f3883a479b%3A0xe67598c1143c6b6d!2sLankaran!5e0!3m2!1sen!2saz!4v1700000000000	Cənub Nağılı: Lənkəran	Sedan	Bakı -> Salyan -> Lənkəran -> Xanbulan gölü
\.


--
-- TOC entry 5045 (class 0 OID 29139)
-- Dependencies: 228
-- Data for Name: users_bookings; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users_bookings (user_id, bookings_id) FROM stdin;
\.


--
-- TOC entry 5074 (class 0 OID 0)
-- Dependencies: 232
-- Name: blogs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.blogs_id_seq', 12, false);


--
-- TOC entry 5075 (class 0 OID 0)
-- Dependencies: 242
-- Name: bookings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bookings_id_seq', 32, true);


--
-- TOC entry 5076 (class 0 OID 0)
-- Dependencies: 219
-- Name: cars_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cars_id_seq', 48, true);


--
-- TOC entry 5077 (class 0 OID 0)
-- Dependencies: 221
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categories_id_seq', 6, false);


--
-- TOC entry 5078 (class 0 OID 0)
-- Dependencies: 250
-- Name: chats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.chats_id_seq', 168, true);


--
-- TOC entry 5079 (class 0 OID 0)
-- Dependencies: 240
-- Name: comments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.comments_id_seq', 26, true);


--
-- TOC entry 5080 (class 0 OID 0)
-- Dependencies: 224
-- Name: contact_messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.contact_messages_id_seq', 1, false);


--
-- TOC entry 5081 (class 0 OID 0)
-- Dependencies: 236
-- Name: messages_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.messages_id_seq', 5, true);


--
-- TOC entry 5082 (class 0 OID 0)
-- Dependencies: 248
-- Name: notifications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notifications_id_seq', 47, true);


--
-- TOC entry 5083 (class 0 OID 0)
-- Dependencies: 246
-- Name: otp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.otp_id_seq', 33, true);


--
-- TOC entry 5084 (class 0 OID 0)
-- Dependencies: 230
-- Name: rentals_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rentals_id_seq', 1, false);


--
-- TOC entry 5085 (class 0 OID 0)
-- Dependencies: 234
-- Name: team_members_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.team_members_id_seq', 8, true);


--
-- TOC entry 5086 (class 0 OID 0)
-- Dependencies: 238
-- Name: testimonials_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.testimonials_id_seq', 10, false);


--
-- TOC entry 5087 (class 0 OID 0)
-- Dependencies: 244
-- Name: travel_routes_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.travel_routes_id_seq', 2, true);


--
-- TOC entry 5088 (class 0 OID 0)
-- Dependencies: 226
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.users_id_seq', 43, true);


-- Completed on 2026-01-17 22:54:22

--
-- PostgreSQL database dump complete
--

\unrestrict DOrQIBhnKMr4eaWVkaOmKqQYRsXuXSo5xxFCdNmj2MTL4HemZg1FWiFHhKxaqmh

