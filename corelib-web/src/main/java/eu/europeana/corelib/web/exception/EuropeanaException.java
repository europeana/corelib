/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.web.exception;



/**
 * Abstract EuropeanaException, modules should define their own extension based on this one.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Luthien <maike.dulk@europeana.eu>
 */
public abstract class EuropeanaException extends Exception {
	private static final long serialVersionUID = 4759945931809288624L;

	private ProblemType problem;

	private String additionalInfo;

	public EuropeanaException(ProblemType problem) {
		this.problem = problem;
	}

	/**
	 * Create a new exception of the provided problem type. Note that when additional information is provided this is appended to the
	 * exception message.
	 * @param problem required field
	 * @param additionalInfo optional string describing details of the problem
	 */
	public EuropeanaException(ProblemType problem, String additionalInfo) {
		this.problem = problem;
		this.additionalInfo = additionalInfo;
	}

	public EuropeanaException(Throwable causedBy, ProblemType problem) {
		super(causedBy);
		this.problem = problem;
	}

	@Override
	public String getMessage() {
		return problem.getMessage() + (additionalInfo == null ? "" : " - "+additionalInfo);
	}

	public ProblemType getProblem() {
		return problem;
	}
}
