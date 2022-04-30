package com.ctc.demo.emulator.util;

import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class CTCLogger {
	public static final Logger log = LogManager.getLogger(CTCLogger.class);

	public static void info(String pattern, Object... params) {
		if (params != null && params.length > 0)
			log.info(MessageFormat.format(pattern, params));
	}

	public static void debug(String pattern, Object... params) {
		if (params != null && params.length > 0)
			log.debug(MessageFormat.format(pattern, params));
	}

	public static void error(String pattern, Object... params) {
		if (params != null && params.length > 0)
			log.error(MessageFormat.format(pattern, params));
	}
}
