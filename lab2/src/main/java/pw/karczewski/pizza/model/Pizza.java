package pw.karczewski.pizza.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;

@Data
public class Pizza {
    @Getter
    private String name;
    @Getter
    private BigDecimal price;
    @Getter
    private boolean isAvailable;

    public Pizza(String name, BigDecimal price, boolean isAvailable) {
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Pizza(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
        this.isAvailable = true;
    }

    public String toString() {
        return String.format("%s (%s)", this.name, this.price);
    }

    // public String getName() {
    // return name;
    // }

    // public BigDecimal getPrice() {
    // return price;
    // }

    // public boolean getIsAvailable() {
    // return isAvailable;
    // }
}
