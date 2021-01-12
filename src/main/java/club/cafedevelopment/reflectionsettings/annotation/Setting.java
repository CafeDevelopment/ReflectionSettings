package club.cafedevelopment.reflectionsettings.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Reap
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Setting {
    /**
     * @return the ID for the setting. Leaving it as default will retrieve the field name as the setting ID.
     */
    String id() default "FIELD_NAME";

    /**
     * @return the description for the setting.
     */
    String description() default "No description provided.";

    /**
     * @return the clamp value for the setting, is empty by default and only override it for number settings.
     */
    Clamp clamp() default @Clamp;
}
