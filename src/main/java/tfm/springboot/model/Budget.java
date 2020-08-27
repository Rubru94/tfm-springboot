package tfm.springboot.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Budget {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private Date date = new Date();
	private double total;

	@ManyToOne
	private Customer customer;

	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetProduct> products = new ArrayList<>();

	public Budget() {

	}

	public Budget(Date date, Customer customer) {
		super();
		this.date = date;
		this.customer = customer;
	}

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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<BudgetProduct> getProducts() {
		return products;
	}

	public void setProducts(List<BudgetProduct> products) {
		this.products = products;
	}

	public void addProduct(BudgetProduct product) {
		this.products.add(product);
		this.total += product.getProduct().getExpenseHours();
	}

	public void removeProduct(int position) {
		this.products.remove(position);
	}

	@Override
	public String toString() {
		return "Budget [date=" + date + ", total=" + total + ", customer=" + customer + ", products=" + products + "]";
	}
}
