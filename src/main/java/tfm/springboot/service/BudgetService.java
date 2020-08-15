package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.DTOs.BasicBudgetDTO;
import tfm.springboot.DTOs.FullBudgetDTO;
import tfm.springboot.model.Budget;
import tfm.springboot.repository.BudgetRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ModelMapper modelMapper;

	public FullBudgetDTO convertToFullBudgetDTO(Budget budget) {
		return modelMapper.map(budget, FullBudgetDTO.class);
	}

	public Budget convertFullBudgetDtoToEntity(FullBudgetDTO budget) {
		return modelMapper.map(budget, Budget.class);
	}
	
	public BasicBudgetDTO convertToBasicBudgetDTO(Budget budget) {
		return modelMapper.map(budget, BasicBudgetDTO.class);
	}

	public Budget convertBasicBudgetDtoToEntity(BasicBudgetDTO budget) {
		return modelMapper.map(budget, Budget.class);
	}

	public List<Budget> getAllBudgets() {
		return budgetRepository.findAll();
	}

	public Budget getBudget(long id) {

		Optional<Budget> op = budgetRepository.findById(id);
		if (op.isPresent()) {
			Budget budget = op.get();
			return budget;
		}
		return null;
	}

	public Budget addBudget(Budget budget) {
		budgetRepository.save(budget);
		return budget;
	}

	public FullBudgetDTO getFullBudgetDTO(long id) {
		
		Optional<Budget> op = budgetRepository.findById(id);
		if (op.isPresent()) {
			Budget budget = op.get();
			return convertToFullBudgetDTO(budget);
		}
		return null;
	}

}
