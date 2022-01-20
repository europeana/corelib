package eu.europeana.corelib.search.queryextractor;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @deprecated will be replaced by new translation services
 */
@Deprecated(since = "July 2021")
public class QueryToken {

    private ArrayDeque<QueryType> typeStack;
    private String normalizedQueryTerm;
    private QueryTermPosition position;
    private int group;

    public QueryToken() {}

    @SuppressWarnings("unchecked")
    public QueryToken(String normalizedQueryTerm, Deque<QueryType> typeStack, int group) {
        this.normalizedQueryTerm = normalizedQueryTerm;
        this.typeStack = ((ArrayDeque<QueryType>) typeStack).clone();
        this.group = group;
    }

    public void setPosition(QueryTermPosition position) {
        this.position = position;
    }

    public Deque<QueryType> getTypeStack() {
        return typeStack;
    }

    public String getNormalizedQueryTerm() {
        return normalizedQueryTerm;
    }

    public QueryTermPosition getPosition() {
        return position;
    }

    public int getGroup() {
        return group;
    }

    public QueryType getType() {
        return typeStack.peek();
    }

    public String getTerm() {
        if (position == null) {
            return normalizedQueryTerm;
        } else {
            return position.getOriginal();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected QueryToken clone() throws CloneNotSupportedException {
        QueryToken cloned = new QueryToken();
        cloned.position = (this.getPosition() == null) ? null : this.getPosition().clone();
        cloned.normalizedQueryTerm = this.normalizedQueryTerm;
        cloned.typeStack = this.typeStack.clone();
        cloned.group = this.group;

        return cloned;
    }

    public void merge(QueryToken other, String rawQueryString) {
        this.normalizedQueryTerm += " " + other.normalizedQueryTerm;
        if (other.getPosition() != null) {
            this.getPosition().setEnd(other.getPosition().getEnd());
            this.getPosition().setOriginal(rawQueryString.substring(this.position.getStart(), this.position.getEnd()));
        }
    }

    public QueryModification createModification(String rawQueryString, List<String> alternatives) {
        if (alternatives.isEmpty() || getType().equals(QueryType.TERMRANGE)) {
            return null;
        }

        int start = position.getStart();
        int end = position.getEnd();
        if (getType().equals(QueryType.PHRASE)) {
            start--;
            end++;
        }

        String query = rawQueryString.substring(start, end);
        if (query.contains(" ") && !getType().equals(QueryType.PHRASE)) {
            query = "(" + query + ")";
        }
        String replacement = query + " OR \"" + StringUtils.join(alternatives, "\" OR \"") + "\"";
        if (!(start == 0 && end == rawQueryString.length())) {
            replacement = "(" + replacement + ")";
        }
        return new QueryModification(start, end, replacement);
    }

    public String getExtendedPosition() {
        if (position == null) {
            return null;
        }
        int start = position.getStart();
        int end = position.getEnd();
        if (getType().equals(QueryType.PHRASE)) {
            start--;
            end++;
        }
        return String.format("%d:%d", start, end);
    }
}
