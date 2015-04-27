package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used on your objects' fields and represents a field of an object
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 */
@Documented
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAdaptedTypeDoc {

    public Class<?> value();

    public String format() default "";

    public String[] allowedValues() default {};

}
