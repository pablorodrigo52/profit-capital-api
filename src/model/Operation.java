package src.model;

import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonProperty;

import src.statics.OperationType;

public class Operation {
    
    private OperationType operation;
    @JsonProperty("unit-cost")
    private BigDecimal unitCost;
    private Long quantity;

    public Operation() {}

    public Operation(
        OperationType operation,
        BigDecimal unitCost,
        Long quantity
    ) {
        this.operation = operation;
        this.unitCost = unitCost;
        this.quantity = quantity;
    }

    public OperationType getOperation() {
        return operation;
    }
   
    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public Long getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "" 
            + this.operation + " -> " 
            + this.unitCost + " -> "
            + this.quantity;
    }

    // public void setOperation(String operation) {
    //     this.operation = operation;
    // }
    
    // public void setUnitCost(BigDecimal unitCost) {
    //     this.unitCost = unitCost;
    // }
    
    // public void setQuantity(Long quantity) {
    //     this.quantity = quantity;
    // }
}
