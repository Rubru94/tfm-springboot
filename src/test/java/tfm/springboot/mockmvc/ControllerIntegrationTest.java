package tfm.springboot.mockmvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import tfm.springboot.Customer;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Order(1)
	public void createCustomer() throws Exception {

		final String NAME = "CustomerTest";
		final int AGE = 50;

		AtomicLong lastCustomerId = new AtomicLong();
		Customer customer = new Customer(NAME, AGE);
		customer.setId(lastCustomerId.incrementAndGet());
		
		System.out.println(customer.getId());

		mvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(customer))).andExpect(status().isCreated());
	}

}
