package Network;

import java.util.ArrayList;

public class Path {

    //Attributs
    ArrayList<Integer> listNumberCities;
    double lenPath;

    //Constructeur primaire
    public Path(){
        listNumberCities = new ArrayList<>();
        lenPath = 0;
    }

    //Constructeur explicite
    public Path(ArrayList<Integer> listNumberCities, double lenPath){
        this.listNumberCities = listNumberCities;
        this.lenPath = lenPath;
    }

    //Setters and Getters

    //Méthodes d'affichage
    public void displayPath(){
        System.out.print("[");
        for(int num : listNumberCities){
                System.out.print(num + " ");
            }
            System.out.println("]");
        System.out.println("Length = "+lenPath);
    }
    
}