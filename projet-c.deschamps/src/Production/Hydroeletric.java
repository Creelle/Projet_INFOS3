package Production;

import java.util.ArrayList;

public class Hydroeletric extends ProductionSystem{
    private double levelWaterMin, levelWaterMax;

    public Hydroeletric(double PowerMax, double levelWaterMax){
        super(PowerMax);
        if(levelWaterMax<30){
            throw new IllegalArgumentException("The level of the water must be greater than 30 meters");
        } else if(levelWaterMax>150){
            throw new IllegalArgumentException("The level of the water must be smaller than 150 meters");
        }
        this.levelWaterMin = 0.25*levelWaterMax;
        this.levelWaterMax = levelWaterMax;
    }

    public String getName(){
        return("Hydroeletric");
    }

    public void addProd(double[] prod, int day){

        double levelDay = BasicModels.genCosDay(levelWaterMin, levelWaterMax, day, 365, 2*Math.PI*33.5/365);

        ArrayList<Double> genLin = BasicModels.genLinear(levelDay, 0.85*levelDay, 1440/2);
        genLin.addAll(BasicModels.genLinear(0.85*levelDay, levelDay, 1440/2));

        for(int i = 0; i<1440; i++){
            double id = i/1.0;
            prod[i] = prod[i] + Math.min(Math.max(5250 * Math.sqrt(genLin.get(i)) * Math.abs(Math.sin(id/5)+100),0),getPowerMax());
        }
        
    }
}
