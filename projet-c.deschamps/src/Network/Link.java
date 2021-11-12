package Network;
import City.City;
import Consumption.Consumption;
import Production.Production;
public class Link {
    
    //Attributs
    private double linkLength; //Nombre de villes dans le Réseau (en km)
    private double[] transportedPower; //Puissance transportée par le lien au départ (W)
    private double lineicLoss; //Perte linéique de puissance (W/km)
    private int start; //Départ du lien
    private int end; //Fin du lien
    
    //Constructeur primaire

    public Link(){
        linkLength = 1.0;
        transportedPower = new double[1440];
        lineicLoss = 1.0;
        start = 1;
        end = 2;
    }

    //Constructeurs explicites
    public Link(double length, double[] transportedPower, int start, int end){
        this.linkLength = length;
        this.transportedPower = transportedPower;
        this.start = start;
        this.end = end;
        lineicLoss = 1.0;
    }
    public Link(double length, int start, int end){
        this.linkLength = length;
        this.transportedPower = new double[1440];
        this.start = start;
        this.end = end;
        lineicLoss = 1.0;
    }


    //Setters and Getters 

    public double getLinkLength() {
        return this.linkLength;
    }

    public void setLinkLength(double linkLength) {
        this.linkLength = linkLength;
    }

    public double[] getTransportedPower() {
        return this.transportedPower;
    }

    public void setTransportedPower(double[] transportedPower) {
        this.transportedPower = transportedPower;
    }

    public double getLineicLoss() {
        return this.lineicLoss;
    }

    public void setLineicLoss(double lineicLoss) {
        this.lineicLoss = lineicLoss;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    //Méthodes

    /**
     * Méthode injectant le surplus de production d'une autre ville dans une certaine ville
     * 
     * @param city //Ville considérée
     * @param j //jour considéré
     */
    public void inject(City city, int j){
        Production P = city.getCityProd();
        double[] prod = P.generate(j);
        for(int k = 0; k<1440; k++){
            prod[k]+= transportedPower[k]-lineicLoss*linkLength;
        }
    }

    public double[] takeSurplus(City city, int j){
        double[] powerToTransport = new double[1440];
        Production P = city.getCityProd();
        double[] prod = P.generate(j);
        Consumption C = city.getCityCons();
        double[] cons = C.generate(j);
        for(int k = 0; k<1440; k++){
            double surplus = prod[k] - cons[k];
            if(surplus > 0){
                powerToTransport[k] = surplus;
            }else{
                powerToTransport[k] = 0;
            }
        }
        return powerToTransport;
    }

}
