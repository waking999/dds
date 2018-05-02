 

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
 
 




select r.i_id,i.i_name, min(r.result_size) as min_result_size
from result_GreedyVoteL2HDDSCompTest r, v_instance i
where r.i_id=i.i_id
and i.d_name='BHOSLIB'
group by r.i_id, i.i_name;