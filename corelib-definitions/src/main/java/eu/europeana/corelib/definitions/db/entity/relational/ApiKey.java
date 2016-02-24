/*
 * Copyright 2007-2012 The Europeana Foundation
 *
 *  Licenced under the EUPL, Version 1.1 (the "Licence") and subsequent versions as approved 
 *  by the European Commission;
 *  You may not use this work except in compliance with the Licence.
 *  
 *  You may obtain a copy of the Licence at:
 *  http://joinup.ec.europa.eu/software/page/eupl
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under 
 *  the Licence is distributed on an "AS IS" basis, without warranties or conditions of 
 *  any kind, either express or implied.
 *  See the Licence for the specific language governing permissions and limitations under 
 *  the Licence.
 */

package eu.europeana.corelib.definitions.db.entity.relational;

import eu.europeana.corelib.definitions.db.entity.relational.abstracts.IdentifiedEntity;
import eu.europeana.corelib.definitions.db.entity.relational.enums.ApiClientLevel;

import java.util.Date;

/**
 * @author Willem-Jan Boogerd <www.eledge.net/contact>
 */
public interface ApiKey extends IdentifiedEntity<String> {

    String QUERY_FINDBY_EMAIL = "ApiKey.findByEmail";

    void setApiKey(String apiKey);

    String getPrivateKey();

    void setPrivateKey(String privateKey);

    String getApplicationName();

    void setApplicationName(String applicationName);

    Long getUsageLimit();

    void setUsageLimit(Long usageLimit);

    String getEmail();

    void setEmail(String email);

    ApiClientLevel getLevel();

    void setLevel(ApiClientLevel level);

    String getCompany();

    void setCompany(String company);

    String getFirstName();

    void setFirstName(String firstName);

    String getLastName();

    void setLastName(String lastName);

    Date getRegistrationDate();

    String getDescription();

    void setDescription(String description);

    String getWebsite();

    void setWebsite(String website);

    Date getActivationDate();

    void setActivationDate(Date activationDate);
}