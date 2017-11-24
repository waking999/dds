package au.edu.cdu.dds;

import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * an util for log
 * 
 * 
 */
public class LogUtil {

	/**X
	 * get log according to class
	 * 
	 * @param clazz
	 *            , class
	 * @return logger
	 */
	public static Logger getLogger(String clazz) {
		Logger log = LogManager.getLogManager().getLogger(clazz); 
		return log;
	}

	 

}
