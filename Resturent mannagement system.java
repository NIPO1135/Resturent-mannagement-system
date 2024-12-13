import java.util.*;

class FoodItem {
    private int id;
    private String name;
    private double price;

    public FoodItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + " - " + name + " - $" + price;
    }
}

class Order {
    private int orderId;
    private List<FoodItem> foodItems;
    private double totalAmount;

    public Order(int orderId) {
        this.orderId = orderId;
        this.foodItems = new ArrayList<>();
        this.totalAmount = 0.0;
    }

    public void addFoodItem(FoodItem item, int quantity) {
        for (int i = 0; i < quantity; i++) {
            foodItems.add(item);
            totalAmount += item.getPrice();
        }
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<FoodItem> getFoodItems() {
        return foodItems;
    }

    @Override
    public String toString() {
        StringBuilder orderSummary = new StringBuilder("Order ID: " + orderId + "\n");
        for (FoodItem item : foodItems) {
            orderSummary.append(item.getName()).append(" - $").append(item.getPrice()).append("\n");
        }
        orderSummary.append("Total: $").append(totalAmount);
        return orderSummary.toString();
    }
}

class RestaurantManagementSystem {
    private Map<Integer, FoodItem> menu;
    private List<Order> orders;
    private int nextFoodId;
    private int nextOrderId;
    private Map<String, String> staffCredentials;

    public RestaurantManagementSystem() {
        menu = new HashMap<>();
        orders = new ArrayList<>();
        nextFoodId = 1;
        nextOrderId = 1;
        staffCredentials = new HashMap<>();
        staffCredentials.put("nipo", "1111");
    }

    public void addFood(String name, double price) {
        FoodItem foodItem = new FoodItem(nextFoodId++, name, price);
        menu.put(foodItem.getId(), foodItem);
    }

    public void updateFood(int id, String name, double price) {
        if (menu.containsKey(id)) {
            FoodItem item = menu.get(id);
            item.setName(name);
            item.setPrice(price);
        }
    }

    public void deleteFood(int id) {
        menu.remove(id);
    }

    public void displayMenu() {
        for (FoodItem item : menu.values()) {
            System.out.println(item);
        }
    }

    public Order createOrder() {
        Order order = new Order(nextOrderId++);
        orders.add(order);
        return order;
    }

    public void addFoodToOrder(Order order, int foodId, int quantity) {
        if (menu.containsKey(foodId)) {
            FoodItem item = menu.get(foodId);
            order.addFoodItem(item, quantity);
        }
    }

    public void confirmPayment(Order order) {
        System.out.println("Payment confirmed for Order ID: " + order.getOrderId());
    }

    public boolean staffLogin(String username, String password) {
        return staffCredentials.containsKey(username) && staffCredentials.get(username).equals(password);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RestaurantManagementSystem system = new RestaurantManagementSystem();

        while (true) {
            System.out.println("1. Staff Login\n2. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                if (system.staffLogin(username, password)) {
                    while (true) {
                        System.out.println("1. Add Food\n2. Update Food\n3. Delete Food\n4. Display Menu\n5. Create Order\n6. Exit");
                        int staffChoice = scanner.nextInt();
                        scanner.nextLine();

                        if (staffChoice == 1) {
                            System.out.print("Enter food name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter price: ");
                            double price = scanner.nextDouble();
                            scanner.nextLine();
                            system.addFood(name, price);
                        } else if (staffChoice == 2) {
                            System.out.print("Enter food ID: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter new name: ");
                            String name = scanner.nextLine();
                            System.out.print("Enter new price: ");
                            double price = scanner.nextDouble();
                            scanner.nextLine();
                            system.updateFood(id, name, price);
                        } else if (staffChoice == 3) {
                            System.out.print("Enter food ID: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();
                            system.deleteFood(id);
                        } else if (staffChoice == 4) {
                            system.displayMenu();
                        } else if (staffChoice == 5) {
                            Order order = system.createOrder();
                            while (true) {
                                System.out.println("1. Add Food to Order\n2. Finalize Order\n");
                                int orderChoice = scanner.nextInt();
                                scanner.nextLine();

                                if (orderChoice == 1) {
                                    System.out.print("Enter food ID: ");
                                    int foodId = scanner.nextInt();
                                    System.out.print("Enter quantity: ");
                                    int quantity = scanner.nextInt();
                                    scanner.nextLine();
                                    system.addFoodToOrder(order, foodId, quantity);
                                } else if (orderChoice == 2) {
                                    System.out.println(order);
                                    system.confirmPayment(order);
                                    break;
                                }
                            }
                        } else if (staffChoice == 6) {
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid login credentials.");
                }
            } else if (choice == 2) {
                break;
            }
        }

        scanner.close();
    }
}
