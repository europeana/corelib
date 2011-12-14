/*
 * Copyright 2007 EDL FOUNDATION
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they
 * will be approved by the European Commission - subsequent
 * versions of the EUPL (the "Licence");
 * you may not use this work except in compliance with the
 * Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl
 *
 * Unless required by applicable law or agreed to in
 * writing, software distributed under the Licence is
 * distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 * See the Licence for the specific language governing
 * permissions and limitations under the Licence.
 */

package eu.europeana.corelib.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import eu.europeana.corelib.db.entity.abstracts.IdentifiedEntity;

/**
 * @author Nicola Aloia <nicola.aloia@isti.cnr.it>
 * @author Cesare Corcordia <cesare.concordia@isti.cnr.it>
 */
@Entity
@Table(name="token")
public class Token implements IdentifiedEntity<String> {
	private static final long serialVersionUID = -9185878608713327601L;

	@Id
    @Column(length = 64, nullable = false)
    private String token;

    @Column(length = 64, nullable = false)
    private String email;

    @Column(nullable = false)
    private long created;
    
    /**
     * GETTERS & SETTTERS
     */
    
    public String getId() {
    	return token;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}