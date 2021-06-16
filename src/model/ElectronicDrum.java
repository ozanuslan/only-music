package model;

public class ElectronicDrum extends PercussionInstrument{
    private int soundNumber;

    public ElectronicDrum(int id, int stock, String name, double price, String soundPath, String imagePath, int snareDrumNumber, int bassDrumNumber, int tomNumber, int soundNumber) {
        super(id, stock, name, price, soundPath, imagePath, snareDrumNumber, bassDrumNumber, tomNumber);
        this.soundNumber = soundNumber;
    }

    public int getSoundNumber() {
        return soundNumber;
    }

    public void setSoundNumber(int soundNumber) {
        this.soundNumber = soundNumber;
    }

    @Override
    public String getDescription(){
        String description = "Snare Drum: "+getSnareDrumNumber()+"\n";
        description += "Snare Drum Number: "+getBassDrumNumber()+"\n";
        description += "Tom Number: "+getTomNumber()+"\n";
        description += "Sound Number: "+soundNumber+"\n";

        return description;
    }
}
