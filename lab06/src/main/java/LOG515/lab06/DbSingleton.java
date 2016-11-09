package LOG515.lab06;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DbSingleton {

	private static Connection dbConnection = null;
	private static String dbUrl = "jdbc:mysql://" + Utils.getProperty(Utils.DBHOST) + ":" + Utils.getProperty(Utils.DBPORT) + "/" + Utils.getProperty(Utils.DBNAME);
	
	public static Connection getDbConnection(){
		try {
			if(dbConnection == null){
				dbConnection = DriverManager.getConnection(dbUrl, Utils.getProperty(Utils.DBUSER), Utils.getProperty(Utils.DBPASS));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbConnection;
	}
}