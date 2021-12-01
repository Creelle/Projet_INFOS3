package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Modele1 extends Device{

    private double tCharge, Period;

    public Modele1(String name, double PowerMax, double tCharge, double Period){
        super(PowerMax,name);
        this.tCharge = tCharge;
        this.Period = Period;
    }

    public void addCons(double[] prod, int day){
        ArrayList<Double> genSin = BasicModels.genSin(getPowerMax(), 0, Period, Math.PI/2);
        ArrayList<Double> genNoise = BasicModels.genWhiteNoise(getPowerMax()/10);

        int contSin=0;

        for(int i=0; i<1440; i++){
            if(i<Period/2 || (i>tCharge && i<(1440-2*tCharge))){
                prod[i] = prod[i] + Math.max(genSin.get(contSin) + genNoise.get(i),0);
                contSin+=1;
            } else if(i>=Period/2 && i<=tCharge){
                prod[i] = prod[i] + getPowerMax() + genNoise.get(i);
            } else {
                prod[i] = prod[i];
            }
        }
    }
}
