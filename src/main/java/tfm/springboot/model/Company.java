package tfm.springboot.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String vatregnumber;
	private String name;
	private String country;
	private String industry;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "company")
	private List<Customer> customers = new ArrayList<>();

	public Company() {

	}

	public Company(String vatregnumber, String name, String country) {
		super();
		this.vatregnumber = vatregnumber;
		this.name = name;
		this.country = country;
	}

	public Company(String vatregnumber, String name, String country, String industry) {
		super();
		this.vatregnumber = vatregnumber;
		this.name = name;
		this.country = country;
		this.industry = industry;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getVatregnumber() {
		return vatregnumber;
	}

	public void setVatregnumber(String vatregnumber) {
		this.vatregnumber = vatregnumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}

	public void removeCustomer(int position) {
		this.customers.remove(position);
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public String toString() {
		return "Company [id= " + id + " name=" + name + ", country=" + country + ", industry=" + industry + "]";
	}

}
