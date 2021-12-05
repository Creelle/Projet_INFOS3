package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of the fourth model of devices */

public class Model4 extends Device {

    /**
     * coefLin is the linear coefficient of the linear function of the model
     * tau is the time increment of the exponential function of the model
     * PowerMin is the power of the device in stand-by
     * fPause is the factor (between 0 and 1) that multiplies the Period (1440/nCycles) and defines the time that device stays in PowerMin
     * nCycles is the number of cycles of consummation this device makes in a day
     * ConsDay is the daily consummation of the device minute by minute
     */
    private double coefLin, tau, PowerMin, fPause;
    private int nCycles;
    private ArrayList<Double> ConsDay;

    /**
     * Constructor 
     * @param PowerMin is the power of the device in stand-by
     * @param PowerMax is the maximum power of the device
     * @param name is the name of the device
     * @param coefLin is the linear coefficient of the linear function of the model
     * @param tau is the time increment of the exponential function of the model
     * @param cycles is the number of cycles of consummation this device makes in a day
     * @param fPause multiplying factor to define the pauses in the model
     */
    public Model4(double PowerMin, double PowerMax, String name, double coefLin, double tau, int cycles, double fPause){
        super(PowerMax, name);
        this.coefLin = coefLin;
        this.tau = - tau;
        this.nCycles = cycles;
        this.PowerMin = PowerMin;
        this.fPause = fPause;
        this.ConsDay = this.addConsDay();
    }

    /**
     * Constructor
     * @param ModRef is a Model4 device that we want to copy
     */
    public Model4(Model4 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.coefLin = ModRef.getCoefLin();
        this.tau = ModRef.getTau();
        this.nCycles = ModRef.getNCycles();
        this.PowerMin = ModRef.getPowerMin();
        this.fPause = ModRef.getFPause();
        this.ConsDay = ModRef.getConsDay();
    }

    /**
     * Getter for coefLin
     * @return coefLin
     */
    public double getCoefLin(){
        return this.coefLin;
    }

    /**
     * Getter for tau
     * @return tau
     */
    public double getTau(){
        return this.tau;
    }

    /**
     * Getter for nCycles
     * @return nCycles
     */
    public int getNCycles(){
        return this.nCycles;
    }

    /**
     * Getter for PowerMin
     * @return PowerMin
     */
    public double getPowerMin(){
        return this.PowerMin;
    }

    /**
     * Getter for fPause
     * @return fPause
     */
    public double getFPause(){
        return this.fPause;
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
    public ArrayList<Double> addConsDay(){
        ArrayList<Double> genExp = BasicModels.genExp(1, tau, (1-fPause)*1440/nCycles);
        ArrayList<Double> genLin = BasicModels.genLinear(coefLin*1440/nCycles, 0, (1-fPause)*1440/nCycles);
        ArrayList<Double> genCons = BasicModels.genConstant(getPowerMax());

        ArrayList<Double> genRet = new ArrayList<Double>();

        int contLin = 0;
        double minExpLin=0;

        for(int i=0; i<1440; i++){
            if(contLin<(1-fPause)*1440/nCycles){
                minExpLin = Math.min(genExp.get(contLin), genLin.get(contLin));
                genRet.add(Math.min(genCons.get(i), minExpLin) + PowerMin);
            } else{
                genRet.add(PowerMin);
            }
            
            contLin+=1;
            if(contLin>=1440/nCycles){
                contLin=0;
            }
        }
        return genRet;
    }

    /**
     * This will add the consummation of Model4 device in vector prod, with noise and a "delay" that considers
     * different hours of the day.
     * 
     * Make in this way instead of generates the same consummation for same the same model all de times helped
     * a lot to reduce computational time.
     */
    public void addCons(double[] prod, int day){
        double PowerNoise = PowerMin*0.75;
        double Noise = 0;

        int tauDelay = Math.round(Math.round(1440*Math.random()));
        ArrayList<Double> ConsDelay = BasicModels.genDelay(this.getConsDay(), tauDelay);

        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            prod[i] = prod[i] + ConsDelay.get(i) + Noise;
        }
    }
}
