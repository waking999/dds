
drop table IF EXISTS "dataset";
CREATE TABLE "dataset" (
	id	INTEGER PRIMARY KEY ,
	d_name	varchar2(30),
	d_path	varchar2(30)
);
INSERT INTO "dataset" (id,d_name,d_path) VALUES (1,'DIMACS','/DIMACS');
INSERT INTO "dataset" (id,d_name,d_path) VALUES (2,'BHOSLIB','/BHOSLIB');
INSERT INTO "dataset" (id,d_name,d_path) VALUES (3,'KONECT','/KONECT');

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
 

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,2,'frb30_15_1','frb30-15-1','/frb30-15-mis/frb30-15-1.mis',450,17827,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,2,'frb30_15_2','frb30-15-2','/frb30-15-mis/frb30-15-2.mis',450,17874,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,2,'frb30_15_3','frb30-15-3','/frb30-15-mis/frb30-15-3.mis',450,17809,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,2,'frb30_15_4','frb30-15-4','/frb30-15-mis/frb30-15-4.mis',450,17831,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,2,'frb30_15_5','frb30-15-5','/frb30-15-mis/frb30-15-5.mis',450,17794,1);

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,2,'frb35_17_1','frb35-17-1','/frb35-17-mis/frb35-17-1.mis',595,27856,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,2,'frb35_17_2','frb35-17-2','/frb35-17-mis/frb35-17-2.mis',595,27847,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,2,'frb35_17_3','frb35-17-3','/frb35-17-mis/frb35-17-3.mis',595,27931,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,2,'frb35_17_4','frb35-17-4','/frb35-17-mis/frb35-17-4.mis',595,27842,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,2,'frb35_17_5','frb35-17-5','/frb35-17-mis/frb35-17-5.mis',595,28143,1);

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,2,'frb40_19_1','frb40-19-1','/frb40-19-mis/frb40-19-1.mis',760,41314,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,2,'frb40_19_2','frb40-19-2','/frb40-19-mis/frb40-19-2.mis',760,41263,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,2,'frb40_19_3','frb40-19-3','/frb40-19-mis/frb40-19-3.mis',760,41095,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,2,'frb40_19_4','frb40-19-4','/frb40-19-mis/frb40-19-4.mis',760,41605,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,2,'frb40_19_5','frb40-19-5','/frb40-19-mis/frb40-19-5.mis',760,41619,1);

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,2,'frb45_21_1','frb45-21-1','/frb45-21-mis/frb45-21-1.mis',945,59186,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,2,'frb45_21_2','frb45-21-2','/frb45-21-mis/frb45-21-2.mis',945,58624,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (18,2,'frb45_21_3','frb45-21-3','/frb45-21-mis/frb45-21-3.mis',945,58245,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (19,2,'frb45_21_4','frb45-21-4','/frb45-21-mis/frb45-21-4.mis',945,58549,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (20,2,'frb45_21_5','frb45-21-5','/frb45-21-mis/frb45-21-5.mis',945,58579,1);

INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (21,2,'frb53_24_1','frb53-24-1','/frb53-24-mis/frb53-24-1.mis',1272,94227,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (22,2,'frb53_24_2','frb53-24-2','/frb53-24-mis/frb53-24-2.mis',1272,94289,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (23,2,'frb53_24_3','frb53-24-3','/frb53-24-mis/frb53-24-3.mis',1272,94127,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (24,2,'frb53_24_4','frb53-24-4','/frb53-24-mis/frb53-24-4.mis',1272,94308,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (25,2,'frb53_24_5','frb53-24-5','/frb53-24-mis/frb53-24-5.mis',1272,94226,1);


INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (1,3,'Zebra','Zebra','/000027_zebra.konect',27,111,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (2,3,'Zachary','Zachary','/000034_zachary.konect',34,78,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (3,3,'Dolphins','Dolphins','/000062_dolphins.konect',62,159,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (4,3,'DavidCopperfield','David Copperfield','/000112_David_Copperfield.konect',112,425,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (5,3,'Jazz','Jazz musicians','/000198_Jazz_musicians.konect',198,2742,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (6,3,'PDZBase','PDZBase','/000212_pdzbase.konect',212,244,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (7,3,'Rovira','U.Rovira i Virgili','/001133_rovira.konect',1133,5451,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (8,3,'Euroroad','Euroroad','/001174_euroroad.konect',1174,1417,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (9,3,'Hamster','Hamsterster','/001858_hamster.konect',1858,12534,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (10,3,'HamsterFul','Hamsterster Full','/002426_hamster_ful.konect',2426,16631,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (11,3,'Facebook','Facebook(NIPS)','/002888_facebook.konect',2888,2981,0);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (12,3,'HumanProtein','Human Protein','/003133_human_protein_Vidal.konect',3133,6726,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (13,3,'Powergrid','Powergrid','/004941_powergrid.konect',4941,6594,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (14,3,'Reactome','Reactome','/006327_reactome.konect',6327,147547,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (15,3,'RouteViews','Route Views','/006474_Route_views.konect',6474,13895,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (16,3,'PrettyGoodPrivacy','Pretty Good Privacy','/010680_Pretty_Good_Privacy.konect',10680,24316,1);
INSERT INTO "instance" (i_id,d_id,i_code,i_name,path_name,v_count,e_count,to_be_tested) VALUES  (17,3,'arXiv','arXiv','/018771_arXiv.konect',18771,198050,1);

drop view if exists "v_instance";
create view "v_instance" as
select i.d_id||'_'||case when i.i_id>=10 then i.i_id else '0'||i.i_id end as i_id, i.i_code,i.i_name, i.d_id, d.d_name, d.d_path, i.path_name, i.v_count, i.e_count, i.to_be_tested
from "instance" i left outer join "dataset" d on i.d_id=d.id;



drop table IF EXISTS "instance_ds_opt";
/*
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
drop view if exists "v_instance_opt";

/*
create view "v_instance_opt" as
select i.i_id, i.i_code, i.i_name, i.d_id, i.d_name, i.d_path, i.path_name, i.v_count, i.e_count, ido.best_result_size,ido.accepted_result_size,ido.unaccepted_result_size, ido.allowed_running_time 
from "v_instance" i left outer join "instance_ds_opt" ido on i.i_id=ido.i_id;
*/


drop table IF EXISTS "alg1running";
/*
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