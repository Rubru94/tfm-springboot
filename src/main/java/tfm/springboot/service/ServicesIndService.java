package tfm.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.model.industries.Services;
import tfm.springboot.repository.ServicesRepository;

@Service
public class ServicesIndService {
	
	@Autowired
	private ServicesRepository serviceRepository;
	
	public Services addServiceInd(Services service) {
		serviceRepository.save(service);
		return service;
	}	
}
