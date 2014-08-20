package eu.europeana.corelib.solr.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.QueryParser.Operator;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

import eu.europeana.corelib.solr.utils.queryextractor.QueryTermPosition;

public class QueryExtractor {

	private static Analyzer analyzer = new SimpleAnalyzer();

	private static QueryParser queryParser = new QueryParser(Version.LUCENE_40, "text", analyzer);
	static {
		queryParser.setDefaultOperator(Operator.AND);
	}

	private List<QueryToken> queryTokens = new ArrayList<QueryToken>();
	private int group = 0;

	private String rawQueryString;

	public QueryExtractor(String rawQueryString) {
		this.rawQueryString = rawQueryString;
	}

	public List<String> extractTerms() {
		return extractTerms(false);
	}

	public List<String> extractTerms(boolean byGroups) {
		List<QueryTermPosition> termPositions = extractTokens(rawQueryString);
		Query query = null;
		try {
			query = queryParser.parse(rawQueryString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Stack<String> queryTypeStack = new Stack<String>();
		deconstructQuery(query, queryTypeStack);
		insertPositions(termPositions);
		List<String> queryTerms;
		if (byGroups) {
			queryTerms = getTermsByGroups();
		} else {
			queryTerms = getTerms();
		}
		return queryTerms;
	}

	private List<String> getTerms() {
		List<String> queryTerms = new ArrayList<String>();
		for (QueryToken token : queryTokens) {
			if (token.getType().equals("PhraseQuery")
				|| token.getPosition() == null) {
				queryTerms.add(token.getNormalizedQueryTerm());
			} else {
				queryTerms.add(token.getPosition().getOriginal());
			}
		}
		return queryTerms;
	}

	private List<String> getTermsByGroups() {
		List<String> queryTerms = new ArrayList<String>();
		int start = 0; int end = 0;
		int currentGroup = -1;
		int prevPosition = -1;
		for (QueryToken token : queryTokens) {
			if (token.getPosition() == null) {
				queryTerms.add(token.getNormalizedQueryTerm());
				continue;
			}
			if (currentGroup != token.getGroup()) {
				if (currentGroup != -1) {
					queryTerms.add(rawQueryString.substring(start, end));
				}
				start = token.getPosition().getStart();
				currentGroup = token.getGroup();
			} else {
				if ((prevPosition + 1) < token.getPosition().getPosition()) {
					queryTerms.add(rawQueryString.substring(start, end));
					start = token.getPosition().getStart();
					currentGroup = token.getGroup();
				}
			}
			end = token.getPosition().getEnd();
			prevPosition = token.getPosition().getPosition();
		}
		if (start < end) {
			queryTerms.add(rawQueryString.substring(start, end));
		}
		return queryTerms;
	}

	private void insertPositions(List<QueryTermPosition> termPositions) {
		int i = 0, lastFoundPosition = -1, max = termPositions.size();
		for (QueryToken token : queryTokens) {
			boolean isPhrase = token.getType().equals("PhraseQuery");
			List<QueryTermPosition> bag = new ArrayList<QueryTermPosition>();
			String foundPart = "";
			boolean success = false;
			for (i = (lastFoundPosition + 1); i < max; i++) {
				QueryTermPosition position = termPositions.get(i);
				if (isPhrase) {
					String candidate = foundPart.equals("") ? position.getTransformed() : foundPart + " " + position.getTransformed();
					if (token.getNormalizedQueryTerm().equals(candidate)) {
						bag.add(position);
						success = true;
						foundPart = candidate;
						lastFoundPosition = i;
						break;
					} else if (token.getNormalizedQueryTerm().startsWith(candidate)) {
						bag.add(position);
						foundPart = candidate;
						continue;
					}
				} else {
					if (token.getNormalizedQueryTerm().equals(position.getTransformed())) {
						token.setPosition(position);
						lastFoundPosition = i;
						success = true;
						break;
					}
				}
			}
			if (success) {
				if (isPhrase) {
					int start = bag.get(0).getStart();
					int end = bag.get(bag.size()-1).getEnd();
					int pos = bag.get(0).getPosition();
					String original = rawQueryString.substring(start, end);
					token.setPosition(new QueryTermPosition(start, end, foundPart, original, pos));
				}
			} else {
				// System.out.println("Not found token: " + token.getNormalizedQueryTerm());
			}
		}
	}

	public boolean deconstructQuery(Query query, Stack<String> queryTypeStack) {
		if (query == null) {
			return true;
		}
		if (query instanceof TermQuery) {
			queryTypeStack.add("TermQuery");
			deconstructTermQuery((TermQuery)query, queryTypeStack);
		} else if (query instanceof PhraseQuery) {
			group++;
			queryTypeStack.add("PhraseQuery");
			deconstructPhraseQuery((PhraseQuery)query, queryTypeStack);
		} else if (query instanceof BooleanQuery) {
			group++;
			queryTypeStack.add("BooleanQuery");
			deconstructBooleanQuery((BooleanQuery)query, queryTypeStack);
		} else if (query instanceof PrefixQuery) {
			group++;
			queryTypeStack.add("PrefixQuery");
			deconstructPrefixQuery((PrefixQuery)query, queryTypeStack);
		} else if (query instanceof FuzzyQuery) {
			group++;
			queryTypeStack.add("FuzzyQuery");
			deconstructFuzzyQuery((FuzzyQuery)query, queryTypeStack);
		} else if (query instanceof TermRangeQuery) {
			group++;
			queryTypeStack.add("TermRangeQuery");
			deconstructTermRangeQuery((TermRangeQuery)query, queryTypeStack);
		} else {
			// System.out.println("Unhandled situation: " + query.getClass());
		}
		queryTypeStack.pop();
		return true;
	}

	private void deconstructPhraseQuery(PhraseQuery query, Stack<String> queryTypeStack) {
		int[] positions = query.getPositions();
		Term[] terms = query.getTerms();
		// System.out.println(String.format("\tPHRASE (%s) boost: %s, slop: %s, positions: %d, terms: %s", query.toString(), query.getBoost(), query.getSlop(), positions.length, terms.length));

		List<String> words = new ArrayList<String>();
		for (int i = 0, m = positions.length; i < m; i++) {
			/*
			System.out.println(String.format("\t\tposition: %s, field: %s, text: %s", positions[i], terms[i].field(), 
					terms[i].text()));
			*/
			words.add(terms[i].text());
		}
		String term = StringUtils.join(words, " ");
		queryTokens.add(new QueryToken(term, queryTypeStack, group));
	}

	private void deconstructTermRangeQuery(TermRangeQuery query, Stack<String> queryTypeStack) {
		/*
		System.out.println(String.format("\tTERMRANGE (%s) boost: %s, field: %s, lower: %s, upper: %s, incl. lower: %s, incl. upper: %s",
				query.toString(), query.getBoost(), query.getField(), query.getLowerTerm().utf8ToString(), query.getUpperTerm().utf8ToString(),
				query.includesLower(), query.includesUpper()));
		*/
		queryTokens.add(new QueryToken(query.getLowerTerm().utf8ToString(), queryTypeStack, group));
		queryTokens.add(new QueryToken(query.getUpperTerm().utf8ToString(), queryTypeStack, group));
	}

	private void deconstructFuzzyQuery(FuzzyQuery query, Stack<String> queryTypeStack) {
		/*
		System.out.println(String.format("\tFUZZY (%s) boost: %s, maxEdits: %d, prefixLength: %d, field: %s, text: %s",
			query.toString(), query.getBoost(), query.getMaxEdits(), query.getPrefixLength(), 
			query.getTerm().field(), query.getTerm().text()));
		*/
		queryTokens.add(new QueryToken(query.getTerm().text(), queryTypeStack, group));
	}

	private void deconstructPrefixQuery(PrefixQuery query, Stack<String> queryTypeStack) {
		// System.out.println(String.format("\tPREFIX (%s) boost: %s, field: %s, text: %s", query.toString(), query.getBoost(), query.getPrefix().field(), query.getPrefix().text()));
		queryTokens.add(new QueryToken(query.getPrefix().text(), queryTypeStack, group));
	}

	private void deconstructBooleanQuery(BooleanQuery query, Stack<String> queryTypeStack) {
		/*
		System.out.println(String.format("\tBOOLEAN (%s) clauses: %d, boost: %s, isCoordDisabled: %s, MinimumNumberShouldMatch: %d", 
				query.toString(), query.clauses().size(), query.getBoost(), query.isCoordDisabled(), query.getMinimumNumberShouldMatch()));
		*/
		Occur prevOccur = null;
		for (BooleanClause clause : query.clauses()) {
			if (prevOccur != clause.getOccur()) {
				group++;
			}
			/*
			System.out.println(String.format(
				"\t\t[clause] occur: %s, query: %s, prohibited: %s, "
				+ "required: %s",
				clause.getOccur().name(), clause.getQuery(), 
				clause.isProhibited(), clause.isRequired()));
			*/
			queryTypeStack.pop();
			queryTypeStack.add("BooleanQuery " + clause.getOccur().name());
			deconstructQuery(clause.getQuery(), queryTypeStack);
			prevOccur = clause.getOccur();
		}
	}

	private void deconstructTermQuery(TermQuery query, Stack<String> queryTypeStack) {
		// System.out.println(String.format("\tTERM (%s) boost: %s, field: %s, text: %s", query.toString(), query.getBoost(), query.getTerm().field(), query.getTerm().text()));
		queryTokens.add(new QueryToken(query.getTerm().text(), queryTypeStack, group));
	}

	private List<QueryTermPosition> extractTokens(String text) {
		List<QueryTermPosition> queryTerms = new ArrayList<QueryTermPosition>();
		TokenStream ts;
		try {
			ts = analyzer.tokenStream("text", new StringReader(text));
			OffsetAttribute offsetAttribute = ts.addAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);

			ts.reset();
			int i = 0;
			while (ts.incrementToken()) {
				int start = offsetAttribute.startOffset();
				int end = offsetAttribute.endOffset();
				String term = charTermAttribute.toString();
				queryTerms.add(new QueryTermPosition(start, end, term, text.substring(start, end), i++));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queryTerms;
	}
}
