package eu.europeana.corelib.solr.derived;

public class AttributionConstants{

    private AttributionConstants(){}

    //AttributionConverter Constants
    public static final String OUT_OF_COPYRIGHT           = "out-of-copyright";
    public static final String DATE_FORMAT                = "yyyy-MM-dd";
    public static final String BASE_URL                   = "http://data.europeana.eu/item";
    public static final String DEF                        = "def";
    public static final String EN                         = "en";

    //html constants
    public static final String TITLE                      = "Title";
    public static final String CREATOR                    = "Creator";
    public static final String DATE                       = "Date";
    public static final String INSTITUTION                = "Institution";
    public static final String COUNTRY                    = "Country";
    public static final String RIGHTS                     = "Rights";

    public static final String HTML_BEGIN_TAG             = "<link rel='stylesheet' type='text/css' href='";
    public static final String HTML_ATTRIBUTION_TAG       = "'/><dl class='europeana-attribution' lang='en'>";
    public static final String HTML_CLOSE_TAG             = "</dl>";

    public static final String TYPE_TAG_OPEN              = "<dt>";
    public static final String TYPE_TAG_CLOSE             = "</dt>";
    public static final String VALUE_TAG_OPEN             = "<dd>";
    public static final String VALUE_TAG_CLOSE            = "</dd>";
    public static final String SPAN_OPENING_TAG           = "<span class='";
    public static final String SPAN_CLOSING_TAG           = "'/>";

    public static final String BEGIN_HREF                 = "<a href='";
    public static final String HREF                       = "' target='_blank' rel='noopener'>";
    public static final String CLOSE_HREF                 = "</a>";
    public static final String LANG_TAG_OPEN              = "<dd lang='";
    public static final String LANG_TAG_CLOSE             = "'>";

    //text constants
    public static final String CANNOT_DETERMINE_RIGHTS    = "Unmatched rights";
}
