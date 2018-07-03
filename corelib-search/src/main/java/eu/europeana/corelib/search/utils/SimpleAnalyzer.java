package eu.europeana.corelib.search.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.core.LetterTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.util.Version;

import java.io.Reader;

@Deprecated // July 3rd 2018 Code isn't used anymore
public class SimpleAnalyzer {
//public class SimpleAnalyzer extends Analyzer {

	//@Override
	//protected TokenStreamComponents createComponents(String fieldName, Reader reader) {
		// CharArraySet stopWords = new CharArraySet(Version.LUCENE_40, Arrays.asList("spinoza"), true);
		//Tokenizer source = new LetterTokenizer(Version.LUCENE_6_6_2, reader);
		//TokenStream filter = new LowerCaseFilter(Version.LUCENE_6_6_2, source);
		// filter = new StopFilter(Version.LUCENE_40, filter, stopWords);
		// filter = new PorterStemFilter(filter);
		//return new TokenStreamComponents(source, filter);
	//}

}
