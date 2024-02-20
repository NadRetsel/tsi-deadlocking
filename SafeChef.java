public class SafeChef implements Runnable {

    private String name;
    private Kitchen kitchen;
    private boolean hasKnife;
    private boolean hasBoard;

    private final Object singleKitchenLock = new Object();

    public SafeChef(String name, Kitchen kitchen) {
        this.name = name;
        this.kitchen = kitchen;
    }


    @Override
    public void run() {

        while(true) {
            if(this.hasKnife && this.hasBoard) {
                System.out.println("Chef " + this.name + ": Cooking.");
                this.hasKnife = false;
                this.hasBoard = false;

                this.kitchen.setKnifeAvailable(true);
                this.kitchen.setBoardAvailable(true);
            }

            // Deadlock prevented
            // Chef must have all resources
            synchronized (singleKitchenLock) {
                if(this.kitchen.isKnifeAvailable())
                {
                    System.out.println("Chef " + this.name + ": Picked up knife.");
                    this.kitchen.setKnifeAvailable(false);
                    this.hasKnife = true;
                }

                if(this.kitchen.isBoardAvailable())
                {
                    System.out.println("Chef " + this.name + ": Picked up chopping board.");
                    this.kitchen.setBoardAvailable(false);
                    this.hasBoard = true;
                }
            }


            /*
            // Deadlock-safe
            // Chefs must pick up knife then board
            // If not available, wait until other Chef releases
            synchronized (knifeLock) {
                while(!this.kitchen.isKnifeAvailable()) System.out.println("Chef " + this.name + ": Waiting for knife to be available.");

                System.out.println("Chef " + this.name + ": Picked up knife.");
                this.kitchen.setKnifeAvailable(false);
                this.hasKnife = true;

            }

            synchronized (boardLock) {
                while(!this.kitchen.isBoardAvailable()) System.out.println("Chef " + this.name + ": Waiting for board to be available.");

                System.out.println("Chef " + this.name + ": Picked up chopping board.");
                this.kitchen.setBoardAvailable(false);
                this.hasBoard = true;

            }
             */
        }
    }
}
