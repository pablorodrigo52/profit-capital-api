package src.service.strategy.factory;

import src.service.strategy.strategies.BuyOperation;
import src.service.strategy.strategies.SellOperation;
import src.service.strategy.strategies.StrategyOperationTaxes;
import src.statics.OperationType;

public class OperationFactory {
    
    private BuyOperation buyOperation;
    private SellOperation sellOperation;

    public OperationFactory() {
        this.buyOperation = new BuyOperation();
        this.sellOperation = new SellOperation();
    }

    /**
     * Factory to return the correct {@link StrategyOperationTaxes} by {@link OperationType}.
     * @param type {@link OperationType} buy or sell
     * @return {@link BuyOperation} or {@link SellOperation}
     */
    public StrategyOperationTaxes get(OperationType type) {
        return switch (type) {
            case OperationType.BUY -> buyOperation;
            case OperationType.SELL -> sellOperation;
        };
    }
}
