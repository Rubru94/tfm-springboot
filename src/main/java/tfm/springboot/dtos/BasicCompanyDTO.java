package tfm.springboot.dtos;

public class BasicCompanyDTO {

	private long id;

	private String vatregnumber;
	private String name;
	private String country;
	private String industry;

	public BasicCompanyDTO() {

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
}
