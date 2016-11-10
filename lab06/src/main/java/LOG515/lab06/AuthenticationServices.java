package LOG515.lab06;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import spark.Request;
import spark.Response;

public class AuthenticationServices {
	
	public static String login(Request req, Response resp) {
		String username = req.params(":username");
		String password = req.params(":password");
		
		if(canUserLogIn(username,password)){
			setToken(username, true);
			resp.body("Successfully logged in");
			resp.status(200);
			return "Successfully logged in";
		}
		resp.body("Could not log in");
		resp.status(404);
		return "Could not log in";
	}

	public static String logout(Request req, Response resp) {
		String username = req.params(":username");
		if(setToken(username, false)){
			resp.body("Successfully logged out");
			resp.status(200);
			return "Successfuly logged out";
		};
		resp.body("Could not log you out!!!");
		resp.status(404);
		return "Could not log you out";
	}
	
	public static boolean canUserLogIn(String username){
		String query = "SELECT token FROM users WHERE username = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setString(1, username);
			System.out.println(stmnt.toString());
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}
	
	private static boolean canUserLogIn(String username, String password) {
		String query = "SELECT token FROM users WHERE username = ? AND password = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setString(1, username);
			stmnt.setString(2, password);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static boolean setToken(String username, boolean token) {
		String query = "UPDATE users SET token = ? WHERE username = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setBoolean(1, token);
			stmnt.setString(2, username);
			System.out.println(stmnt.toString());
			@SuppressWarnings("unused")
			int rs = stmnt.executeUpdate();
			return rs == 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
