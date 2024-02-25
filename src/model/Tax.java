package src.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Tax {
    private BigDecimal tax;

    public Tax() {}

    public Tax(final BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTax() {
        this.tax = this.tax.setScale(2, RoundingMode.FLOOR);
        return this.tax;
    }
}
