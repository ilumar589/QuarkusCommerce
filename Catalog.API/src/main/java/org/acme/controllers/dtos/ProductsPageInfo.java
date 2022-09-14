package org.acme.controllers.dtos;

import org.acme.db.entities.Product;

import java.util.List;

public record ProductsPageInfo(long totalProducts, int totalPages, List<Product> productsPerPage) {
}
