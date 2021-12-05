package GraphicInterface.GICons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;


public class ConsController implements ActionListener{

    private ConsModel model;
    private View view;
    
    public ConsController(ConsModel model,View v){
        /**
         * Associates a view v and a consumption model to the controller
         */
        this.model = model;
        this.view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e){
       
        String s = e.getActionCommand();
        if(s=="Number of days"){
            /**
             * Sets the number of days
             */
            
            model.number_of_days = Integer.parseInt(view.textField.getText());
        }

       
        else if(s=="Plot 1 day"){
            /**
             * Plot the consumption over 1 day
             */
            model.number_of_constant_device = Integer.parseInt(view.nConstantF.getText());
            model.number_of_periodic_device = Integer.parseInt(view.nPeriodicF.getText());
            model.number_model1 = Integer.parseInt(view.nModel1F.getText());
            model.number_model2 = Integer.parseInt(view.nModel2F.getText());
            model.number_model3 = Integer.parseInt(view.nModel3F.getText());
            model.number_model4 = Integer.parseInt(view.nModel4F.getText());
            model.number_of_days = Integer.parseInt(view.textField.getText());
            
            model.plotCons();

        }

        else if(s=="Plot year"){
            /**
             * Plot the consumption over the number_of_days
             */
            model.number_of_constant_device = Integer.parseInt(view.nConstantF.getText());
            model.number_of_periodic_device = Integer.parseInt(view.nPeriodicF.getText());
            model.number_model1 = Integer.parseInt(view.nModel1F.getText());
            model.number_model2 = Integer.parseInt(view.nModel2F.getText());
            model.number_model3 = Integer.parseInt(view.nModel3F.getText());
            model.number_model4 = Integer.parseInt(view.nModel4F.getText());
            model.number_of_days = Integer.parseInt(view.textField.getText());
            
            model.plotProdYear();

        }

        else if(s=="Display devices"){
            /**
             * Displays the list of devices
             */
            model.number_of_constant_device = Integer.parseInt(view.nConstantF.getText());
            model.number_of_periodic_device = Integer.parseInt(view.nPeriodicF.getText());
            model.number_model1 = Integer.parseInt(view.nModel1F.getText());
            model.number_model2 = Integer.parseInt(view.nModel2F.getText());
            model.number_model3 = Integer.parseInt(view.nModel3F.getText());
            model.number_model4 = Integer.parseInt(view.nModel4F.getText());
            model.number_of_days = Integer.parseInt(view.textField.getText());
            
            model.displaylistDP();

        }
        
    }  
}
