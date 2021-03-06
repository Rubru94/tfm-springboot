package tfm.springboot.mockmvc;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import tfm.springboot.dtos.BasicBudgetDTO;
import tfm.springboot.dtos.BasicCompanyDTO;
import tfm.springboot.dtos.ForBudgetBudgetProductDTO;
import tfm.springboot.dtos.FullBudgetDTO;
import tfm.springboot.dtos.FullCustomerDTO;
import tfm.springboot.model.Budget;
import tfm.springboot.model.BudgetProduct;
import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.model.Product;
import tfm.springboot.service.BudgetProductService;
import tfm.springboot.service.BudgetService;
import tfm.springboot.service.CompanyService;
import tfm.springboot.service.CustomerService;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CompanyService companyService;

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private BudgetProductService budgetProductService;

	@Test
	@Order(1)
	public void createCustomer() throws Exception {

		MvcResult result;

		Customer customer = new Customer("TestName", "TestLastname", "test@mail.com");
		
		FullCustomerDTO fullCustomerDTO = this.customerService.convertToFullCustomerDTO(customer);

		result = mvc
				.perform(post("/api/customer").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		fullCustomerDTO = objectMapper.readValue(contentAsString, FullCustomerDTO.class);

		mvc.perform(get("/api/customer/" + fullCustomerDTO.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(fullCustomerDTO.getName())));
	}

	@Test
	@Order(2)
	public void setCompanyToCustomer() throws Exception {

		MvcResult result;

		Customer customer = new Customer("Mike", "Wazowski", "miwa@monsters.com");
		Company company = new Company("B0854687", "Monsters Inc.", "Monstropolis", "Logistic");
		
		FullCustomerDTO fullCustomerDTO = this.customerService.convertToFullCustomerDTO(customer);
		BasicCompanyDTO basicCompanyDTO = this.companyService.convertToBasicCompanyDTO(company);

		result = mvc
				.perform(post("/api/customer").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		fullCustomerDTO = objectMapper.readValue(contentAsString, FullCustomerDTO.class);

		result = mvc.perform(post("/api/customer/" + fullCustomerDTO.getId() + "/company")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(company)))
				.andExpect(status().isCreated()).andReturn();

		contentAsString = result.getResponse().getContentAsString();
		basicCompanyDTO = objectMapper.readValue(contentAsString, BasicCompanyDTO.class);

		mvc.perform(get("/api/customer/" + fullCustomerDTO.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.company.vatregnumber", equalTo(basicCompanyDTO.getVatregnumber())))
				.andExpect(jsonPath("$.company.name", equalTo(basicCompanyDTO.getName())));
	}

	@Test
	@Order(3)
	public void addCustomerBudget() throws Exception {

		MvcResult result;

		Customer customer = new Customer("Mike", "Wazowski", "miwa@monsters.com");
		Company company = new Company("B0854687", "Monsters Inc.", "Monstropolis", "Logistic");

		Product p1 = new Product("AF", "Advanced Finance", "Advanced Finance", 30);
		Product p2 = new Product("AP", "Advanced Purchases", "Advanced Purchases", 40);

		List<Product> products = new ArrayList<>();
		products.add(p1);
		products.add(p2);

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
		
		result = mvc
				.perform(post("/api/customer").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		fullCustomerDTO = objectMapper.readValue(contentAsString, FullCustomerDTO.class);
		
		result = mvc.perform(post("/api/customer/" + fullCustomerDTO.getId() + "/company")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(company)))
				.andExpect(status().isCreated()).andReturn();

		result = mvc
				.perform(post("/api/customer/" + fullCustomerDTO.getId() + "/budget")
						.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(budget)))
				.andExpect(status().isCreated()).andReturn();

		contentAsString = result.getResponse().getContentAsString();
		basicBudgetDTO = objectMapper.readValue(contentAsString, BasicBudgetDTO.class);

		mvc.perform(get("/api/customer/" + fullCustomerDTO.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.budgets[" + customer.getBudgets().size() + "].id",
						equalTo((int) basicBudgetDTO.getId())))
				.andExpect(jsonPath("$.budgets[" + fullCustomerDTO.getBudgets().size() + "].total",
						equalTo(basicBudgetDTO.getTotal())));

	}

}
