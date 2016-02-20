package com.github.fcannizzaro.autosize.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Francesco Cannizzaro (fcannizzaro)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutoScale {
    int width();

    int height();

    String as() default "111";
}
