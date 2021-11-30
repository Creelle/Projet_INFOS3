package CSVbuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Network.Network;
import City.City;
import Production.*;
import Consumption.*;

/**Porvides a method to built a network from a CSV file
 * @author m.forte
 */

public class Reader {

/**
	 * Reads persons from a text file. The file must be in the CSV format:
	 * <ul>
	 * <li> One object per line
	 * <li> Each line must be in the format: 
	 * <pre>
	 * citystart;int nbHouses; double x0; double y0
     * homestart
     * constantdevice;double power
     * periodicdevice;double power,int period, int duration,int timestart,int timeend
     * homeend
     * 
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
        int nbSys = 0;
        ArrayList<ProductionSystem> listSys = new ArrayList<ProductionSystem>(0);
        ProductionSystem constantsys = new ConstantSystem("constsys",0,"const");
        ProductionSystem periodicsys = new PeriodicSystem("periodicsys",0,"periodic",0,0,0,0);
        
		while(bin.ready()) {
			String line = bin.readLine();
			String[] tokens = line.split(";");
            lineselector=tokens[0].trim();

            switch(lineselector){
                case "citystart":
                    mycity = new City(Integer.parseInt(tokens[1].trim()), false, Double.parseDouble(tokens[2].trim()), Double.parseDouble(tokens[3].trim()), 0);
                    network.getListCities().add(mycity);
                break;
                
                case "homestart":
                    listDevices = new ArrayList<Device>(0);
                    nbDevice = 0;
                break;

                case "constantdevice":
                    consdevice = new ConstantDevice("mydevice",Double.parseDouble(tokens[1].trim()),"const");
                    listDevices.add(consdevice);
                    nbDevice++;
                    
                break;

                case "periodicdevice":
                    perdevice = new PeriodicDevice("mydevice",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listDevices.add(perdevice);
                    nbDevice++;

                case "homeend":
                    mycity.getCityCons().getListDelivery().add(new DeliveryPoint("foyer",nbDevice,listDevices));
                break;

                case "powerplantstart":
                    listSys = new ArrayList<ProductionSystem>(0);
                    nbSys = 0;
                    mycity.setProducer(true);
                break;
                
                case "constantsystem":
                    constantsys = new ConstantSystem("constsys",Double.parseDouble(tokens[1].trim()),"const");
                    listSys.add(constantsys);
                    nbSys++;
                break;

                case "periodicsystem":
                    periodicsys = new PeriodicSystem("periodicsys",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listSys.add(periodicsys);
                    nbSys++;
                break;


                case "powerplantend":
                    mycity.getCityProd().getListInj().add(new InjectionPoint("powerplant",nbSys,listSys));
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
