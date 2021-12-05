package dk.au.mad21fall.projekt.rus_app.Models;

import java.util.ArrayList;

public class purchaseForTutor {
    private String tutorName;
    private ArrayList<Purchases> purchases;

    public purchaseForTutor(String name) {
        tutorName = name;
        purchases = new ArrayList<>();
    }

    public String getTutorName() {
        return tutorName;
    }

    public void setTutorName(String tutorName) {
        this.tutorName = tutorName;
    }

    public ArrayList<Purchases> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchases> purchases) {
        this.purchases = purchases;
    }

    public void addPurchase(Purchases purchase) {
        purchases.add(purchase);
    }
}
