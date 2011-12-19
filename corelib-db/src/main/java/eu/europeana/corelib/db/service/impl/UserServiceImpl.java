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

package eu.europeana.corelib.db.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.entity.User;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;

@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
	
	@Resource(type=TokenService.class)
	TokenService tokenService;

	@Override
	public User create(Token token, String password) {
		User user = new User();
		user.setEmail(token.getEmail());
		user.setPassword(password);
		user.setRegistrationDate(new Date());
		user = dao.insert(user);
		tokenService.remove(token);
		return user;
	}


}
