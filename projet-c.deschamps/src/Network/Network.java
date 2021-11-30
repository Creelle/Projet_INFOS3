package Network;

import City.City;
import Consumption.Consumption;
import Production.Production;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    // *************************************************************************\\

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

    // ***************************************************************************\\

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
            // Pas de nouvelle ville dans un cercle de rayon 5km autour d'une ville
            // existante
            for (City city : listCities) {
                while (Math.pow(x - city.getX(), 2) + Math.pow(y - city.getY(), 2) <= 25) {
                    x = 100 * Math.random();
                    y = 100 * Math.random();
                }
            }
            int nbHouses = (int) Math.round(Math.random() * 2000);
            boolean producer = false;
            if (Math.random() < 0.5) {
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

    // **************************************************************************************
    // \\

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
                        && counterLoop < 100) {
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
                } else if (counterLoop == 100) {
                    // Ne rien faire !
                } else {
                    // Création des liens correspondants (aller-retour)
                    double length = calculateLength(city, cityToLink);
                    double lineicLoss = Math.round((Math.random() * 2.5 + 0.5) * 10.0) / 10.0;
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

    // *****************************************************************************************
    // \\

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

    // *************************************************************************************
    // \\

    // Partie Vérification graph connexe

    /**
     * Pour vérifier si le graphe est connesxe ou non, je vérifie qu'il existe un
     * chemin efficace energétiquement entre la ville 1 et les autres du réseau
     * 
     * @param listCities
     * @param listLinks
     * @return true/false suivant si le graphe est connexe ou non
     */
    public boolean checkConnectedNetwork(ArrayList<City> listCities, ArrayList<Link> listLinks) {
        boolean connected = false;
        int numStart = listCities.get(0).getNumber();
        // Pour toutes les autres villes : existe-t-il un chemin efficient pour
        // aller jusqu'à cette ville?
        for (City city : listCities) {
            int numCity = city.getNumber();
            if (city.getNumber() != numStart) {
                try {
                    bestPath(numStart, numCity);
                }
                // Si une ville n'est pas réliée à celle de départ, l'erreur suivante apparaît
                catch (IndexOutOfBoundsException error) {
                    System.out.println("Network not connected");
                    return connected;
                }
            }
        }
        System.out.println("Network connected");
        connected = true;
        return connected;
    }

    // *************************************************************************************
    // \\

    // Partie algorithme du chemin le plus efficace

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
     * @param totalLoss  tableau des distances plus courtes distances entre une
     *                   ville de départ et les villes du tableau.
     * @param listCities liste des villes du tableau
     * @return la ville ayant la plus courte distance par rapport au point de
     *         départ.
     */
    public City cityWithMinTotLoss(double[] totalLoss, ArrayList<City> listCities) {
        double minLoss = Double.MAX_VALUE;
        int numCityWithMinTotLen = 1;
        for (City city : listCities) {
            if (totalLoss[city.getNumber() - 1] < minLoss) {
                numCityWithMinTotLen = city.getNumber();
                minLoss = totalLoss[city.getNumber() - 1];
            }
        }
        // Ceci arrive lorsque le graphe n'est pas connexe
        if (minLoss == Double.MAX_VALUE) {
            City cityToReturn = listCities.get(0);
            return cityToReturn;
        }
        City cityToReturn = getCityInList(numCityWithMinTotLen, listCities);
        return cityToReturn;
    }

    /**
     * Algorithme du meilleur chemin
     * 
     * @param start
     * @param end
     * @return la liste des numéros des villes à suivre
     */
    public Path bestPath(int start, int end) {
        // Déclaration des variables
        ArrayList<Integer> listToFollow = new ArrayList<>();
        int[] pred = new int[listOfCities.size()];
        double[] totalLoss = new double[listOfCities.size()];

        // Création de la liste intermédiaire : elle est constituée de toutes les villes
        // que l'on peut encore aller visiter
        ArrayList<City> intermediateListCities = new ArrayList<>();
        for (City city : listOfCities) {
            intermediateListCities.add(city);
            totalLoss[city.getNumber() - 1] = Double.MAX_VALUE;
        }

        // Initialisation du programme : on démarre à la ville désirée
        City cityStart = getCityInList(start, listOfCities);
        totalLoss[cityStart.getNumber() - 1] = 0.0;

        // Boucle jusqu'a ce qu'il n'y ait plus de villes à visiter
        while (intermediateListCities.isEmpty() == false) {
            City cityToTry = cityWithMinTotLoss(totalLoss, intermediateListCities);
            intermediateListCities.remove(cityToTry);
            // Quels sont les voisins de la ville à essayer?
            ArrayList<City> listOfNeighbors = getNeighbors(cityToTry);
            for (City neighborCity : listOfNeighbors) {
                // A-t-on le droit de visiter ce voisin-ci?
                if (intermediateListCities.contains(neighborCity) == true) {
                    Link link = getLinkInList(cityToTry.getNumber(), neighborCity.getNumber(), listOfLinks);
                    double newLoss = totalLoss[cityToTry.getNumber() - 1] + link.getLineicLoss() * link.getLinkLength();
                    if (newLoss < totalLoss[neighborCity.getNumber() - 1]) {
                        // Mise à jour de la perte si celle ci est moindre
                        totalLoss[neighborCity.getNumber() - 1] = newLoss;
                        // Mise à jour du prédecesseur
                        pred[neighborCity.getNumber() - 1] = cityToTry.getNumber();
                    }
                }

            }
        }

        // Ecriture de la liste des noeuds du chemin
        listToFollow.add(end);
        int number = end;
        while (number != start) {
            listToFollow.add(0, pred[number - 1]);
            number = pred[number - 1];
        }
        // Calcul de la longueur totale
        double length = 0;
        for (int index = 0; index < listToFollow.size() - 1; index++) {
            Link linkConsidered = getLinkInList(listToFollow.get(index), listToFollow.get(index + 1), listOfLinks);
            length = length + linkConsidered.getLinkLength();
        }
        // Création du chemin
        Path shortPath = new Path(listToFollow, length, totalLoss[end - 1]);
        return shortPath;
    }

    // **********************************************************************************\\

    // Partie simulation

    // Méthodes auxiliaires

    /**
     * Méthode auxiliaire du max d'un tableau
     * 
     * @param table
     * @return la valeur max du tableau
     */
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
     * 
     * @param prod
     * @param cons
     * @param cityToPlot
     */
    public void plotSimulationOfCity(double[] prod, double[] cons, City cityToPlot) {
        Plot plot = new Plot();
        for (int i = 0; i < cons.length; i++) {
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

    /**
     * Méthode auxiliaire de la moyenne d'un tableau
     * 
     * @param table
     * @return la valeur moyenne
     */
    public double meanOfTable(double[] table) {
        double s = 0;
        for (double db : table) {
            s += db;
        }
        return s / table.length;
    }

    /**
     * Méthode auxiliaire de la détermination de capacité à fournir de la puissance
     * 
     * @param neededPower //puissance demandée par une ville non productrice
     * @param tableProd   //tableau d eproduction de la ville productrice
     * @return true/false suivant la capacité de la ville à fournir de l'énergie
     */
    public boolean canProvidePower(double neededPower, double[] tableProd) {
        boolean res = false;
        double minPower = tableProd[0];
        for (double db : tableProd) {
            if (minPower > db) {
                minPower = db;
            }
        }
        if (minPower > neededPower) {
            res = true;
        }
        return res;
    }

    /**
     * Effectue la simulation du jour J pour le réseau
     * 
     * @param j
     * @param wantPlotOfDay Veut-on le tracé pour chaque ville pour le jour J?
     * 
     * @return ArrayList des production et consommation moyennes sur le jour J des
     *         villes
     * @throws IOException
     */
    public ArrayList<double[]> simulation(int j, boolean wantPlotOfDay) throws IOException {

        // Initialisation des résultats :
        // res sera composé de meanProd et meanCons
        ArrayList<double[]> res = new ArrayList<>();
        double[] meanProd = new double[listOfCities.size()];
        double[] meanCons = new double[listOfCities.size()];

        // Création des listes de tableaux
        ArrayList<double[]> listTableProd = new ArrayList<>();
        ArrayList<double[]> listTableCons = new ArrayList<>();

        //création de la liste des chemins entre ville productrices et non productrices
        ArrayList<Path> listOfPathsNoProd = new ArrayList<>();

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
        try {
            for (City cityNoProd : listCityNoProd) {
                ArrayList<Integer> listNumCities = new ArrayList<>();
                int numCityProd = 0;
                double maxNecessaryPower = getMaxInTable(listTableCons.get(cityNoProd.getNumber() - 1));
                Path path = new Path(listNumCities, Double.MAX_VALUE, 0.0);
                // Sélection de la ville productrice qui permet de fournir de l'énergie avec
                // peu de pertes
                for (City cityProd : listCityProd) {
                    Path newPath = bestPath(cityProd.getNumber(), cityNoProd.getNumber());
                    // Il faut que la ville productrice soit en capacité de fournir de l'énergie
                    if (path.lenPath > newPath.lenPath && canProvidePower(maxNecessaryPower,
                            listTableProd.get(cityProd.getNumber() - 1)) == true) {
                        path = newPath;
                        numCityProd = cityProd.getNumber();
                    }
                }
                listOfPathsNoProd.add(path);
                path.injectPower(getCityInList(numCityProd, listCityProd), cityNoProd, listTableProd,
                        maxNecessaryPower);
            }

            // Calcul des tableaux de res
            for (City city : listOfCities) {
                double[] cons = listTableCons.get(city.getNumber() - 1);
                double[] prod = listTableProd.get(city.getNumber() - 1);

                // Calcul des moyennes pour chaque ville
                double meanProdOfDay = meanOfTable(prod);
                double meanConsOfDay = meanOfTable(cons);
                double meanSurplus = meanProdOfDay - meanConsOfDay;
                if (meanSurplus < 0) {
                    System.out.println("blem!");
                }
                // Mise à jour des tableaux
                meanProd[city.getNumber() - 1] = meanProdOfDay;
                meanCons[city.getNumber() - 1] = meanConsOfDay;

                // Exemple de tracé si wantPlotOfDay vaut true
                if (wantPlotOfDay == true) {
                    plotSimulationOfCity(prod, cons, city);
                    // Attente pour laisser le temps au graph de se tracer.
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        } catch (IndexOutOfBoundsException error) {
            System.out.println("Production of the network is not sufficient to provide power to all cities!");
        }
        res.add(meanProd);
        res.add(meanCons);

        //Ecriture des fichiers CSV

        //Jour J
        /*CSVNetworkDay(j, listTableCons, listTableProd, listOfPathsNoProd);
        for(City city : listOfCities){
            CSVCityDay(j, city, listTableCons, listTableProd, listOfPathsNoProd);
        }*/

        //Année
        

        return res;
    }

    /**
     * Simulation pour l'année
     * @throws IOException
     */
    public void simulateOnAYear() throws IOException {

        // Création des listes de tableaux
        ArrayList<double[]> listTableMeanProd = new ArrayList<>();
        ArrayList<double[]> listTableMeanCons = new ArrayList<>();

        // Initialisation des tableaux vides
        for (int k = 0; k < listOfCities.size(); k++) {
            double[] prod = new double[365];
            double[] cons = new double[365];
            listTableMeanProd.add(prod);
            listTableMeanCons.add(cons);
        }

        // Mise à jour des tableaux moyens
        for (int j = 0; j < 365; j++) {
            ArrayList<double[]> listMeanTables = simulation(j + 1, false);
            for (int k = 0; k < listOfCities.size(); k++) {
                // Mise à jour de ces tableaux
                listTableMeanProd.get(k)[j] = listMeanTables.get(0)[k];
                listTableMeanCons.get(k)[j] = listMeanTables.get(1)[k];
            }
        }
        // Impression des graphes
        for (int k = 0; k < listTableMeanCons.size(); k++) {
            City city = listOfCities.get(k);
            double[] meanCons = listTableMeanCons.get(k);
            double[] meanProd = listTableMeanProd.get(k);
            Plot plot = new Plot();
            for (int i = 0; i < meanCons.length; i++) {
                plot.addPoint(0, i, meanCons[i], true);
                plot.addPoint(1, i, meanProd[i], true);
            }
            plot.addLegend(0, "Consumption");
            plot.addLegend(1, "Production");
            String nameFrame = "Ville n°" + city.getNumber() + ", Producer : " + city.getProducer()
                    + ", Number of Houses : " + city.getNbHouses();
            JFrame frame = new JFrame(nameFrame);
            frame.add(plot);
            frame.pack();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //***********************************************************************************\\


    //Partie obtention des tableaux généraux pour un réseau entier ou un cluster

    /**
     * Obtention des tableaux de puissance du réseau entier
     * 
     * @param listTableCons liste des tableaux de consommation de toutes les villes
     * @param listTableProd liste des tableaux de production de toutes les villes
     * @return la liste composée du tableau de production et du tableau de consommation du réseau
     */
    public ArrayList<double[]> getNetworkTables(ArrayList<double[]> listTableCons, ArrayList<double[]> listTableProd){
        //Le résutat sera composé du tableau de production totale, puis de celui de consommation
        // puis de celui de toutes les pertes du réseau
        ArrayList<double[]> networkTables = new ArrayList<>();
        double[] networkProd = new double[listTableProd.get(0).length];
        double[] networkCons = new double[listTableCons.get(0).length];
        for(int k=0; k<listTableCons.get(0).length; k++){
            for(int i=0; i<listTableCons.size(); i++){
                networkProd[k]+=listTableProd.get(i)[k];
                networkCons[k]+=listTableCons.get(i)[k];
            }
        }
        networkTables.add(networkProd);
        networkTables.add(networkCons);
        return networkTables;
    }

    /**
     * Calcule la perte d'énergie du réseau entier : celle ci est constante 
     *  car les pertes linéiques sont constantes dans le temps
     * 
     * @param listOfPaths tous les chemins depuis une ville productrice vers 
     *                          les autres non productrices
     * @return
     */
    public double getLossNetwork(ArrayList<Path> listOfPaths){
        double res = 0;
        for(Path p : listOfPaths){
            res+=p.lossPath;
        }
        return res;
    }

    /**
     * Calcule la perte d'énergie d'une ville : celle ci est constante 
     *  car les pertes linéiques sont constantes dans le temps
     * 
     * @param listOfPaths tous les chemins depuis une ville productrice vers 
     *                          les autres non productrices
     * @param city Ville productrice dont on considère les pertes liées
     * @return la valeur des pertes joules
     */
    public double getLossCityProd(City city, ArrayList<Path> listOfPaths){
        double res = 0;
        for(Path p : listOfPaths){
            if(p.getListNumberCities().get(0) == city.getNumber()){
                res+=p.lossPath;
            }
        }
        return res;
    }

    //***********************************************************************************\\

    //Partie écriture des CSV

    public void CSVNetworkDay(int j, ArrayList<double[]> listTableCons, ArrayList<double[]> listTableProd,
                     ArrayList<Path> listPathToNoProd) throws IOException{

        //Obtention des tableaux nécessaires
        ArrayList<double[]> listTablesOfNetwork = getNetworkTables(listTableCons, listTableProd);
        
        //Obtention des pertes de toutes les lignes
        double NetworkLoss = getLossNetwork(listPathToNoProd);

        //Introduction du fichier
        PrintWriter out = new PrintWriter(new FileWriter("../projet-c.deschamps/src/Network/CSV_Of_Network/CSV_Of_Network_Day"+j+".csv"));
        out.println("CSV File for the Network on Day "+ j);
        out.println("1 - Minute m ");
        out.println("2 - Power Consumed at minute m ");
        out.println("3 - Power Produced at minute m ");
        out.println("4 - Energy Consumed since the beginning of the day ");
        out.println("5 - Energy Produced since the beginning of the day ");
        out.println("6 - Energy Lost since the beginning of the day ");
        out.println(" ");
        
        //Initialisation des énergies
        double energyProduced = 0;
        double energyConsummed = 0;
        double energyLost = 0;

        //Ecriture du fichier
        for(int k=0; k<listTableCons.get(0).length; k++){
            out.println(k+ " ; " + listTablesOfNetwork.get(1)[k] + " ; " + listTablesOfNetwork.get(0)[k] +
             " ; " + energyConsummed + " ; " + energyProduced + " ; " + energyLost);
             energyProduced += listTablesOfNetwork.get(0)[k];
             energyConsummed += listTablesOfNetwork.get(1)[k];
             energyLost += NetworkLoss;
        }

        out.close();
    }

    public void CSVNetworkYear(){
        //Stratégie : aller lire la dernière ligne de chaque autre CSV pour récupérer 
        // les valeurs d'énergie produite, consommée et perdue
    }

    public void CSVCityDay(int j, City city, ArrayList<double[]> listTableCons, ArrayList<double[]> listTableProd,
                ArrayList<Path> listPathToNoProd) throws IOException{
        
        //Récupération des tableaux nécessaires
        double[] cityProd = listTableProd.get(city.getNumber()-1);
        double[] cityCons = listTableCons.get(city.getNumber()-1);

        //Récupération de la perte des lignes partant de la ville
        double cityLoss = getLossCityProd(city, listPathToNoProd);
        
        //Introduction du fichier CSV
        PrintWriter out = new PrintWriter(new FileWriter("../projet-c.deschamps/src/Network/CSV_Of_Clusters/CSV_Of_Cluster"+city.getNumber()+"_Day"+j+".csv"));
        out.println("CSV File for the City "+ city.getNumber()+ " on Day "+ j);
        out.println("1 - Minute m ");
        out.println("2 - Power Consumed at minute m ");
        out.println("3 - Power Produced at minute m ");
        out.println("4 - Energy Consumed since the beginning of the day ");
        out.println("5 - Energy Produced since the beginning of the day ");
        out.println("6 - Energy Lost since the beginning of the day on lines starting from the city");
        out.println(" ");

        //Initialisation des énergies
        double energyProduced = 0;
        double energyConsummed = 0;
        double energyLost = 0;

        //Ecriture du fichier
        for(int k=0; k<cityProd.length; k++){
            out.println(k+ " ; " + cityCons[k] + " ; " + cityProd[k] +
             " ; " + energyConsummed + " ; " + energyProduced + " ; " + energyLost);
             energyProduced += cityProd[k];
             energyConsummed += cityCons[k];
             energyLost += cityLoss;
        }

        out.close();
    }
}
