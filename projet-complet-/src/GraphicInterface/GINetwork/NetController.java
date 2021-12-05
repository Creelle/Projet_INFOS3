package GraphicInterface.GINetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import GraphicInterface.View;

public class NetController implements ActionListener {

    private NetModel model;
    private View view;
    
    
    public NetController(NetModel model,View v){
        /**
         * Associates a view v and a network model to the controller
         */
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        /**
         * Determines what happens when a button is pushed
         */
        String s = e.getActionCommand();

        if(s== "Number of cities"){

            /**
             * Sets the number of days
             */

            model.number_of_days = Integer.parseInt(view.textField4.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.ChangeCities();
        }

        

        else if(s == "nB Houses in the city at the index"){
            /**
             * Fixes the nbHouses for the city specified in the index
             */
            model.nbHouses = Integer.parseInt(view.CityNbHousesF.getText());
        }

       

        else if(s == "Change nBHouses of the index city"){
            /**
             * Changes the nbHouses in the city at the specified index
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.ChangeCities();
            model.ChangePop(model.index, model.nbHouses);
        }

        else if(s == "Display the list of cities"){
            /**
             * Displays the list of cities
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.displayListCities();

        }

        else if(s == "Display the links between the cities"){
            /**
             * Displays the links between the cities
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.displayListLinks();

        }

        else if(s == "Plot the graph of the network"){
            /**
             * Plot the links between cities
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.plotGraphNetworks();

        }

        else if(s == "Plot the index city"){
            /**
             * Plot the simulation on the city at the specified index
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.number_of_days = Integer.parseInt(view.textField4.getText());
            model.plotSimulationOfCity(model.index, model.number_of_days);

        }
        
        else if(s == "Plot the consumption / production of the network"){
             /**
             * Plot the simution of the whole network
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.number_of_days = Integer.parseInt(view.textField4.getText());
            model.plotNet();

        }

        else if(s == "CSV year"){
            /**
             * Gives a csv file with the results over a year
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.CSVNetworkYear();
        }

        else if(s =="CSV index city year"){
            /**
             * Gives a csv file with the results over a year for the city at specified index
             */
            model.nbHouses = Integer.parseInt(view.nbHousesF.getText());
            model.number_of_cities = Integer.parseInt(view.nCitiesF.getText());
            model.index = Integer.parseInt(view.CityIndexF.getText());
            model.CSVCityYear();
        }

        else if(s == "CSV read"){
            /**
             * Creates a network from a specified file 
             */
            model.read(view.CSVreadF.getText());

        }

        
        else{
            System.out.println("I dont know this command");
        }
    
    }
    
}