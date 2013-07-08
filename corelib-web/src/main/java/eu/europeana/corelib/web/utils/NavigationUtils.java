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

package eu.europeana.corelib.web.utils;

import java.util.ArrayList;
import java.util.List;

import eu.europeana.corelib.definitions.model.web.BreadCrumb;
import eu.europeana.corelib.definitions.solr.model.Query;

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
		String[] refinements = q.getRefinements();
		if (refinements != null && refinements.length > 0) {
			for (String refinement : refinements) {
				crumb = new BreadCrumb(refinement, "qf", refinement, crumb.getHref());
				crumbs.add(crumb);
			}
		}
		crumb.markAsLast();
		return crumbs;
	}
}