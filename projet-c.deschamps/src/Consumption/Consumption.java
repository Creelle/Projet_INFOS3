package Consumption;

import java.util.ArrayList;

import City.Energy;

public class Consumption implements Energy {

    // Attributs
    private ArrayList<DeliveryPoint> listDelivery; // Liste de tous les points de Livraison
    private int deltaT; // Pas de temps = 1 min
    private int nbHouses; // Nombre de foyers dans la Ville

    /**
     * Constructeur primaire
     */
    public Consumption() {
        listDelivery = new ArrayList<DeliveryPoint>();
        deltaT = 1;
        nbHouses = 1000;
    }

    /**
     * Constructeur explicite partiel la valeur de deltaT reste inchangée égale à 1
     * min et 1000 Foyers Ce constructeur ne sert que pour les tests unitaires sur
     * les unités de consommation
     * 
     * @param listDelivery
     */
    public Consumption(ArrayList<DeliveryPoint> listDelivery) {
        this.listDelivery = listDelivery;
        deltaT = 1;
        nbHouses = listDelivery.size();
    }

    /**
     * Constructeur à partir du nombre de foyers uniquement
     * 
     * @param nbHouses
     */
    public Consumption(int nbHouses) {
        listDelivery = creerSysConso(nbHouses);
        deltaT = 1;
        this.nbHouses = nbHouses;

    }

    // Getters
    public ArrayList<DeliveryPoint> getListDelivery() {
        return this.listDelivery;
    }

    public int getDeltaT() {
        return this.deltaT;
    }

    public int getNbHouses() {
        return this.nbHouses;
    }

    /**
     * Affichage dans la console de tous les Points de Livraison
     */
    public void displaylistDP() {
        System.out.print("[");
        for (DeliveryPoint DP : listDelivery) {
            System.out.print(DP.getName() + "; ");
        }
        System.out.println("]");
    }

    // Creer l'ensemble du système de consommation de façon pseudo aléatoire
    /**
     * La taille de la production dépend de la taille de la ville
     * 
     * @param nbHouses nombre de foyers dans la ville
     * @return la liste des Points d'Injection
     */
    public ArrayList<DeliveryPoint> creerSysConso(int nbHouses) {
        ArrayList<DeliveryPoint> listDP = new ArrayList<>();
        for (int i = 1; i <= nbHouses; i++) {
            ArrayList<Device> listDevice = new ArrayList<>();
            int k = 0; // Compteur du nombre d'appareils dans le foyer
            // Appareils présents dans toutes les maisons
            Device d1 = new PeriodicDevice("Lumière", 400, "const", 780, 120, 360, 1440);
            Device d2 = new ConstantDevice("Frigo", 10, "const");
            Device d3 = new ConstantDevice("Cumulus", 100, "const");
            Device d4 = new PeriodicDevice("PlaquesElectriques", 500, "const", 1440, 60, 1140, 1440);
            Device d5 = new ConstantDevice("BoxInternet", 50, "const");
            listDevice.add(d1);
            listDevice.add(d2);
            listDevice.add(d3);
            listDevice.add(d4);
            listDevice.add(d5);
            k = 5;

            // La maison a-t-elle une télé?
            if (Math.random() < 0.5) {
                Device d6 = new PeriodicDevice("Télévision", 20, "const", 240, 180, 1170, 1440);
                listDevice.add(d6);
                k += 1;
            }
            // La maison a-t-elle un grille-pain?
            if (Math.random() < 0.5) {
                Device d7 = new PeriodicDevice("GrillePain", 700, "const", 60, 10, 480, 540);
                listDevice.add(d7);
                k += 1;
            }
            // La maison a-t-elle un congélateur?
            if (Math.random() < 0.5) {
                Device d8 = new ConstantDevice("Congélateur", 50, "const");
                listDevice.add(d8);
                k += 1;
            }
            // Combien la maison a-t-elle de radiateurs?
            double nb = 4 + Math.floor(Math.random() * (10));
            for (int m = 0; m < nb; m++) {
                Device d9 = new ConstantDevice("Radiateur", 1000, "hiver");
                listDevice.add(d9);
                k += 1;
            }
            // La maison a-t-elle une climatisation?
            if (Math.random() < 0.8) {
                Device d10 = new PeriodicDevice("Climatisation", 2000, "ete", 300, 300, 720, 1020);
                listDevice.add(d10);
                k += 1;
            }
            DeliveryPoint Foyer = new DeliveryPoint("Foyer", k, listDevice);
            listDP.add(Foyer);
        }
        return listDP;
    }

    // Générer et display associé
    /**
     * Génération du tableau de consommation : chaque valeur représente la conso à
     * la minute m
     * 
     * @return cons tableau de consommation
     */
    public double[] generate(int j) {
        double[] cons = new double[1440];
        for (DeliveryPoint DP : listDelivery) {
            ArrayList<Device> listDevices = DP.getListDevices();
            for (Device D : listDevices) {
                D.addCons(cons, j);
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
    public double integrate(int duree, double[] cons) {
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
