package tfm.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByName(String string);

}