package de.pfann.budgetmanager.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	/*
	 * error() methods
	 */
	public static void error(Class<?> _clazz, String _string) {
		getLogger(_clazz).error(_string);
	}

	public static void error(Class<?> _clazz, String _string, Object... _args) {
		getLogger(_clazz).error(_string, _args);
	}

	/*
	 * warn() methods
	 */
	public static void warn(Class<?> _clazz, String _string) {
		getLogger(_clazz).warn(_string);
	}

	public static void warn(Class<?> _clazz, String _string, Object... _args) {
		getLogger(_clazz).warn(_string, _args);
	}

	/*
	 * info() methods
	 */
	public static void info(Class<?> _clazz, String _string) {
		getLogger(_clazz).info(_string);
	}

	public static void info(Class<?> _clazz, String _string, Object... _args) {
		getLogger(_clazz).info(_string, _args);
	}

	/*
	 * debug() methods
	 */
	public static void debug(Class<?> _clazz, String _string) {
		getLogger(_clazz).debug(_string);
	}

	public static void debug(Class<?> _clazz, String _string, Object... _args) {
		getLogger(_clazz).debug(_string, _args);
	}

	private static Logger getLogger(Class<?> _clazz) {
		return LoggerFactory.getLogger(_clazz);
	}

}
