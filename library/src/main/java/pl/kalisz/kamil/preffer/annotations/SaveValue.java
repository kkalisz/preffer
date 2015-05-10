package pl.kalisz.kamil.preffer.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * annotation that indicates method is used as access to prefernces
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SaveValue
{
    /**
     * @return value used as preference key
     */
    String key();

    /**
     * @return value indicates that normal clean preferences should not delete this value,
     */
    boolean persistent() default false;

    /**
     * @return value indicates that this preference is profile dependent
     */
    boolean profile() default false;
}
