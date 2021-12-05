package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of the third model of devices */

public class Model3 extends Device {

    /**
     * nCycles is the number of cycles of consummation this device makes in a day
     * dayMax is the day that the maximum power is requested
     * PowerMin is the power of the device in stand-by
     * factMin is the minimum value of multiplying factor of the seasons. The value must be between 0 and 1
     * ConsYear is a ArrayList that contains ArrayList with daily consummation of the device in one year
     */
    private int nCycles, dayMax;
    private double PowerMin, factMin;
    private ArrayList<ArrayList<Double>> ConsYear;
    
    /**
     * Constructor
     * @param PowerMin is the power of the device in stand-by
     * @param PowerMax is the maximum power of the device
     * @param name is the name of the device
     * @param nCycles is the number of cycles of consummation this device makes in a day
     * @param dayMax is the day that the maximum power is requested
     * @param factMin is the minimum value of multiplying factor of the seasons
     */
    public Model3(double PowerMin, double PowerMax, String name, int nCycles, int dayMax, double factMin){
        super(PowerMax, name);
        this.PowerMin = PowerMin;
        this.nCycles = nCycles;
        this.dayMax = dayMax;
        this.factMin = factMin;
        this.ConsYear = this.addConsYear();
    }

    /**
     * Constructor
     * @param ModRef is a Model3 device that we want to copy
     */
    public Model3(Model3 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.PowerMin = ModRef.getPowerMin();
        this.nCycles = ModRef.getNCycles();
        this.dayMax = ModRef.getDayMax();
        this.factMin = ModRef.getFactMin();
        this.ConsYear = ModRef.getConsYear();
    }

    /**
     * Getter for PowerMin
     * @return PowerMin
     */
    public double getPowerMin(){
        return this.PowerMin;
    }

    /**
     * Getter for nCycles
     * @return nCycles
     */
    public int getNCycles(){
        return this.nCycles;
    }

    /**
     * Getter for dayMax
     * @return dayMax
     */
    public int getDayMax(){
        return this.dayMax;
    }

    /**
     * Getter for factMin
     * @return factMin
     */
    public double getFactMin(){
        return this.factMin;
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

        ArrayList<Double> genFact = BasicModels.genLinear(1, factMin, 182.5);
        genFact.addAll(BasicModels.genLinear(factMin, 1, 182.5));
        genFact = BasicModels.genDelay(genFact, dayMax);

        ArrayList<Double> genCons = BasicModels.genConstant(PowerMin);

        for(int day=1; day<=365; day++){
            ArrayList<Double> genQuad = BasicModels.genQuadratic(genFact.get(day)*(getPowerMax()-PowerMin), 0, 0.6*1440/nCycles);

            int contQuad=0;

            ArrayList<Double> ConsDay = new ArrayList<Double>();

            for(int i=0; i<1440; i++){
                ConsDay.add(genCons.get(i) + genQuad.get(contQuad));
                contQuad+=1;
                if(contQuad>=1440/nCycles){
                    contQuad=0;
                }
            }
            genRet.add(ConsDay);
        }
        return genRet;
    }

    /**
     * This will add the consummation of Model3 device in vector prod, with noise.
     * 
     * Make in this way instead of generates the same consummation for same the same model all de times helped
     * a lot to reduce computational time.
     */
    public void addCons(double[] prod, int day){
        double PowerNoise = PowerMin*0.75;
        double Noise = 0;

        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            prod[i] = prod[i] + ConsYear.get(day-1).get(i) + Noise;
        }
    }
}
