package GraphicInterface.GINetwork;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;

public class NetController implements ActionListener {

    private NetModel model;
    private View view;
    
    public NetController(NetModel model,View v){
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println(this.model);
        System.out.println(this.view);
        System.out.println("This should mean nothing");
        
        System.exit(0);
    }
    
}