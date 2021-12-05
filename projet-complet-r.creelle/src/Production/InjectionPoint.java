package Production;

import java.util.ArrayList;

public class InjectionPoint {

    // Attributs
    String name; // Nom du Point d'Injection
    int nbSys; // Nombre de système de Production dans le Point d'Injection
    ArrayList<ProductionSystem> listSys; // Liste de tous les Systèmes de prod
                                    // dans le Point d'Injection

    /**
     * Constructeur primaire
     */
    public InjectionPoint() {
        name = "Centrale";
        nbSys = 1;
        listSys = new ArrayList<ProductionSystem>(nbSys);
    }

    /**
     * Constructeur explicite
     * 
     * @param name
     * @param nbSys
     * @param listSys
     */
    public InjectionPoint(String name, int nbSys, ArrayList<ProductionSystem> listSys) {
        this.name = name;
        this.nbSys = nbSys;
        this.listSys = listSys;
    }

    // Setters and Getters

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNbSys() {
        return this.nbSys;
    }

    public void setNbSys(int nbSys) {
        this.nbSys = nbSys;
    }

    public ArrayList<ProductionSystem> getListSys() {
        return this.listSys;
    }

    public void setListSys(ArrayList<ProductionSystem> listSys) {
        this.listSys = listSys;
    }

    // display console des Points de Livraison
    public void display() {
        System.out.print(
                "[" + "Name : " + getName() + " ; Number of Systems : " + getNbSys() + " ; List of Systems : (");
        for (ProductionSystem S : listSys) {
            System.out.print(S.getName() + " ");
        }

        System.out.println(")]");
    }

}
