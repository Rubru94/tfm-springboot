package tfm.springboot.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class BudgetProductId implements Serializable {

	private Long budId;
	private Long prodId;

	public BudgetProductId() { }

	public BudgetProductId(Long budId, Long prodId) {
        this.budId = budId;
        this.prodId = prodId;
    }

	public Long getBudId() {
		return budId;
	}

	public void setBudId(Long budId) {
		this.budId = budId;
	}

	public Long getProyId() {
		return prodId;
	}

	public void setProyId(Long prodId) {
		this.prodId = prodId;
	}
}
