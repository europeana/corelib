package eu.europeana.corelib.web.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

public class LocaleInterceptor extends HandlerInterceptorAdapter {

	public static final String DEFAULT_PARAM_NAME = "locale";
	
	private String paramName = DEFAULT_PARAM_NAME;

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return this.paramName;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, 
			Object handler)
					throws IllegalStateException {

		String localeName = request.getParameter(this.paramName);

		if (StringUtils.isNotBlank(localeName)
			&& !(localeName.contains("*"))) {

			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
			}
			LocaleEditor localeEditor = new LocaleEditor();
			localeEditor.setAsText(localeName);
			Locale locale = (Locale) localeEditor.getValue();
			localeResolver.setLocale(request, response, locale);
		}

		return true;
	}
}
