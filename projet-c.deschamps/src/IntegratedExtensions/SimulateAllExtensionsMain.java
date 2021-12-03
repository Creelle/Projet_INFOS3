package IntegratedExtensions;

import java.io.IOException;

import Network.*;
public class SimulateAllExtensionsMain {
    public static void main(String[] args) throws IOException {
        Network network = new Network(6);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);
    }
}
