package tfm.springboot.DTOs;

import java.util.ArrayList;
import java.util.List;

public class FullCompanyDTO {

	private long id;

	private String vatregnumber;
	private String name;
	private String country;
	private String industry;
	private List<BasicCustomerDTO> customers = new ArrayList<>();

	public FullCompanyDTO() {

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

	public List<BasicCustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<BasicCustomerDTO> customers) {
		this.customers = customers;
	}

}
