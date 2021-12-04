package GraphicInterface.GICity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;

public class CityController implements ActionListener {

    private CityModel model;
    private View view;

       
    
    public CityController(CityModel model,View v){
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        
        String s = e.getActionCommand();

        

        if(s == "Check production for 1 day"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.displayCity();
            model.checkCityProd(model.number_of_days);
        }

        else if(s == "Display day"){
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.displayCSVDay(model.number_of_days);
        }

        else if(s == "Display year"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.displayCSVYear();
        }

        else if(s == "Plot day"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.plotCityDay();
        }

        else if(s == "Plot year"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_days = Integer.parseInt(view.textField3.getText());
            model.plotCityYear();
        }
        else{
            System.out.println("I dont know this command");
        }
    }
    
}
