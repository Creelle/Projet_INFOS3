package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model3 extends Device {

    private int nCycles, dayMax;
    private double PowerMin, factMin;
    
    public Model3(double PowerMin, double PowerMax, String name, int nCycles, int dayMax, double factMin){
        super(PowerMax, name);
        this.PowerMin = PowerMin;
        this.nCycles = nCycles;
        this.dayMax = dayMax;
        this.factMin = factMin;
    }

    public void addCons(double[] prod, int day){
        ArrayList<Double> genFact = BasicModels.genLinear(1, factMin, 182.5);
        genFact.addAll(BasicModels.genLinear(factMin, 1, 182.5));
        genFact = BasicModels.genDelay(genFact, dayMax);

        ArrayList<Double> genCons = BasicModels.genConstant(PowerMin);
        ArrayList<Double> genQuad = BasicModels.genQuadratic(genFact.get(day)*(getPowerMax()-PowerMin), 0, 0.6*1440/nCycles);
        ArrayList<Double> genNoise = BasicModels.genWhiteNoise(0.1*getPowerMax());

        int contQuad=0;

        for(int i=0; i<1440; i++){
            prod[i] = prod[i] + Math.max(genCons.get(i) + genQuad.get(contQuad) + genNoise.get(i),0);
            contQuad+=1;
            if(contQuad>=1440/nCycles){
                contQuad=0;
            }
        }
    }
}
