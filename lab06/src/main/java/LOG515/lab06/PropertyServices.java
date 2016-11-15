package LOG515.lab06;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.Statement;

import spark.Request;
import spark.Response;

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

	public static JSONArray getProperties(Request req, Response resp) throws JSONException{
		Gson gson = new Gson();
		String data = gson.toJson(fetchProperties(),new TypeToken<ArrayList<PropertyPOJO>>() {}.getType());
		return new JSONArray(data);
	}

	public static JSONArray getPropertiesByUser(Request req, Response resp) throws JSONException{
		int id = Integer.parseInt(req.params(":userid"));
		String role = req.params(":role");
		Gson gson = new Gson();
		String data = gson.toJson(fetchPropertiesById(id, role),new TypeToken<ArrayList<PropertyPOJO>>() {}.getType());
		return new JSONArray(data);
	}
	
	public static String claimProperty(Request req, Response resp){
		int userid = Integer.parseInt(req.params(":userid"));
		int propertyid = Integer.parseInt(req.params(":propertyid"));

		int id = insertPropertiesUser(userid, propertyid);
		
		if(id != 0){
			resp.body("id:"+id);
			resp.status(200);
			return "id:"+id;
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

	private static ArrayList<PropertyPOJO> fetchProperties(){
		String query = "SELECT DISTINCT * FROM properties";
		try {
			PreparedStatement stmnt  = DbSingleton.getDbConnection().prepareStatement(query);
			ResultSet rs = stmnt.executeQuery();
			ArrayList<PropertyPOJO> properties = new ArrayList<PropertyPOJO>();
			while(rs.next()){
				properties.add(new PropertyPOJO(
						rs.getInt("id"),
						rs.getString("address"),
						rs.getString("postalcode"),
						rs.getString("description"),
						rs.getString("nbapparts")
						));
			}
			return properties;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	private static ArrayList<PropertyPOJO> fetchPropertiesById(int id, String role){
		String query = "SELECT DISTINCT p.* FROM properties as p JOIN property_user as pu ON p.id = pu.propertyid JOIN users ON users.id = pu.userid WHERE users.id = ? AND users.role = ?";

		try {
			PreparedStatement stmnt  = DbSingleton.getDbConnection().prepareStatement(query);
			stmnt.setInt(1, id);
			stmnt.setString(2, role);
			ResultSet rs = stmnt.executeQuery();
			ArrayList<PropertyPOJO> properties = new ArrayList<PropertyPOJO>();
			while(rs.next()){
				properties.add(new PropertyPOJO(
						rs.getInt("id"),
						rs.getString("address"),
						rs.getString("postalcode"),
						rs.getString("description"),
						rs.getString("nbapparts")
						));
			}
			return properties;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int insertPropertiesUser(int userid, int propertyid) {
		String query = "INSERT INTO property_user(propertyid, userid) VALUES (?,?)";
		try {
			PreparedStatement stmnt  = DbSingleton.getDbConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmnt.setInt(1, propertyid);
			stmnt.setInt(2, userid);
			int rs = stmnt.executeUpdate();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}


}
