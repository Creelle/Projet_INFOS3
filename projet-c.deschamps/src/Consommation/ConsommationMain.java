package Consommation;

import java.util.ArrayList;
import javax.swing.JFrame;

import ptolemy.plot.Plot;

public class ConsommationMain {
    public static void main(String[] args) {

        // Création des appareils et listes associées
        Appareil app1 = new AppareilPeriodique("Climatisation", 2000, "ete", 300, 300, 720, 1020);
        Appareil app2 = new AppareilConstant("Radiateur", 500, "hiver");
        ArrayList<Appareil> listApp1 = new ArrayList<Appareil>();
        listApp1.add(app1);
        ArrayList<Appareil> listApp2 = new ArrayList<Appareil>();
        listApp2.add(app2);

        // Création des Points de Livraison et listes associées
        PointLivraison PL1 = new PointLivraison("Foyer1", 1, listApp1);
        PointLivraison PL2 = new PointLivraison("Foyer2", 1, listApp2);
        ArrayList<PointLivraison> listOfLivr1 = new ArrayList<PointLivraison>();
        listOfLivr1.add(PL1);
        ArrayList<PointLivraison> listOfLivr2 = new ArrayList<PointLivraison>();
        listOfLivr2.add(PL2);

        // Création des consommations
        Consommation C1 = new Consommation(listOfLivr1);
        Consommation C2 = new Consommation(listOfLivr2);
        double[] consPeriodique = C1.generer(1); // Exemple pour le jour 1
        double[] consConstante = C2.generer(1);

        // Test affichage console des Appareils
        System.out.println(app1.toString());
        System.out.println(app2.toString());

        // Test affichage des Points de Livraison (Foyers)
        PL1.display();
        PL2.display();

        // Test affichage des Consommations
        C1.displaylistPL();
        C1.displayPuissCons(consPeriodique);
        // Au besoin, celle pour la consommation constante:
        // C2.displaylistPL();
        // C2.displayPuissCons(consConstante);

        // Test intégration sur plus d'une journée (affichage message)
        C1.integrer(2000, consPeriodique);

        // Test affichage graphique
        Plot plot = new Plot();
        for (int i = 0; i < consConstante.length; i++) {
            plot.addPoint(0, i, consConstante[i], true);
        }
        for (int i = 0; i < consPeriodique.length; i++) {
            plot.addPoint(1, i, consPeriodique[i], true);
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
            double[] cons1 = C1.generer(j);
            double[] cons2 = C2.generer(j);
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
