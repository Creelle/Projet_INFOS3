package Consommation;

import java.util.ArrayList;
import Ville.Energie;

public class Consommation implements Energie {

    // Attributs
    private ArrayList<PointLivraison> listLivr; // Liste de tous les points de Livraison
    private int deltaT; // Pas de temps = 1 min
    private int nbFoyers; // Nombre de foyers dans la Ville

    /**
     * Constructeur primaire
     */
    public Consommation() {
        listLivr = new ArrayList<PointLivraison>();
        deltaT = 1;
        nbFoyers = 1000;
    }

    /**
     * Constructeur explicite partiel la valeur de deltaT reste inchangée égale à 1
     * min et 1000 Foyers Ce constructeur ne sert que pour les tests unitaires sur
     * les unités de consommation
     * 
     * @param listLivr
     */
    public Consommation(ArrayList<PointLivraison> listLivr) {
        this.listLivr = listLivr;
        deltaT = 1;
        nbFoyers = 1000;
    }

    /**
     * Constructeur à partir du nombre de foyers uniquement
     * 
     * @param nbFoyers
     */
    public Consommation(int nbFoyers) {
        listLivr = creerSysConso(nbFoyers);
        deltaT = 1;
        this.nbFoyers = nbFoyers;

    }

    // Getters
    public ArrayList<PointLivraison> getListLivr() {
        return this.listLivr;
    }

    public int getDeltaT() {
        return this.deltaT;
    }

    public int getNbFoyers() {
        return this.nbFoyers;
    }

    /**
     * Affichage dans la console de tous les Points de Livraison
     */
    public void displaylistPL() {
        System.out.print("[");
        for (PointLivraison PL : listLivr) {
            System.out.print(PL.getName() + "; ");
        }
        System.out.println("]");
    }

    // Creer l'ensemble du système de consommation de façon pseudo aléatoire
    /**
     * La taille de la production dépend de la taille de la ville
     * 
     * @param nbFoyers nombre de foyers dans la ville
     * @return la liste des Points d'Injection
     */
    public ArrayList<PointLivraison> creerSysConso(int nbFoyers) {
        ArrayList<PointLivraison> listPL = new ArrayList<>();
        for (int i = 1; i <= nbFoyers; i++) {
            ArrayList<Appareil> listApp = new ArrayList<>();
            int k = 0; // Compteur du nombre d'appareils dans le foyer
            // Appareils présents dans toutes les maisons
            Appareil a1 = new AppareilPeriodique("Lumière", 400, "const", 780, 120, 360, 1440);
            Appareil a2 = new AppareilConstant("Frigo", 10, "const");
            Appareil a3 = new AppareilConstant("Cumulus", 100, "const");
            Appareil a4 = new AppareilPeriodique("PlaquesElectriques", 500, "const", 1440, 60, 1140, 1440);
            Appareil a5 = new AppareilConstant("BoxInternet", 50, "const");
            listApp.add(a1);
            listApp.add(a2);
            listApp.add(a3);
            listApp.add(a4);
            listApp.add(a5);
            k = 5;

            // La maison a-t-elle une télé?
            if (Math.random() < 0.5) {
                Appareil a6 = new AppareilPeriodique("Télévision", 20, "const", 240, 180, 1170, 1440);
                listApp.add(a6);
                k += 1;
            }
            // La maison a-t-elle un grille-pain?
            if (Math.random() < 0.5) {
                Appareil a7 = new AppareilPeriodique("GrillePain", 700, "const", 60, 10, 480, 540);
                listApp.add(a7);
                k += 1;
            }
            // La maison a-t-elle un congélateur?
            if (Math.random() < 0.5) {
                Appareil a8 = new AppareilConstant("Congélateur", 50, "const");
                listApp.add(a8);
                k += 1;
            }
            // Combien la maison a-t-elle de radiateurs?
            double nb = 4 + Math.floor(Math.random() * (10));
            for (int m = 0; m < nb; m++) {
                Appareil a9 = new AppareilConstant("Radiateur", 1000, "hiver");
                listApp.add(a9);
                k += 1;
            }
            PointLivraison Foyer = new PointLivraison("Foyer", k, listApp);
            listPL.add(Foyer);
            // La maison a-t-elle une climatisation?
            if (Math.random() < 0.8) {
                Appareil a10 = new AppareilPeriodique("Climatisation", 2000, "ete", 300, 300, 720, 1020);
                listApp.add(a10);
                k += 1;
            }
        }

        return listPL;
    }

    // Générer et display associé
    /**
     * Génération du tableau de consommation : chaque valeur représente la conso à
     * la minute m
     * 
     * @return cons tableau de consommation
     */
    public double[] generer(int j) {
        double[] cons = new double[1440];
        for (PointLivraison PL : listLivr) {
            ArrayList<Appareil> listApp = PL.getListAppareils();
            for (Appareil A : listApp) {
                A.addCons(cons, j);
            }
        }
        return cons;
    }

    /**
     * Affichage console du tableau de consommation
     * 
     * @param cons
     */
    public void displayPuissCons(double[] cons) {
        System.out.print("[");
        for (double d : cons) {
            double arrondi = Math.round(d * 10.0) / 10.0;
            System.out.print(arrondi + ", ");
        }
        System.out.println("]");
    }

    /**
     * Implémentation de l'intégration à partir d'un tableau de consommation
     * 
     * @param duree Entier de durée d'intégration
     * @param cons  tableau de consommation
     * 
     * @return e énergie consommée sur la durée considérée
     */
    public double integrer(int duree, double[] cons) {
        double e = 0;
        // Test duree < 1440 : Arrêt de l'intégration
        try {
            // Intégration
            for (int i = 0; i < duree; i++) {
                e = e + cons[i];
            }
        } catch (ArrayIndexOutOfBoundsException error) {
            System.out.println("Arrêt de l'intégration à 1440 = durée de la journée");
        }
        // Arrondi à deux décimales
        e = Math.round(e / 60.0 * 10.0) / 10.0;

        return e;
    }
}
