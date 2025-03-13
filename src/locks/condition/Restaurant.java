package locks.condition;

import java.util.Arrays;

public class Restaurant {

    public static void main(String[] args) {
        Kitchen kitchen = new Kitchen();

        Thread chef = new Thread(() -> {
            try{
                for (String dish : Arrays.asList("Pizza", "Pasta", "Burger")){
                    kitchen.cook(dish);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        Thread waiter = new Thread(() -> {
            try{
                for (int i = 0; i < 3; i++) {
                    kitchen.serve();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        waiter.start();
        chef.start();
    }
}
