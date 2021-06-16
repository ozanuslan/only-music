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

    @Override
    public String getDescription() {
        String description = "";
        description += "Front Cover: "+getFrontCover()+"\n";
        description += "Back Cover: "+getBackCover()+"\n";
        description += "Height: "+getHeight()+"cm\n";
        description += "String Thickness: "+stringThick+"mm\n";

        return description;
    }
}
