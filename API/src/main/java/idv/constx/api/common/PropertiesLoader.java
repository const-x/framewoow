package idv.constx.api.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	
	private static Properties prop;

	public static String getValue(String key){
		if(prop == null){
			synchronized (PropertiesLoader.class) {
				if(prop == null){
					prop = new Properties();
					InputStream in = PropertiesLoader.class.getResourceAsStream("/app.properties");
					try {
						prop.load(in);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return prop.getProperty(key);
	}
	
}
