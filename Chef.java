public class Chef implements Runnable {

    private String name;
    private Kitchen kitchen;
    private boolean hasKnife;
    private boolean hasBoard;

    private static final Object knifeLock = new Object();
    private static final Object boardLock = new Object();

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


            // Deadlock caused by resource holding
            // If Chef A picks up knife then releases lock, Chef B can 'overtake' Chef A and pick up board before Chef A
            synchronized (knifeLock) {
                if(this.kitchen.isKnifeAvailable())
                {
                    System.out.println("Chef " + this.name + ": Picked up knife.");
                    this.kitchen.setKnifeAvailable(false);
                    this.hasKnife = true;
                }
            }

            // DEADLOCK if CPU switches from Chef A (with Knife, no Board) to Chef B (no Knife, no Board) here

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
