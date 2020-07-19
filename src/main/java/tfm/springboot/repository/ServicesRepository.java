package tfm.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.industries.Services;

public interface ServicesRepository extends JpaRepository<Services, Long> {

	List<Services> findById(long id);

}