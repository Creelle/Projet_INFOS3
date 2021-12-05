package Production;

import javax.swing.JFrame;

import ptolemy.plot.Plot;

/**
 * Main class to plot each model of producers made
 */

public class MainProd {
    public static void main(String[] args) {

        /* Creating the objects */
        Solar Solar = new Solar(250000,150000,400000);
        Eolian Eolian = new Eolian(400000, 10, 140);
        Nuclear Nuclear = new Nuclear(450000,600000, 0.0023, 4);
        Fossil Fossil = new Fossil(400000,4);
        Hydroeletric Hydroeletric = new Hydroeletric(5000000,100);

        /* Here you can choose the day that you want to generate the production
        of the models */
        int day = 200;

        double[] prod1 = new double[1440];
        double[] prod2 = new double[1440];
        double[] prod3 = new double[1440];
        double[] prod4 = new double[1440];
        double[] prod5 = new double[1440];
        
        Eolian.addProd(prod1,day);
        Solar.addProd(prod2,day);
        Nuclear.addProd(prod3,day);
        Fossil.addProd(prod4, day);
        Hydroeletric.addProd(prod5, day);

        double hours;

        Plot plot1 = new Plot();

        for(int i=0; i<1440; i++){
            hours = i/1.0;
            plot1.addPoint(0, hours, prod1[i], true);
        }
        plot1.setLineStyle("solid", 0);

        plot1.addLegend(0, "Eolian");

        JFrame frame1 = new JFrame("Eolian");
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

        plot2.addLegend(0, "Solar");

        JFrame frame2 = new JFrame("Solar");
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

        plot3.addLegend(0, "Nuclear");

        JFrame frame3 = new JFrame("Nuclear");
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

        plot4.addLegend(0, "Fossil");

        JFrame frame4 = new JFrame("Fossil");
        frame4.add(plot4);
        frame4.pack();
        frame4.setVisible(true);
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Plot plot5 = new Plot();

        for(int i=0; i<1440; i++){
            hours = i/1.0;
            plot5.addPoint(0, hours, prod5[i], true);
        }
        plot5.setLineStyle("solid", 0);

        plot5.addLegend(0, "Hydroeletric");

        JFrame frame5 = new JFrame("Hydroeletric");
        frame5.add(plot5);
        frame5.pack();
        frame5.setVisible(true);
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
