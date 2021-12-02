package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model4 extends Device {

    private double coefLin, tau, PowerMin, fPause;
    private int nCycles;
    private ArrayList<Double> ConsDay;

    public Model4(double PowerMin, double PowerMax, String name, double coefLin, double tau, int cycles, double fPause){
        super(PowerMax, name);
        this.coefLin = coefLin;
        this.tau = - tau;
        this.nCycles = cycles;
        this.PowerMin = PowerMin;
        this.fPause = fPause;
        this.ConsDay = this.addConsDay();
    }

    public Model4(Model4 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.coefLin = ModRef.getCoefLin();
        this.tau = ModRef.getTau();
        this.nCycles = ModRef.getNCycles();
        this.PowerMin = ModRef.getPowerMin();
        this.fPause = ModRef.getFPause();
        int tauDelay = Math.round(Math.round(1440*Math.random()));
        this.ConsDay = BasicModels.genDelay(ModRef.getConsDay(), tauDelay);
    }

    public double getCoefLin(){
        return this.coefLin;
    }

    public double getTau(){
        return this.tau;
    }

    public int getNCycles(){
        return this.nCycles;
    }

    public double getPowerMin(){
        return this.PowerMin;
    }

    public double getFPause(){
        return this.fPause;
    }

    public ArrayList<Double> getConsDay(){
        return this.ConsDay;
    }

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

    public void addCons(double[] prod, int day){
        double PowerNoise = PowerMin*0.75;
        double Noise = 0;

        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            prod[i] = prod[i] + ConsDay.get(i) + Noise;
        }
    }
}
