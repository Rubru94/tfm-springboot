package tfm.springboot.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tfm.springboot.DTOs.BasicCompanyDTO;
import tfm.springboot.DTOs.FullBudgetDTO;
import tfm.springboot.DTOs.FullCompanyDTO;
import tfm.springboot.DTOs.FullCustomerDTO;
import tfm.springboot.DTOs.FullProductDTO;
import tfm.springboot.model.Budget;
import tfm.springboot.model.BudgetProduct;
import tfm.springboot.model.Company;
import tfm.springboot.model.Customer;
import tfm.springboot.model.Product;
import tfm.springboot.service.BudgetProductService;
import tfm.springboot.service.BudgetService;
import tfm.springboot.service.CompanyService;
import tfm.springboot.service.CustomerService;
import tfm.springboot.service.ProductService;

@RestController
public class ApiRestController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BudgetService budgetService;

	@Autowired
	private BudgetProductService budgetProductService;

	@PostConstruct
	public void init() {

		// Initial example data load

		Company co1 = new Company("B15248631", "IBM", "Spain");
		companyService.addCompany(co1);
		Customer c1 = new Customer("Alfredo", "Perez", "alpe@gmail.com");
		c1.setCompany(co1);
		this.customerService.addCustomer(c1);
		Customer c2 = new Customer("Gumersindo", "Azcarate", "guaz@gmail.com");
		customerService.addCustomer(c2);

		// BUDGETS & PRODUCTS

		Product product = new Product("INVENTORY", "Gestion de Inventario", "Producto Gestion de Inventario", 15);
		this.productService.addProduct(product);

		Budget budget = new Budget(new Date(), c1);
		this.budgetService.addBudget(budget);

		BudgetProduct budgetProduct = new BudgetProduct(budget, product, new Date());
		this.budgetProductService.addBudgetProduct(budgetProduct);

		budget.addProduct(budgetProduct);
		this.productService.addProduct(product);
		product.addBudget(budgetProduct);
		this.budgetService.addBudget(budget);

	}

	@GetMapping("/api/customers")
	public List<FullCustomerDTO> listFullCustomers() {

		List<Customer> customers = this.customerService.getAllCustomers();
		return customers.stream().map((customer) -> this.customerService.convertToFullCustomerDTO(customer))
				.collect(Collectors.toList());
	}

	@GetMapping("/api/customer/{id}")
	public ResponseEntity<FullCustomerDTO> getCustomer(@PathVariable long id) {
		FullCustomerDTO customer = this.customerService.getFullCustomerDTO(id);
		if (customer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping("/api/customer")
	public ResponseEntity<Customer> createCustomer(@RequestBody FullCustomerDTO customer) {

		Customer newCustomer = this.customerService.convertFullCustomerDtoToEntity(customer);
		this.customerService.addCustomer(newCustomer);

		return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
	}

	@GetMapping("/api/customer/{id}/company")
	public ResponseEntity<BasicCompanyDTO> getCustomerCompany(@PathVariable long id) {

		FullCustomerDTO customer = this.customerService.getFullCustomerDTO(id);
		if (customer != null) {
			return new ResponseEntity<>(customer.getCompany(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/api/customer/{id}/company")
	public ResponseEntity<BasicCompanyDTO> setCustomerCompany(@PathVariable long id, @RequestBody Company company) {
		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

		return new ResponseEntity<>(this.companyService.convertToBasicCompanyDTO(com), HttpStatus.CREATED);
	}

	@PostMapping("/api/customer/{id}/budget")
	public ResponseEntity<FullBudgetDTO> addCustomerBudget(@PathVariable long id, @RequestBody Budget budget) {

		Customer customer = this.customerService.getCustomer(id);
		if (customer == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Budget bud = new Budget(budget.getDate(), customer);

		for (BudgetProduct budgetProduct : budget.getProducts()) {

			BudgetProduct bp = new BudgetProduct(bud, budgetProduct.getProduct(), new Date());
			this.budgetProductService.addBudgetProduct(bp);

			bud.addProduct(bp); 
			this.productService.addProduct(budgetProduct.getProduct());
			budgetProduct.getProduct().addBudget(bp); 
			this.budgetService.addBudget(bud);
		}

		customer.addBudget(bud);

		return new ResponseEntity<>(this.budgetService.convertToFullBudgetDTO(bud), HttpStatus.CREATED);
	}

	@GetMapping("/api/companies")
	public List<FullCompanyDTO> listFullCompanies() {

		List<Company> companies = this.companyService.getAllCompanies();
		return companies.stream().map((company) -> this.companyService.convertToFullCompanyDTO(company))
				.collect(Collectors.toList());
	}

	@GetMapping("/api/company/{id}")
	public ResponseEntity<FullCompanyDTO> getCompany(@PathVariable long id) {
		FullCompanyDTO company = this.companyService.getFullCompanyDTO(id);
		if (company == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	@GetMapping("/api/products")
	public List<FullProductDTO> listBasicProducts() {

		List<Product> products = this.productService.getAllProducts();
		return products.stream().map((product) -> this.productService.convertToFullProductDTO(product))
				.collect(Collectors.toList());
	}

	@GetMapping("/api/product/{id}")
	public ResponseEntity<FullProductDTO> getProduct(@PathVariable long id) {
		FullProductDTO product = this.productService.getFullProductDTO(id);
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@GetMapping("/api/budgets")
	public List<FullBudgetDTO> listFullBudgets() {

		List<Budget> budgets = this.budgetService.getAllBudgets();
		return budgets.stream().map((budget) -> this.budgetService.convertToFullBudgetDTO(budget))
				.collect(Collectors.toList());
	}

	@GetMapping("/api/budget/{id}")
	public ResponseEntity<FullBudgetDTO> getBudget(@PathVariable long id) {
		FullBudgetDTO budget = this.budgetService.getFullBudgetDTO(id);
		if (budget == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(budget, HttpStatus.OK);
	}

}
