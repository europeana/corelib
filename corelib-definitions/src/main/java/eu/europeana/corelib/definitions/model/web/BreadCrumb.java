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

package eu.europeana.corelib.definitions.model.web;

import org.apache.commons.lang.StringUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class BreadCrumb {

	/**
	 * The human-readable label of the breadcrumb item
	 */
	private String display;
	
	/**
	 * The full query URL
	 */
	private String href;
	
	/**
	 * The actual query param specific for this breadcrumb item
	 */
	private String param = null;
	
	/**
	 * The value belongs to the param (this is usually the raw form of the display)
	 */
	private String value = null;
	
	/**
	 * Flag notifies whether it is the last item of the breadcumbs list
	 */
	private boolean last = false;
	
	public BreadCrumb(String display, String href) {
		this.display = display;
		this.href = href;
	}
	
	public BreadCrumb(String display, String param, String value, String prevHref) {
		this.display = display;
		this.param = param;
		this.value = value;
		this.href = createHref(param, value, prevHref);
	}
	
	public String getDisplay() {
		return display;
	}
	
	public String getHref() {
		return href;
	}
	
	private String createHref(String param, String value, String prevHref) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(prevHref)) {
			sb.append(prevHref);
			sb.append("&amp;");
		}
		sb.append(param);
		sb.append("=");
		sb.append(value);
		return sb.toString();
	}
	
	public boolean isLast() {
		return last;
	}

	public void markAsLast() {
		last = true;
	}
	
	public String getParam() {
		return param;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s / %s=%s full: %s (%b)", display, param, value, href, last);
	}
}
