package LOG515.lab06;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import spark.Request;
import spark.Response;

public class PropertyServices {
	
	public static String addProperty(Request req, Response resp) {
		String address = req.params(":address");
		String postalcode = req.params(":postalcode");
		String description = req.params(":description");
		String nbapparts = req.params(":nbapparts");
		
		int propertyId = saveProperty(address, postalcode, description, nbapparts);
		
		if(propertyId != 0){
			//setToken(username, true);
			//System.out.println("YOLO => " + new Gson().toJson(user));
			resp.body("id:"+propertyId);
			resp.status(200);
			return "id:"+propertyId;
		}
		resp.body("Could not log in");
		resp.status(404);
		return "Could not log in";
	}
	
	private static int saveProperty(String address, String postalCode, String description, String nbapparts){
		int propertyId = 0;
		String query = "INSERT INTO properties (address, postalcode, description, nbapparts) VALUES (?,?,?,?)";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setString(1, address);
			stmnt.setString(2, postalCode);
			stmnt.setString(3, description);
			stmnt.setString(4, nbapparts);
			//System.out.println(stmnt.toString());
			int rs = stmnt.executeUpdate();
			if( rs == 0){
				throw new SQLException("Adding property with address: " + address + " failed!");
			}
	        try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	propertyId = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyId;
	}

}
