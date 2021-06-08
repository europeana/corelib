package eu.europeana.corelib.web.exception;

/**
 * What kind of actions should we take for a particular error.
 * Note that
 * - action 'LOG_ERR' includes logging a stacktrace (if available)
 * - action 'LOG_WARN' does NOT include a stacktrace, but does also log the apikey
 */
public enum ProblemResponseAction {
    LOG_ERR, LOG_WARN, IGNORE
}
