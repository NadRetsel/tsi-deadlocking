public class Kitchen {

    private boolean knifeAvailable;
    private boolean boardAvailable;

    public Kitchen() {
        this.knifeAvailable = true;
        this.boardAvailable = true;
    }

    public boolean isKnifeAvailable() {
        return knifeAvailable;
    }

    public boolean isBoardAvailable() {
        return boardAvailable;
    }


    public void setKnifeAvailable(boolean knifeAvailable) {
        this.knifeAvailable = knifeAvailable;
    }

    public void setBoardAvailable(boolean boardAvailable) {
        this.boardAvailable = boardAvailable;
    }
}
