package Consumption;

import java.util.ArrayList;
import javax.swing.JFrame;

import ptolemy.plot.Plot;

public class ConsumptionMain {
    public static void main(String[] args) {

        // Création des appareils et listes associées
        Device dev1 = new PeriodicDevice("Climatisation", 2000, "ete", 300, 300, 720, 1020);
        Device dev2 = new ConstantDevice("Radiateur", 500, "hiver");
        ArrayList<Device> listDevice1 = new ArrayList<Device>();
        listDevice1.add(dev1);
        ArrayList<Device> listDevice2 = new ArrayList<Device>();
        listDevice2.add(dev2);

        // Création des Points de Livraison et listes associées
        DeliveryPoint DP1 = new DeliveryPoint("Foyer1", 1, listDevice1);
        DeliveryPoint DP2 = new DeliveryPoint("Foyer2", 1, listDevice2);
        ArrayList<DeliveryPoint> listOfDeliv1 = new ArrayList<DeliveryPoint>();
        listOfDeliv1.add(DP1);
        ArrayList<DeliveryPoint> listOfDeliv2 = new ArrayList<DeliveryPoint>();
        listOfDeliv2.add(DP2);

        // Création des consommations
        Consumption C1 = new Consumption(listOfDeliv1);
        Consumption C2 = new Consumption(listOfDeliv2);
        double[] consPeriodic = C1.generate(1); // Exemple pour le jour 1
        double[] consConstant = C2.generate(1);

        // Test affichage console des Appareils
        System.out.println(dev1.toString());
        System.out.println(dev2.toString());

        // Test affichage des Points de Livraison (Foyers)
        DP1.display();
        DP2.display();

        // Test affichage des Consommations
        C1.displaylistDP();
        C1.displayPuissCons(consPeriodic);
        // Au besoin, celle pour la consommation constante:
        // C2.displaylistPL();
        // C2.displayPuissCons(consConstante);

        // Test intégration sur plus d'une journée (affichage message)
        C1.integrer(2000, consPeriodic);

        // Test affichage graphique
        Plot plot = new Plot();
        for (int i = 0; i < consConstant.length; i++) {
            plot.addPoint(0, i, consConstant[i], true);
        }
        for (int i = 0; i < consPeriodic.length; i++) {
            plot.addPoint(1, i, consPeriodic[i], true);
        }
        plot.addLegend(0, "Consommation Constante");
        plot.addLegend(1, "Consommation périodique");
        JFrame frame = new JFrame("Consommation des points");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Test affichage des production sur l'année
        Plot plot1 = new Plot();
        for (int j = 1; j < 366; j++) {
            double[] cons1 = C1.generate(j);
            double[] cons2 = C2.generate(j);
            double pConsMoy1 = Math.round(C1.integrer(cons1.length - 1, cons1) * 60 * 10.0 / 1440) / 10.0;
            double pConsMoy2 = Math.round(C2.integrer(cons2.length - 1, cons2) * 60 * 10.0 / 1440) / 10.0;
            plot1.addPoint(0, j, pConsMoy1, true);
            plot1.addPoint(1, j, pConsMoy2, true);
        }
        plot1.addLegend(0, "Climatisation");
        plot1.addLegend(1, "Radiateur");
        JFrame frame1 = new JFrame("Consommation moyenne");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
