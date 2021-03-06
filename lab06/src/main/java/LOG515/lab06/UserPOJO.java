package LOG515.lab06;

public class UserPOJO {
	String username;
	int userId;
	boolean token;
	String phone;
	String role;
	String email;

	UserPOJO(){}
	
	UserPOJO(String username, String phone, String role, boolean token, String email){
		this.username = username;
		this.phone = phone;
		this.role = role;
		this.token = token;
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean getToken() {
		return token;
	}
	public void setToken(boolean token) {
		this.token = token;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
