package GraphicInterface;


import GraphicInterface.GICons.*;
import GraphicInterface.GIProd.*;
import GraphicInterface.GICity.*;
import GraphicInterface.GINetwork.*;

public class InterfaceMain {
    public static void main(String[] args) {
        ConsModel mc = new ConsModel();
        ProdModel mp = new ProdModel();
        CityModel mcity = new CityModel();
        NetModel mnet = new NetModel();

        View v = new View();

        Controller c = new Controller();
        ConsController cc = new ConsController(mc,v);
        ProdController pc = new ProdController(mp,v);
        CityController cityc = new CityController(mcity,v);
        NetController netc = new NetController(mnet,v);
        
        v.setController(c,cc,pc,cityc,netc);
        
        v.show();
    }
}
