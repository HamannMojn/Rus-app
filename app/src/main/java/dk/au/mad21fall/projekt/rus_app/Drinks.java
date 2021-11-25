package dk.au.mad21fall.projekt.rus_app;

public class Drinks {

    private String name;
    private double price;
    private String ThumbnailURL;

    public Drinks(String name, double price, String thumbnailURL) {
        this.name = name;
        this.price = price;
        ThumbnailURL = thumbnailURL;
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
        return ThumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        ThumbnailURL = thumbnailURL;
    }
}
