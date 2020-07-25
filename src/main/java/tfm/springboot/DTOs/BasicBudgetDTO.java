package tfm.springboot.DTOs;

import java.util.Date;

public class BasicBudgetDTO {

	private long id;

	private Date date = new Date();
	private double total;

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
}
