package Network;
//import City.City;
//import java.util.ArrayList;
public class Link {
    
    //Attributs
    private double linkLength; //Nombre de villes dans le Réseau (en km)
    private double lineicLoss; //Perte linéique de puissance (W/km)
    private int start; //Départ du lien
    private int end; //Fin du lien
    
    //Constructeur primaire

    public Link(){
        linkLength = 1.0;
        //transportedPower = new double[1440];
        lineicLoss = 1.0;
        start = 1;
        end = 2;
    }

    //Constructeurs explicites
    /*public Link(double length, double[] transportedPower, int start, int end){
        this.linkLength = length;
        this.transportedPower = transportedPower;
        this.start = start;
        this.end = end;
        lineicLoss = 1.0;
    }*/
    public Link(double length, int start, int end, double lineicLoss){
        this.linkLength = length;
        //this.transportedPower = new double[1440];
        this.start = start;
        this.end = end;
        this.lineicLoss = lineicLoss;
    }


    //Setters and Getters 

    public double getLinkLength() {
        return this.linkLength;
    }

    public void setLinkLength(double linkLength) {
        this.linkLength = linkLength;
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

}
