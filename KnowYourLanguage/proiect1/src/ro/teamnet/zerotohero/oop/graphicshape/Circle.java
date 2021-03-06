package ro.teamnet.zerotohero.oop.graphicshape;
import ro.teamnet.zerotohero.Exceptions.Exception1;
import ro.teamnet.zerotohero.Exceptions.Exception2;

import java.lang.Math;
/**
 * Created by user on 6/30/2016.
 */
public class Circle extends Shape {
    private int xPos;
    private int yPos;
    private int radius;

    public Circle(){
        xPos=yPos=radius=0;
    }

    public Circle(int radius){
        this.radius=radius;
    }

    public Circle(int xPos, int yPos){
        this.xPos=xPos;
        this.yPos=yPos;
    }

    public Circle(int xPos, int yPos ,int radius){
        this.xPos=xPos;
        this.yPos=yPos;
        this.radius=radius;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) throws Exception2 {

        if(xPos == 0)
            if(xPos == 0)
                throw new Exception2("nu ai voie sa pui 0 pe xPOS");
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getRadius() {

        return radius;
    }

    public void setRadius(int radius) throws Exception1 {

        if(radius == 0)
            throw new Exception1("nu ai voie sa pui 0");
        this.radius = radius;
    }

    @Override
    public double area() {
        return (double)Math.PI*radius*radius;
    }

    @Override
    public String toString(){
        return "center = (" + xPos + "," + yPos + ") and radius = " + radius;
    }

    public void fillColor(){
        System.out.println(super.getColor());
    }

    public void fillColor(int newColor){
        super.setColor(newColor);
        System.out.println("The circle color is now " + super.getColor());
    }

    public void fillColor(float newSaturation){
        super.setSaturation(newSaturation);
        System.out.println("The circle saturation is now " + super.getSaturation());
    }
}
