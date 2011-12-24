/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they 
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "License");
 * you may not use this work except in compliance with the
 * License.
 * You may obtain a copy of the License at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the License is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 */

package eu.europeana.corelib.definitions.exception;

/**
 * Abstract EuropeanaException, modules should define their own extention based on this one.
 * 
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public abstract class EuropeanaException extends Exception {
	private static final long serialVersionUID = 4759945931809288624L;

	private ProblemType problem;

	public EuropeanaException(ProblemType problem) {
		this.problem = problem;
	}

	public EuropeanaException(Throwable causedBy, ProblemType problem) {
		super(causedBy);
		this.problem = problem;
	}

	@Override
	public String getMessage() {
		return problem.getMessage();
	}

}
