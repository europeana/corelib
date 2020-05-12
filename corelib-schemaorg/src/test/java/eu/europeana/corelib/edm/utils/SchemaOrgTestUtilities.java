package eu.europeana.corelib.edm.utils;

import eu.europeana.corelib.edm.model.schemaorg.BaseType;
import eu.europeana.corelib.edm.model.schemaorg.MultilingualString;
import eu.europeana.corelib.edm.model.schemaorg.Reference;
import eu.europeana.corelib.edm.model.schemaorg.Thing;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * SchemaOrgUtilsTest Utilities
 *
 * @author Srishti Singh
 * <p>
 * Created on 09-05-2020
 */

public class SchemaOrgTestUtilities {

    protected static final String THING          = "Thing";
    protected static final String TEXT           = "Text";
    protected static final String ORANGANIZATION = "Organization";
    protected static final String PERSON         = "Person";
    protected static final String PLACE          = "Place";

    // get Resources and Reference values
    protected static List<String> getResourceOrReference(String propertyName, Thing object) {
        List<String> resOrRef = new ArrayList<>();
        resOrRef.addAll(getReference(propertyName, object));
        resOrRef.addAll(getResource(propertyName, object));
        return resOrRef;
    }

    // get Reference values
    protected static List<String> getReference(String propertyName, Thing object) {
        List<String> references = new ArrayList<>();
        List<Reference> referenceList = new ArrayList<>();
        List<BaseType> propertiesList = object.getProperty(propertyName);
        for (BaseType property : propertiesList) {
            if (! StringUtils.equalsIgnoreCase(property.getTypeName(), THING) && ! StringUtils.equalsIgnoreCase(property.getTypeName(), TEXT)
                    && ! StringUtils.equalsIgnoreCase(property.getTypeName(), ORANGANIZATION)) {
                referenceList.add((Reference) property);
            }
        }
        for (Reference reference : referenceList) {
            references.add(reference.getId());
        }
        return references;
    }

    //get resources values
    protected static List<String> getResource(String propertyName, Thing object) {
        List<String> resources = new ArrayList<>();
        List<Thing> resourceList = new ArrayList<>();
        List<BaseType> propertiesList = object.getProperty(propertyName);
        for (BaseType resource : propertiesList) {
            if (StringUtils.equalsIgnoreCase(resource.getTypeName(), THING) || StringUtils.equalsIgnoreCase(resource.getTypeName(), ORANGANIZATION)) {
                resourceList.add((Thing) resource);
            }
        }
        for (Thing resource : resourceList) {
            resources.add(StringUtils.substringBetween(resource.getName().toString(), "[", "]"));
        }
        return resources;
    }

    //get Text values
    protected static List<String> getText(String propertyName, Thing object) {
        List<String> texts = new ArrayList<>();
        List<BaseType> propertiesList = object.getProperty(propertyName);
        for (BaseType text : propertiesList) {
            if (StringUtils.equalsIgnoreCase(text.getTypeName(), TEXT)) {
                texts.add(text.toString());
            }
        }
        return texts;
    }

    //get MultiLingual values
    protected static List<String> getMultilingualString(String propertyName, Thing object) {
        List<String> multilingualStrings = new ArrayList<>();
        List<BaseType> propertiesList = object.getProperty(propertyName);
        for (BaseType multiligualString : propertiesList) {
            MultilingualString temp = (MultilingualString) multiligualString;
            multilingualStrings.add(temp.getValue());
        }
        return multilingualStrings;
    }
}
