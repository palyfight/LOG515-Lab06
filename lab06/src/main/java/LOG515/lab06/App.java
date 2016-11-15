package LOG515.lab06;

import static spark.Spark.*;
import LOG515.lab06.AuthenticationServices;

public class App {
    public static void main( String[] args )
    {
    	/*before(new Filter(){
    		public void handle(Request req, Response res){
    			String username = req.params(":usernae");
    			String password = req.params(":password");
    			
    			if(!AuthenticationServices.canUserLogIn(username, password)){
    				halt(401, "Go away you hax3r!!!");
    			}
    		}
    	});*/
    	
    	get("/hello", (req, res) -> "Hello world!");
        post("/login/:username/:password", (req, res) -> {/*res.redirect("/new/route");*/ return AuthenticationServices.login(req, res);});
        
        before("/logout/:username", (req, res) -> {
   String username = req.params(":username");
   
   if(AuthenticationServices.canUserLogIn(username)){
    halt(401, "Go away you hax3r!!!");
   }
        });
        post("/logout/:username", (req, res) -> {return AuthenticationServices.logout(req, res);});
    }
}
