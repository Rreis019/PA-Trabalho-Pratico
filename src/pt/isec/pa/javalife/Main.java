package pt.isec.pa.javalife;

import javafx.application.Application;
import pt.isec.pa.javalife.ui.gui.MainJFX;



public class Main{

    //É so uma função dummy para ver como funciona o junit , depois ira ser removida
    public int somar(int a ,int b){
        return a+b;
    }

    public static void main(String[] args) {
        Application.launch(MainJFX.class, args);
    }
}
