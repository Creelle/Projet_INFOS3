package IntegratedExtensions;

import java.io.IOException;

import Network.*;
import CSVbuilder.*;
import GraphicInterface.*;
import GraphicInterface.GICity.*;
import GraphicInterface.GICons.*;
import GraphicInterface.GINetwork.*;
import GraphicInterface.GIProd.*;


public class SimulateAllExtensionsMain {
    public static void main(String[] args) throws IOException {

        // Network and CSVBuilder part
        Network network = Reader.read("data/TO_LOAD/meganetwork.csv");
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);        
        network.simulateOnAYear();

        // Additional models and graphic interface
        ConsModel model_cons = new ConsModel();
        ProdModel model_prod = new ProdModel();
        CityModel model_city = new CityModel();
        NetModel model_net = new NetModel();

        View view = new View();

        Controller controller = new Controller();
        ConsController cons_controller = new ConsController(model_cons,view);
        ProdController prod_controller = new ProdController(model_prod,view);
        CityController city_controller = new CityController(model_city,view);
        NetController net_controller = new NetController(model_net,view);
        
        view.setController(controller,cons_controller,prod_controller,city_controller,net_controller);
        
        view.show();

    }
}
