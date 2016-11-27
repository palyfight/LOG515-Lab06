package LOG515.lab06;

import static spark.Spark.*;
import LOG515.lab06.AuthenticationServices;
import spark.Spark;

public class App {
	public static void main(String[] args) {        
		port(1738);
		
		staticFileLocation("../log515-frontend"); //path to primary index.html
		webSocket("/chat", ChatWebSocketHandler.class);
        init();
        
		Spark.options("/*", (request, response) -> {

			String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
			if (accessControlRequestHeaders != null) {
				response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
			}

			String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
			if (accessControlRequestMethod != null) {
				response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
			}
			return "OK";
		});

		Spark.before((request, response) -> {
			response.header("Access-Control-Allow-Origin", "*");
		});

		get("/hello", (req, res) -> "Hello world!");
		
		post("/login/:username/:password", (req, res) -> {
			return AuthenticationServices.login(req, res);
		});
		
		before("/logout/:username", (req, res) -> {
			String username = req.params(":username");

			if (AuthenticationServices.canUserLogIn(username)) {
				halt(401, "Go away you hax3r!!!");
			}
		});
		post("/logout/:username", (req, res) -> {
			return AuthenticationServices.logout(req, res);
		});
		
		post("/property/save", (req, res) -> {
			return PropertyServices.addProperty(req, res);
		});
		
		get("/properties", (req, res) -> {
			return PropertyServices.getProperties(req, res);
		});
		
		get("/user/:userid/:role/property", (req, res) -> {
			return PropertyServices.getPropertiesByUser(req, res);
		});
		
		get("/property/:userid/:propertyid/claim", (req, res) -> {
			return PropertyServices.claimProperty(req, res);
		});
		
		get("/property/:userid/:propertyid/unclaim", (req, res) -> {
			return PropertyServices.unclaimProperty(req, res);
		});
		
		post("/signup/:username/:password/:phone/:role/:email", (req, res) -> {
			return AuthenticationServices.signup(req, res);
		});
		
		post("/android/login", (req, res) -> {
			System.out.println("HELLO WORD");
			return AuthenticationServices.androidLogin(req, res);
		});
		
		post("/android/register", (req, res) -> {
			System.out.println("YOLLO IS THE MOTTO");
			return AuthenticationServices.androidRegister(req, res);
		});
		
		post("/android/verify", (req, res) -> {
			System.out.println("SOLO DOLO");
			return AuthenticationServices.androidVerify(req, res);
		});
		
		post("/property/verify", (req, res) -> {
			System.out.println("SOLO DOLO");
			return AuthenticationServices.androidVerify(req, res);
		});
		
		get("/property/:id", (req, res) -> {
			return PropertyServices.getPropertyById(req, res);
		});
		
		get("/users", (req, res) -> {
			return PropertyServices.getUsers(req, res);
		});
	}
}
