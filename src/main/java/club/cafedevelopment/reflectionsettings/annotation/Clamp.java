package club.cafedevelopment.reflectionsettings.annotation;

/**
 * @author Reap
 */
public @interface Clamp {
    /**
     * @return the min value for a number setting.
     */
    double min() default Double.MIN_VALUE;

    /**
     * @return the max value for a number setting.
     */
    double max() default Double.MIN_VALUE;
}
