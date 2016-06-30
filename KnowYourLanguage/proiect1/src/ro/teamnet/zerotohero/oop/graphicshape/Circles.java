package ro.teamnet.zerotohero.oop.graphicshape;

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

    public double getAreaPub(){
        Circle circle = new Circle();
        circle.setRadius(10);
        return circle.area();
    }
}
