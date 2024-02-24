package src.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import src.infraestructure.InputReader;
import src.model.InvestmentStatus;
import src.model.Operation;
import src.model.Tax;
import src.statics.OperationType;

public class CapitalGainApplication {
    
    /**
     * calculateTaxes.
     * Function to calculate taxes from operations informed by user on stdin.
     */
    public void calculateTaxes() {
        final String operationsStringJson = InputReader.readFromStdin();        
        final Optional<List<Operation>> userOperations = Optional.ofNullable(toOperationList(operationsStringJson));
        final InvestmentStatus investmentStatus = new InvestmentStatus();

        if (userOperations.isPresent()) {
            // userOperations.get()
            //     .forEach(operation -> System.out.println(operation.getOperation()));
            userOperations.get()
                .forEach(operation -> calculateOperationTaxes(operation, investmentStatus));
        }

        toTaxesJson(investmentStatus.getTaxes());
    }

    private InvestmentStatus calculateOperationTaxes(
        final Operation operation, 
        final InvestmentStatus investmentStatus) {

            if (OperationType.BUY.equals(operation.getOperation())) { // compra
                // calcular preco medio ponderado de compra
                final BigDecimal investmentAverage = investmentStatus.getAveragePrice()
                    .multiply(
                        new BigDecimal(
                            investmentStatus.getStockAmount()));
                final BigDecimal operationCost = operation.getUnitCost()
                    .multiply(
                        new BigDecimal(
                            operation.getQuantity()));
                final BigDecimal dividend = 
                    new BigDecimal(investmentStatus.getStockAmount())
                    .add(new BigDecimal(operation.getQuantity()));
                final BigDecimal newAveragePrice = investmentAverage
                    .add(operationCost)
                    .divide(dividend);
                
                investmentStatus.setAveragePrice(newAveragePrice);
                investmentStatus.setStockAmount(investmentStatus.getStockAmount() + operation.getQuantity());
                investmentStatus.addTaxes(new Tax(BigDecimal.ZERO));
            } else { // venda
                if (operation.getUnitCost().compareTo(investmentStatus.getAveragePrice()) >= 0) { // se tiver lucro
                    final BigDecimal profit = operation.getUnitCost().subtract(investmentStatus.getAveragePrice());
                    final BigDecimal profitWithoutPreviousLoss = profit
                        .multiply(new BigDecimal(operation.getQuantity()))
                        .subtract(investmentStatus.getLoss()); 

                    // calculate 20% 
                    investmentStatus.addTaxes(
                        new Tax(profitWithoutPreviousLoss
                            .multiply(
                                new BigDecimal(0.2))));

                    // update loss 
                    final BigDecimal updatedLoss = investmentStatus.getLoss()
                        .subtract(profitWithoutPreviousLoss);
                    if (updatedLoss.compareTo(BigDecimal.ZERO) <= 0) {
                        investmentStatus.setLoss(BigDecimal.ZERO);
                    } else {
                        investmentStatus.setLoss(updatedLoss);
                    }

                } else { // se tiver preju
                    final BigDecimal unitLoss = investmentStatus.getAveragePrice()
                        .subtract(operation.getUnitCost());

                    final BigDecimal totalLoss = unitLoss
                    .multiply(
                        new BigDecimal(
                            operation.getQuantity()))
                    .add(investmentStatus.getLoss());

                    investmentStatus.setLoss(totalLoss);
                    investmentStatus.addTaxes(new Tax(BigDecimal.ZERO));
                }

                // update stock amount
                investmentStatus.setStockAmount(investmentStatus.getStockAmount() - operation.getQuantity());
            }
            return investmentStatus;
    }

    /**
     * toOperationList.
     * Receives a string json array with operation structure:
     * [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, ... ]
     * @param input string json array 
     * @return List of {@link Operation}
     */
    private List<Operation> toOperationList(String input) {
        if (!input.isBlank()) {
            final ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(input, new TypeReference<List<Operation>>(){});
            } catch (Exception e) {
                System.out.println("Error on parse string to operations list: " + e.getMessage());
            }
        }
        return null;
    }

    private String toTaxesJson(List<Tax> taxes) {
        try {
            return new ObjectMapper().writeValueAsString(taxes);
        } catch (Exception e) {
            System.out.println("Error on parse taxes object to string json: " + e.getMessage());
        }
        return "";
    }
}
