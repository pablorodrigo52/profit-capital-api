package src.service.strategy.strategies;

import java.math.BigDecimal;

import src.model.InvestmentStatus;
import src.model.Operation;
import src.model.Tax;

public class BuyOperation implements StrategyOperationTaxes {

    /**
     * calculateTaxes.
     * Must perform a operation and update the {@link InvestmentStatus} object.
     * @param operation {@link Operation} inputed by user.
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return {@link InvestmentStatus} updated with stock_amount, tax and average_price values.
     */
    @Override
    public InvestmentStatus calculateTaxes(
        Operation operation, 
        InvestmentStatus investmentStatus) {
       
        final BigDecimal weightedAveragePrice = getWeightedAveragePrice(operation, investmentStatus);
        
        investmentStatus.setAveragePrice(weightedAveragePrice);
        investmentStatus.addTaxes(new Tax(BigDecimal.ZERO));
        investmentStatus.setStockAmount(
            investmentStatus.getStockAmount() + operation.getQuantity());

        return investmentStatus;
    }

    /**
     * Calculate the WeightedAveragePrice.
     * Formua: nova-media-ponderada = ((quantidade-de-acoes-atual * media-ponderadaatual) + (quantidade-de-acoes * valor-de-compra)) / (quantidade-de-acoes-atual + quantidadede-acoes-compradas) 
     * @param operation Operation inputed by user
     * @param investmentStatus Actual state of user investment. 
     * @return WeightedAveragePrice
     */
    private BigDecimal getWeightedAveragePrice(
        final Operation operation, 
        final InvestmentStatus investmentStatus) {
            
            final BigDecimal investmentTotalStocks = BigDecimal.valueOf(investmentStatus.getStockAmount());
            final BigDecimal investmentAveragePrice = investmentStatus
                .getAveragePrice()
                .multiply(investmentTotalStocks);

            final BigDecimal operationQuantity = BigDecimal.valueOf(operation.getQuantity());
            final BigDecimal operationTotalCost = operation
                .getUnitCost()
                .multiply(operationQuantity);

            final BigDecimal totalStocks = investmentTotalStocks
                .add(operationQuantity);

            return investmentAveragePrice
                .add(operationTotalCost)
                .divide(totalStocks);
    }
}
