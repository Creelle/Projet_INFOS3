package GraphicInterface.GINetwork;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;


import Network.*;
import City.*;
import Consumption.*;
import Production.*;
import CSVbuilder.*;

public class NetModel{
    /*
    * Model for the network
     */

    public int number_of_days = 365;
    public int number_of_cities = 5;
    int index = 0;
    int nbHouses = 6000;

    Network net;
    ArrayList<City> cities;
    ArrayList<Link> links;

    String filepath = "../projet-c.deschamps/data/TO_LOAD/network.csv";
    
    public NetModel(){
        net = new Network(number_of_cities);
        cities = net.getListCities();
        links = net.getListLinks();
    }

    public void ChangePop(int index, int nbHouses){
        /**
         * Changes the nbHouses at the city at the specified index
         * @ param : int index :  index of the city
         * @ param : nbHouses : number of houses
         */
        cities.set(index, new City(nbHouses));
        net = new Network(number_of_cities,cities, links);
        cities = net.getListCities();
        links = net.getListLinks();
        net.displayListCities();
    }

    public void ChangeCities(){
        //updates the network
        net = new Network(number_of_cities);
        cities=net.getListCities();
        links = net.getListLinks();
    }

    public void displayListCities(){
        /**
         * Displays the list of cities
         */
        net.displayListCities();
    }

    public void displayListLinks(){
        /**
         * Displays the links between the cities
         */
        net.displayListLinks();
    }

    public void plotGraphNetworks(){
        /**
         * Plot the links between cities
         */
        net.plotGraphNetwork();
    }

    public void plotSimulationOfCity(int index,int day){
        /**
         * Plot the simulation on the city at the specified index
         */
        City c = net.getCityInList(index, cities);
        Production p = c.getCityProd();
        Consumption cons = c.getCityCons();
        net.plotSimulationOfCity(p.generate(day), cons.generate(day), c);
    }

    public void plotNet(){
         /**
         * Plot the simution of the whole network over the number of days
         */

        // Création des listes de tableaux
        ArrayList<double[]> listTableMeanProd = new ArrayList<>();
        ArrayList<double[]> listTableMeanCons = new ArrayList<>();

        // Initialisation des tableaux vides
        for (int k = 0; k < cities.size(); k++) {
            double[] prod = new double[number_of_days];
            double[] cons = new double[number_of_days];
            listTableMeanProd.add(prod);
            listTableMeanCons.add(cons);
        }

        // Mise à jour des tableaux moyens
        for (int j = 1; j < number_of_days; j++) {
            ArrayList<double[]> listMeanTables;
			try {
				listMeanTables = net.simulation(j + 1, false);
                for (int k = 0; k < cities.size(); k++) {
                    // Mise à jour de ces tableaux
                    listTableMeanProd.get(k)[j] = listMeanTables.get(0)[k];
                    listTableMeanCons.get(k)[j] = listMeanTables.get(1)[k];
                }
            } catch (IOException e) {
				
				e.printStackTrace();
                break;
			}
            
        }
        // Impression des graphes
        for (int k = 0; k < listTableMeanCons.size(); k++) {
            City city = cities.get(k);
            double[] meanCons = listTableMeanCons.get(k);
            double[] meanProd = listTableMeanProd.get(k);
            Plot plot = new Plot();
            for (int i = 0; i < meanCons.length; i++) {
                plot.addPoint(0, i, meanCons[i], true);
                plot.addPoint(1, i, meanProd[i], true);
            }
            plot.addLegend(0, "Consumption");
            plot.addLegend(1, "Production");
            String nameFrame = "Ville n°" + city.getNumber() + ", Producer : " + city.getProducer()
                    + ", Number of Houses : " + city.getNbHouses();
            JFrame frame = new JFrame(nameFrame);
            frame.add(plot);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        

    }

    public void CSVNetworkYear(){
         /**
         * Gives a csv file with the results over a year
         */
        try{
            net.CSVNetworkYear();
        }
        catch(IOException e){
            System.out.println("Something went wrong");
        }

    }

   public void CSVCityYear(){
        /**
         * Gives a csv file with the results over a year for the city at specified index
         */
       try{
       net.CSVCityYear(cities.get(index));
       }
       catch(IOException e){
           System.out.println("Something went wrong");
       }
   }

   public void read(String filepath){
        /**
         * Creates a network from a specified file 
         */
       try{
        net= Reader.read(filepath);
        number_of_cities = net.getNbCities();
        net.displayListCities();
        net.displayListLinks();
        net.plotGraphNetwork();
        net.simulation(1, true); 

       }
       catch(IOException e){
           System.out.println("didn't find the file path");
           System.out.println("Here's an example of an accepted filepath : ");
           System.out.println(filepath);
       }
   }

   
}
