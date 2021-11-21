package Network;

import City.City;
import Consumption.Consumption;
import Production.Production;

import java.util.ArrayList;

import ptolemy.plot.Plot;
import javax.swing.JFrame;

public class Network {

    // Attributs
    private int nbCities;
    private ArrayList<City> listOfCities;
    private ArrayList<Link> listOfLinks;

    // Constructeur primaire
    public Network() {
        nbCities = 0;
        listOfCities = new ArrayList<>();
        listOfLinks = new ArrayList<>();
    }

    // Constructeurs explicites
    public Network(int nbCities, ArrayList<City> listCities, ArrayList<Link> listLinks) {
        this.nbCities = nbCities;
        this.listOfCities = listCities;
        this.listOfLinks = listLinks;
    }

    public Network(int nbCities) {
        this.nbCities = nbCities;
        listOfCities = generateListCity();
        listOfLinks = generateListLinks(listOfCities);
        while (checkConnectedNetwork(listOfCities, listOfLinks) == false) {
            listOfLinks = generateListLinks(listOfCities);
        }
    }

    // Setters and Getters

    public int getNbCities() {
        return this.nbCities;
    }

    public void setNbCities(int nbCities) {
        this.nbCities = nbCities;
    }

    public ArrayList<City> getListCities() {
        return this.listOfCities;
    }

    public void setListCities(ArrayList<City> listCities) {
        this.listOfCities = listCities;
    }

    public ArrayList<Link> getListLinks() {
        return this.listOfLinks;
    }

    public void setListLinks(ArrayList<Link> listLinks) {
        this.listOfLinks = listLinks;
    }

    //*************************************************************************\\

    // Méthodes

    // Getters dans une liste

