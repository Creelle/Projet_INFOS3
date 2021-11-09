package Production;

abstract public class SystemeProd {
    // Attributs
    private String name; // nom du point d'injection
    private double puissProd; // puissance nominale produite en W
    private boolean flag; // Booléen représentant le caractère périodique (true) ou non
    private String nameFonc; // nom de la fonction de forme annuelle

    /**
     * Constructeur primaire
     */
    public SystemeProd() {
        name = "Injection";
        puissProd = 100;
        flag = false;
        nameFonc = "const";
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param puissprod
     * @param flag
     * @param nameFonc
     */

    public SystemeProd(String name, double puissprod, boolean flag, String nameFonc) {
        this.name = name;
        this.puissProd = puissprod;
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

    public double getPuissProd() {
        return this.puissProd;
    }

    public void setPuissProd(double puissProd) {
        this.puissProd = puissProd;
    }

    public boolean getFlag() {
        return this.flag;
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
     * @return Chaîne de caractère des l'affichage du Point d'Injection
     */
    public abstract String toString();

    /**
     * Méthode abstraite d'ajout de production d'un Point d'Injection
     * 
     * @param prod tableau des puissances produites à chaque minute à actualiser
     * @return le tableau des puissances produites à chaque minute actualisé
     */
    public abstract double[] addProd(double[] prod, int j);

}
