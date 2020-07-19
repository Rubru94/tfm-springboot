package tfm.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String lastname;
	private String email;

	@OneToOne
	private Company company;

	public Customer() {

	}

	public Customer(String name, String lastname, String email) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.email = email;
	}

	public Customer(String name, String lastname, String email, Company company) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.company = company;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", lastname=" + lastname + ", email= " + email + ", company=" + company + "]";
	}
}
