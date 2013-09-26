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
package eu.europeana.uim.sugarcrmclient.internal;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeader;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPException;

import org.springframework.util.StringUtils;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.SoapMessageCreationException;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.TransportConstants;
import org.springframework.ws.transport.TransportInputStream;

import java.util.zip.GZIPInputStream;



/**
 * This Class is an implementation of the Spring-specific SaajSoapMessageFactory
 * class. SugarCRM  SOAP responses are encoded in a GZIP format. The main purpose
 * of this implementation is to unzip the content before redirecting it to the
 * unmarshalling context.
 * 
 * @author Georgios Markakis
 */
public class ExtendedSaajSoapMessageFactory extends SaajSoapMessageFactory {

    /** Default, empty constructor. */
    public ExtendedSaajSoapMessageFactory() {
    	super();
    }

    /** Constructor that takes a message factory as an argument. */
    public ExtendedSaajSoapMessageFactory(MessageFactory messageFactory) {
        super.setMessageFactory(messageFactory);
    }
	
    
    /* (non-Javadoc)
     * @see org.springframework.ws.soap.saaj.SaajSoapMessageFactory#createWebServiceMessage(java.io.InputStream)
     */
    public SaajSoapMessage createWebServiceMessage(InputStream inputStream) throws IOException {
        MimeHeaders mimeHeaders = parseMimeHeaders(inputStream);

        try {
            inputStream = checkForUtf8ByteOrderMark(inputStream);
            inputStream = decompressStream((PushbackInputStream)inputStream);
            return new SaajSoapMessage(getMessageFactory().createMessage(mimeHeaders, inputStream));
        }
        catch (SOAPException ex) {
            // SAAJ 1.3 RI has a issue with handling multipart XOP content types which contain "startinfo" rather than
            // "start-info", so let's try and do something about it
            String contentType = StringUtils
                    .arrayToCommaDelimitedString(mimeHeaders.getHeader(TransportConstants.HEADER_CONTENT_TYPE));
            if (contentType.indexOf("startinfo") != -1) {
                contentType = contentType.replace("startinfo", "start-info");
                mimeHeaders.setHeader(TransportConstants.HEADER_CONTENT_TYPE, contentType);
                try {
                    return new SaajSoapMessage(getMessageFactory().createMessage(mimeHeaders,inputStream),
                            true);
                }
                catch (SOAPException e) {
                    // fall-through
                }
            }
            throw new SoapMessageCreationException("Could not create message from InputStream: " + ex.getMessage(), ex);
        }
    }
    
    private MimeHeaders parseMimeHeaders(InputStream inputStream) throws IOException {
        MimeHeaders mimeHeaders = new MimeHeaders();
        if (inputStream instanceof TransportInputStream) {
            TransportInputStream transportInputStream = (TransportInputStream) inputStream;
            for (Iterator headerNames = transportInputStream.getHeaderNames(); headerNames.hasNext();) {
                String headerName = (String) headerNames.next();
                for (Iterator headerValues = transportInputStream.getHeaders(headerName); headerValues.hasNext();) {
                    String headerValue = (String) headerValues.next();
                    StringTokenizer tokenizer = new StringTokenizer(headerValue, ",");
                    while (tokenizer.hasMoreTokens()) {
                        mimeHeaders.addHeader(headerName, tokenizer.nextToken().trim());
                    }
                }
            }
        }
        return mimeHeaders;
    }
    
    /**
     * Checks for the UTF-8 Byte Order Mark, and removes it if present. The SAAJ RI cannot cope with these BOMs.
     *
     * @see <a href="http://jira.springframework.org/browse/SWS-393">SWS-393</a>
     * @see <a href="http://unicode.org/faq/utf_bom.html#22">UTF-8 BOMs</a>
     */
    private InputStream checkForUtf8ByteOrderMark(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(new BufferedInputStream(inputStream), 3);
        byte[] bom = new byte[3];
        if (pushbackInputStream.read(bom) != -1) {
            // check for the UTF-8 BOM, and remove it if there. See SWS-393
            if (!(bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF)) {
                pushbackInputStream.unread(bom);
            }
        }
        return pushbackInputStream;
    }
    
    
    
    /**
     * Detects if the incoming stream is Gzip encoded
     * 
     * @param pb
     * @return an InputStream/GZIPInputStream 
     * @throws IOException
     */
    public static InputStream decompressStream(PushbackInputStream pb) throws IOException {    	
        byte [] signature = new byte[2];
        pb.read( signature ); 
        pb.unread( signature );
    
        if( signature[ 0 ] == 31 && signature[ 1 ] == -117 ) 
          return new GZIPInputStream( pb );
        else 
          return pb;
   }
}
