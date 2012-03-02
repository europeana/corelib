package eu.europeana.corelib.solr.utils;


import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MongoUtil {

	public static boolean contains(String[] str1, String str2){
		for(String str:str1){
			if(StringUtils.equals(str, str2)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean contains(Map<String,String> map, String key, String val){
		if(map.keySet().contains(key)){
			if(StringUtils.equals(map.get(key).toString(), val)){
				return true;
			}
		}
		return false;
	}
}
