package tfm.springboot.DTOs;

import java.util.ArrayList;
import java.util.List;

public class FullProductDTO {

	private long id;

	private String code;
	private String name;
	private String description;
	private int expenseHours;

	private List<ForProductBudgetProductDTO> budgets = new ArrayList<>();

	public FullProductDTO() {

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
	
	public List<ForProductBudgetProductDTO> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<ForProductBudgetProductDTO> budgets) {
		this.budgets = budgets;
	}
}
