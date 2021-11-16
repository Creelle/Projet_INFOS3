package GraphicInterface;

public class InterfaceMain {
    public static void main(String[] args) {
        Model m = new Model();
        Controller c = new Controller(m);
        View v = new View();
        v.setController(c);
        
        v.show();
    }
}
