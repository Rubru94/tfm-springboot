package tfm.springboot.controller;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.model.industries.Industries;
import tfm.springboot.model.industries.Logistic;
import tfm.springboot.model.industries.Services;
import tfm.springboot.service.CompanyService;
import tfm.springboot.service.CustomerService;
import tfm.springboot.service.LogisticIndService;
import tfm.springboot.service.ServicesIndService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private LogisticIndService logisticIndService;

	@Autowired
	private ServicesIndService servicesIndService;

	@PostConstruct
	public void init() {

		Company co1 = new Company("IBM", "Spain");
		companyService.addCompany(co1);
		Customer c1 = new Customer("Alfredo", "Perez", "alpe@gmail.com");
		c1.setCompany(co1);
		customerService.addCustomer(c1);
		Customer c2 = new Customer("Gumersindo", "Azcarate", "guaz@gmail.com");
		customerService.addCustomer(c2);

	}

	@GetMapping("/api/customers")
	public List<Customer> getAllCustomers() {

		return customerService.getAllCustomers();
	}

	@GetMapping("/api/customer/{id}")
	public ResponseEntity<Customer> showCustomer(@PathVariable long id) {

		Customer customer = customerService.getCustomer(id);
		if (customer != null) {
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/api/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer createCustomer(@RequestBody Customer customer) {

		return customerService.addCustomer(customer);
	}

	@GetMapping("/api/customer/{id}/company")
	public ResponseEntity<Company> showCompany(@PathVariable long id, Model model, Company company) {

		Customer customer = this.customerService.getCustomer(id);
		if (customer != null) {
			return new ResponseEntity<>(customer.getCompany(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/api/customer/{id}/company")
	public ResponseEntity<Company> setCompany(@PathVariable long id, @RequestBody Company company) {
		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Company com = new Company(company.getName(), company.getCountry(), company.getIndustry());

		if (com.getIndustry().equals(Industries.LOGISTIC.getName())) {
			Logistic logistic = new Logistic();
			com.setLogistic(logistic);
			this.logisticIndService.addLogisticInd(logistic);
		}

		if (com.getIndustry().equals(Industries.SERVICES.getName())) {
			Services services = new Services();
			com.setService(services);
			this.servicesIndService.addServiceInd(services);
		}

		this.companyService.addCompany(com);

		customer.setCompany(com);
		this.customerService.addCustomer(customer);

		return new ResponseEntity<>(com, HttpStatus.CREATED);
	}

}
