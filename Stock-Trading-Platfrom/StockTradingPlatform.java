import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Stock {
    private String name;
    private double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

class Portfolio {
    private Map<String, Integer> stocks;
    private double balance;

    public Portfolio(double initialBalance) {
        this.stocks = new HashMap<>();
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void addStock(String name, int quantity) {
        stocks.put(name, stocks.getOrDefault(name, 0) + quantity);
    }

    public void removeStock(String name, int quantity) {
        if (stocks.containsKey(name) && stocks.get(name) >= quantity) {
            stocks.put(name, stocks.get(name) - quantity);
            if (stocks.get(name) == 0) {
                stocks.remove(name);
            }
        }
    }

    public Map<String, Integer> getStocks() {
        return stocks;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public double calculatePortfolioValue(Map<String, Stock> market) {
        double totalValue = balance;
        for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
            totalValue += entry.getValue() * market.get(entry.getKey()).getPrice();
        }
        return totalValue;
    }
}

public class StockTradingPlatform {
    private static Map<String, Stock> market = new HashMap<>();
    private static Portfolio portfolio = new Portfolio(10000.0); // Initial balance of $10,000

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize market with some stocks
        market.put("AAPL", new Stock("Apple", 150.0));
        market.put("GOOGL", new Stock("Google", 2800.0));
        market.put("AMZN", new Stock("Amazon", 3400.0));
        market.put("TATA", new Stock("Tata", 1000.0));
        market.put("GSPL", new Stock("Gspl", 442.0));

        while (true) {
            System.out.println("\nStock Trading Platform");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewMarketData();
                    break;
                case 2:
                    buyStock(scanner);
                    break;
                case 3:
                    sellStock(scanner);
                    break;
                case 4:
                    viewPortfolio();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewMarketData() {
        System.out.println("\nMarket Data:");
        for (Stock stock : market.values()) {
            System.out.println(stock.getName() + ": $" + stock.getPrice());
        }
    }

    private static void buyStock(Scanner scanner) {
        System.out.print("\nEnter stock name to buy: ");
        String stockName = scanner.next().toUpperCase();
        if (!market.containsKey(stockName)) {
            System.out.println("Stock not found.");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();
        double totalCost = quantity * market.get(stockName).getPrice();

        if (totalCost > portfolio.getBalance()) {
            System.out.println("Insufficient balance.");
        } else {
            portfolio.addStock(stockName, quantity);
            portfolio.updateBalance(-totalCost);
            System.out.println("Bought " + quantity + " shares of " + stockName);
        }
    }

    private static void sellStock(Scanner scanner) {
        System.out.print("\nEnter stock name to sell: ");
        String stockName = scanner.next().toUpperCase();
        if (!portfolio.getStocks().containsKey(stockName)) {
            System.out.println("You don't own this stock.");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();
        if (quantity > portfolio.getStocks().get(stockName)) {
            System.out.println("You don't have enough shares.");
        } else {
            double totalValue = quantity * market.get(stockName).getPrice();
            portfolio.removeStock(stockName, quantity);
            portfolio.updateBalance(totalValue);
            System.out.println("Sold " + quantity + " shares of " + stockName);
        }
    }

    private static void viewPortfolio() {
        System.out.println("\nPortfolio:");
        System.out.println("Balance: $" + portfolio.getBalance());
        System.out.println("Stocks:");
        for (Map.Entry<String, Integer> entry : portfolio.getStocks().entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " shares");
        }
        System.out.println("Total Portfolio Value: $" + portfolio.calculatePortfolioValue(market));
    }
}

    

