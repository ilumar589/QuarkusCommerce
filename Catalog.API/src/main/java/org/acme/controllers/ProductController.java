package org.acme.controllers;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.controllers.dtos.ProductsPageInfo;
import org.acme.db.entities.Product;
import org.acme.services.ProductService;
import org.bson.types.ObjectId;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

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

    @GET
    @Path("/{page}/{pageSize}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<ProductsPageInfo> getProducts(int page, int pageSize) {
        return productService.getPageInfo(page, pageSize);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Void> createOrUpdateProduct(@RequestBody Product product) {
        return productService.createOrUpdateProduct(List.of(product));
    }

    @DELETE
    @Path("/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Boolean> deleteProduct(String productId) {
        return productService.deleteProduct(new ObjectId(productId));
    }
}
