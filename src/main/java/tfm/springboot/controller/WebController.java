package tfm.springboot.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tfm.springboot.model.Budget;
import tfm.springboot.model.BudgetProduct;
import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.model.Product;
import tfm.springboot.service.BudgetService;
import tfm.springboot.service.CompanyService;
import tfm.springboot.service.CustomerService;
import tfm.springboot.service.ProductService;

@Controller
public class WebController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BudgetService budgetService;

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

		Company com = this.companyService.getCompanyByVatregnumber(company.getVatregnumber());
		if (com != null) {
			com.addCustomer(customer);
		} else {
			com = new Company(company.getVatregnumber(), company.getName(), company.getCountry(),
					company.getIndustry());
			com.addCustomer(customer);
			this.companyService.addCompany(com);
		}

		customer.setCompany(com);
		this.customerService.addCustomer(customer);

		return "redirect:/customer/" + customer.getId() + "/products";
	}

	@GetMapping("/customer/{id}/products")
	public String listProducts(@PathVariable long id, Model model) {

		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			model.addAttribute("errorMessage", "Not found client id " + id);
			return "error";
		}

		model.addAttribute("customer", customer);

		model.addAttribute("products", this.productService.getAllProducts());
		return "product";
	}

	@PostMapping("/customer/{id}/budget")
	public String addCustomerBudget(HttpSession session, @PathVariable long id,
			@RequestParam(name = "prods") String prods, Model model) {

		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			model.addAttribute("errorMessage", "Not found client id " + id);
			return "error";
		}

		BudgetProduct bp;
		Product product;
		Budget budget = new Budget(new Date(), customer);

		String[] vector = prods.split(";");

		for (String s : vector) {

			product = this.productService.getProduct(Long.valueOf(s));

			bp = new BudgetProduct(budget, product, new Date());

			budget.addProduct(bp);
			this.productService.addProduct(product);

			product.addBudget(bp);
			this.budgetService.addBudget(budget);
		}

		customer.addBudget(budget);

		return "redirect:/customer/" + customer.getId() + "/lastBudget";
	}

	@GetMapping("/customer/{id}/lastBudget")
	public String lastCustomerBudget(@PathVariable long id, Model model) {

		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			model.addAttribute("errorMessage", "Not found client id " + id);
			return "error";
		}

		model.addAttribute("customer", customer);

		model.addAttribute("lastBudget", customer.getBudgets().get(customer.getBudgets().size() - 1));
		return "budget";
	}

}
