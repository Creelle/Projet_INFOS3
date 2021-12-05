package GraphicInterface.GICity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;

public class CityController implements ActionListener {

    private CityModel model;
    private View view;

       
    
    public CityController(CityModel model,View v){
        /**
         * Associates a view v and a city model to the controller
         */
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        /**
         * Sets what happens when a city button is actionned
         */
        
        String s = e.getActionCommand();

    
        if(s == "Check production for 1 day"){
            /**
             * Chequ if the consumption doesn't exceed production each minute of the day
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.displayCity();
            model.checkCityProd(model.number_of_days);
        }

        else if(s == "Display day"){
            /**
             * Displays in a CSV file the values for one day
             */
            
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.displayCSVDay(model.number_of_days);
        }

        else if(s == "Display year"){
            /**
             * Displays in a CSV file the values for one year or several days
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.displayCSVYear();
        }

        else if(s == "Plot day"){
            /**
             * Plot consumption vs production of a city in one day
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.plotCityDay();
        }

        else if(s == "Plot year"){
            /**
             * Plot consumption vs production of a city in one year
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.plotCityYear();
        }
        else{
            System.out.println("I dont know this command");
        }
    }
    
}
