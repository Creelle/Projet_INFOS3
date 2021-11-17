package GraphicInterface;

import javax.swing.*;
import java.awt.*;

public class View {
    

    Controller controller;

    public JTextField textField;
    
    

    // components
    private JButton plotButton1;
    private JButton exitButton1;
    private JButton exitButton2;
    private JButton exitButton3;
    private JButton exitButton4;
    private JButton submitB;
    
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

        //Textfields

        textField = new JTextField(20);
        textField.setText("365");
        
        
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


        // label creation
        JLabel label = new JLabel();
        label.setText("Hello welcolme in the consummer simulator");
        label.setForeground(Color.BLUE);

        //pane1 filling
        pane1.add(label);
        pane1.add(plotButton1);
        pane1.add(exitButton1);
        pane1.add(submitB);
        pane1.add(textField);
        

        //pane 2 filling
        pane2.add(exitButton2);
        
        

        // pane 3 filling
        pane3.add(exitButton3);

        // pane 4 filling
        pane4.add(exitButton4);

        
        
     }

     public void setController(Controller controller){
        this.controller = controller;
        ExitController exit_controller = new ExitController();

        exitButton1.addActionListener(exit_controller);
        exitButton2.addActionListener(exit_controller);
        exitButton3.addActionListener(exit_controller);
        exitButton4.addActionListener(exit_controller);
        plotButton1.addActionListener(this.controller);
        submitB.addActionListener(this.controller);
      }

     public void show(){
        mainWindow.pack();
        mainWindow.setVisible(true);
     }

     public void refresh(){
         
     }
}

    

