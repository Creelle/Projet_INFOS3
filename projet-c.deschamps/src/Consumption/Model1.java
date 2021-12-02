package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model1 extends Device{

    private double tCharge, Period;
    private ArrayList<Double> DayConsumption;

    public Model1(String name, double PowerMax, double tCharge, double Period){
        super(PowerMax,name);
        this.tCharge = tCharge;
        this.Period = Period;
        this.DayConsumption = this.addConsDay();
    }

    public Model1(Model1 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.tCharge = ModRef.getTCharge();
        this.Period = ModRef.getPeriod();
        int tauDelay = Math.round(Math.round(1440*Math.random()));
        this.DayConsumption = BasicModels.genDelay(ModRef.getDayConsumption(), tauDelay);
    }

    public double getTCharge(){
        return this.tCharge;
    }

    public double getPeriod(){
        return this.Period;
    }

    public ArrayList<Double> getDayConsumption(){
        return this.DayConsumption;
    }

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

    public void addCons(double[] prod, int day){
        double PowerNoise = getPowerMax()*0.1;
        double Noise = 0;

        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            if(DayConsumption.get(i)==0){
                prod[i] = prod[i] + DayConsumption.get(i);
            } else {
                prod[i] = prod[i] + DayConsumption.get(i) + Noise;
            }
        }
    }
}