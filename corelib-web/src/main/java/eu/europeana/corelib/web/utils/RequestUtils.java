package eu.europeana.corelib.web.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, String[]> getParameterMap(HttpServletRequest request) {
		if (request != null) {
			return (Map<String, String[]>)request.getParameterMap();
		}
		return null;
	}

}
