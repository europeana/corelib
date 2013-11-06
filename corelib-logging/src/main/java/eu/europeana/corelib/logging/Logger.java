package eu.europeana.corelib.logging;

import java.util.logging.Level;

public class Logger {
	
	public static Logger getLogger(String logname) {
		return new Logger(java.util.logging.Logger.getLogger(logname));
	}
	
	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}
	
	private final java.util.logging.Logger log;
	
	public Logger(java.util.logging.Logger log) {
		this.log = log;
	}
	
	// INFO
	
	public boolean isInfoEnabled() {
		return log.isLoggable(Level.INFO);
	}
	
	public void info(String msg) {
		log.info(msg);
	}
	
	// DEBUG
	
	public boolean isDebugEnabled() {
		return log.isLoggable(Level.FINE);
	}
	
	public void debug(String msg) {
		log.log(Level.FINE, msg);
	}
	
	public void debug(String msg, Throwable e) {
		log.log(Level.FINE, msg, e);
	}
	
	// TRACE
	
	public boolean isTraceEnabled() {
		return log.isLoggable(Level.FINER);
	}
	
	public void trace(String msg) {
		log.log(Level.FINER, msg);
	}
	
	public void trace(String msg, Throwable e) {
		log.log(Level.FINER, msg, e);
	}
	
	// WARNING
	
	public boolean isWarningEnabled() {
		return log.isLoggable(Level.WARNING);
	}
	
	public void warn(String msg) {
		log.warning(msg);
	}
	
	public void warning(String msg) {
		log.warning(msg);
	}
	
	// ERROR / SEVERE
	
	public boolean isErrorEnabled() {
		return log.isLoggable(Level.SEVERE);
	}
	
	public void error(String msg) {
		log.severe(msg);
	}
	
	public void error(String msg, Throwable e) {
		log.log(Level.SEVERE, msg, e);
	}

}
