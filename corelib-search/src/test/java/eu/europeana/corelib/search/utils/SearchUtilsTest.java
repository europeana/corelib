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

@Ignore
public class SearchUtilsTest {

    @Test
    public void testTypeFacet() {
        String[] refinementsOk = new String[]{"TYPE:IMAGE"};
        String[] refinementsLowercase = new String[]{"TYPE:image"};
        String[] refinementsBad = new String[]{"TYPE:BAD"};
        assertTrue(SearchUtils.checkTypeFacet(refinementsOk));          // call to this method was removed in EA-1631
        assertTrue(SearchUtils.checkTypeFacet(refinementsLowercase));   // if it turns out not to be necessary, also
        assertFalse(SearchUtils.checkTypeFacet(refinementsBad));        // remove these calls and the method itself
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
