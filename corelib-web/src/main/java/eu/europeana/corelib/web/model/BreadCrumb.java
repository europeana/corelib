/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.0 or - as soon they
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

package eu.europeana.corelib.web.model;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class BreadCrumb {

	private String href;
	
	private String display;
	
	private boolean last = false;
	
	public BreadCrumb(String display, String href) {
		this.display = display;
		this.href = href;
	}
	
	public String getDisplay() {
		return display;
	}
	
	public String getHref() {
		return href;
	}
	
	public boolean isLast() {
		return last;
	}

	public void markAsLast() {
		last = true;
	}

}
