package GraphicInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    /**
     * This controls the exit buttons from the 4 panels
     * The controllers for Consumption, production, city and network
     * are seperately defined
     */

    @Override
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }
    
}
