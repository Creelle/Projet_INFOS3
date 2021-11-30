package Network;

import java.io.IOException;


public class NetworkMain {
    public static void main(String[] args) throws IOException {
        Network network = new Network(6);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);        
        network.simulateOnAYear();

    }
}
