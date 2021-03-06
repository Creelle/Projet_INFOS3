package GraphicInterface.GIProd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;

public class ProdController implements ActionListener {

    private ProdModel model;
    private View view;
    
    public ProdController(ProdModel model,View v){
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        /**
         * Associates a view v and a production model to the controller
         */

        String s = e.getActionCommand();
        if(s=="Day production plot"){
            /**
             * Plot the production over a day
             */
            model.number_of_solarpanels = Integer.parseInt(view.nsolarF.getText());
            model.number_of_windmills = Integer.parseInt(view.nwindF.getText());
            model.number_of_coal = Integer.parseInt(view.ncoalF.getText());
            model.number_of_hydraulic = Integer.parseInt(view.nhydraulicF.getText());
            model.number_of_nuclear = Integer.parseInt(view.nnuclearF.getText());
            model.plotProd();
        }

        else if(s=="Year production plot"){
            /**
             * Plot the production over a year
             */
            model.number_of_days = Integer.parseInt(view.textField2.getText());
            
            model.number_of_solarpanels = Integer.parseInt(view.nsolarF.getText());
            model.number_of_windmills = Integer.parseInt(view.nwindF.getText());
            model.number_of_coal = Integer.parseInt(view.ncoalF.getText());
            model.number_of_hydraulic = Integer.parseInt(view.nhydraulicF.getText());
            model.number_of_nuclear = Integer.parseInt(view.nnuclearF.getText());
            model.plotProdYear();
        }
        else if(s == "Number of days"){
            /**
             * Sets the number of days
             */
            model.number_of_days = Integer.parseInt(view.textField2.getText());
        }

    }
    
}

