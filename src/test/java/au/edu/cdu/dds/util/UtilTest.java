package au.edu.cdu.dds.util;

import org.junit.Test;

 

public class UtilTest {
	 
	 
	@Test
	public void testGetBatchNum() {
		 
		String batchNum = Util.getBatchNum();
		System.out.println(batchNum);
	}

}