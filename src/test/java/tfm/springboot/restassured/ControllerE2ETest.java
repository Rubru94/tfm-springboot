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
import static io.restassured.path.json.JsonPath.from;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tfm.springboot.model.Company;
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
		
		Customer customer = new Customer("TestName", "TestLastname", "test@mail.com");
				
		given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(customer)).
		when().
			post("/api/customer").
		then().
			statusCode(201).
			body("name", equalTo(customer.getName())).
			body("lastname", equalTo(customer.getLastname()));
	}
	
	@Test
	public void setCompanyToCustomer() throws Exception {

		Customer customer = new Customer("Bruce", "Wayne", "bruwa@batman.com");
		Company company = new Company("B00564584", "Wayne Industries", "USA", "Logistic");
			
		//Given
		Response response = given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(customer)).
		when().
			post("/api/customer").andReturn();
		
		int customerId = from(response.getBody().asString()).get("id");
		
		//Given
		given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(company)).
		//When
		when().
			post("/api/customer/{customerId}/company", customerId).
		//Then	
		then().
			statusCode(201).
			body("vatregnumber", equalTo(company.getVatregnumber())).
			body("name", equalTo(company.getName()));
	}
	
}
