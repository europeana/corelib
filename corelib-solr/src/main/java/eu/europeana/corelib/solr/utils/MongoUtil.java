package eu.europeana.corelib.solr.utils;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;

import eu.europeana.corelib.solr.server.MongoDBServer;

public class MongoUtil {

	public static boolean contains(String[] str1, String str2) {
		for (String str : str1) {
			if (StringUtils.equals(str, str2)) {
				return true;
			}
		}
		return false;
	}

	public static boolean contains(Map<String, String> map, String key,
			String val) {
		if (map.keySet().contains(key)) {
			if (StringUtils.equals(map.get(key).toString(), val)) {
				return true;
			}
		}
		return false;
	}

	public static void delete(Class<?> clazz, String about,
			MongoDBServer mongoServer) {
		mongoServer.getDatastore().delete(clazz, about);
	}

	/**
	 * Delete a generic Mongo Entity from the MongoDB Server
	 * 
	 * @param clazz
	 *            The class type of the Mongo Entity
	 * @param about
	 *            The rdf:about field of the Mongo entity
	 * @param mongoServer
	 *            The MongoDBServer to connect to
	 * @param field
	 *            The field to update
	 * @param value
	 *            The value to update
	 */
	@SuppressWarnings("unchecked")
	public static <T> void update(Class<T> clazz, String about,
			MongoDBServer mongoServer, String field, Object value) {
		Query<T> updateQuery = mongoServer.getDatastore().createQuery(clazz)
				.field("about").equal(about);
		UpdateOperations<T> ops = null;

		/*
		 * If the value is an ArrayList it must be converted to String Array
		 * unless it refers to Web Resources
		 */
		if (value instanceof List
				&& !(StringUtils.equals("webResources", field))) {
			ops = mongoServer
					.getDatastore()
					.createUpdateOperations(clazz)
					.set(field,
							((List<String>) value)
									.toArray(new String[((List<String>) value)
											.size()]));
		} else {
			ops = mongoServer.getDatastore().createUpdateOperations(clazz)
					.set(field, value);

		}
		mongoServer.getDatastore().update(updateQuery, ops);
	}
}
