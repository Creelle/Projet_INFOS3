package City;

import javax.swing.JFrame;

import Consumption.Consumption;
import Production.Production;
import ptolemy.plot.Plot;


public class CityMain {

    public static void main(String[] args) {

        // Simulation pour la ville
        City myCityTest = new City(6000);
        // Pour le premier jour
        myCityTest.compare(1);
        myCityTest.displayCSVDay(1);
        // Sur l'année
        //myCityTest.displayCSVYear();

        /*
         * Affichage de la consommation (0), de la production (1) et du différentiel (2)
         * sur une journée particulière (ex j=1)
         */
        Production P = myCityTest.getCityProd();
        Consumption C = myCityTest.getCityCons();
        double[] prod = P.generate(1);
        double[] cons = C.generate(1);
        Plot plot = new Plot();
        for (int i = 0; i < cons.length; i++) {
            plot.addPoint(0, i, cons[i], true);
            plot.addPoint(1, i, prod[i], true);
            plot.addPoint(2, i, prod[i] - cons[i], true);
        }
        
        plot.addLegend(0, "Consommation");
        plot.addLegend(1, "Production");
        plot.addLegend(2, "Surplus de production");
        JFrame frame = new JFrame("Ma Ville Test le jour j=1");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /*
         * ***Attention ! ce graph met du temps à apparaître !***
         *
         * Affichage de la consommation moyenne (0), de la production moyenne (1) et du
         * différentiel (2) sur l'année
         */

        Plot plot1 = new Plot();
        for (int j = 1; j < 366; j++) {
            double[] prod1 = P.generate(j);
            double[] cons1 = C.generate(j);
            double pConsMoy = Math.round(C.integrate(cons1.length - 1, cons1) / 1440 * 10.0 * 60) / 10.0;
            double pProdMoy = Math.round(P.integrate(prod1.length - 1, prod1) * 60 * 10.0 / 1440) / 10.0;
            double diff = pProdMoy - pConsMoy;
            plot1.addPoint(0, j, pConsMoy, true);
            plot1.addPoint(1, j, pProdMoy, true);
            plot1.addPoint(2, j, diff, true);
        }
        plot1.addLegend(0, "Consommation Moyenne");
        plot1.addLegend(1, "Production Moyenne");
        plot1.addLegend(2, "Surplus de production");
        JFrame frame1 = new JFrame("Ma Ville Test sur l'Année");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
