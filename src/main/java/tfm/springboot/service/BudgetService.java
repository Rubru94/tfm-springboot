package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.dtos.BasicBudgetDTO;
import tfm.springboot.dtos.FullBudgetDTO;
import tfm.springboot.model.Budget;
import tfm.springboot.repository.BudgetRepository;

@Service
public class BudgetService {

	@Autowired
	private BudgetRepository budgetRepository;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmailService emailService;

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
			return op.get();
		}
		return null;
	}

	public Budget addBudget(Budget budget) {
		budgetRepository.save(budget);
		
		FullBudgetDTO fullBudgetDTO =  convertToFullBudgetDTO(budget);
		emailService.sendMail(budget.getCustomer().getEmail(), 
				"AcevedoApps New Budget", 
				emailService.getTemplate(fullBudgetDTO));
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
