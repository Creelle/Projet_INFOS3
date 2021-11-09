package Consommation;

abstract public class Appareil {

    // Attributs
    private String name; // nom du point d'injection
    private double puissCons; // puissance nominale consommée en W
    private boolean flag; // booléen représentant le caractère constant ou non
    private String nameFonc; // nom de la fonction de forme annuelle

    /**
     * Constructeur primaire
     */
    public Appareil() {
        name = "Livraison";
        puissCons = 100;
        flag = false;
        nameFonc = "const";
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param puissCons
     * @param flag
     * @param nameFonc
     */
    public Appareil(String name, double puissCons, boolean flag, String nameFonc) {
        this.name = name;
        this.puissCons = puissCons;
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

    public double getPuissCons() {
        return this.puissCons;
    }

    public void setPuissCons(double puissCons) {
        this.puissCons = puissCons;
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