    /**
     * Renvoie la Ville demandée à partir de son numéro
     * 
     * @param numCity    //Numéro de la ville cherchée
     * @param listCities //Liste de recherche
     * @return la ville demandée
     */
    public City getCityInList(int numCity, ArrayList<City> listCities) {
        for (City city : listCities) {
            if (city.getNumber() == numCity) {
                return city;
            }
        }
        // System.out.println("City not in the list !");
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
    public Link getLinkInList(int startCityNum, int endCityNum, ArrayList<Link> listLinks) {
        for (Link link : listLinks) {
            if (link.getStart() == startCityNum && link.getEnd() == endCityNum) {
                return link;
            }
        }
        System.out.println("Link not in the list !");
        Link link = new Link();
        return link;
    }

    /**
     * 
     * @return la liste des villes productrices d'énergie
     */
    public ArrayList<City> getProdCities() {
        ArrayList<City> listProdCities = new ArrayList<>();
        int k = 0;
        for (City city : listOfCities) {
            if (city.getProducer() == true) {
                listProdCities.add(k, city);
                k += 1;
            }
        }
        return listProdCities;
    }

    /**
     * 
     * @return la liste des villes non productrices d'énergie
     */
    public ArrayList<City> getNoProdCities() {
        ArrayList<City> listNoProdCities = new ArrayList<>();
        int k = 0;
        for (City city : listOfCities) {
            if (city.getProducer() == false) {
                listNoProdCities.add(k, city);
                k += 1;
            }
        }
        return listNoProdCities;
    }

    //***************************************************************************\\

    // Partie génération de liste de villes

    /**
     * Méthode auxiliaire La liste a-t-elle une ville productrice?
     * 
     * @param listCities
     * @return oui ou non
     */
    public boolean isProducerInList(ArrayList<City> listCities) {
        for (City city : listCities) {
            if (city.getProducer() == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Crée la liste des villes et leur emplacement
     * 
     * @return la liste des villes
     */
    public ArrayList<City> generateListCity() {
        ArrayList<City> listCities = new ArrayList<>();
        for (int k = 1; k < nbCities + 1; k++) {
            double x = 100 * Math.random();
            double y = 100 * Math.random();
            // Pas de nouvelle ville dans un cercle de rayon 2km autour d'une ville
            // existante
            for (City city : listCities) {
                while (Math.pow(x - city.getX(), 2) + Math.pow(y - city.getY(), 2) <= 4) {
                    x = 100 * Math.random();
                    y = 100 * Math.random();
                }
            }
            int nbHouses = (int) Math.round(Math.random() * 4000);
            boolean producer = false;
            if (Math.random() < 2.0 / 5.0) {
                producer = true;
            }
            City city = new City(nbHouses, producer, x, y, k);
            listCities.add(city);
        }
        // Pour ne pas avoir de réseau sans producteur : je change la première ville
        // en une ville productrice
        if (isProducerInList(listCities) == false) {
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
    public void displayListCities() {
        for (City city : listOfCities) {
            System.out.println("Number : " + city.getNumber() + ", Number of Houses : " + city.getNbHouses()
                    + ", Number of Links : " + city.getNbLinks() + ", Coordinates (x,y) = ("
                    + Math.round(city.getX() * 10.0) / 10.0 + "," + Math.round(city.getY() * 10.0) / 10.0
                    + ") , Producer : " + city.getProducer());
            ;
        }
    }

    // ************************************************************************************** \\

    // Partie génération de liste de liens

    /**
     * Méthode auxiliaire
     * 
     * @param city1
     * @param city2
     * @return la longueur du lien
     */
    public double calculateLength(City city1, City city2) {
        double length = Math.sqrt(Math.pow(city1.getX() - city2.getX(), 2) + Math.pow(city1.getY() - city2.getY(), 2));
        length = Math.round(length * 10.0) / 10.0;
        return length;
    }

    /**
     * Méthode auxiliaire Le lien existe-t-il déjà dans la liste?
     * 
     * @param num1
     * @param num2
     * @param listLinks
     * @return oui ou non
     */
    public boolean isLinkInList(int num1, int num2, ArrayList<Link> listLinks) {
        for (Link link : listLinks) {
            if (link.getStart() == num1 && link.getEnd() == num2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Génère la liste des liens entre ville
     * 
     * @param listOfCities
     * @return
     */
    public ArrayList<Link> generateListLinks(ArrayList<City> listOfCities) {
        ArrayList<Link> listLinks = new ArrayList<>();
        // Détermination longueur de la liste
        for (City city : listOfCities) {
            int nbLinks = city.getNbLinks();
            for (int i = 1; i <= nbLinks; i++) {
                // Détermination des autres villes avec lesquelles elle est liée
                int numCity = (int) Math.round(Math.random() * (nbCities - 1) + 1);
                // Pas de lien avec soi-même ou pas de lien en doublon
                // et pas de boucle infinie
                int counterLoop = 0;
                while ((city.getNumber() == numCity || isLinkInList(numCity, city.getNumber(), listLinks) == true)
                        && counterLoop < 10) {
                    numCity = (int) Math.round(Math.random() * (nbCities - 1) + 1);
                    counterLoop += 1;
                }
                City cityToLink = getCityInList(numCity, listOfCities);
                // Ne pas recréer de lien si la ville d'arrivée en a déjà le bon nombre
                // ou si la ville de départ en a déjà le bon nombre
                int counterLinksStart = 0;
                int counterLinksEnd = 0;
                for (Link link : listLinks) {
                    if (link.getEnd() == numCity) {
                        counterLinksEnd += 1;
                    }
                    if (link.getStart() == city.getNumber()) {
                        counterLinksStart += 1;
                    }
                }
                if (counterLinksEnd >= cityToLink.getNbLinks() || counterLinksStart >= city.getNbLinks()) {
                    // Ne rien faire!
                } else if (counterLoop == 10) {
                    // Ne rien faire !
                } else {
                    // Création des liens correspondants (aller-retour)
                    double length = calculateLength(city, cityToLink);
                    double lineicLoss = Math.round((Math.random() * 0.5 + 1.0) * 10.0) / 10.0;
                    Link link1 = new Link(length, city.getNumber(), cityToLink.getNumber(), lineicLoss);
                    Link link2 = new Link(length, cityToLink.getNumber(), city.getNumber(), lineicLoss);
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
    public void displayListLinks() {
        for (Link link : listOfLinks) {
            System.out.println("Link between " + link.getStart() + " and " + link.getEnd() + ", Length = "
                    + link.getLinkLength() + ", Lineic loss = " + link.getLineicLoss());
        }
    }

    // ***************************************************************************************** \\

    // Partie affichage graphique du réseau

    /**
     * Affichage graphique du réseau
     */
    public void plotGraphNetwork() {
        Plot plot = new Plot();
        for (Link link : listOfLinks) {
            City cityStart = getCityInList(link.getStart(), listOfCities);
            plot.addPoint(0, cityStart.getX(), cityStart.getY(), false);
            City cityEnd = getCityInList(link.getEnd(), listOfCities);
            plot.addPoint(0, cityEnd.getX(), cityEnd.getY(), true);
        }
        JFrame frame1 = new JFrame("Network");
        frame1.add(plot);
        frame1.pack();
        frame1.setVisible(true);
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // ************************************************************************************* \\

    // Partie Vérification graph connexe

    public int connectedTo(int numCity, ArrayList<Integer> leftOverCities, ArrayList<Integer> listInt,
            ArrayList<Link> listLinks) {
        for (Link link : listLinks) {
            if (link.getStart() == numCity && leftOverCities.contains(link.getEnd()) == false
                    && listInt.contains(link.getEnd()) == true) {
                return link.getEnd();
            }
        }
        return 0;
    }

    /**
     * Méthode de vérificatin de graphe connexe : existe - il un plus court chemin
     * entre la ville 1 et les autres?
     * 
     * @param listCities
     * @param listLinks
     * @return true/false suivant si le graphe est connexe ou non
     */
    public boolean checkConnectedNetwork(ArrayList<City> listCities, ArrayList<Link> listLinks) {
        boolean connected = false;
        int numStart = listCities.get(0).getNumber();
        for (City city : listCities) {
            int numCity = city.getNumber();
            if (city.getNumber() != numStart) {
                try {
                    shortestPath(numStart, numCity);
                } catch (IndexOutOfBoundsException error) {
                    System.out.println("Network not connected");
                    return connected;
                }
            }
        }
        System.out.println("Network connected");
        connected = true;
        return connected;
    }

    // ************************************************************************************* \\


    // Partie algorithme du plus court chemin

    /**
     * Déterminer la liste des voisins
     * 
     * @param city
     * @param network
     * @return la liste des villes voisines
     */
    public ArrayList<City> getNeighbors(City city) {
        ArrayList<City> listNeighbors = new ArrayList<>();
        int numCity = city.getNumber();
        for (Link link : listOfLinks) {
            if (link.getStart() == numCity) {
                City cityToAdd = getCityInList(link.getEnd(), listOfCities);
                // test la ville ne fait pas partie des villes exclues
                if (city.getNumber() != 0) {
                    listNeighbors.add(cityToAdd);
                }
            }
        }
        return listNeighbors;
    }

    /**
     * 
     * @param totalLength tableau des distances plus courtes distances entre une
     *                    ville de départ et les villes du tableau.
     * @param listCities  liste des villes du tableau
     * @return la ville ayant la plus courte distance par rapport au point de
     *         départ.
     */
    public City cityWithMinTotLen(double[] totalLength, ArrayList<City> listCities) {
        double minLen = Double.MAX_VALUE;
        int numCityWithMinTotLen = 1;
        for (City city : listCities) {
            if (totalLength[city.getNumber() - 1] < minLen) {
                numCityWithMinTotLen = city.getNumber();
                minLen = totalLength[city.getNumber() - 1];
            }
        }
        // Simple sécurité mais n'arrive jamais
        if (minLen == Double.MAX_VALUE) {
            City cityToReturn = listCities.get(0);
            System.out.println("no change");
            return cityToReturn;
        }
        City cityToReturn = getCityInList(numCityWithMinTotLen, listCities);
        return cityToReturn;
    }

    /**
     * Algorithme du plus court chemin
     * 
     * @param start
     * @param end
     * @return la liste des numéros des villes à suivre
     */
    public Path shortestPath(int start, int end) {
        // Déclaration des variables
        ArrayList<Integer> listToFollow = new ArrayList<>();
        int[] pred = new int[listOfCities.size()];
        double[] totalLength = new double[listOfCities.size()];

        // Création de la liste intermédiaire : elle est constituée de toutes les villes 
        // que l'on peut encore aller visiter
        ArrayList<City> intermediateListCities = new ArrayList<>();
        for (City city : listOfCities) {
            intermediateListCities.add(city);
            totalLength[city.getNumber() - 1] = Double.MAX_VALUE;
        }

        // Initialisation
        City cityStart = getCityInList(start, listOfCities);
        totalLength[cityStart.getNumber() - 1] = 0.0;

        // Boucle
        while (intermediateListCities.isEmpty() == false) {
            City cityToTry = cityWithMinTotLen(totalLength, intermediateListCities);
            intermediateListCities.remove(cityToTry);
            //Quels sont les voisins de la ville à essayer?
            ArrayList<City> listOfNeighbors = getNeighbors(cityToTry);
            for (City neighborCity : listOfNeighbors) {
                // A-t-on le droit de visiter ce voisin-ci?
                if (intermediateListCities.contains(neighborCity) == true) {
                    Link link = getLinkInList(cityToTry.getNumber(), neighborCity.getNumber(), listOfLinks);
                    double newLen = totalLength[cityToTry.getNumber() - 1] + link.getLinkLength();
                    if (newLen < totalLength[neighborCity.getNumber() - 1]) {
                        totalLength[neighborCity.getNumber() - 1] = newLen;
                        pred[neighborCity.getNumber() - 1] = cityToTry.getNumber();
                    }
                }

            }
        }

        // Ecriture de la liste des noeuds du chemin calcul de la perte linéique moyenne
        listToFollow.add(end);
        int number = end;
        while (number != start) {
            listToFollow.add(0, pred[number - 1]);
            number = pred[number - 1];
        }
        // Calcul de la perte linéique moyenne
        double meanLineicLoss = 0;
        for (int index = 0; index < listToFollow.size() - 1; index++) {
            Link linkConsidered = getLinkInList(listToFollow.get(index), listToFollow.get(index + 1), listOfLinks);
            meanLineicLoss = meanLineicLoss + linkConsidered.getLineicLoss() * linkConsidered.getLinkLength();
        }
        meanLineicLoss = meanLineicLoss / totalLength[end - 1];
        // Création du chemin
        Path shortPath = new Path(listToFollow, totalLength[end - 1], meanLineicLoss);
        return shortPath;
    }

    // --------------------------------------------------------------------------//

    // Partie simulation

    // Méthode auxiliaire

    public double getMaxInTable(double[] table) {
        double res = 0;
        for (double db : table) {
            if (db > res) {
                res = db;
            }
        }
        return res;
    }

    
    /**
     * Méthode auxiliaire d'affichage de la simulation du jour J pour la ville V
     * @param prod
     * @param cons
     * @param cityToPlot
     */
    public void plotSimulationOfCity(double[] prod, double[] cons, City cityToPlot) {
        Plot plot = new Plot();
        for (int i = 0; i < 1440; i++) {
            plot.addPoint(0, i, cons[i], true);
            plot.addPoint(1, i, prod[i], true);
            plot.addPoint(2, i, prod[i] - cons[i], true);
        }
        plot.addLegend(0, "Consumption");
        plot.addLegend(1, "Production");
        plot.addLegend(2, "Surplus of production");
        String nameFrame = "Ville n°" + cityToPlot.getNumber() + ", Producer : " + cityToPlot.getProducer()
                + ", Number of Houses : " + cityToPlot.getNbHouses();
        JFrame frame = new JFrame(nameFrame);
        frame.add(plot);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public boolean canProvidePower(City cityProd, double neededPower, double[] tableProd){
        boolean res = true;
        double minPower = tableProd[0];
        for(double db : tableProd){
            if(minPower>db){
                minPower=db;
            }
        }
        if(minPower<neededPower){
            res = false;
        }
        return res;
    }

    /**
     * Effectue la simulation du jour J pour le réseau
     * 
     * @param j
     */
    public void simulation(int j) {

        // Création des listes de tableaux
        ArrayList<double[]> listTableProd = new ArrayList<>();
        ArrayList<double[]> listTableCons = new ArrayList<>();

        // Création des tableaux pour chaque ville de la liste et ajout aux listes
        for (City city : listOfCities) {
            Production P = city.getCityProd();
            Consumption C = city.getCityCons();
            double[] prod = P.generate(j);
            double[] cons = C.generate(j);
            listTableProd.add(prod);
            listTableCons.add(cons);
        }

        // Création des listes de Producteurs et des autres villes
        ArrayList<City> listCityProd = getProdCities();
        ArrayList<City> listCityNoProd = getNoProdCities();

        // Simulation
        for (City cityNoProd : listCityNoProd) {
            ArrayList<Integer> listNumCities = new ArrayList<>();
            int numCityProd = 0;
            double maxNecessaryPower = getMaxInTable(listTableCons.get(cityNoProd.getNumber() - 1));
            Path path = new Path(listNumCities, Double.MAX_VALUE, 0.0);
            // Sélection de la ville productrice la plus proche
            for (City cityProd : listCityProd) {
                Path newPath = shortestPath(cityProd.getNumber(), cityNoProd.getNumber());
                //Il faut que la ville productrice soit en capacité de fournir de l'énergie
                if (path.lenPath > newPath.lenPath &&
                        canProvidePower(cityProd, maxNecessaryPower, listTableProd.get(cityProd.getNumber()-1))) {
                    path = newPath;
                    numCityProd = cityProd.getNumber();
                }
            }
            path.displayPath();
            path.injectPower(getCityInList(numCityProd, listCityProd), cityNoProd, listTableProd, maxNecessaryPower);
        }
        for (City city : listOfCities) {
            double[] cons = listTableCons.get(city.getNumber() - 1);
            double[] prod = listTableProd.get(city.getNumber() - 1);
            plotSimulationOfCity(prod, cons, city);
            // Attente pour laisser le temps au graph de se print.
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
