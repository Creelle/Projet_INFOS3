package GraphicInterface;

import javax.swing.*;
import java.awt.*;

import GraphicInterface.GICons.ConsController;
import GraphicInterface.GIProd.ProdController;
import GraphicInterface.GICity.CityController;
import GraphicInterface.GINetwork.NetController;

public class View {
    

   // Controllers
    Controller controller;
    ConsController conscontroller;
    ProdController prodcontroller;
    CityController citycontroller;
    NetController netcontroller;

   //Textfields

   // Texfields pane1
    public JTextField textField;
    public JTextField nConstantF;
    public JTextField nPeriodicF;
    public JTextField nModel1F; 
    public JTextField nModel2F;
    public JTextField nModel3F;
    public JTextField nModel4F;
    public JTextField nbHousesconsF;

    //Textfields pane2
    public JTextField textField2;
    public JTextField nsolarF;
    public JTextField nwindF;
    public JTextField ncoalF;
    public JTextField nhydraulicF;
    public JTextField nnuclearF;

    //Textfields pane3 
    public JTextField textField3;
    public JTextField nbHousesF;

    //Textfields pane4
    public JTextField textField4;
    public JTextField CityNbHousesF;
    public JTextField CityIndexF;
    public JTextField nCitiesF;
    public JTextField CSVreadF;
    
   // Buttons
    
    private JButton exitButton1;
    private JButton exitButton2;
    private JButton exitButton3;
    private JButton exitButton4;

   // Buttons pane 1 
    private JButton plotButton1;
    private JButton submitB;
    private JButton plotProdYearB;
    private JButton displaylistDP;
  
    //Buttons pane 2
    private JButton mainB;
    private JButton mainBYear;
    private JButton submitB2;
    
    //Buttons pane 3
    private JButton CheckProdB;
    private JButton displayCSVdayB;
    private JButton displayCSVyearB;
    private JButton plotDayB;
    private JButton plotYearB;

    //Buttons pane 4
    private JButton nCitiesB;
    private JButton changePopB;
    private JButton displayCitiesB;
    private JButton displayLinksB;
    private JButton plotNetGraphB;
    private JButton plotCityB;
    private JButton plotNetB;
    private JButton CSVnet;
    private JButton CSVcity;
    private JButton CSVread;

    //Labels

    //label pane1
    JLabel label;
    JLabel nbHousesL;
    JLabel nModel1L;
    JLabel nModel2L;
    JLabel nModel3L;
    JLabel nModel4L;
    JLabel nPeriodicL;
    JLabel nConstantL;

    //label pane2
    public JLabel nsolarL;
    public JLabel nwindL;
    public JLabel ncoalL;
    public JLabel nhydraulicL;
    public JLabel nnuclearL;

    // Panes
    private JPanel pane1;
    private JPanel pane2;
    private JPanel pane3;
    private JPanel pane4;

    private JTabbedPane tabs;

    private JFrame mainWindow;

