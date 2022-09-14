package org.acme.db.projections;

import io.quarkus.mongodb.panache.common.ProjectionFor;
import org.acme.db.entities.Product;

// PanacheQuery<ProductName> shortQuery = Product.find("status ", Status.Alive).project(ProductName.class);
@ProjectionFor(Product.class)
public record ProductName(String name) {
}
