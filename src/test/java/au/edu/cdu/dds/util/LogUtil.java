package au.edu.cdu.dds.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * an util for log
 * 
 * 
 */
public class LogUtil {

	/**
	 * get log according to class
	 * 
	 * @param clazz
	 *            , class
	 * @return logger
	 */
	public static Logger getLogger(@SuppressWarnings("rawtypes") Class clazz) {
		Logger log = LogManager.getLogger(clazz);
		log = setLog(log);
		return log;
	}

	/**
	 * set log
	 * 
	 * @param log
	 */
	private static Logger setLog(Logger log) {

		return log;
	}

	/**
	 * get root logger
	 * 
	 * @return logger
	 */
	public static Logger getLogger() {
		Logger log = LogManager.getRootLogger();
		log = setLog(log);
		return log;
	}

}
