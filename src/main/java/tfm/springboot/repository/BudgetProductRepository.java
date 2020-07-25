package tfm.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tfm.springboot.model.BudgetProduct;

public interface BudgetProductRepository extends JpaRepository<BudgetProduct, Long> {


}