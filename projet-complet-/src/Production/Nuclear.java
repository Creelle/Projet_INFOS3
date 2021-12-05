package Production;

import java.util.ArrayList;

import Models.BasicModels;

/** Class of Nuclear producer */

public class Nuclear extends ProductionSystem {
    
    /**
     * PowerMaxFis is the maximum power of nuclear fission
     * tauFis is the time decrement of the exponential function of fission
     * tMaxFis is the period of the exponential function
     */
    private double PowerMaxFis, tauFis, tMaxFis;

    /**
     * Constructor
     * @param PowerMaxR is the maximum power of the producer
     * @param PowerMaxFisR is the maximum power of the fission
     * @param tauFisR is the time decrement of exponential function of fiction
     * @param nFis is the daily number of fissions
     */
    public Nuclear(double PowerMaxR, double PowerMaxFisR, double tauFisR, int nFis) {
        super(PowerMaxR);
        this.PowerMaxFis = PowerMaxFisR;
        this.tauFis = tauFisR;
        this.tMaxFis = 1440/nFis;
    }

    /**
     * Getter name
     * @return name
     */
    public String getName(){
        return("Nuclear");
    }

    /**
     * Generates the daily production and add it to vector prod
     * @param prod is the vector that you want to add the production in
     * @param day is the day of the year that are simulated
     */
    public void addProd(double[] prod, int day){

        ArrayList<Double> diffFis = new ArrayList<Double>();

        ArrayList<Double> diffPow = new ArrayList<Double>();

        ArrayList<Double> prodFission = BasicModels.genExp(PowerMaxFis, tauFis, tMaxFis);

        ArrayList<Double> prodMax = BasicModels.genConstant(getPowerMax());

        ArrayList<Double> delayQuad = BasicModels.genQuadratic(-getPowerMax(), -tMaxFis/10, tMaxFis/10);

        int contQuad = 0;
        double acumulate = 0;
        double minval = 0;
        boolean flagDelay = true;

        for(int i=0;i<1440;i++){

            diffPow.add(prodFission.get(i) - prodMax.get(i));

            if(flagDelay){
                //* This part is responsible for implementing  the quadratic delay*/
                minval = PowerMaxFis*Math.exp(-tMaxFis*tauFis)+getPowerMax();
                prod[i] = prod[i] + minval + delayQuad.get(contQuad);
                contQuad+=1;

                if(contQuad<1440 && (minval+delayQuad.get(contQuad))>prodMax.get(i)){
                    flagDelay = false;
                    contQuad = 0;
                }

            } else if(i>0 && diffPow.get(i)<0 && acumulate>0){
                /* This part is responsible for accumulating the fission power that is not instantly used
                As is accumulated as thermal power, here there's no restrictions in accumulation */
                acumulate+=diffPow.get(i);
                if(acumulate<0){
                    prod[i] = prod[i] + prodMax.get(i)+acumulate;
                    acumulate=0;
                } else{
                    prod[i] = prod[i] + prodMax.get(i);
                }
            } else {
                prod[i] = prod[i] + Math.min(prodFission.get(i), prodMax.get(i));
            }

            diffFis.add(prodFission.get(i) - prod[i]);

            if(diffFis.get(i)>0){
                acumulate+=diffFis.get(i);
            }

            if(i<1439 && prodFission.get(i+1)>prodFission.get(i)){
                flagDelay=true;
            }

        }
    }
}
