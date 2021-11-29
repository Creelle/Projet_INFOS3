package Production;

import java.util.ArrayList;

import City.Energy;

public class Production implements Energy {

    // Attributs
    private ArrayList<InjectionPoint> listInj; // Liste des Points dInjection
    private int deltaT; // Pas de temps égal à 1 min
    private int nbHouses; // Nombre de foyer dans la Ville

    /**
     * Constructeur primaire
     **/
    public Production() {
        listInj = new ArrayList<InjectionPoint>();
        deltaT = 1;
        nbHouses = 1000;
    }

    /**
     * Constructeur explicite tel que le pas de temps deltaT reste inchangé égal à 1
     * min et 1000 foyers Ce constructeur ne sert que pour les tests unitaires sur
     * les unités de production
     * 
     * @param listInj
     */
    public Production(ArrayList<InjectionPoint> listInj) {
        this.listInj = listInj;
        deltaT = 1;
        this.nbHouses = 1000;
    }

    /**
     * Constructeur explicite tel que le pas de temps deltaT reste inchangé égal à 1
     * min. Ce constructeur ne sert que pour les tests unitaires sur
     * les unités de production
     * 
     * @param listInj
     * @param nbHouses
     */
    public Production(ArrayList<InjectionPoint> listInj, int nbHouses) {
        this.listInj = listInj;
        deltaT = 1;
        this.nbHouses = nbHouses;
    }

    /**
     * Constructeur à partir du nombre de foyers uniquement
     * 
     * @param nbHouses
     */
    public Production(int nbHouses) {
        deltaT = 1;
        this.nbHouses = nbHouses;
        listInj = creerSysProd(nbHouses);
    }

    // Getters
    public ArrayList<InjectionPoint> getListInj() {
        return this.listInj;
    }

    public int getDeltaT() {
        return deltaT;
    }

    public int getNbHouses() {
        return this.nbHouses;
    }

    /**
     * Affichage console de la liste de tous les points d'injection
     **/
    public void displaylistIP() {
        for (InjectionPoint IP : listInj) {
            IP.display();
        }
    }

    // Creer l'ensemble du système de production de façon pseudo aléatoire
    /**
     * La taille de la production dépend de la taille de la ville
     * 
     * @param nbHouses nombre de foyers dans la ville
     * @return la liste des Points d'Injection
     */
    public ArrayList<InjectionPoint> creerSysProd(int nbHouses) {
        ArrayList<InjectionPoint> listIP = new ArrayList<>();

        // Création Ferme Solaire
        ArrayList<ProductionSystem> listSolar = new ArrayList<>();
        double nb = 10 + Math.floor(Math.random() * (990));
        int k = 0; // compteur du nombre de système
        for (int i = 0; i < nb; i++) {
            ProductionSystem s1 = new Solar(250, 150, 400);
            listSolar.add(s1);
            k += 1;
        }
        InjectionPoint IP1 = new InjectionPoint("FermeSolaire", k, listSolar);
        listIP.add(IP1);

        // Création Ferme éolienne
        ArrayList<ProductionSystem> listWindTurbine = new ArrayList<>();
        nb = Math.floor(Math.random() * (20));
        k = 0;
        for (int i = 0; i < nb; i++) {
            ProductionSystem s2 = new Eolian(400000, 10, 140);
            listWindTurbine.add(s2);
            k += 1;
        }
        InjectionPoint IP2 = new InjectionPoint("FermeEolienne", k, listWindTurbine);
        listIP.add(IP2);


        // Création Centrale Hydraulique
        if (5000 > nbHouses) {
            ArrayList<ProductionSystem> listHydraulic = new ArrayList<>();
            ProductionSystem s3 = new Hydroeletric(3000000, 35);
            listHydraulic.add(s3);
            k = 1;
            InjectionPoint IP3 = new InjectionPoint("CentraleHydraulique", k, listHydraulic);
            listIP.add(IP3);
        } else if (5000 <= nbHouses && nbHouses < 10000) {
            ArrayList<ProductionSystem> listHydraulic = new ArrayList<>();
            ProductionSystem s3 = new Hydroeletric(6000000, 140);
            listHydraulic.add(s3);
            k = 1;
            InjectionPoint IP3 = new InjectionPoint("CentraleHydraulique", k, listHydraulic);
            listIP.add(IP3);
        } else {
            ArrayList<ProductionSystem> listHydraulic = new ArrayList<>();
            ProductionSystem s3a = new Hydroeletric(5000000, 100);
            ProductionSystem s3b = new Hydroeletric(5000000, 100);
            listHydraulic.add(s3a);
            listHydraulic.add(s3b);
            k = 2;
            InjectionPoint IP3 = new InjectionPoint("CentraleHydraulique", k, listHydraulic);
            listIP.add(IP3);
        }

        // Création Centrale Fossile
        if (nbHouses < 10000) {
            ArrayList<ProductionSystem> listFossil = new ArrayList<>();
            ProductionSystem s4 = new Nuclear(70000000,1000000000, 0.0023, 4);
            listFossil.add(s4);
            k = 1;
            InjectionPoint IP4 = new InjectionPoint("CentraleFossile", k, listFossil);
            listIP.add(IP4);
        } else if (nbHouses >= 10000) {
            ArrayList<ProductionSystem> listFossil = new ArrayList<>();
            ProductionSystem s4a = new Nuclear(95000000,120000000, 0.0023, 4);
            ProductionSystem s4b = new Fossil(50000000, 5);
            listFossil.add(s4a);
            listFossil.add(s4b);
            k = 2;
            InjectionPoint IP4 = new InjectionPoint("CentraleFossile", k, listFossil);
            listIP.add(IP4);
        }

        return listIP;
    }

    // Générer le tableau de production et display associé
    /**
     * Génération du tableau de prroduction : chaque valeur représente la production
     * à la minute m
     * 
     * @return prod tableau de production
     */
    public double[] generate(int j) {
        double[] prod = new double[1440];
        for (InjectionPoint IP : listInj) {
            ArrayList<ProductionSystem> listSys = IP.getListSys();
            for (ProductionSystem S : listSys) {
                S.addProd(prod, j);
            }
        }
        return prod;
    }

    /**
     * Display console du tableau de production
     * 
     * @param prod
     */
    public void displayPowProd(double[] prod) {
        System.out.print("[");
        for (double d : prod) {
            double rounded = Math.round(d * 10.0) / 10.0;
            System.out.print(rounded + ", ");
        }
        System.out.println("]");
    }

    /**
     * Implémentation de l'intégration à partir d'un tableau de production
     * 
     * @param duration Entier de durée d'intégration
     * @param prod  tableau de production
     * 
     * @return e énergie produite sur la durée considérée
     */
    public double integrate(int duration, double[] prod) {
        double e = 0;
        // Test duree < 1440 : Arrêt de l'intégration
        try {
            // Intégration
            for (int i = 0; i < duration; i++) {
                e = e + prod[i];
            }
        } catch (ArrayIndexOutOfBoundsException error) {
            System.out.println("Arrêt de l'intégration à 1440 = durée de la journée");
        }
        // Arrondi à deux décimales
        e = Math.round(e / 60.00 * 10.0) / 10.0;
        return e;
    }
}
