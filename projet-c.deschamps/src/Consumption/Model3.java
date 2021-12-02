package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model3 extends Device {

    private int nCycles, dayMax;
    private double PowerMin, factMin;
    private ArrayList<ArrayList<Double>> ConsYear;
    
    public Model3(double PowerMin, double PowerMax, String name, int nCycles, int dayMax, double factMin){
        super(PowerMax, name);
        this.PowerMin = PowerMin;
        this.nCycles = nCycles;
        this.dayMax = dayMax;
        this.factMin = factMin;
        this.ConsYear = this.addConsYear();
    }

    public Model3(Model3 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.PowerMin = ModRef.getPowerMin();
        this.nCycles = ModRef.getNCycles();
        this.dayMax = ModRef.getDayMax();
        this.factMin = ModRef.getFactMin();
        this.ConsYear = ModRef.getConsYear();
    }

    public double getPowerMin(){
        return this.PowerMin;
    }

    public int getNCycles(){
        return this.nCycles;
    }

    public int getDayMax(){
        return this.dayMax;
    }

    public double getFactMin(){
        return this.factMin;
    }

    public ArrayList<ArrayList<Double>> getConsYear(){
        return this.ConsYear;
    }

    private ArrayList<ArrayList<Double>> addConsYear(){
        ArrayList<ArrayList<Double>> genRet = new ArrayList<>();

        ArrayList<Double> genFact = BasicModels.genLinear(1, factMin, 182.5);
        genFact.addAll(BasicModels.genLinear(factMin, 1, 182.5));
        genFact = BasicModels.genDelay(genFact, dayMax);

        ArrayList<Double> genCons = BasicModels.genConstant(PowerMin);

        for(int day=1; day<=365; day++){
            ArrayList<Double> genQuad = BasicModels.genQuadratic(genFact.get(day)*(getPowerMax()-PowerMin), 0, 0.6*1440/nCycles);

            int contQuad=0;

            ArrayList<Double> ConsDay = new ArrayList<Double>();

            for(int i=0; i<1440; i++){
                ConsDay.add(genCons.get(i) + genQuad.get(contQuad));
                contQuad+=1;
                if(contQuad>=1440/nCycles){
                    contQuad=0;
                }
            }
            genRet.add(ConsDay);
        }
        return genRet;
    }

    public void addCons(double[] prod, int day){
        double PowerNoise = PowerMin*0.75;
        double Noise = 0;

        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            prod[i] = prod[i] + ConsYear.get(day-1).get(i) + Noise;
        }
    }
}
