package model;

public abstract class Instrument extends Item{

    private String soundPath;

    public Instrument(int id, int stock, String name, double price, String soundPath, String imagePath) {
        super(id, stock, name, price,imagePath);
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }
}
