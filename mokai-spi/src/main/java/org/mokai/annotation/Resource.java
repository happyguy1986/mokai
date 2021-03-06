package org.mokai.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class is used to inject resources to the types (receivers, processors,
 * acceptors or actions).
 *
 * @author German Escobar
 */
@Documented
@Retention(value=RUNTIME)
@Target(value=FIELD)
@Inherited
public @interface Resource {

}
