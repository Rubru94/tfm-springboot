package tfm.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

}