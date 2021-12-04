package Production;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of eolian producer */

public class Eolian extends ProductionSystem{

    /**
     * SpeedMin is the minimum speed of the maximum peak of the function of minimum speeds (will be showed on graphs in the report)
     * SpeedMax is the maximum speed of the maximum peak of the function of maximum speeds (will be showed on graphs in the report)
     */
    private double SpeedWindMin, SpeedWindMax;
    
    /**
     * Constructor
     * @param PowerMaxP is the maximum power of this producer
     * @param SpeedWindMinP is the SpeedMin explained above
     * @param SpeedWindMaxP is the SpeedMax explained above
     */
    public Eolian(double PowerMaxP, double SpeedWindMinP, double SpeedWindMaxP){
        super(PowerMaxP);
        this.SpeedWindMin = SpeedWindMinP;
        this.SpeedWindMax = SpeedWindMaxP;
    }

    /**
     * Getter name
     * @return name
     */
    public String getName(){
        return("Eolian");
    }

    /**
     * Generates the daily production add it to vector prod
     * @param prod is the vector that you want to add the production in
     * @param day is the day of the year that are simulated
     */
    public void addProd(double[] prod, int day){
        /* This part calculates the minimum and maximum speeds in the day that is simulated */
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
