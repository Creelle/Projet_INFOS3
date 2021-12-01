package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model4 extends Device {

    private double coefLin, tau, PowerMin, fPause;
    private int nCycles;

    public Model4(double PowerMin, double PowerMax, String name, double coefLin, double tau, int cycles, double fPause){
        super(PowerMax, name);
        this.coefLin = coefLin;
        this.tau = - tau;
        this.nCycles = cycles;
        this.PowerMin = PowerMin;
        this.fPause = fPause;
    }

    public void addCons(double[] prod, int day){
        ArrayList<Double> genExp = BasicModels.genExp(1, tau, (1-fPause)*1440/nCycles);
        ArrayList<Double> genLin = BasicModels.genLinear(coefLin*1440/nCycles, 0, (1-fPause)*1440/nCycles);
        ArrayList<Double> genNoise = BasicModels.genWhiteNoise(0.5*PowerMin);
        ArrayList<Double> genCons = BasicModels.genConstant(getPowerMax());

        int contLin = 0;
        double minExpLin=0;

        for(int i=0; i<1440; i++){
            if(contLin<(1-fPause)*1440/nCycles){
                minExpLin = Math.min(genExp.get(contLin), genLin.get(contLin));
                prod[i] = prod[i] + Math.min(genCons.get(i), minExpLin) + genNoise.get(i) + PowerMin;
            } else{
                prod[i] = prod[i] + genNoise.get(i) + PowerMin;;
            }
            
            contLin+=1;
            if(contLin>=1440/nCycles){
                contLin=0;
            }
        }
        
    }
}
