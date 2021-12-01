package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model4 extends Device {

    private double coefLin, tau, PowerMin;
    private int nCycles;

    public Model4(double PowerMin, double PowerMax, String name, double coefLin, double tau, int cycles){
        super(PowerMax, name);
        this.coefLin = coefLin;
        this.tau = - tau;
        this.nCycles = cycles;
        this.PowerMin = PowerMin;
    }

    public void addCons(double[] prod, int day){
        ArrayList<Double> genExp = BasicModels.genExp(1, tau, 1440/nCycles);
        ArrayList<Double> genLin = BasicModels.genLinear(coefLin*1440/nCycles, 0, 1440/nCycles);
        ArrayList<Double> genNoise = BasicModels.genWhiteNoise(0.5*PowerMin);
        ArrayList<Double> genCons = BasicModels.genConstant(getPowerMax());

        int contLin = 0;
        double minExpLin=0;

        for(int i=0; i<1440; i++){
            minExpLin = Math.min(genExp.get(i), genLin.get(contLin));
            prod[i] = prod[i] + Math.min(genCons.get(i), minExpLin) + genNoise.get(i) + PowerMin;
            contLin+=1;
            if(contLin>=1440/nCycles){
                contLin=0;
            }
        }
        
    }
}
