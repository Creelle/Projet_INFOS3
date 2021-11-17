package Network;

public class NetworkMain {
    public static void main(String[] args) {
        Network network = new Network(10);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        Path shortPath = network.shortestPath(1, 6);
        shortPath.displayPath();
        //network.simulation(1);
    }
}
