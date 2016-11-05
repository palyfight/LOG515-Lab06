package LOG515.lab06;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utils {

	public final static String DBHOST = "db_host";
	public final static String DBPORT = "db_port";
	public final static String DBUSER = "db_user";
	public final static String DBPASS = "db_pass";
	public final static String DBNAME = "db_name";
	
	public static String getProperty(String key){
		Properties prop = new Properties();
		try {
			InputStream inputStream = new FileInputStream("config.properties");
			prop.load(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}
}
