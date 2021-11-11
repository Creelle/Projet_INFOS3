package Consumption;

import java.util.ArrayList;

public class DeliveryPoint {

    // Attributs
    String name; // Nom du Point de Livraison
    int nbDevice; // Nombre d'appareils dans le Point de Livraison
    ArrayList<Device> listDevices; // Liste de tous les appareils dans le Point de Livraison

    /**
     * Constructeur primaire
     */
    public DeliveryPoint() {
        name = "Foyer";
        nbDevice = 10;
        listDevices = new ArrayList<Device>(nbDevice);
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param nbDevice
     * @param listDevices
     */
    public DeliveryPoint(String name, int nbDevice, ArrayList<Device> listDevices) {
        this.name = name;
        this.nbDevice = nbDevice;
        this.listDevices = listDevices;
    }

    // Setters and Getters

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbDevice() {
        return this.nbDevice;
    }

    public void setNbDevice(int nbDevice) {
        this.nbDevice = nbDevice;
    }

    public ArrayList<Device> getListDevices() {
        return this.listDevices;
    }

    public void setListDevices(ArrayList<Device> listDevices) {
        this.listDevices = listDevices;
    }

    // display console des Points de Livraison
    public void display() {
        System.out.print(
                "[" + "name : " + getName() + " ; nombre Appareils : " + getNbDevice() + " ; Liste des appareils : (");
        for (Device D : listDevices) {
            System.out.print(D.getName() + " ");
        }

        System.out.println(")]");
    }

}
