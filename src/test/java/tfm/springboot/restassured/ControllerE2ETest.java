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
import tfm.springboot.Customer;

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

		final String NAME = "CustomerTest";
		final int AGE = 50;
		
		AtomicLong lastPostId = new AtomicLong();
		Customer customer = new Customer(NAME, 50);
		customer.setId(lastPostId.incrementAndGet());
				
		given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(customer)).
		when().
			post("/customer").
		then().
			statusCode(201).
			body("name", equalTo(NAME)).
			body("age", equalTo(AGE));
	}
	
}
