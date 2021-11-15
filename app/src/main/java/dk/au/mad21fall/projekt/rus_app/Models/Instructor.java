package dk.au.mad21fall.projekt.rus_app.Models;

public class Instructor {
    public int Id;
    public String Name;
    public String Role;
    public double Consumed;

    public Instructor(int id, String name, String role, double consumed) {
        Id = id;
        Name = name;
        Role = role;
        Consumed = consumed;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public double getConsumed() {
        return Consumed;
    }

    public void setConsumed(double consumed) {
        this.Consumed = consumed;
    }


}
