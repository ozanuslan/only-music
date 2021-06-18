package model;

public class Administrator extends User {
    public static enum authLevel{
        LOW,
        HIGH
    }

    protected authLevel auth;

    public Administrator(String username,String name, String surname, String email, int id,
                         Administrator.authLevel auth) {
        super(username,name, surname, email, id);
        this.auth = auth;
    }

    public authLevel getAuth() {
        return auth;
    }

    public void setAuth(authLevel auth) {
        this.auth = auth;
    }

}
