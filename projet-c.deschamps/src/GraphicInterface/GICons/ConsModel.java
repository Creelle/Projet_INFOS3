package GraphicInterface.GICons;

import java.util.ArrayList;
import javax.swing.JFrame;
import ptolemy.plot.Plot;

import Consumption.*;


public class ConsModel{

    public int number_of_days = 365;
    public int nbHouses = 1;

    public int number_of_constant_device = 1;
    public int number_of_periodic_device = 1;
    public int number_model1 = 1;
    public int number_model2 = 1;
    public int number_model3 = 1;
    public int number_model4 = 1;

    Device dev1;
    ArrayList<Device> listDevice1 = new ArrayList<Device>();

    Device dev2;
    ArrayList<Device> listDevice2 = new ArrayList<Device>();

    DeliveryPoint DP1; 
    DeliveryPoint DP2;

    ArrayList<DeliveryPoint> listOfDeliv1 = new ArrayList<DeliveryPoint>();
    ArrayList<DeliveryPoint> listOfDeliv2 = new ArrayList<DeliveryPoint>();
    
    Consumption C1;
    Consumption C2;

    Consumption C3; // for the nbHouses
    
    public void ConsCreation(){
        listDevice1 = new ArrayList<Device>();
        listDevice2 = new ArrayList<Device>();

        // Création des consommations
        
        for(int i = 0; i<number_of_constant_device;i++){
            dev1= new PeriodicDevice("Climatisation", 2000, "ete", 300, 300, 720, 1020);
            listDevice1.add(dev1);
        }
        
        
        for(int i = 0; i<number_of_periodic_device;i++){
            dev2 = new ConstantDevice("Radiateur", 500, "hiver");
            listDevice2.add(dev2);
        }

        for(int i = 0; i<number_model1;i++){
            dev1= new Model1("Model1", 500, 100, 10);
            //dev1 = newModel1(String name, double PowerMax, double tCharge, double Period);
            listDevice1.add(dev1);
        }

        for(int i = 0; i<number_model2;i++){
            dev1= new Model2(500,"Model2",100,1000,10,5);
            //Model2(double PowerMax, String name, double PowerMinRequest, double PowerMaxRequest, int dayMax, int hMax);
            listDevice1.add(dev1);
        }

        for(int i = 0; i<number_model3;i++){
            dev1= new Model3(100,500,"Model3",2,10,2);
            //double PowerMin, double PowerMax, String name, int nCycles, int dayMax, double factMin
            listDevice1.add(dev1);
        }

        for(int i = 0; i<number_model4;i++){
            dev1= new Model4(100,500,"Model4",10.0,2.0,2);
            //double PowerMin, double PowerMax, String name, double coefLin, double tau, int cycles
            listDevice1.add(dev1);
        }
        
      
        // Création des Points de Livraison et listes associées
        DP1 = new DeliveryPoint("ConstantFoyer", 1, listDevice1);
        DP2 = new DeliveryPoint("PeriodicFoyer", 1, listDevice2);
        
        listOfDeliv1.add(DP1);
        listOfDeliv2.add(DP2);

        C1 = new Consumption(listOfDeliv1);
        C2 = new Consumption(listOfDeliv2);

    }

    public void plotCons(){
        ConsCreation();

        double[] prodPeriodic = C1.generate(1); // On prend le premier jour par ex
        double[] prodConstant = C2.generate(1);
        
        
       
        // Test affichage des productions sur une journée
        Plot plot = new Plot();
        for (int i = 0; i < prodConstant.length; i++) {
            plot.addPoint(0, i, prodConstant[i], true);
        }
        for (int i = 0; i < prodPeriodic.length; i++) {
            plot.addPoint(1, i, prodPeriodic[i], true);
        }
        plot.addLegend(0, "Constant consumption");
        plot.addLegend(1, "Periodic consumption");
        JFrame frame = new JFrame("Point consumption");
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }

    public void plotProdYear(){

        ConsCreation();
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

    public void displaylistDP(){
        C1.displaylistDP();
        C2.displaylistDP();
    }

    public void plotConsNbHouses(){
        C3 = new Consumption(nbHouses);

        System.out.print("[");
        for (DeliveryPoint DP : C3.getListDelivery()) {
            System.out.print(DP.getName() + "; ");
        }
        System.out.println("]");

        Plot plot1 = new Plot();
        
        double pConsMoy3;
        double[] cons3;
        

        for (int j = 0; j < number_of_days; j++) {
            cons3 = C3.generate(j);
            pConsMoy3 = Math.round(C3.integrate(cons3.length - 1, cons3) * 60 * 10.0 / 1440) / 10.0;
            plot1.addPoint(0, j, pConsMoy3, true);
           
        }
        plot1.addLegend(0, "Consumption of " + nbHouses + "Houses");
        JFrame frame1 = new JFrame("Consumption of houses");
        frame1.add(plot1);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    
}
