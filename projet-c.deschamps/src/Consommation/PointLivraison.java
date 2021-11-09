package Consommation;

import java.util.ArrayList;

public class PointLivraison {

    // Attributs
    String name; // Nom du Point de Livraison
    int nombreApp; // Nombre d'appareils dans le Point de Livraison
    ArrayList<Appareil> listAppareils; // Liste de tous les appareils dans le Point de Livraison

    /**
     * Constructeur primaire
     */
    public PointLivraison() {
        name = "Foyer";
        nombreApp = 10;
        listAppareils = new ArrayList<Appareil>(nombreApp);
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param nombreApp
     * @param listAppareils
     */
    public PointLivraison(String name, int nombreApp, ArrayList<Appareil> listAppareils) {
        this.name = name;
        this.nombreApp = nombreApp;
        this.listAppareils = listAppareils;
    }

    // Setters and Getters

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNombreApp() {
        return this.nombreApp;
    }

    public void setNombreApp(int nombreApp) {
        this.nombreApp = nombreApp;
    }

    public ArrayList<Appareil> getListAppareils() {
        return this.listAppareils;
    }

    public void setListAppareils(ArrayList<Appareil> listAppareils) {
        this.listAppareils = listAppareils;
    }

    // display console des Points de Livraison
    public void display() {
        System.out.print(
                "[" + "name : " + getName() + " ; nombre Appareils : " + getNombreApp() + " ; Liste des appareils : (");
        for (Appareil A : listAppareils) {
            System.out.print(A.getName() + " ");
        }

        System.out.println(")]");
    }

}
