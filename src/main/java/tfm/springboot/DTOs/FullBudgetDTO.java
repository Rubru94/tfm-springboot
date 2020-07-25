package tfm.springboot.DTOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FullBudgetDTO {

	private long id;

	private Date date = new Date();
	private double total;
	private BasicCustomerDTO customer;
	private List<ForBudgetBudgetProductDTO> products = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public BasicCustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(BasicCustomerDTO customer) {
		this.customer = customer;
	}

	public List<ForBudgetBudgetProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ForBudgetBudgetProductDTO> products) {
		this.products = products;
	}
}
