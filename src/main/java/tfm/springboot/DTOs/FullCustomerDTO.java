package tfm.springboot.DTOs;

import java.util.ArrayList;
import java.util.List;

public class FullCustomerDTO {
	
	private long id;

	private String name;
	private String lastname;
	private String email;

	private BasicCompanyDTO company;
	
	private List<BasicBudgetDTO> budgets = new ArrayList<>(); 

	public FullCustomerDTO() {

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

	public BasicCompanyDTO getCompany() {
		return company;
	}

	public void setCompany(BasicCompanyDTO company) {
		this.company = company;
	}
	
	public List<BasicBudgetDTO> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<BasicBudgetDTO> budgets) {
		this.budgets = budgets;
	}
	
	public void addBudget(BasicBudgetDTO budget) {
		this.budgets.add(budget);
	}
}
