package model;

public class AcousticDrum extends PercussionInstrument{
    private String shellMaterial;

    public AcousticDrum(int id, int stock, String name, double price, String soundPath, String imagePath, int snareDrumNumber, int bassDrumNumber, int tomNumber, String shellMaterial) {
        super(id, stock, name, price, soundPath, imagePath, snareDrumNumber, bassDrumNumber, tomNumber);
        this.shellMaterial = shellMaterial;
    }

    public String getShellMaterial() {
        return shellMaterial;
    }

    public void setShellMaterial(String shellMaterial) {
        this.shellMaterial = shellMaterial;
    }

    @Override
    public String getDescription(){
        String description = "Snare Drum: "+getSnareDrumNumber()+"\n";
        description += "Snare Drum Number: "+getBassDrumNumber()+"\n";
        description += "Tom Number: "+getTomNumber()+"\n";
        description += "Shell Material: "+shellMaterial+"\n";

        return description;
    }
}
