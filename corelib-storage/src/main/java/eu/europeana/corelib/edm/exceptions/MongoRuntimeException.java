package eu.europeana.corelib.edm.exceptions;
/*
 * Copyright 2007-2016 The Europeana Foundation
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

import eu.europeana.corelib.web.exception.EuropeanaException;
import eu.europeana.corelib.web.exception.ProblemType;

/**
 * im, luthien, hain echant na 25/07/2016.
 */

public class MongoRuntimeException extends EuropeanaException {

    private static final long serialVersionUID = 1L;

    public MongoRuntimeException(ProblemType problem) {
        super(problem);
    }

    public MongoRuntimeException(Throwable causedBy,ProblemType problem) {
        super(causedBy,problem);
    }

}

