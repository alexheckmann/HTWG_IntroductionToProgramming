package printattributes;

public class City {
    private String zipCode;
    private String name;
    private String state;

    public City(String zipCode, String name, String state) {
        this.zipCode = zipCode;
        this.name = name;
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}