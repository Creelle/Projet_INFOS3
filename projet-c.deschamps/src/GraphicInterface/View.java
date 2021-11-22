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

    //Labels
    JLabel label = new JLabel();

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
        submitB = new JButton("submit text");
        nConstantB = new JButton("Number of constant devices");
        nPeriodicB = new JButton("Number of periodic devices");

        //Textfields

        textField = new JTextField(20);
        textField.setText("365");

        nConstantF = new JTextField(20);
        nConstantF.setText("1");
        
        nPeriodicF = new JTextField(20);
        nPeriodicF.setText("1");
        
        
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


        // label creator
        label.setText("Hello welcolme in the consummer simulator");
        label.setForeground(Color.BLUE);

        //pane1 filling
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
      }

     public void show(){
        mainWindow.pack();
        mainWindow.setVisible(true);
     }

     public void refresh(){
         
     }
}

    

