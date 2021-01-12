package club.cafedevelopment.reflectionsettings.container;

import java.lang.reflect.Field;

/**
 * @author Reap
 */
public final class SettingContainer {
    private final String id, description;
    private final Field value;
    private final Double min, max;
    private final Object host;

    /**
     * The constructor for setting containers. Cannot be executed outside of the {@link club.cafedevelopment.reflectionsettings.container} so it isn't used in the wrong way.
     * @param idIn the ID for the setting.
     * @param descriptionIn the description for the setting.
     * @param valueIn the {@link Field} that holds the value.
     * @param minIn the min value for Number settings.
     * @param maxIn the max value for Number settings.
     * @param hostIn the host object, is included so we bypass IllegalAccessException(s).
     */
    SettingContainer(String idIn, String descriptionIn, Field valueIn, double minIn, double maxIn, Object hostIn) {
        id = idIn;
        description = descriptionIn;
        value = valueIn;
        min = minIn;
        max = maxIn;
        host = hostIn;
    }

    /**
     * @return the setting's ID.
     */
    public String getId() { return id; }
    /**
     * @return the setting's description.
     */
    public String getDescription() { return description; }

    /**
     * @param <T> the type we're casting the return value to.
     * @return the value off of {@link #value}.
     */
    @SuppressWarnings("unchecked")
    public <T> T getValue() {
        try {
            Object val = value.get(host);

            if (val instanceof Number) {
                Number number = (Number) val;
                if (number.doubleValue() < min)
                    return (T) min;
                else if (number.doubleValue() > max)
                    return (T) max;
            }

            return (T) val;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new NullPointerException();
        }
    }

    /**
     *
     * @param val the new value we set
     */
    public void setValue(Object val) {
        try {
            value.set(host, val);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the min value for Number settings.
     */
    public double getMin() { return min; }

    /**
     * @return the max value for Number settings.
     */
    public double getMax() { return max; }

    /**
     * @return the host for the setting.
     */
    public Object getHost() { return host; }

    /**
     * @return debug info for the setting, is useful for debugging, and not much else.
     */
    public String getDebugInfo() { return "ID: " + getId() + ", Description: " + getDescription() + ", Value: " + getValue(); }
}
