package Network;


public class NetworkMain {
    public static void main(String[] args) {
        Network network = new Network(5);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();        
        network.simulation(1);
    }
}
