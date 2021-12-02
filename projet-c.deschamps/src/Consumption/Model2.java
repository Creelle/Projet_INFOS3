package Consumption;

import java.util.ArrayList;

import Models.BasicModels;

public class Model2 extends Device {

    private int dayMax, hMax;
    private double PowerMinRequest, PowerMaxRequest;
    private ArrayList<ArrayList<Double>> ConsYear;
    
    public Model2(double PowerMax, String name, double PowerMinRequest, double PowerMaxRequest, int dayMax, int hMax){
        super(PowerMax,name);
        this.PowerMinRequest = PowerMinRequest;
        this.PowerMaxRequest = PowerMaxRequest;
        this.dayMax = dayMax;
        this.hMax = hMax * 60;
        this.ConsYear = this.addConsYear();
    }

    public Model2(Model2 ModRef){
        super(ModRef.getPowerMax(),ModRef.getName());
        this.PowerMinRequest = ModRef.getPowerMinRequest();
        this.PowerMaxRequest = ModRef.getPowerMaxRequest();
        this.dayMax = ModRef.getDayMax();
        this.hMax = ModRef.getHMax();
        this.ConsYear = ModRef.getConsYear();
    }

    public double getPowerMinRequest(){
        return this.PowerMinRequest;
    }

    public double getPowerMaxRequest(){
        return this.PowerMaxRequest;
    }

    public int getDayMax(){
        return this.dayMax;
    }

    public int getHMax(){
        return this.hMax;
    }

    public ArrayList<ArrayList<Double>> getConsYear(){
        return this.ConsYear;
    }

    private ArrayList<ArrayList<Double>> addConsYear(){
        ArrayList<ArrayList<Double>> genRet = new ArrayList<>();

        ArrayList<Double> genCons = BasicModels.genConstant(getPowerMax());
        
        for(int day=1; day<=365; day++){
            double PowerDayMax = BasicModels.genCosDay(0, PowerMaxRequest, day, 365, 2*Math.PI*dayMax/365);
            double PowerDayMin = BasicModels.genCosDay(0, PowerMinRequest, day, 365, 2*Math.PI*dayMax/365);

            ArrayList<Double> genList = BasicModels.genLinear(PowerDayMax, PowerDayMin, 720);
            genList.addAll(BasicModels.genLinear(PowerDayMin, PowerDayMax, 720));
        
            genList = BasicModels.genDelay(genList, hMax);

            ArrayList<Double> ConsDay = new ArrayList<Double>();

            for(int i=0; i<1440; i++){
                ConsDay.add(Math.min(genList.get(i), genCons.get(i)));
            }

            genRet.add(ConsDay);
        }
        return genRet;
    }

    public void addCons(double[] prod, int day){
        double PowerNoise = getPowerMax()*0.05;
        double Noise = 0;
        
        for(int i=0; i<1440; i++){
            Noise = PowerNoise*Math.random()-PowerNoise/2;
            prod[i] = prod[i] + Math.max(ConsYear.get(day-1).get(i) + Noise, 0);
        }
    }
}
