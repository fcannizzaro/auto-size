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
public @interface AutoMargin {
    int l() default 0;
    int r() default 0;
    int t() default 0;
    int b() default 0;
    int value() default 0;
}
