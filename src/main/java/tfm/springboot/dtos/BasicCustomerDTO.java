package tfm.springboot.dtos;

public class BasicCustomerDTO {
	
	private long id;

	private String name;
	private String lastname;
	private String email;
	
	private BasicCompanyDTO company;
	
	public BasicCustomerDTO() {

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
	
}
