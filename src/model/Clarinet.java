package model;

public class Clarinet extends WindInstrument{
    private int keyNumber;
    private String model;

    public Clarinet(int id, int stock, String name, double price, String soundPath, String imagePath, String material, int boreWidth, int keyNumber, String model) {
        super(id, stock, name, price, soundPath, imagePath, material, boreWidth);
        this.keyNumber = keyNumber;
        this.model = model;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String getDescription() {
        String description = "";
        description += "Material: "+getMaterial()+"\n";
        description += "Bore Width: "+getBoreWidth()+"\n";
        description += "Key Number: "+keyNumber+"\n";
        description += "Model: "+model+"\n";

        return description;
    }
}
