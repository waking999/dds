package au.edu.cdu.dds.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConnectComponents {
	private GlobalVariable g;

	private List<Set<Integer>> compoments;

	public ConnectComponents() {
		this.compoments=new ArrayList<>();
	}
	public void setG(GlobalVariable g) {
		this.g = g;
	}

	public List<Set<Integer>> getConnectComponents() {
		int verCnt = g.getVerCnt();
		boolean[] visited = new boolean[verCnt];
		Arrays.fill(visited, false);
		int[] idxLst = g.getIdxLst();

		int[][] idxAL = g.getIdxAL();

		for (int i = 0; i < verCnt; i++) {
			int vIdx = idxLst[i];
			if (!visited[vIdx]) {
				bfs(vIdx, idxAL, verCnt, visited);
			}

		}
		return compoments;
	}
 

	private void bfs(int vIdx, int[][] idxAL, int verCnt, boolean[] visited) {
		Queue q = new Queue(verCnt);
		Set<Integer> component = new HashSet<>();
		q.add(vIdx);
		visited[vIdx] = true;
		while (!q.isEmpty()) {
			int uIdx = q.poll();
			component.add(uIdx);
			int[] uIdxNeigs = idxAL[uIdx];
			for (int wIdx : uIdxNeigs) {
				if (!visited[wIdx]) {
					q.add(wIdx);
					visited[wIdx] = true;
				}
			}
		}
		compoments.add(component);
	}

}
