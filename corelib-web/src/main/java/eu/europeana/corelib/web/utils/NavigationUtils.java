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

package eu.europeana.corelib.web.utils;

import java.util.ArrayList;
import java.util.List;

import eu.europeana.corelib.definitions.solr.model.Query;
import eu.europeana.corelib.web.model.BreadCrumb;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class NavigationUtils {
	
	private NavigationUtils() {
		// Constructor must be private
	}

	public static List<BreadCrumb> createBreadCrumbList(Query q) {
		List<BreadCrumb> crumbs = new ArrayList<BreadCrumb>();
		BreadCrumb crumb = new BreadCrumb(q.getQuery(), "query", q.getQuery(), null);
		crumbs.add(crumb);
		for (String refinement : q.getRefinements()) {
			crumb = new BreadCrumb(refinement, "qf", refinement, crumb.getHref());
			crumbs.add(crumb);
		}
		crumb.markAsLast();
		return crumbs;
	}
	
}
