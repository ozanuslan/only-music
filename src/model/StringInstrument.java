package model;

public abstract class StringInstrument extends Instrument{
    private String frontCover;
    private String backCover;
    private int height;

    public StringInstrument(int id, int stock, String name, double price, String soundPath, String imagePath, String frontCover, String backCover, int height) {
        super(id, stock, name, price, soundPath, imagePath);
        this.frontCover = frontCover;
        this.backCover = backCover;
        this.height = height;
    }

    public String getFrontCover() {
        return frontCover;
    }

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }

    public String getBackCover() {
        return backCover;
    }

    public void setBackCover(String backCover) {
        this.backCover = backCover;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
