package tfm.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String lastname;
	private String phone;
	private String email;
	private String company;

	public Customer() {

	}
	
	public Customer(String name, String lastname) {
		super();
		this.name = name;
		this.lastname = lastname;
	}

	public Customer(String name, String lastname, String phone, String email, String company) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", lastname=" + lastname + 
				", phone=" + phone + ", email= " + email + 
				", company=" + company + "]";
	}
}
