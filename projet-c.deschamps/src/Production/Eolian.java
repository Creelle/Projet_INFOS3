package Production;

import java.util.ArrayList;

import Models.BasicModels;

public class Eolian extends ProductionSystem{
    private double SpeedWindMin, SpeedWindMax;
    
    public Eolian(double PowerMaxP, double SpeedWindMinP, double SpeedWindMaxP){
        super(PowerMaxP);
        this.SpeedWindMin = SpeedWindMinP;
        this.SpeedWindMax = SpeedWindMaxP;
    }

    public String getName(){
        return("Eolian");
    }

    public void addProd(double[] prod, int day){
        double SpeedMaxWindDay = 0.5 * SpeedWindMax + 0.5 * SpeedWindMax / Math.E * Math.exp(-Math.cos((1/58.2)*(day+240)))*Math.cos((1/29.1)*(day+240));
        double SpeedMinWindDay = 0.108*Math.E*SpeedWindMin+ 0.7*SpeedWindMin / Math.E * Math.exp(-Math.cos((1/58.2)*(day+240)))*Math.cos((1/29.1)*(day+240));

        ArrayList<Double> prodWind = BasicModels.genSin(SpeedMaxWindDay, SpeedMinWindDay, 720, Math.PI/2);

        prodWind = BasicModels.genDelay(prodWind, 10);

        ArrayList<Double> prodMax = BasicModels.genConstant(getPowerMax());

        for(int i=0;i<1440;i++){
            prod[i] = prod[i] + Math.min(50000*Math.sqrt(prodWind.get(i)), prodMax.get(i));
        }
    }
}
