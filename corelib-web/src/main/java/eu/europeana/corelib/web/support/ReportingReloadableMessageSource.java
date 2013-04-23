package eu.europeana.corelib.web.support;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;

public class ReportingReloadableMessageSource extends ReloadableResourceBundleMessageSource {
	
	private final Logger log = Logger.getLogger(ReportingReloadableMessageSource.class.getName());
	
	@Resource
	private EmailService emailService;
	
	@Value("#{europeanaProperties['portal.name']}")
	private String portalName;

	@Value("#{europeanaProperties['portal.server']}")
	private String portalServer;
	
	private static List<String> reportedBefore = new ArrayList<String>();
	
	@Override
	protected String getDefaultMessage(String code) {
		log.info("MISSING LOCALE TAG:"+code);
		reportMissingTag(code);
		return super.getDefaultMessage(code);
	}
	
	private void reportMissingTag(final String code) {
		if (!reportedBefore.contains(code)) {
			new Thread(new Runnable() {
			    @Override
				public void run() {
					StringBuilder message = new StringBuilder();
					message.append("portal.name: ").append(portalName).append("\n");
					message.append("portal.server: ").append(portalServer).append("\n");
					message.append("\n");
					message.append("Missig tag:").append(code);
					try {
						emailService.sendException("Missing locale key:"+code, message.toString());
					} catch (EmailServiceException e) {
						log.info("CANT SEND EMAIL FOR MISSING LOCALE TAG:"+code);
						// ignore
					}
					reportedBefore.add(code);
			    }
			}).start();
		}
	}

}
