package eu.europeana.corelib.web.model.wikipedia;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class WikipediaQuery {

	private Query query;

	public Query getQuery() {
		return query;
	}

	public class Query {

		private List<FromTo> redirects;
		private Map<String, Page> pages;

		public List<FromTo> getRedirects() {
			return redirects;
		}

		public Map<String, Page> getPages() {
			return pages;
		}

		public class FromTo {
			private String from;
			private String to;

			public String getFrom() {
				return from;
			}

			public String getTo() {
				return to;
			}
		}

		public class Page {

			private long pageid;
			private long ns;
			private String title;
			private List<WikiLangLink> langlinks;

			public long getPageid() {
				return pageid;
			}

			public long getNs() {
				return ns;
			}

			public String getTitle() {
				return title;
			}

			public List<WikiLangLink> getLanglinks() {
				return langlinks;
			}

			public class WikiLangLink {

				String lang;

				@SerializedName("*")
				String translation;

				public String getLang() {
					return lang;
				}

				public String getTranslation() {
					return translation;
				}
			}
		}
	}
}
