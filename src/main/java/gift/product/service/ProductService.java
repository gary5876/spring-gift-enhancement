package gift.product.service;

import gift.product.dto.ProductRequest;
import gift.product.entity.Product;
import gift.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAllProducts() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Product create(ProductRequest request) {
        Product product = new Product(request.getName(), request.getPrice(), request.getImgUrl());
        return repository.save(product);
    }

    @Transactional
    public Product update(Long id, ProductRequest request) {
        Product existing = repository.findById(id).orElse(null);
        if (existing == null) return null;

        existing.updateName(request.getName());
        existing.updatePrice(request.getPrice());
        existing.updateImgUrl(request.getImgUrl());

        return existing;
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}