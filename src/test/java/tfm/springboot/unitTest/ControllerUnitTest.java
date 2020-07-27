package tfm.springboot.unitTest;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.service.CompanyService;
import tfm.springboot.service.CustomerService;

@SpringBootTest
public class ControllerUnitTest {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	@Test
	public void givenNewCustomerWhenAddToRepositoryThenNotError() throws Exception {

		Customer customer = this.customerService.addCustomer(new Customer("TestName", "TestLastname", "test@mail.com"));
		assertEquals(customer.getName(), "TestName");
	}

	@Test
	public void givenCustomerWhenAddNewCompanyThenNotError() throws Exception {

		Customer customer = new Customer("Tony", "Stark", "tost@iron.com");
		Company company = new Company("B01482556", "Stark Industries", "USA", "Logistic");

		company.addCustomer(customer);
		this.companyService.addCompany(company);

		customer.setCompany(company);
		this.customerService.addCustomer(customer);

		assertEquals(customer.getCompany().getVatregnumber(), "B01482556");
	}

}
