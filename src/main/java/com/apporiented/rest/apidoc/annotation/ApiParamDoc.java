package com.apporiented.rest.apidoc.annotation;

import java.lang.annotation.*;

/**
 * This annotations is to be used inside an annotations of type ApiParams
 *
 * @author Fabio Maffioletti
 * @author Lars Behnke
 * @see com.apporiented.rest.apidoc.model.ApiParamDocModel
 */
@Documented
@Target(value = ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParamDoc {

    public String value() default "";

    public String[] allowedValues() default {};

    public String format() default "";

    public String dataType() default "";

}
