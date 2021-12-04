package dk.au.mad21fall.projekt.rus_app.Models;

public class Drinks {
    private String id;
    private String name;
    private double price;
    private String thumbnailURL;
    public String amount;

    public Drinks() {
        this.name = "";
        this.price = 0.0;
        this.thumbnailURL = "";
        this.amount = "0";
    }

    public String getName() {
        return name;
    }

    public void setAmount(String amount){
        this.amount = amount;
    }

    public String getAmount(){
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getId(){return this.id;}

    public void setId(String id){this.id=id;}

}
