package model;

public class ElectricGuitar extends Guitar{
    private String magneticType;
    private String bridgeType;

    public ElectricGuitar(int id, int stock, String name, double price, String soundPath, String imagePath, String bodyMaterial, String neckMaterial, String magneticType, String bridgeType) {
        super(id, stock, name, price, soundPath, imagePath, bodyMaterial, neckMaterial);
        this.magneticType = magneticType;
        this.bridgeType = bridgeType;
    }

    public String getMagneticType() {
        return magneticType;
    }

    public void setMagneticType(String magneticType) {
        this.magneticType = magneticType;
    }

    public String getBridgeType() {
        return bridgeType;
    }

    public void setBridgeType(String bridgeType) {
        this.bridgeType = bridgeType;
    }
}
