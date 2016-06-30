package ro.teamnet.zerotohero.oop.graphicshape;

/**
 * Created by user on 6/30/2016.
 */
public class Square extends Shape {

    private int side;

    public Square(int newSide){
        side = newSide;
    }

    @Override
    public double area(){
        return side*side;
    }
}
