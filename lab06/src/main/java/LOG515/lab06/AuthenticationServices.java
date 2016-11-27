package LOG515.lab06;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mysql.jdbc.Statement;

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
	
	public static JSONObject androidLogin(Request req, Response resp) {
		String phone = "";
		String password = "";
		
		try {
			JSONObject obj1 = new JSONObject(req.body());
			phone = obj1.getString("phone");
			password = obj1.getString("password");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("phone => " + phone);
		System.out.println("password => " + password);
		
		JSONObject obj = new JSONObject();
		try {
			if(phone.equals("5147467719") && password.equals("1234")){
				obj.put("success", true);
			}
			else{
				obj.put("success", false);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	public static JSONObject androidRegister(Request req, Response resp) {
		String phone_number = "";
		String country_code = "";
		
		try {
			JSONObject obj1 = new JSONObject(req.body());
			phone_number = obj1.getString("phone_number");
			country_code = obj1.getString("country_code");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("phone_number => " + phone_number);
		System.out.println("country_code => " + country_code);
		
		JSONObject obj = new JSONObject();
		try {
			if(phone_number.equals("5141234567") && country_code.equals("1")){
				obj.put("success", true);
			}
			else{
				obj.put("success", false);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	public static JSONObject androidVerify(Request req, Response resp) {
		String verification_code = "";
		
		try {
			JSONObject obj1 = new JSONObject(req.body());
			verification_code = obj1.getString("verification_code");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("verification_code => " + verification_code);
		
		JSONObject obj = new JSONObject();
		try {
			if(verification_code.equals("12345")){
				obj.put("success", true);
			}
			else{
				obj.put("success", false);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
	
	private static UserPOJO getUser(String uname, String pwd){
		UserPOJO user = new UserPOJO();
		String query = "SELECT id, username, token, phone, role, email FROM users WHERE username = ? AND password = ?";
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
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	
	public static String signup(Request req, Response resp){
		String username = req.params(":username");
		String password = req.params(":password");
		String phone = req.params(":phone");
		String role = req.params(":role");
		String email = req.params(":email");
		
		UserPOJO user = new UserPOJO(username, phone, role, true, email);
		if(saveUser(user, password) != -1){
			resp.body("Successfully added new user");
			resp.status(200);
			return "Successfuly added new user";
		}
		resp.body("Could not add new user :(");
		resp.status(404);
		return "Could not add new user :(";
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

	private static int saveUser(UserPOJO user, String password){
		int userId = -1;
		String query = "INSERT INTO users (username, password, token, phone, role, email) VALUES (?,?,?,?,?,?)";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setString(1, user.getUsername());
			stmnt.setString(2, password);
			stmnt.setBoolean(3, user.getToken());
			stmnt.setString(4, user.getPhone());
			stmnt.setString(5, user.getRole());
			stmnt.setString(6, user.getEmail());

			int rs = stmnt.executeUpdate();
			if( rs == 0){
				throw new SQLException("Adding property with address: " + user.getUsername() + " failed!");
			}
			try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					userId = generatedKeys.getInt(1);
				}
				else {
					throw new SQLException("Adding property failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userId;
	}
	
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
