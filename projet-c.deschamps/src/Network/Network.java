package Network;

import City.City;
import Production.Production;
import Consumption.Consumption;

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

    /**
     * 
     * @param startCityNum
     * @param endCityNum
     * @param listLinks
     * @return le lien désiré
     */
    public Link getLinkInList(int startCityNum, int endCityNum, ArrayList<Link> listLinks){
        for(Link link : listLinks){
            if(link.getStart()==startCityNum && link.getEnd() == endCityNum){
                return link;
            }
        }
        System.out.println("Link not in the list !");
        Link link = new Link();
        return link;   
    }

    /**
     * Le lien existe-t-il déjà dans la liste?
     * @param num1
     * @param num2
     * @param listLinks
     * @return oui ou non
     */
    public boolean isLinkInList(int num1, int num2, ArrayList<Link> listLinks){
        for(Link link : listLinks){
            if(link.getStart()==num1 && link.getEnd()==num2){
                return true;
            }
        }
        return false;
    }

    /**
     * La liste a-t-elle une ville productrice?
     * @param listCities
     * @return oui ou non
     */
    public boolean isProducerInList(ArrayList<City> listCities){
        for(City city : listCities){
            if(city.getProducer()==true){
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
            if(Math.random()<1.0/5.0){
                producer = true;
            }
            City city = new City(nbHouses, producer, x, y, k);
            listCities.add(city);
        }
        //Pour ne pas avoir de réseau sans producteur : je change la première ville 
            // en une ville productrice
        if(isProducerInList(listCities)==false){
            City city = listCities.get(0);
            City newCity = new City(city.getNbHouses(), true, city.getX(), city.getY(), city.getNumber());
            listCities.remove(0);
            listCities.add(0, newCity);
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

    /**
     * Affichage graphique du réseau
     */
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

    /**
     * Renvoie la première ville productrice du réseau
     * @param listCities
     * @return la première ville productrice
     */
    public City firstProducer(ArrayList<City> listCities){
        for(City city : listCities){
            if(city.getProducer()==true){
                return city;
            }
        }
        //Simple sécurité mais n'arrive jamais
        System.out.println("No producer in the network!");
        City cityEmpty = new City();
        return cityEmpty;
    }

    /**
     * 
     * @param number
     * @param listTableProd
     * @param listTableCons
     * @return oui ou non suivant si la ville a besoin d'énergie ou non
     */
    public boolean needPower(int number, ArrayList<double[]> listTableProd, ArrayList<double[]> listTableCons){
        double[] prod = listTableProd.get(number-1);
        double[] cons = listTableCons.get(number-1);
        for(int k=0; k>1440; k++){
            if(cons[k]-prod[k]<0){
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param city
     * @param listTableProd
     * @param listTableCons
     * @return le Ratio de puissance demandée par chaque ville 
     */
    public double detRatio(City city, ArrayList<double[]> listTableProd, ArrayList<double[]> listTableCons){
        int counter = 0;
        int number = city.getNumber();
        //Parcours des liens pour une ville de départ donnée
        for(Link link : listLinks){
            if(link.getStart()==number){
                //Si la ville liée à besoin d'énergie, ajoute 1 au compteur
                if(needPower(link.getEnd(), listTableProd, listTableCons)==true){
                    counter+=1;
                }
            }
        }
        if(counter!=0){
            return 1/counter;
        }
        return 1.0;
    }

    /**
     * Déterminer la liste des voisins
     * @param city
     * @param network
     * @return la liste des villes voisines
     */
    public ArrayList<City> getNeighbors(City city){
        ArrayList<City> listNeighbors = new ArrayList<>();
        int numCity = city.getNumber();
        for(Link link : listLinks){
            if(link.getStart() == numCity){
                City cityToAdd = getCityInList(link.getEnd(), listCities);
                listNeighbors.add(cityToAdd);
            }
        }
        return listNeighbors;
    }

    /**
     * 
     * @param totalLength tableau des distances plus courtes distances entre une ville de départ
     *  et les villes du tableau.
     * @param listCities liste des villes du tableau
     * @return laville ayant la plus courte distance par rapport au point de départ.
     */
    public City cityWithMinTotLen(double[] totalLength, ArrayList<City> listCities){
        double minLen = Double.MAX_VALUE;
        int numCityWithMinTotLen = 0;
        for(City city : listCities){
            if(totalLength[city.getNumber()-1]<minLen){
                numCityWithMinTotLen = city.getNumber();
            }
        }
        //Test la valeur minLen a bien changé
        if(minLen == Double.MAX_VALUE){
            City cityToReturn = listCities.get(0);
            return cityToReturn;
        }
        City cityToReturn = getCityInList(numCityWithMinTotLen, listCities);
        return cityToReturn;
    }

    /**
     *  Algorithme du plus court chemin
     * @param start
     * @param end
     * @return la liste des numéros des villes à suivre
     */
    public ArrayList<Integer> shortestPath(int start, int end){
        //Déclaration des variables
        ArrayList<Integer> listToFollow = new ArrayList<>();
        int[] pred = new int[listCities.size()];
        double[] totalLength = new double[listCities.size()];

        //Création de la liste intermédiaire
        ArrayList<City> intermediateListCities = new ArrayList<>();
        for(City city : listCities){
            intermediateListCities.add(city);
            totalLength[city.getNumber()-1] = Double.MAX_VALUE; 
        }

        //Initialisation
        City cityStart = getCityInList(start, listCities);
        totalLength[cityStart.getNumber()-1] = 0.0;

        //Boucle
        while(intermediateListCities.isEmpty() == false){
            City cityToTry = cityWithMinTotLen(totalLength, intermediateListCities) ;
            intermediateListCities.remove(cityToTry);
            ArrayList<City> listOfNeighbors = getNeighbors(cityToTry);
            for(City neighborCity : listOfNeighbors){
                Link link = getLinkInList(cityToTry.getNumber(), neighborCity.getNumber(), listLinks);
                double newLen = totalLength[cityToTry.getNumber()-1]+link.getLinkLength();
                if(newLen<totalLength[neighborCity.getNumber()-1]){
                    totalLength[neighborCity.getNumber()-1] = newLen;
                    pred[neighborCity.getNumber()-1] = cityToTry.getNumber();
                }
            }
        }
        //Retour de la liste des noeuds du chemin
        listToFollow.add(end);
        int number = end;
        while(number != start){
            listToFollow.add(0, pred[number-1]);
            number = pred[number-1];
        }
        System.out.print("[");
        for(int num : listToFollow){
            System.out.print(num + " ");
        }
        System.out.println("]");
        return listToFollow;
    }

    public void simulation(int j){

        //Création des listes de tableaux
        //ArrayList<City> listOrderedCities = new ArrayList<>();
        ArrayList<double[]> listTableProd = new ArrayList<>();
        ArrayList<double[]> listTableCons = new ArrayList<>();

        //Création des tableaux pour chaque ville de la liste et ajout aux listes
        for(City city : listCities){
            Production P = city.getCityProd();
            Consumption C = city.getCityCons();
            double[] prod = P.generate(j);
            double[] cons = C.generate(j);
            listTableProd.add(prod);
            listTableCons.add(cons);
        }
        
    }
}
