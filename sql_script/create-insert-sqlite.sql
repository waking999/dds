
drop table IF EXISTS "dataset";
CREATE TABLE "dataset" (
	id	INTEGER PRIMARY KEY ,
	d_name	varchar2(30),
	d_path	varchar2(30)
);
INSERT INTO "dataset" (id,d_name,d_path) VALUES (1,'DIMACS','/DIMACS');
INSERT INTO "dataset" (id,d_name,d_path) VALUES (2,'BHOSLIB','/BHOSLIB');
INSERT INTO "dataset" (id,d_name,d_path) VALUES (3,'KONECT','/KONECT');
INSERT INTO "dataset" (id,d_name,d_path) VALUES (4,'DIMACS-MIS','/DIMACS-MIS');
INSERT INTO "dataset" (id,d_name,d_path) VALUES (5,'netrepo','/netrepo');


drop table IF EXISTS "instance";
CREATE TABLE "instance" (
	i_id	INTEGER,
	d_id	INTEGER,
    i_code varchar2(20),
	i_name	varchar2(30),
	path_name	varchar2(30),
	v_count	INTEGER,
	e_count	INTEGER,
    to_be_tested INTEGER
);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,1,'brock200_2','brock200_2','/brock200_2.clq',200,9876,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,1,'brock200_4','brock200_4','/brock200_4.clq',200, 13089,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,1,'brock400_2','brock400_2','/brock400_2.clq',400, 59786,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,1,'brock400_4','brock400_4','/brock400_4.clq',400, 59765,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,1,'brock800_2','brock800_2','/brock800_2.clq',800, 208166,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,1,'brock800_4','brock800_4','/brock800_4.clq',800, 207643,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,1,'C1000.9','C1000.9','/C1000.9.clq',1000, 450079,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,1,'C125.9','C125.9','/C125.9.clq',125 ,6963,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,1,'C2000.5','C2000.5','/C2000.5.clq',2000, 999836,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,1,'C2000.9','C2000.9','/C2000.9.clq',2000, 1799532,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,1,'C250.9','C250.9','/C250.9.clq',250,27984,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,1,'C4000.5','C4000.5','/C4000.5.clq',4000, 4000268,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,1,'C500.9','C500.9','/C500.9.clq',500 ,112332,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,1,'DSJC1000.5','DSJC1000.5','/DSJC1000.5.clq',1000, 249826,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,1,'DSJC500.5','DSJC500.5','/DSJC500.5.clq',500, 62624,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,1,'gen200_p0.9_44','gen200_p0.9_44','/gen200_p0.9_44.clq',200,17910,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,1,'gen200_p0.9_55','gen200_p0.9_55','/gen200_p0.9_55.clq',200,17910,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (18,1,'gen400_p0.9_55','gen400_p0.9_55','/gen400_p0.9_55.clq',400,71820,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (19,1,'gen400_p0.9_65','gen400_p0.9_65','/gen400_p0.9_65.clq',400,71820,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (20,1,'gen400_p0.9_75','gen400_p0.9_75','/gen400_p0.9_75.clq',400,71820,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (21,1,'hamming10-4','hamming10-4','/hamming10-4.clq',1024,434176,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (22,1,'hamming8-4','hamming8-4','/hamming8-4.clq',256 ,20864,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (23,1,'keller4','keller4','/keller4.clq',171, 9435,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (24,1,'keller5','keller5','/keller5.clq',776, 225990,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (25,1,'keller6','keller6','/keller6.clq',3361,4619898,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (26,1,'MANN_a27','MANN_a27','/MANN_a27.clq',378, 70551,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (27,1,'MANN_a81','MANN_a81','/MANN_a81.clq',3321, 5506380,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (28,1,'p_hat1500-1','p_hat1500-1','/p_hat1500-1.clq',1500,284923,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (29,1,'p_hat1500-2','p_hat1500-2','/p_hat1500-2.clq',1500,568960,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (30,1,'p_hat1500-3','p_hat1500-3','/p_hat1500-3.clq',1500,847244,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (31,1,'p_hat300-1','p_hat300-1','/p_hat300-1.clq',300,10933,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (32,1,'p_hat300-2','p_hat300-2','/p_hat300-2.clq',300,21928,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (33,1,'p_hat300-3','p_hat300-3','/p_hat300-3.clq',300,33390,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (34,1,'p_hat700-1','p_hat700-1','/p_hat700-1.clq',700,60999,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (35,1,'p_hat700-2','p_hat700-2','/p_hat700-2.clq',700,121728,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (36,1,'p_hat700-3','p_hat700-3','/p_hat700-3.clq',700,183010,0);		

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,2,'frb30-15-1','frb30-15-1','/frb30-15-mis/frb30-15-1.mis',450,17827,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,2,'frb30-15-2','frb30-15-2','/frb30-15-mis/frb30-15-2.mis',450,17874,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,2,'frb30-15-3','frb30-15-3','/frb30-15-mis/frb30-15-3.mis',450,17809,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,2,'frb30-15-4','frb30-15-4','/frb30-15-mis/frb30-15-4.mis',450,17831,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,2,'frb30-15-5','frb30-15-5','/frb30-15-mis/frb30-15-5.mis',450,17794,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,2,'frb35-17-1','frb35-17-1','/frb35-17-mis/frb35-17-1.mis',595,27856,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,2,'frb35-17-2','frb35-17-2','/frb35-17-mis/frb35-17-2.mis',595,27847,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,2,'frb35-17-3','frb35-17-3','/frb35-17-mis/frb35-17-3.mis',595,27931,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,2,'frb35-17-4','frb35-17-4','/frb35-17-mis/frb35-17-4.mis',595,27842,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,2,'frb35-17-5','frb35-17-5','/frb35-17-mis/frb35-17-5.mis',595,28143,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,2,'frb40-19-1','frb40-19-1','/frb40-19-mis/frb40-19-1.mis',760,41314,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,2,'frb40-19-2','frb40-19-2','/frb40-19-mis/frb40-19-2.mis',760,41263,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,2,'frb40-19-3','frb40-19-3','/frb40-19-mis/frb40-19-3.mis',760,41095,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,2,'frb40-19-4','frb40-19-4','/frb40-19-mis/frb40-19-4.mis',760,41605,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,2,'frb40-19-5','frb40-19-5','/frb40-19-mis/frb40-19-5.mis',760,41619,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,2,'frb45-21-1','frb45-21-1','/frb45-21-mis/frb45-21-1.mis',945,59186,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,2,'frb45-21-2','frb45-21-2','/frb45-21-mis/frb45-21-2.mis',945,58624,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (18,2,'frb45-21-3','frb45-21-3','/frb45-21-mis/frb45-21-3.mis',945,58245,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (19,2,'frb45-21-4','frb45-21-4','/frb45-21-mis/frb45-21-4.mis',945,58549,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (20,2,'frb45-21-5','frb45-21-5','/frb45-21-mis/frb45-21-5.mis',945,58579,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (21,2,'frb50-23-1','frb50-23-1','/frb50-23-mis/frb50-23-1.mis',1150,80072,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (22,2,'frb50-23-2','frb50-23-2','/frb50-23-mis/frb50-23-2.mis',1150,80851,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (23,2,'frb50-23-3','frb50-23-3','/frb50-23-mis/frb50-23-3.mis',1150,81068,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (24,2,'frb50-23-4','frb50-23-4','/frb50-23-mis/frb50-23-4.mis',1150,80258,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (25,2,'frb50-23-5','frb50-23-5','/frb50-23-mis/frb50-23-5.mis',1150,80035,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (26,2,'frb53-24-1','frb53-24-1','/frb53-24-mis/frb53-24-1.mis',1272,94227,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (27,2,'frb53-24-2','frb53-24-2','/frb53-24-mis/frb53-24-2.mis',1272,94289,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (28,2,'frb53-24-3','frb53-24-3','/frb53-24-mis/frb53-24-3.mis',1272,94127,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (29,2,'frb53-24-4','frb53-24-4','/frb53-24-mis/frb53-24-4.mis',1272,94308,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (30,2,'frb53-24-5','frb53-24-5','/frb53-24-mis/frb53-24-5.mis',1272,94226,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (31,2,'frb56-25-1','frb56-25-1','/frb56-25-mis/frb56-25-1.mis',1400,109676,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (32,2,'frb56-25-2','frb56-25-2','/frb56-25-mis/frb56-25-2.mis',1400,109401,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (33,2,'frb56-25-3','frb56-25-3','/frb56-25-mis/frb56-25-3.mis',1400,109379,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (34,2,'frb56-25-4','frb56-25-4','/frb56-25-mis/frb56-25-4.mis',1400,110038,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (35,2,'frb56-25-5','frb56-25-5','/frb56-25-mis/frb56-25-5.mis',1400,109601,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (36,2,'frb59-26-1','frb59-26-1','/frb59-26-mis/frb59-26-1.mis',1534,126555,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (37,2,'frb59-26-2','frb59-26-2','/frb59-26-mis/frb59-26-2.mis',1534,126163,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (38,2,'frb59-26-3','frb59-26-3','/frb59-26-mis/frb59-26-3.mis',1534,126082,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (39,2,'frb59-26-4','frb59-26-4','/frb59-26-mis/frb59-26-4.mis',1534,127011,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (40,2,'frb59-26-5','frb59-26-5','/frb59-26-mis/frb59-26-5.mis',1534,125982,1);

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,3,'Zebra','Zebra','/000027_zebra.konect',27,111,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,3,'Zachary','Zachary','/000034_zachary.konect',34,78,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,3,'Dolphins','Dolphins','/000062_dolphins.konect',62,159,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,3,'DavidCopperfield','David Copperfield','/000112_David_Copperfield.konect',112,425,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,3,'Jazz','Jazz musicians','/000198_Jazz_musicians.konect',198,2742,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,3,'PDZBase','PDZBase','/000212_pdzbase.konect',212,244,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,3,'Rovira','U.Rovira i Virgili','/001133_rovira.konect',1133,5451,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,3,'Euroroad','Euroroad','/001174_euroroad.konect',1174,1417,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,3,'Hamster','Hamsterster','/001858_hamster.konect',1858,12534,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,3,'HamsterFul','Hamsterster Full','/002426_hamster_ful.konect',2426,16631,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,3,'Facebook','Facebook(NIPS)','/002888_facebook.konect',2888,2981,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,3,'HumanProtein','Human Protein','/003133_human_protein_Vidal.konect',3133,6726,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,3,'Powergrid','Powergrid','/004941_powergrid.konect',4941,6594,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,3,'Reactome','Reactome','/006327_reactome.konect',6327,147547,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,3,'RouteViews','Route Views','/006474_Route_views.konect',6474,13895,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,3,'PrettyGoodPrivacy','Pretty Good Privacy','/010680_Pretty_Good_Privacy.konect',10680,24316,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,3,'arXiv','arXiv','/018771_arXiv.konect',18771,198050,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (18,3,'CAIDA','CAIDA','/026475_caida.konect',26475,53381,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (19,3,'Brightkite','Brightkite','/058228_brightkite_edges.konect',58228,214078,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (20,3,'FacebookFriendships','Facebook friendships','/063731_facebook_wosn_links.konect',63731, 817035 ,1);
 

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,4,'brock200_2','brock200_2','/brock200_2.mis',200,10024,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,4,'brock200_4','brock200_4','/brock200_4.mis',200,6811,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,4,'brock400_2','brock400_2','/brock400_2.mis',400,20014,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,4,'brock400_4','brock400_4','/brock400_4.mis',400,20035,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,4,'brock800_2','brock800_2','/brock800_2.mis',800,111434,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,4,'brock800_4','brock800_4','/brock800_4.mis',800,111957,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,4,'C1000.9','C1000.9','/C1000.9.mis',1000,49421,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,4,'C125.9','C125.9','/C125.9.mis',125,787,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,4,'C2000.5','C2000.5','/C2000.5.mis',2000,999164,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,4,'C2000.9','C2000.9','/C2000.9.mis',2000,199468,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,4,'C250.9','C250.9','/C250.9.mis',250,3141,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,4,'C4000.5','C4000.5','/C4000.5.mis',4000,3997732,0); 
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,4,'C500.9','C500.9','/C500.9.mis',500,12418,0); 
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,4,'DSJC1000.5','DSJC1000.5','/DSJC1000.5.mis',1000,249674,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,4,'DSJC500.5','DSJC500.5','/DSJC500.5.mis',500,62126 ,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,4,'gen200_p0.9_44','gen200_p0.9_44','/gen200_p0.9_44.mis',200, 1990,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,4,'gen200_p0.9_55','gen200_p0.9_55','/gen200_p0.9_55.mis',200, 1990,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (18,4,'gen400_p0.9_55','gen400_p0.9_55','/gen400_p0.9_55.mis',400, 7980,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (19,4,'gen400_p0.9_65','gen400_p0.9_65','/gen400_p0.9_65.mis',400, 7980 ,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (20,4,'gen400_p0.9_75','gen400_p0.9_75','/gen400_p0.9_75.mis',400, 7980,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (21,4,'hamming10-4','hamming10-4','/hamming10-4.mis',1024, 89600,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (22,4,'hamming8-4','hamming8-4','/hamming8-4.mis',256, 11776,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (23,4,'keller4','keller4','/keller4.mis',171, 5100,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (24,4,'keller5','keller5','/keller5.mis',776, 74710,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (25,4,'keller6','keller6','/keller6.mis',3361, 1026582,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (26,4,'MANN_a27','MANN_a27','/MANN_a27.mis',378,702,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (27,4,'MANN_a45','MANN_a45','/MANN_a45.mis',1035, 1980,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (28,4,'MANN_a81','MANN_a81','/MANN_a81.mis',3321 ,6480,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (29,4,'p_hat1500-1','p_hat1500-1','/p_hat1500-1.mis',1500, 839327,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (30,4,'p_hat1500-2','p_hat1500-2','/p_hat1500-2.mis',1500 ,555290,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (31,4,'p_hat1500-3','p_hat1500-3','/p_hat1500-3.mis',1500, 277006,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (32,4,'p_hat300-1','p_hat300-1','/p_hat300-1.mis',300, 33917,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (33,4,'p_hat300-2','p_hat300-2','/p_hat300-2.mis',300 ,22922,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (34,4,'p_hat300-3','p_hat300-3','/p_hat300-3.mis',300, 11460,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (35,4,'p_hat700-1','p_hat700-1','/p_hat700-1.mis',700, 183651,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (36,4,'p_hat700-2','p_hat700-2','/p_hat700-2.mis',700, 122922,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (37,4,'p_hat700-3','p_hat700-3','/p_hat700-3.mis',700, 61640,0);	


INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,5,'bio-celegans','bio-celegans','/bio/bio-celegans',453,2025,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,5,'bio-diseasome','bio-diseasome','/bio/bio-diseasome',516, 1188,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,5,'bio-dmela','bio-dmela','/bio/bio-dmela',7393 ,25569,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,5,'bio-yeast','bio-yeast','/bio/bio-yeast',1458, 1948,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,5,'ca-AstroPh','ca-AstroPh','/col/ca-AstroPh',17903,196972,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,5,'ca-citeseer','ca-citeseer','/col/ca-citeseer',227320,814134,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,5,'ca-coauthors-dblp','ca-coauthors-dblp','/col/ca-coauthors-dblp',540486,15245729,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,5,'ca-CondMat','ca-CondMat','/col/ca-CondMat',21363, 91286,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,5,'ca-CSphd','ca-CSphd','/col/ca-CSphd',1882,1740,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,5,'ca-dblp-2010','ca-dblp-2010','/col/ca-dblp-2010',226413, 716460,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,5,'ca-dblp-2012','ca-dblp-2012','/col/ca-dblp-2012',317080, 1049866,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,5,'ca-Erdos992','ca-Erdos992','/col/ca-Erdos992',6100,7515,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,5,'ca-GrQc','ca-GrQc','/col/ca-GrQc',4158, 13422,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,5,'ca-HepPh','ca-HepPh','/col/ca-HepPh',11204, 117619,1);
--INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,5,'ca-hollywood-2009','ca-hollywood-2009','/col/ca-hollywood-2009',1458, 1948,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,5,'ca-MathSciNet','ca-MathSciNet','/col/ca-MathSciNet',332689, 820644,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,5,'ca-netscience','ca-netscience','/col/ca-netscience',379, 914,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (18,5,"inf-power","inf-power","/inf/inf-power",4941,6594,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (19,5,"inf-road-usa","inf-road-usa","/inf/inf-road-usa",23947347,28854312,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (20,5,"inf-roadNet-CA","inf-roadNet-CA","/inf/inf-roadNet-CA",1957027,2760388,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (21,5,"inf-roadNet-PA","inf-roadNet-PA","/inf/inf-roadNet-PA",1087562,1541514,0);

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (22,5,"socfb-A-anon","socfb-A-anon","/fb/socfb-A-anon",3097165,23667394,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (23,5,"socfb-B-anon","socfb-B-anon","/fb/socfb-B-anon",2937612,20959854,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (24,5,"socfb-Berkeley13","socfb-Berkeley13","/fb/socfb-Berkeley13",22900,852419,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (25,5,"socfb-CMU","socfb-CMU","/fb/socfb-CMU",6621,249959,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (26,5,"socfb-Duke14","socfb-Duke14","/fb/socfb-Duke14",9885,506437,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (27,5,"socfb-Indiana","socfb-Indiana","/fb/socfb-Indiana",29732,1305757,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (28,5,"socfb-MIT","socfb-MIT","/fb/socfb-MIT",6402,251230,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (29,5,"socfb-OR","socfb-OR","/fb/socfb-OR",63392,816886,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (30,5,"socfb-Penn94","socfb-Penn94","/fb/socfb-Penn94",41536,1362220,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (31,5,"socfb-Stanford3","socfb-Stanford3","/fb/socfb-Stanford3",11586,568309,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (32,5,"socfb-Texas84","socfb-Texas84","/fb/socfb-Texas84",36364,1590651,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (33,5,"socfb-uci-uni","socfb-uci-uni","/fb/socfb-uci-uni",58790782,92208195,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (34,5,"socfb-UCLA","socfb-UCLA","/fb/socfb-UCLA",20453,747604,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (35,5,"socfb-UConn","socfb-UConn","/fb/socfb-UConn",17206,604867,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (36,5,"socfb-UCSB37","socfb-UCSB37","/fb/socfb-UCSB37",14917,482215,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (37,5,"socfb-UF","socfb-UF","/fb/socfb-UF",35111,1465654,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (38,5,"socfb-UIllinois","socfb-UIllinois","/fb/socfb-UIllinois",30795,1264421,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (39,5,"socfb-Wisconsin87","socfb-Wisconsin87","/fb/socfb-Wisconsin87",23831,835946,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (40,5,"ia-email-EU","ia-email-EU","/int/ia-email-EU",32430,54397,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (41,5,"ia-email-univ","ia-email-univ","/int/ia-email-univ",1133,5451,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (42,5,"ia-enron-large","ia-enron-large","/int/ia-enron-large",33696,180811,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (43,5,"ia-enron-only","ia-enron-only","/int/ia-enron-only",143,623,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (44,5,"ia-fb-messages","ia-fb-messages","/int/ia-fb-messages",1266,6451,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (45,5,"ia-infect-dublin","ia-infect-dublin","/int/ia-infect-dublin",410,2765,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (46,5,"ia-infect-hyper","ia-infect-hyper","/int/ia-infect-hyper",113,2196,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (47,5,"ia-reality","ia-reality","/int/ia-reality",6809,7680,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (48,5,"ia-wiki-Talk","ia-wiki-Talk","/int/ia-wiki-Talk",92117,360767,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (49,5,"ca-AstroPh","ca-AstroPh","/col/ca-AstroPh",17903,196972,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (50,5,"ca-citeseer","ca-citeseer","/col/ca-citeseer",227320,814134,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (51,5,"ca-coauthors-dblp","ca-coauthors-dblp","/col/ca-coauthors-dblp",540486,15245729,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (52,5,"ca-CondMat","ca-CondMat","/col/ca-CondMat",21363,91286,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (53,5,"ca-CSphd","ca-CSphd","/col/ca-CSphd",1882,1740,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (54,5,"ca-dblp-2010","ca-dblp-2010","/col/ca-dblp-2010",226413,716460,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (55,5,"ca-dblp-2012","ca-dblp-2012","/col/ca-dblp-2012",317080,1049866,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (56,5,"ca-Erdos992","ca-Erdos992","/col/ca-Erdos992",6100,7515,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (57,5,"ca-GrQc","ca-GrQc","/col/ca-GrQc",4158,13422,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (58,5,"ca-HepPh","ca-HepPh","/col/ca-HepPh",11204,117619,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (59,5,"ca-hollywood-2009","ca-hollywood-2009","/col/ca-hollywood-2009",1069126,56306653,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (60,5,"ca-MathSciNet","ca-MathSciNet","/col/ca-MathSciNet",332689,820644,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (61,5,"ca-netscience","ca-netscience","/col/ca-netscience",379,914,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (62,5,"rec-amazon","rec-amazon","/rec/rec-amazon",91813,125704,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (63,5,"rt-retweet","rt-retweet","/ret/rt-retweet",96,117,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (64,5,"rt-retweet-crawl","rt-retweet-crawl","/ret/rt-retweet-crawl",1112702,2278852,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (65,5,"rt-twitter-copen","rt-twitter-copen","/ret/rt-twitter-copen",761,1029,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (66,5,"sc-ldoor","sc-ldoor","/sci/sc-ldoor",952203,20770807,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (67,5,"sc-msdoor","sc-msdoor","/sci/sc-msdoor",415863,9378650,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (68,5,"sc-nasasrb","sc-nasasrb","/sci/sc-nasasrb",54870,1311227,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (69,5,"sc-pkustk11","sc-pkustk11","/sci/sc-pkustk11",87804,2565054,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (70,5,"sc-pkustk13","sc-pkustk13","/sci/sc-pkustk13",94893,3260967,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (71,5,"sc-pwtk","sc-pwtk","/sci/sc-pwtk",217891,5653221,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (72,5,"sc-shipsec1","sc-shipsec1","/sci/sc-shipsec1",140385,1707759,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (73,5,"sc-shipsec5","sc-shipsec5","/sci/sc-shipsec5",179104,2200076,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (74,5,"soc-BlogCatalog","soc-BlogCatalog","/soc/soc-BlogCatalog",88784,2093195,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (75,5,"soc-brightkite","soc-brightkite","/soc/soc-brightkite",56739,212945,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (76,5,"soc-buzznet","soc-buzznet","/soc/soc-buzznet",101163,2763066,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (77,5,"soc-delicious","soc-delicious","/soc/soc-delicious",536108,1365961,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (78,5,"soc-digg","soc-digg","/soc/soc-digg",770799,5907132,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (79,5,"soc-dolphins","soc-dolphins","/soc/soc-dolphins",62,159,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (80,5,"soc-douban","soc-douban","/soc/soc-douban",154908,327162,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (81,5,"soc-epinions","soc-epinions","/soc/soc-epinions",26588,100120,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (82,5,"soc-flickr","soc-flickr","/soc/soc-flickr",513969,3190452,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (83,5,"soc-flixster","soc-flixster","/soc/soc-flixster",2523386,7918801,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (84,5,"soc-FourSquare","soc-FourSquare","/soc/soc-FourSquare",639014,3214986,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (85,5,"soc-gowalla","soc-gowalla","/soc/soc-gowalla",196591,950327,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (86,5,"soc-karate","soc-karate","/soc/soc-karate",34,78,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (87,5,"soc-lastfm","soc-lastfm","/soc/soc-lastfm",1191805,4519330,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (88,5,"soc-livejournal","soc-livejournal","/soc/soc-livejournal",4033137,27933062,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (89,5,"soc-LiveMocha","soc-LiveMocha","/soc/soc-LiveMocha",104103,2193083,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (90,5,"soc-orkut","soc-orkut","/soc/soc-orkut",2997166,106349209,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (91,5,"soc-pokec","soc-pokec","/soc/soc-pokec",1632803,22301964,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (92,5,"soc-slashdot","soc-slashdot","/soc/soc-slashdot",70068,358647,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (93,5,"soc-twitter-follows","soc-twitter-follows","/soc/soc-twitter-follows",404719,713319,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (94,5,"soc-ube","soc-ube","/soc/soc-ube",495957,1936748,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (95,5,"soc-ube-snap","soc-ube-snap","/soc/soc-ube-snap",1134890,2987624,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (96,5,"soc-wiki-Vote","soc-wiki-Vote","/soc/soc-wiki-Vote",889,2914,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (97,5,"tech-as-caida2007","tech-as-caida2007","/tec/tech-as-caida2007",26475,53381,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (98,5,"tech-as-skitter","tech-as-skitter","/tec/tech-as-skitter",1694616,11094209,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (99,5,"tech-ers-rf","tech-ers-rf","/tec/tech-ers-rf",2113,6632,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (100,5,"tech-internet-as","tech-internet-as","/tec/tech-internet-as",40164,85123,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (101,5,"tech-p2p-gnutella","tech-p2p-gnutella","/tec/tech-p2p-gnutella",62561,147878,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (102,5,"tech-RL-caida","tech-RL-caida","/tec/tech-RL-caida",190914,607610,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (103,5,"tech-WHOIS","tech-WHOIS","/tec/tech-WHOIS",7476,56943,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (104,5,"scc_enron-only","scc_enron-only","/tem/scc_enron-only",151,9828,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (105,5,"scc_fb-forum","scc_fb-forum","/tem/scc_fb-forum",897,71011,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (106,5,"scc_fb-messages","scc_fb-messages","/tem/scc_fb-messages",1899,531893,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (107,5,"scc_infect-dublin","scc_infect-dublin","/tem/scc_infect-dublin",10972,175573,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (108,5,"scc_infect-hyper","scc_infect-hyper","/tem/scc_infect-hyper",113,6222,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (109,5,"scc_reality","scc_reality","/tem/scc_reality",6809,4714485,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (110,5,"scc_retweet","scc_retweet","/tem/scc_retweet",18469,65990,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (111,5,"scc_retweet-crawl","scc_retweet-crawl","/tem/scc_retweet-crawl",1131801,24015,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (112,5,"scc_rt_alwefaq","scc_rt_alwefaq","/tem/scc_rt_alwefaq",4157,355,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (113,5,"scc_rt_assad","scc_rt_assad","/tem/scc_rt_assad",2035,96,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (114,5,"scc_rt_bahrain","scc_rt_bahrain","/tem/scc_rt_bahrain",4659,129,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (115,5,"scc_rt_barackobama","scc_rt_barackobama","/tem/scc_rt_barackobama",9551,226,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (116,5,"scc_rt_damascus","scc_rt_damascus","/tem/scc_rt_damascus",2962,41,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (117,5,"scc_rt_dash","scc_rt_dash","/tem/scc_rt_dash",5968,39,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (118,5,"scc_rt_gmanews","scc_rt_gmanews","/tem/scc_rt_gmanews",8330,1078,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (119,5,"scc_rt_gop","scc_rt_gop","/tem/scc_rt_gop",3716,7,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (120,5,"scc_rt_http","scc_rt_http","/tem/scc_rt_http",5691,6,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (121,5,"scc_rt_israel","scc_rt_israel","/tem/scc_rt_israel",3686,12,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (122,5,"scc_rt_justinbieber","scc_rt_justinbieber","/tem/scc_rt_justinbieber",9364,442,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (123,5,"scc_rt_ksa","scc_rt_ksa","/tem/scc_rt_ksa",5775,23,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (124,5,"scc_rt_lebanon","scc_rt_lebanon","/tem/scc_rt_lebanon",3370,5,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (125,5,"scc_rt_libya","scc_rt_libya","/tem/scc_rt_libya",5021,26,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (126,5,"scc_rt_lolgop","scc_rt_lolgop","/tem/scc_rt_lolgop",9742,4510,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (127,5,"scc_rt_mittromney","scc_rt_mittromney","/tem/scc_rt_mittromney",7850,108,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (128,5,"scc_rt_obama","scc_rt_obama","/tem/scc_rt_obama",3040,4,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (129,5,"scc_rt_occupy","scc_rt_occupy","/tem/scc_rt_occupy",3090,60,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (130,5,"scc_rt_occupywallstnyc","scc_rt_occupywallstnyc","/tem/scc_rt_occupywallstnyc",3594,931,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (131,5,"scc_rt_oman","scc_rt_oman","/tem/scc_rt_oman",4452,13,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (132,5,"scc_rt_onedirection","scc_rt_onedirection","/tem/scc_rt_onedirection",7704,368,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (133,5,"scc_rt_p2","scc_rt_p2","/tem/scc_rt_p2",4785,15,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (134,5,"scc_rt_qatif","scc_rt_qatif","/tem/scc_rt_qatif",6718,11,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (135,5,"scc_rt_saudi","scc_rt_saudi","/tem/scc_rt_saudi",6805,91,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (136,5,"scc_rt_tcot","scc_rt_tcot","/tem/scc_rt_tcot",4506,18,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (137,5,"scc_rt_tlot","scc_rt_tlot","/tem/scc_rt_tlot",3513,8,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (138,5,"scc_rt_uae","scc_rt_uae","/tem/scc_rt_uae",4757,12,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (139,5,"scc_rt_voteonedirection","scc_rt_voteonedirection","/tem/scc_rt_voteonedirection",1833,5,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (140,5,"scc_twitter-copen","scc_twitter-copen","/tem/scc_twitter-copen",8580,473614,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (141,5,"web-arabic-2005","web-arabic-2005","/web/web-arabic-2005",163598,1747269,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (142,5,"web-BerkStan","web-BerkStan","/web/web-BerkStan",12305,19500,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (143,5,"web-edu","web-edu","/web/web-edu",3031,6474,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (144,5,"web-google","web-google","/web/web-google",1299,2773,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (145,5,"web-indochina-2004","web-indochina-2004","/web/web-indochina-2004",11358,47606,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (146,5,"web-it-2004","web-it-2004","/web/web-it-2004",509338,7178413,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (147,5,"web-polblogs","web-polblogs","/web/web-polblogs",643,2280,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (148,5,"web-sk-2005","web-sk-2005","/web/web-sk-2005",121422,334419,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (149,5,"web-spam","web-spam","/web/web-spam",4767,37375,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (150,5,"web-uk-2005","web-uk-2005","/web/web-uk-2005",129632,11744049,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (151,5,"web-webbase-2001","web-webbase-2001","/web/web-webbase-2001",16062,25593,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (152,5,"web-wikipedia2009","web-wikipedia2009","/web/web-wikipedia2009",1864433,4507315,0);




drop view if exists "v_instance";
create view "v_instance" as
select i.d_id||'_'||case when i.i_id>=100 then i.i_id when i.i_id>=10 then '0'||i.i_id else '00'||i.i_id end as i_id, i.i_code,i.i_name, i.d_id, d.d_name, d.d_path, i.path_name, i.v_count, i.e_count, i.to_be_tested
from "instance" i left outer join "dataset" d on i.d_id=d.id;


/*
drop table IF EXISTS "instance_ds_opt";

CREATE TABLE "instance_ds_opt" (
	i_id	varchar2(10),
	best_result_size	INTEGER,
    accepted_result_size INTEGER,
    unaccepted_result_size INTEGER,
    allowed_running_time INTEGER,
	FOREIGN KEY(i_id) REFERENCES "v_instance"(i_id)
);
 
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_1',12,12,14,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_2',12,12,14,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_3',12,12,14,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_4',12,12,14,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_5',12,12,14,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_6',14,14,16,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_7',14,14,16,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_8',15,15,17,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_9',17,17,19,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_10',14,14,16,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_11',16,16,18,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_12',17,17,19,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_13',17,17,19,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_14',17,17,19,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_15',16,16,18,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_16',18,18,20,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_17',19,19,21,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_18',18,18,20,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_19',18,18,20,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('2_20',19,19,21,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_1',18,20,22,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_2',13,15,17,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_3',52,52,55,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_4',211,211,214,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_5',384,384,385,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time)
VALUES ('3_6',241,241,243,0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_7',416,416,419,  0);
INSERT INTO "instance_ds_opt" (i_id,best_result_size,accepted_result_size,unaccepted_result_size,allowed_running_time) VALUES ('3_8',10,10,12, 0);
*/


/*
drop view if exists "v_instance_opt";
create view "v_instance_opt" as
select i.i_id, i.i_code, i.i_name, i.d_id, i.d_name, i.d_path, i.path_name, i.v_count, i.e_count, ido.best_result_size,ido.accepted_result_size,ido.unaccepted_result_size, ido.allowed_running_time 
from "v_instance" i left outer join "instance_ds_opt" ido on i.i_id=ido.i_id;
*/

/*
drop table IF EXISTS "alg1running";
CREATE TABLE "alg1running" (
	id	INTEGER PRIMARY KEY AUTOINCREMENT,
	batch_num	INTEGER,
	i_id	varchar2(10),
	model varchar2(5),
	k INTEGER,
	result_size	INTEGER,
	running_nano_sec	INTEGER,
	FOREIGN KEY(i_id) REFERENCES "v_instance"(i_id)
);
*/
 