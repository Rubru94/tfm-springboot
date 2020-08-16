package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.dtos.BasicCompanyDTO;
import tfm.springboot.dtos.FullCompanyDTO;
import tfm.springboot.model.Company;
import tfm.springboot.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ModelMapper modelMapper;

	public BasicCompanyDTO convertToBasicCompanyDTO(Company company) {
		return modelMapper.map(company, BasicCompanyDTO.class);
	}

	public FullCompanyDTO convertToFullCompanyDTO(Company company) {
		return modelMapper.map(company, FullCompanyDTO.class);
	}

	public Company convertBasicCompanyDTOToEntity(BasicCompanyDTO company) {
		return modelMapper.map(company, Company.class);
	}

	public Company convertFullCompanyDTOToEntity(FullCompanyDTO company) {
		return modelMapper.map(company, Company.class);
	}

	public List<Company> getAllCompanies() {
		return companyRepository.findAll();
	}

	public Company getCompany(long id) {

		Optional<Company> co = companyRepository.findById(id);
		if (co.isPresent()) {
			Company company = co.get();
			return company;
		}
		return null;
	}

	public Company getCompanyByVatregnumber(String vatregnumber) {

		Optional<Company> co = companyRepository.findByVatregnumber(vatregnumber);
		if (co.isPresent()) {
			Company company = co.get();
			return company;
		}
		return null;
	}

	public Company addCompany(Company company) {
		companyRepository.save(company);
		return company;
	}

	public FullCompanyDTO getFullCompanyDTO(long id) {

		Optional<Company> co = companyRepository.findById(id);
		if (co.isPresent()) {
			Company company = co.get();
			return convertToFullCompanyDTO(company);
		}
		return null;
	}

	public BasicCompanyDTO getBasicCompanyDTO(long id) {

		Optional<Company> co = companyRepository.findById(id);
		if (co.isPresent()) {
			Company company = co.get();
			return convertToBasicCompanyDTO(company);
		}
		return null;
	}
}
