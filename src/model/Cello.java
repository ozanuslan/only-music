package model;

public class Cello extends StringInstrument{
    private int stringCount;

    public Cello(int id, int stock, String name, double price, String soundPath, String imagePath, String frontCover, String backCover, int height, int stringCount) {
        super(id, stock, name, price, soundPath, imagePath, frontCover, backCover, height);
        this.stringCount = stringCount;
    }

    public int getStringCount() {
        return stringCount;
    }

    public void setStringCount(int stringCount) {
        this.stringCount = stringCount;
    }

    @Override
    public String getDescription() {
        String description = "";
        description += "Front Cover: "+getFrontCover()+"\n";
        description += "Back Cover: "+getBackCover()+"\n";
        description += "Height: "+getHeight()+"cm\n";
        description += "Model: "+stringCount+"\n";

        return description;
    }
}
