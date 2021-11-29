package Production;

import java.util.ArrayList;

public class Fossil extends ProductionSystem {
    
    private int Cycles;

    public Fossil (double PowerMaxR, int dayCycles){
        super(PowerMaxR);
        this.Cycles = dayCycles;
    }

    public String getName(){
        return("Fossil");
    }

    public void addProd(double[] prod, int day){
        ArrayList<Double> prodFos = BasicModels.genTanH(1, Cycles);

        prodFos = BasicModels.genDelay(prodFos, 30);

        for(int i=0; i<1440; i++){
            double id = i/1.0;
            prod[i] = prod[i] + getPowerMax()/3*prodFos.get(i)*Math.abs(Math.sin(id/5)+2);
        }

    }
}
