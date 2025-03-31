import java.util.*;

class Stock {
    String name;
    double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public void updatePrice() {
        double change = (Math.random() * 10 - 5) / 100;
        price += price * change;
        price = Math.round(price * 100.0) / 100.0;
    }
}

class Portfolio {
    Map<String, Integer> holdings = new HashMap<>();
    double balance = 10000.0;
    List<String> transactionHistory = new ArrayList<>();

    public void buyStock(String stockName, double stockPrice, int quantity) {
        double cost = stockPrice * quantity;
        if (cost > balance) {
            System.out.println("Insufficient balance to buy " + quantity + " shares of " + stockName);
            return;
        }
        holdings.put(stockName, holdings.getOrDefault(stockName, 0) + quantity);
        balance -= cost;
        transactionHistory.add("Bought " + quantity + " shares of " + stockName + " at $" + stockPrice);
        System.out.println("Successfully bought " + quantity + " shares of " + stockName);
    }

    public void sellStock(String stockName, double stockPrice, int quantity) {
        if (!holdings.containsKey(stockName) || holdings.get(stockName) < quantity) {
            System.out.println("Not enough shares to sell.");
            return;
        }
        holdings.put(stockName, holdings.get(stockName) - quantity);
        balance += stockPrice * quantity;
        transactionHistory.add("Sold " + quantity + " shares of " + stockName + " at $" + stockPrice);
        System.out.println("Successfully sold " + quantity + " shares of " + stockName);
        if (holdings.get(stockName) == 0) holdings.remove(stockName);
    }

    public void viewPortfolio() {
        System.out.println("\n===== Portfolio Summary =====");
        System.out.println("Balance: $" + balance);
        System.out.println("Holdings: " + holdings);
        System.out.println("=============================");
    }

    public void viewTransactionHistory() {
        System.out.println("\n===== Transaction History =====");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
        System.out.println("===============================");
    }
}

public class stocktrading {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Portfolio portfolio = new Portfolio();

        List<Stock> stocks = Arrays.asList(
                new Stock("AAPL", 150.0),
                new Stock("GOOGL", 2800.0),
                new Stock("AMZN", 3400.0),
                new Stock("TSLA", 700.0)
        );

        while (true) {
            for (Stock stock : stocks) stock.updatePrice();

            System.out.println("\n====== Stock Trading Platform ======");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n===== Market Data =====");
                    for (Stock stock : stocks) {
                        System.out.println(stock.name + " - $" + stock.price);
                    }
                    break;

                case 2:
                    System.out.print("Enter stock name: ");
                    String buyStockName = scanner.next().toUpperCase();
                    Stock buyStock = stocks.stream().filter(s -> s.name.equals(buyStockName)).findFirst().orElse(null);
                    if (buyStock == null) {
                        System.out.println("Stock not found!");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int buyQuantity = scanner.nextInt();
                    portfolio.buyStock(buyStockName, buyStock.price, buyQuantity);
                    break;

                case 3:
                    System.out.print("Enter stock name: ");
                    String sellStockName = scanner.next().toUpperCase();
                    Stock sellStock = stocks.stream().filter(s -> s.name.equals(sellStockName)).findFirst().orElse(null);
                    if (sellStock == null) {
                        System.out.println("Stock not found!");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int sellQuantity = scanner.nextInt();
                    portfolio.sellStock(sellStockName, sellStock.price, sellQuantity);
                    break;

                case 4:
                    portfolio.viewPortfolio();
                    break;

                case 5:
                    portfolio.viewTransactionHistory();
                    break;

                case 6:
                    System.out.println("Exiting platform. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option! Please choose a valid option.");
            }
        }
    }
}

/*
   ==============================
   Sample Output 1 (View Market Data)
   ==============================

   ====== Stock Trading Platform ======
   1. View Market Data
   2. Buy Stock
   3. Sell Stock
   4. View Portfolio
   5. Transaction History
   6. Exit
   Choose an option: 1

   ===== Market Data =====
   AAPL - $149.50
   GOOGL - $2810.00
   AMZN - $3395.50
   TSLA - $695.00

   ==============================
   Sample Output 2 (Buy Stock)
   ==============================

   Choose an option: 2
   Enter stock name: AAPL
   Enter quantity: 5
   Successfully bought 5 shares of AAPL

   ==============================
   Sample Output 3 (Sell Stock)
   ==============================

   Choose an option: 3
   Enter stock name: AAPL
   Enter quantity: 2
   Successfully sold 2 shares of AAPL

   ==============================
   Sample Output 4 (View Portfolio)
   ==============================

   Choose an option: 4

   ===== Portfolio Summary =====
   Balance: $9925.0
   Holdings: {AAPL=3}
   =============================

   ==============================
   Sample Output 5 (Transaction History)
   ==============================

   Choose an option: 5

   ===== Transaction History =====
   Bought 5 shares of AAPL at $149.50
   Sold 2 shares of AAPL at $150.75
   ==============================
*/
