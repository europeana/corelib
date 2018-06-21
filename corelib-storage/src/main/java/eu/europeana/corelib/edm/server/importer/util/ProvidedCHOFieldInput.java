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
package eu.europeana.corelib.edm.server.importer.util;

import eu.europeana.corelib.definitions.jibx.ProvidedCHOType;
import eu.europeana.corelib.edm.utils.SolrUtils;
import eu.europeana.corelib.solr.entity.ProvidedCHOImpl;

/**
 * Class constructing a SOLR document and MongoDB representation of a
 * ProvidedCHO
 * 
 * 
 * @author Yorgos.Mamakis@ kb.nl
 * 
 */
public final class ProvidedCHOFieldInput {

	public ProvidedCHOFieldInput() {

	}

        /**
	 * 
	 * Method Creating a MongoDB Entity of a ProvidedCHO
	 * 
	 * @param providedCHO
	 *            The ProvidedCHO representation from the JiBX bindings
	 * @return The MongoDB ProvidedCHO Entity
	 */
	public ProvidedCHOImpl createProvidedCHOMongoFields(
			ProvidedCHOType providedCHO) {
		ProvidedCHOImpl mongoProvidedCHO = new ProvidedCHOImpl();
			mongoProvidedCHO.setAbout(providedCHO.getAbout());

			mongoProvidedCHO.setOwlSameAs(SolrUtils
					.resourceListToArray(providedCHO.getSameAList()));
		return mongoProvidedCHO;
	}
}
