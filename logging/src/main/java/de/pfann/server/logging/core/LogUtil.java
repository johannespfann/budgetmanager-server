package de.pfann.server.logging.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	/*
	 * error() methods
	 */

	public static void error(Class<?> aClass, String aString) {
		//String logMessage = new StringBuilder("[ERROR] ").append(aClass.getSimpleName()).append(" - ").append(aString);
		getLogger(aClass).error(aString);
	}
	public static void error(Class<?> aClass, String aString, Object... aArgs) {
		getLogger(aClass).error(aString, aArgs);
	}

	/*
	 * warn() methods
	 */

	public static void warn(Class<?> aClass, String aString) {
		getLogger(aClass).warn(aString);
	}
	public static void warn(Class<?> aClass, String aString, Object... aArgs) {
		getLogger(aClass).warn(aString, aArgs);
	}

	/*
	 * info() methods
	 */

	public static void info(Class<?> aClass, String aString) {
		//String logMessage = new StringBuilder("[INFO]  ").append(aClass.getSimpleName()).append(" - ").append(aString).toString();
		getLogger(aClass).info(aString);
	}


	public static void info(Class<?> aClass, String aString, Object... aArgs) {
		getLogger(aClass).info(aString, aArgs);
	}

	/*
	 * debug() methods
	 */
	public static void debug(Class<?> aClass, String aString) {
		// StringBuilder builder = new StringBuilder("[DEBUG] ")
		getLogger(aClass).info(aString);
	}

	public static void debug(Class<?> aClass, String aString, Object... aArgs) {
		getLogger(aClass).info(aString, aArgs);
	}

	private static Logger getLogger(Class<?> aClass) {
		return LoggerFactory.getLogger(aClass);
	}

}
