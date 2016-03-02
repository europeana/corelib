package eu.europeana.corelib.search.queryextractor;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;

public class QueryExtractor {

	private Logger log = Logger.getLogger(QueryExtractor.class
			.getCanonicalName());

	private static Analyzer analyzer;

	private static QueryParser queryParser;
	static {

		analyzer = new StandardAnalyzer();
		queryParser = new QueryParser("text", analyzer);
		queryParser.setDefaultOperator(Operator.AND);
	}

	private List<QueryToken> queryTokens = new ArrayList<>();
	private int group = 0;

	private String rawQueryString;

	public QueryExtractor(String rawQueryString) {
		this.rawQueryString = rawQueryString;
	}

	public List<String> extractTerms() {
		return extractTerms(false);
	}

	public List<String> extractTerms(boolean byGroups) {
		List<String> terms = new ArrayList<>();
		for (QueryToken token : extractInfo(byGroups)) {
			terms.add(token.getPosition() == null ? token
					.getNormalizedQueryTerm() : token.getPosition()
					.getOriginal());
		}
		return terms;
	}

	public List<QueryToken> extractInfo(boolean byGroups) {
		parseQuery();
		List<QueryToken> queryTerms;
		if (byGroups) {
			queryTerms = getTermsByGroups();
		} else {
			queryTerms = queryTokens;
		}
		return queryTerms;
	}

	public String rewrite(List<QueryModification> modifications) {
		boolean[] mask = new boolean[rawQueryString.length()];
		Map<Integer, String> map = new HashMap<>();
		for (int i = modifications.size() - 1; i >= 0; i--) {
			QueryModification modification = modifications.get(i);
			if (modification != null) {
				for (int j = modification.getStart(); j < modification.getEnd(); j++) {
					mask[j] = true;
				}
				map.put(modification.getStart(), modification.getModification());
			}
		}
		String rewritten = "";
		boolean lastValue = false;
		for (int i = 0; i < mask.length; i++) {
			if (!mask[i]) {
				rewritten += rawQueryString.substring(i, i + 1);
			} else {
				if (!lastValue) {
					rewritten += map.get(i);
				}
			}
			lastValue = mask[i];
		}
		return rewritten;
	}

