import java.util.*;
import java.util.stream.Collectors;

public class ShoppingCentre {
    private Map<String, List<Product>> productsByProducer;
    private Map<String, List<Product>> productsByName;
    private TreeMap<Double, List<Product>> productsByPrice;

    public ShoppingCentre() {
        this.productsByProducer = new HashMap<>();
        this.productsByName = new HashMap<>();
        this.productsByPrice = new TreeMap<>();
    }

    public String addProduct(String name, double price, String producer) {
        productsByProducer.putIfAbsent(producer, new ArrayList<>());
        Product product = new Product(name, price, producer);
        productsByProducer.get(producer).add(product);
        productsByName.putIfAbsent(name, new ArrayList<>());
        productsByName.get(name).add(product);

        // Add product to productsByPrice
        productsByPrice.putIfAbsent(price, new ArrayList<>());
        productsByPrice.get(price).add(product);

        return "Product added";
    }

    public String delete(String name, String producer) {
        List<Product> products = productsByProducer.get(producer);
        if (products == null) return "No products found";

        List<Product> toBeDel = products
                .stream()
                .filter(product -> product.getName().equals(name))
                .collect(Collectors.toList());

        int countDeleted = toBeDel.size();
        toBeDel.forEach(product -> {
            productsByPrice.get(product.getPrice()).remove(product);
        });
        productsByName.get(name).clear();

        return String.format("%d products deleted", countDeleted);
    }

    public String delete(String producer) {
        List<Product> products = productsByProducer.get(producer);
        if (products == null) return "No products found";

        int countDeleted = products.size();
        products.forEach(product -> {
            productsByPrice.get(product.getPrice()).remove(product);
        });
        products.clear();

        productsByName.values().removeAll(products);

        return String.format("%d products deleted", countDeleted);
    }

    public String findProductsByName(String name) {
        if (productsByName.get(name) == null) return "No such Products";

        List<Product> products = productsByName.get(name);
        return String.format("%d products found", products.size());
    }

    public String findProductsByProducer(String producer) {
        List<Product> products = productsByProducer.get(producer);
        if (products == null) return "No products found";

        return String.format("%d products found", products.size());
    }

    public String findProductsByPriceRange(double priceFrom, double priceTo) {
        // Retrieve products within the specified price range from productsByPrice
        SortedMap<Double, List<Product>> subMap = productsByPrice.subMap(priceFrom, true, priceTo, true);

        List<Product> productsInRange = new ArrayList<>();
        subMap.values().forEach(productsInRange::addAll);

        if (productsInRange.isEmpty()) {
            return "No products found in the specified price range";
        } else {
            StringBuilder result = new StringBuilder();
            for (Product product : productsInRange) {
                result.append(String.format("{%s;%s;%.2f}\n", product.getName(), product.getProducer(), product.getPrice()));
            }
            return result.toString();
        }
    }

}

