package LOG515.lab06;

public class PropertyPOJO {
	public String getIdProperty() {
		return idProperty;
	}
	public void setIdProperty(String idProperty) {
		this.idProperty = idProperty;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalcode) {
		this.postalCode = postalcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNbAppartments() {
		return nbAppartments;
	}
	public void setNbAppartments(String nbAppartments) {
		this.nbAppartments = nbAppartments;
	}
	
	public PropertyPOJO(String address, String postalCode, String description, String nbapparts) {
		super();
		this.address = address;
		this.postalCode = postalCode;
		this.description = description;
		this.nbAppartments = nbapparts;
	}

	public PropertyPOJO(String idProperty, String address, String postalCode, String description, String nbAppartments) {
		super();
		this.idProperty = idProperty;
		this.address = address;
		this.postalCode = postalCode;
		this.description = description;
		this.nbAppartments = nbAppartments;
	}
	
	public PropertyPOJO(){
		
	}

	private String idProperty;
	private String address;
	private String postalCode;
	private String description;
	private String nbAppartments;
	
	
}
