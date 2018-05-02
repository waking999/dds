package au.edu.cdu.common.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * an util for log
 */
public class LogUtil {

	/**
	 * get log according to class
	 * 
	 * @param className, class name
	 * @return logger
	 */
	public static Logger getLogger(String className) {
		return LogManager.getLogger(className);
	}

}
