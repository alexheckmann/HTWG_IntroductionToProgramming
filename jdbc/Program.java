package jdbc;

import oracle.jdbc.driver.OracleDriver;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Program {

    protected static Connection connection;
    protected static boolean debugMode = false;

    protected static void connect() {
        try {
            OracleDriver oracleDriver = new OracleDriver();
            DriverManager.registerDriver(oracleDriver);

            final String DB_URL = "jdbc:oracle:thin:@oracle12c.in.htwg-konstanz.de:1521:ora12c";
            final String DB_USERNAME = "dbs21";
            final String DB_PASSWORD = "alexandriner";


            System.out.println("Trying to connect to the data base...");
            Program.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("... connection successful.");

        } catch (SQLException e) {

            Utils.printEverySQLException(e);

        }
    }


    public static void insertCustomer() throws ParseException {

        System.out.println("Inserting a new customer...");

        try {

            int userID = Queries.selectMaxValue("Kunde K", "K.UserID");

            String surname = Utils.readString("Please enter the surname: ");
            Utils.printDebugInfo("Entered surname: " + surname);

            String name = Utils.readString("Please enter the name: ");
            Utils.printDebugInfo("Entered name: " + name);

            String email = Utils.readString("Please enter the email address: ");
            Utils.printDebugInfo("Entered email address: " + email);

            String birthday = Format.validate(Utils.readString("Please enter the birthday; \n " +
                    "The date format is 'DD.MM.YYYY': "), Format.BIRTHDAY_DATE);
            Utils.printDebugInfo("Entered birthday: " + birthday);

            String phoneNumber = Utils.readString("Please enter the phone number: ");
            Utils.printDebugInfo("Entered phone number: " + phoneNumber);

            int adressID = Queries.selectMaxValue("Adresse A", "A.AdressID");

            String streetName = Utils.readString("Please enter the street: ");
            Utils.printDebugInfo("Entered street: " + streetName);

            String houseNumber = Utils.readString("Please enter the house number: ");
            Utils.printDebugInfo("Entered house number: " + houseNumber);

            String postCode = Utils.readString("Please enter the post code of the city: ");
            Utils.printDebugInfo("Entered post code: " + postCode);

            String cityName;

            System.out.println("These are the available cities and the country they lie in: ");
            if (Queries.selectTable(Table.CITY) == 0) {
                System.out.println("No cities found. Cancelling...");
                return;
            }

            cityName = Utils.readString("Please enter one of the cities mentioned above: ");
            Utils.printDebugInfo("Entered post code: " + cityName);

            // check whether the city is contained in the data base
            while (!(Queries.contains(cityName, "Ort O", "O.Name"))) {

                cityName = Utils.readString("City not found. Please enter one of the cities mentioned above: ");
                Utils.printDebugInfo("Entered post code: " + cityName);

            }

            Utils.printDebugInfo("Entered city: " + cityName);

            String insertAddress = "INSERT INTO Adresse(AdressID, Strasse, HausNr, PLZ, Ort) " +
                    "VALUES (" + adressID + ", '" + streetName + "', '" + houseNumber + "', '" + postCode + "', '" + cityName + "')";
            Queries.insert(insertAddress, "address");

            String iban = Utils.readString("Enter the IBAN: ");

            String bic = Utils.readString("Enter the BIC: ");

            int bankAccountNumber = Utils.readInt("Please enter your bank account number: ");

            int sortCode = Utils.readInt("Please enter the sort code of your bank: ");

            String insertBank = "INSERT INTO Bankverbindung(IBAN, BIC, BLZ, KontoNr) " +
                    "VALUES ('" + iban + "', '" + bic + "', " + sortCode + ", " + bankAccountNumber + ")";
            Queries.insert(insertBank, "bank information");

            String insertCustomer = "INSERT INTO Kunde(UserID, Name, Vorname, Email, Geburtsdatum, TelefonNr, Adresse, IBAN) " +
                    "VALUES (" + userID + ", '" + name + "', '" + surname + "', '" + email + "', '" +
                    birthday + "', '" + phoneNumber + "', " + adressID + ", '" + iban + "')";
            Queries.insert(insertCustomer, "customer");

            connection.commit();
            Utils.printDebugInfo("Transaction committed.");

        } catch (SQLException e1) {

            Utils.printEverySQLException(e1);

            // rollback, if an error occurs
            try {

                connection.rollback();
                Utils.printDebugInfo("Initiating rollback...");

            } catch (SQLException e2) {

                Utils.printEverySQLException(e2);

            }

        }

    }

    /**
     * Searches the data base for a name or surname similar to a text snippet entered in the console.
     */
    public static void searchCustomer() throws ParseException {
        System.out.println("Searching a customer...");

        String name = Utils.readString("Please enter a name: ");
        Utils.printDebugInfo("Entered name to search for: " + name);

        try {

            String selectCustomers =
                    "SELECT K.UserID, K.Name, K.Vorname, K.Email, K.Geburtsdatum, K.TelefonNr, B.*, A.Strasse, A.HausNr, A.PLZ, O.Name, L.Name " +
                            "FROM Kunde K, Bankverbindung B, Adresse A, Ort O, Land L " +
                            "WHERE ((UPPER(K.Name) LIKE UPPER('%" + name + "%') OR " +
                            "UPPER(K.Vorname) LIKE UPPER('%" + name + "%')) AND " +
                            "K.IBAN = B.IBAN AND " +
                            "K.Adresse = A.AdressID AND " +
                            "A.Ort = O.Name AND " +
                            "O.Land = L.ISO" +
                            ")" +
                            "ORDER BY K.UserID";

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(selectCustomers);
            System.out.println();
            Utils.printResultSet(resultSet);
            connection.commit();


        } catch (SQLException e) {

            Utils.printEverySQLException(e);

        }

    }

    public static void createOccupancy() throws ParseException {

        System.out.println("Booking a flat...");

        try {

            System.out.println("These are the available customers: ");
            if (Queries.selectTable(Table.CUSTOMER) == 0) {
                System.out.println("No customers found. Cancelling...");
                return;
            }

            int userID = Utils.readInt("Please enter the userID of the user you want to book for: ");

            while (!Queries.contains(userID, "Kunde", "UserID")) {
                userID = Utils.readInt("User not found. Please enter one of the user IDs mentioned above: ");
            }

            Utils.printDebugInfo("Entered userID: " + userID);

            if (Queries.selectTable(Table.FLAT) == 0) {
                System.out.println("No flats found. Cancelling...");
                return;
            }

            int flatID = Utils.readInt("Please enter the flatID of the flat you want to book for: ");

            while (!Queries.contains(flatID, "Ferienwohnung", "WohnungsID")) {
                flatID = Utils.readInt("Flat not found. Please enter one of the flat IDs mentioned above: ");
            }

            Utils.printDebugInfo("Entered flatID: " + flatID);

            String beginDate = Format.validate(Utils.readString("Please enter the date " +
                    "the occupancy shall start at: "), Format.DATE);
            Utils.printDebugInfo("Start date of the occupancy: " + beginDate);
            String endDate = Format.validate(Utils.readString("Please enter the date " +
                    "the occupancy shall end at: "), Format.DATE);
            Utils.printDebugInfo("End date of the occupancy: " + endDate);

            boolean validTimePeriod = Utils.checkPeriodValidity(beginDate, endDate);

            while (!validTimePeriod) {

                int decision = Utils.readInt("Please enter if you want to \n" +
                        "1) enter a new begin date, \n" +
                        "2) enter a new end date. \n" +
                        "Please enter your decision: ");
                boolean validDecision = false;

                while (!validDecision) {
                    switch (decision) {
                        case 1:
                            validDecision = true;
                            beginDate = Format.validate(Utils.readString("Please enter a correct begin date: "), Format.DATE);
                            validTimePeriod = Utils.checkPeriodValidity(beginDate, endDate);
                            break;

                        case 2:
                            validDecision = true;
                            endDate = Format.validate(Utils.readString("Please enter a correct end date: "), Format.DATE);
                            validTimePeriod = Utils.checkPeriodValidity(beginDate, endDate);
                            break;
                        default:
                            System.out.println("Please enter a correct number. ");
                    }
                }
            }

            boolean available = Queries.checkFlatAvailability(flatID, beginDate, endDate);

            while (!available) {

                System.out.println("The desired flat is not available in this period. " +
                        "Do you wish to enter another period or cancel the occupancy?");
                System.out.println("1) Enter new time period");
                System.out.println("2) Cancel");


                int decision = Utils.readInt("Please enter which function you wanna use: ");
                Utils.printDebugInfo("decision is " + decision);

                boolean validDecision = false;

                while (!validDecision) {

                    switch (decision) {

                        case 1:
                            validDecision = true;
                            beginDate = Format.validate(Utils.readString("Please enter the date " +
                                    "the occupancy shall start at: "), Format.DATE);
                            Utils.printDebugInfo("Start date of the occupancy: " + beginDate);
                            endDate = Format.validate(Utils.readString("Please enter the date " +
                                    "the occupancy shall end at: "), Format.DATE);
                            Utils.printDebugInfo("End date of the occupancy: " + endDate);

                            available = Queries.checkFlatAvailability(flatID, beginDate, endDate);

                            break;

                        case 2:

                            System.out.println("Cancelling... ");
                            return;

                        default:
                            System.out.println("Not a valid option. ");
                    }
                }
            }

            System.out.println("The desired flat is available in this period. " +
                    "Do you wish to make a reservation, book the flat or cancel the process?");
            System.out.println("1) Make a reservation");
            System.out.println("2) Book the flat");
            System.out.println("3) Cancel");

            int decision = Utils.readInt("Please enter which function you wanna use: ");
            Utils.printDebugInfo("decision is " + decision);

            boolean validDecision = false;
            while (!validDecision) {

                if (decision == 1 || decision == 2) {

                    validDecision = true;
                    int occupancyID = Queries.selectMaxValue("Belegung B", "B.BuchungsNr");

                    DateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                    String currentDate = simpleDateFormat.format(new Date());

                    if (decision == 1) {

                        String insertOccupancy = "INSERT INTO Belegung(BuchungsNr, Belegungsdatum, UserID, " +
                                "Ferienwohnung, Status, Startdatum, Enddatum) VALUES (" +
                                occupancyID + ", '" + currentDate + "', " + userID + ", " + flatID +
                                ", " + "'reserviert', '" + beginDate + "', '" + endDate + "')";

                        Queries.insert(insertOccupancy, "occupancy");

                        connection.commit();

                    } else {

                        String insertOccupancy = "INSERT INTO Belegung(BuchungsNr, Belegungsdatum, UserID, " +
                                "Ferienwohnung, Status, Startdatum, Enddatum) VALUES (" +
                                occupancyID + ", '" + currentDate + "', " + userID + ", + " + flatID +
                                ", " + "'gebucht', '" + beginDate + "', '" + endDate + "')";
                        Queries.insert(insertOccupancy, "occupancy");

                        int invoiceID = Queries.selectMaxValue("Rechnung R", "R.RechnungsNr");

                        String selectFlatPrice = "SELECT F.Preis " +
                                "FROM Ferienwohnung F " +
                                "WHERE F.WohnungsID = " + flatID;

                        int amountOfDays = Utils.calculateDifferenceDays(beginDate, endDate);
                        Utils.printDebugInfo("Amount of days: " + amountOfDays);
                        double balanceDue = Queries.selectScalarDoubleValue(selectFlatPrice) * amountOfDays;
                        Utils.printDebugInfo("Balance due: " + balanceDue);

                        String insertInvoice = "INSERT INTO Rechnung(RechnungsNr, Rechnungsdatum, BuchungsNr, Betrag, Zahlungseingang) VALUES (" +
                                invoiceID + ", '" + currentDate + "', " + occupancyID + ", + " + balanceDue + ", " + "NULL )";

                        Queries.insert(insertInvoice, "invoice");

                        connection.commit();

                    }

                } else if (decision == 3) {

                    System.out.println("Cancelling...");
                    return;

                } else {

                    System.out.println("Not a valid option. ");

                }
            }

        } catch (SQLException e) {

            Utils.printEverySQLException(e);

            try {

                connection.rollback();
                Utils.printDebugInfo("Initiating rollback...");

            } catch (SQLException e1) {

                Utils.printEverySQLException(e1);

            }

        }
    }

    public static void deleteOccupancy() {

        System.out.println("Deleting a flat...");

        try {

            System.out.println("These are the available occupancies: ");

            if (Queries.selectTable(Table.OCCUPANCY) == 0) {
                System.out.println("No occupancies found. Cancelling...");
                return;
            }

            int occupancyID = Utils.readInt("Please enter one the ID of the occupancies mentioned above: ");

            while (!(Queries.contains(occupancyID, "Belegung B", "B.BuchungsNr"))) {

                occupancyID = Utils.readInt("Occupancy not found. " +
                        "Please enter one the ID of the occupancies mentioned above: ");

            }

            String deleteInvoice = "DELETE FROM Rechnung " +
                    "WHERE BuchungsNr = " + occupancyID;

            Statement invoiceStatement = connection.createStatement();
            int affectedRecords = invoiceStatement.executeUpdate(deleteInvoice);

            if (debugMode) {

                if (affectedRecords == 0) {

                    System.out.println("... no corresponding invoice found.");

                } else if (affectedRecords == 1) {

                    System.out.println("... deletion of the invoice was successful");

                } else {

                    System.out.println("Something went wrong.");

                }

            }


            String deleteOccupancy = "DELETE FROM Belegung " +
                    "WHERE BuchungsNr = " + occupancyID;

            Statement occupancyStatement = connection.createStatement();
            affectedRecords += occupancyStatement.executeUpdate(deleteOccupancy);

            occupancyStatement.close();

            if (affectedRecords == 1 || affectedRecords == 2) {

                System.out.println("... deletion successful");

            } else {

                System.out.println("Something went wrong.");

            }

            connection.commit();
            Utils.printDebugInfo("Transaction committed.");

        } catch (SQLException e1) {

            Utils.printEverySQLException(e1);

            try {

                connection.rollback();
                Utils.printDebugInfo("Initiating rollback...");

            } catch (SQLException e2) {

                Utils.printEverySQLException(e2);

            }
        }

    }
}
