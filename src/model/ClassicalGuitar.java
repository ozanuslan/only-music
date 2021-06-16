package model;

public class ClassicalGuitar extends Guitar{
    private int stringNumber;
    private String bodyType;

    public ClassicalGuitar(int id, int stock, String name, double price, String soundPath, String imagePath, String bodyMaterial, String neckMaterial, int stringNumber, String bodyType) {
        super(id, stock, name, price, soundPath, imagePath, bodyMaterial, neckMaterial);
        this.stringNumber = stringNumber;
        this.bodyType = bodyType;
    }

    public int getStringNumber() {
        return stringNumber;
    }

    public void setStringNumber(int stringNumber) {
        this.stringNumber = stringNumber;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
}
