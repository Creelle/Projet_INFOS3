package Production;

import java.util.ArrayList;

public class BasicModels {
    
    public static ArrayList<Double> genConstant(double PowerMax){
        ArrayList<Double> genList = new ArrayList<Double>();
        
        for(int i=0;i<1440;i++){
            genList.add(PowerMax);
        }

        return genList;
    }

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

    public static ArrayList<Double> genDelay(ArrayList<Double> listRef, int tau){
        ArrayList<Double> genList = new ArrayList<Double>();

        for(int i=0;i<1440;i++){
            if(i<tau){
                genList.add(listRef.get(listRef.size()-tau+i));
            } else {
                genList.add(listRef.get(i-tau));
            }
        }

        return genList;
    }

    public static ArrayList<Double> genSin(double PowerMax, double PowerMin, double Period, double Phase){
        ArrayList<Double> genList = new ArrayList<Double>();

        for(int i=0;i<1440;i++){
            genList.add((PowerMax+PowerMin)/2 + ((PowerMax-PowerMin)/2)*Math.sin(2*Math.PI*i/Period - Phase));
        }

        return genList;
    }

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

    public static double genCosDay(double PowerMin, double PowerMax, int day, double Period, double Phase){
        double genRet;

        genRet = (PowerMax+PowerMin)/2 + ((PowerMax-PowerMin)/2)*Math.cos(2*Math.PI*day/Period - Phase);
        
        return genRet;
    }

    public static double genLinear(int day1, int day2, double hour1, double hour2, int day){
        double genRet;

        genRet = (hour2*60 - hour1*60)/(day2 - day1) * (day - day1) + hour1*60;

        return genRet;
    }

}
