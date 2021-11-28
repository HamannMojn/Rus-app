package dk.au.mad21fall.projekt.rus_app.Models;

public class Drinks {

    private String name;
    private double price;
    private String thumbnailURL;

    public Drinks(String name, double price, String thumbnailURL) {
        this.name = name;
        this.price = price;
        this.thumbnailURL = thumbnailURL;
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
