package Production;

import java.util.ArrayList;

public class PointInjection {

    // Attributs
    String name; // Nom du Point d'Injection
    int nombreSys; // Nombre de système de Production dans le Point d'Injection
    ArrayList<SystemeProd> listSys; // Liste de tous les Systèmes de prod
                                    // dans le Point d'Injection

    /**
     * Constructeur primaire
     */
    public PointInjection() {
        name = "Centrale";
        nombreSys = 1;
        listSys = new ArrayList<SystemeProd>(nombreSys);
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param nombreSys
     * @param listSys
     */
    public PointInjection(String name, int nombreSys, ArrayList<SystemeProd> listSys) {
        this.name = name;
        this.nombreSys = nombreSys;
        this.listSys = listSys;
    }

    // Setters and Getters

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNombreSys() {
        return this.nombreSys;
    }

    public void setNombreSys(int nombreSys) {
        this.nombreSys = nombreSys;
    }

    public ArrayList<SystemeProd> getListSys() {
        return this.listSys;
    }

    public void setListSys(ArrayList<SystemeProd> listSys) {
        this.listSys = listSys;
    }

    // display console des Points de Livraison
    public void display() {
        System.out.print(
                "[" + "name : " + getName() + " ; nombre Systèmes : " + getNombreSys() + " ; Liste des Systèmes : (");
        for (SystemeProd S : listSys) {
            System.out.print(S.getName() + " ");
        }

        System.out.println(")]");
    }

}
