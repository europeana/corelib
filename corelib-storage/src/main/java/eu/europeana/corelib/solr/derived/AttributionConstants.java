package eu.europeana.corelib.solr.derived;

public class AttributionConstants{

    private AttributionConstants(){}

    //AttributionConverter Constants
    public static final String OUT_OF_COPYRIGHT           = "out-of-copyright";
    public static final String DATE_FORMAT                = "yyyy-MM-dd";
    public static final String BASE_URL                   = "http://data.europeana.eu/item";
    public static final String DEF                        = "def";
    public static final String EN                        = "en";


    //html constants
    public static final String TITLE                      = "Title";
    public static final String CREATOR                    = "Creator";
    public static final String DATE                       = "Date";
    public static final String INSTITUTION                = "Institution";
    public static final String COUNTRY                    = "Country";
    public static final String RIGHTS                     = "Rights";

    public static final String HTML_BEGIN_TAG             = "<link rel='stylesheet' type='text/css' href='";
    public static final String HTML_ATTRIBUTION_TAG       = "'/><div class='attribution'>";

    public static final String SPAN_FIELD                 ="<span class='field'>";
    public static final String SPAN_FNAME                 = "<span class='fname'>";
    public static final String SPAN_FVALUE                = "<span class='fvalue'>";
    public static final String SPAN_CLOSING_TAG           = "</span>";
    public static final String COLON                      = ":";

    public static final String BEGIN_HREF                 = "<a href='";
    public static final String HREF                       = "' target='_blank' rel='noopener'>";
    public static final String CLOSE_HREF                 = "</a>";

    public static final String RIGHTS_LIST_BEGIN          = "<ul class='rights-list'>";
    public static final String RIGHTS_LIST_CLOSE          = "</ul>";
    public static final String ICON_LIST_OPEN_TAG         = "<li class='";
    public static final String ICON_LIST_CLOSE_TAG        = "'></li>";
    public static final String DIV_CLOSE_TAG              = "</div>";

    public static final String SPAN_FVALUE_LANG_TAG       = "<span class='fvalue' xml:lang = '";
    public static final String SPAN_FVALUE_LANG_CLOSE_TAG = "'>";

    //text constants
    public static final String CANNOT_DETERMINE_RIGHTS    = "Unmatched rights";
}
