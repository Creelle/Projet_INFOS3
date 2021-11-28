package Production;

import java.util.ArrayList;

public class Eolian extends ProductionSystem{
    private double PowerWindMin, PowerWindMax;
    
    public Eolian(double PowerMaxP, double PowerWindMinP, double PowerWindMaxP){
        super(PowerMaxP);
        this.PowerWindMin = PowerWindMinP;
        this.PowerWindMax = PowerWindMaxP;
    }

    public void addProd(double[] prod, int day){
        ArrayList<Double> result = new ArrayList<Double>();

        double PowerMaxWindDay = 0.5 * PowerWindMax + 0.5 * PowerWindMax / Math.E * Math.exp(-Math.cos((1/58.2)*(day+240)))*Math.cos((1/29.1)*(day+240));
        double PowerMinWindDay = 0.5 * PowerWindMin + 0.5 * PowerWindMin / Math.E * Math.exp(-Math.cos((1/58.2)*(day+240)))*Math.cos((1/29.1)*(day+240));

        ArrayList<Double> prodWind = BasicModels.genSin(PowerMaxWindDay, PowerMinWindDay, 720, Math.PI/2);

        ArrayList<Double> prodMax = BasicModels.genConstant(getPowerMax());

        for(int i=0;i<1440;i++){
            result.add(Math.min(prodWind.get(i), prodMax.get(i)));
        }

        result = BasicModels.genDelay(result, 20);

        for(int i=0;i<1440;i++){
            prod[i] = prod[i] + result.get(i);
        }
    }
}
