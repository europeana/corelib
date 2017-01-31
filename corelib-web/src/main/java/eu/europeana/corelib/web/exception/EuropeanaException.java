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


import eu.europeana.corelib.web.service.EmailService;

import javax.annotation.Resource;

/**
 * Abstract EuropeanaException, modules should define their own extension based on this one.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * @author Luthien <maike.dulk@europeana.eu>
 */
public abstract class EuropeanaException extends Exception {
	private static final long serialVersionUID = 4759945931809288624L;
	private final String SUBJECTPREFIX = "Europeana exception email handler: ";
	private final String NOCAUSEDBY = "No throwable root exception available";

	@Resource(name = "corelib_web_emailService")
	private EmailService emailService;

	private ProblemType problem;

	public EuropeanaException(ProblemType problem) {
		this.problem = problem;
		handleProblemResponseAction(problem, null);
	}

	public EuropeanaException(Throwable causedBy, ProblemType problem) {
		super(causedBy);
		this.problem = problem;
		handleProblemResponseAction(problem, causedBy);
	}

	@Override
	public String getMessage() {
		return problem.getMessage();
	}

	public ProblemType getProblem() {
		return problem;
	}

	private void handleProblemResponseAction(ProblemType problem, Throwable causedBy){
		String newline = System.getProperty("line.separator");

		if (problem.getAction().equals(ProblemResponseAction.MAIL)){
			String header = SUBJECTPREFIX + problem.getMessage();
			String body = (causedBy == null ? NOCAUSEDBY : causedBy.getMessage() + newline + newline
					+ causedBy.getStackTrace());
			try {
				emailService.sendException(header, body);
			} catch (EmailServiceException e) {
				e.printStackTrace();
			}
		}
	}
}
