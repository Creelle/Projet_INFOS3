package Production;

public class SystemePeriodique extends SystemeProd {

    // Attributs
    private int periode; // Période de production en min
    private int duree; // Durée de production en min
    private int t1; // Instant de début de production
    private int t2; // Instant de fin de production

    /**
     * Constructeur explicite avec initialisation des attributs de la classe Point
     * d'Injection
     * 
     * @param name
     * @param puissProd
     * @param nameFonc
     * @param periode
     * @param duree
     * @param t1
     * @param t2
     * @param nameFonc
     */
    public SystemePeriodique(String name, double puissProd, String nameFonc, int periode, int duree, int t1, int t2) {
        super(name, puissProd, true, nameFonc); // flag = true car Production périodique du point
        this.periode = periode;
        this.duree = duree;
        this.t1 = t1;
        this.t2 = t2;
    }

    // Setters and Getters

    public double getPeriode() {
        return this.periode;
    }

    public void setPeriode(int periode) {
        this.periode = periode;
    }

    public int getDuree() {
        return this.duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getT1() {
        return this.t1;
    }

    public void setT1(int t1) {
        this.t1 = t1;
    }

    public int getT2() {
        return this.t2;
    }

    public void setT2(int t2) {
        this.t2 = t2;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "{" + "name : " + getName() + ", Périodique :" + getFlag() + ", Puissance = " + getPuissProd()
                + ", nameFonc : " + getNameFonc() + ", Periode=" + getPeriode() + ", duree=" + getDuree() + ", t1="
                + getT1() + ", t2=" + getT2() + "}";
    }

    /**
     * Ajout de la production du point à la production existante
     * 
     * @param prod Production existante sous forme de tableau
     * @return prod Production actualisée
     */

    public double[] addProd(double[] prod, int j) {
        double f;
        if (getNameFonc() == "sin") {
            f = 0.3 * Math.cos(2 * Math.PI * (j - 80) / 365) + 0.7; // Représente la fluctuation de puissance
                                                                    // solaire reçue par la Terre par ex
        } else if (getNameFonc() == "hiver") { // Représente une utilisation en hiver (ex : Chauffage)
            if (j < 61) {
                f = -Math.pow(j / 60.0 - 0.1, 6) + 1;
            } else if (j >= 61 && j < 300) {
                f = 0;
            } else {
                f = -Math.pow(j / 60.0 - 6, 6) + 1;
            }
        } else if (getNameFonc() == "ete") {
            if (j >= 120 && j < 259) {
                f = Math.pow(Math.E, -Math.pow((j - 189) / 55.0, 4.0));
            } else {
                f = 0;
            }
        } else {
            f = 1;
        }
        // Nombre de période de production sur la durée considérée
        double n = Math.ceil((t2 - t1) / getPeriode());
        // Production : la production vaut soit 0 soit puissProd
        // Boucle sur le nombre n
        for (int k = 0; k <= n - 1; k++) {
            // Boucle sur la durée duree
            for (int i = t1 + k * periode; i < t1 + k * periode + duree; i++) {
                // Prévention d'écriture hors limite
                if (i < 1440) {
                    prod[i] = prod[i] + getPuissProd() * f;
                }
            }
        }
        return prod;
    }
}
