package tfm.springboot.model;

import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class BudgetProduct {

    @EmbeddedId
    private BudgetProductId id;

	@ManyToOne
    @MapsId("budId")
    private Budget budget;

    @ManyToOne
    @MapsId("prodId")
    private Product product;

	private Date regDate = new Date();

    public BudgetProduct() {
    	
    }

    public BudgetProduct(Budget budget, Product product, Date regDate) {
        this.budget = budget;
        this.product = product;
        this.regDate = regDate;
        this.id = new BudgetProductId(budget.getId(), product.getId());
    }

    public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
