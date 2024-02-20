import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryChef implements Runnable {

    private String name;
    private Kitchen kitchen;
    private boolean hasKnife;
    private boolean hasBoard;

    private static final Lock knifeLock = new ReentrantLock();
    private static final Lock boardLock = new ReentrantLock();


    public TryChef(String name, Kitchen kitchen) {
        this.name = name;
        this.kitchen = kitchen;
    }


    @Override
    public void run() {

        while(true)
        {
            if(this.hasKnife && this.hasBoard)
            {
                System.out.println("Chef " + this.name + ": Cooking.");
                this.hasKnife = false;
                this.hasBoard = false;

                this.kitchen.setKnifeAvailable(true);
                this.kitchen.setBoardAvailable(true);

                knifeLock.unlock();
                boardLock.unlock();
            }


            // Livelock prevented
            // Chef attempts to obtain the lock. If not, drop any resources held and do some cleaning

            boolean hasKnifeLock = false;
            boolean hasBoardLock = false;
            try {
                hasKnifeLock = knifeLock.tryLock(0, TimeUnit.MILLISECONDS);
                hasBoardLock = boardLock.tryLock(0, TimeUnit.MILLISECONDS);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            if(!(hasKnifeLock && hasBoardLock)) {
                System.out.println("Chef " + this.name + ": Doing some cleaning.");
                if(this.hasKnife)
                {
                    this.hasKnife = false;
                    this.kitchen.setKnifeAvailable(true);
                    knifeLock.unlock();
                }
                if(this.hasBoard)
                {
                    this.hasBoard = false;
                    this.kitchen.setBoardAvailable(true);
                    boardLock.unlock();
                }

            }

            if (hasKnifeLock) {
                if(this.kitchen.isKnifeAvailable())
                {
                    System.out.println("Chef " + this.name + ": Picked up knife.");
                    this.kitchen.setKnifeAvailable(false);
                    this.hasKnife = true;
                }
            }

            if (hasBoardLock) {
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
