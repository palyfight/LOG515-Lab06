package LOG515.lab06;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import spark.Request;
import spark.Response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.Statement;

public class PropertyServices {
	
	public static String addProperty(Request req, Response resp) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		PropertyPOJO property = mapper.readValue(req.body(), PropertyPOJO.class);
		
		int propertyId = saveProperty(property);
		
		if(propertyId != 0){
			resp.body("id:"+propertyId);
			resp.status(200);
			return "id:"+propertyId;
		}
		resp.body("Could not save property");
		resp.status(404);
		return "Could not save property";
	}
	
	private static int saveProperty(PropertyPOJO property){
		int propertyId = 0;
		String query = "INSERT INTO properties (address, postalcode, description, nbapparts) VALUES (?,?,?,?)";
		try {
			PreparedStatement stmnt = DbSingleton.getDbConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setString(1, property.getAddress());
			stmnt.setString(2, property.getPostalCode());
			stmnt.setString(3, property.getDescription());
			stmnt.setString(4, property.getNbAppartments());

			int rs = stmnt.executeUpdate();
			if( rs == 0){
				throw new SQLException("Adding property with address: " + property.getAddress() + " failed!");
			}
	        try (ResultSet generatedKeys = stmnt.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	            	propertyId = generatedKeys.getInt(1);
	            }
	            else {
	                throw new SQLException("Adding property failed, no ID obtained.");
	            }
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propertyId;
	}

}
