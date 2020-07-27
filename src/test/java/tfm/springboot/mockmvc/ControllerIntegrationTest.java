package tfm.springboot.mockmvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;

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

		MvcResult result;

		Customer customer = new Customer("TestName", "TestLastname", "test@mail.com");

		result = mvc
				.perform(post("/api/customer").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		customer = objectMapper.readValue(contentAsString, Customer.class);

		mvc.perform(get("/api/customer/" + customer.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(customer.getName())));
	}

	@Test
	@Order(2)
	public void setCompanyToCustomer() throws Exception {

		MvcResult result;

		Customer customer = new Customer("Mike", "Wazowski", "miwa@monsters.com");
		Company company = new Company("B0854687", "Monsters Inc.", "Monstropolis", "Logistic");

		result = mvc
				.perform(post("/api/customer").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(customer)))
				.andExpect(status().isCreated()).andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		customer = objectMapper.readValue(contentAsString, Customer.class);

		result = mvc.perform(post("/api/customer/" + customer.getId() + "/company")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(company)))
				.andExpect(status().isCreated()).andReturn();

		contentAsString = result.getResponse().getContentAsString();
		company = objectMapper.readValue(contentAsString, Company.class);

		mvc.perform(get("/api/customer/" + customer.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.company.vatregnumber", equalTo(company.getVatregnumber())))
				.andExpect(jsonPath("$.company.name", equalTo(company.getName())));
	}

}
