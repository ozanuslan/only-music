package model;

public abstract class User {
    protected String username, name, surname, email;
    protected int id, privilegeLevel;

    public User(String username, String name, String surname, String email, int id, int privilegeLevel) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.id = id;
        this.privilegeLevel = privilegeLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPrivilegeLevel() { return privilegeLevel; }

    public void setPrivilegeLevel(int privilegeLevel) {this.privilegeLevel = privilegeLevel; }
}
