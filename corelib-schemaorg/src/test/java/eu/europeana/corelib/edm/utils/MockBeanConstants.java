package eu.europeana.corelib.edm.utils;

/**
 * constants for MockFullBean
 *
 * @author Srishti Singh
 * <p>
 * Created on 09-05-2020
 */

import eu.europeana.corelib.utils.DateUtils;

import java.util.Date;

public class MockBeanConstants {

    protected static final String   FULL_BEAN_FILE              = "/schemaorg/fullbean.json";
    protected static final String   EDMORGANIZATION_FILE        = "/schemaorg/edmorganization.json";

    protected static final String TEMPORAL_COVERAGE_TIMESPAN_1  = "1901-01-01T00:19:32/2000-12-31T01:00:00";
    protected static final String TEMPORAL_COVERAGE_TIMESPAN_2  = "1901-01-01/1902-01-01";
    protected static final String VIDEO_OBJECT                  = "VideoObject";
    protected static final String IMAGE_OBJECT                  = "ImageObject";


    protected static final String  COMMON_ABOUT             = "2021618/internetserver_Details_kunst_25027";
    protected static final String  DEF                      = "def";
    protected static final String  IT                       = "it";
    protected static final String  EN                       = "en";
    protected static final String  PL                       = "pl";
    protected static final String  FR                       = "fr";

    protected static final String  URL_PREFIX               = "http://data.europeana.eu";
    protected static final String  ABOUT                    = "/item/" + COMMON_ABOUT ;
    protected static final String  EDM_IS_SHOWN_AT          = "http://teylers.adlibhosting.com/internetserver/Details/kunst/25027";
    protected static final String  EDM_IS_SHOWN_BY          = "http://teylers.adlibhosting.com/wwwopacx/wwwopac.ashx?command=getcontent&server=images&value=TvB%20G%203674.jpg";
    protected static final String  EDM_PREVIEW              = "http://edmPreview.jpg";
    protected static final String  DC_TYPE_1                = "grafiek";
    protected static final String  DC_TYPE_2                = "http://data.europeana.eu/concept/base/48";
    protected static final String  DC_COVERAGE_ABOUT        = "01 December 1950";  //invalid date hence will be added as about
    protected static final String  DC_COVERAGE_TEMPORAL     = "http://semium.org/time/1977" ;
    protected static final String  DC_CREATOR_1             = "Calamatta, Luigi (1801-1869)";
    protected static final String  DC_CREATOR_2             = "Leonardo da Vinci (1452 - 1519)";
    protected static final String  DC_CREATOR_3             = "graveur";
    protected static final String  DC_CREATOR_4             = "http://data.europeana.eu/concept/base/190";
    protected static final String  DC_CREATOR_5             = "http://data.europeana.eu/agent/base/103195";
    protected static final String  DC_CREATOR_6             = "http://data.europeana.eu/agent/base/6";
    protected static final String  DC_CREATOR_7             = "#place_Paris";
    protected static final String  DC_CREATOR_8             = "http://data.europeana.eu/agent/base/146741";
    protected static final String  DC_CREATOR_9             = "Europeana";
    protected static final String  DC_TERMS_CREATED         = "01 November 1928";
    protected static final String  EDM_COUNTRY              = "netherlands";
    protected static final String  DC_TERMS_TEMPORAL_1      = "http://semium.org/time/19xx";
    protected static final String  DC_TERMS_TEMPORAL_2      = "http://semium.org/time/1901";
    protected static final String  DC_TERMS_TEMPORAL_3      = "1981-1990";
    protected static final String  DC_TERMS_TEMPORAL_4      = "1930";
    protected static final String  EDM_PROVIDER_1           = "Teylers Museum";
    protected static final String  EDM_PROVIDER_2           = "Digitale Collectie";
    protected static final String  DC_TITLE                 = "Mona Lisa";
    protected static final String  DC_DESCRIPTION           = "https://data.europeana.eu/test";
    protected static final String  DC_ALTERNATIVE           = "MonaLisa Painting";
    protected static final long    TIMESTAMP_CREATED        = 1395759054479L;
    protected static final Date    TIMESTAMP_CREATED_DATE   = new Date(1395759054479L);
    protected static final long    TIMESTAMP_UPDATED        = 1395759054479L;
    protected static final String  MIME_TYPE_VIDEO          = "video/mp4";
    protected static final String  MIME_TYPE_IMAGE          = "image/jpeg";
    protected static final String  LANGUAUGE_NL             = "nl";
    protected static final String  EDM_RIGHTS               = "http://creativecommons.org/licenses/by-nc/3.0/nl/";
    protected static final String  EUROPEANA_COLLECTION     = "2021618_Ag_NL_DigitaleCollectie_TeylersKunst";
    protected static final String  AGGREGATION_ABOUT        = "/aggregation/provider/" + COMMON_ABOUT ;
    protected static final String  DC_DATE                  = "1821 - 1869";
    protected static final String  DC_IDENTIFIER            = "TvB G 3674";
    protected static final String  PROXY_ABOUT_1            = "/proxy/provider/" + COMMON_ABOUT ;
    protected static final String  EUROPEANA_AGG_ABOUT      = "/aggregation/europeana/" + COMMON_ABOUT ;
    protected static final String  PROXY_ABOUT_2            = "/proxy/europeana/" +COMMON_ABOUT ;

