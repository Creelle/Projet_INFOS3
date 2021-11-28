package GraphicInterface.GIProd;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;
import Production.*;



public class ProdModel{

    public int number_of_days = 365;

    public int number_of_solarpanels = 10;
    public int number_of_windmills = 25;
    public int number_of_coal = 1;
    public int number_of_nuclear = 0;
    public int number_of_hydraulic = 0;

    ProductionSystem s1;
    ArrayList<ProductionSystem> listSys1;

    ProductionSystem s2;
    ArrayList<ProductionSystem> listSys2;
    InjectionPoint IP1;
    InjectionPoint IP2;
    ArrayList<InjectionPoint> listOfInj1;
    ArrayList<InjectionPoint> listOfInj2;
    Production P1;
    Production P2;

    public void ProdCreation(){
        // Création des Systèmes de Prod et listes associées
        
        listSys1= new ArrayList<ProductionSystem>();

        for(int i = 0; i<number_of_solarpanels;i++){
            s1 = new Solar(500000,10000,10000);
            listSys1.add(s1);
        }
        for(int i = 0; i<number_of_windmills;i++){
            s1 = new Eolian(1000000,10000,10000);
            listSys1.add(s1);
        }
        //for(int i = 0; i<number_of_hydraulic;i++){
            //s1 = new PeriodicSystem("TurbineHydraulique", 5000000, "const", 720, 240, 360, 1440);
            ///listSys1.add(s1);
        //}

        listSys2 = new ArrayList<ProductionSystem>();

        //for(int i = 0; i<number_of_coal;i++){
            //s2 = new ConstantSystem("CentraleCharbon", 25000000, "const");
            //listSys2.add(s2);
        //}

        for(int i = 0; i<number_of_nuclear;i++){
            s2 = new Nuclear(10000000,100000,0,10000);// en W
            listSys2.add(s2);
        }

       
        

        // Création des Points d'Injection et listes associées
        IP1 = new InjectionPoint("Centrale1", 1, listSys1);
        IP2 = new InjectionPoint("Centrale2", 1, listSys2);
        listOfInj1 = new ArrayList<InjectionPoint>();
        listOfInj1.add(IP1);
        listOfInj2 = new ArrayList<InjectionPoint>();
        listOfInj2.add(IP2);
        // Création des Productions
        P1 = new Production(listOfInj1);
        P2 = new Production(listOfInj2);
        



    }

    public void plotProd(){
        ProdCreation();
        double[] prodPeriodic = P1.generate(1); // On prend le premier jour par ex
        double[] prodConstant = P2.generate(1);
        
        
       
        // Test affichage des productions sur une journée
        Plot plot = new Plot();
        for (int i = 0; i < prodConstant.length; i++) {
            plot.addPoint(0, i, prodConstant[i], true);
        }
        for (int i = 0; i < prodPeriodic.length; i++) {
            plot.addPoint(1, i, prodPeriodic[i], true);
        }
        plot.addLegend(0, "Constant production");
        plot.addLegend(1, "Periodic production");
        JFrame frame = new JFrame("Point production");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }

    public void plotProdYear(){
        ProdCreation();

        // Test affichage des production sur l'année
        Plot plot1 = new Plot();
        double[] prod1;
        double[] prod2;
        double pProdMoy1;
        double pProdMoy2;
        for (int j = 1; j < number_of_days+1; j++) {
            prod1 = P1.generate(j);
            prod2 = P2.generate(j);
            pProdMoy1 = Math.round(P1.integrate(prod1.length - 1, prod1) * 60 * 10.0 / 1440) / 10.0;
            pProdMoy2 = Math.round(P2.integrate(prod2.length - 1, prod2) * 60 * 10.0 / 1440) / 10.0;
            plot1.addPoint(0, j, pProdMoy1, true);
            plot1.addPoint(1, j, pProdMoy2, true);
        }
        plot1.addLegend(0, "Periodic");
        plot1.addLegend(1, "Constant");
        JFrame frame1 = new JFrame("Mean production");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }    
}
