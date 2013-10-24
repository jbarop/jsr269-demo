package de.barop.demos.jsr269.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Annotation which will be processed.
 *
 * @author Johannes Barop <jb@barop.de>
 */
@Target({TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Generate {
}
