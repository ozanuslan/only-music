package model;

public abstract class WindInstrument extends Instrument{
    private String material;
    private int boreWidth;

    public WindInstrument(int id, int stock, String name, double price, String soundPath, String imagePath, String material, int boreWidth) {
        super(id, stock, name, price, soundPath, imagePath);
        this.material = material;
        this.boreWidth = boreWidth;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getBoreWidth() {
        return boreWidth;
    }

    public void setBoreWidth(int boreWidth) {
        this.boreWidth = boreWidth;
    }
}
