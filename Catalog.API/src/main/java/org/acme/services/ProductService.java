package org.acme.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.db.entities.Product;
import org.acme.db.repositories.ProductRepository;
import org.bson.types.ObjectId;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public  Multi<Product> getProducts() {
        return productRepository.streamAll();
    }

    public Uni<Product> createOrUpdateProduct(Product product) {
        return productRepository.persistOrUpdate(product);
    }

    public Uni<Boolean> deleteProduct(ObjectId productId) {
        return productRepository.deleteById(productId);
    }
}
