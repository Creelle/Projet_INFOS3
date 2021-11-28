package Production;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;

public class ProductionMain {
    public static void main(String[] args) {

        // Création des Systèmes de Prod et listes associées
        //ProductionSystem s1 = new PeriodicSystem("PanneauSolaire", 600, "sin", 520, 300, 480, 1200);
        ProductionSystem s1 = new Solar(2500000,1500000,4000000);

        ProductionSystem s2 = new Nuclear(20000000,35000000, 0.0023, 400);
        ArrayList<ProductionSystem> listSys1 = new ArrayList<ProductionSystem>();
        listSys1.add(s1);
        ArrayList<ProductionSystem> listSys2 = new ArrayList<ProductionSystem>();
        listSys2.add(s2);

        // Création des Points d'Injection et listes associées
        InjectionPoint IP1 = new InjectionPoint("Centrale1", 1, listSys1);
        InjectionPoint IP2 = new InjectionPoint("Centrale2", 1, listSys2);
        ArrayList<InjectionPoint> listOfInj1 = new ArrayList<InjectionPoint>();
        listOfInj1.add(IP1);
        ArrayList<InjectionPoint> listOfInj2 = new ArrayList<InjectionPoint>();
        listOfInj2.add(IP2);

        // Création des Productions
        Production P1 = new Production(listOfInj1);
        Production P2 = new Production(listOfInj2);
        double[] prodSolar = P1.generate(200); // On prend le premier jour par ex
        double[] prodNuclear = P2.generate(200);

        // Test affichage console des Systèmes
        System.out.println(s1.toString());
        System.out.println(s2.toString());

        // Test affichage des Points d'Injection
        IP1.display();
        IP2.display();

        // Test affichage console d'une production
        P1.displayPowProd(prodSolar);
        P1.displaylistIP();

        // Test intégration sur plus d'une journée (affichage message)
        System.out.println(P1.integrate(2000, prodSolar));

        // Test affichage des productions dans un graph
        Plot plot = new Plot();
        for (int i = 0; i < prodNuclear.length; i++) {
            plot.addPoint(0, i, prodNuclear[i], true);
        }
        for (int i = 0; i < prodSolar.length; i++) {
            plot.addPoint(1, i, prodSolar[i], true);
        }
        plot.addLegend(0, "Production Nuclear");
        plot.addLegend(1, "Production Solar");
        JFrame frame = new JFrame("Production des points");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Test affichage des production sur l'année
        Plot plot1 = new Plot();
        for (int j = 1; j < 366; j++) {
            double[] prod1 = P1.generate(j);
            double[] prod2 = P2.generate(j);
            double pProdMoy1 = Math.round(P1.integrate(prod1.length - 1, prod1) * 60 * 10.0 / 1440) / 10.0;
            double pProdMoy2 = Math.round(P2.integrate(prod2.length - 1, prod2) * 60 * 10.0 / 1440) / 10.0;
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
