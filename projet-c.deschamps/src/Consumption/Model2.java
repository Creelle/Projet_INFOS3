package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model2 extends Device {

    private int dayMax, hMax;
    private double PowerMinRequest, PowerMaxRequest;
    
    public Model2(double PowerMax, String name, double PowerMinRequest, double PowerMaxRequest, int dayMax, int hMax){
        super(PowerMax,name);
        this.PowerMinRequest = PowerMinRequest;
        this.PowerMaxRequest = PowerMaxRequest;
        this.dayMax = dayMax;
        this.hMax = hMax * 60;
    }

    public void addCons(double[] prod, int day){
        double PowerDayMax = BasicModels.genCosDay(0, PowerMaxRequest, day, 365, 2*Math.PI*dayMax/365);
        double PowerDayMin = BasicModels.genCosDay(0, PowerMinRequest, day, 365, 2*Math.PI*dayMax/365);

        ArrayList<Double> genList = BasicModels.genLinear(PowerDayMax, PowerDayMin, 720);
        genList.addAll(BasicModels.genLinear(PowerDayMin, PowerDayMax, 720));

        ArrayList<Double> genCons = BasicModels.genConstant(getPowerMax());

        ArrayList<Double> genNoise = BasicModels.genWhiteNoise(0.05*getPowerMax());

        genList = BasicModels.genDelay(genList, hMax);

        for(int i=0; i<1440; i++){
            prod[i] = prod[i] + Math.min(Math.max(genList.get(i)+genNoise.get(i),0), genCons.get(i)+genNoise.get(i));
        }
    }

}
