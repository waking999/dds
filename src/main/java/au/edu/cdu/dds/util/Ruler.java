package au.edu.cdu.dds.util;

/**
 * this is java bean to contains key and ruler for dom-a-vc problem=>set cover
 * 
 * @author kwang1
 *
 */
public class Ruler {
	private String key;
	private boolean[] ruler;

	public Ruler(String key, boolean[] ruler) {
		this.key = key;
		this.ruler = ruler;
	}

	public String getKey() {
		return key;
	}

	public boolean[] getRuler() {
		return ruler;
	}
}
