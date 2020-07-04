package tfm.springboot.restassured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import tfm.springboot.model.Customer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerE2ETest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@LocalServerPort
    int port;
	
	@BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

	@Test
	public void createCustomer() throws Exception {

		final String NAME = "TestName";
		final String LASTNAME = "TestLastname";
		
		AtomicLong lastCustomerId = new AtomicLong();
		Customer customer = new Customer(NAME, LASTNAME);
		customer.setId(lastCustomerId.incrementAndGet());
				
		given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(customer)).
		when().
			post("/api/customer").
		then().
			statusCode(201).
			body("name", equalTo(NAME)).
			body("lastname", equalTo(LASTNAME));
	}
	
}
