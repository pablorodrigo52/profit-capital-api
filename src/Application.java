package src;

import src.application.CapitalGainApplication;

public class Application {
    
    private static final CapitalGainApplication capitalGainApplication 
        = new CapitalGainApplication();

    public static void main (String[] args) {
        System.out.println("--------------------- STARTING ---------------------");
        capitalGainApplication.calculateTaxes();        
        System.out.println("--------------------- ENDING ---------------------");
    }
}