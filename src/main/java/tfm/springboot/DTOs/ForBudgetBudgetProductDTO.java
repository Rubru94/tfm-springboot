package tfm.springboot.DTOs;

import java.util.Date;

public class ForBudgetBudgetProductDTO {
    
    private BasicProductDTO product;

	private Date regDate = new Date();

    public ForBudgetBudgetProductDTO() {
    	
    }

	public BasicProductDTO getProduct() {
		return product;
	}

	public void setProduct(BasicProductDTO product) {
		this.product = product;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
