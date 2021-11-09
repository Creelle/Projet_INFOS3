package Production;

import java.util.ArrayList;
import Ville.Energie;

public class Production implements Energie {

    // Attributs
    private ArrayList<PointInjection> listInj; // Liste des Points dInjection
    private int deltaT; // Pas de temps égal à 1 min
    private int nbFoyers; // Nombre de foyer dans la Ville

    /**
     * Constructeur primaire
     **/
    public Production() {
        listInj = new ArrayList<PointInjection>();
        deltaT = 1;
        nbFoyers = 1000;
    }

    /**
     * Constructeur explicite tel que le pas de temps deltaT reste inchangé égal à 1
     * min et 1000 foyers Ce constructeur ne sert que pour les tests unitaires sur
     * les unités de production
     * 
     * @param listInj
     */
    public Production(ArrayList<PointInjection> listInj) {
        this.listInj = listInj;
        deltaT = 1;
        this.nbFoyers = 1000;
    }

    /**
     * Constructeur à partir du nombre de foyers uniquement
     * 
     * @param nbFoyers
     */
    public Production(int nbFoyers) {
        deltaT = 1;
        this.nbFoyers = nbFoyers;
        listInj = creerSysProd(nbFoyers);
    }

    // Getters
    public ArrayList<PointInjection> getListInj() {
        return this.listInj;
    }

    public int getDeltaT() {
        return deltaT;
    }

    public int getNbFoyers() {
        return this.nbFoyers;
    }

    /**
     * Affichage console de la liste de tous les points d'injection
     **/
    public void displaylistPI() {
        for (PointInjection PI : listInj) {
            PI.display();
        }
    }

    // Creer l'ensemble du système de production de façon pseudo aléatoire
    /**
     * La taille de la production dépend de la taille de la ville
     * 
     * @param nbFoyers nombre de foyers dans la ville
     * @return la liste des Points d'Injection
     */
    public ArrayList<PointInjection> creerSysProd(int nbFoyers) {
        ArrayList<PointInjection> listPI = new ArrayList<>();

        // Création Ferme Solaire
        ArrayList<SystemeProd> listSolaire = new ArrayList<>();
        double nb = 10 + Math.floor(Math.random() * (990));
        int k = 0; // compteur du nombre de système
        for (int i = 0; i < nb; i++) {
            SystemeProd s1 = new SystemePeriodique("PanneauSolaire", 200, "sin", 720, 600, 480, 1200);
            listSolaire.add(s1);
            k += 1;
        }
        PointInjection PI1 = new PointInjection("FermeSolaire", k, listSolaire);
        listPI.add(PI1);

        // Création Ferme éolienne
        ArrayList<SystemeProd> listEolienne = new ArrayList<>();
        nb = Math.floor(Math.random() * (20));
        k = 0;
        for (int i = 0; i < nb; i++) {
            SystemeProd s2 = new SystemePeriodique("Eolienne", 400000, "const", 120, 60, 240, 1140);
            listEolienne.add(s2);
            k += 1;
        }
        PointInjection PI2 = new PointInjection("FermeEolienne", k, listEolienne);
        listPI.add(PI2);

        // Création Centrale Hydraulique
        if (5000 > nbFoyers) {
            ArrayList<SystemeProd> listHydraulique = new ArrayList<>();
            SystemeProd s3 = new SystemePeriodique("TurbineHydraulique", 300000, "const", 720, 240, 360, 1440);
            listHydraulique.add(s3);
            k = 1;
            PointInjection PI3 = new PointInjection("CentraleHydraulique", k, listHydraulique);
            listPI.add(PI3);
        } else if (5000 <= nbFoyers && nbFoyers < 10000) {
            ArrayList<SystemeProd> listHydraulique = new ArrayList<>();
            SystemeProd s3 = new SystemePeriodique("TurbineHydraulique", 6000000, "const", 720, 240, 360, 1440);
            listHydraulique.add(s3);
            k = 1;
            PointInjection PI3 = new PointInjection("CentraleHydraulique", k, listHydraulique);
            listPI.add(PI3);
        } else {
            ArrayList<SystemeProd> listHydraulique = new ArrayList<>();
            SystemeProd s3a = new SystemePeriodique("TurbineHydraulique", 5000000, "const", 720, 240, 360, 1440);
            SystemeProd s3b = new SystemePeriodique("TurbineHydraulique", 5000000, "const", 720, 240, 360, 1440);
            listHydraulique.add(s3a);
            listHydraulique.add(s3b);
            k = 2;
            PointInjection PI3 = new PointInjection("CentraleHydraulique", k, listHydraulique);
            listPI.add(PI3);
        }

        // Création Centrale Fossile
        if (nbFoyers < 10000) {
            ArrayList<SystemeProd> listFossile = new ArrayList<>();
            SystemeProd s4 = new SystemeConstant("RéacteurNucléaire", 95000000, "const");
            listFossile.add(s4);
            k = 1;
            PointInjection PI4 = new PointInjection("CentraleFossile", k, listFossile);
            listPI.add(PI4);
        } else if (nbFoyers >= 10000) {
            ArrayList<SystemeProd> listFossile = new ArrayList<>();
            SystemeProd s4a = new SystemeConstant("RéacteurNucléaire", 900000000, "const");
            SystemeProd s4b = new SystemeConstant("TurbineCharbon", 250000000, "const");
            listFossile.add(s4a);
            listFossile.add(s4b);
            k = 2;
            PointInjection PI4 = new PointInjection("CentraleFossile", k, listFossile);
            listPI.add(PI4);
        }

        return listPI;
    }

    // Générer le tableau de production et display associé
    /**
     * Génération du tableau de prroduction : chaque valeur représente la production
     * à la minute m
     * 
     * @return prod tableau de production
     */
    public double[] generer(int j) {
        double[] prod = new double[1440];
        for (PointInjection PI : listInj) {
            ArrayList<SystemeProd> listSys = PI.getListSys();
            for (SystemeProd S : listSys) {
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
    public void displayPuissProd(double[] prod) {
        System.out.print("[");
        for (double d : prod) {
            double arrondi = Math.round(d * 10.0) / 10.0;
            System.out.print(arrondi + ", ");
        }
        System.out.println("]");
    }

    /**
     * Implémentation de l'intégration à partir d'un tableau de production
     * 
     * @param duree Entier de durée d'intégration
     * @param prod  tableau de production
     * 
     * @return e énergie produite sur la durée considérée
     */
    public double integrer(int duree, double[] prod) {
        double e = 0;
        // Test duree < 1440 : Arrêt de l'intégration
        try {
            // Intégration
            for (int i = 0; i < duree; i++) {
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
