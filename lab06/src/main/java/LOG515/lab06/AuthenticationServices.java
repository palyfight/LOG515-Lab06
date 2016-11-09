package LOG515.lab06;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class AuthenticationServices {

	public static String login(Request req, Response resp) {
		String username = new Gson().fromJson(req.body(), JsonObject.class).get("username").toString();
		String password = new Gson().fromJson(req.body(), JsonObject.class).get("password").toString();
		
		if(canUserLogIn(username,password)){
			resp.body("Successfully logged in");
			resp.status(200);
			return "Successfully logged in";
		}
		resp.body("Could not log in");
		resp.status(404);
		return "Could not log in";
	}

	public static String logout(Request req, Response resp) {
		String username = new Gson().fromJson(req.body(), JsonObject.class).get("username").toString();
		setToken(username, false);
		return "";
	}
	
	private static boolean canUserLogIn(String username, String password){
		String query = "SELECT token FROM users WHERE username = ? AND password = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setString(1, username);
			stmnt.setString(2, password);
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				setToken(username, true);
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private static void setToken(String username, boolean token){
		String query = "UPDATE users SET token = ? WHERE username = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setBoolean(1, token);
			stmnt.setString(2, username);
			ResultSet rs = stmnt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}