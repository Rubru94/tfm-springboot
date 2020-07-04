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

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostConstruct
	public void init() {
		
		Customer c1 = new Customer("Alfredo", "Perez");
		customerRepository.save(c1);
		Customer c2 = new Customer("Gumersindo", "Azcarate");
		customerRepository.save(c2);
	}

	@GetMapping("/customers")
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<Customer> showPost(@PathVariable long id) {
		Optional<Customer> op = customerRepository.findById(id);
		if (op.isPresent()) {
			Customer customer = op.get();
			return new ResponseEntity<>(customer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/customer")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer createPost(@RequestBody Customer customer) {
		customerRepository.save(customer);
		return customer;
	}

}
