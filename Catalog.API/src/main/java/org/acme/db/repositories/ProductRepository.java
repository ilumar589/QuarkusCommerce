package org.acme.db.repositories;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import org.acme.db.entities.Product;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProductRepository implements ReactivePanacheMongoRepository<Product> {
}
