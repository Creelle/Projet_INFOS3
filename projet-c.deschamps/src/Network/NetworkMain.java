package Network;

public class NetworkMain {
    public static void main(String[] args) {
        Network network = new Network(20);
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
    }
}
