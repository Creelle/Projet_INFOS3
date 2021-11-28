package Production;

import java.util.ArrayList;

public class Solar extends ProductionSystem{
    
    private double PowerSunMin, PowerSunMax;

    public Solar(double PowerMaxR, double PowerSunMinR, double PowerSunMaxR){
        super(PowerMaxR);
        this.PowerSunMin = PowerSunMinR;
        this.PowerSunMax = PowerSunMaxR;
    }

    public String getName(){
        return("Solar");
    }

    public void addProd(double[] prod, int day){
        double acumulate = 0;
        double diff=0;
        double SunRise=7*60, SunSet=19*60;

        double PowerDay;

        PowerDay = BasicModels.genCosDay(PowerSunMin,PowerSunMax,day, 365, (216.5/365)*2*Math.PI);

        if(day>33 && day<=218){
            SunRise = BasicModels.genLinear(33, 218, 8.5, 5, day);
            SunSet = BasicModels.genLinear(33, 218, 17, 20, day);
        } else if(day>218 || day<=33){
            if(day<=33){
                day = day+365;
            }
            SunRise = BasicModels.genLinear(218, 398, 5, 8.5, day);
            SunSet = BasicModels.genLinear(218, 398, 20, 17, day);
        }
        
        ArrayList<Double> prodSun = BasicModels.genQuadratic(PowerDay, SunRise, SunSet);

        ArrayList<Double> prodMax = BasicModels.genConstant(getPowerMax());


        for(int i=0;i<1440;i++){
            diff = prodSun.get(i) - prodMax.get(i);

            if(diff>0){
                acumulate+=diff;
                acumulate = Math.min(acumulate, getPowerMax()*20);
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
