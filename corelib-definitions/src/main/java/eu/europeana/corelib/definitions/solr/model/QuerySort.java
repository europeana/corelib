package eu.europeana.corelib.definitions.solr.model;

import org.apache.logging.log4j.LogManager;

import java.util.regex.Pattern;

/**
 * Container for user-specified sort fields and sort orders (ascending/descending)
 * @author Patrick Ehlert
 * Created on 10-04-2018
 */
public class QuerySort {

    /**
     * Use these instead of the ones provided in the apache Solr package
     * in order to avoid introducing a dependency to that package in all modules
     * they're public because they are read from SearchServiceImpl
     */
    public static final int ORDER_DESC = 0;
    public static final int ORDER_ASC  = 1;

    /**
     * @param s
     * @return true if the provided (trimmed) string is sort order specification (i.a. ascending, descending, asc, or desc)
     */
    public static boolean isSortOrder(String s) {
        return (s != null && s.trim().matches("^"+SORT_ORDER_MATCH+"$"));
    }

    /**
     * @param sortOrder
     * @return true if specified sort order is ascending
     */
    public static boolean isAscending(String sortOrder) {
        return sortOrder != null && sortOrder.trim().matches("(?i)(asc(ending)?)");
    }

    private static final String SORT_ORDER_MATCH = "(?i)(ascending|descending|asc|desc)";

    private String sortField;
    private int sortOrder = ORDER_DESC;

    /**
     * Create a new query sort and order
     * @param sortField name of the sort field
     * @param sortOrder sort order, either ascending (1) or descending (0)
     */
    public QuerySort(String sortField, int sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    /**
     * Create a new query sort and order
     * @param sortField
     * @param sortOrder
     */
    public QuerySort(String sortField, String sortOrder) {
        this.sortField = sortField;
        if (isSortOrder(sortOrder)) {
            if (isAscending(sortOrder)){
                this.sortOrder = ORDER_ASC;
            } else {
                this.sortOrder = ORDER_DESC;
            }
        } else {
            throw new IllegalArgumentException("Provided sort order isn't a really a sort order");
        }
    }

    /**
     * Create a new query sort and order
     * @param sortField name of the sort field with optionally ASC, ASCENDING, DESC or DESCENDING to specify sort order
     */
    public QuerySort(String sortField) {
        // does the sortField end with a sortorder specification?
        if (sortField != null && sortField.trim().matches(".+\\s+"+SORT_ORDER_MATCH)) {
            // sort order defined
            String[] sortFieldParts = sortField.split("\\s+");
            if (isAscending(sortFieldParts[1])) {
                this.sortField = sortFieldParts[0];
                this.sortOrder = ORDER_ASC;
            } else {
                this.sortField = sortFieldParts[0];
                this.sortOrder = ORDER_DESC;
            }
        } else {
            // no sort order defined, using solr default (descending)
            this.sortField = sortField;
            this.sortOrder = ORDER_DESC;
        }
    }

    public String getSortField() {
        return sortField;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public String toString() {
        return sortField + (sortOrder == ORDER_ASC ? " asc" : " desc");
    }

}