    public View(){
        
        //Buttons creation
        
        exitButton1 = new JButton("EXIT");
        exitButton2 = new JButton("EXIT");
        exitButton3 = new JButton("EXIT");
        exitButton4 = new JButton("EXIT");

        //Buttons pane1
        
        submitB = new JButton("Number of days");
        plotButton1 = new JButton("Plot 1 day");
        plotProdYearB = new JButton("Plot year");
        displaylistDP = new JButton("Display devices");
       
        //Buttons pane2
        mainB = new JButton("Day production plot");
        submitB2 = new JButton("Number of days");
        mainBYear = new JButton("Year production plot");

        //Buttons pane3 
        JLabel nbHousesL = new JLabel("Number of houses");
        JLabel submitL3 = new JLabel("Number of days");
        CheckProdB = new JButton("Check production for 1 day");
        displayCSVdayB = new JButton("Display day");
        displayCSVyearB = new JButton("Display year");
        plotDayB = new JButton("Plot day");
        plotYearB = new JButton("Plot year");

        //Buttons pane4
        nCitiesB= new JButton("Number of cities");
        JLabel submitL4= new JLabel("Number of days");
        JLabel cityIndexL= new JLabel("City index");
        JLabel CityNbHousesL= new JLabel("nB Houses in the city at the index");
        changePopB= new JButton("Change nBHouses of the index city");
        displayCitiesB= new JButton("Display the list of cities");
        displayLinksB= new JButton("Display the links between the cities");
        plotNetGraphB= new JButton("Plot the graph of the network");
        plotCityB= new JButton("Plot the index city");
        plotNetB= new JButton("Plot the consumption / production of the network");
        CSVnet = new JButton("CSV year");
        CSVcity = new JButton("CSV index city year");
        CSVread = new JButton("CSV read");


        //Textfields
        //textF pane1
        textField = new JTextField(20);
        textField.setText("365");

        nConstantF = new JTextField(20);
        nConstantF.setText("1");
        
        nPeriodicF = new JTextField(20);
        nPeriodicF.setText("1");

        nModel1F = new JTextField(20);
        nModel1F.setText("0");

        nModel2F = new JTextField(20);
        nModel2F.setText("0");

        nModel3F = new JTextField(20);
        nModel3F.setText("0");

        nModel4F = new JTextField(20);
        nModel4F.setText("0");

        nbHousesconsF = new JTextField(20);
        nbHousesconsF.setText("1");

        //textF pane2
        textField2 = new JTextField(20);
        textField2.setText("365");

        nsolarF = new JTextField(20);
        nsolarF.setText("10");

        nwindF = new JTextField(20);
        nwindF.setText("25");

        ncoalF = new JTextField(20);
        ncoalF.setText("1");

        nhydraulicF = new JTextField(20);
        nhydraulicF.setText("0");

        nnuclearF = new JTextField(20);
        nnuclearF.setText("0");

        // pane 3 
        textField3 = new JTextField(20);
        textField3.setText("365");

        nbHousesF =  new JTextField(20);
        nbHousesF.setText("6000");

        // pane 4 
        textField4 = new JTextField(20);
        textField4.setText("365");

        CityNbHousesF = new JTextField(20);
        CityNbHousesF.setText("6000");

        nCitiesF = new JTextField(20);
        nCitiesF.setText("5");

        CityIndexF = new JTextField(20);
        CityIndexF.setText("0");

        CSVreadF = new JTextField(50);
        CSVreadF.setText("../projet-complet-r.creelle/data/TO_LOAD/network.csv");

        
        // pane creator
        pane1 = new JPanel();
        pane1.setLayout(new GridLayout(6,3));//size and organisation of the panel
        
        pane2= new JPanel();
        pane2.setLayout(new GridLayout(4,4));

        pane3= new JPanel();
        pane3.setLayout(new GridLayout(3,4));

        pane4= new JPanel();
        pane4.setLayout(new GridLayout(5,4));

        //tabs creator
        tabs = new JTabbedPane();
        tabs.addTab("Consumption", pane1);
        tabs.addTab("Production", pane2);
        tabs.addTab("City", pane3);
        tabs.addTab("Network",pane4);
        tabs.setForegroundAt(1, Color.RED);
        tabs.setForegroundAt(0,Color.BLUE);
        tabs.setForegroundAt(2,Color.GREEN);
        tabs.setForegroundAt(3,Color.BLACK);

        //frame creator
        mainWindow = new JFrame("Power Grid Interface");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(300,300);
        mainWindow.add(tabs);

        //pane1 filling
        label = new JLabel("Consummer simulator");
        label.setForeground(Color.BLUE);

        pane1.add(label);
        pane1.add(exitButton1);
        pane1.add(submitB);
        pane1.add(textField);

        nbHousesL = new JLabel("Number of Houses");
        pane1.add(nbHousesL);
        pane1.add(nbHousesconsF);

        nConstantL = new JLabel("Number of Constant devices");
        pane1.add(nConstantL);
        pane1.add(nConstantF);

        nPeriodicL = new JLabel("Number of Periodic devices");
        pane1.add(nPeriodicL);
        pane1.add(nPeriodicF);

        nModel1L = new JLabel("Number of Model1 devices");
        pane1.add(nModel1L);
        pane1.add(nModel1F);

        nModel2L = new JLabel("Number of Model2 devices");
        pane1.add(nModel2L);
        pane1.add(nModel2F);

        nModel3L = new JLabel("Number of Model3 devices");
        pane1.add(nModel3L);
        pane1.add(nModel3F);

        nModel4L = new JLabel("Number of Model4 devices");
        pane1.add(nModel4L);
        pane1.add(nModel4F);

        JLabel dummyL = new JLabel("");
        pane1.add(dummyL);
        JLabel dummyL2 = new JLabel("");
        pane1.add(dummyL2);

        pane1.add(plotButton1);
        pane1.add(plotProdYearB);
        pane1.add(plotProdYearB);
        pane1.add(displaylistDP);
        
        

        //pane 2 filling

        label = new JLabel("Producer simulator");
        label.setForeground(Color.RED);
        pane2.add(label);
        pane2.add(exitButton2);
        pane2.add(mainB);
        pane2.add(mainBYear);
        pane2.add(submitB2);
        pane2.add(textField2);

        nnuclearL = new JLabel("Number of nuclear plants");
        pane2.add(nnuclearL);
        pane2.add(nnuclearF);

        ncoalL = new JLabel("Number of coal plants");
        pane2.add(ncoalL);
        pane2.add(ncoalF);

        nhydraulicL = new JLabel("Number of hydraulic plants");
        pane2.add(nhydraulicL);
        pane2.add(nhydraulicF);

        nsolarL = new JLabel("Number of solar plants");
        pane2.add(nsolarL);
        pane2.add(nsolarF);

        nwindL = new JLabel("Number of wind plants");
        pane2.add(nwindL);
        pane2.add(nwindF);

        // pane 3 filling

        label = new JLabel("City simulator");
        label.setForeground(Color.GREEN);
        pane3.add(label);
        pane3.add(exitButton3);
        pane3.add(submitL3);
        pane3.add(textField3);
        pane3.add(nbHousesL);
        pane3.add(nbHousesF);
        pane3.add(CheckProdB);
        pane3.add(displayCSVdayB);
        pane3.add(displayCSVyearB);
        pane3.add(plotDayB);
        pane3.add(plotYearB);

        // pane 4 filling
        label = new JLabel("Network simulator");
        label.setForeground(Color.BLACK);
        pane4.add(label);
        pane4.add(exitButton4);
        pane4.add(submitL4);
        pane4.add(textField4);
        pane4.add(nCitiesB);
        pane4.add(nCitiesF);
        pane4.add(cityIndexL);
        pane4.add(CityIndexF);
        pane4.add(CityNbHousesL);
        pane4.add(CityNbHousesF);
        pane4.add(changePopB);
        pane4.add(displayCitiesB);
        pane4.add(displayLinksB);
        pane4.add(plotNetGraphB);
        pane4.add(plotCityB);
        pane4.add(plotNetB);
        pane4.add(CSVnet);
        pane4.add(CSVcity);
        pane4.add(CSVread);
        pane4.add(CSVreadF);
        
        
     }

