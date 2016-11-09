package LOG515.lab06;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class DbSingleton {

	private static Connection dbConnection = null;
	
	public static Connection getDbConnection(){
		try {
			if(dbConnection == null){
				dbConnection = DriverManager.getConnection(Utils.DBHOST, Utils.DBUSER, Utils.DBPASS);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbConnection;
	}
}