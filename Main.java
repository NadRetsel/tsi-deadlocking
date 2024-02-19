
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Kitchen kitchen = new Kitchen();

        /*
        // Deadlock-prone Chefs
        Chef chefA = new Chef("Alice", kitchen);
        Chef chefB = new Chef("Bob", kitchen);
        */

        // Deadlock-safe Chefs
        SafeChef chefA = new SafeChef("Alice", kitchen);
        SafeChef chefB = new SafeChef("Bob", kitchen);

        Thread threadA = new Thread(chefA);
        Thread threadB = new Thread(chefB);

        threadA.start();
        threadB.start();


    }


}