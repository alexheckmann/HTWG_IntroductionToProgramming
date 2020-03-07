package labor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Tandem 21
 */
public class Queries {

    /**
     * Calculates the max value
     *
     * @param tableName  name of the table
     * @param columnName name of the column the max value shall be extracted from
     * @return scalar value
     * @throws SQLException
     */
    protected static int selectMaxValue(String tableName, String columnName) throws SQLException {

        String selectMaxValue =
                "SELECT MAX(" + columnName + ") + 1 " +
                        "FROM " + tableName;

        int maxValue = selectScalarIntValue(selectMaxValue);

        return maxValue;

    }

    /**
     * Calculates a scalar value
     *
     * @param sql SELECT statement
     * @return scalar value
     * @throws SQLException
     */
    protected static int selectScalarIntValue(String sql) throws SQLException {

        Utils.printDebugInfo("SELECT statement: " + sql);

        Statement statement = Program.connection.createStatement();

        ResultSet maxValueResult = statement.executeQuery(sql);
        maxValueResult.next();
        int maxValue = maxValueResult.getInt(1);
        Utils.printDebugInfo("Query result: " + maxValue);

        statement.close();

        return maxValue;
    }

    /**
     * Calculates a scalar value
     *
     * @param sql SELECT statement
     * @return scalar value
     * @throws SQLException
     */
    protected static double selectScalarDoubleValue(String sql) throws SQLException {

        Utils.printDebugInfo("SELECT statement: " + sql);

        Statement statement = Program.connection.createStatement();

        ResultSet scalarValueResult = statement.executeQuery(sql);
        scalarValueResult.next();
        double value = scalarValueResult.getDouble(1);
        Utils.printDebugInfo("Query result: " + value);

        statement.close();

        return value;
    }

    /**
     * Prints all records of a given table contained in the data base
     *
     * @param table table to select from
     * @return number of rows
     * @throws SQLException
     */
    protected static int selectTable(Table table) throws SQLException {


        String select = table.getQuery();
        Utils.printDebugInfo("SELECT statement: " + select);

        Statement statement = Program.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(select);

        int nRows = Utils.printResultSet(resultSet);

        statement.close();

        return nRows;
    }


    /**
     * Inserts a new record into the data base
     *
     * @param insertString DML statement
     * @param tableName    table to be inserted into
     * @throws SQLException
     */
    protected static void insert(String insertString, String tableName) throws SQLException {
        Utils.printDebugInfo("INSERT statement: " + insertString);
        Statement statement = Program.connection.createStatement();

        int affectedRows = statement.executeUpdate(insertString);
        Utils.printDebugInfo("Affected rows: " + affectedRows);

        statement.close();

        System.out.println();

        // if one row was affected
        if (affectedRows == 1) {

            System.out.println("... insertion of new " + tableName + " successful!");

        }
        // if no row or more than one was affected
        else {

            System.out.println("An error occured while inserting.");

        }
    }

    /**
     * Checks whether the given table contains records with a given string
     *
     * @param enteredString String to be checked
     * @return true if an element is found, otherwise false
     * @throws SQLException
     */
    protected static boolean contains(String enteredString, String tableName, String columnName) throws SQLException {

        String select =
                "SELECT " + columnName + " " +
                        "FROM " + tableName + " " +
                        "WHERE " + columnName + " = '" + enteredString + "'";
        Utils.printDebugInfo("SELECT statement: " + select);
        Statement statement = Program.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(select);

        while (resultSet.next()) {

            String currentString = resultSet.getString(1);

            if (enteredString.equals(currentString)) {

                statement.close();
                return true;

            }

        }

        statement.close();

        return false;
    }

    /**
     * Checks whether the given table contains records with a given id
     *
     * @param enteredID  ID to check
     * @param tableName  Name of the table to be searched
     * @param columnName Name of the column to be searched
     * @return {@code true} if an element is found, otherwise {@code false}
     * @throws SQLException
     */
    protected static boolean contains(int enteredID, String tableName, String columnName) throws SQLException {

        String select =
                "SELECT " + columnName +
                        " FROM " + tableName + " " +
                        "WHERE " + columnName + " = " + enteredID;
        Utils.printDebugInfo("SELECT statement: " + select);

        Statement statement = Program.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(select);


        while (resultSet.next()) {

            int currentID = resultSet.getInt(1);

            if (enteredID == currentID) {

                statement.close();
                return true;

            }

        }

        statement.close();

        return false;
    }

    /**
     * Checks whether a given flat is free at a given period of time
     *
     * @param flatID    ID of the flat to check
     * @param beginDate begin of the occupancy
     * @param endDate   end of the occupancy
     * @return {@code true} if the flat is free, otherwise {@code false}
     * @throws SQLException
     */
    protected static boolean checkFlatAvailability(int flatID, String beginDate, String endDate) throws SQLException {

        String selectFlatsOccupancy =
                "SELECT F.* " +
                        "FROM Ferienwohnung F LEFT OUTER JOIN Belegung B ON F.WohnungsID = B.Ferienwohnung " +
                        "WHERE F.WohnungsID = " + flatID + " AND " +
                        "( " +
                        "(B.Startdatum <= '" + beginDate + "' AND B.Enddatum >= '" + endDate + "')  OR " +
                        "(B.Startdatum < '" + beginDate + "' AND B.Enddatum >= '" + beginDate + "')  OR " +
                        "(B.Startdatum < '" + endDate + "' AND B.Enddatum > '" + endDate + "')  OR " +
                        "(B.Startdatum >= '" + beginDate + "' AND B.Enddatum <= '" + endDate + "') " +
                        ")";

        Statement statement = Program.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectFlatsOccupancy);

        int nRows = Utils.printResultSet(resultSet);
        statement.close();

        return nRows == 0;

    }

}
