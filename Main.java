
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Kitchen kitchen = new Kitchen();

        /*
        // Deadlock-prone Chefs
        Chef chefA = new Chef("Alice", kitchen);
        Chef chefB = new Chef("Bob", kitchen);
        Chef chefC = new Chef("Charlie", kitchen);
        */

        /*
        // Prevent resource holding deadlock
        SafeChef chefA = new SafeChef("Alice", kitchen);
        SafeChef chefB = new SafeChef("Bob", kitchen);
        SafeChef chefC = new SafeChef("Charlie", kitchen);

        */

        TryChef chefA = new TryChef("Alice", kitchen);
        TryChef chefB = new TryChef("Bob", kitchen);
        TryChef chefC = new TryChef("Charlie", kitchen);

        Thread threadA = new Thread(chefA);
        Thread threadB = new Thread(chefB);
        Thread threadC = new Thread(chefC);

        threadA.start();
        threadB.start();
        threadC.start();


    }


}