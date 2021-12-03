package IntegratedExtensions;

import java.io.IOException;

import Network.*;
import CSVbuilder.*;
public class SimulateAllExtensionsMain {
    public static void main(String[] args) throws IOException {
        Network network = Reader.read("data/meganetwork.csv");
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);        
        network.simulateOnAYear();
    }
}
