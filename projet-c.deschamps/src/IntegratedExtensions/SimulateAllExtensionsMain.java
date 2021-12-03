package IntegratedExtensions;

import java.io.IOException;

import Network.*;
import CSVbuilder.*;
public class SimulateAllExtensionsMain {
    public static void main(String[] args) throws IOException {
        Network network = Reader.read("data/meganetwork.csv");
        System.out.println(network.getCityInList(3, network.getListCities()).getCityProd().getListInj().get(0).getListSys().get(0).getName());
        System.out.println(network.getCityInList(1, network.getListCities()).getCityProd().getListInj().get(0).getListSys().get(0).getName());
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);        
        network.simulateOnAYear();
    }
}
