package src.infraestructure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputReader {    

    /**
     * readFromStdin.
     * Reads a command line input from user. Stops reading when the last line is empty or null.
     * @return a string concatened with all inputed data by user
     */
    public static String readFromStdin() {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // why buffered reader?
        final StringBuilder accumulator = new StringBuilder(); // why string builder? 

        try {
            String line = "";
            while ((line = in.readLine()) != null && 
                !line.isEmpty()) {
                accumulator.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error on read from stdin: " + e.getMessage());
        }
        return accumulator.toString();
    }
}
