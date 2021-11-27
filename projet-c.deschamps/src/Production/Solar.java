package Extension4;

import java.util.ArrayList;

public class Solar extends ProductionSystem{
    
    private double PowerSunMin, PowerSunMax;

    public Solar(double PowerMaxR, double PowerSunMinR, double PowerSunMaxR){
        super(PowerMaxR);
        this.PowerSunMin = PowerSunMinR;
        this.PowerSunMax = PowerSunMaxR;
    }

    public void addProd(double[] prod, int day){
        double acumulate = 0;
        double diff=0;

        double PowerDay;

        PowerDay = BasicModels.genCosDay(PowerSunMin,PowerSunMax,day, 365, (216.5/365)*2*Math.PI);

        ArrayList<Double> prodSun = BasicModels.genQuadratic(PowerDay, 8*60, 19*60);

        ArrayList<Double> prodMax = BasicModels.genConstant(getPowerMax());


        for(int i=0;i<1440;i++){
            diff = prodSun.get(i) - prodMax.get(i);

            if(diff>0){
                acumulate+=diff;
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