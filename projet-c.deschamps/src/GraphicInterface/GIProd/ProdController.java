package GraphicInterface.GIProd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GraphicInterface.View;

public class ProdController implements ActionListener {

    private ProdModel model;
    private View view;
    
    public ProdController(ProdModel model,View v){
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
        String s = e.getActionCommand();
        if(s=="Main"){
            model.plotProd();
        }
    }
    
}
