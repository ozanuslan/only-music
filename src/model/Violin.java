package model;

public class Violin extends StringInstrument{
    private int stringThick;

    public Violin(int id, int stock, String name, double price, String soundPath, String imagePath, String frontCover, String backCover, int height, int stringThick) {
        super(id, stock, name, price, soundPath, imagePath, frontCover, backCover, height);
        this.stringThick = stringThick;
    }

    public int getStringThick() {
        return stringThick;
    }

    public void setStringThick(int stringThick) {
        this.stringThick = stringThick;
    }
}
