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
            // Chef must check if it can collect *both* before the other Chef can
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
        }
    }
}
