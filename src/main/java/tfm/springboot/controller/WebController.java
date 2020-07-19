package tfm.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.model.industries.Industries;
import tfm.springboot.model.industries.Logistic;
import tfm.springboot.model.industries.Services;
import tfm.springboot.service.CompanyService;
import tfm.springboot.service.CustomerService;
import tfm.springboot.service.LogisticIndService;
import tfm.springboot.service.ServicesIndService;

@Controller
public class WebController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private LogisticIndService logisticIndService;
	
	@Autowired
	private ServicesIndService servicesIndService;

	@GetMapping("/")
	public String customers(Model model) {
		model.addAttribute("customers", this.customerService.getAllCustomers());
		return "index";
	}

	@GetMapping("/customer/{id}")
	public String customer(HttpSession session, @PathVariable long id, Model model) {
		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			model.addAttribute("errorMessage", "No existe un cliente con id " + id);
			return "error";
		}
		Object userName = session.getAttribute("userName");
		model.addAttribute("userName", userName != null ? userName : "Your name");
		model.addAttribute("customer", customer);
		return "customer";
	}

	@GetMapping("/customer/new")
	public String customer(Model model) {
		return "newCustomer";
	}

	@PostMapping("/customer")
	public String customer(Model model, Customer customer) {
		this.customerService.addCustomer(customer);
		return "redirect:/customer/" + customer.getId();
	}
	
	@GetMapping("/customer/{id}/company")
	public String showCompany(@PathVariable long id, Model model, Company company) {
		
		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			model.addAttribute("errorMessage", "Not found client id " + id);
			return "error";
		}
		
		model.addAttribute("company", customer.getCompany());
		return "company";
	}

	@PostMapping("/customer/{id}/company")
	public String setCompany(HttpSession session, @PathVariable long id, Model model, Company company) {

		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			model.addAttribute("errorMessage", "Not found client id " + id);
			return "error";
		}
		
		Company com = new Company(company.getName(), company.getCountry(), company.getIndustry());		
		
		if (com.getIndustry().equals(Industries.LOGISTIC.getName())) {
			Logistic logistic = new Logistic();
			com.setLogistic(logistic);
			this.logisticIndService.addLogisticInd(logistic);
		}

		if (com.getIndustry().equals(Industries.SERVICES.getName())) {
			Services services = new Services();
			com.setService(services);
			this.servicesIndService.addServiceInd(services);
		}
		
		this.companyService.addCompany(com);
		
		customer.setCompany(com);
		this.customerService.addCustomer(customer);

		return "company";
		//return "redirect:/customer/" + customer.getId();
	}

}
