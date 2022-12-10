package org.acme.db.entities;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

import java.math.BigDecimal;

@MongoEntity(collection = "products")
public class Product extends PanacheMongoEntity {
    public String name;
    public String summary;
    public String description;
    public String category;
    public BigDecimal price;

    public Product() {
        // serialization/deserialization
    }

    public Product(String name, String summary, String description, String category, BigDecimal price) {
        this.name = name;
        this.summary = summary;
        this.description = description;
        this.category = category;
        this.price = price;
    }
}
