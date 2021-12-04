package dk.au.mad21fall.projekt.rus_app.Models;

import java.util.Comparator;

public class Team implements Comparable<Team> {
    private String name;
    private int amount;
    private String id;

    //Dont delete, used by firestore
    public Team(){}

    public Team(String _name){
        this.setName(_name);
        this.setAmount(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() { return amount; }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setId(String id) {this.id = id;}
    public String getId() {return this.id;}

    @Override
    public int compareTo(Team team) {
        int compareAmount=team.getAmount();

        return compareAmount-this.getAmount();
    }

}