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