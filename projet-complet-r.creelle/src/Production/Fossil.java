package Production;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of Fossil producer */

public class Fossil extends ProductionSystem {
    
    /** Is the number of daily cycles of the function */
    private int Cycles;

    /**
     * Constructor
     * @param PowerMaxR is the maximum power of this producer
     * @param dayCycles is the number of daily cycles of this producer
     */
    public Fossil (double PowerMaxR, int dayCycles){
        super(PowerMaxR);
        this.Cycles = dayCycles;
    }

    /**
     * Getter name
     * @return name
     */
    public String getName(){
        return("Fossil");
    }

    /**
     * Generates the daily production and add it to vector prod
     * @param prod is the vector that you want to add the production in
     * @param day is the day of the year that are simulated
     */
    public void addProd(double[] prod, int day){
        ArrayList<Double> prodFos = BasicModels.genTanH(1, Cycles);

        prodFos = BasicModels.genDelay(prodFos, 30);

        for(int i=0; i<1440; i++){
            double id = i/1.0;
            prod[i] = prod[i] + getPowerMax()/3*prodFos.get(i)*Math.abs(Math.sin(id/5)+2);
        }

    }
}
