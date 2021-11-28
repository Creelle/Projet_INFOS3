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
        
		while(bin.ready()) {
			String line = bin.readLine();
			String[] tokens = line.split(";");

            switch(lineselector){
                case "citystart":
                    
                    Boolean producer=false;
                    if (Integer.parseInt(tokens[2].trim())==1){
                        producer=true;
                    }
                    else{
                        producer=false;
                    }

                    City mycity = new City(Integer.parseInt(tokens[1].trim()), producer, Integer.parseInt(tokens[3].trim()), Integer.parseInt(tokens[4].trim()), 0);
                    
                    break;
                
                case "home":
                    
            }
			String name = tokens[0].trim();
			int age = Integer.parseInt(tokens[1].trim());
			persons.add(new Person(name, age));
		}
		bin.close();
		
		return persons;
	}
    
}
