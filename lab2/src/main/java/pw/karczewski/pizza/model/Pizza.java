package pw.karczewski.pizza.model;

import java.math.BigDecimal;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString
public class Pizza {
    final private String name;
    final private BigDecimal price;
    final private boolean isAvailable;

    public Pizza(String name, BigDecimal price) {
        this(name, price, true);
    }
}
