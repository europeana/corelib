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

package eu.europeana.corelib.web.utils;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public class EmailBuilder implements MimeMessagePreparator {

    private static final String TEMPLATE_NAME_AFFIX_TEXT = ".txt.vm";
    private static final String TEMPLATE_NAME_AFFIX_HTML = ".html.vm";
	
	private String template;
	
	private Map<String, Object> model;
	
	private VelocityEngine engine;
	
	public EmailBuilder(String template, Map<String, Object> model, VelocityEngine engine) {
		this.template = template;
		this.model = model;
		this.engine = engine;
	}

	@Override
	public void prepare(MimeMessage mimeMessage) throws Exception {
		// TODO Auto-generated method stub

		MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
		//message.setTo(token.getEmail());
		message.setFrom("webmaster@csonth.gov.uk"); // could be parameterized...
		String text = VelocityEngineUtils.mergeTemplateIntoString(engine,
				"email/"+template+TEMPLATE_NAME_AFFIX_TEXT, model);
		message.setText(text, true);
		
	}

}
