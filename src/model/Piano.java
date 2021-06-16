package model;

public class Piano extends Instrument {
    private int keyNumber;
    private String cabineType;
    private int width;
    private int height;

    public Piano(int id, int stock, String name, double price, String soundPath, String imagePath, int keyNumber, String cabineType, int width, int height) {
        super(id, stock, name, price, soundPath, imagePath);
        this.keyNumber = keyNumber;
        this.cabineType = cabineType;
        this.width = width;
        this.height = height;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getCabineType() {
        return cabineType;
    }

    public void setCabineType(String cabineType) {
        this.cabineType = cabineType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
