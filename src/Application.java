package src;

import src.service.CapitalGainService;

public class Application {

    private static CapitalGainService capitalGainService = new CapitalGainService();

    /**
     * Main class. 
     * @param args input arguments
     * @throws Exception if an error occours
     */
    public static void main (String[] args) throws Exception {
        capitalGainService.calculateTaxes();
    }
}