	private void parseQuery() {
		List<QueryTermPosition> termPositions = extractTokens(rawQueryString);
		Query query = null;
		try {
			query = queryParser.parse(rawQueryString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Stack<QueryType> queryTypeStack = new Stack<>();
		deconstructQuery(query, queryTypeStack);
		insertPositions(termPositions);
	}

	private List<QueryToken> getTermsByGroups() {
		List<QueryToken> queryTerms = new ArrayList<>();
		for (QueryToken token : queryTokens) {
			if (queryTerms.size() == 0
					|| token.getType().equals(QueryType.TERMRANGE)) {
				queryTerms.add(token);
			} else {
				int lastIndex = queryTerms.size() - 1;
				QueryToken prevToken = queryTerms.get(lastIndex);
				if (prevToken.getGroup() == token.getGroup()) {
					try {
						QueryToken mergedToken = prevToken.clone();
						mergedToken.merge(token, rawQueryString);
						if (!mergedToken.getPosition().getOriginal()
								.contains(" AND ")
								&& !mergedToken.getPosition().getOriginal()
										.contains(" OR ")
								&& !mergedToken.getPosition().getOriginal()
										.contains(" NOT ")) {
							queryTerms.set(lastIndex, mergedToken);
						} else {
							queryTerms.add(token);
						}
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				} else {
					queryTerms.add(token);
				}
			}
		}
		return queryTerms;
	}

	private void insertPositions(List<QueryTermPosition> termPositions) {
		
		int lastFoundPosition = -1, max = termPositions.size();
		
		for (QueryToken token : queryTokens) {
			boolean isPhrase = token.getType().equals(QueryType.PHRASE);
			List<QueryTermPosition> bag = new ArrayList<>();
			String foundPart = "";
			boolean success = false;
			for (int i = (lastFoundPosition + 1); i < max; i++) {
				QueryTermPosition position = termPositions.get(i);
				if (isPhrase) {
					String candidate = foundPart.equals("") ? position
							.getTransformed() : foundPart + " "
							+ position.getTransformed();
					if (token.getNormalizedQueryTerm().equals(candidate)) {
						bag.add(position);
						success = true;
						foundPart = candidate;
						lastFoundPosition = i;
						break;
					} else if (token.getNormalizedQueryTerm().startsWith(
							candidate)) {
						bag.add(position);
						foundPart = candidate;
					}
				} else {
					if (token.getNormalizedQueryTerm().equals(
							position.getTransformed())) {
						token.setPosition(position);
						lastFoundPosition = i;
						if (token.getType().equals(QueryType.TERM)
								&& position.getStart() > 0
								&& position.getEnd() < rawQueryString.length()
								&& rawQueryString.substring(
										position.getStart() - 1,
										position.getStart()).equals("\"")
								&& rawQueryString.substring(position.getEnd(),
										position.getEnd() + 1).equals("\"")) {
							token.getTypeStack().pop();
							token.getTypeStack().add(QueryType.PHRASE);
						}
						success = true;
						break;
					}
					else{
						// ANDY
						if(position.getTransformed().contains(":")){
							token.setPosition(position);
							lastFoundPosition = i;
							success = true;
							break;
						}
						// END ANDY
					}
				}
			}
			if (success) {
				if (isPhrase) {
					int start = bag.get(0).getStart();
					int end = bag.get(bag.size() - 1).getEnd();
					int pos = bag.get(0).getPosition();
					String original = rawQueryString.substring(start, end);
					token.setPosition(new QueryTermPosition(start, end,
							foundPart, original, pos));
				}
			}
			else {
				log.debug("token not found for: " + token.getNormalizedQueryTerm());
			}
		}
	}

	public boolean deconstructQuery(Query query, Stack<QueryType> queryTypeStack) {
		if (query == null) {
			return true;
		}
		if (query instanceof TermQuery) {
			queryTypeStack.add(QueryType.TERM);
			deconstructTermQuery((TermQuery) query, queryTypeStack);
		} else if (query instanceof PhraseQuery) {
			group++;
			queryTypeStack.add(QueryType.PHRASE);
			deconstructPhraseQuery((PhraseQuery) query, queryTypeStack);
		} else if (query instanceof BooleanQuery) {
			group++;
			queryTypeStack.add(QueryType.BOOLEAN);
			deconstructBooleanQuery((BooleanQuery) query, queryTypeStack);
		} else if (query instanceof PrefixQuery) {
			group++;
			queryTypeStack.add(QueryType.PREFIX);
			deconstructPrefixQuery((PrefixQuery) query, queryTypeStack);
		} else if (query instanceof FuzzyQuery) {
			group++;
			queryTypeStack.add(QueryType.FUZZY);
			deconstructFuzzyQuery((FuzzyQuery) query, queryTypeStack);
		} else if (query instanceof TermRangeQuery) {
			group++;
			queryTypeStack.add(QueryType.TERMRANGE);
			deconstructTermRangeQuery((TermRangeQuery) query, queryTypeStack);
		} else if (query instanceof MatchAllDocsQuery) {
			group++;
			queryTypeStack.add(QueryType.MATCHALLDOCS);
			deconstructMatchAllDocsQuery((MatchAllDocsQuery) query,
					queryTypeStack);
		} else {
			log.trace("Unhandled query class: " + query.getClass());
		}
		if (queryTypeStack.size() > 0) {
			queryTypeStack.pop();
		}
		return true;
	}

	private void deconstructPhraseQuery(PhraseQuery query,
			Stack<QueryType> queryTypeStack) {
		int[] positions = query.getPositions();
		Term[] terms = query.getTerms();

		List<String> words = new ArrayList<>();
		for (int i = 0, m = positions.length; i < m; i++) {
			words.add(terms[i].text());
		}
		String term = StringUtils.join(words, " ");
		queryTokens.add(new QueryToken(term, queryTypeStack, group));
	}

	private void deconstructTermRangeQuery(TermRangeQuery query,
			Stack<QueryType> queryTypeStack) {
		queryTokens.add(new QueryToken(query.getLowerTerm().utf8ToString(),
				queryTypeStack, group));
		queryTokens.add(new QueryToken(query.getUpperTerm().utf8ToString(),
				queryTypeStack, group));
	}

	private void deconstructFuzzyQuery(FuzzyQuery query,
			Stack<QueryType> queryTypeStack) {
		queryTokens.add(new QueryToken(query.getTerm().text(), queryTypeStack,
				group));
	}

	private void deconstructPrefixQuery(PrefixQuery query,
			Stack<QueryType> queryTypeStack) {
		queryTokens.add(new QueryToken(query.getPrefix().text(),
				queryTypeStack, group));
	}

	private void deconstructMatchAllDocsQuery(MatchAllDocsQuery query,
			Stack<QueryType> queryTypeStack) {
		// queryTokens.add(new QueryToken(query.toString(), queryTypeStack,
		// group));
	}

	private void deconstructBooleanQuery(BooleanQuery query,
			Stack<QueryType> queryTypeStack) {
		Occur prevOccur = null;
		for (BooleanClause clause : query.clauses()) {
			if (prevOccur != clause.getOccur()) {
				group++;
			}
			queryTypeStack.pop();
			queryTypeStack.add(resolveOccur(clause.getOccur().name()));
			deconstructQuery(clause.getQuery(), queryTypeStack);
			prevOccur = clause.getOccur();
		}
	}

	private QueryType resolveOccur(String name) {
		switch (name) {
		case "AND":
			return QueryType.BOOLEAN_AND;
		case "OR":
			return QueryType.BOOLEAN_OR;
		case "NOT":
			return QueryType.BOOLEAN_NOT;
		default:
			return QueryType.BOOLEAN;
		}
	}

	private void deconstructTermQuery(TermQuery query,
			Stack<QueryType> queryTypeStack) {
		queryTokens.add(new QueryToken(query.getTerm().text(), queryTypeStack,
				group));
	}

	private List<QueryTermPosition> extractTokens(String text) {

		List<QueryTermPosition> queryTerms = new ArrayList<>();
		TokenStream ts;
		try {
			ts = analyzer.tokenStream("text", new StringReader(text));
			OffsetAttribute offsetAttribute = ts
					.addAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttribute = ts
					.addAttribute(CharTermAttribute.class);
			ts.reset();

			int i = 0;
			while (ts.incrementToken()) {
				int start = offsetAttribute.startOffset();
				int end = offsetAttribute.endOffset();
				String term = charTermAttribute.toString();
				
				// ANDY  
				if(term.contains(":")){
					start = start + term.indexOf(":")+1;
				}
				// END ANDY
				
				queryTerms.add(
					new QueryTermPosition(start, end, term, text.substring(start, end), i++)
				);
				
			}
			ts.end();
			ts.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queryTerms;
	}
}
