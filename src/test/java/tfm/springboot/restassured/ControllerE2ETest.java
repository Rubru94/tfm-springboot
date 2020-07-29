package tfm.springboot.restassured;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import tfm.springboot.DTOs.BasicBudgetDTO;
import tfm.springboot.DTOs.ForBudgetBudgetProductDTO;
import tfm.springboot.DTOs.FullBudgetDTO;
import tfm.springboot.DTOs.FullCustomerDTO;
import tfm.springboot.model.Budget;
import tfm.springboot.model.BudgetProduct;
import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.model.Product;
import tfm.springboot.service.BudgetProductService;
import tfm.springboot.service.BudgetService;
import tfm.springboot.service.CustomerService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerE2ETest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private BudgetProductService budgetProductService;
	
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
	
	@Test
	public void addCustomerBudget() throws Exception {

		Customer customer = new Customer("Bruce", "Wayne", "bruwa@batman.com");
		
		Product p1 = new Product("INVENTORY", "Inventory management", "Inventory management product", 15);
		Product p2 = new Product("ECOMMERCE", "Electronic commerce", "Electronic commerce product", 30);

		List<Product> products = new ArrayList<>();
		products.add(p1); products.add(p2);

		Budget budget = new Budget(new Date(), customer);

		ForBudgetBudgetProductDTO forBudgetBudgetProductDTO;

		FullBudgetDTO fullBudgetDTO = this.budgetService.convertToFullBudgetDTO(budget);

		for (Product p : products) {

			forBudgetBudgetProductDTO = this.budgetProductService
					.convertToForBudgetBudgetProductDTO(new BudgetProduct(budget, p, new Date()));

			fullBudgetDTO.addProduct(forBudgetBudgetProductDTO);
		}

		budget = this.budgetService.convertFullBudgetDtoToEntity(fullBudgetDTO);
		BasicBudgetDTO basicBudgetDTO = this.budgetService.convertToBasicBudgetDTO(budget);

		FullCustomerDTO fullCustomerDTO = this.customerService.convertToFullCustomerDTO(customer);
		fullCustomerDTO.addBudget(basicBudgetDTO);
			
		//Given
		Response response = given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(fullCustomerDTO)).
		when().
			post("/api/customer").andReturn();
		
		int customerId = from(response.getBody().asString()).get("id");
		
		//Given
		given().
			contentType("application/json").
			body(objectMapper.writeValueAsString(basicBudgetDTO)).
		//When
		when().
			post("/api/customer/{customerId}/budget", customerId).
		//Then	
		then().
			statusCode(201).
			body("id", equalTo((int)basicBudgetDTO.getId()));		
				
	}
	
}
