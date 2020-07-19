package tfm.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.model.industries.Logistic;
import tfm.springboot.repository.LogisticRepository;

@Service
public class LogisticIndService {
	
	@Autowired
	private LogisticRepository logisticRepository;
	
	public Logistic addLogisticInd(Logistic logistic) {
		logisticRepository.save(logistic);
		return logistic;
	}	
}
