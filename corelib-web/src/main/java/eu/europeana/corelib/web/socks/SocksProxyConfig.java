/*
 * Copyright 2007-2017 The Europeana Foundation
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

package eu.europeana.corelib.web.socks;

import org.springframework.context.annotation.Configuration;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by luthien on 09/08/2017.
 */

@Configuration
public class SocksProxyConfig extends Authenticator{

    private ProxyAuthenticator auth;
//    private String host = "169.51.8.234";
//    private String port = "32080";
//    private String user = "socksuser";
//    private String password = "hjwP14h9oSEp423df";
//    private Boolean useauth = true;

    private String host;
    private String port;
    private String user;
    private String password;
    private Boolean useauth;

//    @Value("${socks.host}")
//    String  host;
//    @Value("#{socks.port}")
//    String  port;
//    @Value("${socks.user}")
//    String  user;
//    @Value("${socks.password}")
//    String  password;
//    @Value("${socks.useauth}")
//    Boolean useauth;


    public SocksProxyConfig(String host, String port, String user, String password, String useauth) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.useauth = Boolean.valueOf(useauth);

        System.setProperty("socksProxyHost", this.host);
        System.setProperty("socksProxyPort", this.port);
        if (this.useauth) {
            System.setProperty("java.net.socks.username", this.user);
            System.setProperty("java.net.socks.password", this.password);
            Authenticator.setDefault(new ProxyAuthenticator(this.user, this.password));
        }
    }



    public String getEncodedAuth(){
        String encoded = java.util.Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
        return encoded;
    }

    public ProxyAuthenticator getAuth(){
        return auth;
    }

    class ProxyAuthenticator extends Authenticator {

        private String user, password;

        public ProxyAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password.toCharArray());
        }
    }
}
