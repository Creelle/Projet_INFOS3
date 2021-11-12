package Network;
import City.City;
import java.util.ArrayList;
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
     * @param prod tableau de production existant de la ville considérée
     */
    public void inject(double[] prod){
        for(int k = 0; k<1440; k++){
            prod[k]+= transportedPower[k]-lineicLoss*linkLength;
        }
    }
    /**
     * Génère le tableau de puissance en trop dans la ville proportionnellement au nombre
     * de ville ne produisant pas
     * @param city
     * @param j
     * @param ratio inverse du nombre de villes ne produisant pas
     *  à laquelle la ville considérée est reliée
     * @return le tableau de puissance en trop
     */
    public double[] takeSurplus(City city, ArrayList<double[]> listTableProd, ArrayList<double[]> listTableCons, double ratio){
        double[] powerToTransport = new double[1440];
        double[] prod = listTableProd.get(city.getNumber()-1);
        double[] cons = listTableCons.get(city.getNumber()-1);
        for(int k = 0; k<1440; k++){
            double surplus = Math.round((prod[k] - cons[k])/ratio*10.0)/10.0;
            if(surplus > 0){
                powerToTransport[k] = surplus;
                prod[k] = prod[k] - surplus; //L'énergie n'est plus dispo dans cette ville!
            }else{
                powerToTransport[k] = 0;
            }
        }
        return powerToTransport;
    }

}
