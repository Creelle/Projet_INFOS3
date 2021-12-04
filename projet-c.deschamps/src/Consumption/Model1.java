package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of the first model of devices */

public class Model1 extends Device{

    /**
     * tCharge is how much time the device spend charging
     * Period is the period of the sinus function of the model
     * ConsDay is the daily consummation of the device minute by minute
     */
    private double tCharge, Period;
    private ArrayList<Double> ConsDay;

    /**
     * Constructor
     * @param name is the name of the device
     * @param PowerMax is the maximum power
     * @param tCharge is the period of charging
     * @param Period is the period of sinus function in the model
     */
    public Model1(String name, double PowerMax, double tCharge, double Period){
        super(PowerMax,name);
        this.tCharge = tCharge;
        this.Period = Period;
        this.ConsDay = this.addConsDay();
    }

    /**
     * Constructor
     * @param ModRef is a Model1 device that we want to copy
     */
    public Model1(Model1 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.tCharge = ModRef.getTCharge();
        this.Period = ModRef.getPeriod();
        this.ConsDay = ModRef.getConsDay(); // Not necessary copy element by element of the arraylist because we won't
        //change it, so copy the reference is sufficient
    }

    /**
     * Getter for tCharge
     * @return tCharge
     */
    public double getTCharge(){
        return this.tCharge;
    }

    /**
     * Getter for Period
     * @return Period
     */
    public double getPeriod(){
        return this.Period;
    }

    /**
     * Getter for ConsDay
     * @return ConsDay
     */
    public ArrayList<Double> getConsDay(){
        return this.ConsDay;
    }

    /**
     * Generates the daily consummation minute by minute of the model
     * It's private because we just want to call this in the constructor
     * @return ArrayList with consummation of the day
     */
    private ArrayList<Double> addConsDay(){
        ArrayList<Double> genRet = new ArrayList<Double>();
        ArrayList<Double> genSin = BasicModels.genSin(getPowerMax(), 0, Period, Math.PI/2);

        int contSin=0;

        for(int i=0; i<1440; i++){
            if(i<Period/2 || (i>tCharge && i<(1440-3*tCharge))){
                genRet.add(Math.max(genSin.get(contSin),0));
                contSin+=1;
            } else if(i>=Period/2 && i<=tCharge){
                genRet.add(getPowerMax());
            } else {
                genRet.add(0.0);
            }
        }

        return genRet;
    }

    /**
     * This will add the consummation of Model1 device in vector prod, with noise and a "delay" that considers
     * different hours of the day.
     * 
     * Make in this way instead of generates the same consummation for same the same model all de times helped
     * a lot to reduce computational time.
     */
    public void addCons(double[] prod, int day){
        double PowerNoise = getPowerMax()*0.1;
        double Noise = 0;

        int tauDelay = Math.round(Math.round(1440*Math.random()));
        ArrayList<Double> ConsDelay = BasicModels.genDelay(this.getConsDay(), tauDelay);

        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            if(ConsDelay.get(i)==0){
                prod[i] = prod[i] + ConsDelay.get(i);
            } else {
                prod[i] = prod[i] + ConsDelay.get(i) + Noise;
            }
        }
    }
}