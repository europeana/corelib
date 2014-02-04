package eu.europeana.corelib.logging;

import org.apache.log4j.Level;

public class Logger {
	
	public static Logger getLogger(String logname) {
		return new Logger(org.apache.log4j.Logger.getLogger(logname));
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return new Logger(org.apache.log4j.Logger.getLogger(clazz));
	}
	
	private final org.apache.log4j.Logger log;
	
	public Logger(org.apache.log4j.Logger log) {
		this.log = log;
	}
	
	// INFO
	
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	// DEBUG
	
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}
	
	public void debug(String msg) {
		log.debug(msg);
	}
	
	public void debug(String msg, Throwable e) {
		log.debug(msg, e);
	}
	
	// TRACE
	
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}
	
	public void trace(String msg) {
		log.trace(msg);
	}
	
	public void trace(String msg, Throwable e) {
		log.trace(msg, e);
	}
	
	// WARNING
	
	public boolean isWarningEnabled() {
		return log.isEnabledFor(Level.WARN);
	}

	public void warn(String msg) {
		log.warn(msg);
	}
	
	public void warning(String msg) {
		log.warn(msg);
	}
	
	public void warn(String msg, Throwable e) {
		log.warn(msg,e);
	}
	
	public void warning(String msg, Throwable e) {
		log.warn(msg,e);
	}
	
	// ERROR / SEVERE
	
	public boolean isErrorEnabled() {
		return log.isEnabledFor(Level.ERROR);
	}
	
	public void error(String msg) {
		log.error(msg);
	}
	
	public void error(String msg, Throwable e) {
		log.error(msg, e);
	}

}
