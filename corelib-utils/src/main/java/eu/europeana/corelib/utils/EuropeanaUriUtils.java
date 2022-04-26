package eu.europeana.corelib.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * EuropeanaID creator class
 *
 * @author yorgos.mamakis@ kb.nl
 *
 */

public final class EuropeanaUriUtils {
    
    private static final Pattern RELATIVEURLPATTERN = Pattern.compile("^(?!www\\.|(?:http|ftp|session)s?://|[A-Za-z]:\\|//)(?:#|\\./|\\.\\./|/)\\S+$");
//    private static final Pattern RELATIVEURLPATTERN = Pattern.compile("^(?!www\\.|(?:http|ftp|session|bitcoin)s?://|[A-Za-z]:\\|//)(?:#|\\./|\\.\\./|/|[A-Za-z])\\S+$");
    //private static final Pattern ABSOLUTEURLPATTERN = Pattern.compile("^([a-zA-Z][a-zA-Z+-.]*):.*$");
    private static final Pattern ABSOLUTEURLPATTERN = Pattern.compile("^(https?|ftp|session)://[^\\s/$.?#].[^\\s]*$");
    private static final Set<String> schemes= new HashSet<>();

    static {
        schemes.add("aaa");
        schemes.add("aaas");
        schemes.add("about");
        schemes.add("acap");
        schemes.add("acct");
        schemes.add("acr");
        schemes.add("adiumxtra");
        schemes.add("afp");
        schemes.add("afs");
        schemes.add("aim");
        schemes.add("appdata");
        schemes.add("apt");
        schemes.add("attachment");
        schemes.add("aw");
        schemes.add("barion");
        schemes.add("beshare");
        schemes.add("bitcoin");
        schemes.add("blob");
        schemes.add("bolo");
        schemes.add("browserext");
        schemes.add("callto");
        schemes.add("cap");
        schemes.add("chrome");
        schemes.add("chrome-extension");
        schemes.add("cid");
        schemes.add("coap");
        schemes.add("coaps");
        schemes.add("com-eventbrite-attendee");
        schemes.add("content");
        schemes.add("crid");
        schemes.add("cvs");
        schemes.add("data");
        schemes.add("dav");
        schemes.add("dict");
        schemes.add("dis");
        schemes.add("dlna-playcontainer");
        schemes.add("dlna-playsingle");
        schemes.add("dns");
        schemes.add("dntp");
        schemes.add("dtn");
        schemes.add("dvb");
        schemes.add("ed2k");
        schemes.add("example");
        schemes.add("facetime");
        schemes.add("fax");
        schemes.add("feed");
        schemes.add("feedready");
        schemes.add("file");
        schemes.add("filesystem");
        schemes.add("finger");
        schemes.add("fish");
        schemes.add("ftp");
        schemes.add("geo");
        schemes.add("gg");
        schemes.add("git");
        schemes.add("gizmoproject");
        schemes.add("go");
        schemes.add("gopher");
        schemes.add("gtalk");
        schemes.add("h323");
        schemes.add("ham");
        schemes.add("hcp");
        schemes.add("http");
        schemes.add("https");
        schemes.add("iax");
        schemes.add("icap");
        schemes.add("icon");
        schemes.add("im");
        schemes.add("imap");
        schemes.add("info");
        schemes.add("iotdisco");
        schemes.add("ipn");
        schemes.add("ipp");
        schemes.add("ipps");
        schemes.add("irc");
        schemes.add("irc6");
        schemes.add("ircs");
        schemes.add("iris");
        schemes.add("iris.beep");
        schemes.add("iris.lwz");
        schemes.add("iris.xpc");
        schemes.add("iris.xpcs");
        schemes.add("isostore");
        schemes.add("itms");
        schemes.add("jabber");
        schemes.add("jar");
        schemes.add("jms");
        schemes.add("keyparc");
        schemes.add("lastfm");
        schemes.add("ldap");
        schemes.add("ldaps");
        schemes.add("lvlt");
        schemes.add("magnet");
        schemes.add("mailserver");
        schemes.add("mailto");
        schemes.add("maps");
        schemes.add("market");
        schemes.add("message");
        schemes.add("mid");
        schemes.add("mms");
        schemes.add("modem");
        schemes.add("ms-access");
        schemes.add("ms-browser-extension");
        schemes.add("ms-drive-to");
        schemes.add("ms-enrollment");
        schemes.add("ms-excel");
        schemes.add("ms-gamebarservices");
        schemes.add("ms-getoffice");
        schemes.add("ms-help");
        schemes.add("ms-infopath");
        schemes.add("ms-media-stream-id");
        schemes.add("ms-project");
        schemes.add("ms-powerpoint");
        schemes.add("ms-publisher");
        schemes.add("ms-search-repair");
        schemes.add("ms-secondary-screen-controller");
        schemes.add("ms-secondary-screen-setup");
        schemes.add("ms-settings");
        schemes.add("ms-settings-airplanemode");
        schemes.add("ms-settings-bluetooth");
        schemes.add("ms-settings-camera");
        schemes.add("ms-settings-cellular");
        schemes.add("ms-settings-cloudstorage");
        schemes.add("ms-settings-connectabledevices");
        schemes.add("ms-settings-displays-topology");
        schemes.add("ms-settings-emailandaccounts");
        schemes.add("ms-settings-language");
        schemes.add("ms-settings-location");
        schemes.add("ms-settings-lock");
        schemes.add("ms-settings-nfctransactions");
        schemes.add("ms-settings-notifications");
        schemes.add("ms-settings-power");
        schemes.add("ms-settings-privacy");
        schemes.add("ms-settings-proximity");
        schemes.add("ms-settings-screenrotation");
        schemes.add("ms-settings-wifi");
        schemes.add("ms-settings-workplace");
        schemes.add("ms-spd");
        schemes.add("ms-transit-to");
        schemes.add("ms-virtualtouchpad");
        schemes.add("ms-visio");
        schemes.add("ms-walk-to");
        schemes.add("ms-word");
        schemes.add("msnim");
        schemes.add("msrp");
        schemes.add("msrps");
        schemes.add("mtqp");
        schemes.add("mumble");
        schemes.add("mupdate");
        schemes.add("mvn");
        schemes.add("news");
        schemes.add("nfs");
        schemes.add("ni");
        schemes.add("nih");
        schemes.add("nntp");
        schemes.add("notes");
        schemes.add("ocf");
        schemes.add("oid");
        schemes.add("opaquelocktoken");
        schemes.add("pack");
        schemes.add("palm");
        schemes.add("paparazzi");
        schemes.add("pkcs11");
        schemes.add("platform");
        schemes.add("pop");
        schemes.add("pres");
        schemes.add("prospero");
        // schemes.add("proxy"); //removed See ticket EA-1781
        schemes.add("psyc");
        schemes.add("qb");
        schemes.add("query");
        schemes.add("redis");
        schemes.add("rediss");
        schemes.add("reload");
        schemes.add("res");
        schemes.add("resource");
        schemes.add("rmi");
        schemes.add("rsync");
        schemes.add("rtmfp");
        schemes.add("rtmp");
        schemes.add("rtsp");
        schemes.add("rtsps");
        schemes.add("rtspu");
        schemes.add("secondlife");
        schemes.add("service");
        schemes.add("session");
        schemes.add("sftp");
        schemes.add("sgn");
        schemes.add("shttp");
        schemes.add("sieve");
        schemes.add("sip");
        schemes.add("sips");
        schemes.add("skype");
        schemes.add("smb");
        schemes.add("sms");
        schemes.add("smtp");
        schemes.add("snews");
        schemes.add("snmp");
        schemes.add("soap.beep");
        schemes.add("soap.beeps");
        schemes.add("soldat");
        schemes.add("spotify");
        schemes.add("ssh");
        schemes.add("steam");
        schemes.add("stun");
        schemes.add("stuns");
        schemes.add("submit");
        schemes.add("svn");
        schemes.add("tag");
        schemes.add("teamspeak");
        schemes.add("tel");
        schemes.add("teliaeid");
        schemes.add("telnet");
        schemes.add("tftp");
        schemes.add("things");
        schemes.add("thismessage");
        schemes.add("tip");
        schemes.add("tn3270");
        schemes.add("tool");
        schemes.add("turn");
        schemes.add("turns");
        schemes.add("tv");
        schemes.add("udp");
        schemes.add("unreal");
        schemes.add("urn");
        schemes.add("ut2004");
        schemes.add("v-event");
        schemes.add("vemmi");
        schemes.add("ventrilo");
        schemes.add("videotex");
        schemes.add("vnc");
        schemes.add("view-source");
        schemes.add("wais");
        schemes.add("webcal");
        schemes.add("wpid");
        schemes.add("ws");
        schemes.add("wss");
        schemes.add("wtai");
        schemes.add("wyciwyg");
        schemes.add("xcon");
        schemes.add("xcon-userid");
        schemes.add("xfire");
        schemes.add("xmlrpc.beep");
        schemes.add("xmlrpc.beeps");
        schemes.add("xmpp");
        schemes.add("xri");
        schemes.add("ymsgr");
        schemes.add("z39.50");
        schemes.add("z39.50r");
        schemes.add("z39.50s");
    }

