package LOG515.lab06;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class AuthenticationServices {

	public static String login(Request req, Response resp) {
		String username = new Gson().fromJson(req.body(), JsonObject.class).get("username").toString();
		String password = new Gson().fromJson(req.body(), JsonObject.class).get("password").toString();
		
		//check in database if user exists 
		resp.status(200);
		return "Successfully logged in";
	}

	public static String logout(Request req, Response resp) {
		String username = new Gson().fromJson(req.body(), JsonObject.class).get("username").toString();
		return "";
	}
	
	private boolean canUserLogIn(String username, String password){
		String query = "SELECT token FROM users WHERE username = ? AND password = ?";
		PreparedStatement stmnt = 
		return false;
	}
}