package City;

import Consumption.Consumption;
import Production.Production;

public class City {

    // Attributs
    private int nbHouses; // Nombre d'habitations
    private Production cityProd; // Production de la Ville
    private Consumption cityCons; // Consommation de la Ville
    /*private double x; //Coordonnées x de la ville
    private double y; //Coordonées y de la ville
    private int number; //Numéro (étiquette) de la Ville   */ 

    /**
     * Constructeur primaire
     */
    public City() {
        nbHouses = 1000;
        cityProd = new Production();
        cityCons = new Consumption();
    }

    /**
     * Constructeur explicite
     * 
     * @param nbHouses
     */
    public City(int nbHouses) {
        this.nbHouses = nbHouses;
        cityProd = new Production(nbHouses);
        cityCons = new Consumption(nbHouses);
    }

    // Setters and Getters

    public int getNbHouses() {
        return this.nbHouses;
    }

    public void setNbHouses(int nbHouses) {
        this.nbHouses = nbHouses;
    }

    public Production getCityProd() {
        return this.cityProd;
    }

    public void setCityProd(Production cityProd) {
        this.cityProd = cityProd;
    }

    public Consumption getCityCons() {
        return this.cityCons;
    }

    public void setCityCons(Consumption cityCons) {
        this.cityCons = cityCons;
    }

    // Méthodes

    /**
     * Affichage console de la liste des Points d'Injection et des Points de
     * Livraison
     */
    public void displayVille() {
        System.out.println("Production :");
        cityProd.displaylistIP();
        System.out.println("Consommation");
        cityCons.displaylistDP();
    }

    /**
     * Comparaison de la consommation et de la production Vérifie qu'à aucun moment
     * la Ville est en déficit énergétique
     */
    public void compare(int j) {
        double[] prod = cityProd.generate(j);
        double[] cons = cityCons.generate(j);
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
    public void displayCSVDay(int j) {
        double[] prod = cityProd.generate(j);
        double[] cons = cityCons.generate(j);
        for (int i = 0; i < cons.length; i++) {
            System.out.println(i + " ; " + cons[i] + " ; " + prod[i] + " ; " + cityCons.integrate(i, cons) + " ; "
                    + cityProd.integrate(i, prod));
        }
    }

    /**
     * Display format CSV des énergies consommée et produite depuis le début de
     * l'année au jour J
     */
    public void displayCSVYear() {
        for (int j = 1; j < 366; j++) {
            double[] prod = cityProd.generate(j);
            double[] cons = cityCons.generate(j);
            System.out
                    .println(j + " ; " + Math.round(cityCons.integrate(cons.length - 1, cons) / 1440 * 10.0 * 60) / 10.0
                            + " ; " + Math.round(cityProd.integrate(prod.length - 1, prod) * 60 * 10.0 / 1440) / 10.0
                            + " ; " + j * cityCons.integrate(cons.length - 1, cons) + " ; "
                            + Math.round(j * cityProd.integrate(prod.length - 1, prod) * 10.0) / 10.0);
        }

    }
}