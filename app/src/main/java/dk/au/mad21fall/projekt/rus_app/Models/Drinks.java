package dk.au.mad21fall.projekt.rus_app.Models;

public class Drinks {

    private String name;
    private double price;
    private String thumbnailURL;

    public Drinks() {
        this.name = "";
        this.price = 0.0;
        this.thumbnailURL = "";
    }

    public String getName() {
        return name;
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
}