    private EuropeanaUriUtils() {

    }

    /**
     * Create the EuropeanaID from the collection ID and record ID
     *
     * @param collectionId  collection ID
     * @param recordId      record ID (unique local identifier of a collection record)
     * @return              Europeana compatible ID
     */

    public static String createSanitizedEuropeanaId(String collectionId, String recordId) {
        return "/" + sanitizeCollectionId(collectionId) + "/" + sanitizeRecordId(recordId);
    }

    public static String createEuropeanaId(String collectionId, String recordId){
        return "/" + collectionId + "/" + recordId;
    }

    private static String sanitizeRecordId(String recordId) {

        recordId = StringUtils.startsWith(recordId, "http://") ?
                StringUtils.substringAfter(StringUtils.substringAfter(recordId, "http://"), "/") :
                recordId;
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9_]");
        Matcher matcher = pattern.matcher(recordId);
        recordId = matcher.replaceAll("_");
        return recordId;
    }

    private static String sanitizeCollectionId(String collectionId) {
        Pattern pattern = Pattern.compile("[a-zA-Z]");
        Matcher matcher = pattern.matcher(collectionId.substring(collectionId.length() - 1));
        return matcher.find() ? StringUtils.substring(collectionId, 0, collectionId.length() - 1) : collectionId;
    }

    /**
     * Check if the provided string is a valid (absolute or relative) URI
     *
     * @param str URI to check
     * @return true if the provided string is not empty and a valid URI, otherwise false
     */
    public static boolean isUri(String str) {
        if (StringUtils.isNotEmpty(str)) {
            return (isAbsoluteUri(str) || isRelativeUri(str));
        }
        return false;
    }
    
    /**
     * Checks if the URI is absolute URI
     *
     * @param uri URI to check
     * @return true is the URI is absolute, false otherwise
     */
    public static boolean isAbsoluteUri(String uri) {
        Matcher m = ABSOLUTEURLPATTERN.matcher(uri);
        return ( m.find() && schemes.contains(m.group(1)));
    }
    
    /**
     * Checks if the supplied string is a relative URI by checking if it starts with
     * "#" (edm specific cases), "/", "../", "./" or directly with the filepath, and
     * contains no whitespace characters (\r\n\t\f\v)
     * The first half starting (?! ... pattern is a negative lookout to actively filter
     * absolute URIs
     *
     * @param uri URI to check
     * @return true is the URI is relative according to the above, false otherwise
     */
    static boolean isRelativeUri(String uri) {
        Matcher m = RELATIVEURLPATTERN.matcher(uri);
        return m.find();
    }

}
