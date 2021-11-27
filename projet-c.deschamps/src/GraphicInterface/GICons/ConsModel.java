package GraphicInterface.GICons;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;

import Consumption.*;


public class ConsModel{

    public int number_of_days = 365;

    public int number_of_constant_device = 1;
    public int number_of_periodic_device = 1;

    
    public void plotCons(){

        // Création des consommations
        Device dev1;
        ArrayList<Device> listDevice1 = new ArrayList<Device>();

        for(int i = 0; i<number_of_constant_device;i++){
            dev1= new PeriodicDevice("Climatisation", 2000, "ete", 300, 300, 720, 1020);
            listDevice1.add(dev1);
        }
        
        Device dev2;
        ArrayList<Device> listDevice2 = new ArrayList<Device>();
       
        
        for(int i = 0; i<number_of_periodic_device;i++){
            dev2 = new ConstantDevice("Radiateur", 500, "hiver");
            listDevice2.add(dev2);
        }
        
      
        // Création des Points de Livraison et listes associées
        DeliveryPoint DP1 = new DeliveryPoint("ConstantFoyer", 1, listDevice1);
        DeliveryPoint DP2 = new DeliveryPoint("PeriodicFoyer", 1, listDevice2);
        ArrayList<DeliveryPoint> listOfDeliv1 = new ArrayList<DeliveryPoint>();
        listOfDeliv1.add(DP1);
        ArrayList<DeliveryPoint> listOfDeliv2 = new ArrayList<DeliveryPoint>();
        listOfDeliv2.add(DP2);

        Consumption C1 = new Consumption(listOfDeliv1);
        Consumption C2 = new Consumption(listOfDeliv2);
        
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
