package model;

public class Trumpet extends WindInstrument{
    private String pistonValve;
    private int bellWidth;

    public Trumpet(int id, int stock, String name, double price, String soundPath, String imagePath, String material, int boreWidth, String pistonValve, int bellWidth) {
        super(id, stock, name, price, soundPath, imagePath, material, boreWidth);
        this.pistonValve = pistonValve;
        this.bellWidth = bellWidth;
    }

    public String getPistonValve() {
        return pistonValve;
    }

    public void setPistonValve(String pistonValve) {
        this.pistonValve = pistonValve;
    }

    public int getBellWidth() {
        return bellWidth;
    }

    public void setBellWidth(int bellWidth) {
        this.bellWidth = bellWidth;
    }
}
