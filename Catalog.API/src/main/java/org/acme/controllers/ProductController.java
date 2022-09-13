package org.acme.controllers;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.db.entities.Product;
import org.acme.services.ProductService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Multi<Product> getProducts() {
        return productService.getProducts();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Product> createOrUpdateProduct(@RequestBody Product product) {
        return productService.createOrUpdateProduct(product);
    }

    @DELETE
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteProduct(String productId) {
        return productService.deleteProduct(new ObjectId(productId));
    }
}
