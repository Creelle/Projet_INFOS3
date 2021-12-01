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

        //initialisation des variables
		FileReader in = new FileReader(filename);
		BufferedReader bin = new BufferedReader(in);
        String lineselector;
		Network network = new Network();
        City mycity = new City();
        int number =0;
        ArrayList<Device> listDevices = new ArrayList<Device>(0);
        int nbDevice = 0;        
        Device consdevice = new ConstantDevice("mydevice",0,"const");
        Device perdevice = new PeriodicDevice("mydevice",0,"periodic",0,0,0,0);
        Device model1 = new Model1("model1",0,0,0);
        Device model2 = new Model2(0,"model2",0,0,0,0);
        Device model3 = new Model3(0,0,"model2",0,0,0);
        Device model4 = new Model4(0,0,"model2",0,0,0,0);        
        int nbSys = 0;
        ArrayList<ProductionSystem> listSys = new ArrayList<ProductionSystem>(0);
        ProductionSystem constantsys = new ConstantSystem("constsys",0,"const");
        ProductionSystem periodicsys = new PeriodicSystem("periodicsys",0,"periodic",0,0,0,0);
        ProductionSystem eolian = new Eolian(0,0,0);
        ProductionSystem dam = new Hydroeletric(0,0);
        ProductionSystem fossil = new Fossil(0,0);
        ProductionSystem nuclear = new Nuclear(0,0,0,0);
        ProductionSystem solar = new Solar(0,0,0);
        Link mylink = new Link();
        double mylenght = 0;
        

		while(bin.ready()) {
			String line = bin.readLine();
			String[] tokens = line.split(";");
            lineselector=tokens[0].trim();

            switch(lineselector){
                case "citystart":
                    //DEGUG:System.out.println("citystart");
                    number++;
                    mycity = new City(0, false, Double.parseDouble(tokens[1].trim()), Double.parseDouble(tokens[2].trim()), number);
                    network.getListCities().add(mycity);
                break;
                
                case "homestart":
                    //DEGUG:System.out.println("homestrat");
                    listDevices = new ArrayList<Device>(0);
                    nbDevice = 0;
                break;

                case "constantdevice":
                    //DEGUG:System.out.println("consdev");
                    consdevice = new ConstantDevice("mydevice",Double.parseDouble(tokens[1].trim()),"const");
                    listDevices.add(consdevice);
                    nbDevice++;
                    
                break;

                case "periodicdevice":
                    //DEGUG:System.out.println("perdiodicdev");
                    perdevice = new PeriodicDevice("mydevice",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listDevices.add(perdevice);
                    nbDevice++;
                break;

                case "model1":
                    model1 = new Model1("model1",Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()),Double.parseDouble(tokens[3].trim()));
                    listDevices.add(model1);
                    nbDevice++;
                break;

                case "model2":
                    model2 = new Model2(Double.parseDouble(tokens[1].trim()),"model2",Double.parseDouble(tokens[2].trim()),Double.parseDouble(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listDevices.add(model2);
                    nbDevice++;
                break;

                case "model3":
                    model3 = new Model3(Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()),"model3",Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Double.parseDouble(tokens[3].trim()));
                    listDevices.add(model3);
                    nbDevice++;
                break;

                case "model4":
                    model4 = new Model4(Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()),"model4",Double.parseDouble(tokens[3].trim()),Double.parseDouble(tokens[4].trim()),Integer.parseInt(tokens[5].trim()),Double.parseDouble(tokens[3].trim()));
                    listDevices.add(model4);
                    nbDevice++;
                break;

                case "homeend":
                    //DEGUG:System.out.println("homeend");
                    mycity.setNbHouses(mycity.getNbHouses()+1);
                    mycity.getCityCons().getListDelivery().add(new DeliveryPoint("foyer",nbDevice,listDevices));
                break;

                case "powerplantstart":
                    //DEGUG:System.out.println("pps");
                    listSys = new ArrayList<ProductionSystem>(0);
                    nbSys = 0;
                    mycity.setProducer(true);
                break;
                
                case "constantsystem":
                    //DEGUG: System.out.println("cs");
                    constantsys = new ConstantSystem("constsys",Double.parseDouble(tokens[1].trim()),"const");
                    listSys.add(constantsys);
                    nbSys++;
                break;

                case "periodicsystem":
                    //DEGUG:System.out.println("ps");
                    periodicsys = new PeriodicSystem("periodicsys",Double.parseDouble(tokens[1].trim()),"periodic",Integer.parseInt(tokens[2].trim()),Integer.parseInt(tokens[3].trim()),Integer.parseInt(tokens[4].trim()),Integer.parseInt(tokens[5].trim()));
                    listSys.add(periodicsys);
                    nbSys++;
                break;

                case "eolian":
                    eolian = new Eolian(Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()),Double.parseDouble(tokens[3].trim()));
                    listSys.add(eolian);
                    nbSys++;
                break;

                case "fossil":
                    fossil = new Fossil(Double.parseDouble(tokens[1].trim()),Integer.parseInt(tokens[2].trim()));
                    listSys.add(fossil);
                    nbSys++;
                break;

                case "hydroeletric":
                    dam = new Hydroeletric(Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()));
                    listSys.add(dam);
                    nbSys++;
                break;

                case "nuclear":
                    nuclear = new Nuclear(Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()),Double.parseDouble(tokens[3].trim()),Integer.parseInt(tokens[4].trim()));
                    listSys.add(nuclear);
                    nbSys++;
                break;

                case "solar":
                    solar = new Solar(Double.parseDouble(tokens[1].trim()),Double.parseDouble(tokens[2].trim()),Double.parseDouble(tokens[3].trim()));
                    listSys.add(solar);
                    nbSys++;
                break;

                case "powerplantend":
                    //DEGUG:System.out.println("ppe");
                    mycity.getCityProd().getListInj().add(new InjectionPoint("powerplant",nbSys,listSys));
                break;

                case "cityend":
                    //DEGUG:System.out.println("ce");
                    network.getListCities().add(mycity);
                break;

                case "link":
                    //DEGUG:System.out.println("l");
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
