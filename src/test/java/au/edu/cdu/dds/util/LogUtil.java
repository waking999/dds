package au.edu.cdu.dds.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * an util for log
 */
public class LogUtil {

	/**
	 * X
	 * get log according to class
	 * 
	 * @param clazz
	 *            , class
	 * @return logger
	 */
	public static Logger getLogger(String className) {
		Logger log = LogManager.getLogger(className);

		return log;
	}

}
