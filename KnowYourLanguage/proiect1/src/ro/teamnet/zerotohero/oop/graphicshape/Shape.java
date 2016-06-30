package ro.teamnet.zerotohero.oop.graphicshape;

/**
 * Created by user on 6/30/2016.
 */
public class Shape extends AbstractShape implements ShapeBehaviour {

    protected int color;
    protected float saturation;

    public Shape(){
        color = 0;
        saturation = 0;
    }

    public Shape(int newColor, float newSaturation){
        color = newColor;
        saturation = newSaturation;
    }

    @Override
    public double area(){
        return 0;
    }


    public int getColor(){
        return color;
    }

    public float getSaturation(){
        return saturation;
    }

    public void setColor(int color){
        this.color=color;
    }

    public void setSaturation(float saturation){
        this.saturation=saturation;
    }

}
