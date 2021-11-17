package GraphicInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller implements ActionListener{

    private Model model;
    private View view;
    
    public Controller(Model model,View v){
        this.model = model;
        this.view = v;


    }

    @Override
    public void actionPerformed(ActionEvent e){
       
        String s = e.getActionCommand();
        if(s=="submit text"){
            System.out.println(view.textField.getText());
            model.number_of_days = Integer.parseInt(view.textField.getText());

            //view.textField.setText("   ");
        }
        else if(s=="Plot button"){
            PlotActivation();

        }
        /*

        switch(e.getActionCommand()){
            
            case "submit text":
                System.out.println(view.textField.getText());
            case "Plot button":
                System.out.println("not supposed to happen");
                //PlotActivation();
        }
        */

        
    }

    public void PlotActivation(){
        model.plotCons();
    }

    
}
