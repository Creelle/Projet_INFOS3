package GraphicInterface.GINetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import GraphicInterface.View;

public class NetController implements ActionListener {

    private NetModel model;
    private View view;
    
    
    public NetController(NetModel model,View v){
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();

        if(s== "Number of cities"){
            model.number_of_days = Integer.parseInt(view.textField4.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.ChangeCities();
        }

        

        else if(s == "nB Houses in the city at the index"){
            model.nbHouses = Integer.parseInt(view.CityNbHousesF.getText());
        }

       

        else if(s == "Change nBHouses of the index city"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.ChangeCities();
            model.ChangePop(model.index, model.nbHouses);
        }

        else if(s == "Display the list of cities"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.displayListCities();

        }

        else if(s == "Display the links between the cities"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.displayListLinks();

        }

        else if(s == "Plot the graph of the network"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.plotGraphNetworks();

        }

        else if(s == "Plot the index city"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.number_of_days = Integer.parseInt(view.textField4.getText());
            model.plotSimulationOfCity(model.index, model.number_of_days);

        }

        else if(s == "Plot the consumption / production of the network"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.number_of_days = Integer.parseInt(view.textField4.getText());
            model.plotNet();

        }

        else if(s == "CSV year"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.CSVNetworkYear();
        }

        else if(s =="CSV index city year"){
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.CSVCityYear();
        }

        else if(s == "CSV read"){
            model.read(view.CSVreadF.getText());

        }

        
        else{
            System.out.println("I dont know this command");
        }
    
    }
    
}