package tfm.springboot.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import tfm.springboot.Customer;

@SpringBootTest
public class ControllerUnitTest {

	@Test
	public void createPost() throws Exception {

		final String NAME = "CustomerTest";

		Customer customer = new Customer(NAME, 50);
		assertEquals(customer.getName(), NAME);
	}

}
