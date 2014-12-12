package eu.europeana.corelib.web.support;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import eu.europeana.corelib.logging.Log;
import eu.europeana.corelib.logging.Logger;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;

public class ReportingReloadableMessageSource extends ReloadableResourceBundleMessageSource {
	
	@Log
	private Logger log;
	
	@Resource
	private EmailService emailService;
	
//	@Value("#{europeanaProperties['portal.name']}")
//	private String portalName;

	@Value("#{europeanaProperties['portal.server']}")
	private String portalServer;
	
	// contains lowercase values of earlier reported codes
	private static List<String> reportedBefore = new ArrayList<String>();

	// static list of locale tags to ignore completely
	private static List<String> ignoreList = new ArrayList<String>();
	
	static {
		ignoreList.add("notranslate_carousel-item");
		ignoreList.add("notranslate_featured-partner");
		ignoreList.add("notranslate_featured-item");
		ignoreList.add("language_");
	}
	
	@Override
	protected String getDefaultMessage(String code) {
		boolean report = true;
		for (String s: ignoreList) {
			report &= StringUtils.containsIgnoreCase(code, s); 
		}
		
		if(	report && !reportedBefore.contains(code.toLowerCase())) {
			log.warn("MISSING LOCALE TAG:"+code);
			reportMissingTag(code);
		}
		
		return super.getDefaultMessage(code);
	}
	
	private void reportMissingTag(final String code) {
		reportedBefore.add(code.toLowerCase());
		new Thread(new Runnable() {
		    @Override
			public void run() {
				StringBuilder message = new StringBuilder();
//				message.append("portal.name: ").append(portalName).append("\n");
				message.append("portal.server: ").append(portalServer).append("\n");
				message.append("\n");
				message.append("Missing tag:").append(code);
				try {
					emailService.sendException("Missing locale key:"+code, message.toString());
				} catch (EmailServiceException e) {
					log.info("CANT SEND EMAIL FOR MISSING LOCALE TAG:"+code);
				}
		    }
		}).start();
	}

}
