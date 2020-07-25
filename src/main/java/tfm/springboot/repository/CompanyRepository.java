package tfm.springboot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByName(String string);
	
	Optional<Company> findByVatregnumber(String string);

}