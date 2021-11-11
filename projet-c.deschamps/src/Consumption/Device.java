package Consumption;

abstract public class Device {

    // Attributs
    private String name; // nom du point d'injection
    private double powCons; // puissance nominale consommée en W
    private boolean flag; // booléen représentant le caractère constant ou non
    private String nameFonc; // nom de la fonction de forme annuelle

    /**
     * Constructeur primaire
     */
    public Device() {
        name = "Livraison";
        powCons = 100;
        flag = false;
        nameFonc = "const";
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param powCons
     * @param flag
     * @param nameFonc
     */
    public Device(String name, double powCons, boolean flag, String nameFonc) {
        this.name = name;
        this.powCons = powCons;
        this.flag = flag;
        this.nameFonc = nameFonc;
    }

    // Setters and Getters
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public double getPowCons() {
        return this.powCons;
    }

    public void setPowCons(double powCons) {
        this.powCons = powCons;
    }

    public String getNameFonc() {
        return this.nameFonc;
    }

    public void setNameFonc(String nameFonc) {
        this.nameFonc = nameFonc;
    }

    /**
     * Méthode abstraite de redéfinition de toString
     * 
     * @return Chaîne de caractère du Point de Livraison
     */
    public abstract String toString();

    /**
     * Méthode abstraite d'ajout de la consommation du Point
     * 
     * @param prod tableau des puissances consommées à chaque minute à actualiser
     * @return tableau des puissances consommées à chaque minute actualisé
     */
    public abstract double[] addCons(double[] prod, int j);

}
