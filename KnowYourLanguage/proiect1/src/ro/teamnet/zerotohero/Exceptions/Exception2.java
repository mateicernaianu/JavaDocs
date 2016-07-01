package ro.teamnet.zerotohero.Exceptions;

import sun.awt.CausedFocusEvent;

/**
 * Created by user on 7/1/2016.
 */
public class Exception2 extends Throwable {
    public Exception2(String message){
        super(message);
    }
    public Exception2(CausedFocusEvent.Cause c){
        super(String.valueOf(c));
    }
}
