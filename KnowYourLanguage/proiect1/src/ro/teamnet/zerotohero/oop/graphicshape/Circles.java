package ro.teamnet.zerotohero.oop.graphicshape;

import ro.teamnet.zerotohero.Exceptions.Exception1;

/**
 * Created by user on 6/30/2016.
 */
public class Circles {

    public void getAreaDef(){
        Circle circle = new Circle();
        circle.fillColor();
        circle.fillColor(2);
        circle.fillColor((float)3.3);
    }

    public double getAreaPub() throws Exception1 {
        Circle circle = new Circle();

        return circle.area();
    }
}
