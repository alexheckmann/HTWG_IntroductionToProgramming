package exercise10.task03;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InvalidAccessException {

        Human pilot = new Pilot("Timmy", 9);
        Human crewMember = new CrewMember("Tommy", 25);

        Human passenger1 = new Passenger("Hugo", 14);
        Human passenger2 = new Passenger("Marvin", 19);
        Human passenger3 = null;

        Flightsystem flightsystem = new Flightsystem();

        List<Human> passengerList = flightsystem.getPassengerList();
        passengerList.add(passenger1);
        passengerList.add(passenger2);

        flightsystem.printPassengerList(passenger3);

    }
}
