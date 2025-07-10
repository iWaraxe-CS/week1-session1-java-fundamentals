package com.coherentsolutions.session1.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private List<String> tags;
    private Category category;
    private boolean inStock;
    private int stockQuantity;
    
    public enum Category {
        ELECTRONICS,
        CLOTHING,
        BOOKS,
        SPORTS,
        HOME,
        TOYS
    }
    
    public boolean isExpensive() {
        return price != null && price.compareTo(new BigDecimal("100.00")) > 0;
    }
    
    public boolean isAvailable() {
        return inStock && stockQuantity > 0;
    }
    
    public String getFirstTag() {
        return tags != null && !tags.isEmpty() ? tags.get(0) : "untagged";
    }
    
    @Override
    public String toString() {
        return String.format("%s: $%s", name, price);
    }
}