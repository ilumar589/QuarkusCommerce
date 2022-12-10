package org.acme.utils;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.mutiny.groups.UniAwait;
import org.acme.db.entities.Product;
import org.acme.services.ProductService;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class AppLifeCycleBean {

    private static final Logger LOGGER = Logger.getLogger(AppLifeCycleBean.class.getName());

    private final ProductService productService;

    public AppLifeCycleBean(ProductService productService) {
        this.productService = productService;
    }


    void  onStart(@Observes StartupEvent event) {
        LOGGER.info("Catalog.API is starting...");
        LOGGER.info("Inserting Catalog Mock Data...");

        UniAwait<Void> result = productService.createOrUpdateProduct(List.of(
                new Product("Old book",
                        "Just an old book",
                        "Old book with ancient philosophy",
                        "Books",
                        new BigDecimal("10")),
                new Product("Spider-man",
                        "Spider-man costume",
                        "Spider-man costume for kids",
                        "Costumes",
                        new BigDecimal("200")),
                new Product("Megatron",
                        "Megatron toy",
                        "Megatron with photon lasers",
                        "Toys",
                        new BigDecimal("30"))
        )).await();

        result.indefinitely();

        LOGGER.info("Inserting Catalog Mock Data Finished");
    }

    void onStop(@Observes ShutdownEvent event) {
        if (!event.isStandardShutdown()) {
            LOGGER.info("Catalog.API is stopping...");
            LOGGER.info("Deleting Catalog Mock Data...");

            UniAwait<Long> result =  productService.deleteAllProducts().await();

            long nrOfDeletedProducts = result.indefinitely();

            LOGGER.info(String.format("Deleting Catalog Mock Data Finished. Deleted %d products", nrOfDeletedProducts));
        }
    }
}
