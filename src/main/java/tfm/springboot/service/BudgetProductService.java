package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.DTOs.ForBudgetBudgetProductDTO;
import tfm.springboot.model.BudgetProduct;
import tfm.springboot.repository.BudgetProductRepository;

@Service
public class BudgetProductService {

	@Autowired
	private BudgetProductRepository budgetProductRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ForBudgetBudgetProductDTO convertToForBudgetBudgetProductDTO(BudgetProduct budgetProduct) {
		return modelMapper.map(budgetProduct, ForBudgetBudgetProductDTO.class);
	}

	public List<BudgetProduct> getAllBudgetProducts() {
		return budgetProductRepository.findAll();
	}

	public BudgetProduct getBudgetProduct(long id) {

		Optional<BudgetProduct> op = budgetProductRepository.findById(id);
		if (op.isPresent()) {
			BudgetProduct budgetProduct = op.get();
			return budgetProduct;
		}
		return null;
	}

	public BudgetProduct addBudgetProduct(BudgetProduct budgetProduct) {
		budgetProductRepository.save(budgetProduct);
		return budgetProduct;
	}

}
