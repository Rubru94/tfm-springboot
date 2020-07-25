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
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String code;
	private String name;
	private String description;
	private int expenseHours;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BudgetProduct> budgets = new ArrayList<>();

	public Product() {
		
	}
	
	public Product(String code, String name, String description, int expenseHours) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.expenseHours = expenseHours;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getExpenseHours() {
		return expenseHours;
	}

	public void setExpenseHours(int expenseHours) {
		this.expenseHours = expenseHours;
	}

	public List<BudgetProduct> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<BudgetProduct> budgets) {
		this.budgets = budgets;
	}
	
	public void addBudget(BudgetProduct budget) {
		this.budgets.add(budget);
	}

	public void removeCustomer(int position) {
		this.budgets.remove(position);
	}

	@Override
	public String toString() {
		return "Product [code=" + code + ", name=" + name + ", description=" + description + ", expenseHours="
				+ expenseHours + ", budgets=" + budgets + "]";
	}
}
