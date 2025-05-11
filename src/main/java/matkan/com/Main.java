package matkan.com;

import matkan.com.core.ApplicationRunner;


public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java -jar app.jar <orders.json> <paymentmethods.json>");
            System.exit(1);
        }

        try {
            new ApplicationRunner().run(args[0], args[1]);
        } catch (Exception e) {
            System.err.println("Application error " + e.getMessage());
            System.exit(2);
        }
    }

}