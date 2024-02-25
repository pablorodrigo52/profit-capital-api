package src.validator;

import java.math.BigDecimal;
import java.util.Objects;

import src.model.Operation;

public class OperationValidator {
    
    /**
     * isValid for operation. 
     * A validator to verify if operation is valid to be processed.
     * @param operation {@link Operation} inputed by user
     * @return true if valid.
     * @throws RuntimeException if not valid (the flow must stop).
     */
    public static Boolean isValid(Operation operation) throws RuntimeException {
        if (Objects.isNull(operation) ||
            Objects.isNull(operation.getQuantity()) ||
            operation.getQuantity() <= 0 || 
            operation.getUnitCost().compareTo(BigDecimal.ZERO) <= 0) {
                final String errorMessage = "Cannot continue proccess due to: Invalid parameters";
                System.out.println(errorMessage);
                throw new RuntimeException(errorMessage);
        }
        return Boolean.TRUE;
    }
}
