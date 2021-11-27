package GraphicInterface.GIProd;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;



public class ProdModel{

    public int number_of_days = 365;

    public int number_of_constant_production = 1;
    public int number_of_periodic_production = 1;

    
    public void plotProd(){
        // Création des Systèmes de Prod et listes associées
        Production.ProductionSystem s1 = new Production.PeriodicSystem("PanneauSolaire", 600, "sin", 520, 300, 480, 1200);
        Production.ProductionSystem s2 = new Production.ConstantSystem("CentraleCharbon", 900, "const");
        ArrayList<Production.ProductionSystem> listSys1 = new ArrayList<Production.ProductionSystem>();
        listSys1.add(s1);
        ArrayList<Production.ProductionSystem> listSys2 = new ArrayList<Production.ProductionSystem>();
        listSys2.add(s2);

        // Création des Points d'Injection et listes associées
        Production.InjectionPoint IP1 = new Production.InjectionPoint("Centrale1", 1, listSys1);
        Production.InjectionPoint IP2 = new Production.InjectionPoint("Centrale2", 1, listSys2);
        ArrayList<Production.InjectionPoint> listOfInj1 = new ArrayList<Production.InjectionPoint>();
        listOfInj1.add(IP1);
        ArrayList<Production.InjectionPoint> listOfInj2 = new ArrayList<Production.InjectionPoint>();
        listOfInj2.add(IP2);

        // Création des Productions
        Production.Production P1 = new Production.Production(listOfInj1);
        Production.Production P2 = new Production.Production(listOfInj2);
        double[] prodPeriodic = P1.generate(1); // On prend le premier jour par ex
        double[] prodConstant = P2.generate(1);

        // Test affichage console des Systèmes
        System.out.println(s1.toString());
        System.out.println(s2.toString());

        // Test affichage des Points d'Injection
        IP1.display();
        IP2.display();

        // Test affichage console d'une production
        P1.displayPowProd(prodPeriodic);
        P1.displaylistIP();

        // Test intégration sur plus d'une journée (affichage message)
        System.out.println(P1.integrate(2000, prodPeriodic));

        // Test affichage des productions dans un graph
        Plot plot = new Plot();
        for (int i = 0; i < prodConstant.length; i++) {
            plot.addPoint(0, i, prodConstant[i], true);
        }
        for (int i = 0; i < prodPeriodic.length; i++) {
            plot.addPoint(1, i, prodPeriodic[i], true);
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
