 

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
and a4.batch_num='20171107-2130'
and a4.k=10
and a4.r=7;

--show different k,r,result size
select i_id,k,r, a.result_size
from result_GreedyVoteL2HDDSTest a
where a.batch_num='20171107-2223';

--filter duplicated result size
select distinct a.result_size
from result_GreedyVoteL2HDDSTest a
where a.batch_num='20171107-2223';