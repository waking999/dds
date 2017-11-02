select a1.i_id, a1.i_name, a1.result_size as voteh2l_result_size, a1.running_nano_sec as voth2l_running_nano_sec, a2.result_size as votel2hadd_result_size, a2.running_nano_sec as votel2hadd_running_nano_sec
from v_GreedyVoteH2LTest_KONECT a1, v_GreedyVoteL2HAddTest_KONECT a2
where a1.i_id=a2.i_id
;

