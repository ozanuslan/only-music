package model;

public class Administrator extends User {

    public Administrator(String username,String name, String surname, String email, int id,
                         int privilegeLevel) {
        super(username,name, surname, email, id,privilegeLevel);
    }
}
