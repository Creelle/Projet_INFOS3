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

    //Textfiles pane2
    public JTextField textField2;
    public JTextField nsolarF;
    public JTextField nwindF;
    public JTextField ncoalF;
    public JTextField nhydraulicF;
    public JTextField nnuclearF;
    
   // Buttons
    
    private JButton exitButton1;
    private JButton exitButton2;
    private JButton exitButton3;
    private JButton exitButton4;

   // Buttons pane 1 
    private JButton plotButton1;
    private JButton submitB;
    private JButton nConstantB;
    private JButton nPeriodicB;

    //Buttons pane 2

    private JButton mainB;
    private JButton mainBYear;
    private JButton submitB2;
    
    

    //Labels
    JLabel label;

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
        plotButton1 = new JButton("Plot button");
        exitButton1 = new JButton("EXIT");
        exitButton2 = new JButton("EXIT");
        exitButton3 = new JButton("EXIT");
        exitButton4 = new JButton("EXIT");

        //pane1
        submitB = new JButton("Number of days");
        nConstantB = new JButton("Number of constant devices");
        nPeriodicB = new JButton("Number of periodic devices");

        //pane2
        mainB = new JButton("Day production plot");
        submitB2 = new JButton("Number of days");
        mainBYear = new JButton("Year production plot");


        //Textfields
        //textF pane1
        textField = new JTextField(20);
        textField.setText("365");

        nConstantF = new JTextField(20);
        nConstantF.setText("1");
        
        nPeriodicF = new JTextField(20);
        nPeriodicF.setText("1");

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


        
        // pane creator
        pane1 = new JPanel();
        pane1.setLayout(new GridLayout(2,2));
        
        pane2= new JPanel();
        pane2.setLayout(new GridLayout(2,2));

        pane3= new JPanel();
        pane3.setLayout(new GridLayout(2,2));

        pane4= new JPanel();
        pane4.setLayout(new GridLayout(2,2));

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
        label = new JLabel("Hello welcolme in the consummer simulator");
        label.setForeground(Color.BLUE);

        pane1.add(label);
        pane1.add(plotButton1);
        pane1.add(exitButton1);
        pane1.add(submitB);
        pane1.add(textField);
        pane1.add(nConstantB);
        pane1.add(nConstantF);
        pane1.add(nPeriodicB);
        pane1.add(nPeriodicF);
        

        //pane 2 filling
        pane2.add(exitButton2);
        pane2.add(mainB);
        pane2.add(mainBYear);
        pane2.add(submitB2);
        pane2.add(textField2);

        JLabel fillLabel = new JLabel("");
        pane2.add(fillLabel);

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
        pane3.add(exitButton3);

        // pane 4 filling
        pane4.add(exitButton4);

        
        
     }

     public void setController(Controller controller, ConsController conscontroller,ProdController prodcontrol, CityController citycontrol, NetController netcontrol){
        this.controller = controller;
        this.conscontroller = conscontroller;
        this.prodcontroller = prodcontrol;
        this.citycontroller = citycontrol;
        this.netcontroller = netcontrol;
        

        exitButton1.addActionListener(this.controller);
        exitButton2.addActionListener(this.controller);
        exitButton3.addActionListener(this.controller);
        exitButton4.addActionListener(this.controller);

        plotButton1.addActionListener(this.conscontroller);
        submitB.addActionListener(this.conscontroller);
        nConstantB.addActionListener(this.conscontroller);
        nPeriodicB.addActionListener(this.conscontroller);

        mainB.addActionListener(this.prodcontroller);
        mainBYear.addActionListener(this.prodcontroller);
        submitB2.addActionListener(this.prodcontroller);
        
      }

     public void show(){
        mainWindow.pack();
        mainWindow.setVisible(true);
     }

     public void refresh(){
         
     }
}

    

