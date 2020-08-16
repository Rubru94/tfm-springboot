package tfm.springboot.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tfm.springboot.dtos.BasicProductDTO;
import tfm.springboot.dtos.FullProductDTO;
import tfm.springboot.model.Product;
import tfm.springboot.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	public FullProductDTO convertToFullProductDTO(Product product) {
		return modelMapper.map(product, FullProductDTO.class);
	}

	public Product convertFullProductDtoToEntity(FullProductDTO product) {
		return modelMapper.map(product, Product.class);
	}

	public BasicProductDTO convertToBasicProductDTO(Product product) {
		return modelMapper.map(product, BasicProductDTO.class);
	}

	public Product convertBasicProductDtoToEntity(BasicProductDTO product) {
		return modelMapper.map(product, Product.class);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Product getProduct(long id) {

		Optional<Product> op = productRepository.findById(id);
		if (op.isPresent()) {
			Product product = op.get();
			return product;
		}
		return null;
	}

	public Product getProductByCode(String code) {

		Optional<Product> op = productRepository.findByCode(code);
		if (op.isPresent()) {
			Product product = op.get();
			return product;
		}
		return null;
	}

	public Product addProduct(Product product) {
		productRepository.save(product);
		return product;
	}

	public FullProductDTO getFullProductDTO(long id) {

		Optional<Product> op = productRepository.findById(id);
		if (op.isPresent()) {
			Product product = op.get();
			return convertToFullProductDTO(product);
		}
		return null;
	}

	public BasicProductDTO getBasicProductDTO(long id) {

		Optional<Product> op = productRepository.findById(id);
		if (op.isPresent()) {
			Product product = op.get();
			return convertToBasicProductDTO(product);
		}
		return null;
	}

}
