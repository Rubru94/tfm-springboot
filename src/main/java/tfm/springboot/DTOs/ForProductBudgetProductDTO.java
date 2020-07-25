package tfm.springboot.DTOs;

import java.util.Date;

public class ForProductBudgetProductDTO {
    
    private BasicBudgetDTO budget;

	private Date regDate = new Date();

    public ForProductBudgetProductDTO() {
    	
    }

    public BasicBudgetDTO getBudget() {
		return budget;
	}

	public void setBudget(BasicBudgetDTO budget) {
		this.budget = budget;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
