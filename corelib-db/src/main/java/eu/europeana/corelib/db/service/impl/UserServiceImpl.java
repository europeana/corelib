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

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import eu.europeana.corelib.db.entity.SavedSearch;
import eu.europeana.corelib.db.entity.Token;
import eu.europeana.corelib.db.entity.User;
import eu.europeana.corelib.db.service.TokenService;
import eu.europeana.corelib.db.service.UserService;
import eu.europeana.corelib.db.service.abstracts.AbstractServiceImpl;

/**
 * @author Willem-Jan Boogerd <europeana [at] eledge.net>
 * 
 * @see eu.europeana.corelib.db.service.UserService
 * @see eu.europeana.corelib.db.entity.User
 */
@Transactional
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {

	@Resource(type = TokenService.class)
	TokenService tokenService;

	@Override
	public User create(Token token, String username, String password) {
		User user = new User();
		user.setEmail(token.getEmail());
		user.setUserName(username);
		user.setPassword(hashPassword(password));
		user.setRegistrationDate(new Date());
		user = dao.insert(user);
		tokenService.remove(token);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		return dao.findOneByNamedQuery(User.QUERY_FINDBY_EMAIL, StringUtils.lowerCase(email));
	}

	@Override
	public User authenticateUser(String email, String password) {
		User user = findByEmail(email);
		if ((user != null) && StringUtils.equals(user.getPassword(), hashPassword(password))) {
			return user;
		}
		return null;
	}
	
	@Override
	public User createSavedSearch(User user, String query, String queryString) {
		user = dao.findByPK(user.getId());
		SavedSearch savedSearch = new SavedSearch();
		savedSearch.setUser(user);
		savedSearch.setDateSaved(new Date());
		savedSearch.setQuery(query);
		savedSearch.setQueryString(queryString);
		user.getSavedSearches().add(savedSearch);
		return user;
	}

	@Override
	public void removeSavedSearch(Long savedSearchId) {
		SavedSearch savedSearch = dao.findByPK(SavedSearch.class, savedSearchId);
		savedSearch.getUser().getSavedSearches().remove(savedSearch);
	}

	/**
	 * Hashing password using ShaPasswordEncoder.
	 * 
	 * @param password
	 *            The password in initial form.
	 * @return Hashed password as to be stored in database
	 */
	private String hashPassword(String password) {
		if (StringUtils.isNotBlank(password)) {
			return new ShaPasswordEncoder().encodePassword(password, null);
		}
		return null;
	}

}
