package tfm.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByName(String string);

}