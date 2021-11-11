package Network;
import City.City;
import java.util.ArrayList;



public class Network {
    
    // Attributs 
    private int nbCities;
    private ArrayList<City> listCities;
    private ArrayList<Link> listLinks;

    //Constructeur primaire
    public Network(){
        nbCities = 0;
        listCities = new ArrayList<>();
        listLinks = new ArrayList<>();
    }

    //Constructeur explicite
    public Network(int nbCities, ArrayList<City> listCities, ArrayList<Link> listLinks){
        this.nbCities = nbCities;
        this.listCities = listCities;
        this.listLinks = listLinks;
    }

    //Setters and Getters

    public int getNbCities() {
        return this.nbCities;
    }

    public void setNbCities(int nbCities) {
        this.nbCities = nbCities;
    }

    public ArrayList<City> getListCities() {
        return this.listCities;
    }

    public void setListCities(ArrayList<City> listCities) {
        this.listCities = listCities;
    }

    public ArrayList<Link> getListLinks() {
        return this.listLinks;
    }

    public void setListLinks(ArrayList<Link> listLinks) {
        this.listLinks = listLinks;
    }

    //méthodes

    public double calculateLength(City city1, City city2){
        double length = Math.sqrt(Math.pow(city1.getX()-city2.getX(),2) + Math.pow(city1.getY()-city2.getY(),2));
        length = Math.round(length*10.0)/10.0;
        return length;
    }

    public ArrayList<City> generateListCity(){
        ArrayList<City> listCities = new ArrayList<>();
        for(int k = 1; k<nbCities+1; k++){
            double x = 100*Math.random();
            double y = 100*Math.random();
            //Pas de nouvelle ville dans un cercle de rayon 2km autour d'une ville existante
            for(City city : listCities){
                while(Math.pow(x-city.getX(),2)+Math.pow(y-city.getY(),2) <= 4){
                    x = 100*Math.random();
                    y = 100*Math.random();   
                }
            }
            int nbHouses = (int) Math.round(Math.random()*2000);
            boolean producer = false;
            if(Math.random()<1.0/20.0){
                producer = true;
            }
            City city = new City(nbHouses, producer, x, y, k);
            listCities.add(city);
        }
        return listCities;
    }
}
