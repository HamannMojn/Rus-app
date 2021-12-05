package dk.au.mad21fall.projekt.rus_app.Models;

public class Purchases {

    private int Amount;
    private String TutorName;
    private String DrinkName;

    public Purchases() {
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getTutorID() {
        return TutorName;
    }

    public void setTutorID(String tutorID) {
        TutorName = tutorID;
    }

    public String getDrinkID() {
        return DrinkName;
    }

    public void setDrinkID(String drinkID) {
        DrinkName = drinkID;
    }
}
