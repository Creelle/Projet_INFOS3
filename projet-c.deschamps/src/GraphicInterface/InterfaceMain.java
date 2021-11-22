package GraphicInterface;

public class InterfaceMain {
    public static void main(String[] args) {
        ConsModel m = new ConsModel();
        View v = new View();
        ConsController cc = new ConsController(m,v);
        Controller c = new Controller();
        
        v.setController(c,cc);
        
        v.show();
    }
}
