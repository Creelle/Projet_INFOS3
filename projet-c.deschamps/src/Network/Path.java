package Network;

import java.util.ArrayList;

import City.City;

public class Path {

    //Attributs
    ArrayList<Integer> listNumberCities; //Liste des numéros des villes du chemin
    double lenPath; //Longueur du chemin
    double lossPath; //Puissance perdue sur le chemin

    //Constructeur primaire
    public Path(){
        listNumberCities = new ArrayList<>();
        lenPath = 0;
        lossPath = 0;
    }

    //Constructeur explicite
    public Path(ArrayList<Integer> listNumberCities, double lenPath, double lossPath){
        this.listNumberCities = listNumberCities;
        this.lenPath = lenPath;
        this.lossPath = lossPath;
    }

    //Setters and Getters

    public ArrayList<Integer> getListNumberCities() {
        return this.listNumberCities;
    }

    public void setListNumberCities(ArrayList<Integer> listNumberCities) {
        this.listNumberCities = listNumberCities;
    }

    public double getLenPath() {
        return this.lenPath;
    }

    public void setLenPath(double lenPath) {
        this.lenPath = lenPath;
    }

    public double getLossPath() {
        return this.lossPath;
    }

    public void setLossPath(double lossPath) {
        this.lossPath = lossPath;
    }


    //Méthode d'affichage
    public void displayPath(){
        System.out.print("[");
        for(int num : listNumberCities){
                System.out.print(num + " ");
            }
            System.out.print("]");
        System.out.print(", Length = "+Math.round(lenPath*10.0)/10.0);
        System.out.println(", Loss of Power = "+Math.round(lossPath*10.0)/10.0);
    }
    
    /**
     * Méthode de transport de l'énergie le long d'un chemin
     * 
     * @param cityProd      ville Productrice d'où part l'énergie
     * @param cityReciever  ville réceptrice de l'énergie
     * @param listTableProd liste des tableaux de production de toutes les villes du
     *                      réseau
     * @param maxConsOfDay  consommation max de la ville réceptrice sur la journée
     */
    public void injectPower(City cityProd, City cityReciever, ArrayList<double[]> listTableProd, double maxConsOfDay){
        double[] prodCityProd = listTableProd.get(cityProd.getNumber()-1);
        double[] prodCityReciever = listTableProd.get(cityReciever.getNumber()-1);
        for(int i =0; i<1440; i++){
            //Envoi de la puissance maximale nécessaire sur la journée à la ville
            prodCityReciever[i]=maxConsOfDay;
            //Déduction de la puissance produite disponible dans la ville produtrice
            prodCityProd[i] = prodCityProd[i] - lossPath - maxConsOfDay;
        }
    }
}