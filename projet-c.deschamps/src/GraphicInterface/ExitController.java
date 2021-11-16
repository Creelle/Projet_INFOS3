package GraphicInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExitController implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }
    
}
