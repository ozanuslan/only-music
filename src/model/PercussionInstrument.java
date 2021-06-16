package model;

public abstract class PercussionInstrument extends Instrument {
    private int snareDrumNumber;
    private int bassDrumNumber;
    private int tomNumber;

    public PercussionInstrument(int id, int stock, String name, double price, String soundPath, String imagePath, int snareDrumNumber, int bassDrumNumber, int tomNumber) {
        super(id, stock, name, price, soundPath, imagePath);
        this.snareDrumNumber = snareDrumNumber;
        this.bassDrumNumber = bassDrumNumber;
        this.tomNumber = tomNumber;
    }

    public int getSnareDrumNumber() {
        return snareDrumNumber;
    }

    public void setSnareDrumNumber(int snareDrumNumber) {
        this.snareDrumNumber = snareDrumNumber;
    }

    public int getBassDrumNumber() {
        return bassDrumNumber;
    }

    public void setBassDrumNumber(int bassDrumNumber) {
        this.bassDrumNumber = bassDrumNumber;
    }

    public int getTomNumber() {
        return tomNumber;
    }

    public void setTomNumber(int tomNumber) {
        this.tomNumber = tomNumber;
    }
}
