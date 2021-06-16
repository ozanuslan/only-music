package model;

public class Amp extends Item{
    private String effects;
    private int power;
    private String type;
    private int channelNumber;

    public Amp(int id, int stock, String name, double price, String imagePath, String effects, int power, String type, int channelNumber) {
        super(id, stock, name, price, imagePath);
        this.effects = effects;
        this.power = power;
        this.type = type;
        this.channelNumber = channelNumber;
    }

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getChannelNumber() {
        return channelNumber;
    }

    public void setChannelNumber(int channelNumber) {
        this.channelNumber = channelNumber;
    }
}

