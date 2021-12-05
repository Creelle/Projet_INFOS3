package Consumption;

import javax.swing.JFrame;

import ptolemy.plot.Plot;

/**
 * Main class to plot each model made
 */

public class MainCons {
    public static void main(String[] args) {
        

        /* Creating the objects */
        Model1 mod1 = new Model1("PhoneCharger",30, 250, 60);
        Model2 mod2 = new Model2(1000, "Heater", 200, 2000, 33, 5);
        Model3 mod3 = new Model3(20,200,"Frigo",5,100,0.2);
        Model4 mod4 = new Model4(20, 700, "Grille-pain", 4, 0.5, 4, 0.4);

        /* Here you can choose the day that you want to generate the production
        of the models */
        int day = 33;

        double[] prod1 = new double[1440];
        double[] prod2 = new double[1440];
        double[] prod3 = new double[1440];
        double[] prod4 = new double[1440];
        
        mod1.addCons(prod1, day);
        mod2.addCons(prod2, day);
        mod3.addCons(prod3, day);
        mod4.addCons(prod4, day);
        
        double hours;

        Plot plot1 = new Plot();

        for(int i=0; i<1440; i++){
            hours = i/1.0;
            plot1.addPoint(0, hours, prod1[i], true);
        }
        plot1.setLineStyle("solid", 0);

        plot1.addLegend(0, "Model 1");

        JFrame frame1 = new JFrame("Tests1");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        Plot plot2 = new Plot();

        for(int i=0; i<1440; i++){
            hours = i/1.0;
            plot2.addPoint(0, hours, prod2[i], true);
        }
        plot2.setLineStyle("solid", 0);

        plot2.addLegend(0, "Model 2");

        JFrame frame2 = new JFrame("Tests2");
        frame2.add(plot2);
        frame2.pack();
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Plot plot3 = new Plot();

        for(int i=0; i<1440; i++){
            hours = i/1.0;
            plot3.addPoint(0, hours, prod3[i], true);
        }
        plot3.setLineStyle("solid", 0);

        plot3.addLegend(0, "Model 3");

        JFrame frame3 = new JFrame("Tests3");
        frame3.add(plot3);
        frame3.pack();
        frame3.setVisible(true);
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Plot plot4 = new Plot();

        for(int i=0; i<1440; i++){
            hours = i/1.0;
            plot4.addPoint(0, hours, prod4[i], true);
        }
        plot4.setLineStyle("solid", 0);

        plot4.addLegend(0, "Model 4");

        JFrame frame4 = new JFrame("Tests4");
        frame4.add(plot4);
        frame4.pack();
        frame4.setVisible(true);
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
