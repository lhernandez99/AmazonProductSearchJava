package main;

import static spark.Spark.*;
import com.google.gson.Gson;

import java.util.List;

/**
 * REST API server using Spark to handle product search requests.
 */
public class Server {
    public static void main(String[] args) {
        // Set the server port
        port(4567);

        // Serve static files from the "public" folder (src/main/resources/public)
        staticFiles.location("/public");

        // Create and load product search engine
        ProductSearchEngine searchEngine = new ProductSearchEngine();
        searchEngine.loadProductsFromCSV("data/sample_products.csv");

        // Search API endpoint: /search?query=term&minPrice=0&maxPrice=100&minRating=4.5&category=Electronics
        get("/search", (req, res) -> {
            String query = req.queryParams("query");
            double minPrice = parseDoubleOrDefault(req.queryParams("minPrice"), 0);
            double maxPrice = parseDoubleOrDefault(req.queryParams("maxPrice"), 9999);
            double minRating = parseDoubleOrDefault(req.queryParams("minRating"), 0);
            String category = req.queryParams("category");
            if (category == null) category = "";

            List<Product> results = searchEngine.search(query, minPrice, maxPrice, minRating, category);

            res.type("application/json");
            return new Gson().toJson(results);
        });

        // Optional: Health check endpoint (optional, not overriding /)
        //get("/status", (req, res) -> "Amazon Product Search API is running!");
        get("/test", (req, res) -> "Hello from test route!");

        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
});

    }

    /**
     * Helper method to safely parse doubles from query params.
     */
    private static double parseDoubleOrDefault(String input, double defaultValue) {
        try {
            return input == null ? defaultValue : Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
