package LOG515.lab06;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;

public class AuthenticationServices {
	
	public static String login(Request req, Response resp) {
		String username = req.params(":username");
		String password = req.params(":password");
		
		UserPOJO user = getUser(username, password);
		if(user != null /*canUserLogIn(username,password)*/){
			setToken(username, true);
			System.out.println("YOLO => " + new Gson().toJson(user));
			resp.body(new Gson().toJson(user));
			resp.status(200);
			return resp.body();
		}
		resp.body("Could not log in");
		resp.status(404);
		return "Could not log in";
	}
	
	private static UserPOJO getUser(String uname, String pwd){
		UserPOJO user = new UserPOJO();
		String query = "SELECT id, username, token, phone, role FROM users WHERE username = ? AND password = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setString(1, uname);
			stmnt.setString(2, pwd);
			//System.out.println(stmnt.toString());
			ResultSet rs = stmnt.executeQuery();
			if(rs.next()){
				user.setUsername(rs.getString("username"));
				user.setPhone(rs.getString("phone"));
				user.setRole(rs.getString("role"));
				user.setToken(rs.getBoolean("token"));
				user.setUserId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
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
	
	/*private static boolean canUserLogIn(String username, String password) {
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
	}*/
	
	private static boolean setToken(String username, boolean token) {
		String query = "UPDATE users SET token = ? WHERE username = ?";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setBoolean(1, token);
			stmnt.setString(2, username);
			System.out.println(stmnt.toString());
			int rs = stmnt.executeUpdate();
			return rs == 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