    //agent Person
    protected static final String  BIRTH_DATE               = "1452-04-15";
    protected static final String  DEATH_DATE               = "1519-05-02";
    protected static final String  BIRTH_PLACE_1            = "http://dbpedia.org/resource/Republic_of_Florence";
    protected static final String  BIRTH_PLACE_2            = "http://dbpedia.org/resource/Vinci,_Tuscany";
    protected static final String  BIRTH_PLACE_3            = "Vinci, Republic of Florence";
    protected static final String  DEATH_PLACE_1            = "http://dbpedia.org/resource/Indre-et-Loire";
    protected static final String  DEATH_PLACE_2            = "http://dbpedia.org/resource/Amboise";

    //Organization
    protected static final String  DISOLUTION_DATE          = "2019-05-02";

    //Place
    protected static final String  PLACE_PREF_LABEL         = "Paris";
    protected static final String  PLACE_NOTE               = "Probably in Popicourt";
    protected static final String  PLACE_SAME_OWL_AS        = "http://www.somewhere.eu/place/5";
    protected static final String  PLACE_HAS_PART           = "http://www.somewhere.eu/place/2";
    protected static final String  PLACE_IS_PART            = "http://www.somewhere.eu/place/3";

    //Concept
    protected static final String  CONCEPT_PREF_LABEL_1     = "Landbruk";
    protected static final String  CONCEPT_PREF_LABEL_2     = "Landwirtschaft";
    protected static final String  CONCEPT_NOTE_1           = "Landbruk er en fellesbetegnelse for jordbruk og skogbruk som begge er primærnæringer, og omfatter en rekke næringsgrener der foredling av jord til kulturplanter eller beite er grunnleggende for produksjonen. Ordet har samme betydning som agrikultur, fra latin ager («åker») og cultura («dyrking»).I Norge blir 2,8 % av landarealet brukt til jordbruk (2005).";
    protected static final String  CONCEPT_NOTE_2           = "Als Landwirtschaft wird der Wirtschaftsbereich der Urproduktion bezeichnet. Das Ziel der Urproduktion ist die zielgerichtete Herstellung pflanzlicher oder tierischer Erzeugnisse auf einer zu diesem Zweck bewirtschafteten Fläche. In der Wissenschaft sowie der fachlichen Praxis ist synonym der Begriff Agrarwirtschaft gebräuchlich.Die Landwirtschaft stellt einen der ältesten Wirtschaftsbereiche der Menschheit dar. Heute beläuft sich die landwirtschaftlich genutzte Fläche auf 48.827.330 km2, dies sind 9,6 % der Erdoberfläche. Somit wird etwa ein Drittel der Landfläche der Erde landwirtschaftlich genutzt.Der Wirtschaftsbereich Agrarwirtschaft wird zumeist in die beiden Sektoren Pflanzenproduktion Tierproduktioneingeteilt und dann weiter untergliedertDer Anbau von Nutzpflanzen dient zuallererst der Nahrungsmittelproduktion direkt wie indirekt. In letzterem Fall erfolgt die Herstellung von Rohstoffen zur weiteren Verarbeitung in Teilen des nachgelagerten Wirtschaftsbereichs des sogenannten Agribusiness (z. B. Weiterverarbeitung von Getreide zu Mehl für die Brotherstellung). Darüber hinaus werden landwirtschaftliche Rohstoffe (u. a. Faserpflanzen wie Baumwolle oder Leinen) auch in der Bekleidungsindustrie weiter veredelt.Die Haltung von Nutztieren dient in erster Linie der Nahrungsmittelproduktion (z. B. Milch, Eier), in zweiter Linie der Herstellung von Rohstoffen für die Herstellung von Bekleidung. Vor der Produktion von Kunstfasern schufen die Menschen noch ihre gesamte Bekleidung u. a. aus den tierischen Produkten Leder, Pelz und Wolle.Die Verwertung der durch die Agrarwirtschaft, zum Teil erst seit kürzerer Zeit, angebauten Biomasse aus nachwachsenden Rohstoffen (u. a. Holz, Mais) in Form von Vergasung, Karbonisierung und Raffinierung stellt eine erst seit kurzer Zeit mitunter stark zunehmende Form der Veredelung dar.Die Landwirtschaft ist Teilwirtschaftszweig eines größeren Gesamtsystems mit vor- und nachgelagerten Sektoren.Eine Person, die Landwirtschaft betreibt, bezeichnet man als Landwirt. Neben berufspraktischen Ausbildungen bestehen an zahlreichen Universitäten und Fachhochschulen eigene landwirtschaftliche Fachbereiche. Das dort gelehrte und erforschte Fach Agrarwissenschaft bereitet sowohl auf die Führung von landwirtschaftlichen Betrieben als auch auf Tätigkeiten in verwandten Wirtschaftsbereichen vor und ist ein ingenieurwissenschaftliches Fach.";
}