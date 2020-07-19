package tfm.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.model.Company;
import tfm.springboot.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Company addCompany(Company company) {
		companyRepository.save(company);
		return company;
	}
	
}
