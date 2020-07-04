package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.model.Customer;
import tfm.springboot.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomer(long id) {
		
		Optional<Customer> op = customerRepository.findById(id);
		if (op.isPresent()) {
			Customer customer = op.get();
			return customer;
		}
		return null;
	}

	public Customer addCustomer(Customer customer) {
		customerRepository.save(customer);
		return customer;
	}

}
