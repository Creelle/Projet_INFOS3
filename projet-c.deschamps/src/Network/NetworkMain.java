package Network;

public class NetworkMain {
    public static void main(String[] args) {
        Network network = new Network(6);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.shortestPath(1, 6);
        //network.simulation(1);
    }
}
