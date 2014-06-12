package eu.europeana.corelib.solr.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;

import eu.europeana.corelib.solr.utils.queryextractor.QueryTerm;

public class QueryExtractor {

	private static Analyzer analyzer = new SimpleAnalyzer();

	public static List<String> extractTerms(String queryTerm) {
		System.out.println(queryTerm);
		List<QueryTerm> queryTokens = extractTokens(queryTerm);
		for (QueryTerm queryToken : queryTokens) {
			System.out.println(String.format("%s -> %s", queryToken.getOriginal(), queryToken.getTransformed()));
		}
		List<String> queryTerms = new ArrayList<String>();
		// Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		QueryParser parser = new QueryParser(Version.LUCENE_40, "text", analyzer);
		// LuceneQParserPlugin queryParserHelper = new LuceneQParserPlugin();
		// StandardQueryParser queryParserHelper = new StandardQueryParser();
		Query query = null;
		try {
			query = parser.parse(queryTerm);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deconstructQuery(query, queryTerms);
		return queryTerms;
	}

	public static boolean deconstructQuery(Query query, List<String> queryTerms) {
		if (query == null) {
			return true;
		}
		if (query instanceof TermQuery) {
			deconstructTermQuery((TermQuery)query, queryTerms);
		} else if (query instanceof PhraseQuery) {
			deconstructPhraseQuery((PhraseQuery)query, queryTerms);
		} else if (query instanceof BooleanQuery) {
			deconstructBooleanQuery((BooleanQuery)query, queryTerms);
		} else if (query instanceof PrefixQuery) {
			deconstructPrefixQuery((PrefixQuery)query, queryTerms);
		} else if (query instanceof FuzzyQuery) {
			deconstructFuzzyQuery((FuzzyQuery)query, queryTerms);
		} else if (query instanceof TermRangeQuery) {
			deconstructTermRangeQuery((TermRangeQuery)query, queryTerms);
		} else {
			System.out.println("Unhandled situation: " + query.getClass());
		}
		return true;
	}

	private static void deconstructPhraseQuery(PhraseQuery query, List<String> queryTerms) {
		int[] positions = query.getPositions();
		Term[] terms = query.getTerms();
		System.out.println(String.format("\tPHRASE (%s) boost: %s, slop: %s, positions: %d, terms: %s", query.toString(), query.getBoost(), query.getSlop(), positions.length, terms.length));

		List<String> words = new ArrayList<String>();
		for (int i = 0, m = positions.length; i < m; i++) {
			System.out.println(String.format("\t\tposition: %s, field: %s, text: %s", positions[i], terms[i].field(), 
					terms[i].text()));
			words.add(terms[i].text());
		}
		queryTerms.add(StringUtils.join(words, " "));
	}

	private static void deconstructTermRangeQuery(TermRangeQuery query, List<String> queryTerms) {
		System.out.println(String.format("\tTERMRANGE (%s) boost: %s, field: %s, lower: %s, upper: %s, incl. lower: %s, incl. upper: %s",
				query.toString(), query.getBoost(), query.getField(), query.getLowerTerm().utf8ToString(), query.getUpperTerm().utf8ToString(),
				query.includesLower(), query.includesUpper()));
		queryTerms.add(query.getLowerTerm().utf8ToString());
		queryTerms.add(query.getUpperTerm().utf8ToString());
	}

	private static void deconstructFuzzyQuery(FuzzyQuery query, List<String> queryTerms) {
		System.out.println(String.format("\tFUZZY (%s) boost: %s, maxEdits: %d, prefixLength: %d, field: %s, text: %s",
			query.toString(), query.getBoost(), query.getMaxEdits(), query.getPrefixLength(), 
			query.getTerm().field(), query.getTerm().text()));
		queryTerms.add(query.getTerm().text());
	}

	private static void deconstructPrefixQuery(PrefixQuery query, List<String> queryTerms) {
		System.out.println(String.format("\tPREFIX (%s) boost: %s, field: %s, text: %s", query.toString(), query.getBoost(), query.getPrefix().field(), query.getPrefix().text()));
		queryTerms.add(query.getPrefix().text());
	}

	private static void deconstructBooleanQuery(BooleanQuery query, List<String> queryTerms) {
		System.out.println(String.format("\tBOOLEAN (%s) boost: %s, isCoordDisabled: %s, MinimumNumberShouldMatch: %d", 
				query.toString(), query.getBoost(), query.isCoordDisabled(), query.getMinimumNumberShouldMatch()));
		for (BooleanClause clause : query.clauses()) {
			System.out.println(String.format("\toccur: %s, query: %s, prohibited: %s, required: %s",
					clause.getOccur().name(), clause.getQuery(), clause.isProhibited(), clause.isRequired()));
			deconstructQuery(clause.getQuery(), queryTerms);
		}
	}

	private static void deconstructTermQuery(TermQuery query, List<String> queryTerms) {
		System.out.println(String.format("\tTERM (%s) boost: %s, field: %s, text: %s", query.toString(), query.getBoost(), query.getTerm().field(), query.getTerm().text()));
		queryTerms.add(query.getTerm().text());
	}

	private static List<QueryTerm> extractTokens(String text) {
		List<QueryTerm> queryTerms = new ArrayList<QueryTerm>();
		TokenStream ts;
		try {
			ts = analyzer.tokenStream("text", new StringReader(text));
			OffsetAttribute offsetAttribute = ts.addAttribute(OffsetAttribute.class);
			CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);

			ts.reset();
			while (ts.incrementToken()) {
				int start = offsetAttribute.startOffset();
				int end = offsetAttribute.endOffset();
				String term = charTermAttribute.toString();
				queryTerms.add(new QueryTerm(start, end, term, text.substring(start, end)));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queryTerms;
	}
}
