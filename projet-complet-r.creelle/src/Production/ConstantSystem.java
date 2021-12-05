package Production;

public class ConstantSystem extends ProductionSystem {

    private String name; // nom du point d'injection
    private boolean flag; // Booléen représentant le caractère périodique (true) ou non
    private String nameFonc; // nom de la fonction de forme annuelle

    /**
     * Constructeur avec initialisation des attributs de la classe Point d'Injection
     * 
     * @param name
     * @param powProd
     * @param nameFonc
     */
    public ConstantSystem(String name, double powProd, String nameFonc) {
        super(powProd);
        this.name = name;
        this.flag = false;  // flag = false car Consommation constante du point
        this.nameFonc = nameFonc; 
    }

    public String getName() {
        return this.name;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public String getNameFonc() {
        return this.nameFonc;
    }

    /**
     * méthode toString
     */
    @Override
    public String toString() {
        return "{" + "Name : " + getName() + ", Periodic : " + getFlag() + ", " + "Puissance : " + getPowerMax()
                + ", nameFonc : " + getNameFonc() + "}";
    }

    /**
     * Ajout de la production du point à la production existante
     * 
     * @param prod Production existante sous forme de tableau
     * @return prod Production actualisée
     */
    public void addProd(double[] prod, int j) {
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
        for (int i = 0; i < prod.length; i++) {
            prod[i] = prod[i] + getPowerMax() * f;
        }
    }
}
