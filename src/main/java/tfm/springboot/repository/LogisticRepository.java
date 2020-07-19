package tfm.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.industries.Logistic;

public interface LogisticRepository extends JpaRepository<Logistic, Long> {

	List<Logistic> findById(long id);

}