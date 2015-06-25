package eu.europeana.corelib.search.utils;

import eu.europeana.corelib.definitions.edm.beans.ApiBean;
import eu.europeana.corelib.definitions.edm.beans.BriefBean;
import eu.europeana.corelib.definitions.edm.beans.FullBean;
import eu.europeana.corelib.definitions.edm.beans.IdBean;
import eu.europeana.corelib.edm.utils.FieldMapping;
import eu.europeana.corelib.solr.bean.impl.ApiBeanImpl;
import eu.europeana.corelib.solr.bean.impl.BriefBeanImpl;
import eu.europeana.corelib.solr.bean.impl.IdBeanImpl;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@Ignore // TODO API Using wikipediaApiService which is part of another module...
public class SearchUtilsTest {

    @Test
    public void translateQueryTitle() {
        SearchUtils.translateQuery("title:paris", getLanguageList());
        SearchUtils.translateQuery("title:paris", getLanguageList(true));
        assertEquals(1, 1);
    }

    @Test
    public void translateQueryTitles() {
        SearchUtils.translateQuery("title:paris rome berlin", getLanguageList(true));
        SearchUtils.translateQuery("title:paris rome berlin", getLanguageList());
        assertEquals(1, 1);
    }

    @Test
    public void translateQueryQuotedTitle() {
        SearchUtils.translateQuery("title:\"paris\"", getLanguageList());
        SearchUtils.translateQuery("title:\"paris\"", getLanguageList(true));
        assertEquals(1, 1);
    }

    @Test
    public void translateQueryQuotedTitles() {
        SearchUtils.translateQuery("title:\"paris berlin\"", getLanguageList());
        SearchUtils.translateQuery("title:\"paris berlin\"", getLanguageList(true));
        assertEquals(1, 1);
    }


    /**
     * Tests the translateQuery function.
     */
    @Test
    public void translateQueryTest() {
        Map<String, String> identicalQueries = new HashMap<String, String>() {
            {
                put("simpleQuery", "simpleQuery");
                put("proxy_dc_coverage:ok", "proxy_dc_coverage:ok");
            }
        };
        for (String query : identicalQueries.keySet()) {
            assertEquals(identicalQueries.get(query),
                    SearchUtils.rewriteQueryFields(query));
        }

        Map<String, String> aggregatedQueries = new HashMap<String, String>() {
            {
                put("title:*:*", "title:*");
                put("who:*:*", "who:*");
                put("what:*:*", "what:*");
                put("where:*:*", "where:*");
                put("when:*:*", "when:*");
            }
        };
        for (String query : aggregatedQueries.keySet()) {
            assertEquals(aggregatedQueries.get(query),
                    SearchUtils.rewriteQueryFields(query));
        }

        for (FieldMapping field : FieldMapping.values()) {
            String eseQuery = field.getEseField() + ":test";
            String edmQuery = field.getEdmField() + ":test";
            // ESE is translated to EDM
            assertEquals(edmQuery, SearchUtils.rewriteQueryFields(eseQuery));

            // but EDM is not transformed
            assertEquals(edmQuery, SearchUtils.rewriteQueryFields(edmQuery));
        }

        // testing replace in context
        for (FieldMapping field : FieldMapping.values()) {
            String eseQuery = "test " + field.getEseField() + ":test";
            String edmQuery = "test " + field.getEdmField() + ":test";
            // ESE is translated to EDM
            assertEquals(edmQuery, SearchUtils.rewriteQueryFields(eseQuery));

            // but EDM is not transformed
            assertEquals(edmQuery, SearchUtils.rewriteQueryFields(edmQuery));
        }
    }

    @Test
    public void testTypeFacet() {
        String[] refinementsOk = new String[]{"TYPE:IMAGE"};
        String[] refinementsLowercase = new String[]{"TYPE:image"};
        String[] refinementsBad = new String[]{"TYPE:BAD"};
        assertTrue(SearchUtils.checkTypeFacet(refinementsOk));
        assertTrue(SearchUtils.checkTypeFacet(refinementsLowercase));
        assertFalse(SearchUtils.checkTypeFacet(refinementsBad));
    }

    @Test
    public void testGetImplementationClass() {
        assertTrue(SearchUtils.getImplementationClass(IdBean.class).isAssignableFrom(IdBeanImpl.class));
        assertTrue(SearchUtils.getImplementationClass(ApiBean.class).isAssignableFrom(ApiBeanImpl.class));
        assertTrue(SearchUtils.getImplementationClass(BriefBean.class).isAssignableFrom(BriefBeanImpl.class));
        assertNull(SearchUtils.getImplementationClass(FullBean.class));
    }

    @Test
    public void testEscapeQueryChars() {
        assertEquals("http\\:\\/\\/ -", SearchUtils.escapeQuery("http:// -"));
    }

    @Test
    public void testIsSimpleQuery() {
        assertTrue(SearchUtils.isSimpleQuery("spinoza"));
        assertTrue(SearchUtils.isSimpleQuery("den haag"));
        assertTrue(SearchUtils.isSimpleQuery("den END haag"));
        assertTrue(SearchUtils.isSimpleQuery("den and haag"));
        assertFalse(SearchUtils.isSimpleQuery("den OR haag"));
        assertFalse(SearchUtils.isSimpleQuery("den AND haag"));
        assertFalse(SearchUtils.isSimpleQuery("den:haag"));
        assertFalse(SearchUtils.isSimpleQuery("haag*"));
    }


    private ArrayList<String> getLanguageList() {
        return getLanguageList(false);
    }

    private ArrayList<String> getLanguageList(boolean empty) {
        ArrayList<String> languages = new ArrayList<>();
        if (empty) {
            return languages;
        }
        languages.add("da");
        languages.add("de");
        languages.add("fr");
        languages.add("ga");
        languages.add("it");
        languages.add("nl");
        return languages;
    }

}
