package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.DTOs.FullCustomerDTO;
import tfm.springboot.model.Customer;
import tfm.springboot.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FullCustomerDTO convertToFullCustomerDTO(Customer customer) {
		return modelMapper.map(customer, FullCustomerDTO.class);
	}

	public Customer convertFullCustomerDtoToEntity(FullCustomerDTO customer) {
		return modelMapper.map(customer, Customer.class);
	}
	
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
	
	public FullCustomerDTO getFullCustomerDTO(long id) {
		Customer customer = customerRepository.findById(id).get();
		if (customer != null) {
			return convertToFullCustomerDTO(customer);
		}
		return null;
	}

}
