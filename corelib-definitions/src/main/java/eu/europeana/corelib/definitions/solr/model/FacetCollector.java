package eu.europeana.corelib.definitions.solr.model;

import eu.europeana.corelib.definitions.solr.SolrFacetType;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FacetCollector {

    private final static String OR          = " OR ";

    private boolean isTagged = true;
    private String name;
    private String tagName;
    private List<String> values         = new ArrayList<>();
    private List<String> replacedValues = new ArrayList<>();
    private boolean      isApiQuery     = false;
    private boolean      replaced       = false;

    public FacetCollector(String name) {
        this.name = name;
        this.tagName = name;
    }

    public FacetCollector(String name, boolean isApiQuery) {
        this(name);
        this.isApiQuery = isApiQuery;
    }

    public boolean isTagged() {
        return isTagged;
    }

    public void setTagged(boolean isTagged) {
        this.isTagged = isTagged;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    private boolean isAlreadyQuoted(String value) {
        return value.startsWith("\"") && value.endsWith("\"");
    }

    private boolean hasOr(String value) {
        return value.startsWith("(") && value.endsWith(")") && value.contains(" OR ");
    }

    public void addValue(String value, boolean isReplaced) {
        value = cleanIfValueContainsFieldName(value);
        if (name.equals(SolrFacetType.RIGHTS.name())) {
            if (value.endsWith("*")) {
                value = value.replace(":", "\\:").replace("/", "\\/");
            } else if (!isAlreadyQuoted(value) && !hasOr(value)) {
                value = '"' + value + '"';
            }
        } else if (name.equals(SolrFacetType.TYPE.name())) {
            value = value.toUpperCase().replace("\"", "");
        } else {
            if (!isApiQuery && (value.contains(" ") || value.contains("!"))) {
                if (!value.startsWith("\"")) {
                    value = '"' + value;
                }
                if (!value.endsWith("\"")) {
                    value += '"';
                }
            }
        }
        if (isReplaced) {
            replacedValues.add(value);
        } else {
            values.add(value);
        }
    }

    /**
     * EA-4192
     * There are instances that the refinement array contains the field names multiple times along with a boolean operator
     * Ex : [ foaf_organization:"organization/123",
     *       foaf_organization:"organization/567" OR foaf_organization:"organization/xyz"]
     *       OR
     *       [RIGHTS:http://creativecommons.org/licenses/by-nc/4.0/ OR RIGHTS:http://creativecommons.org/licenses/by-nc-nd/4.0/] etc..
     * When the second value is processed in FacetCollector,
     * value is = "organization/567" OR foaf_organization:"organization/xyz"
     *       OR = "http://creativecommons.org/licenses/by-nc/4.0/ OR RIGHTS:http://creativecommons.org/licenses/by-nc-nd/4.0/"
     * which contains the facet/fieldname as well.
     * This Fix is to clean up the value if they contain the name already in it.
     *
     */
    private String cleanIfValueContainsFieldName(String value) {
        if (StringUtils.contains(value, name)) {
            return StringUtils.replace(value, name + ":", "");
        }
        return value;
    }

    private String join(List<String> valueList, String booleanOperator) {
        if (valueList.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        if (valueList.size() > 1) {
            sb.append("(");
            sb.append(StringUtils.join(valueList, booleanOperator));
            sb.append(")");
        } else {
            sb.append(valueList.get(0));
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isTagged && !replaced) {
            sb.append("{!tag=").append(tagName).append("}");
        }
        sb.append(name);
        sb.append(":");

        String valuesString         = join(values, OR);
        String replacedValuesString = join(replacedValues, OR);

        if (StringUtils.isNotBlank(valuesString)) {
            if (StringUtils.isNotBlank(replacedValuesString)) {
                sb.append(String.format("(%s AND %s)", valuesString, replacedValuesString));
            } else {
                sb.append(valuesString);
            }
        } else {
            if (StringUtils.isNotBlank(replacedValuesString)) {
                sb.append(replacedValuesString);
            }
        }

        return sb.toString();
    }
}
