package src.service;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import src.model.InvestmentStatus;
import src.model.Operation;
import src.statics.OperationType;
import src.model.Trade;
import src.service.strategy.factory.OperationFactory;
import src.model.Tax;
import src.util.InputReader;
import src.validator.OperationValidator;

public class CapitalGainService {

    private OperationFactory operationFactory;
    private ObjectMapper mapper;

    public CapitalGainService() {
        this.operationFactory = new OperationFactory();
        this.mapper = new ObjectMapper();
        this.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
    
    /**
     * CalculateTaxes. 
     * Function to orchestrates operations informed by user on stdin to calculate taxes.
     * @throws Exception when occurs an error on flow.
     */
    public void calculateTaxes() throws Exception {
        final Trade trades = InputReader.readFromStdin();

        trades
        .getOperations()
        .stream()
        .map(operations -> operations.stream()
            .filter(operation -> OperationValidator.isValid(operation))
            .reduce(new InvestmentStatus(), 
                   (investmentStatus, operation) -> calculateOperationTaxes(operation, investmentStatus), 
                   (s1, s2) -> s1))
        .forEach(investmentStatus -> System.out.println(toTaxesJson(investmentStatus.getTaxes())));
    }

    /**
     * calculateOperationTaxes.
     * Given a {@link Operation} with {@link OperationType} updates the {@link InvestmentStatus} 
     * with taxes and other stock attributes.
     * @param operation Operation inputed by user
     * @param investmentStatus Object to mantain the most recent state of operation. 
     * @return {@link InvestmentStatus} updated.
     */
    private InvestmentStatus calculateOperationTaxes(
        final Operation operation, 
        final InvestmentStatus investmentStatus) {

            return operationFactory
                .get(operation.getOperation())
                .calculateTaxes(operation, investmentStatus);
    }

    /**
     * toTaxesJson.
     * Parse a list of {@link Tax} to string json. 
     * @param taxes {@link Tax} object.
     * @return String json to be showed on stdout.
     * @throws RuntimeException when parse fails.
     */
    private String toTaxesJson(List<Tax> taxes) throws RuntimeException {
        try {
            return this.mapper.writeValueAsString(taxes);
        } catch (Exception e) {
            final String errorMessage = "Error on parse taxes object to string json: " + e.getMessage();
            System.out.println(errorMessage);
            throw new RuntimeException(e);
        }
    }
}
