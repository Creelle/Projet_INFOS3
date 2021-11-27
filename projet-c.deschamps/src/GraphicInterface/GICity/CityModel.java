package GraphicInterface.GICity;

import javax.swing.JFrame;
import ptolemy.plot.Plot;

import City.*;
import Production.*;
import Consumption.*;


public class CityModel{

    public int number_of_days = 365;
    public int nbHouses = 6000;
    City myCity;
    Production P;
    Consumption C;

    public void createCity(){
        myCity = new City(nbHouses);
        P = myCity.getCityProd();
        C = myCity.getCityCons();
    }
   
    public void checkCityProd(int j){
        createCity();
        // checks if the city consumption doesn't exceed city prod for 1 day
        myCity.compare(j);

    }

    public void displayCSVDay(int j){
        createCity();
        myCity.displayCSVDay(j);
    }

    public void displayCSVYear(){
        createCity();
        myCity.displayCSVYear();
    }

    public void displayCity(){
        createCity();
        myCity.displayCity();
    }

    
    public void plotCityDay(){
        createCity();

        /*
         * Affichage de la consommation (0), de la production (1) et du différentiel (2)
         * sur une journée particulière (ex j=1)
         */
        
        double[] prod = P.generate(1);
        double[] cons = C.generate(1);
        Plot plot = new Plot();
        for (int i = 0; i < cons.length; i++) {
            plot.addPoint(0, i, cons[i], true);
            plot.addPoint(1, i, prod[i], true);
            plot.addPoint(2, i, prod[i] - cons[i], true);
        }
        
        plot.addLegend(0, "Consumption");
        plot.addLegend(1, "Production");
        plot.addLegend(2, "Production surplus");
        JFrame frame = new JFrame("One day balance");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }

    public void plotCityYear(){
        createCity();
        /*
         * ***Attention ! ce graph met du temps à apparaître !***
         *
         * Affichage de la consommation moyenne (0), de la production moyenne (1) et du
         * différentiel (2) sur l'année
         */

        Plot plot1 = new Plot();
        double[] prod1;
        double[] cons1;
        double pConsMoy;
        double pProdMoy;
        double diff;

        for (int j = 0; j < number_of_days; j++) {
            prod1 = P.generate(j);
            cons1 = C.generate(j);
            pConsMoy = Math.round(C.integrate(cons1.length - 1, cons1) / 1440 * 10.0 * 60) / 10.0;
            pProdMoy = Math.round(P.integrate(prod1.length - 1, prod1) * 60 * 10.0 / 1440) / 10.0;
            diff = pProdMoy - pConsMoy;
            plot1.addPoint(0, j, pConsMoy, true);
            plot1.addPoint(1, j, pProdMoy, true);
            plot1.addPoint(2, j, diff, true);
        }
        plot1.addLegend(0, "Consumption");
        plot1.addLegend(1, "Production");
        plot1.addLegend(2, "Production surplus");
        JFrame frame1 = new JFrame("Balance over a year");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
