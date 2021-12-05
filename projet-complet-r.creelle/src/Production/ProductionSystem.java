package Production;

/** Super class of Production Systems */

public abstract class ProductionSystem {

    /**
     * This represents de maximum Power of the producer
     */

    private double PowerMax;

    /**
     * Constructor
     * @param PowerMaxP is the maximum power of the producer
     */
    public ProductionSystem(double PowerMaxP){
        this.PowerMax = PowerMaxP;
    }

    /**
     * Getter maximum power
     * @return the maximum power
     */
    public double getPowerMax() {
        return this.PowerMax;
    }

    /**
     * Setter power max
     * @param PowerMax is the power that you want to define
     */
    public void setPowerMax(double PowerMax) {
        this.PowerMax = PowerMax;
    }
    
    /**
     * Getter name of the producer
     * @return name
     */
    abstract public String getName();

    /**
     * Generates the daily production of the producer and add it
     * to the vector prod
     * @param prod is the vector that you want to add the production in
     * @param day is the day of the year that are simulated
     */
    abstract public void addProd(double[] prod, int day);
}
