package GraphicInterface;

import javax.swing.*;
import java.awt.*;

public class View {

    // components
    private JButton plotButton1;
    private JButton exitButton1;
    private JButton exitButton2;
    
    private JPanel pane1;
    private JPanel pane2;

    private JTabbedPane tabs;

    private JFrame mainWindow;

    public View(){
        
        //Buttons creation
        plotButton1 = new JButton("Press here to graph the consummer");
        exitButton1 = new JButton("EXIT");
        exitButton2 = new JButton("EXIT");
        ExitController exit_controller = new ExitController();
        exitButton1.addActionListener(exit_controller);
        exitButton2.addActionListener(exit_controller);
        
        // pane creator
        pane1 = new JPanel();
        pane1.setLayout(new GridLayout(2,2));
        
        pane2= new JPanel();
        pane2.setLayout(new GridLayout(2,2));

        //tabs creator
        tabs = new JTabbedPane();
        tabs.addTab("Consummers", pane1);
        tabs.addTab("Producers", pane2);
        tabs.setForegroundAt(1, Color.RED);
        tabs.setForegroundAt(0,Color.BLUE);

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

        //pane 2 filling
        pane2.add(exitButton2);
        
     }

     public void show(){
         mainWindow.pack();
         mainWindow.setVisible(true);
     }

     public void refresh(){
         
     }
}

    

