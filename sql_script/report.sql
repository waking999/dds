 

select a1.i_id,   i.i_name, i.v_count,i.e_count, a1.result_size as a1_result_size,a1.running_nano_sec as a1_running_nano_sec, a2.result_size as a2_result_size, a2.running_nano_sec as a2_running_nano_sec
from result_GreedyVoteH2LTest  a1 left outer join result_GreedyVoteL2HTest a2 on a1.i_id=a2.i_id,
v_instance i
where a1.i_id=i.i_id
and i.d_name='KONECT'
and a1.batch_num='20171107-2110'
and a2.batch_num='20171107-2111';


--algorithm result compare
select a1.i_id, i.i_name, a1.result_size as a1_result_size,  a2.result_size as a2_result_size, a3.result_size as a3_result_size, a4.result_size as a4_result_size
from result_GreedyVoteH2LTest  a1 left outer join result_GreedyVoteL2HTest a2 on a1.i_id=a2.i_id left outer join result_GreedyVoteL2HAddTest a3 on a1.i_id=a3.i_id 
left outer join result_GreedyVoteL2HDDSTest a4 on a1.i_id=a4.i_id,
v_instance i
where a1.i_id=i.i_id
and i.d_name='KONECT'
and a1.batch_num='20171107-2110'
and a2.batch_num='20171107-2111'
and a3.batch_num='20171107-2121'
and a4.batch_num='20171108-0853'
and a4.k=10
and a4.r=7;

--show different k,r,result size
select i_id,k,r, a.result_size
from result_GreedyVoteL2HDDSTest a
where a.batch_num='20171108-0845';

--filter duplicated result size
select distinct a.result_size
from result_GreedyVoteL2HDDSTest a
where a.batch_num='20171107-2223';

select r.i_id, r.result_size, r.results
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='DIMACS-MIS'
and i.i_name='gen400_p0.9_75';


select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='BHOSLIB' 
group by r.i_id, i.i_name
;


with min_result as (
select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='BHOSLIB' 
group by r.i_id, i.i_name
)
, all_result as (
select r.id,r.i_id,mr.i_name,r.k,r.r, r.result_size, r.results 
from result_GreedyVoteL2HDDSCompTest r,min_result mr
where r.i_id=mr.i_id
and r.result_size=mr.min_result_size
)
, only_result as(
select i_id,result_size,min(id) as id 
from all_result
group by i_id, result_size
)
select ar.*
from all_result ar,only_result r
where ar.id=r.id
;
 
 

 
with min_result as (
select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='KONECT' 
group by r.i_id, i.i_name
)
, all_result as (
select r.id,r.i_id,mr.i_name,r.k,r.r, r.result_size, r.results, printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDSCompTest r,min_result mr
where r.i_id=mr.i_id
and r.result_size=mr.min_result_size
and r.batch_num='20180515-1146'
)
, only_result as(
select i_id,result_size,min(id) as id 
from all_result
group by i_id, result_size
)
select ar.*
from all_result ar,only_result r, v_instance vi
where ar.id=r.id
;



with min_result as (
select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='DIMACS-MIS' 
group by r.i_id, i.i_name
)
, all_result as (
select r.id,r.i_id,mr.i_name,r.k,r.r, r.result_size, r.results, printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDSCompTest r,min_result mr
where r.i_id=mr.i_id
and r.result_size=mr.min_result_size
and r.batch_num='20180519-1806'
)
, only_result as(
select i_id,result_size,min(id) as id 
from all_result
group by i_id, result_size
)
select  ar.*
from all_result ar,only_result r, v_instance vi
where ar.id=r.id
and ar.i_id=vi.i_id
;



with min_result as (
select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='DIMACS-MIS' 
group by r.i_id, i.i_name
)
, all_result as (
select r.id,r.i_id,mr.i_name,r.k,r.r, r.result_size, r.results, printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDSCompTest r,min_result mr
where r.i_id=mr.i_id
and r.result_size=mr.min_result_size
and r.batch_num='20180519-1806'
)
, only_result as(
select i_id,result_size,min(id) as id 
from all_result
group by i_id, result_size
)
select vi.i_code,v_count,e_count,ar.result_size, ar.running_sec
from all_result ar,only_result r, v_instance vi
where ar.id=r.id
and ar.i_id=vi.i_id
;

with min_result as (
select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='DIMACS-MIS' 
group by r.i_id, i.i_name
)
, all_result as (
select r.id,r.i_id,mr.i_name,r.k,r.r, r.result_size, r.results, printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDSCompTest r,min_result mr
where r.i_id=mr.i_id
and r.result_size=mr.min_result_size
and r.batch_num='20180519-1806'
)
, only_result as(
select i_id,result_size,min(id) as id 
from all_result
group by i_id, result_size
)
, turbodds_result as (
select vi.i_code,v_count,e_count,ar.result_size, ar.running_sec
from all_result ar,only_result r, v_instance vi
where ar.id=r.id
and ar.i_id=vi.i_id
)
select  i_code||"&"||v_count||"&"||e_count||"& & & & & & & &"|| result_size||"&"|| running_sec||"\\" as result0
from turbodds_result tr
;

