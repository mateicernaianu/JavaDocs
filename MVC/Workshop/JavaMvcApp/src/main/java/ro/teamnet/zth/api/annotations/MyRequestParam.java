package ro.teamnet.zth.api.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by user on 7/15/2016.
 */
@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
@Documented
public @interface MyRequestParam {
    String name();
}
