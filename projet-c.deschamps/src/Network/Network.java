package Network;

import City.City;
import java.util.ArrayList;
import ptolemy.plot.Plot;
import javax.swing.JFrame;

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

    //Constructeurs explicites
    public Network(int nbCities, ArrayList<City> listCities, ArrayList<Link> listLinks){
        this.nbCities = nbCities;
        this.listCities = listCities;
        this.listLinks = listLinks;
    }

    public Network(int nbCities){
        this.nbCities = nbCities;
        listCities = generateListCity();
        listLinks = generateListLinks(listCities);
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

    /**
     * Calcule la longueur du lien entre deux villes
     * 
     * @param city1
     * @param city2
     * @return la longueur du lien
     */
    public double calculateLength(City city1, City city2){
        double length = Math.sqrt(Math.pow(city1.getX()-city2.getX(),2) + Math.pow(city1.getY()-city2.getY(),2));
        length = Math.round(length*10.0)/10.0;
        return length;
    }

    /**
     * Renvoie la Ville demandée à partir de son numéro
     * @param numCity //Numéro de la ville cherchée
     * @param listCities //Liste de recherche
     * @return la ville demandée
     */
    public City getCityInList(int numCity, ArrayList<City> listCities){
        for(City city : listCities){
            if(city.getNumber()==numCity){
                return city;
            }
        }
        System.out.println("City not in the list !");
        City cityEmpty = new City();
        return cityEmpty;   
    }

    public boolean isLinkInList(int num1, int num2, ArrayList<Link> listLinks){
        for(Link link : listLinks){
            if(link.getStart()==num1 && link.getEnd()==num2){
                return true;
            }
        }
        return false;
    }

    /**
     * Crée la liste des villes et leur emplacement
     * @return la liste des villes
     */
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

    /**
     * Display de la liste des villes du réseau.
     */
    public void displayListCities(){
        for(City city : listCities){
            System.out.println("Number : "+city.getNumber()+
            ", Number of Houses : "+city.getNbHouses()+
            ", Number of Links : "+city.getNbLinks()+
            ", Coordinates (x,y) = ("
            +Math.round(city.getX()*10.0)/10.0+","+Math.round(city.getY()*10.0)/10.0+
            ") , Producer : "+ city.getProducer());;
        }
    }

    /**
     * Génère la liste des liens entre ville
     * @param listOfCities
     * @return
     */
    public ArrayList<Link> generateListLinks( ArrayList<City> listOfCities){
        ArrayList<Link> listLinks = new ArrayList<>();
        //Détermination longueur de la liste
        for(City city : listCities){
            int nbLinks = city.getNbLinks();
            for(int i=1; i<= nbLinks; i++){
                //Détermination des autres villes avec lesquelles elle est liée
                int numCity = (int) Math.round(Math.random()*(nbCities-1)+1);
                //Pas de lien avec soi-même ou pas de lien en doublon 
                //et pas de boucle infinie
                int counterLoop = 0;
                while((city.getNumber() == numCity || 
                        isLinkInList(numCity, city.getNumber(), listLinks) == true) 
                            && counterLoop<10){
                    numCity = (int) Math.round(Math.random()*(nbCities-1)+1);
                    counterLoop+=1;
                }
                City cityToLink = getCityInList(numCity, listOfCities);
                //Ne pas recréer de lien si la ville d'arrivée en a déjà le bon nombre
                // ou si la ville de départ en a déjà le bon nombre
                int counterLinksStart = 0;
                int counterLinksEnd = 0;
                for(Link link : listLinks){
                    if(link.getEnd() == numCity){
                        counterLinksEnd +=1;
                    }
                    if(link.getStart()==city.getNumber()){
                        counterLinksStart+=1;
                    }
                }
                if(counterLinksEnd>=cityToLink.getNbLinks() ||
                     counterLinksStart>= city.getNbLinks()){
                    //Ne rien faire!
                }
                else if(counterLoop==10){
                    //Ne rien faire !
                }else{
                    //Création des liens correspondants (aller-retour)
                    double length = calculateLength(city, cityToLink);
                    Link link1 = new Link(length , city.getNumber(), cityToLink.getNumber());
                    Link link2 = new Link(length , cityToLink.getNumber(), city.getNumber());
                    listLinks.add(link1);
                    listLinks.add(link2);
                }
                System.out.println("linkStart :" + counterLinksStart);
                System.out.println("linkEnd :" + counterLinksEnd);
                System.out.println("loop :" + counterLoop);
            }
        }
        return listLinks;
    }

    /** 
     * Affichage des liens de la liste
     */
    public void displayListLinks(){
        for(Link link : listLinks){
            System.out.println("Link between "+link.getStart()+" and "+link.getEnd()+", Length = "
            + link.getLinkLength());
        }
    }

    public void plotGraphNetwork(){
        Plot plot = new Plot();
        for(Link link : listLinks){
            City cityStart = getCityInList(link.getStart(), listCities);
            plot.addPoint(0, cityStart.getX(), cityStart.getY(), false);
            City cityEnd = getCityInList(link.getEnd(), listCities);
            plot.addPoint(0, cityEnd.getX(), cityEnd.getY(), true);
        }
        JFrame frame1 = new JFrame("Network");
        frame1.add(plot);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
