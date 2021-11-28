package CSVbuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Network.Network;
import City.City;
import Production.*;
import Consumption.*;

/**Porvides a method to built a city from a CSV file
 * @author m.forte
 */

public class Reader {

/**
	 * Reads persons from a text file. The file must be in the CSV format:
	 * <ul>
	 * <li> One object per line
	 * <li> Each line must be in the format: 
	 * <pre>
	 * citystart;int nbHouses; 1 if producer/0 if not; double x0; double y0
     * homestart
     * constantdevice;515;6984;2295
     * homeend
     * cityend
	 * </pre>
	 * </ul>
	 * 
	 * Here is an example file of CSV file content:
	 * 
	 * <pre>
	 * Jean Dupont,33
	 * Pierre Martin,55
	 * </pre>
 	 * 
	 * @param filename Path of the file to read, e.g. "data/persons.txt"
	 * @return A list of object in the text file
	 * @throws IOException when the file cannot be read or has a bad format.
	 */


    static public Network read(String filename) throws IOException {
		FileReader in = new FileReader(filename);
		BufferedReader bin = new BufferedReader(in);
        String lineselector;
		Network network = new Network();
        ConstantDevice consdevice = new ConstantDevice("mydevice",0,"const");
        PeriodicDevice perdevice = new PeriodicDevice("mydevice",0,"periodic",0,0,0,0);
        City mycity = new City();
        ArrayList<Device> listDevices = new ArrayList<Device>(0);
        int nbDevice = 0;
        
		while(bin.ready()) {
			String line = bin.readLine();
			String[] tokens = line.split(";");
            lineselector=tokens[0].trim();

            switch(lineselector){
                case "citystart":
                    
                    Boolean producer=false;
                    if (Integer.parseInt(tokens[2].trim())==1){
                        producer=true;
                    }
                    else{
                        producer=false;
                    }

                    mycity = new City(Integer.parseInt(tokens[1].trim()), producer, Double.parseDouble(tokens[3].trim()), Double.parseDouble(tokens[4].trim()), 0);
                    network.getListCities().add(mycity);
                break;
                
                case "homestart":
                    listDevices = new ArrayList<Device>(0);
                    nbDevice = 0;
                break;

                case "constantdevice":
                    consdevice = new ConstantDevice("mydevice",Double.parseDouble(tokens[1].trim()),"const");
                    listDevices.add(consdevice);
                    
                break;

                case "periodicdevice":
                    perdevice = new PeriodicDevice("mydevice",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listDevices.add(perdevice);

                case "homeend":
                    mycity.getCityCons().getListDelivery().add(new DeliveryPoint("foyer",nbDevice,listDevices));
                break;

                case "cityend":
                   network.getListCities().add(mycity);
                break;

                    
            }
			
		}
		bin.close();
		
		return network;
	}
    
}
