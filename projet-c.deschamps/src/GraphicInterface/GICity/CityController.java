package GraphicInterface.GICity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;

public class CityController implements ActionListener {

    private CityModel model;
    private View view;
    
    public CityController(CityModel model,View v){
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
