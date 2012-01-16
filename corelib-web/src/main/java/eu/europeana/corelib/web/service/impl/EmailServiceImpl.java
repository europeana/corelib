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

package eu.europeana.corelib.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.mail.javamail.JavaMailSender;

import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.definitions.db.entity.User;
import eu.europeana.corelib.web.email.EmailBuilder;
import eu.europeana.corelib.web.exception.EmailServiceException;
import eu.europeana.corelib.web.service.EmailService;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 * 
 * @see eu.europeana.corelib.web.service.EmailService
 */
public abstract class EmailServiceImpl implements EmailService {

	@Resource
	private JavaMailSender mailSender;

	@Override
	public void sendToken(final Token token, String url) throws EmailServiceException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token", token.getToken());
		model.put("url", url);
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("register");
		builder.setEmailTo(token.getEmail());
		mailSender.send(builder);
	}
	
	@Override
	public void sendForgotPassword(User user, String url) throws EmailServiceException {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("url", url);
		EmailBuilder builder = createEmailBuilder();
		builder.setModel(model);
		builder.setTemplate("forgotPassword");
		builder.setEmailTo(user.getEmail());
		mailSender.send(builder);
	}
	
	/**
	 * This method will be handled by Spring Framework.
	 * No implementation needed
	 * 
	 * @return a instance of EmailBuilder
	 */
	protected abstract EmailBuilder createEmailBuilder();

}
