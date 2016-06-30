package ro.teamnet.zerotohero.gc;

/**
 * Created by user on 6/30/2016.
 */
public class DemoObject {

    private static final int bufferSize = 100000;
    private String temp;
    private String objectRef;
    private static int count = 0;

    public DemoObject() {
        this.objectRef = this.toString();

    }
}

