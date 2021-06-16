package model;

public class Trombone extends WindInstrument{
    private int valveNumber;
    private String model;

    public Trombone(int id, int stock, String name, double price, String soundPath, String imagePath, String material, int boreWidth, int valveNumber, String model) {
        super(id, stock, name, price, soundPath, imagePath, material, boreWidth);
        this.valveNumber = valveNumber;
        this.model = model;
    }

    public int getValveNumber() {
        return valveNumber;
    }

    public void setValveNumber(int valveNumber) {
        this.valveNumber = valveNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
