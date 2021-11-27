package Production;

import java.util.ArrayList;

public class Nuclear extends ProductionSystem {
    private double PowerMaxFis, tauFis, tMaxFis;

    public Nuclear(double PowerMaxR, double PowerMaxFisR, double tauFisR, double tFisR) {
        super(PowerMaxR);
        this.PowerMaxFis = PowerMaxFisR;
        this.tauFis = tauFisR;
        this.tMaxFis = tFisR;
    }

    public String getName(){
        return("Nuclear");
    }

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
                minval = PowerMaxFis*Math.exp(-tMaxFis*tauFis)+getPowerMax();
                prod[i] = prod[i] + minval + delayQuad.get(contQuad);
                contQuad+=1;

                if(contQuad<1440 && (minval+delayQuad.get(contQuad))>prodMax.get(i)){
                    flagDelay = false;
                    contQuad = 0;
                }

            } else if(i>0 && diffPow.get(i)<0 && acumulate>0){
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
