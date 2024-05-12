import java.util.Scanner;

public class CoffeeMachine {
    private enum Action {
        BUY, FILL, TAKE, REMAINING, EXIT
    }

    private enum CoffeeType {
        ESPRESSO("Espresso", 250, 0, 16, 4),
        LATTE("Latte", 350, 75, 20, 7),
        CAPPUCCINO("Cappuccino", 200, 100, 12, 6);

        private final String name;
        private final int water;
        private final int milk;
        private final int coffeeBeans;
        private final int cost;

        CoffeeType(String name, int water, int milk, int coffeeBeans, int cost) {
            this.name = name;
            this.water = water;
            this.milk = milk;
            this.coffeeBeans = coffeeBeans;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public int getWater() {
            return water;
        }

        public int getMilk() {
            return milk;
        }

        public int getCoffeeBeans() {
            return coffeeBeans;
        }

        public int getCost() {
            return cost;
        }
    }

    private int water;
    private int milk;
    private int coffeeBeans;
    private int disposableCups;
    private int money;

    public CoffeeMachine(int water, int milk, int coffeeBeans, int disposableCups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffeeBeans = coffeeBeans;
        this.disposableCups = disposableCups;
        this.money = money;
    }

    public void processAction(Action action) {
        switch (action) {
            case BUY:
                buyCoffee();
                break;
            case FILL:
                fillMachine();
                break;
            case TAKE:
                takeMoney();
                break;
            case REMAINING:
                printMachineInfo();
                break;
            case EXIT:
                System.exit(0);
                break;
            default:
                System.out.println("Invalid action!");
        }
    }

    private void buyCoffee() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What do you want to buy? 1 - Espresso, 2 - Latte, 3 - Cappuccino, back - to main menu:");
        String choice = scanner.next();

        if (choice.equals("back")) return;

        int choiceIndex = Integer.parseInt(choice) - 1;
        if (choiceIndex < 0 || choiceIndex >= CoffeeType.values().length) {
            System.out.println("Invalid choice!");
            return;
        }

        CoffeeType coffeeType = CoffeeType.values()[choiceIndex];
        makeCoffee(coffeeType);
    }

    private void makeCoffee(CoffeeType coffeeType) {
        if (water < coffeeType.getWater()) {
            System.out.println("Sorry, not enough water!");
        } else if (milk < coffeeType.getMilk()) {
            System.out.println("Sorry, not enough milk!");
        } else if (coffeeBeans < coffeeType.getCoffeeBeans()) {
            System.out.println("Sorry, not enough coffee beans!");
        } else if (disposableCups < 1) {
            System.out.println("Sorry, not enough disposable cups!");
        } else {
            System.out.println("I have enough resources, making you a " + coffeeType.getName() + "!");
            water -= coffeeType.getWater();
            milk -= coffeeType.getMilk();
            coffeeBeans -= coffeeType.getCoffeeBeans();
            disposableCups--;
            money += coffeeType.getCost();
        }
    }

    private void fillMachine() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write how many ml of water you want to add:");
        water += scanner.nextInt();
        System.out.println("Write how many ml of milk you want to add:");
        milk += scanner.nextInt();
        System.out.println("Write how many grams of coffee beans you want to add:");
        coffeeBeans += scanner.nextInt();
        System.out.println("Write how many disposable cups you want to add:");
        disposableCups += scanner.nextInt();
    }

    private void takeMoney() {
        System.out.println("I gave you $" + money);
        money = 0;
    }

    private void printMachineInfo() {
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(coffeeBeans + " g of coffee beans");
        System.out.println(disposableCups + " disposable cups");
        System.out.println("$" + money + " of money");
    }

    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String actionStr = scanner.next().toUpperCase();

            try {
                Action action = Action.valueOf(actionStr);
                coffeeMachine.processAction(action);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid action!");
            }
        }
    }
}