     public void setController(Controller controller, ConsController conscontroller,ProdController prodcontrol, CityController citycontrol, NetController netcontrol){
        /**
         * set the controller for each button definesd in view
         * 
         * @param  : 5 controllers 1 for the exit , conumption, production,city and network
         * 
         * 
         */

        this.controller = controller;
        this.conscontroller = conscontroller;
        this.prodcontroller = prodcontrol;
        this.citycontroller = citycontrol;
        this.netcontroller = netcontrol;

        
        // Exit butttons
        exitButton1.addActionListener(controller);
        exitButton2.addActionListener(controller);
        exitButton3.addActionListener(controller);
        exitButton4.addActionListener(controller);

        // Consumption buttons
        plotButton1.addActionListener(conscontroller);
        submitB.addActionListener(conscontroller);
        plotProdYearB.addActionListener(conscontroller);
        displaylistDP.addActionListener(conscontroller);
        
        // Production buttons
        mainB.addActionListener(prodcontroller);
        mainBYear.addActionListener(prodcontroller);
        submitB2.addActionListener(prodcontroller);

        // city buttons
        CheckProdB.addActionListener(citycontroller);
        displayCSVdayB.addActionListener(citycontroller);
        displayCSVyearB.addActionListener(citycontroller);
        plotDayB.addActionListener(citycontroller);
        plotYearB.addActionListener(citycontroller);
 
        // net buttons
        nCitiesB.addActionListener(netcontroller);
        changePopB.addActionListener(netcontroller);
        displayCitiesB.addActionListener(netcontroller);
        displayLinksB.addActionListener(netcontroller);
        plotNetGraphB.addActionListener(netcontroller);
        plotCityB.addActionListener(netcontroller);
        plotNetB.addActionListener(netcontroller);
        CSVnet.addActionListener(netcontroller);
        CSVcity.addActionListener(netcontroller);
        CSVread.addActionListener(netcontroller);
        
      }

     public void show(){
        /**
         * Plot the graphic view
         */
        mainWindow.pack();
        mainWindow.setVisible(true);
     }

}

    

