package main;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point for the Amazon-style product search app.
 * Allows keyword search with filters, ranking, and fallback recommendations.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductSearchEngine searchEngine = new ProductSearchEngine();

        // Load product data
        System.out.println("Loading products from CSV...");
        searchEngine.loadProductsFromCSV("data/sample_products.csv");

        System.out.println("=== Welcome to Amazon Product Search CLI ===");

        while (true) {
            System.out.print("\nEnter a keyword to search (or type 'exit'): ");
            String query = scanner.nextLine();
            if (query.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye!");
                break;
            }

            // Gather filter inputs
            System.out.print("Minimum price (default 0): ");
            double minPrice = getDoubleInput(scanner, 0);

            System.out.print("Maximum price (default 9999): ");
            double maxPrice = getDoubleInput(scanner, 9999);

            System.out.print("Minimum rating (0.0 to 5.0, default 0): ");
            double minRating = getDoubleInput(scanner, 0);

            System.out.print("Category (leave blank for any): ");
            String category = scanner.nextLine().trim();

            // Perform product search
            List<Product> results = searchEngine.search(query, minPrice, maxPrice, minRating, category);

            if (results.isEmpty()) {
                System.out.println("No products found with those filters. Trying a fallback recommendation...");

                List<Product> fallbackList = searchEngine.search(query, 0, 9999, 0, "");

                if (!fallbackList.isEmpty()) {
                    Product recommendation = fallbackList.get(0);
                    System.out.println("\nHere's a recommended alternative:");
                    System.out.println(recommendation);
                    System.out.println("--------------------------");
                } else {
                    System.out.println("Sorry, we couldn't find any similar products.");
                }

            } else {
                System.out.println("\n--- Search Results ---");
                for (Product product : results) {
                    System.out.println(product);
                    System.out.println("--------------------------");
                }
            }
        }

        scanner.close();
    }

    /**
     * Utility method to safely parse double input or return a default value.
     */
    private static double getDoubleInput(Scanner scanner, double defaultValue) {
        String input = scanner.nextLine();
        try {
            return input.isEmpty() ? defaultValue : Double.parseDouble(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Using default: " + defaultValue);
            return defaultValue;
        }
    }
}
