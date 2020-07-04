package tfm.springboot.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tfm.springboot.model.Customer;
import tfm.springboot.service.CustomerService;

@Controller
public class WebController {

	@Autowired
	private CustomerService customerService;

	@GetMapping("/")
	public String customers(Model model) {
		model.addAttribute("customers", this.customerService.getAllCustomers());
		return "customers";
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
	
}
