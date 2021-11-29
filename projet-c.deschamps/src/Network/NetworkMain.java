package Network;

import java.io.FileNotFoundException;

public class NetworkMain {
    public static void main(String[] args) throws FileNotFoundException {
        Network network = new Network(15);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);        
        //network.simulateOnAYear();
    }
}
