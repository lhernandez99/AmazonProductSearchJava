package main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;

public class ProductSearchEngine {

    private List<Product> products;

    public ProductSearchEngine() {
        this.products = new ArrayList<>();
    }

    public void loadProductsFromCSV(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 5) continue;

                String name = parts[0].trim();
                String description = parts[1].trim();
                String category = parts[2].trim();
                double price = Double.parseDouble(parts[3].trim());
                double rating = Double.parseDouble(parts[4].trim());

                Product product = new Product(name, description, category, price, rating);
                products.add(product);

                System.out.println("Loaded product: " + name + ", Price: " + price + ", Rating: " + rating);
            }

            System.out.println("Total products loaded: " + products.size());

        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
    }

    public List<Product> search(String query, double minPrice, double maxPrice, double minRating, String category) {
        List<ScoredProduct> scoredResults = new ArrayList<>();
        String[] keywords = query.toLowerCase().split("\\s+");

        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();

        for (Product product : products) {
            System.out.println("Checking product: " + product.getName() +
                               ", Price: " + product.getPrice() +
                               ", Rating: " + product.getRating() +
                               ", Category: " + product.getCategory());

            if (product.getPrice() < minPrice || product.getPrice() > maxPrice) {
                System.out.println("Skipping due to price filter");
                continue;
            }
            if (product.getRating() < minRating) {
                System.out.println("Skipping due to rating filter");
                continue;
            }
            if (category != null && !category.isEmpty() &&
                !product.getCategory().equalsIgnoreCase(category)) {
                System.out.println("Skipping due to category filter");
                continue;
            }

            int score = 0;
            String text = (product.getName() + " " + product.getDescription()).toLowerCase();

            for (String word : keywords) {
                if (text.contains(word)) {
                    score += 2;
                } else {
                    String[] textWords = text.split("\\s+");
                    for (String tw : textWords) {
                        double sim = similarity.apply(word, tw);
                        if (sim > 0.8) {
                            score += 1;
                            break;
                        }
                    }
                }
            }

            if (score > 0) {
                scoredResults.add(new ScoredProduct(product, score));
                System.out.println("Added product with score: " + score);
            }
        }

        return scoredResults.stream()
                .sorted(Comparator.comparingInt(ScoredProduct::getScore).reversed()
                        .thenComparingDouble(sp -> -sp.getProduct().getRating()))
                .map(ScoredProduct::getProduct)
                .collect(Collectors.toList());
    }

    private static class ScoredProduct {
        private Product product;
        private int score;

        public ScoredProduct(Product product, int score) {
            this.product = product;
            this.score = score;
        }

        public Product getProduct() { return product; }
        public int getScore() { return score; }
    }
}
