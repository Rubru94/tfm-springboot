package tfm.springboot.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import tfm.springboot.model.Customer;

@SpringBootTest
public class ControllerUnitTest {

	@Test
	public void createPost() throws Exception {

		final String NAME = "TestName";
		final String LASTNAME = "TestLastname";

		Customer customer = new Customer(NAME, LASTNAME, null);
		assertEquals(customer.getName(), NAME);
	}

}
