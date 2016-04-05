package com.egen.mongo_spark.mongo_spark;

import org.bson.Document;

public class UserModel{
	private String id;
	private String firstName;
	private String lastName;
	private String email;
	private String dateCreated;
	private String profilePic;
	private Address address;
	private Company company;
	
	
	public UserModel(Document doc){
		this.id = doc.getString("_id");
		this.firstName = doc.getString("firstName").toString();
		this.lastName = doc.getString("lastName").toString();
		this.email = doc.getString("email");
		this.dateCreated = doc.getString("dateCreated");
		this.profilePic = doc.getString("profilePic");
		this.address = new Address();
		this.address.city = ((Document) doc.get("address")).getString("city");
		this.address.state = ((Document) doc.get("address")).getString("state");
		this.address.country = ((Document) doc.get("address")).getString("country");
		this.address.street = ((Document) doc.get("address")).getString("street");
		this.address.zip = ((Document) doc.get("address")).getString("zip");
		this.company = new Company();
		this.company.name = ((Document) doc.get("company")).getString("name");
		this.company.website = ((Document) doc.get("company")).getString("website");
		
	}
	
	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}
	/**
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}
	@Override
	public String toString(){
		return this.profilePic;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the dateCreated
	 */
	public String getDateCreated() {
		return dateCreated;
	}

	/**
	 * @return the profilePic
	 */
	public String getProfilePic() {
		return profilePic;
	}
	
	
	public class Address {
		String street;
		String city;
		String state;
		String country;
		String zip;	
		public Address() {
			
		}
		/**
		 * @return the street
		 */
		public String getStreet() {
			return street;
		}
		/**
		 * @param street the street to set
		 */
		public void setStreet(String street) {
			this.street = street;
		}
		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}
		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}
		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}
		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}
		/**
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}
		/**
		 * @param country the country to set
		 */
		public void setCountry(String country) {
			this.country = country;
		}
		/**
		 * @return the zip
		 */
		public String getZip() {
			return zip;
		}
		/**
		 * @param zip the zip to set
		 */
		public void setZip(String zip) {
			this.zip = zip;
		}
	}
	
	public class Company {
		
		String name;
		String website;
		
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the website
		 */
		public String getWebsite() {
			return website;
		}
		/**
		 * @param website the website to set
		 */
		public void setWebsite(String website) {
			this.website = website;
		}

	}

}
