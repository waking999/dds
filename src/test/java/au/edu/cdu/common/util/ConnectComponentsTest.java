package au.edu.cdu.common.util;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import au.edu.cdu.TestUtil;
import au.edu.cdu.common.io.FileOperation;


public class ConnectComponentsTest {
	@Test
	public void testGetConnectComponents() throws IOException{
		String filePath = TestUtil.getBasePath() + "/src/test/resources/sample2.txt";
		GlobalVariable g = FileOperation.readGraphByEdgePair(filePath);
		
		ConnectComponents cc=new ConnectComponents();
		cc.setG(g);
		List<Set<Integer>> list=cc.getConnectComponents();  
		int listSize=list.size();
		Assert.assertEquals(2, listSize);
	 
	}

}
