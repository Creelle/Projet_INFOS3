package CSVbuilder;
import java.io.IOException;
import Network.*;

public class ReaderMain {
    
    public static void main(String[] args) throws IOException {
        Network network = Reader.read("data/TO_LOAD/meganetwork.csv");
        network.displayListCities();
        network.displayListLinks();
        network.plotGraphNetwork();
        network.simulation(1, true);        
        //network.simulateOnAYear();
    }
}
