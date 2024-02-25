package src.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import src.model.Operation;
import src.model.Trade;

public class InputReader {    

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * readFromStdin.
     * Reads a command line input from user. Stops reading when the last line is empty or null.
     * @return a string concatened with all inputed data by user
     * @throws Exception when occurs a error on read input user
     */
    public static Trade readFromStdin() throws Exception {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
        final Trade trades = new Trade();

        try {
            String line = "";
            while ((line = in.readLine()) != null && 
                !line.isEmpty()) {
                    List<Operation> operations = toOperationList(line);
                    trades.addOperations(operations);
            }
        } catch (IOException e) {
            final String errorMessage = "Error on read from stdin. Cannot continue due: " + e.getMessage();
            System.out.println(errorMessage);
            throw new Exception(errorMessage);
            
        }
        return trades;
    }

    /**
     * toOperationList.
     * Receives a string json array with operation structure:
     * [{"operation":"buy", "unit-cost":10.00, "quantity": 10000}, ... ], and return a list of
     * operation objects.
     * @param input string json array 
     * @return List of {@link Operation}
     * @throws Exception when occurs a error on parse
     */
    private static List<Operation> toOperationList(String input) throws Exception {
        try {
            return mapper.readValue(input, new TypeReference<List<Operation>>(){});
        } catch (Exception e) {
            final String errorMessage = "Error on parse string to operations list. Cannot continue due: " + e.getMessage();
            System.out.println(errorMessage);
            throw new Exception(errorMessage);
        }
    }
}
