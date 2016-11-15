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
	public String getPostalcode() {
		return postalCode;
	}
	public void setPostalcode(String postalcode) {
		this.postalCode = postalcode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNbapparts() {
		return nbAppartments;
	}
	public void setNbapparts(String nbapparts) {
		this.nbAppartments = nbapparts;
	}
	
	public PropertyPOJO(String address, String postalcode, String description, String nbapparts) {
		super();
		this.address = address;
		this.postalCode = postalcode;
		this.description = description;
		this.nbAppartments = nbapparts;
	}

	public PropertyPOJO(String idProperty, String address, String postalcode, String description, String nbapparts) {
		super();
		this.idProperty = idProperty;
		this.address = address;
		this.postalCode = postalcode;
		this.description = description;
		this.nbAppartments = nbapparts;
	}
	
	public PropertyPOJO(){
		
	}

	private String idProperty;
	private String address;
	private String postalCode;
	private String description;
	private String nbAppartments;
	
	
}
