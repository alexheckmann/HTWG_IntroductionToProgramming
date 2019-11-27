package sqlgenerator4;

@Entity
public class Student {

    @Column("id")
    @ID
    private int id;
    @Column("first_name")
    @NotNull
    private String name;
    @Column("surname")
    @NotNull
    private String surname;
    @Column("email_address")
    @Unique
    @NotNull
    private String emailaddress;

    public Student(int id, String name, String surname, String emailaddress) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.emailaddress = emailaddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }
}
