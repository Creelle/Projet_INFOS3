package Production;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;

public class ProductionMain {
    public static void main(String[] args) {

        // Création des Systèmes de Prod et listes associées
        SystemeProd s1 = new SystemePeriodique("PanneauSolaire", 600, "sin", 520, 300, 480, 1200);
        SystemeProd s2 = new SystemeConstant("CentraleCharbon", 900, "const");
        ArrayList<SystemeProd> listSys1 = new ArrayList<SystemeProd>();
        listSys1.add(s1);
        ArrayList<SystemeProd> listSys2 = new ArrayList<SystemeProd>();
        listSys2.add(s2);

        // Création des Points d'Injection et listes associées
        PointInjection PI1 = new PointInjection("Centrale1", 1, listSys1);
        PointInjection PI2 = new PointInjection("Centrale2", 1, listSys2);
        ArrayList<PointInjection> listOfInj1 = new ArrayList<PointInjection>();
        listOfInj1.add(PI1);
        ArrayList<PointInjection> listOfInj2 = new ArrayList<PointInjection>();
        listOfInj2.add(PI2);

        // Création des Productions
        Production P1 = new Production(listOfInj1);
        Production P2 = new Production(listOfInj2);
        double[] prodPeriodique = P1.generer(1); // On prend le premier jour par ex
        double[] prodConstante = P2.generer(1);

        // Test affichage console des Systèmes
        System.out.println(s1.toString());
        System.out.println(s2.toString());

        // Test affichage des Points d'Injection
        PI1.display();
        PI2.display();

        // Test affichage console d'une production
        P1.displayPuissProd(prodPeriodique);
        P1.displaylistPI();

        // Test intégration sur plus d'une journée (affichage message)
        System.out.println(P1.integrer(2000, prodPeriodique));

        // Test affichage des productions dans un graph
        Plot plot = new Plot();
        for (int i = 0; i < prodConstante.length; i++) {
            plot.addPoint(0, i, prodConstante[i], true);
        }
        for (int i = 0; i < prodPeriodique.length; i++) {
            plot.addPoint(1, i, prodPeriodique[i], true);
        }
        plot.addLegend(0, "Production Constante");
        plot.addLegend(1, "Production périodique");
        JFrame frame = new JFrame("Production des points");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Test affichage des production sur l'année
        Plot plot1 = new Plot();
        for (int j = 1; j < 366; j++) {
            double[] prod1 = P1.generer(j);
            double[] prod2 = P2.generer(j);
            double pProdMoy1 = Math.round(P1.integrer(prod1.length - 1, prod1) * 60 * 10.0 / 1440) / 10.0;
            double pProdMoy2 = Math.round(P2.integrer(prod2.length - 1, prod2) * 60 * 10.0 / 1440) / 10.0;
            plot1.addPoint(0, j, pProdMoy1, true);
            plot1.addPoint(1, j, pProdMoy2, true);
        }
        plot1.addLegend(0, "Panneaux Solaires");
        plot1.addLegend(1, "CentraleCharbon");
        JFrame frame1 = new JFrame("Production moyenne");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
