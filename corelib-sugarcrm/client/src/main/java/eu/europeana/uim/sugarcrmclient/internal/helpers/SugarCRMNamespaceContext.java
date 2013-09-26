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

package eu.europeana.uim.sugarcrmclient.internal.helpers;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 * This is an auxiliary helper class used during the evaluation of xpath 
 * statements in SOAP responses delivered by SugarCRM 
 * 
 * @author Georgios Markakis
 */
public class SugarCRMNamespaceContext implements NamespaceContext {


    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
     */
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
        	throw new IllegalArgumentException("Null prefix");
        }
        else if ("ns1".equals(prefix)){
        	 return "http://www.sugarcrm.com/sugarcrm";
        }
        else if ("xsi".equals(prefix)){
        	 return "http://www.w3.org/2001/XMLSchema-instance";    
        }
        else if ("SOAP-ENC".equals(prefix)){
        	 return "http://schemas.xmlsoap.org/soap/encoding/";  
        }
        else if ("xml".equals(prefix)){
        	 return XMLConstants.XML_NS_URI;
        }
        return XMLConstants.NULL_NS_URI;
    }


    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
     */
    public String getPrefix(String uri) {
        throw new UnsupportedOperationException();
    }


    /* (non-Javadoc)
     * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
     */
    public Iterator<?> getPrefixes(String uri) {
        throw new UnsupportedOperationException();
    }

}