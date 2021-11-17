package GraphicInterface;

public class InterfaceMain {
    public static void main(String[] args) {
        Model m = new Model();
        View v = new View();
        Controller c = new Controller(m,v);
        
        v.setController(c);
        
        v.show();
    }
}
