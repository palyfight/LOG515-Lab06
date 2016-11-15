package LOG515.lab06;

import static spark.Spark.*;
import LOG515.lab06.AuthenticationServices;
import spark.Spark;

public class App {
    public static void main( String[] args )
    {	
    	port(1738);
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

    	Spark.before((request, response) -> {response.header("Access-Control-Allow-Origin", "*");});
    
    	get("/hello", (req, res) -> "Hello world!");
        post("/login/:username/:password", (req, res) -> {return AuthenticationServices.login(req, res);});
        
        before("/logout/:username", (req, res) -> {
   String username = req.params(":username");
   
   if(AuthenticationServices.canUserLogIn(username)){
    halt(401, "Go away you hax3r!!!");
   }
        });
        post("/logout/:username", (req, res) -> {return AuthenticationServices.logout(req, res);});
        post("/property/:address/:postalcode/:description/:nbapparts", (req, res) -> {return PropertyServices.addProperty(req, res);});
    }
}
