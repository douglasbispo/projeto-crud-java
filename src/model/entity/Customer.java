package model.entity;

public class Customer {
	private long code;
	private String neighborhood;
	private String street;
	private String city;
	private Integer number;
	
	public Customer() {}
	
	public Customer(String neighborhood, String street, String city, Integer number) {
		this.neighborhood = neighborhood;
		this.street = street;
		this.city = city;
		this.number = number;
	}

	public Customer(long code, String neighborhood, String street, String city, Integer number) {
		this.code = code;
		this.neighborhood = neighborhood;
		this.street = street;
		this.city = city;
		this.number = number;
	}

	public long getCode() {
		return code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "\nENDEREÇO" +
				"\nBairro: " + this.getNeighborhood() + 
				"\nRua: " + this.getStreet() + 
				"\nCidade: " + this.getStreet() + 
				"\nNumero: " + this.getNumber() +
				"\n--------------------------------------------\n";
	}
	
	
}
