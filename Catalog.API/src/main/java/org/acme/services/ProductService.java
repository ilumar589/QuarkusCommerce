package org.acme.services;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.controllers.dtos.ProductsPageInfo;
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

    public Multi<Product> getProducts() {
        return productRepository.streamAll();
    }

    public Uni<ProductsPageInfo> getPageInfo(int page, int pageSize) {
        return Uni
                .combine()
                .all()
                .unis(
                        getTotalProductsCounts(),
                        getTotalPages(page, pageSize),
                        getProductsPaged(page, pageSize).collect().asList()
                )
                .combinedWith(ProductsPageInfo::new);
    }

    public Uni<Product> createOrUpdateProduct(Product product) {
        return productRepository.persistOrUpdate(product);
    }

    public Uni<Boolean> deleteProduct(ObjectId productId) {
        return productRepository.deleteById(productId);
    }

    private Multi<Product> getProductsPaged(int page, int pageSize) {
        return productRepository
                .findAll()
                .page(page, pageSize)
                .stream();
    }

    private Uni<Long> getTotalProductsCounts() {
        return productRepository.count();
    }

    private Uni<Integer> getTotalPages(int page, int pageSize) {
        return productRepository.findAll().page(page,pageSize).pageCount();
    }
}
