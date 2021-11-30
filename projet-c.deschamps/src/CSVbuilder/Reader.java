package CSVbuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Network.*;
import City.*;
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
	 * citystart; double x0; double y0
     * homestart
     * constantdevice; double power
     * periodicdevice; double power; int period; int duration; int timestart; int timeend
     * homeend
     * powerplantstart
     * constantsystem; double power
     * periodicsystem; double power; int period; int duration; int timestart; int timeend
     * powerplantend
     * cityend
     * link, int numberCity1, int numberCity2, double lineicLoss
	 * </pre>
	 * </ul>
	 * 
	 * Here is an example file of CSV file content:
	 * 
	 * <pre>
	 * citystart;0;0
     * 
     * homestart
     * constantdevice; 2000
     * constantdevice; 500
     * periodicdevice; 2000; 10; 500; 0; 1440
     * homeend
     * 
     * homestart
     * constantdevice; 3000
     * constantdevice; 100
     * periodicdevice; 2000; 20; 1000; 0; 1440
     * homeend
     * 
     * powerplantstart
     * constantsystem;1000000
     * powerplantend
     * 
     * cityend
     * 
     * citystart;2;2
     * 
     * homestart
     * constantdevice; 2000
     * constantdevice; 500
     * periodicdevice; 2000; 10; 500; 0; 1440
     * homeend
     * 
     * homestart
     * constantdevice; 3000
     * constantdevice;100
     * periodicdevice;2000;20;1000;0;1440
     * homeend
     * 
     * cityend
     * 
     * link;1;2;10
     * 
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
        int number =0;
        ArrayList<Device> listDevices = new ArrayList<Device>(0);
        int nbDevice = 0;
        int nbSys = 0;
        ArrayList<ProductionSystem> listSys = new ArrayList<ProductionSystem>(0);
        ProductionSystem constantsys = new ConstantSystem("constsys",0,"const");
        ProductionSystem periodicsys = new PeriodicSystem("periodicsys",0,"periodic",0,0,0,0);
        //ArrayList<Link> listLinks = new ArrayList<>();
        Link mylink = new Link();
        double mylenght = 0;
        
		while(bin.ready()) {
			String line = bin.readLine();
			String[] tokens = line.split(";");
            lineselector=tokens[0].trim();

            switch(lineselector){
                case "citystart":
                    System.out.println("citystart");
                    number++;
                    mycity = new City(0, false, Double.parseDouble(tokens[1].trim()), Double.parseDouble(tokens[2].trim()), number);
                    network.getListCities().add(mycity);
                break;
                
                case "homestart":
                    System.out.println("homestrat");
                    listDevices = new ArrayList<Device>(0);
                    nbDevice = 0;
                break;

                case "constantdevice":
                System.out.println("consdev");
                    consdevice = new ConstantDevice("mydevice",Double.parseDouble(tokens[1].trim()),"const");
                    listDevices.add(consdevice);
                    nbDevice++;
                    
                break;

                case "periodicdevice":
                    System.out.println("perdiodicdev");
                    perdevice = new PeriodicDevice("mydevice",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listDevices.add(perdevice);
                    nbDevice++;
                break;

                case "homeend":
                    System.out.println("homeend");
                    mycity.setNbHouses(mycity.getNbHouses()+1);
                    mycity.getCityCons().getListDelivery().add(new DeliveryPoint("foyer",nbDevice,listDevices));
                break;

                case "powerplantstart":
                    System.out.println("pps");
                    listSys = new ArrayList<ProductionSystem>(0);
                    nbSys = 0;
                    mycity.setProducer(true);
                break;
                
                case "constantsystem":
                    System.out.println("cs");
                    constantsys = new ConstantSystem("constsys",Double.parseDouble(tokens[1].trim()),"const");
                    listSys.add(constantsys);
                    nbSys++;
                break;

                case "periodicsystem":
                    System.out.println("ps");
                    periodicsys = new PeriodicSystem("periodicsys",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listSys.add(periodicsys);
                    nbSys++;
                break;


                case "powerplantend":
                    System.out.println("ppe");
                    mycity.getCityProd().getListInj().add(new InjectionPoint("powerplant",nbSys,listSys));
                break;

                case "cityend":
                    System.out.println("ce");
                    network.getListCities().add(mycity);
                break;

                case "link":
                    System.out.println("l");
                    City cityToLink1 = network.getCityInList(Integer.parseInt(tokens[1].trim()), network.getListCities());
                    City cityToLink2 = network.getCityInList(Integer.parseInt(tokens[2].trim()), network.getListCities());
                    mylenght = network.calculateLength(cityToLink1,cityToLink2);
                    mylink = new Link(mylenght,Integer.parseInt(tokens[1].trim()),Integer.parseInt(tokens[2].trim()),Double.parseDouble(tokens[3].trim()));
                    network.getListLinks().add(mylink);
                break;

                default:
                break;  
            }
			
		}
		bin.close();
		
		return network;
	}
    
}
