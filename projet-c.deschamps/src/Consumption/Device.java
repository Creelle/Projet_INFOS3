package Consumption;

abstract public class Device {
    private double PowerMax;
    private String name;

    public Device(double PowerMax, String name){
        this.PowerMax = PowerMax;
        this.name = name;
    }

    public double getPowerMax(){
        return PowerMax;
    }

    public String getName(){
        return this.name;
    }

    abstract public void addCons(double[] prod, int day);
}
