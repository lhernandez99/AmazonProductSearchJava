package main;
public class Product {
    private String name;
    private String description;
    private String category;
    private double price;
    private double rating;

    public Product(String name, String description, String category, double price, double rating) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.rating = rating;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public double getRating() { return rating; }

    @Override
    public String toString() {
        return String.format(
            "Name: %s\nDescription: %s\nCategory: %s\nPrice: $%.2f\nRating: %.1f\n",
            name, description, category, price, rating
        );
    }
}
