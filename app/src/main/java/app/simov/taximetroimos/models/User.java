package app.simov.taximetroimos.models;

public class User {
    String  id;
    String  nombre;
    String  email;

    public User() {

    }

    public User(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
