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

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

import eu.europeana.corelib.definitions.db.entity.Token;
import eu.europeana.corelib.web.service.EmailService;
import eu.europeana.corelib.web.utils.EmailBuilder;

public class EmailServiceImpl implements EmailService {

	@Resource
	private JavaMailSender mailSender;

	@Resource
	private VelocityEngine velocityEngine;

	@Override
	public void sendToken(final Token token) {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("token", token);
		EmailBuilder builder = new EmailBuilder("confirmation", model, velocityEngine);
		mailSender.send(builder);
	}

}
