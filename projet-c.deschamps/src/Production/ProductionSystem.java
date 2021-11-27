package Production;


public abstract class ProductionSystem {

    private double PowerMax;

    public ProductionSystem(double PowerMaxP){
        this.PowerMax = PowerMaxP;
    }

    public double getPowerMax() {
        return this.PowerMax;
    }

    public void setPowerMax(double PowerMax) {
        this.PowerMax = PowerMax;
    }
    

    abstract public void addProd(double[] result, int day);
}
