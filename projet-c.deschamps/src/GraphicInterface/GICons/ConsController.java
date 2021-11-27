package GraphicInterface.GICons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;


public class ConsController implements ActionListener{

    private ConsModel model;
    private View view;
    
    public ConsController(ConsModel model,View v){
        this.model = model;
        this.view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e){
       
        String s = e.getActionCommand();
        if(s=="Number of days"){
            
            model.number_of_days = Integer.parseInt(view.textField.getText());

            //view.textField.setText("   ");
       
        }

        else if(s=="Number of constant devices"){
            model.number_of_constant_device = Integer.parseInt(view.nConstantF.getText());
        }

        else if(s=="Number of periodic devices"){
            model.number_of_periodic_device = Integer.parseInt(view.nPeriodicF.getText());
        }
       
        
        else if(s=="Plot button"){
            PlotActivation();

        }

        
    }

    public void PlotActivation(){
        model.plotCons();
    }

    
}
