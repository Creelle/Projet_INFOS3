package Network;

import java.util.ArrayList;

import City.City;

public class Path {

    //Attributs
    ArrayList<Integer> listNumberCities;
    double lenPath;
    double meanLineicLoss;

    //Constructeur primaire
    public Path(){
        listNumberCities = new ArrayList<>();
        lenPath = 0;
        meanLineicLoss = 1.0;
    }

    //Constructeur explicite
    public Path(ArrayList<Integer> listNumberCities, double lenPath, double meanLineicLoss){
        this.listNumberCities = listNumberCities;
        this.lenPath = lenPath;
        this.meanLineicLoss = meanLineicLoss;
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

    public double getMeanLineicLoss() {
        return this.meanLineicLoss;
    }

    public void setMeanLineicLoss(double meanLineicLoss) {
        this.meanLineicLoss = meanLineicLoss;
    }


    //Méthode d'affichage
    public void displayPath(){
        System.out.print("[");
        for(int num : listNumberCities){
                System.out.print(num + " ");
            }
            System.out.println("]");
        System.out.println("Length = "+Math.round(lenPath*10.0)/10.0);
        System.out.println("Mean lineic loss of Power = "+Math.round(meanLineicLoss*10.0)/10.0);
    }
    
    //Méthode de transport de l'énergie le long d'un chemin
    public void injectPower(City cityProd, City cityReciever, ArrayList<double[]> listTableProd, double maxConsOfDay){
        double[] prodCityProd = listTableProd.get(cityProd.getNumber()-1);
        double[] prodCityReciever = listTableProd.get(cityReciever.getNumber()-1);
        for(int i =0; i<1440; i++){
            //Envoi de la puissance maximale nécessaire sur la journée à la ville
            prodCityReciever[i]=maxConsOfDay;
            //Déduction de la puissance produite disponible dans la ville produtrice
            prodCityProd[i] = prodCityProd[i] - meanLineicLoss*lenPath - maxConsOfDay;
        }
    }
}