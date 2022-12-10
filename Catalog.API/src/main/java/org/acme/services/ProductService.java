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
                        getTotalProductsCount(),
                        getTotalPages(page, pageSize),
                        getProductsPaged(page, pageSize).collect().asList()
                )
                .combinedWith(ProductsPageInfo::new);
    }

    public Uni<Void> createOrUpdateProduct(Iterable<Product> products) {
        return productRepository.persistOrUpdate(products);
    }

    public Uni<Boolean> deleteProduct(ObjectId productId) {
        return productRepository.deleteById(productId);
    }

    public Uni<Long> deleteAllProducts() {
        return productRepository.deleteAll();
    }

    private Multi<Product> getProductsPaged(int page, int pageSize) {
        return productRepository
                .findAll()
                .page(page, pageSize)
                .stream();
    }

    private Uni<Long> getTotalProductsCount() {
        return productRepository.count();
    }

    private Uni<Integer> getTotalPages(int page, int pageSize) {
        return productRepository.findAll().page(page,pageSize).pageCount();
    }
}
