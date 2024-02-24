package src.model;

import java.math.BigDecimal;

public class Tax {
    private BigDecimal tax;

    public Tax(
        final BigDecimal tax
    ) {
        this.tax = tax;
    }

    @Override
    public String toString() {
        return "{\"tax\": " + this.tax.toString() + "}";
    }
}
