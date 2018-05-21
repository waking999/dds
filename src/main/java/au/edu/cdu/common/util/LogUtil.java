package au.edu.cdu.common.util;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * a util for log
 *
 * @author Kai Wang
 */
public class LogUtil {


    /**
     * get log according to class
     *
     * @param clazzName , class name
     * @return logger
     */
    public static Logger getLogger(String clazzName) {
        Logger log = Logger.getLogger(clazzName);
        return log;
    }

    /**
     * get root logger
     *
     * @return logger
     */
    public static Logger getLogger() {
        Logger log = Logger.getRootLogger();
        return log;
    }

    public static void printResult(String message, List<Integer> solution) {
        Logger log = getLogger();
        log.debug(message);
        //StringBuffer sb = new StringBuffer("(").append(solution.size()).append(				"):");
//		for (Integer i : solution) {
//			sb.append(i).append(" ");
//		}
        //log.debug(sb);
    }
}
