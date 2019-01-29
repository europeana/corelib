package eu.europeana.corelib.definitions.solr.model;

/**
 * Container for user-specified sort fields and sort orders (ascending/descending)
 * @author Patrick Ehlert
 * Created on 10-04-2018
 */
public class QuerySort {

    /**
     * Use these instead of the ones provided in the apache Solr package
     * in order to avoid introducing a dependency to that package in all modules
     * Values are public because they are read from SearchServiceImpl
     */
    public static final int ORDER_DESC = 0;
    public static final int ORDER_ASC  = 1;

    private static final String SORT_ORDER_PATTERN = "(?i)(ascending|descending|asc|desc)";
    private static final String SORT_ORDER_DELIMETER_PATTERN = "(\\s|\\+)";

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
     * @param sortField name of the field used for sorting
     * @param sortOrder ascending or descending order
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
            throw new IllegalArgumentException("Provided sort order isn't a proper sort order");
        }
    }

    /**
     * Create a new query sort and order
     * @param sortField name of the sort field with optionally ASC, ASCENDING, DESC or DESCENDING to specify sort order
     */
    public QuerySort(String sortField) {
        if (sortField == null) {
            throw new IllegalArgumentException("Sort field is null");
        }
        String field = sortField.trim();

        // does the field end with a sort order specification?
        if (field.matches(".*" + SORT_ORDER_DELIMETER_PATTERN + SORT_ORDER_PATTERN +"$")) {
            // there can be extra spaces (e.g. when using multi-value sort functions) so we split after the last space
            // or + we can find.
            int index = Math.max(field.lastIndexOf('+'), field.lastIndexOf(' '));
            this.sortField = field.substring(0, index).trim();
            if (isAscending(field.substring(index+1, field.length()))) {
                this.sortOrder = ORDER_ASC;
            } else {
                this.sortOrder = ORDER_DESC;
            }
        } else {
            // no sort order defined, using solr default (descending)
            this.sortField = field;
            this.sortOrder = ORDER_DESC;
        }
    }

    /**
     * Checks if the provided (trimmed) string is a sort order specification
     * @param s string
     * @return true if the provided (trimmed) string is a sort order specification (i.a. ascending, descending, asc, or desc)
     */
    public static boolean isSortOrder(String s) {
        return (s != null && s.trim().matches("^"+ SORT_ORDER_PATTERN +"$"));
    }

    /**
     * Checks if the specified sort order is ascending
     * @param sortOrder string
     * @return true if specified sort order is ascending
     */
    public static boolean isAscending(String sortOrder) {
        return sortOrder != null && sortOrder.trim().matches("(?i)(asc(ending)?)");
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