with hybird_min_result as (
select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='DIMACS-MIS' 
group by r.i_id, i.i_name
)
, all_result as (
select r.id,r.i_id,mr.i_name,r.k,r.r, r.result_size, r.results, printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDSCompTest r,min_result mr
where r.i_id=mr.i_id
and r.result_size=mr.min_result_size
and r.batch_num='20180519-1806'
)
, only_result as(
select i_id,result_size,min(id) as id 
from all_result
group by i_id, result_size
)
, turbodds_result as (
select vi.i_code,v_count,e_count,ar.result_size, ar.running_sec
from all_result ar,only_result r, v_instance vi
where ar.id=r.id
and ar.i_id=vi.i_id
)
select  i_code||"&"||v_count||"&"||e_count||"& & & & & & & &"|| result_size||"&"|| running_sec||"\\" as result0
from turbodds_result tr
;

 
with naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyNaiveTest r 
where r.batch_num='20180521-1941'
)
,vote_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteTest r 
where r.batch_num='20180521-2010'
)
,vote_gr_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteGrTest r 
where r.batch_num='20180521-2114'
)
,dds_naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDS3Test r 
where r.batch_num='20180521-2312'
)
,dds_vote_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDS4Test r 
where r.batch_num='20180521-2319'
)
select vi.i_code,v_count,e_count,nr.result_size as s1, nr.running_sec as t1, vr.result_size as s2, vr.running_sec as t2 
, vgr.result_size as s3, vgr.running_sec as t3 , dnr.result_size as s4, dnr.running_sec as t4 
, dvr.result_size as s5, dvr.running_sec as t5 
from v_instance vi, naive_result nr,vote_result vr,vote_gr_result vgr,dds_naive_result dnr,dds_vote_result dvr
where vi.i_id=nr.i_id
and vi.i_id=vr.i_id
and vi.i_id=vgr.i_id
and vi.i_id=dnr.i_id
and vi.i_id=dvr.i_id
;

--dimacs-mis
with naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyNaiveTest r 
where r.batch_num='20180521-1941'
)
,vote_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteTest r 
where r.batch_num='20180521-2010'
)
,vote_gr_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteGrTest r 
where r.batch_num='20180521-2114'
)
,dds_naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDS3Test r 
where r.batch_num='20180521-2312'
)
,hybrid_star_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDSComp4Test r 
where r.batch_num='20180521-2326'
)
select vi.i_code,v_count,e_count,nr.result_size as chvatal_s , nr.running_sec as chvatal_t , vr.result_size as vote_s , vr.running_sec as vote_t, vgr.result_size as vote_ls_s, vgr.running_sec as vote_ls_t , hsr.result_size as hybrid_star_s, hsr.running_sec as hybrid_star_t  
from v_instance vi, naive_result nr,vote_result vr,vote_gr_result vgr,hybrid_star_result hsr
where vi.i_id=nr.i_id
and vi.i_id=vr.i_id
and vi.i_id=vgr.i_id 
and vi.i_id=hsr.i_id
;

--konect
with naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyNaiveTest r 
where r.batch_num='20180522-1145'
)
,vote_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteTest r 
where r.batch_num='20180522-1147'
)
,vote_gr_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteGrTest r 
where r.batch_num='20180522-1149'
)
,dds_naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDS3Test r 
where r.batch_num='20180522-1151'
) 
select vi.i_code,v_count,e_count
,nr.result_size as chvatal_s , nr.running_sec as chvatal_t 
, vr.result_size as vote_s, vr.running_sec as vote_t
, vgr.result_size as vote_ls_s, vgr.running_sec as vote_ls_t  
, dnr.result_size as hybrid_s, dnr.running_sec as hybrid_t  
from v_instance vi, naive_result nr,vote_result vr,vote_gr_result vgr , dds_naive_result dnr
where vi.i_id=nr.i_id
and vi.i_id=vr.i_id
and vi.i_id=vgr.i_id  
and vi.i_id=dnr.i_id
;


--dimacs-mis
with naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyNaiveTest r 
where r.batch_num='20180522-1146'
)
,vote_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteTest r 
where r.batch_num='20180522-1148'
)
,vote_gr_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteGrTest r 
where r.batch_num='20180522-1150'
)
,dds_naive_result as (
select r.id,r.i_id,   r.result_size,  printf("%.3f",r.running_nano_sec/1000000000.0) as running_sec
from result_GreedyVoteL2HDDS3Test r 
where r.batch_num='20180522-1153'
) 
select vi.i_code,v_count,e_count
,nr.result_size as chvatal_s , nr.running_sec as chvatal_t 
, vr.result_size as vote_s, vr.running_sec as vote_t
, vgr.result_size as vote_ls_s, vgr.running_sec as vote_ls_t  
, dnr.result_size as hybrid_s, dnr.running_sec as hybrid_t  
from v_instance vi, naive_result nr,vote_result vr,vote_gr_result vgr , dds_naive_result dnr
where vi.i_id=nr.i_id
and vi.i_id=vr.i_id
and vi.i_id=vgr.i_id  
and vi.i_id=dnr.i_id
;







select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='BHOSLIB'
group by r.i_id, i.i_name;