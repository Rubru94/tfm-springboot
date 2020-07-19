package tfm.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import tfm.springboot.model.industries.Logistic;
import tfm.springboot.model.industries.Services;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String name;
	private String country;
	private String industry;

	@OneToOne
	private Logistic logistic;

	@OneToOne
	private Services services;

	private int employees;

	public Company() {

	}

	public Company(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}

	public Company(String name, String country, String industry) {
		super();
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

	public int getEmployees() {
		return employees;
	}

	public void setEmployees(int employees) {
		this.employees = employees;
	}
	
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Logistic getLogistic() {
		return logistic;
	}

	public void setLogistic(Logistic logistic) {
		this.logistic = logistic;
	}

	public Services getService() {
		return services;
	}

	public void setService(Services services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "Company [id= " + id + " name=" + name + ", country=" + country + ", employees=" + employees + "]";
	}

}
