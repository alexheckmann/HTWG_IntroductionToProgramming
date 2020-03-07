package exercise10.task03;

import java.util.List;
import java.util.ArrayList;

public class Flightsystem {

    private List<Human> passengerList = new ArrayList<>();

    public List<Human> getPassengerList() {
        return passengerList;
    }

    public void printPassengerList(Human human) throws InvalidAccessException {
        if (passengerList.isEmpty()){
            return;
        }
        if (human instanceof Pilot){
            for (Human h: passengerList) {
                System.out.println(h.toString());
            }
        } else if (human instanceof CrewMember){
            for (Human h: passengerList) {
                System.out.println(h.getName());
            }
        } else {
            throw new InvalidAccessException("Because of security and privacy measures, you're not allowed to access the passenger list.");
        }
    }
}
