public class Chef implements Runnable {

    private String name;
    private Kitchen kitchen;
    private boolean hasKnife;
    private boolean hasBoard;

    private final Object knifeLock = new Object();
    private final Object boardLock = new Object();

    public Chef(String name, Kitchen kitchen) {
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

            /*
            // Livelock caused by resource holding
            // One Chef holds knife while other Chef holds board
            synchronized (knifeLock) {
                if(this.kitchen.isKnifeAvailable())
                {
                    System.out.println("Chef " + this.name + ": Picked up knife.");
                    this.kitchen.setKnifeAvailable(false);
                    this.hasKnife = true;
                }
            }

            synchronized (boardLock) {
                if(this.kitchen.isBoardAvailable())
                {
                    System.out.println("Chef " + this.name + ": Picked up chopping board.");
                    this.kitchen.setBoardAvailable(false);
                    this.hasBoard = true;
                }
            }
             */

            // Deadlock caused by resource holding
            // Chef holding knife waits for board to be available
            // Chef holding board waits for knife to be available
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
        }
    }
}
