package Consumption;

public class ConstantDevice extends Device {

    private boolean flag; // booléen représentant le caractère constant ou non
    private String nameFonc; // nom de la fonction de forme annuelle

    /**
     * Constructeur avec initialisation des attributs de la classe Point de
     * Livraison
     * 
     * @param name
     * @param powCons
     * @param nameFonc
     */
    public ConstantDevice(String name, double powCons, String nameFonc) {
        super(powCons, name); 
        this.flag = false; // flag = false car Production constante du point
        this.nameFonc = nameFonc;
    }

    public boolean getFlag(){
        return flag;
    }

    public String getNameFonc(){
        return nameFonc;
    }

    // méthode toString
    @Override
    public String toString() {
        return "{" + "Name : " + getName() + ", Periodic : " + getFlag() + ", " + "Power : " + getPowerMax()
                + ", NameFonc : " + getNameFonc() + "}";
    }

    /**
     * Ajout de la consommation du point à la consommation existante
     * 
     * @param cons Consommation existante sous forme de tableau
     * @return cons Consommation actualisée
     */
    public void addCons(double[] cons, int j) {
        double f;
        if (getNameFonc() == "sin") {
            f = 0.3 * Math.sin(2 * Math.PI * (j - 80) / 365) + 0.7; // Représente la fluctuation de puissance
                                                                    // solaire reçue par la Terre
        } else if (getNameFonc() == "hiver") { // Représente une utilisation en hiver du 1er Nov
                                               // au 15 Mars (ex : Chauffage)
            if (j < 61) {
                f = -Math.pow(j / 60.0 - 0.1, 6) + 1;
            } else if (j >= 61 && j < 300) {
                f = 0;
            } else {
                f = -Math.pow(j / 60.0 - 6, 6) + 1;
            }
        } else {
            f = 1;
        }
        for (int i = 0; i < cons.length; i++) {
            cons[i] = cons[i] + getPowerMax() * f;
        }
    }
}
