package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @author Tandem 21
 */
public class Utils {

    /**
     * @param instruction caption to be printed
     * @return entered text
     */
    protected static String readString(String instruction) {

        System.out.print(instruction);

        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();

        return text;
    }

    /**
     * @param instruction caption to be printed
     * @return entered text
     */
    protected static int readInt(String instruction) {

        int number = 0;
        boolean successfulParse = false;

        while (!successfulParse) {
            try {

                number = Integer.parseInt(readString(instruction));
                successfulParse = true;

            } catch (NumberFormatException e) {

                System.out.println("Please enter a number. \n");

            }
        }

        return number;

    }

    /**
     * Prints the result of a SQL query
     *
     * @param resultSet result of a query
     * @return number of rows of the query
     */
    public static int printResultSet(ResultSet resultSet) {

        int nRows = 0;

        try {

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            while (resultSet.next()) {

                if (nRows == 0) {

                    for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
                        System.out.print(resultSetMetaData.getColumnName(i));
                        if (i == resultSetMetaData.getColumnCount()) {
                            System.out.print("\n");
                        } else {
                            System.out.print("\t");
                        }
                    }
                }

                for (int col = 1; col <= resultSetMetaData.getColumnCount(); col++) {
                    System.out.print(resultSet.getObject(col));
                    if (col == resultSetMetaData.getColumnCount()) {
                        System.out.print("\n");
                    } else {
                        System.out.print("\t");
                    }
                }

                nRows++;

            }

        } catch (SQLException se) {

            return -1;

        }

        if (nRows == 0 && Program.debugMode) {

            System.out.println("No rows found!");

        }

        return nRows;

    }

    /**
     * Decode and print all chained SQL exceptions
     *
     * @param sqlException SQL exception
     */
    public static void printEverySQLException(SQLException sqlException) {

        SQLException currentSQLException = sqlException;

        while (currentSQLException != null) {

            printSQLException(currentSQLException);
            currentSQLException = currentSQLException.getNextException();

        }

    }

    /**
     * Prints a single SQL exception
     *
     * @param sqlException single SQL exception
     */
    public static void printSQLException(SQLException sqlException) {

        System.out.println();

        System.out.println("*** SQL exception: ***********");
        System.out.println("  - Error message     : " + sqlException.getMessage());
        System.out.println("  - SQL state         : " + sqlException.getSQLState());
        System.out.println("  - Vendor error code : " + sqlException.getErrorCode());
        System.out.println("******************************");
    }

    /**
     * Prints debug information if debug mode is enabled
     *
     * @param information debug information to print
     */
    public static void printDebugInfo(String information) {
        if (Program.debugMode) {
            System.out.println(">> DEBUG INFO: " + information);
        }
    }

    /*
     * Decode SQL transaction isolation levels
     */
    public static String decodeTransactionIsolationLevel(int transactionIsolationLevel) {

        switch (transactionIsolationLevel) {

            case Connection.TRANSACTION_NONE:
                return "No transactions supported";
            case Connection.TRANSACTION_READ_COMMITTED:
                return "Read committed";
            case Connection.TRANSACTION_READ_UNCOMMITTED:
                return "Read uncommitted";
            case Connection.TRANSACTION_REPEATABLE_READ:
                return "Repeatable read";
            case Connection.TRANSACTION_SERIALIZABLE:
                return "Serializable";
            default:
                return "Unknown trasaction isolation level";
        }

    }

    /**
     * Calculates the difference between two dates
     *
     * @param beginDate begin of the interval
     * @param endDate   end of the interval
     * @return difference in days
     * @throws ParseException when an incorrect string is parsed to a date
     */
    protected static int calculateDifferenceDays(String beginDate, String endDate) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = dateFormat.parse(beginDate);
        Date d2 = dateFormat.parse(endDate);

        long diff = d2.getTime() - d1.getTime();

        // converts the time difference from milliseconds to days
        return ((int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
    }

    /**
     * Checks whether {@code beginDate} is before {@code endDate}
     *
     * @param beginDate begin of the interval
     * @param endDate   end of the interval
     * @return whether it's correct
     * @throws ParseException when an incorrect string is parsed to a date
     */
    protected static boolean checkPeriodValidity(String beginDate, String endDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date d1 = dateFormat.parse(beginDate);
        Date d2 = dateFormat.parse(endDate);

        return d1.before(d2);
    }
}
