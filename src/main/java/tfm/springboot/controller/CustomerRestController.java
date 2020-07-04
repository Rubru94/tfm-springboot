package tfm.springboot.controller;

import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tfm.springboot.model.Customer;
import tfm.springboot.repository.CustomerRepository;
import tfm.springboot.service.CustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private CustomerService customerService;

	@PostConstruct
	public void init() {

		Customer c1 = new Customer("Alfredo", "Perez");
		customerService.addCustomer(c1);
		Customer c2 = new Customer("Gumersindo", "Azcarate");
		customerService.addCustomer(c2);	}

	@GetMapping("/api/customers")
	public List<Customer> getAllCustomers() {

		return customerService.getAllCustomers();
	}

	@GetMapping("/api/customer/{id}")
	public ResponseEntity<Customer> showPost(@PathVariable long id) {

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

}
