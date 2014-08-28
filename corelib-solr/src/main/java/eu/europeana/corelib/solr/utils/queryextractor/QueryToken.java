package eu.europeana.corelib.solr.utils.queryextractor;

import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import eu.europeana.corelib.logging.Logger;

public class QueryToken {

	Logger log = Logger.getLogger(QueryToken.class.getCanonicalName());

	private Stack<QueryType> typeStack;
	private String normalizedQueryTerm;
	private QueryTermPosition position;
	private int group;

	public QueryToken() {}

	@SuppressWarnings("unchecked")
	public QueryToken(String normalizedQueryTerm, Stack<QueryType> typeStack, int group) {
		this.normalizedQueryTerm = normalizedQueryTerm;
		this.typeStack = (Stack<QueryType>) typeStack.clone();
		this.group = group;
	}

	public void setPosition(QueryTermPosition position) {
		this.position = position;
	}

	public Stack<QueryType> getTypeStack() {
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
		cloned.position = (this.getPosition() == null) ? null : (QueryTermPosition) this.getPosition().clone();
		cloned.normalizedQueryTerm = new String(this.normalizedQueryTerm);
		cloned.typeStack = (Stack<QueryType>) this.typeStack.clone();
		cloned.group = new Integer(this.group);
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
		if (alternatives.size() == 0 || getType().equals(QueryType.TERMRANGE)) {
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
		log.info(query + " vs " + rawQueryString);
		log.info(String.format("start: %d, end: %d, length: %d", start, end, rawQueryString.length()));
		if (!(start == 0 && end == rawQueryString.length())) {
			replacement = "(" + replacement + ")";
		}
		return new QueryModification(start, end, replacement);
	}

	public String getExtendedPosition() {
		int start = position.getStart();
		int end = position.getEnd();
		if (getType().equals(QueryType.PHRASE)) {
			start--;
			end++;
		}
		return String.format("%d:%d", start, end);
	}
}
