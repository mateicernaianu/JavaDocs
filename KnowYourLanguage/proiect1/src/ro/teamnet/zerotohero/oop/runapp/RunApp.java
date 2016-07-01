package ro.teamnet.zerotohero.oop.runapp;

import ro.teamnet.zerotohero.Exceptions.Exception1;
import ro.teamnet.zerotohero.Exceptions.Exception2;
import ro.teamnet.zerotohero.canvas.Canvas;
import ro.teamnet.zerotohero.oop.graphicshape.*;

/**
 * Created by user on 6/30/2016.
 */
public class RunApp {

    public static void main(String[] args) throws Exception1 {
        Circles circles = new Circles();

        //=====================EXCEPTII==========================
        Circle circle = new Circle();
        try{//nu se arunca
            circle.setRadius(10);
        }
        catch(Exception1 e)
        {
            System.out.println(e.getMessage());
        }

        try{//se arunca
            circle.setRadius(0);
        }
        catch(Exception1 e)
        {
            System.out.println(e.getMessage());
        }
        //======================================================================


        //=====================EXCEPTII MULTIPLE==========================
        try{//nu se arunca
            circle.setRadius(10);
            circle.setxPos(10);
        }
        catch(Exception1 e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception2 e)
        {
            System.out.println(e.getMessage());
        }

        try{//se arunca
            circle.setRadius(0);
            circle.setxPos(0);
        }
        catch(Exception1 e)
        {
            System.out.println(e.getMessage());
        }
        catch(Exception2 e)
        {
            System.out.println(e.getMessage());
        }
        finally{
            System.out.println("aici");
        }
        //======================================================================


        System.out.println("The default circle area is " + circles.getAreaPub());
        circles.getAreaDef();
        Canvas canvas = new Canvas();
        canvas.getArea();

        Shape shape = new Circle(10);
        System.out.println(shape.toString());

        ShapeBehaviour sb = new Square(10);
        System.out.println(sb.area());

        Object p1 = new Point(10, 20);
        Object p2 = new Point(50, 100);
        Object p3 = new Point(10, 20);
        System.out.println("p1 equals p2 is " + p1.equals(p2));
        System.out.println("p1 equals p3 is " + p1.equals(p3));

    }


}
