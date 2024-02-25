package src.service.strategy.strategies;

import java.math.BigDecimal;

import src.model.InvestmentStatus;
import src.model.Operation;
import src.model.Tax;

public class SellOperation implements StrategyOperationTaxes {
    private static final BigDecimal TAX = BigDecimal.valueOf(0.2);
    private static final BigDecimal TAXES_THRESHOLD = BigDecimal.valueOf(20000);

    /**
     * calculateTaxes.
     * Must perform a operation and update the {@link InvestmentStatus} object.
     * @param operation {@link Operation} inputed by user.
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return {@link InvestmentStatus} updated with stock_amount, tax, loss and average_price values.
     */
    @Override
    public InvestmentStatus calculateTaxes(
        Operation operation, 
        InvestmentStatus investmentStatus) {
            
            if (hasProfitForTaxes(operation, investmentStatus)) {
                final BigDecimal profitPerStock = operation
                    .getUnitCost()
                    .subtract(investmentStatus.getAveragePrice());
                final BigDecimal profitAfterLoss = profitPerStock
                    .multiply(BigDecimal.valueOf(operation.getQuantity()))
                    .subtract(investmentStatus.getLoss());

                investmentStatus = applyTaxOnProfitableOperation(profitAfterLoss, investmentStatus);
                investmentStatus = updateLossOnProfitableOperation(profitAfterLoss, investmentStatus);
            } else {
                final BigDecimal lossPerStock = investmentStatus
                    .getAveragePrice()
                    .subtract(operation.getUnitCost());
                final BigDecimal operationTotalLoss = lossPerStock
                    .multiply(BigDecimal.valueOf(operation.getQuantity()))
                    .add(investmentStatus.getLoss());

                investmentStatus.setLoss(operationTotalLoss);
                investmentStatus.addTaxes(new Tax(BigDecimal.ZERO));
            }

            investmentStatus.setStockAmount(
                investmentStatus.getStockAmount() - operation.getQuantity());

            return investmentStatus;
    }

    /**
     * hasProfitForTaxes.
     * Verify if whether the operation has a profit where the TAX can be applied. 
     * @param operation {@link Operation} inputed by user.
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return true if has taxed profit and false otherwise. 
     */
    private Boolean hasProfitForTaxes (
        final Operation operation, 
        final InvestmentStatus investmentStatus) {

            final BigDecimal operationTotalCost = operation
                .getUnitCost()
                .multiply(BigDecimal.valueOf(operation.getQuantity()));

            return operationTotalCost.compareTo(TAXES_THRESHOLD) >= 0 &&
                operation.getUnitCost().compareTo(investmentStatus.getAveragePrice()) >= 0;
    }
    
    /**
     * applyTaxOnProfitableOperation.
     * Updates the {@link InvestmentStatus} with correct tax value.
     * @param profitAfterLoss the profit after discount the loss value.
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return {@link InvestmentStatus} updated with tax value.
     */
    private InvestmentStatus applyTaxOnProfitableOperation(
        final BigDecimal profitAfterLoss,
        final InvestmentStatus investmentStatus) {
            
            if (profitAfterLoss.signum() > 0) {
                final BigDecimal taxValue = profitAfterLoss
                    .multiply(TAX);
                investmentStatus.addTaxes(new Tax(taxValue));
            } else {
                investmentStatus.addTaxes(new Tax(BigDecimal.ZERO));
            }
            return investmentStatus;
    }

    /**
     * updateLossOnProfitableOperation.
     * Updates the {@link InvestmentStatus} with correct loss value.
     * @param profitAfterLoss the profit after discount the loss value.
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return {@link InvestmentStatus} updated with loss value.
     */
    private InvestmentStatus updateLossOnProfitableOperation(
        BigDecimal profitAfterLoss,
        final InvestmentStatus investmentStatus) {

            if (profitAfterLoss.signum() < 0) {
                profitAfterLoss = profitAfterLoss.negate();
            }

            if (profitAfterLoss.signum() == 0) {
                investmentStatus.setLoss(BigDecimal.ZERO);
                return investmentStatus;
            }

            final BigDecimal updatedLoss = investmentStatus
                .getLoss()
                .subtract(profitAfterLoss);
        
            if (updatedLoss.signum() <= 0) {
                investmentStatus.setLoss(BigDecimal.ZERO);
            } else {
                investmentStatus.setLoss(updatedLoss);
            }

            return investmentStatus;
    }
}
