package LOG515.lab06;
import static spark.Spark.*;

public class App 
{
    public static void main( String[] args )
    {
        get("/hello", (req, res) -> "Hello world!");
        post("/login", (req, res) -> {return AuthenticationServices.login(req, res);});
        post("/logout", (req, res) -> {return AuthenticationServices.logout(req, res);});
    }
}
