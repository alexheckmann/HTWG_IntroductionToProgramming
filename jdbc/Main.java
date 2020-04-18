package jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * @author Tandem 21
 */
public class Main {

    /**
     * Asks the user for the desired part of the program and reads in the decision as an integer number.
     */
    private static void printOptions() throws ParseException {

        while (true) {

            System.out.println();
            System.out.println("1) Insert a customer");
            System.out.println("2) Search a customer by name");
            System.out.println("3) Book a flat");
            System.out.println("4) Delete a flat");
            System.out.println("5) Exit the program.");

            if (Program.debugMode) {
                System.out.println("6) Disable debug mode");
            } else {
                System.out.println("6) Enable debug mode");
            }
            System.out.println();

            int decision = Utils.readInt("Please enter which function you wanna use: ");
            Utils.printDebugInfo("decision is " + decision);
            executeProgram(decision);

        }
    }

    /**
     * Executes a part of the program specified by {@code decision}
     *
     * @param decision number indicating which part of the program you want to run
     */
    private static void executeProgram(int decision) throws ParseException {

        boolean validDecision = false;

        while (!validDecision) {


            switch (decision) {

                case 1:
                    validDecision = true;
                    Program.insertCustomer();
                    break;

                case 2:
                    validDecision = true;
                    Program.searchCustomer();
                    break;

                case 3:
                    validDecision = true;
                    Program.createOccupancy();
                    break;

                case 4:
                    validDecision = true;
                    Program.deleteOccupancy();
                    break;

                case 5:
                    System.out.println("Exiting...");

                    try {

                        if (Program.connection != null && !Program.connection.isClosed()) {
                            Program.connection.close();
                        }

                    } catch (SQLException e) {

                        Utils.printEverySQLException(e);
                        System.exit(-1);

                    }


                    System.exit(0);

                case 6:
                    validDecision = true;

                    if (!Program.debugMode) {
                        Program.debugMode = true;

                        System.out.println("\n Debug mode enabled.");
                    } else {

                        Program.debugMode = false;
                        System.out.println("\n Debug mode disabled.");

                    }
                    break;

                default:
                    decision = Utils.readInt("Not a valid option, please enter another decision: ");
                    Utils.printDebugInfo("decision is " + decision);
                    System.out.println();
            }
        }
    }

    /**
     * Reads in all relevant personal information regarding a new customer
     * including account information and address, validating them and inserting them into the corresponding tables
     * in the data base.
     */


    public static void main(String[] args) throws ParseException {

        try {

            Program.connect();

            if (Program.connection.getMetaData().supportsTransactionIsolationLevel(Program.connection.TRANSACTION_SERIALIZABLE)) {
                // Set the transaction isolation level to serializable:
                // to be sure that the DBS uses serializability as correctness criterion
                System.out.println(" - transaction isolation level is: " + Program.connection.getTransactionIsolation()
                        + " (" + Utils.decodeTransactionIsolationLevel(Program.connection.getTransactionIsolation()) + ")");
                Program.connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println(" - transaction isolation level is now: " + Program.connection.getTransactionIsolation()
                        + " (" + Utils.decodeTransactionIsolationLevel(Program.connection.getTransactionIsolation()) + ")");
            }

            System.out.println("Auto Commit was " + Program.connection.getAutoCommit());
            Program.connection.setAutoCommit(false);
            System.out.println("Auto Commit is now " + Program.connection.getAutoCommit());

            System.out.println("... transaction parameters for connection set!");

            printOptions();

        } catch (SQLException e) {

            Utils.printEverySQLException(e);

        }

    }

}
