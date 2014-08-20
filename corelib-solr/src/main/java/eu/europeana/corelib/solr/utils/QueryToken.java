package eu.europeana.corelib.solr.utils;

import java.util.Stack;

import eu.europeana.corelib.solr.utils.queryextractor.QueryTermPosition;

public class QueryToken {

	private Stack<String> typeStack;
	private String normalizedQueryTerm;
	private QueryTermPosition position;
	private int group;

	@SuppressWarnings("unchecked")
	public QueryToken(String normalizedQueryTerm, Stack<String> typeStack, int group) {
		this.normalizedQueryTerm = normalizedQueryTerm;
		this.typeStack = (Stack<String>) typeStack.clone();
		this.group = group;
	}

	public void setPosition(QueryTermPosition position) {
		this.position = position;
	}

	public Stack<String> getTypeStack() {
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

	public String getType() {
		return typeStack.peek();
	}
}
