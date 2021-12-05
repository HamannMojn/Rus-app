package dk.au.mad21fall.projekt.rus_app.Models;

public class Purchases {

    private String drinksId;
    private String tutorId;
    private int amount;
    private double value;

    public String getDrinksId() {
        return drinksId;
    }

    public void setDrinksId(String drinksId) {
        this.drinksId = drinksId;
    }

    public String getTutorId() {
        return tutorId;
    }

    public void setTutorId(String tutorId) {
        this.tutorId = tutorId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
