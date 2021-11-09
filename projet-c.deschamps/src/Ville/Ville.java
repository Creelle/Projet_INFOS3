package Ville;

import Consommation.Consommation;
import Production.Production;

public class Ville {

    // Attributs
    private int nbFoyers; // Nombre d'habitations
    private Production prodVille; // Production de la Ville
    private Consommation consVille; // Consommation de la Ville

    /**
     * Constructeur primaire
     */
    public Ville() {
        nbFoyers = 1000;
        prodVille = new Production();
        consVille = new Consommation();
    }

    /**
     * Constructeur explicite
     * 
     * @param nbFoyers
     */
    public Ville(int nbFoyers) {
        this.nbFoyers = nbFoyers;
        prodVille = new Production(nbFoyers);
        consVille = new Consommation(nbFoyers);
    }

    // Setters and Getters

    public int getNbFoyers() {
        return this.nbFoyers;
    }

    public void setNbFoyers(int nbFoyers) {
        this.nbFoyers = nbFoyers;
    }

    public Production getProdVille() {
        return this.prodVille;
    }

    public void setProdVille(Production prodVille) {
        this.prodVille = prodVille;
    }

    public Consommation getConsVille() {
        return this.consVille;
    }

    public void setConsVille(Consommation consVille) {
        this.consVille = consVille;
    }

    // Méthodes

    /**
     * Affichage console de la liste des Points d'Injection et des Points de
     * Livraison
     */
    public void displayVille() {
        System.out.println("Production :");
        prodVille.displaylistPI();
        System.out.println("Consommation");
        consVille.displaylistPL();
    }

    /**
     * Comparaison de la consommation et de la production Vérifie qu'à aucun moment
     * la Ville est en déficit énergétique
     */
    public void compare(int j) {
        double[] prod = prodVille.generer(j);
        double[] cons = consVille.generer(j);
        for (int i = 0; i < prod.length; i++) {
            if (prod[i] - cons[i] < 0) {
                System.out.println("A la minute " + i + " la production est inférieure à la consommation");
            }
        }
    }

    /**
     * Display format CSV des consommation et production à chaque minute de la
     * journée
     */
    public void displayCSVJour(int j) {
        double[] prod = prodVille.generer(j);
        double[] cons = consVille.generer(j);
        for (int i = 0; i < cons.length; i++) {
            System.out.println(i + " ; " + cons[i] + " ; " + prod[i] + " ; " + consVille.integrer(i, cons) + " ; "
                    + prodVille.integrer(i, prod));
        }
    }

    /**
     * Display format CSV des énergies consommée et produite depuis le début de
     * l'année au jour J
     */
    public void displayCSVAn() {
        for (int j = 1; j < 366; j++) {
            double[] prod = prodVille.generer(j);
            double[] cons = consVille.generer(j);
            System.out
                    .println(j + " ; " + Math.round(consVille.integrer(cons.length - 1, cons) / 1440 * 10.0 * 60) / 10.0
                            + " ; " + Math.round(prodVille.integrer(prod.length - 1, prod) * 60 * 10.0 / 1440) / 10.0
                            + " ; " + j * consVille.integrer(cons.length - 1, cons) + " ; "
                            + Math.round(j * prodVille.integrer(prod.length - 1, prod) * 10.0) / 10.0);
        }

    }
}