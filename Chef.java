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


            // Deadlock caused by hold-and-wait
            // One Chef can hold knife while other Chef holds board
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

        }
    }
}
