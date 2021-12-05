package Consumption;

/** Super class of Devices */

abstract public class Device {

    /**
     * This represents de maximum Power of the device
     */
    private double PowerMax;
    /**
     * This is the name of the device
     */
    private String name;

    /**
     * Constructor
     * @param PowerMax is the maximum power of the device
     * @param name is the name of the device
     */
    public Device(double PowerMax, String name){
        this.PowerMax = PowerMax;
        this.name = name;
    }

    /**
     * Getter maximum power
     * @return the maximum power
     */
    public double getPowerMax(){
        return PowerMax;
    }

    /**
     * Getter name
     * @return name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Generates the daily consummation of this device add it to vector cons
     * @param cons is the vector that you want to add the production in
     * @param day is the day of the year that are simulated
     */
    abstract public void addCons(double[] cons, int day);
}
