package tfm.springboot.model.industries;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Logistic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	public Logistic() {

	}

}