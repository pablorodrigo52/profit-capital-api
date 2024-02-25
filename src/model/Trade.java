package src.model;

import java.util.ArrayList;
import java.util.List;

public class Trade {
    private List<List<Operation>> operations;

    public Trade() {
        this.operations = new ArrayList<>();
    }

    public List<List<Operation>> getOperations() {
        return this.operations;
    }

    public void addOperations(List<Operation> operations) {
        this.operations.add(operations);
    }

    @Override
    public String toString() {
        StringBuilder accumulator = new StringBuilder();
        for(List<Operation> ops: operations) {
            for (Operation op: ops) {
                accumulator.append(op.toString());
            }
            accumulator.append("\n");
        }
        return accumulator.toString();
    }
}
