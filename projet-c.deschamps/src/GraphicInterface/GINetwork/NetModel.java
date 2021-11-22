package GraphicInterface.GINetwork;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;


public class NetModel{

    public int number_of_days = 365;

    public int number_of_constant_device = 1;
    public int number_of_periodic_device = 1;

    
    public void plotCons(){

        // Création des consommations
        Consumption.Device dev1;
        ArrayList<Consumption.Device> listDevice1 = new ArrayList<Consumption.Device>();

        for(int i = 0; i<number_of_constant_device;i++){
            dev1= new Consumption.PeriodicDevice("Climatisation", 2000, "ete", 300, 300, 720, 1020);
            listDevice1.add(dev1);
        }
        
        ArrayList<Consumption.Device> listDevice2 = new ArrayList<Consumption.Device>();
       
        Consumption.Device dev2;
        for(int i = 0; i<number_of_periodic_device;i++){
            dev2 = new Consumption.ConstantDevice("Radiateur", 500, "hiver");
            listDevice2.add(dev2);
        }
        
      
        // Création des Points de Livraison et listes associées
        Consumption.DeliveryPoint DP1 = new Consumption.DeliveryPoint("ConstantFoyer", 1, listDevice1);
        Consumption.DeliveryPoint DP2 = new Consumption.DeliveryPoint("PeriodicFoyer", 1, listDevice2);
        ArrayList<Consumption.DeliveryPoint> listOfDeliv1 = new ArrayList<Consumption.DeliveryPoint>();
        listOfDeliv1.add(DP1);
        ArrayList<Consumption.DeliveryPoint> listOfDeliv2 = new ArrayList<Consumption.DeliveryPoint>();
        listOfDeliv2.add(DP2);

        Consumption.Consumption C1 = new Consumption.Consumption(listOfDeliv1);
        Consumption.Consumption C2 = new Consumption.Consumption(listOfDeliv2);
        
        Plot plot1 = new Plot();
        
        double pConsMoy1;
        double pConsMoy2;
        double[] cons1;
        double[] cons2;

        for (int j = 0; j < number_of_days; j++) {
            cons1 = C1.generate(j);
            cons2 = C2.generate(j);
            pConsMoy1 = Math.round(C1.integrate(cons1.length - 1, cons1) * 60 * 10.0 / 1440) / 10.0;
            pConsMoy2 = Math.round(C2.integrate(cons2.length - 1, cons2) * 60 * 10.0 / 1440) / 10.0;
            plot1.addPoint(0, j, pConsMoy1, true);
            plot1.addPoint(1, j, pConsMoy2, true);
        }
        plot1.addLegend(0, "Constant");
        plot1.addLegend(1, "Periodic");
        JFrame frame1 = new JFrame("Consommation moyenne");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
