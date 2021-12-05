package Production;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of solar producer */

public class Solar extends ProductionSystem{
    
    /**
     * PowerSunMin is the minimum power (of the maximum daily power) that the sun can develop in the year
     * PowerSunMax is the maximum power (of the maximum daily power) that the sun can develop in the year
     */
    private double PowerSunMin, PowerSunMax;

    /**
     * 
     * @param PowerMaxR maximum power of this producer
     * @param PowerSunMinR minimum power (of the maximum daily power) that sun can develop in the year of this producer
     * @param PowerSunMaxR is the maximum power (of the maximum daily power) that sun can develop in the year of this producer
     */
    public Solar(double PowerMaxR, double PowerSunMinR, double PowerSunMaxR){
        super(PowerMaxR);
        this.PowerSunMin = PowerSunMinR;
        this.PowerSunMax = PowerSunMaxR;
    }

    /**
     * Getter name
     * @return name
     */
    public String getName(){
        return("Solar");
    }

    /**
     * Generates the daily production and add it to vector prod
     * @param prod is the vector that you want to add the production in
     * @param day is the day of the year that are simulated
     */
    public void addProd(double[] prod, int day){
        double acumulate = 0;
        double diff=0;
        double SunRise=7*60, SunSet=19*60;

        double PowerDay;

        /* This part calculates the maximum power in the day considering the parameters */
        PowerDay = BasicModels.genCosDay(PowerSunMin,PowerSunMax,day, 365, (216.5/365)*2*Math.PI);

        /* This part calculates the time of the sunrise and sunset with a linear model */
        if(day>33 && day<=218){
            SunRise = BasicModels.genLinearDay(33, 218, 8.5, 5, day);
            SunSet = BasicModels.genLinearDay(33, 218, 17, 20, day);
        } else if(day>218 || day<=33){
            if(day<=33){
                day = day+365;
            }
            SunRise = BasicModels.genLinearDay(218, 398, 5, 8.5, day);
            SunSet = BasicModels.genLinearDay(218, 398, 20, 17, day);
        }
        
        ArrayList<Double> prodSun = BasicModels.genQuadratic(PowerDay, SunRise, SunSet);

        ArrayList<Double> prodMax = BasicModels.genConstant(getPowerMax());


        for(int i=0;i<1440;i++){
            diff = prodSun.get(i) - prodMax.get(i);

            /* This part calculates de power accumulated, but with the restriction of the 30 min of maximum
            power at max accumulated */
            if(diff>0){
                acumulate+=diff;
                acumulate = Math.min(acumulate, getPowerMax()*30);
            }

            if(diff<0 && acumulate>0){
                acumulate+=diff;
                if(acumulate<0){
                    prod[i] = prodMax.get(i) + acumulate + prod[i];
                    acumulate=0;
                } else{
                    prod[i] = prodMax.get(i) + prod[i];
                }
            } else {
                prod[i] = Math.min(prodSun.get(i), prodMax.get(i))+ prod[i];
            }
        }
    }
}
