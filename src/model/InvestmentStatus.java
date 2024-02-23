package src.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InvestmentStatus {
    
    private BigDecimal averagePrice;
    private BigDecimal loss;
    private Long stockAmount;
    private List<Tax> taxes = new ArrayList<>();

    public InvestmentStatus() {
        this.averagePrice = BigDecimal.ZERO;
        this.loss = BigDecimal.ZERO;
        this.stockAmount = 0L;
    }

    public BigDecimal getAveragePrice() {
        return this.averagePrice;
    }

    public BigDecimal getLoss() {
        return this.loss;
    }

    public Long getStockAmount() {
        return this.stockAmount;
    }

    public List<Tax> getTaxes() {
        return this.taxes;
    }

    public void setAveragePrice(final BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setLoss(final BigDecimal loss) {
        this.loss = loss;
    }

    public void setStockAmount(final Long stockAmount) {
        this.stockAmount = stockAmount;
    }

    public void addTaxes(final Tax tax) {
        taxes.add(tax);
    }
}
