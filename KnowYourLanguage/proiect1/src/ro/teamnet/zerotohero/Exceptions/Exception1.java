package ro.teamnet.zerotohero.Exceptions;

import sun.awt.CausedFocusEvent;

/**
 * Created by user on 7/1/2016.
 */
public class Exception1 extends Exception {
    public Exception1(String message){
        super(message);
    }
    Exception1(CausedFocusEvent.Cause c){
        super(String.valueOf(c));
    }

}
