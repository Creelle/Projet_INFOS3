package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of the second model of devices */

public class Model2 extends Device {

    /**
     * dayMax is the day that the maximum power is requested
     * hMax is the hour of the day that the maximum power is request
     * PowerMinRequest is the peak of minimum power requested
     * PowerMaxRequest is the peak of maximum power requested
     * ConsYear is a ArrayList that contains ArrayList with daily consummation of the device in one year
     */
    private int dayMax, hMax;
    private double PowerMinRequest, PowerMaxRequest;
    private ArrayList<ArrayList<Double>> ConsYear;
    
    /**
     * Constructor
     * @param PowerMax is the maximum power of the device
     * @param name is the name of the device
     * @param PowerMinRequest is the peak of minimum power requested
     * @param PowerMaxRequest is the peak of maximum power requested
     * @param dayMax is the day that the maximum power is requested
     * @param hMax is the hour of the day that maximum power is requested
     */
    public Model2(double PowerMax, String name, double PowerMinRequest, double PowerMaxRequest, int dayMax, int hMax){
        super(PowerMax,name);
        this.PowerMinRequest = PowerMinRequest;
        this.PowerMaxRequest = PowerMaxRequest;
        this.dayMax = dayMax;
        this.hMax = hMax * 60;
        this.ConsYear = this.addConsYear();
    }

    /**
     * Constructor
     * @param ModRef is a Model2 device that we want to copy
     */
    public Model2(Model2 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.PowerMinRequest = ModRef.getPowerMinRequest();
        this.PowerMaxRequest = ModRef.getPowerMaxRequest();
        this.dayMax = ModRef.getDayMax();
        this.hMax = ModRef.getHMax();
        this.ConsYear = ModRef.getConsYear();
    }

    /**
     * Getter for PowerMinRequest
     * @return PowerMinRequest
     */
    public double getPowerMinRequest(){
        return this.PowerMinRequest;
    }

    /**
     * Getter for PowerMaxRequest
     * @return PowerMaxRequest
     */
    public double getPowerMaxRequest(){
        return this.PowerMaxRequest;
    }

    /**
     * Getter for dayMax
     * @return dayMax
     */
    public int getDayMax(){
        return this.dayMax;
    }

    /**
     * Getter for hMax
     * @return hMax
     */
    public int getHMax(){
        return this.hMax;
    }

    /**
     * Getter for ConsYear
     * @return ConsYear
     */
    public ArrayList<ArrayList<Double>> getConsYear(){
        return this.ConsYear;
    }

    /**
     * Generates the consummation of all the year of this device
     * It's private because we just want to call this in the constructor
     * @return ArrayList with ArrayLists with daily consummation
     */
    private ArrayList<ArrayList<Double>> addConsYear(){
        ArrayList<ArrayList<Double>> genRet = new ArrayList<>();

        ArrayList<Double> genCons = BasicModels.genConstant(getPowerMax());
        
        for(int day=1; day<=365; day++){
            double PowerDayMax = BasicModels.genCosDay(0, PowerMaxRequest, day, 365, 2*Math.PI*dayMax/365);
            double PowerDayMin = BasicModels.genCosDay(0, PowerMinRequest, day, 365, 2*Math.PI*dayMax/365);

            ArrayList<Double> genList = BasicModels.genLinear(PowerDayMax, PowerDayMin, 720);
            genList.addAll(BasicModels.genLinear(PowerDayMin, PowerDayMax, 720));
        
            genList = BasicModels.genDelay(genList, hMax);

            ArrayList<Double> ConsDay = new ArrayList<Double>();

            for(int i=0; i<1440; i++){
                ConsDay.add(Math.min(genList.get(i), genCons.get(i)));
            }

            genRet.add(ConsDay);
        }
        return genRet;
    }

    /**
     * This will add the consummation of Model2 device in vector prod, with noise.
     * 
     * Make in this way instead of generates the same consummation for same the same model all de times helped
     * a lot to reduce computational time.
     */
    public void addCons(double[] prod, int day){
        double PowerNoise = getPowerMax()*0.05;
        double Noise = 0;
        
        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            prod[i] = prod[i] + Math.max(ConsYear.get(day-1).get(i) + Noise, 0);
        }
    }
}
