package model;

public abstract class Guitar extends Instrument {
    private String bodyMaterial;
    private String neckMaterial;

    public Guitar(int id, int stock, String name, double price, String soundPath, String imagePath, String bodyMaterial, String neckMaterial) {
        super(id, stock, name, price, soundPath, imagePath);
        this.bodyMaterial = bodyMaterial;
        this.neckMaterial = neckMaterial;
    }

    public String getBodyMaterial() {
        return bodyMaterial;
    }

    public void setBodyMaterial(String bodyMaterial) {
        this.bodyMaterial = bodyMaterial;
    }

    public String getNeckMaterial() {
        return neckMaterial;
    }

    public void setNeckMaterial(String neckMaterial) {
        this.neckMaterial = neckMaterial;
    }
}
