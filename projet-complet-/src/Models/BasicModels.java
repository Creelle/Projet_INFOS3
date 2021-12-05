package Models;

import java.util.ArrayList;

/**
 *  This group of static methods were used to make functions that built different models
 */

public class BasicModels {
    
    /**
     * This method constructs a constant function
     * @param PowerMax is the power of the function that we want to generate
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genConstant(double PowerMax){
        ArrayList<Double> genList = new ArrayList<Double>();
        
        for(int i=0;i<1440;i++){
            genList.add(PowerMax);
        }

        return genList;
    }

    /**
     * This method constructs a quadratic function that's 0 when it's smaller than r1 and bigger than r2 (roots of the function)
     * @param PowerMax is the power of the function that we want to generate
     * @param r1 the first root of the quadratic function
     * @param r2 the second root of the quadratic function
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genQuadratic(double PowerMax, double r1, double r2){
        ArrayList<Double> genList = new ArrayList<Double>();

        double coefQuad = -4 * PowerMax / Math.pow(r2 - r1, 2);
        
        for(int i=0; i<1440; i++){
            if(i<=r1 || i>=r2){
                genList.add(0.0);
            } else{
                genList.add(coefQuad*(i-r1)*(i-r2));
            }
        }

        return genList;
    }

    /**
     * This method constructs a delay in a function that already exists
     * @param listRef is the constructed function that we want to apply the delay
     * @param tau is the delay that we want to apply
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genDelay(ArrayList<Double> listRef, int tau){
        ArrayList<Double> genList = new ArrayList<Double>();

        for(int i=0;i<listRef.size();i++){
            if(i<tau){
                genList.add(listRef.get(listRef.size()-tau+i));
            } else {
                genList.add(listRef.get(i-tau));
            }
        }

        return genList;
    }

    /**
     * This method constructs a sine function
     * @param PowerMax is the maximum power of sine function
     * @param PowerMin is the minimum power of the sine function
     * @param Period is the period of the function
     * @param Phase is the phase of the function
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genSin(double PowerMax, double PowerMin, double Period, double Phase){
        ArrayList<Double> genList = new ArrayList<Double>();

        for(int i=0;i<1440;i++){
            genList.add((PowerMax+PowerMin)/2 + ((PowerMax-PowerMin)/2)*Math.sin(2*Math.PI*i/Period - Phase));
        }

        return genList;
    }

    /**
     * This method constructs a exponential periodic function
     * @param PowerMax is the maximum power of the function
     * @param tau is the time decrement of the function
     * @param tmax is the period of the function
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genExp(double PowerMax, double tau, double tmax){
        ArrayList<Double> genList = new ArrayList<Double>();

        int contExp = 0;

        for(int i=0; i<1440; i++){
            genList.add(PowerMax*Math.exp(-contExp*tau));
            contExp+=1;
            if(contExp>=tmax){
                contExp=0;
            }
        }

        return genList;
    }

    /**
     * This method constructs a hyperbolical tangent periodic function
     * @param PowerMax is the maximum power of the function
     * @param nTimes is the frequency of the function
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genTanH(double PowerMax, int nTimes){
        ArrayList<Double> genList = new ArrayList<Double>();

        int Period = 1440/nTimes;

        int contTH = 0;

        for(int i=0; i<1440; i++){
            genList.add(0.5*PowerMax*(Math.tanh(0.1*contTH-3)-Math.tanh(0.1*contTH-Period*0.1+3)));
            contTH+=1;
            if(contTH>=Period){
                contTH=0;
            }
        }

        return genList;
    }

    /**
     * This method constructs a linear function
     * @param P1 is the power in the beginning of the linear function
     * @param P1 is the power in the end of the linear function
     * @param tMax is the maximum time (the end) of the linear function
     * @return an ArrayList with the function
     */
    public static ArrayList<Double> genLinear(double P1, double P2, double tMax){
        ArrayList<Double> genList = new ArrayList<Double>();
        double genRet;

        for(int i=0; i<tMax; i++){
            genRet = (P2 - P1)/tMax * i + P1;
            genList.add(genRet);
        }

        return genList;
    }
    
    /**
     * This method is a cosine function that return just one point of the function
     * It was useful to make seasonal producers models
     * @param PowerMin is the minimum power of the function
     * @param PowerMax is the maximum power of the function
     * @param day is the point (day) that we want to know
     * @param Period is the period of the function
     * @param Phase is the phase of the function
     * @return the value of the function in the point (day)
     */
    public static double genCosDay(double PowerMin, double PowerMax, int day, double Period, double Phase){
        double genRet;

        genRet = (PowerMax+PowerMin)/2 + ((PowerMax-PowerMin)/2)*Math.cos(2*Math.PI*day/Period - Phase);
        
        return genRet;
    }

    /**
     * This method is a linear function that return just one point of the function
     * It was useful to make seasonal producers models
     * @param day1 is the first day of the function (interpret as the x1 of the linear function)
     * @param day2 is the second day of the function (interpret as the x2 of the linear function)
     * @param hour1 is the first hour of the function (interpret as the y1 of the linear function)
     * @param hour2 is the second hour of the function (interpret as the y2 of the linear function)
     * @param day is the day that we want to know
     * @return the result of the point (day)
     */
    public static double genLinearDay(int day1, int day2, double hour1, double hour2, int day){
        double genRet;

        genRet = (hour2*60 - hour1*60)/(day2 - day1) * (day - day1) + hour1*60;

        return genRet;
    }
}
