package src.service.strategy.strategies;

import src.model.InvestmentStatus;
import src.model.Operation;

public interface StrategyOperationTaxes {

    /**
     * calculateTaxes.
     * Must perform a operation and update the {@link InvestmentStatus} object.
     * @param operation {@link BuyOperation} or {@link SellOperation}
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return {@link InvestmentStatus} updated with stock_amount, tax, loss and average_price values
     */
    InvestmentStatus calculateTaxes(
        Operation operation, 
        InvestmentStatus investmentStatus);
}
