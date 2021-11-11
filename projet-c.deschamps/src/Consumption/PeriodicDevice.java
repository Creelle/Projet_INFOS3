package Consumption;

public class PeriodicDevice extends Device {

    // Attributs
    private int periode; // Période de consommation en min
    private int duree; // Durée de consommation en min (durée sur laquelle la conso vaut son max)
    private int t1; // Instant de début de consommation
    private int t2; // Instant de fin de consommation

    /**
     * Constructeur avec initialisation des attributs de PointLivraison
     * 
     * @param name
     * @param puissCons
     * @param nameFonc
     * @param periode
     * @param duree
     * @param t1
     * @param t2
     */
    public PeriodicDevice(String name, double puissCons, String nameFonc, int periode, int duree, int t1, int t2) {
        super(name, puissCons, true, nameFonc); // flag = true car Consommation périodique du point
        this.periode = periode;
        this.duree = duree;
        this.t1 = t1;
        this.t2 = t2;
    }

    // Setters et Getters
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
        return "{" + "name : " + getName() + ", Périodique :" + getFlag() + ", Puissance = " + getPowCons()
                + ", nameFonc : " + getNameFonc() + ", Periode=" + getPeriode() + ", duree=" + getDuree() + ", t1="
                + getT1() + ", t2=" + getT2() + "}";
    }

    /**
     * Ajout de la consommation du point à la consommation existante
     * 
     * @param cons Consommation existante sous forme de tableau
     * @return cons Consommation actualisée
     */
    public double[] addCons(double[] cons, int j) {
        double f = 0;
        if (getNameFonc() == "sin") {
            f = 0.3 * Math.sin(2 * Math.PI * (j - 80) / 365) + 0.7; // Représente la fluctuation de puissance
                                                                    // solaire reçue par la Terre par ex
        } else if (getNameFonc() == "hiver") { // Représente une utilisation en hiver du 1er Nov
                                               // au 15 Mars (ex : Chauffage)
            if (j < 61) {
                f = -Math.pow(j / 60.0 - 0.1, 6) + 1;
            } else if (j >= 61 && j < 300) {
                f = 0;
            } else {
                f = -Math.pow(j / 60.0 - 6, 6) + 1;
            }
        }else if(getNameFonc()== "ete"){
            if(j>=120 && j<259){
                f = Math.pow(Math.E, -Math.pow((j-189)/55.0, 4.0));
            }
            else{
                f=0;
            }
        } else {
            f = 1;
        }
        // Changement si duree>periode:
        if(duree>periode){
            System.out.println("durée > periode : la durée ne peut pas être plus "+
            "grande que la période, la durée est donc fixée à la période.");
            setDuree(periode);
            System.out.println("nouvelle durée = "+ duree);
        }
        // Nombre de période de consommation sur la durée considérée
        double n = Math.ceil((t2 - t1) / getPeriode());
        // Consommation : elle vaut soit 0 soit puissCons
        // Boucle sur le nombre n
        for (int k = 0; k <= n - 1; k++) {
            // Boucle sur la durée duree
            for (int i = t1 + k * periode; i < t1 + k * periode + duree; i++) {
                if (i < 1440) {
                    cons[i] = cons[i] + getPowCons() * f;
                }
            }
        }
        return cons;
    }

}
