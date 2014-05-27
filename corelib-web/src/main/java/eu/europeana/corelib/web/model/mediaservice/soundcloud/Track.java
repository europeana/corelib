package eu.europeana.corelib.web.model.mediaservice.soundcloud;

public class Track {

	private String state;
	private String id;
	private String permalink;
	private String permalink_url;
	private User user;

	public User getUser() {
		return user;
	}

	public String getState() {
		return state;
	}

	public String getId() {
		return id;
	}

	public String getPermalink() {
		return permalink;
	}

	public String getPermalink_url() {
		return permalink_url;
	}

	public String getPath() {
		return user.permalink + "/" + permalink;
	}

	public class User {
		private String permalink;

		public String getPermalink() {
			return permalink;
		}
	}
}
